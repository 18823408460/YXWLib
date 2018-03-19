package com.uurobot.yxwlib.retrofit.method1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WEI on 2018/3/19.
 */

public class YunWenTest extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void test1() {
        IYunWen iyunwen = RetrofitMgr.getInstance().getIyunwen();
        Call<Access_Token> token = iyunwen.getToken("", "");
        token.enqueue(new Callback<Access_Token>() {
            @Override
            public void onResponse(Call<Access_Token> call, Response<Access_Token> response) {

            }

            @Override
            public void onFailure(Call<Access_Token> call, Throwable t) {

            }
        });
    }

    private void test2() {
        IYunWenRx iyunwenRx = RetrofitMgr.getInstance().getIyunwenRx();
        iyunwenRx.getToken("", "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Access_Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Access_Token access_token) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
