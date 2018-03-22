package com.uurobot.yxwlib.uiTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.car.BaseActivity;
import com.zqg.dialogviewpager.DepthPageTransformer;
import com.zqg.dialogviewpager.StepDialog;
import com.zqg.dialogviewpager.ZoomOutPageTransformer;

/**
 * Created by Administrator on 2018/3/22.
 */

public class FirstGuideActivity extends AppCompatActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
               // setContentView(R.layout.activity_firstguide);

                StepDialog.getInstance().setImages(new int[]{R.drawable.riben,R.drawable.icon_lawyer,R.mipmap.img_advert})
//                        .setPageTransformer(new ZoomOutPageTransformer())
                        .setPageTransformer(new DepthPageTransformer())
                        .show(getFragmentManager());
        }
}
