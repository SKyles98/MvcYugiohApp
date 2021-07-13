package com.saleef.mvcyugiohapp.Views.DataBaseScreen;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import com.saleef.mvcyugiohapp.Utilities.CardtoCV;
import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;

import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;


import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;

import java.util.List;


public class DataBaseFragment extends BaseFragment implements DataBaseMvc.Listener {



    private ScreenNavigator mScreenNavigator;
    private DialogManager mDialogManager;
    private DataBaseMvc mDataBaseMvc;
    private ContentResolver mContentResolver;
    private SharedPrefs mSharedPrefs;
    private CardtoCV mCardtoCV;
    private static  List<YugiohCard> cards = new ArrayList<>();


    public static DataBaseFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DataBaseFragment fragment = new DataBaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      mContentResolver =  getControllerCompositionRoot().getContentResolver();
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
        mDialogManager = getControllerCompositionRoot().getDialogManger();
        mCardtoCV = new CardtoCV();
        mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mDataBaseMvc = getControllerCompositionRoot().getViewMvcFactory().getDataBaseViewImpl(container);
        catchDialogResult();
        return mDataBaseMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataBaseMvc.registerListener(this);
        if (cards.size()==0) { // We've already gotten cards from database no need to do it again
            fetchFromDataBase();
        } else {
            mDataBaseMvc.bindCards(cards);
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        mDataBaseMvc.unregisterListener(this);
    }



   private void fetchFromDataBase(){
        mDataBaseMvc.showProgressIndication();
       Cursor cursor = mContentResolver.query(YgoInfoFeeder.InfoFeeder.CONTENT_URI,YgoInfoFeeder.InfoFeeder.PROJECTION_ALL
               ,null,null,YgoInfoFeeder.InfoFeeder.CARD_NAME);
       List<YugiohCard> gottenCards = mCardtoCV.convertToCards(cursor);
       cards = gottenCards;
       mSharedPrefs.saveDataBaseSize(cards.size());
       mDataBaseMvc.hideProgressIndication();
       mDataBaseMvc.bindCards(gottenCards);
   }




    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onSortClicked() {
          mDialogManager.showSortOptionsBottomSheetDialog(4);
    }


    private void catchDialogResult() {
        getParentFragmentManager().setFragmentResultListener("JU", this, (requestKey, result) ->
                mDataBaseMvc.bindSortOptions(result.getInt(Constant.SORTKEY),result.getInt(Constant.ORDERKEY)));
    }

}
