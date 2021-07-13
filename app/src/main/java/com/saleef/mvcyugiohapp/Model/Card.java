package com.saleef.mvcyugiohapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Card {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("atk")
    @Expose
    private Integer atk;
    @SerializedName("def")
    @Expose
    private Integer def;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("attribute")
    @Expose
    private String attribute;
    @SerializedName("scale")
    @Expose
    private Integer scale;
    @SerializedName("archetype")
    @Expose
    private String archetype;
    @SerializedName("card_sets")
    @Expose
    private List<CardSet> cardSets;
    @SerializedName("card_images")
    @Expose
    private List<CardImage> cardImages;
    @SerializedName("card_prices")
    @Expose
    private List<CardPrice> cardPrices;

    @SerializedName("linkval")
    @Expose
    private Integer linkRating;

    @SerializedName("linkmarkers")
    @Expose
    private List<String> linkArrows;

    @SerializedName("banlist_info")
    @Expose
    private BanListInfo mBanListInfo;


    public BanListInfo getBanListInfo() {
        return mBanListInfo;
    }

    public Integer getLinkRating() {
        return linkRating;
    }

    public List<String> getLinkArrows() {
        return linkArrows;
    }

    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    public String getType() {
        return type;
    }



    public String getDesc() {
        return desc;
    }



    public Integer getAtk() {
        return atk;
    }



    public Integer getDef() {
        return def;
    }



    public Integer getLevel() {
        return level;
    }



    public String getRace() {
        return race;
    }

    public Integer getScale() {
        return scale;
    }

    public String getAttribute() {
        return attribute;
    }



    public String getArchetype() {
        return archetype;
    }



    public List<CardSet> getCardSets() {
        return cardSets;
    }



    public List<CardImage> getCardImages() {
        return cardImages;
    }



    public List<CardPrice> getCardPrices() {
        return cardPrices;
    }



}

