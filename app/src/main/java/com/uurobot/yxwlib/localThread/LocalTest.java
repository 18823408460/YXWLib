package com.uurobot.yxwlib.localThread;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/21.
 */

class LocalTest {

        private static final String TAG = "LocalTest";
        public LocalTest() {
                ThreadLocal<String> threadLocal = new ThreadLocal<>() ;
                String s = threadLocal.get();
                Log.e(TAG, "LocalTest: s==="+s );
        }
}
