package com.uurobot.yxwlib.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.okhttp.yunwenTest.Constant;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DB extends SQLiteOpenHelper {
       // SQLiteDatabase  writableDatabase;
        private String name ;
        public DB(Context context) {
                super(context, Cons.DATABASE_NAME, null, Cons.DATABASE_VERSION);
              //  writableDatabase = getWritableDatabase();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Logger.e("","oncreate");
                //创建表结构
                String sql = "create table "+ Cons.TABLE_NAME+"("+
                        Cons.ID+" integer primary key autoincrement,"+
                        Cons.NAME+" varchar(20)," +
                        Cons.AGE+" integer)";
                db.execSQL(sql);//执行sql语句
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Logger.e("","onUpgrade");
        }

//        public void query(){
//                writableDatabase.execSQL("select * from test" );
//        }
}
