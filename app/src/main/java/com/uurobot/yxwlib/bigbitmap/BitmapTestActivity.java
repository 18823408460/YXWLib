package com.uurobot.yxwlib.bigbitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BitmapTestActivity extends Activity {

        private static final String TAG = "BitmapTestActivity";
        private ImageView image ;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_bitmap);

                //initImage();
//                AnimationUtils.la

        }

        private void initImage() {
                image = findViewById(R.id.image);
                try {
                        InputStream inputStream = getAssets().open("qingming.jpg");
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        image.setImageBitmap(bitmap);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        Logger.e(TAG,"IOException =="+e.toString());
                }
        }
}
