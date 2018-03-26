package com.uurobot.yxwlib.bulr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2017/11/20.
 */

public class BlurActivity extends Activity {
        Button button ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_blur);
                button = (Button) findViewById(R.id.onClick);
        }

        public void onClick(View v){
//                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("你好").setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                        }
//                }).create();
//
//                Window window = alertDialog.getWindow();
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                window.setAttributes(attributes);
//                window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                alertDialog.show();

                new BlurPopWin.Builder(BlurActivity.this).setContent("helollllllll")
                        //Radius越大耗时越长,被图片处理图像越模糊
                        .setRadius(19).setTitle("我是标题")
                        //设置居中还是底部显示
                        .setshowAtLocationType(0)
                        .setShowCloseButton(true)
                        .setOutSideClickable(false)
                        /*.onClick(new BlurPopWin.PopupCallback() {
                            @Override
                            public void onClick(@NonNull BlurPopWin blurPopWin) {
                                Toast.makeText(LiveTvMainActivity.this, "中间被点了", Toast.LENGTH_SHORT).show();
                                blurPopWin.dismiss();
                            }
                        })*/.show(button);

        }
}
