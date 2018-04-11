package com.uurobot.yxwlib.uiTest.draw;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.util.SysUtil;

/**
 * Created by Administrator on 2018/4/11.
 */

/**
 * PathMeasure api 的使用
 */
public class PathMeasureView extends View {

        private static final String TAG = "PathMeasureView";

        private String text = "abc";

        private Paint paint;

        private Context context;

        private Path path;

        private PathMeasure pathMeasure;

        private float animatedValue;

        private Path mDestPath;

        public PathMeasureView(Context context) {
                this(context, null);
        }

        public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs, -1);
        }

        public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                this.context = context;
                initDrawData();
                initAnimData();
//                initTextPath();
        }

        private void initAnimData() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                                animatedValue = (float) animation.getAnimatedValue();
                                invalidate();
//                                Logger.e(TAG, "animatedValue = " + animatedValue);
                        }
                });
                valueAnimator.setDuration(3000);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.start();
        }

        private Path mTextPath;

        private void initTextPath() {
                mTextPath = new Path();
                paint.getTextPath(text, 0, text.length(), SysUtil.getScreenWidthPix() / 2, SysUtil.getScreenHeightPix() / 2, mTextPath);
        }


        private void initDrawData() {
                paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                paint.setAntiAlias(true);
                paint.setTextSize(300);
                paint.setColor(Color.parseColor("#ff0000"));

                path = new Path();
                path.addCircle(SysUtil.getScreenWidthPix() / 2, SysUtil.getScreenHeightPix() / 2, 400, Path.Direction.CW);

                pathMeasure = new PathMeasure();
                mTextPath = new Path();
                paint.getTextPath(text, 0, text.length(), SysUtil.getScreenWidthPix() / 2, SysUtil.getScreenHeightPix() / 2, mTextPath);
                pathMeasure.setPath(mTextPath, false);

                length = pathMeasure.getLength(); // 这仅仅是一个封闭曲线的path长度（一个汉字可能有很多的path）。。。
                int count = 0 ;
                while (pathMeasure.nextContour()){
                       Logger.e(TAG, "--------nextContour------------"+count++);
                        length += pathMeasure.getLength();
                }
                Logger.e(TAG, "--------nextContour-----length-------"+length);
                mDestPath = new Path();
        }

        float length ;
        @Override
        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Logger.e(TAG, "--------ondraw------------");

                mDestPath.reset();
                //硬件加速的bug
                mDestPath.lineTo(0, 0);

                float stop = animatedValue * length;
                Logger.e(TAG, "length==" + length + "   stop=========" + stop);

                float start = (float) (stop - ((0.5 - Math.abs(animatedValue - 0.5)) * length));
                pathMeasure.getSegment(0, stop, mDestPath, true);

                canvas.drawPath(mDestPath, paint);
        }
}
