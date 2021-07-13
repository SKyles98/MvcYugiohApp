package com.saleef.mvcyugiohapp.Views.Dialogs.AddCardDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;

import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;


import java.util.ArrayList;
import java.util.List;

public class AddCardToDeckDialog extends BaseDialog implements AddCardViewMvc.Listener {



    private AddCardViewMvc mAddCardViewMvc;
   //On call to this dialog a need to display all of decks in a deck spinner
   // Before dialog is loaded I populate Deck section with all created decks.
    // This is done through the shared preferences getalldecks call
    //2. Populate the amount field with copies of cards to add to deck
    // To know the amount I need to perform a check on the currently selected deck in the spinner
    // and check for copies of the card in the main extra or side based on the type of card type
    // 3. For the add to section display spinner strings based off of card type Either Main and Side or Extra and Side
    // should be displayed
       public static AddCardToDeckDialog newAddToDeckDialog(List<Deck> decksList, YugiohCard yugiohCard){
                AddCardToDeckDialog addCardToDeckDialog = new AddCardToDeckDialog();
                Bundle args = new Bundle(2);
                args.putParcelableArrayList(Constant.DKEY, (ArrayList<? extends Parcelable>) decksList);
                args.putParcelable(Constant.CKEY,yugiohCard);
                addCardToDeckDialog.setArguments(args);
                return addCardToDeckDialog;
       }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
           if (getArguments()==null){
               throw new IllegalArgumentException("Arguments can be null");
           }
           mAddCardViewMvc = getControllerCompositionRoot().getViewMvcFactory().getAddCardViewImpl(null);
          mAddCardViewMvc.setDecks(getArguments().getParcelableArrayList(Constant.DKEY));
          mAddCardViewMvc.setYugiohCard(getArguments().getParcelable(Constant.CKEY));
          mAddCardViewMvc.setDeckSpinner();
          mAddCardViewMvc.setDeckTypeSpinner();
          mAddCardViewMvc.setAmountSpinner();

           Dialog dialog = new Dialog(requireContext());

          dialog.setContentView(mAddCardViewMvc.getRootView());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAddCardViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAddCardViewMvc.unregisterListener(this);
    }

    @Override // Recheck the amount of addable cards for new deck
    public void onSpinnerDeckClicked() {
           mAddCardViewMvc.setAmountSpinner();
    }



    @Override // Same logic as above  except for card type;
    public void onSpinnerDeckTypeClicked() {
             mAddCardViewMvc.setAmountSpinner();
    }

    @Override
    public void onAddClicked() {
        // Put data from dialog in Bundle for fragement result to take
        setResults();
           dismiss();
    }

    @Override
    public void onCancelClicked() {
           dismiss();
    }

    private void setResults(){
           Bundle bundle = new Bundle(4);
           bundle.putParcelable(Constant.DKEY,mAddCardViewMvc.getSelectedDeck());
           bundle.putParcelable(Constant.CKEY,getArguments().getParcelable(Constant.CKEY));
           bundle.putInt(Constant.AMOUNTKEY,mAddCardViewMvc.getSelectedAmount());
           bundle.putString(Constant.DECKTYPEKEY,mAddCardViewMvc.getSelectedDeckType());
           getParentFragmentManager().setFragmentResult(Constant.ADDialog,bundle);
    }
}
