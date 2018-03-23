package com.uurobot.yxwlib.uiTest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class DialogFragmentTest extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.dialog_test);




        }

        public void onClick(View view) {
                MyDialog myDialog = new MyDialog();

                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                Window window = getDialog().getWindow();
//                window.setGravity(Gravity.BOTTOM);
//                window.setWindowAnimations(R.style.buttomDailog);
                myDialog.show(fragmentManager,"");
        }

        @Override
        protected void onPause() {
                super.onPause();

        }

        public static class MyDialog extends DialogFragment{
                public MyDialog() {

                }

                @Nullable
                @Override
                public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
                        View inflate = inflater.inflate(R.layout.dialog_fragment, container, false);
                        Button button = inflate.findViewById(R.id.button4);
                        button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        getDialog().dismiss();
                                }
                        });
                        startDownAnimation(inflate);
                        return inflate;
                }
        }

        private static  void startDownAnimation(View view) {
                Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

                slide.setDuration(400);
                slide.setFillAfter(true);
                slide.setFillEnabled(true);
                slide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                });
                view.startAnimation(slide);
        }

}



