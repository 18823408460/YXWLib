package com.uurobot.yxwlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ProgressActivity extends Activity {
        private static final int MSG_PROGRESS_UPDATE = 0x110;
        private ProgressHor progressHor ;

        private Handler mHandler = new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                        int progress = progressHor.getProgress();
                        progressHor.setProgress(++progress);
                        if (progress >= 100) {
                                mHandler.removeMessages(MSG_PROGRESS_UPDATE);

                        }
                        mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
                };
        };


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_progress);
                progressHor = findViewById(R.id.progressHor);
                //mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);

        }
}
