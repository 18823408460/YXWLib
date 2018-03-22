package com.uurobot.yxwlib.localThread;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/21.
 */

class LocalTest {

        private static final String TAG = "LocalTest";
        public LocalTest() {

                Log.e(TAG, "LocalTest: s==="+LocalInstance.getInstance().getData() );
        }
}
