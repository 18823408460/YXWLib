package com.uurobot.yxwlib.uiTest.draw;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/4/10.
 */
//  怎么判断二进制111111111是255还是-1 : 你把它解释成无符号数，他就是255，解释成有符号数，他就是-1
public class DrawPathActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.draw_path);
        }
}
