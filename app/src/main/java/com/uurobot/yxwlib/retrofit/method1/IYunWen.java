package com.uurobot.yxwlib.retrofit.method1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by WEI on 2018/3/19.
 */

public interface IYunWen {
    /**
     * 获取  token
     * @param appid
     * @param secret
     * @return
     */
    @GET("token/gettoken")
    Call<Access_Token> getToken(@Query("appid") String appid, @Query("secret") String secret ) ;

}
