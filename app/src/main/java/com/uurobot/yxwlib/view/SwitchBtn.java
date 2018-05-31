package com.uurobot.yxwlib.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.uurobot.yxwlib.R;

/**
 * Created by WEI on 2018/5/31.
 */

public class SwitchBtn extends View implements View.OnClickListener {
    public SwitchBtn(Context context) {
        this(context, null);
    }

    public SwitchBtn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SwitchBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(BitmaBk.getWidth(), BitmaBk.getHeight());
    }

    private Bitmap BitmaBk, bimmapSlid;
    private float slidMax;
    private float slidLeft;

    @SuppressLint("ResourceType")
    private void initData() {
        BitmaBk = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.switch_background));
        bimmapSlid = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.slide_button));
        slidMax = BitmaBk.getWidth() - bimmapSlid.getWidth();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(BitmaBk, 0, 0, null);
        canvas.drawBitmap(bimmapSlid, slidLeft, 0, null);
    }

    private boolean isOpen;

    @Override
    public void onClick(View v) {
        if (isClickEnable){
            isOpen = !isOpen;
            if (isOpen) {
                slidLeft = 0;
            } else {
                slidLeft = slidMax;
            }
            postInvalidate();
        }
    }
    // 当 view中既要处理 onclick，还要处理 onTouch 事件，就会存在事件冲突

    private float startX, startXpoint ;
    private boolean isClickEnable ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 这句话能否去掉???? (去掉的话 onClick 事件就接受不到)
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startXpoint =  startX = event.getX();
                isClickEnable = true ;
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float distance = endX - startX;
                slidLeft += distance;
                if (slidLeft <= 0) {
                    slidLeft = 0;
                } else if (slidLeft >= slidMax) {
                    slidLeft = slidMax;
                }
                startX = event.getX();
                postInvalidate();

                if ( Math.abs(endX - startXpoint) > 5){
                    isClickEnable = false ;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isClickEnable){
                    if (slidLeft > (slidMax/2)){
                        isOpen  = false ;
                    }else {
                        isOpen  = true;
                    }
                    if (isOpen) {
                        slidLeft = 0;
                    } else {
                        slidLeft = slidMax;
                    }
                    postInvalidate();
                }
                break;
        }
        return true;
    }
}
