package com.uurobot.yxwlib.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.uurobot.yxwlib.MainApplication;

/**
 * Created by Administrator on 2018/3/16.
 */

public class DaoMgr {
        public static DaoMgr instance ;
        private DaoSession daoSession ;
        public static  DaoMgr getInstance(){
                if (instance == null){
                        synchronized (DaoMgr.class){
                                if (instance == null){
                                        instance = new DaoMgr() ;
                                }
                        }
                }
                return instance ;
        }

        private DaoMgr() {
                init();
        }

        private void init() {
                DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MainApplication.getContext(),"test.db",null){
                        @Override
                        public void onCreate(SQLiteDatabase db) {
                                super.onCreate(db);
                        }

                        @Override
                        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                                super.onUpgrade(db, oldVersion, newVersion);
                        }
                };
                DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
                daoSession = daoMaster.newSession();
        }

        public DaoSession getDaoSession() {
                return daoSession;
        }
}
