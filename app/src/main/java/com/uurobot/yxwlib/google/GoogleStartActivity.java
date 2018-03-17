package com.uurobot.yxwlib.google;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uurobot.yxwlib.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/13.
 */

public class GoogleStartActivity extends Activity {

        private static final String TAG = "GoogleStartActivity";
        private static final int GET_SPEECH_RESULT = 1;

        public static final String SPEECH_RESULT_STATUS = "speech_result_status";

        public static final String SPEECH_RESULT_VALUE = "speech_result_value";
        TextView textView ;

        private TextToSpeech textToSpeech;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.google_start);
                textView = findViewById(R.id.textView);
                PackageManager pm = getPackageManager();
                List<ResolveInfo> activities = pm.queryIntentActivities(
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
                Log.e(TAG, "onCreate: "+activities.size());


                textToSpeech = new TextToSpeech(this.getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                        int result;
                                        result = textToSpeech.setLanguage(Locale.ENGLISH);//支持的语言类型(中文不行,在网上听说要下载一些支持包才可以)

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
                                        Toast.makeText(GoogleStartActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
                                }
                        }
                });
        }


        public void custom(View view){
                Intent intent = new Intent(this,SpeechRecognitionActivity.class);

                Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                        }
                });
                thread.setName("ChildThread");
                thread.start();
                System.out.println("hello child google thread");
                startActivityForResult(intent, GET_SPEECH_RESULT);
        }

        public void system(View view){
//                Intent intent = new Intent(this,GoogleActivity.class);
//                startActivity(intent);

                RecogMgr.getRecogMgr().startRecognizer(new RecogMgr.IResult() {
                        @Override
                        public void onResult(String text) {
                                textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                        }
                },this);
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
                if (requestCode == GET_SPEECH_RESULT){
                        if (resultCode == RESULT_CANCELED){
                                //do nothing for now
                        }else if (resultCode == RESULT_OK){
                                Log.i(TAG,"status;"+ data.getIntExtra(SPEECH_RESULT_STATUS,0));
                                switch (data.getIntExtra(SPEECH_RESULT_STATUS,0)){
                                        case SpeechRecognitionActivity.ERR_NONE:
                                                String text = data.getStringExtra(SPEECH_RESULT_VALUE);
                                                if (text != null && text.trim().length() > 0){
                                                        textView.setText(text);
                                                }
                                                break;
                                        default:
                                                Toast.makeText(this,R.string.error,Toast.LENGTH_SHORT).show();
                                                break;
                                }
                        }
                }
        }
}
