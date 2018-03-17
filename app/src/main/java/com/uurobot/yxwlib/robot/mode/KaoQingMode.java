package com.uurobot.yxwlib.robot.mode;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class KaoQingMode extends BaseMode {
        private static final String handlerStr = "考勤";
        private static final String TAG = "KaoQingMode";

        @Override
        public void handlerRecognizer(String result) {
                if (result.contains(handlerStr)){
                        CurrentMode = TAG ;
                        Logger.e(TAG,"KaoQingMode.....handlerRecognizer");
                        return;
                }else if (TAG.equals(CurrentMode)){
                        if (exit.equals(result)){
                                CurrentMode = DefalutMode ;
                                Logger.e(TAG,"KaoQingMode.....handlerRecognizer....exit");
                        }
                        return;
                }
                getNextMode().handlerRecognizer(result);
        }

        @Override
        public void handlerWake() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"KaoQingMode.....handlerWake");
                }else {
                        getNextMode().handlerWake();
                }
        }

        @Override
        public void handlerSensor() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"KaoQingMode.....handlerSensor");
                }else {
                       getNextMode().handlerSensor();
                }
        }


        @Override
        public void handlerPad() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"KaoQingMode.....handlerPad");
                }else {
                       getNextMode().handlerPad();
                }
        }

        @Override
        public void handlerSocket() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"KaoQingMode.....handlerSocket");
                }else {
                        getNextMode().handlerSocket();
                }
        }
}
