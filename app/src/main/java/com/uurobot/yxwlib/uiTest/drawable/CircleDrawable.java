package com.uurobot.yxwlib.uiTest.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by WEI on 2018/4/7.
 */

/** 1.BitmapShader
 *  > 画笔 paint 在绘制东西时，需要为其设置色彩，如果是单纯的颜色 --- setColor，
 *    而如果是复杂的图片，则需要通过着色器 shader 来添加纹理。。。
 *
 *  2.ColorFilter
 *  > 滤镜--- 对物体的色彩，饱和度，亮度，等的处理。。 涉及到颜色矩阵。。
 *
 *
 * 3 getOpacity  --- PixelFormat.TRANSLUCENT
 */
public class CircleDrawable extends Drawable {
    private int radio;// 半径
    private Paint paint;
    private float x;
    private float y;

    public  CircleDrawable(Bitmap bitmap) {
        paint = new Paint();
        paint.setAntiAlias(true);

        BitmapShader shape = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shape);

        radio = Math.min(bitmap.getWidth(), bitmap.getHeight());

        x = bitmap.getWidth() / 2;
        y = bitmap.getHeight() / 2;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(x, y, radio, paint);
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
