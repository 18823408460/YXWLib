package com.uurobot.yxwlib.robot.mode;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BaseMode  implements IBaseMode{
        public static final String type_recognizer = "1";
        public static final String type_wake = "2";
        public static final String type_sensor = "3";
        public static final String type_pad = "4";
        public static final String type_socket = "5";
        public static final String exit = "退出";
        protected String CurrentMode = "" ;
        private IBaseMode NextMode ;

        protected static final String DefalutMode = "BaseMode";
        @Override
        public void handlerRecognizer(String result) {

        }

        @Override
        public void handlerSensor() {

        }

        @Override
        public void handlerWake() {

        }

        @Override
        public void handlerPad() {

        }

        @Override
        public void handlerSocket() {

        }

        public void setNextMode(IBaseMode nextMode) {
                NextMode = nextMode;
        }

        public IBaseMode getNextMode() {
                return NextMode;
        }
}
