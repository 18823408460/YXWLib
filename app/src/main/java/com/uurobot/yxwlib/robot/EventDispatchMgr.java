package com.uurobot.yxwlib.robot;

import com.uurobot.yxwlib.robot.mode.BaseMode;
import com.uurobot.yxwlib.robot.mode.DanceMode;
import com.uurobot.yxwlib.robot.mode.IBaseMode;
import com.uurobot.yxwlib.robot.mode.KaoQingMode;
import com.uurobot.yxwlib.robot.mode.NormalMode;

/**
 * Created by Administrator on 2017/11/22.
 */

// 事件分发器
public class EventDispatchMgr {
        private static EventDispatchMgr eventDispatchMgr ;

        private final DanceMode danceMode;


        public EventDispatchMgr() {
                danceMode = new DanceMode() ;
                KaoQingMode kaoQingMode = new KaoQingMode() ;
                NormalMode normalMode = new NormalMode() ;
                danceMode.setNextMode(kaoQingMode);
                kaoQingMode.setNextMode(normalMode);
        }

        public static EventDispatchMgr getEventDispatchMgr() {
                if (eventDispatchMgr==null){
                        eventDispatchMgr = new EventDispatchMgr();
                }
                return eventDispatchMgr;
        }


        public IBaseMode getMode(){
                return danceMode ;
        }

        public void handler(String type,Object data){
                switch (type){
                        case BaseMode.type_pad:{
                                danceMode.handlerPad();
                                break;
                        }
                        case BaseMode.type_recognizer:{
                                danceMode.handlerRecognizer((String) data);
                                break;
                        }
                        case BaseMode.type_wake:{
                                danceMode.handlerWake();
                                break;
                        }
                        case BaseMode.type_sensor:{
                                danceMode.handlerSensor();
                                break;
                        }
                        case BaseMode.type_socket:{
                                danceMode.handlerSocket();
                                break;
                        }
                }
        }
}

































