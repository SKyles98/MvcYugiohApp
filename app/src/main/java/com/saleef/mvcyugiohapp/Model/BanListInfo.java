package com.saleef.mvcyugiohapp.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanListInfo {

    @SerializedName("ban_tcg")
    @Expose
    private String tcgBan;

    @SerializedName("ban_ocg")
    @Expose
    private String ocgBan;


    public String getOcgBan() {
        return ocgBan;
    }

    public String getTcgBan() {
        return tcgBan;
    }
}
