package com.saleef.mvcyugiohapp.Views.DecksScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.Views.Adapters.DeckScreenAdapter;

import java.util.List;

// Standalone View class that shows list of created decks
public class DecksScreenImpl extends BaseObservableMvc<DecksScreenMvc.Listener> implements DecksScreenMvc, DeckScreenAdapter.Listener {


    private final Toolbar mToolbar;
    private final RecyclerView mRecyclerView;
    private final DeckScreenAdapter mDeckScreenAdapter;

    public DecksScreenImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.decks_screen,viewGroup,false));

        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.decksRecycler);
        mDeckScreenAdapter = new DeckScreenAdapter(this,new ViewMvcFactory(layoutInflater,null,null
                ,null));
        mRecyclerView.setAdapter(mDeckScreenAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initToolBar();
    }

    private void initToolBar(){


        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId()==R.id.aDD){
                for (Listener listener:getListeners()){
                    listener.onCreateDeckClicked();
                }
            } else if (item.getItemId() == R.id.delete){
                for (Listener listener:getListeners()){
                    listener.onDeleteDeckClicked();
                }
            } else if (item.getItemId() == R.id.edit){
                for (Listener listener:getListeners()){
                    listener.onRenameClicked();
                }
            }
            return false;
        });
        mToolbar.setNavigationOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.navigateUp();
            }
        });
    }


    @Override
    public void bindCreatedDecks(List<Deck> decks) {
           mDeckScreenAdapter.bindDecks(decks);
    }



    @Override
    public void onDeckClicked(Deck deck) {
          for (Listener listener:getListeners()){
              listener.onDeckClicked(deck);
          }
    }
}
