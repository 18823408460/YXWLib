package com.uurobot.yxwlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;
import static android.widget.RelativeLayout.CENTER_VERTICAL;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ViewChangeActivity extends Activity {

        private Button button;
        private ImageView imageView ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_change);
                button = findViewById(R.id.btn_C);
                imageView = findViewById(R.id.image);
        }

        boolean flag = false ;
        public void onClick(View view){
                if (!flag){
                        imageView.setVisibility(View.GONE);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.addRule(CENTER_IN_PARENT);
                        button.setLayoutParams(layoutParams);
                }else {
                        imageView.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.addRule(ALIGN_PARENT_TOP);
                        button.setLayoutParams(layoutParams);

                }
                flag = !flag ;

        }
}
