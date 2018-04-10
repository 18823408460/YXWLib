package com.uurobot.yxwlib.uiTest.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/4/10.
 */

public class DrawPathView extends View {

        private Paint paint;

        private String text = "你好是的发送到发送到你好是的发送到发送到你好是的发送到发送到你好是的发送到发送到你好是的发送到发送到";

        public DrawPathView(Context context) {
                this(context, null);
        }

        public DrawPathView(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs, -1);
        }

        public DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                initData();
        }

        private void initData() {
                paint = new Paint();
                paint.setColor(Color.parseColor("#ff0044"));
                paint.setStrokeWidth(12);
                Path mFontPath = new Path();
                paint.getTextPath(text, 0, text.length(), 0, paint.getTextSize(), mFontPath);

                PathMeasure pathMeasure = new PathMeasure();
                pathMeasure.setPath(mFontPath, false);
                float length = pathMeasure.getLength();
                while (pathMeasure.nextContour()) {
                        length += pathMeasure.getLength();
                }
        }


        @Override
        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Path mPath = new Path();
                mPath.addCircle(110, 110, 45, Path.Direction.CCW);
                mPath.addCircle(210, 210, 45, Path.Direction.CCW);
                mPath.addCircle(320, 320, 45, Path.Direction.CCW);
                canvas.drawPath(mPath, paint);


                Path path = new Path();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                path.addRect(300.0f, 500.0f, 1200.0f, 1400.0f, Path.Direction.CW);

                canvas.drawPath(path, paint);
                paint.setTextSize(100);
                paint.setColor(Color.BLUE);

                // hOffet，文字距离路径的距离，如果文字过多，会在path的末尾叠加绘制
                canvas.drawTextOnPath(text, path, 0, 20, paint);


                // ?? Picture 的使用场景：（联想camera中takePicture（））
                // 1. 首先将要绘制的东西录制到Picture中(在view 的构造中)，（必须关闭硬件加速）
                // 2. 将Picture中的东西绘制出来，（绘制的3中方式对比： Picture.draw(), Canvas.drawPicture(), PictuenDrawable--Canvas.draw(drawable)）
//                canvas.drawPicture();
        }
}
