package com.uurobot.yxwlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/14.
 */

public class TtActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

               setContentView(R.layout.activity_tt);

               findViewById(R.id.msg).setVisibility(View.GONE);
               findViewById(R.id.left).setVisibility(View.GONE);
               findViewById(R.id.right).setVisibility(View.GONE);

                TextView viewById = findViewById(R.id.tt);
                viewById.setVisibility(View.VISIBLE);
                viewById.setText("ss");
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
                return super.onTouchEvent(event);
        }


}
