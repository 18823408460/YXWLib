package com.uurobot.yxwlib.uiTest.dialog;

import android.app.Activity;
import android.app.Dialog;
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
                dialog = new Dialog(this,R.style.mydialog_progress);
        }

        // dialog 默认就是居中显示，布局中写不写无所谓。。android:gravity="center" android_layoutGravity;
        // 子控件要居中，需要子控件自己设置相关属性。

        /**
         *   android:layout_width="wrap_content" match_content 不起作用
         *   android:layout_height="wrap_content"
         */
        private Dialog dialog ;
        @OnClick(R.id.button9)
        public void show() {
                // progressBar作为一个view，不是dialog，所以必须add到父布局中，然后通过显示隐藏的方法处理。
//                progressBar = new ProgressBar(this);
//                progressBar.setVisibility(View.VISIBLE);
                dialog.setContentView(R.layout.dialog_progressbar);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                // dialog 可以直接显示，是因为里面直接用了 WindowManager
        }

        @OnClick(R.id.button10)
        public void cancel() {
//                progressBar.setVisibility(View.INVISIBLE);
                dialog.cancel();
        }
}
