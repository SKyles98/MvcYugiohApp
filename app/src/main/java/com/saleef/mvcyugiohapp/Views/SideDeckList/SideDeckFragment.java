package com.saleef.mvcyugiohapp.Views.SideDeckList;


import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;


import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.DeckTypeMvc;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;




public class SideDeckFragment extends BaseFragment implements  DeckTypeMvc.Listener {
    private ScreenNavigator mScreenNavigator;
    private DeckTypeMvc mSideDeckMvc;
    private DialogManager mDialogManager;
    private SharedPrefs mSharedPrefs;

    public static SideDeckFragment newInstance(Deck sideDeck) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.DECKTYPEKEY,  sideDeck);
        SideDeckFragment fragment = new SideDeckFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        mSideDeckMvc.registerListener(this);
        mSideDeckMvc.bindCards(getSideDeck().getSideDeck());
        mSideDeckMvc.showProgressIndication();
        mSideDeckMvc.hideProgressIndication();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSideDeckMvc.unregisterListener(this);
    }



    private Deck getSideDeck(){
        return getArguments().getParcelable(Constant.DECKTYPEKEY);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mDialogManager = getControllerCompositionRoot().getDialogManger();
            mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
           mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mSideDeckMvc = getControllerCompositionRoot().getViewMvcFactory().getSideDeckViewImpl(container);
        catchDialogResult();
        return mSideDeckMvc.getRootView();
    }



    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onDeleteClicked() {

    }

    @Override
    public void onSortClicked() {
        mDialogManager.showSortOptionsBottomSheetDialog(3);
    }

    private void catchDialogResult() {
        getParentFragmentManager().setFragmentResultListener("SAN", this, (requestKey, result) -> {
            // ?? Go through the deck and get all the monsters sort those and then get all the spells and sort those and traps etc...
            mSideDeckMvc.bindSortOptions(result.getInt(Constant.SORTKEY), result.getInt(Constant.ORDERKEY));

        });
    }


    @Override
    public void onCardDeleted(String cardName) {
          mSharedPrefs.deleteCard(getSideDeck().getDeckName(),cardName,"Side");
    }
}
