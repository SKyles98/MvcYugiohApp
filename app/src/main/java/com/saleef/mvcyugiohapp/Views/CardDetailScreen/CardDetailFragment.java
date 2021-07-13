package com.saleef.mvcyugiohapp.Views.CardDetailScreen;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;


public class CardDetailFragment extends BaseFragment implements CardDetailViewMvc.Listener {


     public static final String YUGIOOH = "YUGIOOOOOOH";


    public CardDetailFragment() {
        // Required empty public constructor
    }

    private CardDetailViewMvc mCardDetailViewMvc;
    private ScreenNavigator mScreenNavigator;
    private DialogManager mDialogManager;
    private SharedPrefs mSharedPrefs;




    public static CardDetailFragment newInstance(YugiohCard yugiohCard) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(YUGIOOH,yugiohCard);
        fragment.setArguments(args);

        return fragment;
    }

    private YugiohCard getClickedCard(){
        return getArguments().getParcelable(YUGIOOH);
    }



    @Override
    public void onStart() {
        super.onStart();
        mCardDetailViewMvc.registerListener(this);
        mCardDetailViewMvc.bindYugiohCard(getClickedCard());
    }

    @Override
    public void onStop() {
        super.onStop();
        mCardDetailViewMvc.unregisterListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
           mDialogManager = getControllerCompositionRoot().getDialogManger();
           mSharedPrefs = getControllerCompositionRoot().getSharedPrefs();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getParentFragmentManager().setFragmentResultListener(Constant.ADDialog, this, (requestKey, result) -> {
             Deck deck = result.getParcelable(Constant.DKEY);
             YugiohCard yugiohCard = result.getParcelable(Constant.CKEY);
             int amount = result.getInt(Constant.AMOUNTKEY);
             String type = result.getString(Constant.DECKTYPEKEY);
             mSharedPrefs.addCardtoDeck(type,deck.getDeckName(),amount,yugiohCard);
            Toast.makeText(getContext(),yugiohCard.getName() + " was added",Toast.LENGTH_LONG).show();
        });
        mCardDetailViewMvc = getControllerCompositionRoot().getViewMvcFactory().getCardDetailViewImpl(container);
        return mCardDetailViewMvc.getRootView();
    }

    @Override
    public void onAddCardToDeckClicked(YugiohCard yugiohCard) {
           //Display a dialog with all of the user created decks
           // Store created decks in a sharedPreferences
        if (mSharedPrefs.getAllDecks().size() == 0){
            mDialogManager.showErrorDialog("Need to create a deck first","NO Decks Found");
        } else {
            mDialogManager.showAddToDeckDialog(mSharedPrefs.getAllDecks(), yugiohCard);
        }
    }



    @Override
    public void onNavigateUpClicked() {
        mScreenNavigator.navigateUp();
    }

    @Override
    public void onImageClicked(String cardUrl) {
        mScreenNavigator.navigateToZoomedImage(cardUrl);
    }


}