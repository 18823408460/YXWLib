package com.uurobot.yxwlib.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ProjectActivity extends Activity {

        private static final String TAG = "ProjectActivity";
        private MediaProjectionManager mediaProjectionManager ;
        private int code_project = 1;
        private SurfaceView surfaceView ;
        private SurfaceHolder surfaceHolder  ;

        private MediaProjection mediaProject;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_project);
                surfaceView = findViewById(R.id.surfaceView);
                surfaceHolder = surfaceView.getHolder();

                initProject();
        }

        private void initProject(){
                mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                Intent intent =mediaProjectionManager.createScreenCaptureIntent();
                startActivityForResult(intent,code_project);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                Logger.e(TAG, "onActivityResult:  requestCode="+requestCode +"  resultCode="+resultCode );
                
                mediaProject = mediaProjectionManager.getMediaProjection(resultCode,data);
                mediaProject.registerCallback(new MediaProjection.Callback() {
                        @Override
                        public void onStop() {
                                super.onStop();
                                Logger.e(TAG,"on stop.........");
                        }
                },null);

                mediaProject.createVirtualDisplay("test", surfaceView.getWidth(), surfaceView.getHeight(), getDensity(),
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, surfaceHolder.getSurface(), new VirtualDisplay.Callback() {
                                @Override
                                public void onPaused() {
                                        super.onPaused();
                                        Logger.e(TAG,"on onPaused.........");
                                }

                                @Override
                                public void onResumed() {
                                        super.onResumed();
                                        Logger.e(TAG,"on onResumed.........");
                                }

                                @Override
                                public void onStopped() {
                                        super.onStopped();
                                        Logger.e(TAG,"onStopped......");
                                }
                        },null);
        }


        private int getDensity(){
                DisplayMetrics displayMestric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMestric);
                return (int) displayMestric.density;
        }

        private void startRecordPicture(){

        }

        private void startRecordVideo(){

        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
                mediaProject.stop();
        }
}
