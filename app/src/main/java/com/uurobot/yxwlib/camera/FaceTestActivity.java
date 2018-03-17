package com.uurobot.yxwlib.camera;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/12/8.
 */

public class FaceTestActivity extends  CameraBaseActivity {
        private SurfaceView test_sf ;

        private static final String TAG = "FaceTestActivity";
        private SurfaceHolder holder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test);
                test_sf = (SurfaceView) findViewById(R.id.test_sf);
                holder = test_sf.getHolder();

                holder.addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {
                                Logger.e(TAG,"surfaceCreated");
                                //open();
                                initCamera(holder);
                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                Logger.e(TAG,"surfaceChanged");
                                // prew();
                                openCamera();
                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {
                                Logger.e(TAG,"surfaceDestroyed");
                                recycleCamera();
                        }
                });
        }

        @Override
        protected void onPreviewCallback(byte[] data) {
                Logger.e(TAG,"onPreviewCallback");
        }
}
