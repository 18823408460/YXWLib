package com.uurobot.yxwlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.uurobot.yxwlib.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SinButtonActivity extends Activity {

        private static final String TAG = "SinButtonActivity";
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_sinbtn);

                final SinButton sinBtn = findViewById(R.id.sinBtn);
                sinBtn.setClickLisener(new SinButton.IClickLisener() {
                        @Override
                        public void onClick(int index) {
                                Log.e(TAG, "onClick: "+Thread.currentThread().getName() );
                                Toast.makeText(SinButtonActivity.this,"index="+index,Toast.LENGTH_SHORT).show();
                        }
                });
                TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                             //   Log.e(TAG, "run: ............." );
                                sinBtn.postInvalidate();
                        }
                };
                Timer timer = new Timer();
                //timer.schedule(timerTask,10,200);
        }
}
