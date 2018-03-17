package com.uurobot.yxwlib.robot.mgr;

import android.app.Activity;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/11/22.
 */

public class ActionMgr {

        private static final String TAG = "ActionMgr";
        public static ActionMgr actionMgr ;

        public static ActionMgr getActionMgr() {
                if (actionMgr==null){
                        actionMgr = new ActionMgr();
                }
                return actionMgr;
        }

        public void exeAction(int id){
                Logger.e(TAG,"exeAction====="+id);
        }
}
