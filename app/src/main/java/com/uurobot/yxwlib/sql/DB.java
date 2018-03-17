package com.uurobot.yxwlib.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uurobot.yxwlib.alarm.Logger;

/**
 * Created by Administrator on 2017/12/16.
 */

public class DB extends SQLiteOpenHelper {
        SQLiteDatabase  writableDatabase;
        private String name ;
        public DB(Context context, String name, int version) {
                super(context, name, null, version);
                this.name = name ;
                writableDatabase = getWritableDatabase();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Logger.e("","oncreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Logger.e("","onUpgrade");
        }

        public void query(){
                writableDatabase.execSQL("select * from test" );
        }
}
