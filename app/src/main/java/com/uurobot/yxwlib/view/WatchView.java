package com.uurobot.yxwlib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2017/11/22.
 */

public class WatchView extends View {

        private static final String TAG = "WatchView";

        private Paint paint;

        private int width;

        private int height;

        private int radio;

        private int centerX;

        private int centerY;

        private int angle = 0;

        private int hourInterver = 50;

        private int minuteInterver = 70;

        private int secondInterver = 90;

        private int DEFAULT_LONG_DEGREE_LENGTH = 15;

        private int DEFAULT_SHORT_DEGREE_LENGTH = 11;

        private int muniteAngle;

        public WatchView(Context context) {
                this(context, null);
        }

        public WatchView(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs, 0);
        }

        public WatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                initData();
        }

        private void initData() {
                paint = new Paint();

                width = getResources().getDisplayMetrics().widthPixels;
                height = getResources().getDisplayMetrics().heightPixels;
                radio = width / 4;

                centerX = width / 2;
                centerY = height / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
                paint.reset();

                // 绘制外圆
                paint.setColor(Color.RED);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                canvas.drawCircle(centerX, centerY, radio, paint);

                //绘制中心点
                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(15);
                canvas.drawPoint(centerX, centerY, paint);

                //绘制时针
                paint.setColor(Color.GRAY);
                paint.setStrokeWidth(3);
                int hourRadio = radio - hourInterver;
                float hourX = (float) (hourRadio * Math.sin(angle * Math.PI / 180));
                float hourY = (float) (hourRadio * Math.cos(angle * Math.PI / 180));
                angle += 6;
                canvas.drawLine(centerX, centerY, centerX + hourX, centerY - hourY, paint);

                //绘制分针
                paint.setColor(0xffff9922);
                paint.setStrokeWidth(5);
                int muniteRadio = radio - minuteInterver;
                float muniteRadioX = (float) (muniteRadio * Math.sin(muniteAngle * Math.PI / 180));
                float muniteRadioY = (float) (muniteRadio * Math.cos(muniteAngle * Math.PI / 180));
                canvas.drawLine(centerX, centerY, centerX + muniteRadioX, centerY - muniteRadioY, paint);
                if (angle>=360){
                        if (angle%360==0){
                                muniteAngle+=6;
                        }
                }

                paint.setAntiAlias(true);
                int degreeLength;
                for (int i = 0; i < 60; i++) {
                        if (i % 5 == 0) {
                                paint.setColor(Color.RED);
                                paint.setStrokeWidth(6);
                                degreeLength = DEFAULT_LONG_DEGREE_LENGTH;
                        }
                        else {
                                paint.setColor(Color.BLUE);
                                paint.setStrokeWidth(3);
                                degreeLength = DEFAULT_SHORT_DEGREE_LENGTH;
                        }
                        canvas.drawLine(centerX, centerY - radio, centerX, centerY - radio + degreeLength, paint);
                        canvas.rotate(360 / 60, centerX, centerY);
                }
                canvas.restore();


                int initTime = 12;
                for (int i = 0; i < 12; i++) {
                        paint.setColor(Color.RED);
                        paint.setStrokeWidth(1);
                        if (initTime > 12) {
                                initTime = 1;
                        }
                        float textWidth = paint.measureText(String.valueOf(initTime));
                        canvas.drawText(String.valueOf(initTime), centerX - textWidth / 2, centerY - radio + 30, paint);
                        canvas.rotate(360 / 12, centerX, centerY);
                        initTime++ ;

                }
                canvas.restore();

               // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cross);

               // canvas.drawBitmap(bitmap,centerX-bitmap.getWidth()/2,centerY-bitmap.getHeight()/2,paint);

        }


}
