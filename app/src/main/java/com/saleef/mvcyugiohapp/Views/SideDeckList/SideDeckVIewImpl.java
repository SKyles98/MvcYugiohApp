package com.saleef.mvcyugiohapp.Views.SideDeckList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.Views.Adapters.YugiohCardRecycler;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.DeckTypeMvc;


import java.util.ArrayList;
import java.util.List;

public class SideDeckVIewImpl extends BaseObservableMvc<DeckTypeMvc.Listener> implements DeckTypeMvc,YugiohCardRecycler.Listener{

    private final ProgressBar mProgressBar;
    private final YugiohCardRecycler mYugiohCardRecycler;
    private final ImageView deleteImg,filterImg;
    private List<YugiohCard> currCards;
    public SideDeckVIewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.deck_detail_deck,viewGroup,false));
        currCards = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress);
        filterImg = findViewById(R.id.filterCard);
        deleteImg = findViewById(R.id.deleteCard);
        RecyclerView recyclerView = findViewById(R.id.deckRecycler);
        new ItemTouchHelper(mSimpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mYugiohCardRecycler = new YugiohCardRecycler(
                new ViewMvcFactory(layoutInflater,null,null,null),this);
        recyclerView.setAdapter(mYugiohCardRecycler);
        initImg();
    }







    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }





    @Override
    public void bindCards(List<YugiohCard> yugiohCards) {
        currCards = yugiohCards;
        mYugiohCardRecycler.bindYugiohCards(yugiohCards);
    }

    @Override
    public void bindSortOptions(int sortkey, int orderKey) {
        mYugiohCardRecycler.bindSortOptions(sortkey,orderKey);
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        for (Listener listener:getListeners()){
            listener.onCardClicked(yugiohCard);
        }
    }




    private void initImg(){
        deleteImg.setOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onDeleteClicked();
            }
        });
        filterImg.setOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onSortClicked();
            }
        });
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            String deletedCardName = currCards.get(position).getName();

            for (Listener listener:getListeners()){
                listener.onCardDeleted(deletedCardName);
            }

            mYugiohCardRecycler.removeCard(position);

        }
    };
}
