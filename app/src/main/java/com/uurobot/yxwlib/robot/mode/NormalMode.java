package com.uurobot.yxwlib.robot.mode;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class NormalMode extends BaseMode  {

        private static final String TAG = "NormalMode";

        @Override
        public void handlerRecognizer(String result) {
                        Logger.e(TAG,"NormalMode.....handlerRecognizer");
        }

        @Override
        public void handlerSensor() {
                Logger.e(TAG,"NormalMode.....handlerSensor");
        }

        @Override
        public void handlerWake() {
                Logger.e(TAG,"NormalMode.....handlerWake");
        }

        @Override
        public void handlerPad() {
                Logger.e(TAG,"NormalMode.....handlerPad");
        }

        @Override
        public void handlerSocket() {
                Logger.e(TAG,"NormalMode.....handlerSocket");
        }
}
