package com.uurobot.yxwlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/30.
 */

public class FragmentTestActivity extends FragmentActivity {

        private static final String TAG = "FirstFragment";
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_fragment_main);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
                FirstFragment  firstFragment = new FirstFragment();

                fragmentTransaction.add(R.id.frament,firstFragment,"first");

                fragmentTransaction.commit();
                Log.e(TAG, "onCreate: FragmentTestActivity" );
        }

        @Override
        protected void onResume() {
                super.onResume();
                Log.e(TAG, "onResume: FragmentTestActivity" );
        }

        @Override
        protected void onPause() {
                super.onPause();
        }


}
