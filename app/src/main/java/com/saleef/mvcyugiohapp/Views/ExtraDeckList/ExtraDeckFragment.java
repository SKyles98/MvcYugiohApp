package com.saleef.mvcyugiohapp.Views.ExtraDeckList;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;

import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.DeckTypeMvc;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;



public class ExtraDeckFragment extends BaseFragment implements DeckTypeMvc.Listener {
    private ScreenNavigator mScreenNavigator;
    private DeckTypeMvc mExtraDeckMvc;
    private DialogManager mDialogManager;
    private SharedPrefs mSharedPrefs;

    public static ExtraDeckFragment newInstance(Deck extraDeck) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.DECKTYPEKEY, extraDeck);
        ExtraDeckFragment fragment = new ExtraDeckFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        mExtraDeckMvc.registerListener(this);
        mExtraDeckMvc.bindCards(getExtraDeck().getExtraDeck());
        mExtraDeckMvc.showProgressIndication();
        mExtraDeckMvc.hideProgressIndication();
    }

    @Override
    public void onStop() {
        super.onStop();
        mExtraDeckMvc.unregisterListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume","resumed");

    }

    private Deck getExtraDeck(){
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

        mExtraDeckMvc = getControllerCompositionRoot().getViewMvcFactory().getExtraDeckViewImpl(container);
        catchDialogResult();
        return mExtraDeckMvc.getRootView();
    }



    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onDeleteClicked() {
        Toast.makeText(getContext(), "Swipe to Delete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSortClicked() {
        mDialogManager.showSortOptionsBottomSheetDialog(2);
    }

    private void catchDialogResult() {
        getParentFragmentManager().setFragmentResultListener("Ni", this,
                (requestKey, result) -> mExtraDeckMvc.bindSortOptions(result.getInt(Constant.SORTKEY),result.getInt(Constant.ORDERKEY)));
    }


    @Override
    public void onCardDeleted(String cardName) {
        mSharedPrefs.deleteCard(getExtraDeck().getDeckName(),cardName,"Extra");
    }
}
