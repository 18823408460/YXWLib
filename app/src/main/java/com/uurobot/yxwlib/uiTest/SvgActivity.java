package com.uurobot.yxwlib.uiTest;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/10.
 */

public class SvgActivity extends Activity {

        @BindView(R.id.image_svg)
        ImageView imageSvg;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_svg);
                ButterKnife.bind(this);

                Resources resources = getResources();
                Resources.Theme theme = getTheme();
                VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(resources, R.drawable.vector_drawable, theme);
                imageSvg.setBackground(vectorDrawableCompat);
        }
}
