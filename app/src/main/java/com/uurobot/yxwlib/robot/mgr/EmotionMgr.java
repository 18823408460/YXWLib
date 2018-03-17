package com.uurobot.yxwlib.robot.mgr;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class EmotionMgr {

        private static final String TAG = "ActionMgr";
        public static EmotionMgr actionMgr ;

        public static EmotionMgr getActionMgr() {
                if (actionMgr==null){
                        actionMgr = new EmotionMgr();
                }
                return actionMgr;
        }

        public void exeEye(int id){
                Logger.e(TAG,"exeEye====="+id);
        }
}
