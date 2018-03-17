package com.uurobot.yxwlib.bigbitmap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/13.
 */

public class LargeView extends View {


        public LargeView(Context context) {
                this(context,null);
        }

        public LargeView(Context context, @Nullable AttributeSet attrs) {
                this(context, attrs,0);
        }

        public LargeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
        }

        private void init(){

        }

}
