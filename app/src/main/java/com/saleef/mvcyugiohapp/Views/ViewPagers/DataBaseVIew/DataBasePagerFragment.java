package com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;

public class DataBasePagerFragment extends BaseFragment implements DataBasePagerImpl.Listener {


    private DataBasePagerImpl mDataBasePager;
    private ScreenNavigator mScreenNavigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBasePager = getControllerCompositionRoot().getViewMvcFactory().getDataBasePagerImpl(container);
        return mDataBasePager.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDataBasePager.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDataBasePager.unregisterListener(this);
    }

    @Override
    public void onNavigateUpClicked() {
        mScreenNavigator.navigateUp();
    }
}
