package com.uurobot.yxwlib.robot.mgr;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class MusicMgr {

        private static final String TAG = "ActionMgr";
        public static MusicMgr actionMgr ;

        public static MusicMgr getActionMgr() {
                if (actionMgr==null){
                        actionMgr = new MusicMgr();
                }
                return actionMgr;
        }

        public void playMusic(int id){
                Logger.e(TAG,"playMusic====="+id);
        }
}
