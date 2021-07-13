package com.saleef.mvcyugiohapp.Views.DeckInfo;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;

public interface DeckInfoViewMvc extends ObservableMvc<DeckInfoViewMvc.Listener> {

    void nullFabReference();

    interface Listener{
        void onDeleteClicked();
        void onRenameClicked();
        void onPieChartClicked(String deckType);
    }


    void bindDeckInfo(Deck deck);
    void shareFab(FloatingActionButton floatingActionButton);
}
