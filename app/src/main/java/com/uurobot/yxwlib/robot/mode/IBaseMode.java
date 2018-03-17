package com.uurobot.yxwlib.robot.mode;

/**
 * Created by Administrator on 2017/11/22.
 */

public interface IBaseMode {
        void handlerRecognizer(String result);
        void handlerSensor();
        void handlerWake() ;
        void handlerPad();
        void handlerSocket() ;
}
