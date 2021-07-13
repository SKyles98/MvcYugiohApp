package com.saleef.mvcyugiohapp.Views.DataBaseScreen;

import android.text.Editable;
import android.text.TextWatcher;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.Views.Adapters.YugiohCardRecycler;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;


public class

DataBaseViewImpl extends BaseObservableMvc<DataBaseMvc.Listener> implements DataBaseMvc,YugiohCardRecycler.Listener {

    private final ProgressBar mProgressBar;
    private final ImageView filterImg,searchImg,reverseImg;

    private final TextInputEditText searchInput;
    private final RecyclerView mRecyclerView;
    private final YugiohCardRecycler mYugiohCardRecycler;
    private final LinearLayout searchL;

    public DataBaseViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.database_screen,viewGroup,false));
        mProgressBar = findViewById(R.id.progress);
        filterImg = findViewById(R.id.filterCard);
        searchImg = findViewById(R.id.searchCard);
        reverseImg = findViewById(R.id.reverseList);

        searchInput = findViewById(R.id.searchEdit);
        searchL = findViewById(R.id.searchLayout);
        mRecyclerView = findViewById(R.id.deckRecycler);
        mYugiohCardRecycler = new YugiohCardRecycler(new ViewMvcFactory(layoutInflater,null,null,null),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mYugiohCardRecycler);
        initFilter();
        initSearch();
        scrollToTop();
    }



   private void initFilter(){
        filterImg.setOnClickListener(v->{
            for (Listener listener:getListeners()){
                listener.onSortClicked();
            }
        });
   }

   private void scrollToTop(){
        reverseImg.setOnClickListener(v -> {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                    .getLayoutManager();
            layoutManager.scrollToPositionWithOffset(0, 0);
        });
   }
   private void initSearch(){

      searchImg.setOnClickListener(v -> {
              if (searchL.getVisibility()!=View.VISIBLE) {
                  searchL.setVisibility(View.VISIBLE);
              } else{
                  searchL.setVisibility(View.GONE);
              }
      });
       searchInput.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               //Log.i("tTracker",searchInput.getText().toString());
               mYugiohCardRecycler.getFilter().filter(searchInput.getText().toString());
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
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
             mYugiohCardRecycler.bindYugiohCards(yugiohCards);
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        for (Listener listener:getListeners()){
            listener.onCardClicked(yugiohCard);
        }
    }

    @Override
    public void bindSortOptions(int sortKey,int orderKey) {
          mYugiohCardRecycler.bindSortOptions(sortKey,orderKey);
    }
}
