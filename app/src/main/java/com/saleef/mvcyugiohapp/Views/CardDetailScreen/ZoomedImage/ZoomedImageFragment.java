package com.saleef.mvcyugiohapp.Views.CardDetailScreen.ZoomedImage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;

public class ZoomedImageFragment extends BaseFragment implements ZoomedImageViewImpl.Listener {

      private ZoomedImageViewImpl mZoomedImageView;
      private ScreenNavigator mScreenNavigator;


    public static ZoomedImageFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();
        args.putString("Image",imageUrl);
        ZoomedImageFragment fragment = new ZoomedImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
    }


    @Override
    public void onStart() {
        super.onStart();
        mZoomedImageView.registerListener(this);
        mZoomedImageView.bindImage(getImageURl());
    }

    @Override
    public void onStop() {
        super.onStop();
        mZoomedImageView.unregisterListener(this);
    }

    private String getImageURl(){
        return getArguments().getString("Image");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mZoomedImageView = getControllerCompositionRoot().getViewMvcFactory().getZoomedImageViewImpl(container);
        return mZoomedImageView.getRootView();
    }

    @Override
    public void onNavigateBack() {
        mScreenNavigator.navigateUp();
    }




}
