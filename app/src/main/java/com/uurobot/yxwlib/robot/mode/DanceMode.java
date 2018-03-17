package com.uurobot.yxwlib.robot.mode;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class DanceMode extends BaseMode {
        private static final String handlerStr = String.valueOf("");
        private static final String TAG = "DanceMode";

        @Override
        public void handlerRecognizer(String result) {
                if (result.contains(handlerStr)){
                        CurrentMode = TAG ;
                        Logger.e(TAG,"danceMode.....handlerRecognizer");
                        return;

                }else if (TAG.equals(CurrentMode)){
                        if (exit.equals(result)){
                                CurrentMode = DefalutMode ;
                                Logger.e(TAG,"danceMode.....handlerRecognizer....exit");
                        }
                        return;
                }
                if (getNextMode()!=null){
                        getNextMode().handlerRecognizer(result);
                }else {
                        Logger.e(TAG,"danceMode.....null");
                }

        }

        @Override
        public void handlerSensor() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"danceMode.....handlerSensor");
                }else {
                        getNextMode().handlerSensor();
                }
        }

        @Override
        public void handlerWake() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"danceMode.....handlerWake");
                }else {
                        getNextMode().handlerWake();
                }
        }

        @Override
        public void handlerPad() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"danceMode.....handlerPad");
                }else {
                        getNextMode().handlerPad();
                }
        }

        @Override
        public void handlerSocket() {
                if (CurrentMode.equals(TAG)){
                        Logger.e(TAG,"danceMode.....handlerSocket");
                }else {
                        getNextMode().handlerSocket();
                }
        }


}
