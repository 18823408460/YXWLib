package com.uurobot.yxwlib.uiTest.ScrollLayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/4/4.
 */

/**
 *  ScrollView 的使用注意点：
 *  1， 只能作为最外层的Layout
 *  2， 里面只能包含一个控件，一般是LinearLayout（Vertical）

 *
 */
public class ScrollViewAcitivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_scrolllayout);
        }
}
