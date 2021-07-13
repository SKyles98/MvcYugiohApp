package com.saleef.mvcyugiohapp.Views.ViewContainer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseViewMvc;
import com.saleef.mvcyugiohapp.R;

// Our container FrameLayout for single activity approach all Fragments goes in here
public class ContainerViewImpl extends BaseViewMvc implements ContainerViewMvc {

    private final FrameLayout mFrameLayout;



    public ContainerViewImpl(LayoutInflater inflater, ViewGroup viewGroup){
        setRootView(inflater.inflate(R.layout.activity_main,viewGroup,false));
        mFrameLayout = findViewById(R.id.frameContainer);
    }

    @Override
    public FrameLayout getFrameLayout() {
        return mFrameLayout;
    }
}
