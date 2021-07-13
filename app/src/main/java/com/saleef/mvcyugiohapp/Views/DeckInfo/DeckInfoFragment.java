package com.saleef.mvcyugiohapp.Views.DeckInfo;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;


public class DeckInfoFragment extends BaseFragment implements DeckInfoViewMvc.Listener {




    private DeckInfoViewMvc mDeckInfoViewMvc;
    private ScreenNavigator mScreenNavigator;
    private DialogManager mDialogManager;
    public DeckInfoFragment() {
        // Required empty public constructor
    }


    public static DeckInfoFragment newInstance(Deck deck) {
        DeckInfoFragment fragment = new DeckInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.DKEY,deck);
        fragment.setArguments(args);
        return fragment;
    }

    private Deck getDeck(){
        return getArguments().getParcelable(Constant.DKEY);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
             mDialogManager = getControllerCompositionRoot().getDialogManger();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDeckInfoViewMvc = getControllerCompositionRoot().getViewMvcFactory().getDeckInfoViewImpl(container);
        return mDeckInfoViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDeckInfoViewMvc.registerListener(this);
        mDeckInfoViewMvc.bindDeckInfo(getDeck());
    }

    @Override
    public void onStop() {
        super.onStop();
        mDeckInfoViewMvc.unregisterListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       mDeckInfoViewMvc.nullFabReference();
    }

    @Override
    public void onDeleteClicked() {

    }

    @Override
    public void onRenameClicked() {

    }

    @Override
    public void onPieChartClicked(String deckType) {
        mDialogManager.showDeckCompositionDialog(deckType,getDeck());
    }

    public void shareFab(FloatingActionButton floatingActionButton){

        mDeckInfoViewMvc.shareFab(floatingActionButton);
    }
}