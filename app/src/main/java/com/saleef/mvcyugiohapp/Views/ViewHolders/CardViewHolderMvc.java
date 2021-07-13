package com.saleef.mvcyugiohapp.Views.ViewHolders;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

public interface CardViewHolderMvc extends ObservableMvc<CardViewHolderMvc.Listener> {

    interface Listener{
       void onCardClicked(YugiohCard card);
    }


    void bindYugiohCards(YugiohCard card);


}
