package com.saleef.mvcyugiohapp.Views.DuelCalculator;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;

public interface DuelCalculatorMvc extends ObservableMvc<DuelCalculatorMvc.Listener> {


    interface Listener{
        void onNavigateUpClicked();
        void onCoinTossClicked();
        void onDiceRollClicked();
    }


    void setAmount(String amount);
    void clearAmount();
}
