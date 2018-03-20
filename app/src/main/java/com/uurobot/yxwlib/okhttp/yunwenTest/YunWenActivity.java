package com.uurobot.yxwlib.okhttp.yunwenTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.okhttp.OkHttpUtils;
import com.uurobot.yxwlib.okhttp.yunwenTest.bean.Access_Token;
import com.uurobot.yxwlib.okhttp.yunwenTest.bean.ResultData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/19.
 */

public class YunWenActivity extends Activity {
        Access_Token access_token ;
        String baseUrl = String.format("%stoken/getToken?", Constant.HOSTNAME);
        String chatbaseUrl = String.format("%sservlet/ApiChat?", Constant.HOSTNAME);
        EditText editText ;
        private static final String TAG = "YunWenActivity";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_yunwen);
                editText = findViewById(R.id.editText);
                StringBuilder stringBuilder = new StringBuilder();
                long time = System.currentTimeMillis();

//                stringBuilder.append(baseUrl)
//                        .append("token=" + RobotBase62.base62Encode(getToken(time).getBytes()))
//                        .append("&")
//                        .append("sourceId="+Constant.SOURCEID)
//                        .append("&")
//                        .append("s=ss")
//                        .append("&")
//                        .append("timestamp="+time)
//                        .append("&")
//                        .append("clientId=" + Constant.CLIENDID);
                stringBuilder.append(baseUrl)
                        .append("appId=" + Constant.APPID)
                        .append("&")
                        .append("secret="+Constant.SECRET);

                Log.e(TAG, "onCreate: url==" + stringBuilder.toString());
                OkHttpUtils.getInstance().getAsyn(this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                        @Override
                        public void onBefore(Request var1, Object var2) {
                                Log.e(TAG, "onBefore: ");
                        }

                        @Override
                        public void onAfter(Object var1) {
                                Log.e(TAG, "onAfter: ");
                        }

                        @Override
                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                Log.e(TAG, "onError: "+var2);
                                Log.e(TAG, "onError: "+var3);
                        }

                        @Override
                        public void onResponse(Object var1, Object var2) {
                                Log.e(TAG, "onResponse: " + var1);
                                Gson gson = new Gson();
                                access_token = gson.fromJson((String) var1, Access_Token.class);
                                Log.e(TAG, "onResponse: "+access_token );
                                String q = "";
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(chatbaseUrl)
                                        .append("access_token=" + access_token.getAccess_token())
                                        .append("&")
                                        .append("sourceId="+Constant.SOURCEID)
                                        .append("&")
                                        .append("s="+Constant.ROBAT_ONLIONING)
                                        .append("&")
//                                        .append("question="+q)
//                                        .append("&")
                                        .append("clientId=" + Constant.CLIENDID);
                               OkHttpUtils.getInstance().getAsyn(YunWenActivity.this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                                        @Override
                                        public void onBefore(Request var1, Object var2) {
                                                Log.e(TAG, "onBefore: ");
                                        }

                                        @Override
                                        public void onAfter(Object var1) {
                                                Log.e(TAG, "onAfter: ");
                                        }

                                        @Override
                                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                                Log.e(TAG, "onError: "+var2);
                                                Log.e(TAG, "onError: "+var3);
                                        }

                                        @Override
                                        public void onResponse(Object var1, Object var2) {
                                                Log.e(TAG, "onResponse: " + var1);
                                        }
                                });
                        }
                });
        }


        public void send(View view) throws UnsupportedEncodingException {
                String q = URLEncoder.encode("深圳的天气","utf-8");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(chatbaseUrl)
                        .append("access_token=" + access_token.getAccess_token())
                        .append("&")
                        .append("sourceId="+Constant.SOURCEID)
                        .append("&")
                        .append("s="+Constant.QUESTION)
                        .append("&")
                        .append("question="+q)
                        .append("&")
                        .append("clientId=" + Constant.CLIENDID);
                Log.e(TAG, "send: url==" + stringBuilder.toString());
                OkHttpUtils.getInstance().getAsyn(this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                        @Override
                        public void onBefore(Request var1, Object var2) {
                                Log.e(TAG, "onBefore: ");
                        }

                        @Override
                        public void onAfter(Object var1) {
                                Log.e(TAG, "onAfter: ");
                        }

                        @Override
                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                Log.e(TAG, "onError: "+var2);
                                Log.e(TAG, "onError: "+var3);
                        }

                        @Override
                        public void onResponse(Object var1, Object var2) {
                                Log.e(TAG, "onResponse: " + var1);
                                String data = (String) var1;
                                Gson  gson = new Gson() ;
                                ResultData resultData = gson.fromJson(data, ResultData.class);
                                String ansCon = resultData.getRobotAnswer().get(0).getAnsCon();
                                Log.e(TAG, "onResponse: ansCon==="+ ansCon);
                                if (ansCon.contains("<br />")){
                                        String s = ansCon.replaceAll("<br />", ",");
                                        Log.e(TAG, "onResponse: "+s );
                                }
                        }
                });
        }

        private String getToken(long times) {
                String cliendid =Constant.CLIENDID;
                int nonce = new Random().nextInt(10);

                String source = cliendid + times + nonce;
                Log.e(TAG, "getToken: "+source );
                return source;
        }

}
