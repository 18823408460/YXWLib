package com.uurobot.yxwlib.hotfix;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/23.
 */

public class SimpleHotFixBugTest {
        public void getBug(Context context) {
                int i = 10;
                int a = 0;
                Toast.makeText(context, "Hello,I am CSDN_LQR:" + i / a, Toast.LENGTH_SHORT).show();
        }
}
