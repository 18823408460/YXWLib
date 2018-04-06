package com.uurobot.yxwlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.uurobot.yxwlib.hotfix.FixDexUtils;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MainApplication extends Application {
    private static Context context;
    private static final String TAG = "MainApplication";
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        FixDexUtils.loadFixedDex(this, Environment.getExternalStorageDirectory());
        registerActivityListener();

//                handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                                Intent intent = new Intent(context,RefreshActivity.class);
//                                intent.putExtra("d","d");
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent);
//                        }
//                },2000);

    }


    /**
     * 倒计时功能--- 使用不当，很容易造成内存泄漏。。。
     */
    private void timerTest() {
        /**
         * 参数1，设置倒计时的总时间（毫秒）
         * 参数2，设置每次减去多少毫秒
         */
        CountDownTimer countDownTimer = new CountDownTimer(1000, 60) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

            }
        };
    }

    public static Context getContext() {
        return context;
    }

    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {


            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e(TAG, "onActivityCreated: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e(TAG, "onActivityStarted: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e(TAG, "onActivityResumed: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.e(TAG, "onActivityPaused: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.e(TAG, "onActivityStopped: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.e(TAG, "onActivitySaveInstanceState: " + activity.getComponentName().toString());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.e(TAG, "onActivityDestroyed: " + activity.getComponentName().toString());
            }
        });
    }
}
