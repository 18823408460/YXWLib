package com.uurobot.yxwlib.percent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/16.
 */

public class PercentActivity extends Activity {
        @BindView(R.id.left)
        TextView textLeft ;
        @BindView(R.id.right)
        TextView textRight ;
        @BindView(R.id.click)
        Button button;
        @BindView(R.id.img)
        ImageView imageView ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
//                setContentView(R.layout.activity_percent);

                setContentView(R.layout.activity_framlayout);
                ButterKnife.bind(this);
                textLeft.setText("hello left");
                textRight.setText("hello textRight");
        }
        @OnClick(R.id.click)
        public void tt(){
                button.setText("click....");

                Glide.with(this)
                        .load(R.drawable.icon_lawyer)
                        .asBitmap()
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // 清除缓存
                        .error(R.mipmap.head_bk)
                        .into(imageView);
        }
}
