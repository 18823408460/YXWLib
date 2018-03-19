package com.uurobot.yxwlib.retrofit.method1;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by WEI on 2018/3/19.
 */

public interface IYunWenRx {
    /**
     * ªÒ»°  token
     * @param appid
     * @param secret
     * @return
     */
    @GET("token/gettoken")
    Observable<Access_Token> getToken(@Query("appid") String appid, @Query("secret") String secret) ;

}
