package com.uurobot.yxwlib.Strategy;

import android.util.Log;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Price {

        private static final String TAG = "Price";

        //原价
        public void normal(float price) {
                Log.e(TAG, "normal: price=" + price);
        }

        // 8折优惠
        public void normal_8(float price) {
                Log.e(TAG, "normal: price = " + price * 0.8);
        }

        // 9折优惠
        public void normal_9(float price) {
                Log.e(TAG, "normal: price = " + price * 0.9);
        }
}
