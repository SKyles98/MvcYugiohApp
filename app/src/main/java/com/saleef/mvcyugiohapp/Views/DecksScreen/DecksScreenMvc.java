package com.saleef.mvcyugiohapp.Views.DecksScreen;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;

import java.util.List;

public interface DecksScreenMvc extends ObservableMvc<DecksScreenMvc.Listener> {


    interface Listener{
       void onCreateDeckClicked();
       void onDeckClicked(Deck deck);
       void navigateUp();
       void onRenameClicked();
       void onDeleteDeckClicked();
    }


    void bindCreatedDecks(List<Deck> decks);
}
