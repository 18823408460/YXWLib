package com.uurobot.yxwlib.anim;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.uurobot.yxwlib.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WEI on 2018/4/7.
 */

/**
 * 补间动画。。
 * <p>
 * 1. 纯代码怎么使用
 * 2. xml中怎么使用
 */
public class TweenAnimActivity extends Activity {

        @BindView(R.id.btn_anim_start)
        Button btnAnimStart;

        @BindView(R.id.image_anim)
        ImageView imageAnim;

        @OnClick(R.id.btn_anim_start)
        public void start() {
                testTranslate();
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_tween);
                ButterKnife.bind(this);
        }


        private void testScaleAnim() {
//                ScaleAnimation scaleAnimation = new ScaleAnimation()
                Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
                imageAnim.startAnimation(scaleAnimation);
        }


        private void testTranslate() {
                TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_translate);
//                imageAnim.setAnimation(animation);
//                animation.start();
                imageAnim.startAnimation(animation);
        }


}
