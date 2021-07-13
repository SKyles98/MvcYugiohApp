package com.saleef.mvcyugiohapp.Views.BanListScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.Views.Adapters.YugiohCardRecycler;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;

public class BanListViewImpl extends BaseObservableMvc<BanlistMvc.Listener> implements BanlistMvc, YugiohCardRecycler.Listener {


      private final ProgressBar mProgressBar;
    private final Spinner formatsSpinner;
      private final YugiohCardRecycler mYugiohCardRecycler;
      private final MaterialButton limitedTxt,semiLimitedTxt,allBannedTxt;
      private final ImageView bannedImg;
      private int check = 0;
      public BanListViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
          setRootView(layoutInflater.inflate(R.layout.banlist_screen,viewGroup,false));
          mProgressBar = findViewById(R.id.progress);
          RecyclerView recyclerView = findViewById(R.id.deckRecycler);
          formatsSpinner = findViewById(R.id.formatSpinner);
          limitedTxt = findViewById(R.id.limited);
          semiLimitedTxt = findViewById(R.id.semiLimited);
          allBannedTxt = findViewById(R.id.allCards);
          bannedImg = findViewById(R.id.banned);
          mYugiohCardRecycler = new YugiohCardRecycler(new ViewMvcFactory(layoutInflater,null,null
                  ,null),this);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
          recyclerView.setAdapter(mYugiohCardRecycler);
          initSpinner();
          initBtnListeners();
      }





     private void initSpinner(){
         final String[] formats = {"tcg","ocg"};
         ArrayAdapter<String> formatOptions = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, formats);
         formatsSpinner.setAdapter(formatOptions);

         formatsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (check>0) { // Prevent first time call during screen initialization
                            for (Listener listener : getListeners()) {
                                listener.onFormatClicked((String) formatsSpinner.getSelectedItem());
                            }
                        }
                        check++;
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
     }

     private void initBtnListeners(){
          limitedTxt.setOnClickListener(v -> {
              for (Listener listener:getListeners()){
                  listener.onLimitedClicked();
              }
          });
         semiLimitedTxt.setOnClickListener(v -> {
             for (Listener listener:getListeners()){
                 listener.onSemiLimitedClicked();
             }
         });
         bannedImg.setOnClickListener(v -> {
             for (Listener listener:getListeners()){
                 listener.onBannedClicked();
             }
         });
         allBannedTxt.setOnClickListener(v -> {
             for (Listener listener:getListeners()){
                 listener.onShowAll();
             }
         });
     }

    @Override
    public void showProgressNotification() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressNotification() {
       mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void bindBanlistCards(List<YugiohCard> yugiohCards) {
             mYugiohCardRecycler.bindYugiohCards(yugiohCards);
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
        for (Listener listener:getListeners()){
            listener.onCardClicked(yugiohCard);
        }
    }
}
