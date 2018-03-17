package com.uurobot.yxwlib.google;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uurobot.yxwlib.MainActivity;
import com.uurobot.yxwlib.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 2018/2/5.
 */

public class GoogleActivity extends Activity {

        private static final String TAG = "GoogleActivity";

        private int mBindFlag;

        private Messenger mServiceMessenger;

        private IBinder mService;

        private TextToSpeech textToSpeech;

        private TextView textView;

        boolean isEn = false;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.google);
                textView = findViewById(R.id.showmsg);
                Intent service = new Intent(this, VoiceCommandService.class);
                startService(service);
                mBindFlag = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ? 0 : Context.BIND_ABOVE_CLIENT;

                textToSpeech = new TextToSpeech(this.getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                        int result;
                                        if (isEn) {
                                                result = textToSpeech.setLanguage(Locale.ENGLISH);//支持的语言类型(中文不行,在网上听说要下载一些支持包才可以)
                                        }
                                        else {
                                                result = textToSpeech.setLanguage(Locale.CHINA);//支持的语言类型(中文不行,在网上听说要下载一些支持包才可以)
                                        }

                                        Log.e(TAG, "onInit ok===" + result);
                                        if (result == TextToSpeech.LANG_MISSING_DATA
                                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                Log.e(TAG, "onInit: can not use");
                                        }
                                        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                                                @Override
                                                public void onStart(String utteranceId) {
                                                        Log.e(TAG, "onStart: " + utteranceId);
                                                }

                                                @Override
                                                public void onDone(String utteranceId) {
                                                        Log.e(TAG, "onDone: " + utteranceId);
                                                }

                                                @Override
                                                public void onError(String utteranceId) {
                                                        Log.e(TAG, "onError: " + utteranceId);
                                                }
                                        });
                                        //初始化成功
                                }
                                else {

                                        Toast.makeText(GoogleActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
                                }
                        }
                });
        }

        private final ServiceConnection mServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.d(TAG, "onServiceConnected");
                        mService = service;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                        Log.d(TAG, "onServiceDisconnected");
                        mServiceMessenger = null;
                }

        };

        @Override
        protected void onResume() {
                super.onResume();
                bindService(new Intent(this.getApplicationContext(), VoiceCommandService.class), mServiceConnection, mBindFlag);
        }

        @Override
        protected void onPause() {
                super.onPause();
                if (mServiceConnection != null) {
                        unbindService(mServiceConnection);
                }
        }

        public void startCn(View view) {
                //开启语音识别功能
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //设置模式，目前设置的是自由识别模式
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //提示语言开始文字，就是效果图上面的文字
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please start your voice");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 4);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-CN");
                textToSpeech.setLanguage(Locale.CHINA);
                try {
                        startActivityForResult(intent, 0);
                }
                catch (ActivityNotFoundException a) {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Opps! Your device doesn't support Speech to Text",
                                Toast.LENGTH_SHORT);
                        t.show();
                }
        }

        public void startEn(View view) {
                //开启语音识别功能
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //设置模式，目前设置的是自由识别模式
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //提示语言开始文字，就是效果图上面的文字
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please start your voice");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 4);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                textToSpeech.setLanguage(Locale.ENGLISH);
                try {
                        startActivityForResult(intent, 0);
                }
                catch (ActivityNotFoundException a) {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Opps! Your device doesn't support Speech to Text",
                                Toast.LENGTH_SHORT);
                        t.show();
                }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (data == null) {
                        return;
                }
                Log.e(TAG, "requestCode=" + requestCode);
                Log.e(TAG, "resultCode=" + resultCode);
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Bundle bundle = data.getExtras();
                Log.e(TAG, "onActivityResult: " + bundle.toString());
                textToSpeech.speak(results.get(0), TextToSpeech.QUEUE_FLUSH, null);
                Log.e(TAG, "results=" + results);
                textView.setText(results.get(0));
                float[] confidence;
                String confidenceExtra = RecognizerIntent.EXTRA_CONFIDENCE_SCORES;
                confidence = data.getFloatArrayExtra(confidenceExtra);
                Log.e(TAG, "onActivityResult: "+confidence);
        }
}
