package com.saleef.mvcyugiohapp.Common.DependencyInjection;


import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Model.YugiohApi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Intializes Our Apis at the Root before onActivityCreate is called
public class CompositionRoot {

    private Retrofit mRetrofit;


    private Retrofit getRetrofit(){
        if (mRetrofit==null){
            mRetrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).build();

        }
        return mRetrofit;
    }


    public YugiohApi getYugiohApi(){
        return getRetrofit().create(YugiohApi.class);
    }

}
