package com.saleef.mvcyugiohapp.Views;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;


// Interface for managing the decktypes (MainDeck,SideDeck,ExtraDeck)
public interface DeckTypeMvc extends ObservableMvc<DeckTypeMvc.Listener> {

    interface Listener{
        void onCardClicked(YugiohCard yugiohCard);
        void onDeleteClicked();
        void onSortClicked();
        void onCardDeleted(String cardName);
    }


    void showProgressIndication();
    void hideProgressIndication();
    void bindCards(List<YugiohCard> yugiohCards);
    void bindSortOptions(int sortkey,int orderKey);
}
