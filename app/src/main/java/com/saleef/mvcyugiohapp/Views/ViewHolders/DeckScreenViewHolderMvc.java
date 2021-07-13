package com.saleef.mvcyugiohapp.Views.ViewHolders;


import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;

public interface DeckScreenViewHolderMvc extends ObservableMvc<DeckScreenViewHolderMvc.Listener> {
    interface Listener{
       void onDeckClicked(Deck deck);
    }

    void bindDecks(Deck deck);

}
