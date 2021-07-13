package com.saleef.mvcyugiohapp.Views.HomeScreen;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;

public interface HomeScreenMvc extends ObservableMvc<HomeScreenMvc.Listener> {

    interface Listener{
       void onDataBaseClicked();
       void onDeckBuilderClicked();
       void onCardClicked(YugiohCard yugiohCard);
       void onCalculatorClicked();
    }


    void bindLatestYugiohCards(List<YugiohCard> yugiohCards);
    // Checks whether we already have the cards in our database or not
    void bindDateInfo(String data,String CardSize);
    void showProgressNotification();
    void hideProgressNotification();
}
