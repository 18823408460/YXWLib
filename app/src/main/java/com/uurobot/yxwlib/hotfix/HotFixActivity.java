package com.uurobot.yxwlib.hotfix;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/2/23.
 */

public class HotFixActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.hotfix);
        }

        public void fix(View view) {
                FixDexUtils.loadFixedDex(this, Environment.getExternalStorageDirectory());
        }


        public void calc(View view) {
                 SimpleHotFixBugTest simpleHotFixBugTest = new SimpleHotFixBugTest();
                 simpleHotFixBugTest.getBug(this);
        }
}
