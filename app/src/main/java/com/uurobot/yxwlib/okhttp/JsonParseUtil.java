package com.uurobot.yxwlib.okhttp;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/10/13.
 */

public class JsonParseUtil {
        private static final String TAG = JsonParseUtil.class.getSimpleName();
        private static Gson gson = new Gson();

        public JsonParseUtil() {
        }

        public static <T> T json2Object(String json, Class<T> valueType) {
                if(TextUtils.isEmpty(json)) {
                        return null;
                } else {
                        if(gson == null) {
                                gson = new Gson();
                        }

                        Object bean = null;

                        try {
                                bean = gson.fromJson(json, valueType);
                                return (T) bean;
                        } catch (Exception var4) {
                                return null;
                        }
                }
        }

        public static String object2Json(Object valueType) {
                String json = "";
                Gson gson = new Gson();
                json = gson.toJson(valueType);
                return json;
        }
}

