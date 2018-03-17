package com.uurobot.yxwlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/22.
 */

public class WatchActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_watch);

                final WatchView watchView = findViewById(R.id.watch);

                Timer timer  =new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                                watchView.postInvalidate();
                        }
                },0,1000);
        }
}
