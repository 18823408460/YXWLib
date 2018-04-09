package com.uurobot.yxwlib.mqtt;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.uurobot.yxwlib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/8.
 */

public class MqttActivity extends Activity {

        @BindView(R.id.button17)
        Button button17;

        @OnClick(R.id.button17)
        public void publish() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Mqtt.getInstance(context).publish("topic1", "尹晓伟", 0);
                        }
                }).start();
//                new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                                ActiveMq.init();
//                        }
//                }).start();
        }

        private Context context;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.mqtt);
                ButterKnife.bind(this);
                context = this;
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Mqtt.getInstance(context).connect();
                        }
                }).start();

        }
}
