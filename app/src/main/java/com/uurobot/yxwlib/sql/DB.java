package com.uurobot.yxwlib.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uurobot.yxwlib.MainApplication;
import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.okhttp.yunwenTest.Constant;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DB extends SQLiteOpenHelper {

        private static final String TAG = "DB";
       // SQLiteDatabase  writableDatabase;
        private String name ;
        public DB(Context context) {
                super(context, Cons.DATABASE_NAME, null, Cons.DATABASE_VERSION);
              //  writableDatabase = getWritableDatabase();
                Log.e(TAG, "DB: construct");
        }

        private static DB instance ;
        public  static DB getInstance(){
                if (instance == null){
                        synchronized (DB.class){
                                if (instance == null){
                                        instance = new DB(MainApplication.getContext());
                                }
                        }
                }
                        return new DB(MainApplication.getContext()) ;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Logger.e("","oncreate");
                //创建表结构
                String sql = "create table "+ Cons.TABLE_NAME+"("+
                        Cons.ID+" integer primary key autoincrement,"+
                        Cons.NAME+" varchar(20)," +
                        Cons.AGE+" integer)";
                db.execSQL(sql);//执行sql语句，， 这里可以创建多个表。。。通过执行多个 sql
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Logger.e("","onUpgrade");
        }

//        public void query(){
//                writableDatabase.execSQL("select * from test" );
//        }
}
