package com.saleef.mvcyugiohapp.Views.MainDeckList;


import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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





public class MainDeckFragment extends BaseFragment implements DeckTypeMvc.Listener {


    private ScreenNavigator mScreenNavigator;
    private DeckTypeMvc mDeckMvc;
    private DialogManager mDialogManager;
    private SharedPrefs mSharedPreferences;

    public static MainDeckFragment newInstance(Deck mainDeck) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.DECKTYPEKEY,  mainDeck);
        MainDeckFragment fragment = new MainDeckFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        mDeckMvc.registerListener(this);
        mDeckMvc.bindCards(getMainDeck().getMainDeck());
        mDeckMvc.showProgressIndication();
        mDeckMvc.hideProgressIndication();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDeckMvc.unregisterListener(this);
    }



    private Deck getMainDeck(){
        return getArguments().getParcelable(Constant.DECKTYPEKEY);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mDialogManager = getControllerCompositionRoot().getDialogManger();
            mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
            mSharedPreferences = getControllerCompositionRoot().getSharedPrefs();

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mDeckMvc = getControllerCompositionRoot().getViewMvcFactory().getMainDeckViewImpl(container);
        catchDialogResult();
        return mDeckMvc.getRootView();
    }



    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        mScreenNavigator.navigateToCardDetail(yugiohCard);
    }

    @Override
    public void onDeleteClicked() {
        Toast.makeText(getContext(), "Swipe on the cards to delete them", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSortClicked() {
        mDialogManager.showSortOptionsBottomSheetDialog(1);
    }

    private void catchDialogResult() {
        getParentFragmentManager().setFragmentResultListener("ICHI", this, (requestKey, result) -> {
            // ?? Go through the deck and get all the monsters sort those and then get all the spells and sort those and traps etc...
              mDeckMvc.bindSortOptions(result.getInt(Constant.SORTKEY),result.getInt(Constant.ORDERKEY));

        });
    }

    @Override
    public void onCardDeleted(String cardName) { // Reflect delete changes back to shared Prefs
           mSharedPreferences.deleteCard(getMainDeck().getDeckName(),cardName,"Main");
    }
}
