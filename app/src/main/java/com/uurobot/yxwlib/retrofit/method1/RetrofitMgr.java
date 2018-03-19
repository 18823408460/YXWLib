package com.uurobot.yxwlib.retrofit.method1;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WEI on 2018/3/19.
 */

public class RetrofitMgr {
    private static RetrofitMgr retrofitMgr;
    private final String baseur = "";
    private Retrofit retrofit;

    private RetrofitMgr() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseur)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public IYunWen getIyunwen(){
        return  retrofit.create(IYunWen.class);
    }


    public IYunWenRx getIyunwenRx(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(baseur)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return  retrofit1.create(IYunWenRx.class);
    }


    public static RetrofitMgr getInstance() {
        if (retrofitMgr == null) {
            synchronized (RetrofitMgr.class) {
                if (retrofitMgr == null) {
                    retrofitMgr = new RetrofitMgr();
                }
            }
        }
        return retrofitMgr;
    }


}
