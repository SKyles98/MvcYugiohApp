package com.saleef.mvcyugiohapp.Views.CardDetailScreen.ZoomedImage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;

public class ZoomedImageViewImpl extends BaseObservableMvc<ZoomedImageViewImpl.Listener> {

    interface Listener{
        void onNavigateBack();

    }

    private final Toolbar mToolbar;
    private final ImageView mImageView;
    private final RequestOptions mRequestOptions;
    public ZoomedImageViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
           setRootView(layoutInflater.inflate(R.layout.zoomed_image_screen,viewGroup,false));
           mToolbar = findViewById(R.id.zoomToolbar);
           mImageView = findViewById(R.id.zoomImage);
        mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.monstercarddefault).
                error(R.drawable.monstercarddefault);
           mImageView.setOnClickListener(v -> {
               for (Listener listener:getListeners()){
                   listener.onNavigateBack();
               }
           });
           mToolbar.setNavigationOnClickListener(v -> {
               for (Listener listener:getListeners()){
                   listener.onNavigateBack();
               }
           });

    }

    public void bindImage(String imageUrl){
        loadImage(imageUrl);
    }
    private void loadImage(String imageUrl){

        Glide.with(getContext()).
                load(imageUrl) // loads image based off a url
                .apply(mRequestOptions). // apply image based off options
                into(mImageView);
    }

}
