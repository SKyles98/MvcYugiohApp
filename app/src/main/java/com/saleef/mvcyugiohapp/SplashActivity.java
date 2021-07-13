package com.saleef.mvcyugiohapp;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;




import com.saleef.mvcyugiohapp.Common.Base.BaseActivity;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Common.FragmentHelper.FragmentFrameHelper;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;
import com.saleef.mvcyugiohapp.UseCases.FetchCardsUseCase;
import com.saleef.mvcyugiohapp.UseCases.FetchDataBaseVersionUseCase;
import com.saleef.mvcyugiohapp.UseCases.FetchNewCardsUseCase;
import com.saleef.mvcyugiohapp.Utilities.CardtoCV;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;


import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;






// TODO Implement sortOptions by limit and reverse option in Banlist Screen
//TODO Create database for ocg as well to prevent constant network calls
public class SplashActivity extends BaseActivity implements FetchCardsUseCase.Listener,
        FragmentFrameHelper,FetchDataBaseVersionUseCase.Listener,FetchNewCardsUseCase.Listener {




    private enum ScreenState { // Finite state machine to monitor state of user screen
        IDLE, FETCHING_Cards, Card_Retrieved, NETWORK_ERROR
    }
   private FetchCardsUseCase mFetchCardsUseCase;
    private FetchDataBaseVersionUseCase mFetchDataBaseVersionUseCase;
    private FetchNewCardsUseCase mFetchNewCardsUseCase;
    private ScreenNavigator mScreenNavigator;
    private  DialogManager mDialogManager;
    private CardtoCV mCardtoCV;
    private ScreenState mScreenState = ScreenState.IDLE;
    private SharedPrefs mSharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        mFetchCardsUseCase = getControllerCompositionRoot().getFetchCardsUseCase();
        mFetchDataBaseVersionUseCase = getControllerCompositionRoot().getFetchDataBaseVersionUseCase();
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
        mDialogManager = getControllerCompositionRoot().getDialogManger();
        mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();
        mFetchNewCardsUseCase = getControllerCompositionRoot().getFetchNewCardsUseCase();
        catchErrorResult();
        mCardtoCV = new CardtoCV();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchCardsUseCase.registerListener(this);
        mFetchDataBaseVersionUseCase.registerListener(this);
        mFetchNewCardsUseCase.registerListener(this);
        if (!alreadyInserted() && mScreenState!=ScreenState.NETWORK_ERROR){
              fetchCards();
        } else{
            fetchDataBaseVersion();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchCardsUseCase.unregisterListener(this);
        mFetchNewCardsUseCase.unregisterListener(this);
        mFetchDataBaseVersionUseCase.unregisterListener(this);
    }


    private void fetchCards(){
        mScreenState = ScreenState.FETCHING_Cards;
        mDialogManager.showLoadingDialog();
        mFetchCardsUseCase.fetchCards();
    }

    private void fetchDataBaseVersion(){
        mScreenState = ScreenState.FETCHING_Cards;
        mDialogManager.showLoadingDialog();
        mFetchDataBaseVersionUseCase.fetchDataBaseVer();
    }

    private void fetchNewCards(){
        mScreenState = ScreenState.FETCHING_Cards;
        mFetchNewCardsUseCase.fetchNewCards();
    }

    // Checks if the database is cached or not
    private boolean alreadyInserted(){
        File dbFile = getDatabasePath("Yugioh.db");
        return dbFile.exists();
    }


    @Override
    public void onAllCardsRetrievalSuccess(List<YugiohCard> YugiohCards) {
        mDialogManager.showLoadingDialog();
                mScreenState = ScreenState.Card_Retrieved;
                mSharedPrefs.saveDataBaseSize(YugiohCards.size());
                insertIntoDataBase(YugiohCards);
    }

    @Override
    public void onAllCardsRetrievalFailure(String errorMessage) {
        mScreenState = ScreenState.NETWORK_ERROR;
        mDialogManager.showErrorDialog(Constant.NETWORK_ERROR_MESSAGE,"network");
        Log.i("dERROR",errorMessage);
    }

    private void insertIntoDataBase(List<YugiohCard> yugiohCards){


           ContentValues[] bulkValues = mCardtoCV.convertToContentValues(yugiohCards);
           int i =  getContentResolver().bulkInsert(YgoInfoFeeder.InfoFeeder.CONTENT_URI, bulkValues);

              // Successful insertion
              if (i==0){
                  mScreenNavigator.leaveSplashScreen();
              }
        }

        private List<YugiohCard> getAllCardsDataBase(){
         Cursor cursor = getContentResolver().query(YgoInfoFeeder.InfoFeeder.CONTENT_URI,YgoInfoFeeder.InfoFeeder.PROJECTION_ALL
                 ,null,null,null);

            return mCardtoCV.convertToCards(cursor);
        }

    private void insertIntoNewCardsDataBase(List<YugiohCard> yugiohCards){


        ContentValues[] bulkValues = mCardtoCV.convertToContentValues(yugiohCards);
        int i =  getContentResolver().bulkInsert(YgoInfoFeeder.NewCards.CONTENT_URI, bulkValues);

        // Successful insertion
        if (i==0){
            Log.i("newest",yugiohCards.toString());
        } else {
            Log.i("error","Cards wasnt added or something");
        }
    }

    private List<YugiohCard> getNewestCardsFromDataBase(){ // Used to check if any of the new cards we fetched isnt in here
        Cursor cursor = getContentResolver().query
                (YgoInfoFeeder.NewCards.CONTENT_URI,YgoInfoFeeder.NewCards.PROJECTION_ALL,null,null,null);
        return mCardtoCV.convertToCards(cursor);
    }



   private void catchErrorResult(){ // When hit with a network error retry the fetch if user hit yes
        getSupportFragmentManager().setFragmentResultListener(Constant.ERRORKEY, this, (requestKey, result) -> {
            mDialogManager.showLoadingDialog();
            mScreenState = ScreenState.FETCHING_Cards;
            fetchCards();
        });
   }

    @Override
    public void onUpdateRetrievalSuccess(String dataBaseUpdate) {
        /*
        If this returns true meaning that the dataBase has been updated with new cards then fetch latest 100 cards(to be safe) and compare
        with a list of all the fetched cards and add all cards from the new and sort the list again
        then we perform same check for newCards DataBase.
         */
        mScreenState = ScreenState.Card_Retrieved;
       boolean shouldWeFetch = mSharedPrefs.upDateDatabaseDate(dataBaseUpdate);
       Log.i("added",shouldWeFetch + "");
       if (shouldWeFetch){
           fetchNewCards();
       } else{
           mScreenNavigator.leaveSplashScreen();
       }
    }

    @Override
    public void onUpdateRetrievalFailure(String errorMessage) {
        mScreenState = ScreenState.NETWORK_ERROR;
        mScreenNavigator.leaveSplashScreen();
        Log.i("dERROR",errorMessage);
    }

    @Override // Remove already added cards in the database then add the newest ones
    public void onRetrievalSuccess(List<YugiohCard> newestCards) {
        mScreenState = ScreenState.Card_Retrieved;

        insertIntoNewCardsDataBase(removeAlreadyAdded(newestCards,"New"));
        insertIntoDataBase(removeAlreadyAdded(newestCards,"All"));

    }

    @Override
    public void onRetrievalFailure(String errorMessage) {
        mScreenState = ScreenState.NETWORK_ERROR;
        mScreenNavigator.leaveSplashScreen(); // cant upDate newest Cards  so we leave
        Log.i("dERROR",errorMessage);
    }




    // Method to remove the already retrieved cards
    private List<YugiohCard> removeAlreadyAdded(List<YugiohCard> yugiohCards,String type){

        HashSet<YugiohCard> cardsToAdd = new HashSet<>(yugiohCards.size());
        List<YugiohCard> cards = type.equals("All") ? getAllCardsDataBase():getNewestCardsFromDataBase();

        for (YugiohCard yugiohCard:yugiohCards) {
            if (!cards.contains(yugiohCard)) {
                cardsToAdd.add(yugiohCard); // Not in list so its a new Card we need to add
            }
        }

        Log.i("newAdds",cardsToAdd.toString());
            return new ArrayList<>(cardsToAdd);
        }





    @Override
    public FrameLayout getFrameLayout() {
        return null;
    }

}