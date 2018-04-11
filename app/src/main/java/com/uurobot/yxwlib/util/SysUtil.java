package com.uurobot.yxwlib.util;

import android.content.res.Resources;

import com.uurobot.yxwlib.MainApplication;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SysUtil {
        public static int getScreenWidthPix(){
                Resources resources = MainApplication.getContext().getResources();
                return resources.getDisplayMetrics().widthPixels;
        }

        public static int getScreenHeightPix(){
                Resources resources = MainApplication.getContext().getResources();
                return resources.getDisplayMetrics().heightPixels;
        }
}
