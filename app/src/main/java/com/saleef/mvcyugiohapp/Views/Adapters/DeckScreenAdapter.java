package com.saleef.mvcyugiohapp.Views.Adapters;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.ViewHolders.DeckScreenViewHolderImpl;
import com.saleef.mvcyugiohapp.Views.ViewHolders.DeckScreenViewHolderMvc;

import java.util.ArrayList;
import java.util.List;

public class DeckScreenAdapter extends RecyclerView.Adapter<DeckScreenAdapter.DeckScreenViewHolder> implements DeckScreenViewHolderMvc.Listener {




    public interface Listener{
        void onDeckClicked(Deck deck);
    }

    private List<Deck> mDecks;
    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    public DeckScreenAdapter(Listener listener, ViewMvcFactory viewMvcFactory){
        mDecks = new ArrayList<>();
        mListener = listener;
        mViewMvcFactory = viewMvcFactory;

    }
    @NonNull
    @Override
    public DeckScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       DeckScreenViewHolderImpl mDeckScreenViewHolderMvc = mViewMvcFactory.getDeckScreenViewHolderImpl(parent);
        mDeckScreenViewHolderMvc.registerListener(this);
        return new DeckScreenViewHolder(mDeckScreenViewHolderMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckScreenViewHolder holder, int position) {
           holder.mDeckScreenViewHolder.bindDecks(mDecks.get(position));
    }

    @Override
    public int getItemCount() {
        return mDecks.size();
    }

    public void bindDecks(List<Deck> decks){
        mDecks = new ArrayList<>(decks);
        notifyDataSetChanged();
    }


    @Override
    public void onDeckClicked(Deck deck) {
         mListener.onDeckClicked(deck);
    }




    static class DeckScreenViewHolder extends RecyclerView.ViewHolder{
         private final DeckScreenViewHolderImpl mDeckScreenViewHolder;
        public DeckScreenViewHolder(@NonNull DeckScreenViewHolderImpl deckScreenViewHolder) {
            super(deckScreenViewHolder.getRootView());
            mDeckScreenViewHolder = deckScreenViewHolder;
        }
    }
}
