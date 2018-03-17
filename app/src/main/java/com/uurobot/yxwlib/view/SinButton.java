package com.uurobot.yxwlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class SinButton extends View {

        private static final String TAG = "SinButton";

        private Paint paint = new Paint();

        public SinButton(Context context) {
                super(context);
        }

        public SinButton(Context context, AttributeSet attrs) {
                super(context, attrs);
        }

        public SinButton(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
        }

        float x = 1.1f;

        float interver = 0.2f;

        private List<PointP> list = new ArrayList<>();

        @Override
        protected void onDraw(Canvas canvas) {
                canvas.save();
                canvas.translate(getWidth()/2,getHeight()/2);
                canvas.rotate(90);
                canvas.translate(-getWidth()/2,-getHeight()/2);
                paint.reset();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(1);
                paint.setAntiAlias(true);
                for (int i = 1; i < getWidth(); i++) {
                        canvas.drawCircle(i, (float) (100 * Math.sin(i * Math.PI / 180) + getHeight() / 2), 1, paint);

                        if (i == getWidth() / 2 || i == getWidth() / 4 || i == getWidth() / 8) {
                                paint.setColor(Color.BLUE);
                                canvas.drawCircle(i, (float) (100 * Math.sin(i * Math.PI / 180) + getHeight() / 2), 50, paint);
                                paint.setColor(Color.BLACK);
                                canvas.drawCircle(i, (float) (100 * Math.sin(i * Math.PI / 180) + getHeight() / 2), 5, paint);
                                list.add(new PointP(i, (float) (100 * Math.sin(i * Math.PI / 180) + getHeight() / 2)));
                        }
                }
                canvas.restore();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                                Log.e(TAG, "onTouchEvent: " + event.getRawX() + "    " + event.getRawY());
                                break;
                        case MotionEvent.ACTION_DOWN:
                                for (int i = 0; i < list.size(); i++) {
                                        float p1 = event.getX();
                                        float p2 = event.getY();
                                        Log.e(TAG, "onTouchEvent: getX=" + p1 + "  getY=" + p2);
                                        Log.e(TAG, "onTouchEve getRawX=" + event.getRawX() + "  getRawY=" + event.getRawY());
                                        float jili = (float) Math.sqrt(Math.abs((p1 - list.get(i).getX())
                                                * (p1 - list.get(i).getX()) + (p2 - list.get(i).getY())
                                                * (p2 - list.get(i).getY())));
                                        if (jili <= 50) {
                                                if (iclickLisner != null) {
                                                        iclickLisner.onClick(i);
                                                }
                                        }
                                }
                                break;
                }
                return super.onTouchEvent(event);
        }

        private float px2dp(float px) {
                return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
        }


        public void setClickLisener(IClickLisener i) {
                this.iclickLisner = i;
        }


        IClickLisener iclickLisner;

        public interface IClickLisener {

                void onClick(int index);
        }
}
