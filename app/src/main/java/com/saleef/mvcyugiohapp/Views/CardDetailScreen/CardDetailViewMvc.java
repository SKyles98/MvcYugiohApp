package com.saleef.mvcyugiohapp.Views.CardDetailScreen;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

public interface CardDetailViewMvc extends ObservableMvc<CardDetailViewMvc.Listener> {

    interface Listener{
       void  onAddCardToDeckClicked(YugiohCard yugiohCard);
       void onNavigateUpClicked();
       void onImageClicked(String cardUrl);
    }


       void bindYugiohCard(YugiohCard yugiohCard);
       void showProgressIndication();
       void hideProgressIndication();
}
