package com.uurobot.yxwlib.uiTest.drawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by WEI on 2018/4/7.
 */

public class DrawableActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = new ImageView(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon);

        CircleDrawable circleDrawable = new CircleDrawable(bitmap);
        imageView.setBackground(circleDrawable);
    }
}
