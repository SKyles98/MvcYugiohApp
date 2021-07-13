package com.saleef.mvcyugiohapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardPrice {
    @SerializedName("cardmarket_price")
    @Expose
    private String cardmarketPrice;
    @SerializedName("tcgplayer_price")
    @Expose
    private String tcgplayerPrice;
    @SerializedName("ebay_price")
    @Expose
    private String ebayPrice;
    @SerializedName("amazon_price")
    @Expose
    private String amazonPrice;
    @SerializedName("coolstuffinc_price")
    @Expose
    private String coolstuffincPrice;

    public String getCardmarketPrice() {
        return cardmarketPrice;
    }


    public String getTcgplayerPrice() {
        return tcgplayerPrice;
    }



    public String getEbayPrice() {
        return ebayPrice;
    }



    public String getAmazonPrice() {
        return amazonPrice;
    }



    public String getCoolstuffincPrice() {
        return coolstuffincPrice;
    }



}






