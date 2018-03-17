package com.uurobot.yxwlib.google;

/**
 * Created by Administrator on 2018/2/5.
 */

public class TtsMgr {
        private static TtsMgr ttsMgr ;

        private TtsMgr(){

        }
        public static TtsMgr getTtsMgr(){
                if (ttsMgr == null){
                        synchronized (TtsMgr.class){
                                if (ttsMgr == null){
                                        ttsMgr = new TtsMgr();
                                }
                        }
                }
                return ttsMgr ;
        }
}
