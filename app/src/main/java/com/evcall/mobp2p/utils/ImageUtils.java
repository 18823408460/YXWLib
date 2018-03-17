package com.evcall.mobp2p.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/15.
 */
public class ImageUtils {

    /**
     * 图片转为base64
     */
    public static String imgToBase64(String path) {
        if (path == null) {
            return "";
        }
        File file = new File(path);
        if (file.exists()) {
            try {

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                String string = null;

                ByteArrayOutputStream bStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);

                byte[] bytes = bStream.toByteArray();

                string = Base64.encodeToString(bytes, Base64.DEFAULT);

                return string;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 图片转为base64
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, 0,
                        bitmapBytes.length, Base64.DEFAULT);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为图片
     */
    public static Bitmap getBitmap(String iconBase64) {
        byte[] bitmapArray;
        bitmapArray = Base64.decode(iconBase64, 0);
        return BitmapFactory
                .decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * base64转换为图片
     */
    public static void base64ToBitmap(String base64Data, String imgName,
                                      String imgFormat) throws FileNotFoundException {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        File myCaptureFile = new File(Environment.getExternalStorageDirectory().getPath(), imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myCaptureFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean isTu = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if (isTu) {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将图片进行翻转
     * @param info
     * @param bitmap
     * @return
     */
    public static  Bitmap roatBitmaoByDegree(Camera.CameraInfo info,Bitmap bitmap) {
        Bitmap returnBitmap = null;
        Matrix matrix = new Matrix();
        try {
            int bWidth=bitmap.getWidth();
            int bHeight=bitmap.getHeight();
            // 镜像水平翻转
            int potate;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                if(bWidth>bHeight){
                    potate=270;
                }else{
                    potate=180;
                }
                matrix.postRotate(potate);
            }
            returnBitmap = Bitmap.createBitmap(bitmap, 0, 0, bWidth, bHeight, matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBitmap == null) {
            returnBitmap = bitmap;
        }
        if (bitmap != returnBitmap) {
            bitmap.recycle();
        }
        return returnBitmap;
    }
}
