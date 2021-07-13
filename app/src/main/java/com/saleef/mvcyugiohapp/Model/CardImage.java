package com.saleef.mvcyugiohapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardImage {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("image_url_small")
    @Expose
    private String imageUrlSmall;

    public Integer getId() {
        return id;
    }



    public String getImageUrl() {
        return imageUrl;
    }



    public String getImageUrlSmall() {
        return imageUrlSmall;
    }



}

