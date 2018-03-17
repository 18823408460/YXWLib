package com.uurobot.yxwlib.Strategy;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/10/30.
 */

public class FanType<Result, Params> {

        private static final String TAG = "FanType";

        public void parse() {
                Type type = this.getClass().getGenericSuperclass();
                Log.e(TAG, "parse:type==" + type);
                if (!(type instanceof ParameterizedType)) {
                        Log.e(TAG, "parse:ddd ");
                        return ;
                }

                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] classes = (Type[]) parameterizedType.getActualTypeArguments();
                for (Type ee : classes) {
                        Log.e(TAG, "parse: " + ee.toString());
                }
        }

}
