package com.uurobot.yxwlib.localThread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/21.
 */

public class LocalThreadActivity extends AppCompatActivity {
        private static HashMap<Thread,String> hashMap = new HashMap<>();
        private static final String TAG = "LocalThreadActivity";
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                LocalInstance.getInstance().setData("helooworld");
                LocalTest localTest = new LocalTest();
                Log.e(TAG, "onCreate: "+LocalInstance.getInstance().getData() );

        }
}
