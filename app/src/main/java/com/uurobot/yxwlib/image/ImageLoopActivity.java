package com.uurobot.yxwlib.image;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.evcall.mobp2p.utils.SizeUtil;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ImageLoopActivity extends Activity {

        @BindView(R.id.rollPagerView)
        RollPagerView rollPagerView;

        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        @BindView(R.id.switch_dot)
        Button switchDot;

        @BindView(R.id.imageshow)
        ImageView imageshow;

        private ColorPointHintView colorPointHintView;

        private RelativeLayout.LayoutParams lp;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_imageloop);
                ButterKnife.bind(this);

//                showView();
//                showIndict();
                  showImage();
        }


        private void showImage() {
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(Color.parseColor("#ff0044"));
                int radio  = 100 ;
                gradientDrawable.setCornerRadius(SizeUtil.dip2px(getBaseContext(),radio));
                gradientDrawable.setSize(SizeUtil.dip2px(getBaseContext(),radio),SizeUtil.dip2px(getBaseContext(),radio));
                imageshow.setBackground(gradientDrawable);
        }

        private void showView() {
                rollPagerView.setPlayDelay(3000);
                rollPagerView.setAdapter(new TestNormalAdapter());
        }

        private void showIndict() {
                colorPointHintView = new ColorPointHintView(getBaseContext(), Color.parseColor("#E3AC42"), Color.parseColor("#000000"));
                colorPointHintView.setPadding(10, 10, 10, 10);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                ((View) colorPointHintView).setLayoutParams(lp);
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.rgb(112, 54, 11));
                gd.setAlpha(100);
                colorPointHintView.setBackgroundDrawable(gd);
//                colorPointHintView.initView(length, 1); //这里可以居中，下面的方式无法无尽中
                colorPointHintView.initView(length, Gravity.CENTER);
                linearLayout.addView(colorPointHintView);
        }

        private int length = 6;

        private int index;

        @OnClick(R.id.switch_dot)
        public void switchDot() {
                colorPointHintView.setCurrent(++index % length);
        }

}
