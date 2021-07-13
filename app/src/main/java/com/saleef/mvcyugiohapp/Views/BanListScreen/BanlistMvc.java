package com.saleef.mvcyugiohapp.Views.BanListScreen;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.List;

public interface BanlistMvc extends ObservableMvc<BanlistMvc.Listener> {

    interface Listener{
        void onCardClicked(YugiohCard yugiohCard);
        void onFormatClicked(String selectedFormat);
        void onLimitedClicked();
        void onSemiLimitedClicked();
        void onBannedClicked();
        void onShowAll();
    }

     void showProgressNotification();
     void hideProgressNotification();
     void bindBanlistCards(List<YugiohCard> yugiohCards);
}
