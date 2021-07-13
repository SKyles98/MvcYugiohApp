package com.saleef.mvcyugiohapp.Views.DecksScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;



public class DecksScreenFragment extends BaseFragment implements DecksScreenMvc.Listener {



   private DialogManager mDialogManager;
   private ScreenNavigator mScreenNavigator;
   private DecksScreenMvc mDecksScreenMvc;
   private SharedPrefs mSharedPrefs;



    public DecksScreenFragment() {
        // Required empty public constructor
    }



    public static DecksScreenFragment newInstance() {
        DecksScreenFragment fragment = new DecksScreenFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
            mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();
            mDialogManager = getControllerCompositionRoot().getDialogManger();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mDecksScreenMvc.registerListener(this);
        bindUserDecks();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDecksScreenMvc.unregisterListener(this);
    }

    private void bindUserDecks(){
        mDecksScreenMvc.bindCreatedDecks(mSharedPrefs.getAllDecks());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDecksScreenMvc = getControllerCompositionRoot().getViewMvcFactory().getDeckScreenImpl(container);
        catchAddDialogResult();
        catchDeleteDialogResult();
        catchRenameDialogResult();
        return mDecksScreenMvc.getRootView();
    }

    @Override
    public void onCreateDeckClicked() {
        mDialogManager.showCreateDeckDialog();
        mDecksScreenMvc.bindCreatedDecks(mSharedPrefs.getAllDecks());
    }

    @Override
    public void navigateUp() {
        mScreenNavigator.navigateUp();
    }

    @Override
    public void onRenameClicked() {
        if (mSharedPrefs.getAllDecks().size()>0) {
            mDialogManager.showRenameDialog(mSharedPrefs.getAllDecks());
        } else{ mDialogManager.showErrorDialog("Need to create a deck First","error");}
    }

    @Override
    public void onDeleteDeckClicked() {
          if (mSharedPrefs.getAllDecks().size()>0) {
              mDialogManager.showDeleteDeckDialog(mSharedPrefs.getAllDecks());
          } else { mDialogManager.showErrorDialog("Need to create a deck First","error"); }
    }

    @Override
    public void onDeckClicked(Deck deck) {
        //Navigate to deck details screen
        mScreenNavigator.navigateToDeckDetails(deck);
    }

    private void catchAddDialogResult(){
        getParentFragmentManager().setFragmentResultListener(Constant.CDDialog, this, (requestKey, result) -> {
            if (requestKey.equals(Constant.CDDialog)){ // Means name already exist show a error dialog
                if (mSharedPrefs.findEquivalentKey(result.getString("deckName"))){
                    mDialogManager.showErrorDialog(Constant.NAMEERROR,"nameError");
                } else {
                    mSharedPrefs.createDeck(result.getString("deckName", ""));
                    mDecksScreenMvc.bindCreatedDecks(mSharedPrefs.getAllDecks());
                }
            }
        });
    }

    private void catchDeleteDialogResult(){
        getParentFragmentManager().setFragmentResultListener(Constant.DELETEKEY, this, (requestKey, result) -> {
            mSharedPrefs.deleteDeck(result.getString(Constant.DKEY));
            bindUserDecks();
        });
    }

    private void catchRenameDialogResult(){
          getParentFragmentManager().setFragmentResultListener(Constant.RENAMEKEY, this, (requestKey, result) -> {
              String old = result.getString("Old");
              String newN = result.getString("New");
                  if (mSharedPrefs.findEquivalentKey(newN)){
                      mDialogManager.showErrorDialog(Constant.NAMEERROR,"nameError");
                  } else {
                      mSharedPrefs.renameDeck(old, newN);
                      mDecksScreenMvc.bindCreatedDecks(mSharedPrefs.getAllDecks());
                  }
          });
    }





}