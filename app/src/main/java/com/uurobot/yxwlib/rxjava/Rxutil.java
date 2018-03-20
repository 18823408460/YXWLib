package com.uurobot.yxwlib.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/23.
 */

public class Rxutil {

        //        public static Observable<String>  getLoginfo(final String name){
//                return Observable.create(new Observable.OnSubscribe<String>() {
//                        @Override
//                        public void call(Subscriber<? super String> subscriber) {
//                                subscriber.onNext(String.format("%s hello",name));
//                        }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//        }
        public static Observable<String> getLoginfo(final String name) {
                return Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(ObservableEmitter<String> e) throws Exception {
                                e.onNext(name);
                        }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        }

}
