package com.saleef.mvcyugiohapp.Views.ViewHolders;

import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.TextView;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;

public class DeckScreenViewHolderImpl extends BaseObservableMvc<DeckScreenViewHolderMvc.Listener> implements DeckScreenViewHolderMvc {


    private final TextView mDeckNameTxt,mDeckCountTxt;
    private Deck mDeck;
    public DeckScreenViewHolderImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.decks_viewholder,viewGroup,false));
        mDeckNameTxt = findViewById(R.id.deckName);
        mDeckCountTxt = findViewById(R.id.deckCountTxt);
        getRootView().setOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onDeckClicked(mDeck);
            }
        });
    }



    @Override
    public void bindDecks(Deck deck) {
        mDeck = deck;
        mDeckNameTxt.setText(deck.getDeckName());
        String deckCount = deck.getMainDeck().size() + "/" + "60";
        mDeckCountTxt.setText(deckCount);
    }
}
