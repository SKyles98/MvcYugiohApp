package com.saleef.mvcyugiohapp.Views.CardDetailScreen;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;


import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;


public class CardDetailViewImpl extends BaseObservableMvc<CardDetailViewMvc.Listener> implements CardDetailViewMvc {


    private final ProgressBar mProgressBar;
    private final TextView cardAttributeRaceTxt,cardStatsTxt,cardEffectTxt,cardLevel,cardName;
    private final ImageView starsImg;
    private final Toolbar mToolbar;
    private final ImageView cardPhotoView;

   private final RequestOptions mRequestOptions;
   private YugiohCard mYugiohCard;

    public CardDetailViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.card_detail_screen,viewGroup,false));
        mProgressBar = findViewById(R.id.progress);
        cardPhotoView = findViewById(R.id.photoView);
        starsImg = findViewById(R.id.starLevel);
        cardLevel = findViewById(R.id.typeIndicator);
        cardAttributeRaceTxt = findViewById(R.id.attributeRaceTxt);
        cardStatsTxt = findViewById(R.id.statsTxt);
        cardEffectTxt = findViewById(R.id.effectTxt);
        cardName = findViewById(R.id.monsterName);
        mToolbar = findViewById(R.id.toolbar);

        mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.monstercarddefault).
                error(R.drawable.monstercarddefault);
        initToolbar();
        // Scuffed way to zoom in and out of image
        cardPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener:getListeners()){
                    listener.onImageClicked(mYugiohCard.getCardImageUrl());
                }
            }
        });

    }

    private void initToolbar(){
        // hide menuItems
        mToolbar.getMenu().getItem(1).setVisible(false);
        mToolbar.getMenu().getItem(2).setVisible(false);
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.aDD) {
                for (Listener listener : getListeners()) {
                    listener.onAddCardToDeckClicked(mYugiohCard);
                }
            }
            return false;
        });
        mToolbar.setNavigationOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onNavigateUpClicked();
            }
        });
    }
    @Override
    public void bindYugiohCard(YugiohCard yugiohCard) {
        mYugiohCard = yugiohCard;
        mToolbar.setTitle(mYugiohCard.getName());
           loadImage();
           setUpLogic();
    }

    private void setUpLogic(){
        String type = mYugiohCard.getType();
        cardEffectTxt.setText(mYugiohCard.getDesc());
        cardName.setText(mYugiohCard.getName());
        if (type.contains("Monster")){
            starsImg.setVisibility(View.VISIBLE);
            cardLevel.setVisibility(View.VISIBLE);
            String attribute = mYugiohCard.getAttribute() + "/" + mYugiohCard.getRace();
            cardAttributeRaceTxt.setText(attribute);
            if(type.contains("Link")){
                starsImg.setVisibility(View.GONE);
                String linkRating = mYugiohCard.getLinkRating() + "";
                cardLevel.setText(linkRating);
                String linkStat = "ATK " + mYugiohCard.getAtk() + "/";
                cardStatsTxt.setText(linkStat);
            } else{
                String monsterStat = "ATK " + mYugiohCard.getAtk() + " DEF " + mYugiohCard.getDef();
                String level = mYugiohCard.getLevel() + "";
                cardStatsTxt.setText(monsterStat);
                cardLevel.setText(level);
            }
        } else {
            cardLevel.setVisibility(View.GONE);
            starsImg.setVisibility(View.GONE);
            cardStatsTxt.setVisibility(View.GONE);
            String stat = type + "/" + mYugiohCard.getRace();
            cardAttributeRaceTxt.setText(stat);
        }
    }
    private void loadImage(){

       Glide.with(getContext()).
                load(mYugiohCard.getCardImageUrl()) // loads image based off a url
               .apply(mRequestOptions). // apply image based off options
                into(cardPhotoView);
    }

    @Override
    public void showProgressIndication() {
         mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
          mProgressBar.setVisibility(View.GONE);
    }


}
