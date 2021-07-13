package com.saleef.mvcyugiohapp.Common.DependencyInjection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.saleef.mvcyugiohapp.Common.DependencyInjection.CompositionRoot;

public class ActivityCompositionRoot {

    private final AppCompatActivity mAppCompatActivity;
    private final FragmentActivity mFragmentActivity;
    private final CompositionRoot mCompositionRoot;



    public ActivityCompositionRoot(AppCompatActivity appCompatActivity,FragmentActivity fragmentActivity,CompositionRoot compositionRoot){
        mAppCompatActivity = appCompatActivity;
        mFragmentActivity = fragmentActivity;
        mCompositionRoot = compositionRoot;
    }


    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }



    public FragmentActivity getFragmentActivity() {
        return mFragmentActivity;
    }



    public AppCompatActivity getAppCompatActivity() {
        return mAppCompatActivity;
    }
}
