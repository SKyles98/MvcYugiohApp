package com.saleef.mvcyugiohapp.Views.Dialogs.AddCardDialog;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;

public interface AddCardViewMvc extends ObservableMvc<AddCardViewMvc.Listener> {


    interface Listener{
        void onSpinnerDeckClicked();
        void onSpinnerDeckTypeClicked();
        void onAddClicked();
        void onCancelClicked();
    }


    void setDeckSpinner();
    void setDeckTypeSpinner();
    void setAmountSpinner();
    Deck getSelectedDeck();
    int getSelectedAmount();
    String getSelectedDeckType();
    void setDecks(List<Deck> decks);
    void setYugiohCard(YugiohCard card);
}
