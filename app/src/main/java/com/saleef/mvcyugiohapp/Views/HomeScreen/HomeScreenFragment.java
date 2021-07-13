package com.saleef.mvcyugiohapp.Views.HomeScreen;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import com.saleef.mvcyugiohapp.Utilities.CardtoCV;
import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;
import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.UseCases.FetchNewCardsUseCase;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;



import java.util.List;
import java.util.Objects;

//TODO fetch new cards and store them in table
public class HomeScreenFragment extends BaseFragment implements HomeScreenMvc.Listener, FetchNewCardsUseCase.Listener {





    public HomeScreenFragment() {
        // Required empty public constructor
    }



    private enum ScreenState { // Finite state machine to moniter state of user screen
        IDLE, FETCHING_Cards, Card_Retrieved, NETWORK_ERROR
    }
    private ScreenState mScreenState = ScreenState.IDLE;
  private HomeScreenMvc mHomeScreenMvc;
    private  FetchNewCardsUseCase mFetchNewCardsUseCase;
    private ScreenNavigator mScreenNavigator;
    private DialogManager mDialogManager;
    private SharedPrefs mSharedPrefs;
    private CardtoCV mCardtoCV;
    private ContentResolver mContentResolver;
    public static HomeScreenFragment newInstance() {
        HomeScreenFragment fragment = new HomeScreenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             mFetchNewCardsUseCase = getControllerCompositionRoot().getFetchNewCardsUseCase();
             mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
            mDialogManager = getControllerCompositionRoot().getDialogManger();
             mCardtoCV = new CardtoCV();
             mContentResolver = getControllerCompositionRoot().getContentResolver();
             mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHomeScreenMvc = getControllerCompositionRoot().getViewMvcFactory().getHomeScreenViewImpl(container);
        catchErrorResult();
        return mHomeScreenMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchNewCardsUseCase.registerListener(this);
        mHomeScreenMvc.registerListener(this);
        // If we already performed the network call while during app lifetime dont do it again and just singleton list
        if ((getNewestCardsFromDataBase() == null && mScreenState != ScreenState.NETWORK_ERROR) ||
                (Objects.requireNonNull(getNewestCardsFromDataBase()).size()==0 && mScreenState != ScreenState.NETWORK_ERROR)) {
            fetchNewCards();
        } else {
            mHomeScreenMvc.bindLatestYugiohCards(getNewestCardsFromDataBase());
            mHomeScreenMvc.bindDateInfo(mSharedPrefs.getUpdateDate(),mSharedPrefs.getDataBaseSize());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchNewCardsUseCase.unregisterListener(this);
        mHomeScreenMvc.unregisterListener(this);
    }

    private void fetchNewCards(){
        mHomeScreenMvc.showProgressNotification();
        mScreenState = ScreenState.FETCHING_Cards;
        mFetchNewCardsUseCase.fetchNewCards();
    }
    @Override
    public void onRetrievalSuccess(List<YugiohCard> newestCards) {

          mHomeScreenMvc.hideProgressNotification();
          mScreenState = ScreenState.Card_Retrieved;
          insertIntoDataBase(newestCards);
    }

    @Override
    public void onRetrievalFailure(String errorMessage) {
        mHomeScreenMvc.hideProgressNotification();
        mScreenState = ScreenState.NETWORK_ERROR;
        mDialogManager.showErrorDialog("Network Call Failed Retry?","network");
         Log.i("nError",errorMessage);
    }

    @Override
    public void onDataBaseClicked() {
            mScreenNavigator.navigateToDataBase();
    }

    @Override
    public void onDeckBuilderClicked() {
         mScreenNavigator.navigateToDecks();
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
          mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onCalculatorClicked() {
        mScreenNavigator.navigateToDuelCalculator();
    }


    private void insertIntoDataBase(List<YugiohCard> yugiohCards){


        ContentValues[] bulkValues = mCardtoCV.convertToContentValues(yugiohCards);
        int i =  mContentResolver.bulkInsert(YgoInfoFeeder.NewCards.CONTENT_URI, bulkValues);

        // Successful insertion
        if (i==0){
            mHomeScreenMvc.bindLatestYugiohCards(yugiohCards);
            mHomeScreenMvc.bindDateInfo(mSharedPrefs.getUpdateDate(),mSharedPrefs.getDataBaseSize());
        } else {
            Log.i("error","Cards wasnt added or something");
        }
    }

    private List<YugiohCard> getNewestCardsFromDataBase(){
        Cursor cursor = mContentResolver.query
                (YgoInfoFeeder.NewCards.CONTENT_URI,YgoInfoFeeder.NewCards.PROJECTION_ALL,null,null,null);
        return mCardtoCV.convertToCards(cursor);
    }

   private void catchErrorResult(){
       getParentFragmentManager().setFragmentResultListener(Constant.ERRORKEY, this, (requestKey, result) -> fetchNewCards());
   }
}