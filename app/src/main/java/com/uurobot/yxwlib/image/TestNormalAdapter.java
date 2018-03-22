package com.uurobot.yxwlib.image;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/3/22.
 */

public class TestNormalAdapter extends StaticPagerAdapter {
        private static final int[]  images = new int[]{
                R.drawable.cross,
                R.drawable.icon_lawyer,
                R.drawable.riben,
                R.mipmap.ic_launcher
        };
        @Override
        public View getView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setImageResource(images[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


                return imageView;
        }

        @Override
        public int getCount() {
                return images.length;
        }
}
