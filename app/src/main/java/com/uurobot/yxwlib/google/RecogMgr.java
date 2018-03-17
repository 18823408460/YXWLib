package com.uurobot.yxwlib.google;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import com.uurobot.yxwlib.MainApplication;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RecogMgr {

        private static final String TAG = "RecogMgr";

        private static RecogMgr recogMgr;

        private SpeechRecognizer speechRecognizer;

        private RecogMgr() {
                Log.e(TAG, "RecogMgr: create start");

                Log.e(TAG, "RecogMgr: create end");
        }

        public static RecogMgr getRecogMgr() {
                if (recogMgr == null) {
                        synchronized (RecogMgr.class) {
                                if (recogMgr == null) {
                                        recogMgr = new RecogMgr();
                                }
                        }
                }
                return recogMgr;
        }

        public void startRecognizer(final IResult iResult, Activity activity) {
                Log.e(TAG, "RecogMgr: start");
                final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // // 语言模式和自由模式的语音识别
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, MainApplication.getContext().getPackageName());
                // intent.setPackage("com.google.android.voicesearce");
                // intent.setClassName("com.google.android.voicesearce","com.google.android.voicesearch.RecognitionService");
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainApplication.getContext());
                Log.e(TAG, "RecogMgr: create start22");
                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                        @Override
                        public void onReadyForSpeech(Bundle params) {
                                Log.e(TAG, "onReadyForSpeech: ");
                        }

                        @Override
                        public void onBeginningOfSpeech() {
                                Log.e(TAG, "onBeginningOfSpeech: ");
                        }

                        @Override
                        public void onRmsChanged(float rmsdB) {
                                Log.e(TAG, "onRmsChanged: " + rmsdB);
                        }

                        @Override
                        public void onBufferReceived(byte[] buffer) {
                                Log.e(TAG, "onBufferReceived: ");
                        }

                        @Override
                        public void onEndOfSpeech() {
                                Log.e(TAG, "onEndOfSpeech: ");
                        }

                        @Override
                        public void onError(int error) {
                                Log.e(TAG, "onError: ");
                        }

                        @Override
                        public void onResults(Bundle results) {
                                Log.e(TAG, "onResults: ");
                                if (results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) != null) {
                                        String text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                                        Log.e(TAG, "onResults: text=" + text);
                                        iResult.onResult(text);
                                }
                        }

                        @Override
                        public void onPartialResults(Bundle partialResults) {
                                Log.e(TAG, "onPartialResults: ");
                        }

                        @Override
                        public void onEvent(int eventType, Bundle params) {
                                Log.e(TAG, "onEvent: ");
                        }
                });

                // 判断是否有WRITE_SETTINGS权限if(!Settings.System.canWrite(this)) {
                // 申请WRITE_SETTINGS权限
                if(!Settings.System.canWrite(activity)) {
                        Intent intent1 = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + activity.getPackageName()));
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivityForResult(intent1, 100);
                }else {
                        Settings.Secure.putString(MainApplication.getContext().getContentResolver(), "voice_recognition_service", "com.google.android.voicesearch/.GoogleRecognitionService");
                        speechRecognizer.startListening(intent);
                }

                Log.e(TAG, "RecogMgr: start222");
        }

        interface IResult {

                void onResult(String text);
        }

}
