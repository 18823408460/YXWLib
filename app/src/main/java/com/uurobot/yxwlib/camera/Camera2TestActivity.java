package com.uurobot.yxwlib.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/11/13.
 */

public class Camera2TestActivity extends Activity {

        private static final String TAG = "Camera2TestActivity";

        private TextureView textureView;

        private CameraDevice cameraDevice;

        private CameraCaptureSession cameraCaptureSession;

        private CameraManager cameraManager;

        private CaptureRequest.Builder prevBuild;

        private ImageReader imageReader;
        private SurfaceView surfaceView ;
        private SurfaceHolder surfaceHolder ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_camera);
//                textureView = (TextureView) findViewById(R.id.texture);

                surfaceView  = findViewById(R.id.surfaceView);
                surfaceHolder = surfaceView.getHolder() ;
                surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {
                                Logger.e(TAG,"surfaceCreated");
                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                Logger.e(TAG,"surfaceChanged");
                                initCamera();
                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {
                                Logger.e(TAG,"surfaceDestroyed");
                        }
                });

                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                        @Override
                        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                                Logger.e(TAG, "onSurfaceTextureAvailable");
//                                initCamera();
                        }

                        @Override
                        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                                Logger.e(TAG, "onSurfaceTextureSizeChanged");
                        }

                        @Override
                        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                                Logger.e(TAG, "onSurfaceTextureDestroyed");
                                return false;
                        }

                        @Override
                        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                                Logger.e(TAG, "onSurfaceTextureUpdated");
                        }
                });
        }

        private void initCamera() {
                cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                int cameraID = CameraCharacteristics.LENS_FACING_FRONT;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Logger.e(TAG,"....checkSelfPermission........");
                        return;
                }
                try {
                        cameraManager.openCamera(String.valueOf(cameraID), stateCallback, null);
                }
                catch (CameraAccessException e) {
                        Logger.e(TAG,"CameraAccessException...."+e.toString());
                        e.printStackTrace();
                }
        }


        private void startPrev(){
                SurfaceTexture surfaceTexture =textureView.getSurfaceTexture() ;
                surfaceTexture.setDefaultBufferSize(textureView.getWidth(),textureView.getHeight());


                Surface surface1 = new Surface(surfaceTexture);
                Surface prevSurface = surfaceHolder.getSurface();
                try {
                        prevBuild = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                }
                catch (CameraAccessException e) {
                        e.printStackTrace();
                        Logger.e(TAG,"CameraAccessException="+e.toString());
                }

                setImageReader();

                Surface imageReaderSurface = imageReader.getSurface() ;
//                prevBuild.addTarget(imageReaderSurface);
                prevBuild.addTarget(prevSurface);
                prevBuild.addTarget(surface1);
                try {
                        cameraDevice.createCaptureSession(Arrays.asList(prevSurface,surface1),sessionCallback,null);
                }
                catch (CameraAccessException e) {
                        Logger.e(TAG,"CameraAccessException="+e.toString());
                        e.printStackTrace();
                }
        }


        private CameraCaptureSession.CaptureCallback  captureCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        Logger.e(TAG,"onCaptureCompleted");
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                        super.onCaptureFailed(session, request, failure);
                        Logger.e(TAG,"onCaptureFailed===");
                }
        };

        private CameraCaptureSession.StateCallback sessionCallback = new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                        Logger.e(TAG,"onConfigured");

                        try {
                                session.setRepeatingRequest(prevBuild.build(),captureCallback,null);
                        }
                        catch (CameraAccessException e) {
                                e.printStackTrace();
                                Logger.e(TAG,"www  CameraAccessException="+e.toString());
                        }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                        Logger.e(TAG,"onConfigureFailed");
                }
        };

        private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                        Logger.e(TAG,"onOpened");
                        cameraDevice = camera ;
                        startPrev();
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                        Logger.e(TAG,"onDisconnected");
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                        Logger.e(TAG,"onError");
                }
        };

        private void setImageReader(){
                 imageReader = ImageReader.newInstance(textureView.getWidth(),textureView.getHeight(), ImageFormat.JPEG, 50);
                imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        @Override
                        public void onImageAvailable(ImageReader reader) {
                              // reader.acquireLatestImage();
                               // reader.close();
                                Logger.e(TAG,"........onImageAvailable...........");
                        }
                },null);
        }
}
