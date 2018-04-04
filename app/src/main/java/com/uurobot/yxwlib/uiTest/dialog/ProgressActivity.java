package com.uurobot.yxwlib.uiTest.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/4.
 */

public class ProgressActivity extends Activity {
        //在API level 26 中，ProgressDialog被声明不赞成使用，应使用的替代方法是ProgressBar
        //ProgressDialog progressDialog ;


        @BindView(R.id.button9)
        Button show;

        @BindView(R.id.button10)
        Button cancel;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_progresstest);
                ButterKnife.bind(this);

        }

        @OnClick(R.id.button9)
        public void show() {
                progressBar.setVisibility(View.VISIBLE);

//                progressBar.showContextMenu()

        }

        @OnClick(R.id.button10)
        public void cancel() {
                progressBar.setVisibility(View.INVISIBLE);
        }
}
