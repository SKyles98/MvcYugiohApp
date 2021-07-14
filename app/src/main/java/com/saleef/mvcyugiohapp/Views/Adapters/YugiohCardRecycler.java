package com.saleef.mvcyugiohapp.Views.Adapters;


import android.util.Log;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.saleef.mvcyugiohapp.Utilities.DeckSorter;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.Views.ViewHolders.CardViewHolderMvc;
import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class YugiohCardRecycler extends RecyclerView.Adapter<YugiohCardRecycler.YugiohViewHolder> implements CardViewHolderMvc.Listener, Filterable {




    public interface Listener{
       void onCardClicked(YugiohCard yugiohCard);
    }


    private List<YugiohCard> originalList;
    private List<YugiohCard> mYugiohCards;
    private int count;
    private final ViewMvcFactory mViewMvcFactory;
    private final Listener mListener;
    private final DeckSorter mDeckSorter;
    public YugiohCardRecycler(ViewMvcFactory viewMvcFactory,Listener listener){
        mViewMvcFactory = viewMvcFactory;
        mYugiohCards = new ArrayList<>();
        originalList = new ArrayList<>();
        count = 0;
        mListener = listener;
        mDeckSorter = new DeckSorter();
    }

    @NonNull
    @Override
    public YugiohViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       CardViewHolderMvc cardViewHolderMvc = mViewMvcFactory.getCardViewHolderImpl(parent);
       cardViewHolderMvc.registerListener(this);
        return new YugiohViewHolder(cardViewHolderMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull YugiohViewHolder holder, int position) {
               holder.mCardViewHolder.bindYugiohCards(mYugiohCards.get(position));
    }



    public void bindYugiohCards(List<YugiohCard> yugiohCards){
        if (count<1) { // only keep the original once
            originalList = yugiohCards;
        }
        mYugiohCards = yugiohCards;
        // calls bindView holder again to rebind
        count++;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return mYugiohCards.size();
    }

    @Override // When user clicks on card (navigate to detail screen)
    public void onCardClicked(YugiohCard card) {
            mListener.onCardClicked(card);
    }

    public void bindSortOptions(int sortKey,int orderKey) {
        boolean order = orderKey == R.id.ascending;
        switch (sortKey){
            case R.id.alphabet:
               mYugiohCards =  mDeckSorter.sortByName(mYugiohCards,order);
               notifyDataSetChanged();
                break;
            case R.id.attack:
                mYugiohCards =  mDeckSorter.sortByAttack(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            case R.id.defense:
                mYugiohCards =  mDeckSorter.sortByDefense(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            case R.id.level:
                mYugiohCards =  mDeckSorter.sortByLevel(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            case R.id.attribute:
                mYugiohCards =  mDeckSorter.sortByAttribute(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            case R.id.race:
                mYugiohCards = mDeckSorter.sortByRace(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            case R.id.cardType:
                mYugiohCards = mDeckSorter.sortByType(mYugiohCards,order);
                notifyDataSetChanged();
                break;
            default: // No sort option clicked just reverse
                mYugiohCards = mDeckSorter.justOrder(mYugiohCards);
                notifyDataSetChanged();
        }
    }




    public void removeCard(int position){
            mYugiohCards.remove(position);
            notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.i("cTracker",constraint.toString());
                List<YugiohCard> filterResults;
                if (constraint.length() == 0){ // User text is empty so return our original arraylist
                    filterResults = originalList;
                } else{
                    String constraintString = constraint.toString().toLowerCase();
                    filterResults =  getFilteredResults(constraintString);
                }

                FilterResults results = new FilterResults();
                results.values = filterResults;
                return  results;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                bindYugiohCards((List<YugiohCard>) results.values);

            }
        };
    }
    // Adds everything matching our conditions to the new arraylist
    protected List<YugiohCard> getFilteredResults(String filterString){


        // Hash set is used over arrayList due to maintenance of insertion order
        // When arrayList adds it shifts all the elements over by 1 takes way longer
        HashSet<YugiohCard> set = new HashSet<>();
        for (YugiohCard yugiohCard:originalList){
            if (yugiohCard.getName().toLowerCase().contains(filterString) || yugiohCard.getDesc().toLowerCase().contains(filterString)){
                set.add(yugiohCard);
            }
        }
        return new ArrayList<>(set);
    }




    static class YugiohViewHolder extends RecyclerView.ViewHolder{
         private final CardViewHolderMvc mCardViewHolder;
        public YugiohViewHolder(@NonNull CardViewHolderMvc cardViewHolder) {
            super(cardViewHolder.getRootView());
            mCardViewHolder = cardViewHolder;
        }
    }
}
