package com.uurobot.yxwlib.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20.
 *///即 Android 3.0 才开始有 Property Animation 相关的 API

/**
 * 属性动画: AnimatorSet, ValueAnimation, ObjecttAnimation
 * <p>
 * Animator
 * /        \
 * AnimatorSet       ValueAnimation
 * /         \
 * TimeAnimator   ObjecttAnimation
 */
public class PropertyAnimActivity extends Activity {

        private static final String TAG = "PropertyAnimActivity";

        @BindView(R.id.value)
        Button value;

        @BindView(R.id.anim_tv)
        TextView animTv;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_anim);
                ButterKnife.bind(this);

        }


        @OnClick(R.id.value)
        public void onClick(View view) {
//                ValueAnimator valueAnimator = ObjectAnimator.ofInt(viewById, "backgroudColor", 0xffff8080, 0xff8080ff);
//                valueAnimator.setDuration(30000);
//                valueAnimator.setEvaluator(new ArgbEvaluator());
//                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//                valueAnimator.setRepeatCount(ValueAnimator.REVERSE);
//                valueAnimator.start();

//                ObjectAnimator.ofInt(value, "width", 500).setDuration(5000).start();

//                AnimatorInflater.loadAnimator()
                testObjAnim     ();
        }

        private void testValueAnim() {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 200);
                valueAnimator.setDuration(3000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                                Logger.e(TAG, "onAnimationUpdate=" + animation.getAnimatedValue());
                                Integer animatedValue = (Integer) animation.getAnimatedValue();
                                animTv.layout(0 + animatedValue, 100 + animatedValue, animTv.getWidth() + animatedValue, animTv.getHeight() + animatedValue + 100);
                        }
                });
                valueAnimator.setInterpolator(new TimeInterpolator() {
                        @Override
                        public float getInterpolation(float input) { //这里是个百分比
                                Logger.e(TAG, "getInterpolation====" + input);
                                return input;
                        }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {
                                Logger.e(TAG, "...onAnimationStart...");
                        }

                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                                Logger.e(TAG, "...onAnimationEnd...");
                        }
                });
                valueAnimator.start();
        }


        private void testObjectAnim() {
                // rorationY 是 animTv 的一个属性，
                // 在 0-180-0 这个角度中用4s 完成动画。
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(animTv, "rotationY", 0, 180, 0);
                objectAnimator.setDuration(4000);
                objectAnimator.start();
        }

        private void testObjAnim(){
                ViewWrap viewWrap = new ViewWrap(animTv);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewWrap, "width", viewWrap.getWidth(), 800);
                objectAnimator.setDuration(4000);
                objectAnimator.start();
        }

        /**
         * 在视图动画中用AnimationSet可以实现多个动画一起执行，在属性动画中，
         * 针对一个对象的多个属性进行操控，完成类似AnimationSet的效果可以通过PropertyValuesHolder来实现
         *  对于一个属性同时作用多个属性动画效果，可以用PropertyValuesHolder实现，
         *  但是用AnimatorSet不仅能实现这样的效果还可以更加精确的顺序控制
         */
        private void testHolder(){

        }

        //Interpolator这个东西很难进行翻译，直译过来的话是补间器的意思，它的主要作用是可以控制动画的变化速率
        //那么TypeEvaluator的作用到底是什么呢？简单来说，就是告诉动画系统如何从初始值过度到结束值。我们在上一篇文章中学到的
        // ValueAnimator.ofFloat()方法就是实现了初始值与结束值之间的平滑过度，
        // 那么这个平滑过度是怎么做到的呢？其实就是系统内置了一个FloatEvaluator，它通过计算告知动画系统如何从初始值过度到结束值

}
