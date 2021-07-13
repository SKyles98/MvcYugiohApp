package com.saleef.mvcyugiohapp;

import android.os.Bundle;

import android.widget.FrameLayout;

import com.saleef.mvcyugiohapp.Common.Base.BaseActivity;
import com.saleef.mvcyugiohapp.Common.FragmentHelper.FragmentFrameHelper;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Views.ViewContainer.ContainerViewMvc;

import java.util.HashSet;
import java.util.Set;
// This will be a yugioh app that Utilizes the Mvc Architecture Pattern





public class MainActivity extends BaseActivity implements
        FragmentFrameHelper,BackPressedDispatcher {



    private ContainerViewMvc mContainerViewMvc;
    private final Set<BackPressedListener> mBackPressedListeners = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         mContainerViewMvc = getControllerCompositionRoot().getViewMvcFactory().getContainerViewImpl(null);
        ScreenNavigator screenNavigator = getControllerCompositionRoot().getScreenNavigator();
        setContentView(mContainerViewMvc.getRootView());

        if (savedInstanceState==null){
             screenNavigator.navigateToHomeScreen();
         }



    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override // Gets the FrameLayout of our container class to load all of our fragments in
    public FrameLayout getFrameLayout() {
        return mContainerViewMvc.getFrameLayout();
    }



    @Override
    public void registerListener(BackPressedListener listener) {
             mBackPressedListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
            mBackPressedListeners.add(listener);
    }

    @Override
    public void onBackPressed() {


        boolean isBackPressConsumedByAnyListener = false;
        for (BackPressedListener listener : mBackPressedListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }
        if (isBackPressConsumedByAnyListener) {
            return;
        }
        super.onBackPressed();
    }
}