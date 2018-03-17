package com.uurobot.yxwlib.view;

import android.animation.FloatArrayEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/22.
 */

public class AirClear extends View {
        private Paint paint ;

        private float centerX;
        private float centerY;

        private float radio;

        public AirClear(Context context) {
                this(context,null);
        }

        public AirClear(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs,0);
        }

        public AirClear(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                initData();
        }

        private void initData(){
                paint = new Paint();
                int widthPixels = getResources().getDisplayMetrics().widthPixels;
                int heightPixels = getResources().getDisplayMetrics().heightPixels;
                centerX = widthPixels/2 ;
                centerY = heightPixels/2 ;
                radio = widthPixels/4;
        }

        @Override
        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                //绘制外圆
                paint.setColor(Color.RED);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(centerX,centerY,radio,paint);


                float []datas = {(float) (paint.getStrokeWidth()*0.4),paint.getStrokeWidth()};
                DashPathEffect dashPathEffect = new DashPathEffect(datas,0);

        }
}
