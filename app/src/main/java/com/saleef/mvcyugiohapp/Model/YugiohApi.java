package com.saleef.mvcyugiohapp.Model;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

//API calls
public interface YugiohApi {

    @GET("cardinfo.php")
    Observable<YugiohCardSchema> getAllYugiohCards();

    @GET("cardinfo.php")  // Returns 40 of the newest cards
    Observable<YugiohCardSchema> getNewestYugiohCard(@QueryMap Map<String,String> filters);

    @GET("cardinfo.php")
    Observable<YugiohCardSchema> getBanListCards(@Query("banlist") String format);


    @GET("checkDBVer.php")
    Observable<List<DataBaseVersion>> getDataBaseVersion();



}
