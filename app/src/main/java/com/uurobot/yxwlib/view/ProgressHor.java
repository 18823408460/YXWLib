package com.uurobot.yxwlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2017/11/21.
 */

public class ProgressHor  extends ProgressBar{

        private static final String TAG = "ProgressHor";
        private Paint paint ;
        private int unReachColor ;
        private int reachColor ;
        private int textColor ;
        private int  textSize ;

        private int realWidth;

        private int widthPixels;

        private int heightPixels;

        private float radio=100;


        public ProgressHor(Context context) {
                this(context,null);
        }

        public ProgressHor(Context context, AttributeSet attrs) {
                this(context, attrs,0);
        }

        public ProgressHor(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                initData(attrs);
        }

        @Override
        protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                Log.e(TAG, "onMeasure: ................descent="+paint.descent() + "    paint.ascent()="+ paint.ascent());


                if (widthMode == MeasureSpec.EXACTLY){
                        Log.e(TAG, "onMeasure:  exactly" );
                }else{
                        Log.e(TAG, "onMeasure: not exactly" );
                        float textHeight = paint.descent() + paint.ascent() ;
                        int expectHeight = (int) (getPaddingBottom()+getPaddingTop() + Math.abs(textHeight));
                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(expectHeight,MeasureSpec.EXACTLY);
                }
                // 这里会报错
                setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

               realWidth =  getMeasuredWidth() - getPaddingLeft() - getPaddingRight() ;
                Log.e(TAG, "onMeasure: realWidth="+realWidth );
        }


//        private int totalLen = 600 ; // 总长度 = 600
        @Override
        protected synchronized void onDraw(Canvas canvas) {
               // super.onDraw(canvas);
                drawHor(canvas);
                drawCircor(canvas);
        }

        private void drawHor(Canvas canvas) {
                paint.reset();
                paint.setColor(reachColor);
                paint.setStrokeWidth(10);

                int reachlen = (int) (((float)getProgress()/getMax())*realWidth);

                String text = String.format("%s%%",getProgress());
                paint.setTextSize(textSize);
                int textLen = (int) paint.measureText(text);
                int textHeight = (int) ((paint.descent() + paint.ascent())/2);

                canvas.drawLine(0,50,reachlen,50,paint);

                paint.setColor(textColor);
                canvas.drawText(text,reachlen,50-textHeight,paint);

                paint.setColor(unReachColor);
                canvas.drawLine(reachlen+textLen,50,realWidth,50,paint);
        }


        private void drawCircor(Canvas canvas) {
                paint.reset();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(reachColor);

                paint.setStrokeWidth(5);

                int reachlen = (int) (((float)getProgress()/getMax())*realWidth);

                String text = String.format("%s%%",getProgress());
                paint.setTextSize(textSize);
                int textLen = (int) paint.measureText(text);
                int textHeight = (int) ((paint.descent() + paint.ascent())/2);

                canvas.drawCircle(widthPixels/2,heightPixels/2,radio,paint);


                paint.setColor(textColor);
                canvas.drawText(text,widthPixels/2-textLen/2,heightPixels/2-textHeight,paint);


                int angle = (int) (((float)getProgress()/getMax())*360);

                paint.setColor(unReachColor);
                RectF  rectF =  new RectF();
                rectF.left = widthPixels/2-radio ;
                rectF.top = heightPixels/2 -radio ;
                rectF.bottom = heightPixels/2+radio ;
                rectF.right = widthPixels/2+radio ;
                canvas.drawArc(rectF,-90,angle,false, paint);
        }

        private void initData(AttributeSet attrs){
                TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.progressHor);
                int len  = typedArray.getIndexCount();
                Log.e(TAG, "initData:  len = "+len);
                for (int i=0 ; i<len ;i++){
                        int typedArrayIndex = typedArray.getIndex(i);
                        switch (typedArrayIndex){
                                case R.styleable.progressHor_unReachColor:
                                        unReachColor = typedArray.getColor(typedArrayIndex,0x000012);
                                        Log.e(TAG, "initData: unReachColor="+ unReachColor );
                                        break;
                                case R.styleable.progressHor_reachColor:
                                        reachColor = typedArray.getColor(typedArrayIndex,0x000022);
                                        Log.e(TAG, "initData: reachColor="+ reachColor );
                                        break;
                                case R.styleable.progressHor_textColor:
                                        textColor = typedArray.getColor(typedArrayIndex,0x000032);
                                        Log.e(TAG, "initData: textColor="+ textColor );
                                        break;
                                case R.styleable.progressHor_textSize:
                                        textSize =typedArray.getDimensionPixelSize(typedArrayIndex,1);
                                        Log.e(TAG, "initData: textSize="+ textSize );
                                        break;
                        }
                }
                typedArray.recycle();

                paint = new Paint();

                 widthPixels = getResources().getDisplayMetrics().widthPixels;
                 heightPixels = getResources().getDisplayMetrics().heightPixels;
        }


        private int px2sp(int px){
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,px,getContext().getResources().getDisplayMetrics());
        }
        private int px2dp(int px){
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,getContext().getResources().getDisplayMetrics());
        }
}
