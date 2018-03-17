package com.uurobot.yxwlib.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.robot.EventDispatchMgr;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;


/**
 * Created by Administrator on 2017/11/23.
 */

public class RxjavaActivity extends Activity {
        private String single  = test();
        private static final String TAG = "RxjavaActivity";
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_rxjava);

//                testRxjava();
                   testRxjava1();
        }

        private void testRxjava1() {
                Rxutil.getLoginfo("yinxiaowei").subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                                Logger.e(TAG,"onNext========"+s + "   "+Thread.currentThread().getName());
                        }
                });
        }


        String text = "";
        private void testRxjava(){

                final Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
                        @Override
                        public Observable<String> call() {
                                return Observable.from(text.split("e"));
                        }
                });
                text = "hello";
                Subscriber<String> stringSubscriber = new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                                EventDispatchMgr.getEventDispatchMgr().getMode().handlerRecognizer(s);
                                Logger.e(TAG,"-----------------------onNext=="+s);
                        }
                };

                observable.subscribe(stringSubscriber);
        }

        private String test(){
                Logger.e(TAG,".............test............");
                return "hello";
        }
}
