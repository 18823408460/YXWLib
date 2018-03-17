package com.uurobot.yxwlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/11/14.
 */

public class TouchActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Button button = new android.support.v7.widget.AppCompatButton(this){
                        @Override
                        public boolean onTouchEvent(MotionEvent event) {
                                //222222
                                return super.onTouchEvent(event);
                        }
                };
                button.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                                //11111
                                return false;
                        }

                });

                button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                //33333
                        }
                });


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                return super.onTouchEvent(event);
        }


}
