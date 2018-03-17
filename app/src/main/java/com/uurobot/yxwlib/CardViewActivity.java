package com.uurobot.yxwlib;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CardViewActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_card);

                CardView cardview = findViewById(R.id.cardView1);
                cardview.setRadius(30);

                cardview.setCardElevation(30);

                cardview.setContentPadding(12,12,12,12);
        }
}
