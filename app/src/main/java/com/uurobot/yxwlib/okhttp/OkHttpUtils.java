package com.uurobot.yxwlib.okhttp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/13.
 */

public class OkHttpUtils {
        public static final String REQUEST_STRING_KEY_NAME = "mobile_request_attribute";
        public static final int CONNECTION_TIME_OUT_DEFAULT = 20000;
        public static final int READ_TIME_OUT_DEFAULT = 60000;
        private static OkHttpUtils mInstance;
        private OkHttpClient mOkHttpClient;
        private Handler mDelivery;
        private Context mContext;

        public OkHttpUtils(OkHttpClient okHttpClient) {
                if(okHttpClient == null) {
                        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
                        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                                public boolean verify(String hostname, SSLSession session) {
                                        return true;
                                }
                        });
                        this.mOkHttpClient = okHttpClientBuilder.build();
                } else {
                        this.mOkHttpClient = okHttpClient;
                }

                this.init();
        }

        private void init() {
                this.mDelivery = new Handler(Looper.getMainLooper());
        }

        public static OkHttpUtils getInstance(OkHttpClient okHttpClient) {
                if(mInstance == null) {
                        synchronized(OkHttpUtils.class) {
                                if(mInstance == null) {
                                        mInstance = new OkHttpUtils(okHttpClient);
                                }
                        }
                }

                return mInstance;
        }

        public static OkHttpUtils getInstance() {
                if(mInstance == null) {
                        synchronized(OkHttpUtils.class) {
                                if(mInstance == null) {
                                        mInstance = new OkHttpUtils((OkHttpClient)null);
                                }
                        }
                }

                return mInstance;
        }

        private Handler getDelivery() {
                return this.mDelivery;
        }

        private OkHttpClient getOkHttpClient() {
                this.mOkHttpClient.newBuilder().connectTimeout(20000L, TimeUnit.MILLISECONDS).readTimeout(60000L, TimeUnit.MILLISECONDS).writeTimeout(60000L, TimeUnit.MICROSECONDS).build();
                return this.mOkHttpClient;
        }

        public void cancelTag(Object tag) {
                Iterator var2 = this.mOkHttpClient.dispatcher().queuedCalls().iterator();

                Call call;
                while(var2.hasNext()) {
                        call = (Call)var2.next();
                        if(tag.equals(call.request().tag())) {
                                call.cancel();
                        }
                }

                var2 = this.mOkHttpClient.dispatcher().runningCalls().iterator();

                while(var2.hasNext()) {
                        call = (Call)var2.next();
                        if(tag.equals(call.request().tag())) {
                                call.cancel();
                        }
                }

        }

        private void sendFailResultCallback(final Call call, final Response response, final Exception e, final OkHttpUtils.OkCallBack callback, final Object tag) {
                if(callback != null) {
                        this.mDelivery.post(new Runnable() {
                                public void run() {
                                        callback.onError(call, response, e, tag);
                                        callback.onAfter(tag);
                                }
                        });
                }
        }

        private void sendSuccessResultCallback(final String result, final OkHttpUtils.OkCallBack callback, final Object tag) {
                if(callback != null) {
                        this.mDelivery.post(new Runnable() {
                                public void run() {
                                        if(result != null) {
                                                callback.onResponse(result, tag);
                                        } else {
                                        }

                                        callback.onAfter(tag);
                                }
                        });
                }
        }

        private static Request postRequest(Object tag, String url, ArrayMap<String, String> requestStrs) {
                okhttp3.FormBody.Builder mBuilder = new okhttp3.FormBody.Builder();
                if(requestStrs != null) {
                        for(int i = 0; i < requestStrs.size(); ++i) {
                                mBuilder.add((String)requestStrs.keyAt(i), (String)requestStrs.valueAt(i));
                        }
                }

                return (new okhttp3.Request.Builder()).tag(tag).url(url).post(mBuilder.build()).build();
        }

        private static Request postRequestWithPic(Object tag, String url, ArrayMap<String, String> requestStrs, ArrayMap<String, String> files) {
                okhttp3.MultipartBody.Builder builder = new okhttp3.MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                int i;
                if(requestStrs != null) {
                        for(i = 0; i < requestStrs.size(); ++i) {
                                builder.addFormDataPart((String)requestStrs.keyAt(i), (String)requestStrs.valueAt(i));
                        }
                }

                if(files != null) {
                        for(i = 0; i < files.size(); ++i) {
                                builder.addFormDataPart((String)files.keyAt(i), (String)files.keyAt(i), RequestBody.create(MultipartBody.FORM, new File((String)files.valueAt(i))));
                        }
                }

                return (new okhttp3.Request.Builder()).tag(tag).url(url).post(builder.build()).build();
        }

        private static Request getRequest(Object tag, String url) {
                return (new okhttp3.Request.Builder()).tag(tag).url(url).get().build();
        }

        private void foaCall(Call call, final OkHttpUtils.OkCallBack okCallBack, final Object tag) {
                if(okCallBack != null) {
                        okCallBack.onBefore(call.request(), tag);
                        call.enqueue(new Callback() {
                                public void onFailure(Call call, IOException e) {
                                        if(null != e && null != e.getMessage() && e.getMessage().equals("Canceled")) {
                                                Log.e("OkHttpUtils", "Canceled");
                                        } else {
                                                sendFailResultCallback(call, (Response)null, e, okCallBack, tag);
                                        }

                                }

                                public void onResponse(Call call, Response response) throws IOException {
                                        if(response.code() >= 400 && response.code() <= 599) {
                                                try {
                                                        sendFailResultCallback(call, response, new RuntimeException(response.body().string()), okCallBack, tag);
                                                } catch (IOException var4) {
                                                        var4.printStackTrace();
                                                }

                                        } else {
                                                try {
                                                        String e = OkHttpUtils.this.parseNetworkResponse(response);
                                                        sendSuccessResultCallback(e, okCallBack, tag);
                                                } catch (Exception var5) {
                                                        sendFailResultCallback(call, response, var5, okCallBack, tag);
                                                }

                                        }
                                }
                        });
                }
        }

        private static final String TAG = "OkHttpUtils";
        private String parseNetworkResponse(Response response) {
                String data = IOStreamUtils.formatResponse(response);
                BaseResponseBean responseBean = (BaseResponseBean)JsonParseUtil.json2Object(data, BaseResponseBean.class);
                Log.e(TAG, "parseNetworkResponse: "+ responseBean);
                return  data;
        }

        public void postAsyn(Context context, Object tag, String url, ArrayMap<String, String> requestStrs, OkHttpUtils.OkCallBack callback) {
                this.mContext = context;
                Call call = getInstance().getOkHttpClient().newCall(postRequest(tag, url, requestStrs));
                getInstance().foaCall(call, callback, tag);
        }

        public String postSyn(Context context, Object tag, String url, ArrayMap<String, String> requestStrs) throws Exception {
                this.mContext = context;
                return getInstance().getOkHttpClient().newCall(postRequest(tag, url, requestStrs)).execute().body().string();
        }

        public void getAsyn(Context context, Object tag, String url,OkHttpUtils.OkCallBack callback) {
                this.mContext = context;
                Log.i("OkHttpUtils", "tag:" + tag + "-----------url:" + url);
                Call call = getInstance().getOkHttpClient().newCall(getRequest(tag, url));
                getInstance().foaCall(call, callback, tag);
        }

        public void postAsynWithPic(Context context, Object tag, String url, ArrayMap<String, String> requestStrs, ArrayMap<String, String> files, OkHttpUtils.OkCallBack callback) {
                this.mContext = context;
                Call call = getInstance().getOkHttpClient().newCall(postRequestWithPic(tag, url, requestStrs, files));
                getInstance().foaCall(call, callback, tag);
        }

        public interface OkCallBack {
                void onBefore(Request var1, Object var2);

                void onAfter(Object var1);

                void onError(Call var1, Response var2, Exception var3, Object var4);

                void onResponse(Object var1, Object var2);
        }
}
