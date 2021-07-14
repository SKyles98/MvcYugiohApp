package com.saleef.mvcyugiohapp.Views.BanListScreen;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Utilities.CardtoCV;
import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;
import com.saleef.mvcyugiohapp.UseCases.FetchBanlistCards;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BanListFragment extends BaseFragment implements BanlistMvc.Listener, FetchBanlistCards.Listener {


    private ScreenNavigator mScreenNavigator;
    private BanlistMvc mBanlistMvc;
    private FetchBanlistCards mFetchBanlistCards;
    private CardtoCV mCardtoCV;
    private ContentResolver mContentResolver;
    private DialogManager mDialogManager;
   private String currFormat = "tcg";

    private enum ScreenState { // Finite state machine to moniter state of user screen
        IDLE, FETCHING_Cards, Card_Retrieved, NETWORK_ERROR
    }
    private ScreenState mScreenState = ScreenState.IDLE;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
       mFetchBanlistCards = getControllerCompositionRoot().getFetchBanListCardsUseCase();
       mCardtoCV = new CardtoCV();
       mContentResolver = getControllerCompositionRoot().getContentResolver();
       mDialogManager = getControllerCompositionRoot().getDialogManger();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBanlistMvc = getControllerCompositionRoot().getViewMvcFactory().getBanListViewImpl(container);
        catchErrorDialogResult();
        return mBanlistMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanlistMvc.registerListener(this);
        mFetchBanlistCards.registerListener(this);
        if (currFormat.equals("tcg") && getTcgCards()!=null && getTcgCards().size()==0 && mScreenState!=ScreenState.NETWORK_ERROR){
            fetchBannedCards(currFormat);
        } else if (currFormat.equals("ocg") && getOcgCards()!=null && getOcgCards().size()==0 && mScreenState!=ScreenState.NETWORK_ERROR){
             fetchBannedCards(currFormat);
        } else if (currFormat.equals("tcg") && Objects.requireNonNull(getTcgCards()).size()>0){
            mBanlistMvc.bindBanlistCards(getTcgCards());
        } else {
            mBanlistMvc.bindBanlistCards(getOcgCards());
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        mBanlistMvc.unregisterListener(this);
        mFetchBanlistCards.unregisterListener(this);
    }

    private void fetchBannedCards(String format){
        mBanlistMvc.showProgressNotification();
        mScreenState = ScreenState.FETCHING_Cards;
        mFetchBanlistCards.fetchBannedCards(format);//Default format to view
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onFormatClicked(String selectedFormat) {
        currFormat = selectedFormat;
        // At this point we wouldve already fetched so bind as normal
        if (currFormat.equals("ocg") && getOcgCards()!=null && getOcgCards().size()==0 && mScreenState!=ScreenState.NETWORK_ERROR){
            Log.i("zormal","1ocgAdd");
            fetchBannedCards(selectedFormat);
        } else if (currFormat.equals("tcg") && getTcgCards()!=null && getTcgCards().size()==0 && mScreenState!=ScreenState.NETWORK_ERROR){
            Log.i("mormal","1ocgAdd");
            fetchBannedCards(selectedFormat);
        } else{
            Log.i("hormal","godaddy");
            mBanlistMvc.bindBanlistCards(getFormatCards());
        }

    }

    @Override
    public void onLimitedClicked() {
        List<YugiohCard> limitedCards = new ArrayList<>();
          for (YugiohCard yugiohCard:getFormatCards()){
              if (currFormat.equals("tcg")){ // Adds Limited cards to be viewed
                  if (yugiohCard.getTcgBanlistInfo().equals("Limited")){
                      limitedCards.add(yugiohCard);
                  }
              } else if (yugiohCard.getOcgBanlistInfo().equals("Limited")){
                  limitedCards.add(yugiohCard);
              }
          }
          mBanlistMvc.bindBanlistCards(limitedCards);
    }

    @Override
    public void onSemiLimitedClicked() {
        List<YugiohCard> limitedCards = new ArrayList<>();
        for (YugiohCard yugiohCard:getFormatCards()){
            if (currFormat.equals("tcg")){ // Adds Limited cards to be viewed
                if (yugiohCard.getTcgBanlistInfo().equals("Semi-Limited")){
                    limitedCards.add(yugiohCard);
                }
            } else if (yugiohCard.getOcgBanlistInfo().equals("Semi-Limited")){
                limitedCards.add(yugiohCard);
            }
        }
        mBanlistMvc.bindBanlistCards(limitedCards);
    }

    @Override
    public void onBannedClicked() {
        List<YugiohCard> limitedCards = new ArrayList<>();
        for (YugiohCard yugiohCard:getFormatCards()){
            if (currFormat.equals("tcg")){ // Adds Limited cards to be viewed
                if (yugiohCard.getTcgBanlistInfo().equals("Banned")){
                    limitedCards.add(yugiohCard);
                }
            } else if (yugiohCard.getOcgBanlistInfo().equals("Banned")){
                limitedCards.add(yugiohCard);
            }
        }
        mBanlistMvc.bindBanlistCards(limitedCards);
    }

    @Override
    public void onShowAll() {
        mBanlistMvc.bindBanlistCards(getFormatCards());
    }

    private List<YugiohCard> getFormatCards(){
        switch (currFormat){
            case "tcg":
                return getTcgCards();

            case "ocg":
                return getOcgCards();

        }
        return getTcgCards();
    }
    @Override
    public void onRetrievalSuccess(List<YugiohCard> yugiohCards) {
        mBanlistMvc.hideProgressNotification();
        mScreenState = ScreenState.Card_Retrieved;
        switch (currFormat){
            case "tcg":
                insertIntoTcgDataBase(yugiohCards);
                break;
            case "ocg":
                insertIntoOcgDataBase(yugiohCards);
                break;
        }

    }

    @Override
    public void onRetrievalFailure(String errorMessage) {
         mBanlistMvc.hideProgressNotification();
         mScreenState = ScreenState.NETWORK_ERROR;
         mDialogManager.showErrorDialog("Network Call Failed Retry","network");
    }

    private void insertIntoTcgDataBase(List<YugiohCard> cards){

          ContentValues[] bulkValues = mCardtoCV.convertToBanlistContentValues(cards,currFormat);
        int success = mContentResolver.bulkInsert(YgoInfoFeeder.BanListTcgCards.CONTENT_URI,bulkValues);

        if (success==0){
            mBanlistMvc.bindBanlistCards(cards);
        } else{
            Toast.makeText(getContext(),"Perhaps a network error",Toast.LENGTH_LONG).show();
        }

    }

    private void insertIntoOcgDataBase(List<YugiohCard> cards){

        ContentValues[] bulkValues = mCardtoCV.convertToBanlistContentValues(cards,currFormat);
        int success = mContentResolver.bulkInsert(YgoInfoFeeder.BanListOcgCards.CONTENT_URI,bulkValues);

        if (success==0){
            mBanlistMvc.bindBanlistCards(cards);
        } else{
            Toast.makeText(getContext(),"Perhaps a network error",Toast.LENGTH_LONG).show();
        }

    }

    private List<YugiohCard> getTcgCards(){

        Cursor cursor = mContentResolver.query(YgoInfoFeeder.BanListTcgCards.CONTENT_URI,YgoInfoFeeder.BanListTcgCards.PROJECTION_ALL,
                null,null ,YgoInfoFeeder.BanListTcgCards.CARD_NAME);
        return mCardtoCV.convertBanlistToCards(cursor,currFormat);
    }

    private List<YugiohCard> getOcgCards(){

        Cursor cursor = mContentResolver.query(YgoInfoFeeder.BanListOcgCards.CONTENT_URI,YgoInfoFeeder.BanListOcgCards.PROJECTION_ALL,
                null,null ,YgoInfoFeeder.BanListOcgCards.CARD_NAME);
        return mCardtoCV.convertBanlistToCards(cursor,currFormat);
    }
   private void catchErrorDialogResult(){
        getParentFragmentManager().setFragmentResultListener(Constant.ERRORKEY, this, (requestKey, result) -> fetchBannedCards(currFormat));
   }
}
