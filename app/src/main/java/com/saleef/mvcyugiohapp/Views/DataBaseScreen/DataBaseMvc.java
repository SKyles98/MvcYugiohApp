package com.saleef.mvcyugiohapp.Views.DataBaseScreen;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;

public interface DataBaseMvc extends ObservableMvc<DataBaseMvc.Listener> {


    interface Listener{
       void onCardClicked(YugiohCard yugiohCard);
       void onSortClicked();
    }


    void showProgressIndication();
    void hideProgressIndication();
    void bindCards(List<YugiohCard> yugiohCards);
    void bindSortOptions(int sortKey,int orderKey);
}
