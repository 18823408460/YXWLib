package com.uurobot.yxwlib.camera;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;


import com.uurobot.yxwlib.alarm.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by zmp on 2017/7/20.
 */

public abstract class CameraBaseActivity extends Activity {

        private String TAG = "CameraBaseActivity";

        private Camera mCamera;

        private Camera.PreviewCallback cb;

        protected boolean initCamera(SurfaceHolder holder) {
                try {
                        this.mCamera = Camera.open(0);
                        this.mCamera.setPreviewDisplay(holder);
                        return true;
                }
                catch (Exception ioe) {
                        ioe.printStackTrace(System.out);
                        if (mCamera != null) {
                                mCamera.release();
                                mCamera = null;
                        }
                        Logger.e(TAG, "初始化摄像头失败");
                        return false;
                }
        }

        protected void openCamera() {
                if (mCamera != null) {
                        try {
                                Camera.Parameters parameters = mCamera.getParameters();
                                parameters.setPreviewSize(640,480);// 设置预览照片的大小
                                parameters.setPictureSize(640,480);

                                parameters.setPreviewFrameRate(30);// 设置每秒30帧

                                List<Integer> frameRates = parameters.getSupportedPreviewFrameRates();
                                for (int i : frameRates) {
                                        Logger.d(TAG, "支持的预览帧数:" + i);
                                }
                                // 实现自动对焦
                                List<String> focusModes = parameters.getSupportedFocusModes();
                                if (focusModes.contains("continuous-video")) {
                                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                                }
                                if (focusModes.contains("continuous-picture")) {
                                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                                }
                                if (focusModes.contains("torch")) {
                                        parameters.setFocusMode(Camera.Parameters.FLASH_MODE_TORCH);
                                }
                                parameters.setPictureFormat(ImageFormat.JPEG);// 设置照片的格式（只支持JPEG）
                                parameters.setPreviewFormat(ImageFormat.NV21);// (支持NV21、YV12)
                                parameters.setJpegQuality(100);// 设置照片的质量
                                mCamera.setDisplayOrientation(90);
                                mCamera.setParameters(parameters);
                                cb = new Camera.PreviewCallback() {
                                        @Override
                                        public void onPreviewFrame(byte[] data, Camera camera) {
                                                mCamera.setOneShotPreviewCallback(this);
                                                onPreviewCallback(data);
                                        }
                                };
                                mCamera.setOneShotPreviewCallback(cb);
                                mCamera.startPreview();
                                mCamera.cancelAutoFocus();
                                //连续自动对焦
                                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                                        @Override
                                        public void onAutoFocus(boolean success, Camera camera) {
//                                                Logger.e(TAG, "onAutoFocus_success：" + success);
                                                if (success) {
                                                        camera.cancelAutoFocus();
                                                        mCamera.autoFocus(this);
                                                }
                                        }
                                });
                                Logger.e(TAG, "初始化摄像头成功");
                        }
                        catch (Exception e) {
                                e.printStackTrace();
                                Logger.e(TAG, "初始化摄像头失败");
                        }
                }
        }

        /**
         * 释放摄像头
         */
        protected synchronized void recycleCamera() {
                Logger.e(TAG, "释放摄像头！recycleCamera");
                if (mCamera != null) {
                        try {
                                mCamera.setPreviewDisplay(null);
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                        }
                        mCamera.setOneShotPreviewCallback(null);
                        mCamera.stopPreview();
                        mCamera.release();
                        mCamera = null;
                }
        }

        /**
         * 摄像头数据预览回调
         *
         * @param data
         */
        protected abstract void onPreviewCallback(byte[] data);

        protected void stopPreview() {
                if (mCamera != null) {
                        mCamera.stopPreview();
                }
        }

        protected void startPreview() {
                if (mCamera != null) {
                        mCamera.startPreview();
                        mCamera.setOneShotPreviewCallback(cb);
                }
        }


        protected interface ITakePhotoCallBack {

                void callBack(String photoName);
        }
}
