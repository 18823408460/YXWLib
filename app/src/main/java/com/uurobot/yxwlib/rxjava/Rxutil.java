package com.uurobot.yxwlib.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/23.
 */

public class Rxutil {

        public static Observable<String>  getLoginfo(final String name){
                return Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                                subscriber.onNext(String.format("%s hello",name));
                        }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }


}
