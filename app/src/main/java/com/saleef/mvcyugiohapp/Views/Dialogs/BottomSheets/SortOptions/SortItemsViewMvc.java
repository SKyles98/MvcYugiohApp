package com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.SortOptions;


import com.saleef.mvcyugiohapp.Common.MvcSkeleton.ObservableMvc;

public interface SortItemsViewMvc extends ObservableMvc<SortItemsViewMvc.Listener> {

    interface Listener {
        void onApplyClicked(int sortId,int orderId);


    }



}
