package com.uurobot.yxwlib.anim;

import android.view.View;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ViewWrap {

        private View view;

        public ViewWrap(View view) {
                this.view = view;
        }

        public void setWidth(int width) {
                view.getLayoutParams().width = width;
                view.requestLayout();
        }

        public int getWidth() {
                return view.getLayoutParams().width;
        }
}
