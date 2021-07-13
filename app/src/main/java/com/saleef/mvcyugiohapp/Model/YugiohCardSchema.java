package com.saleef.mvcyugiohapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YugiohCardSchema {


    @SerializedName("data")
    @Expose
    private List<Card> mCardList;



    public YugiohCardSchema(List<Card> cardList){
        mCardList = cardList;
    }


    public List<Card> getCardList() {
        return mCardList;
    }
}
