package com.uurobot.yxwlib.anim;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/20.
 */

public class AnimActivity extends Activity {

        Button viewById;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_anim);
                viewById = findViewById(R.id.value);
        }


        public void onClick(View view) {
                Looper.prepare();
//                ValueAnimator valueAnimator = ObjectAnimator.ofInt(viewById, "backgroudColor", 0xffff8080, 0xff8080ff);
//                valueAnimator.setDuration(30000);
//                valueAnimator.setEvaluator(new ArgbEvaluator());
//                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//                valueAnimator.setRepeatCount(ValueAnimator.REVERSE);
//                valueAnimator.start();
                ObjectAnimator.ofInt(viewById,"width",500).setDuration(5000).start();
        }

}
