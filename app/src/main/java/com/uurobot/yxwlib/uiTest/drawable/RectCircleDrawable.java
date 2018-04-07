package com.uurobot.yxwlib.uiTest.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by WEI on 2018/4/7.
 */

/** 1.setBounds
 *
 *
 *
 *
 *
 *
 */
public class RectCircleDrawable extends Drawable {
    private Paint paint;
    private float x; // 中心点
    private float y; // 中心点
    private Rect rect ;
    private int radio ;

    public RectCircleDrawable(Bitmap bitmap) {
        paint = new Paint();
        paint.setAntiAlias(true);
        BitmapShader shape = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shape);
        x = bitmap.getWidth() / 2;
        y = bitmap.getHeight() / 2;
       radio = 30 ;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.drawCircle(x, y, radio, paint);
//        canvas.drawRoundRect(); 画圆角矩形
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
