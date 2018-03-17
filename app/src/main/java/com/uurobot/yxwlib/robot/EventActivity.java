package com.uurobot.yxwlib.robot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.robot.mode.BaseMode;

/**
 * Created by Administrator on 2017/11/22.
 */

public class EventActivity extends Activity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_event);

                final EditText edit= findViewById(R.id.event_input);

                findViewById(R.id.send_result).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EventDispatchMgr.getEventDispatchMgr().handler(BaseMode.type_recognizer,edit.getText().toString());
                        }
                });

                findViewById(R.id.wake_test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EventDispatchMgr.getEventDispatchMgr().handler(BaseMode.type_wake,edit.getText().toString());
                        }
                });

                findViewById(R.id.sensor_test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EventDispatchMgr.getEventDispatchMgr().handler(BaseMode.type_sensor,edit.getText().toString());
                        }
                });

                findViewById(R.id.pad_test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EventDispatchMgr.getEventDispatchMgr().handler(BaseMode.type_pad,edit.getText().toString());
                        }
                });

                findViewById(R.id.socket_test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EventDispatchMgr.getEventDispatchMgr().handler(BaseMode.type_socket,edit.getText().toString());
                        }
                });
        }
}
