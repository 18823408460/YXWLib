package com.uurobot.yxwlib.uiTest.MixView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Switch;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SpaceActivity extends Activity {

        private static final String TAG = "SpaceActivity";

        @BindView(R.id.button12)
        Button button12;

        @BindView(R.id.button13)
        Button button13;

        @BindView(R.id.space)
        Space space; // 纯粹是用在占用空间的场景下，

        @BindView(R.id.spinner)
        Spinner spinner; // 下拉列表

        @BindView(R.id.switch1)
        Switch switch1;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.space);
                ButterKnife.bind(this);
                initData();
                initSwitch();
        }

        private void initSwitch(){
                switch1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Logger.e(TAG,"onclick=-=-=-=-=-=-=-=-=-=-"+ switch1.isChecked());
                        }
                });

        }


        private void initData() {
                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                        arrayList.add("hello" + i);
                }
                SpinnerAdater spinnerAdater = new SpinnerAdater(this, arrayList);

                spinner.setAdapter(spinnerAdater);
        }
}
