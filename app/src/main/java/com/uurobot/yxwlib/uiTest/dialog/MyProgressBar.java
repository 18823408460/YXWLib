package com.uurobot.yxwlib.uiTest.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.uurobot.yxwlib.alarm.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/4.
 */

/**
 * Physical density: 640 (模拟器的密度是640)
 * <p>
 * 640/160 == 4 ; (160标准的屏幕)
 * <p>
 * <p>
 * xml中配置的dp，最终会根据屏幕密度进行转换成 pix 像素处理。
 */


public class MyProgressBar extends ProgressBar {

        private static final String TAG = "MyProgressBar";

        public MyProgressBar(Context context) {
                this(context, null);
        }

        public MyProgressBar(Context context, AttributeSet attrs) {
                this(context, attrs, -1);
        }

        public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                init();
        }

        private Paint paint; // Paint的 唯一 子类， TextPaint==当绘制纯文本时，可以用这个。

        private TextPaint textPaint;

        private int percent = 0;

        private String text = String.format("%d%%", percent); // %的转义用两个%

        private void init() {
                paint = new Paint();
                paint.setTextSize(60); // 代码中设置的尺寸都是 pix 像素

                paint.setStrokeWidth(100); // 这个设置对字体不起作用，只有在画线条的时候作用
                paint.setColor(Color.parseColor("#ff00ff"));
                TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                                if (percent == 100) {
                                        percent = 0;
                                }
                                percent++;
                                text = String.format("%d%%", percent);
                                Logger.d(TAG, "text====" + text);
                        }

                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 1000, 1000);
        }

        private void init2() {
                textPaint = new TextPaint();
        }

        // FontMetrics字体测量，是 Paint 的一个内部类。。。

        @Override
        protected synchronized void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                int width = getWidth();
                int height = getHeight();
                Logger.e(TAG, "width=" + width);
                Logger.e(TAG, "height=" + height);

                float widthText = paint.measureText(text);
                float heightText = paint.getFontMetrics().ascent + paint.getFontMetrics().descent;

                canvas.drawText(text, width / 2 - widthText / 2, height / 2 - heightText / 2, paint);
        }


        /**
         *   字体的高度 h= getAscent() + getDescent
         *   TextView在绘制文字的高度时，实际文字的绘制高度比h要高，增加了top ,bottom，bottom可以忽略，top在声调时要用到
         *
         *
         *   ------------------------------- top
         *   ------------------------------- Ascent
         *   -------------------------------
         *
         *   ------------------------------- baseline
         *   ------------------------------- descent
         *   ------------------------------- bottom
         */
}
