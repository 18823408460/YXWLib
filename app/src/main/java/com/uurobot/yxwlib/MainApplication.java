package com.uurobot.yxwlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.uurobot.yxwlib.hotfix.FixDexUtils;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MainApplication extends Application {
        private static Context context ;
        private static final String TAG = "MainApplication";
        @Override
        public void onCreate() {
                super.onCreate();
                context = this ;
                FixDexUtils.loadFixedDex(this, Environment.getExternalStorageDirectory());
                 registerActivityListener();
        }

        public static Context getContext(){
                return context;
        }

        private void registerActivityListener() {
                registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {


                        @Override
                        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                                Log.e(TAG, "onActivityCreated: "+ activity.getComponentName().toString());
                        }

                        @Override
                        public void onActivityStarted(Activity activity) {
                                Log.e(TAG, "onActivityStarted: "+activity.getComponentName().toString() );
                        }

                        @Override
                        public void onActivityResumed(Activity activity) {
                                Log.e(TAG, "onActivityResumed: "+activity.getComponentName().toString() );
                        }

                        @Override
                        public void onActivityPaused(Activity activity) {
                                Log.e(TAG, "onActivityPaused: "+activity.getComponentName().toString() );
                        }

                        @Override
                        public void onActivityStopped(Activity activity) {
                                Log.e(TAG, "onActivityStopped: "+activity.getComponentName().toString() );
                        }

                        @Override
                        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                                Log.e(TAG, "onActivitySaveInstanceState: "+activity.getComponentName().toString() );
                        }

                        @Override
                        public void onActivityDestroyed(Activity activity) {
                                Log.e(TAG, "onActivityDestroyed: "+activity.getComponentName().toString() );
                        }
                });
        }
}
