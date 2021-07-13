package com.saleef.mvcyugiohapp.Common.MvcSkeleton;

import android.content.Context;
import android.view.View;

public abstract class BaseViewMvc implements ViewMvc {
    private View rootView;

    @Override
    public View getRootView() {
        return rootView;
    }

    protected void setRootView(View view){
        rootView = view;
    }

    protected Context getContext(){
        return rootView.getContext();
    }

    protected <T extends View>T  findViewById(int id){
       return rootView.findViewById(id);
    }
}
