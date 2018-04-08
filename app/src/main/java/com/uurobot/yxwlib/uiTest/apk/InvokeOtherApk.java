package com.uurobot.yxwlib.uiTest.apk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/8.
 */

public class InvokeOtherApk extends Activity {

        @BindView(R.id.button14)
        Button button14;

        @BindView(R.id.button15)
        Button button15;

        @OnClick(R.id.button14)
        public void invoke1() {
                Intent intent = new Intent();

                //setClassName ==== mComponent = new ComponentName(packageName, className);
                intent.setClassName("com.uurobot.launcher", "com.uurobot.launcher.MainActivity");
                startActivity(intent);
        }

        @OnClick(R.id.button15)
        public void invoke2() {
                ComponentName componentName = new ComponentName("com.uurobot.launcher", "com.uurobot.launcher.MainActivity");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);

        }


        // 上面两种方式是一样。。。


        @OnClick(R.id.button16)
        public void invoke3() {
                Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.uurobot.launcher");
                startActivity(launchIntentForPackage);

        }


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_invokeapk);
                ButterKnife.bind(this);
        }
}
