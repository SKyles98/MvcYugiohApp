package com.saleef.mvcyugiohapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBaseVersion {



    @Expose
    @SerializedName("last_update")
    String last_update;


    public String getLast_update() {
        return last_update;
    }
}
