package com.uurobot.yxwlib.bigbitmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/13.
 */

public class CircleView extends View {

        private static final String TAG = "CircleView";
        private int color ;
        private Paint paint ;

        private int screenHeight;
        private int screenWidth;

        public CircleView(Context context) {
                this(context,null);
        }

        public CircleView(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs,0);
        }

        public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                init();
                getData();
        }

        private void init(){
                color = Color.RED;
                paint = new Paint();
                paint.setColor(color);

        }
        private void getData(){
                DisplayMetrics dm =  getResources().getDisplayMetrics();
                Logger.e(TAG,"density = "+dm.density);
                Logger.e(TAG,"densityDpi = "+dm.densityDpi);
                Logger.e(TAG,"widthPixels = "+dm.widthPixels);
                Logger.e(TAG,"heightPixels = "+dm.heightPixels);
                screenWidth = dm.widthPixels;
                screenHeight = dm.heightPixels;
        }

        @Override
        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);


                // 这两个值由布局中定义来决定的。。
                int width = getWidth();
                int height = getHeight() ;

                int radio = Math.min(width,height)/2 ;

                canvas.drawCircle(width/2,height/2,radio,paint);
        }
}
