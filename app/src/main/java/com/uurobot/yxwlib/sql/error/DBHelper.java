package com.uurobot.yxwlib.sql.error;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.uurobot.yxwlib.sql.Cons;

/**
 * ?????DBHelper??
 *
 * @author Administrator
 */
public class DBHelper extends SQLiteOpenHelper {

        public final static String DATABASE_NAME = "test.db";

        public final static String TABLE = "properly";

        public final static int DATABASE_VERSION = 2;

        private static DBHelper mInstance;

        public DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * ???????
         *
         * @param context
         * @return
         */
//	    public  static DBHelper getInstance(Context context) {
//	    	/**
//	    	 * ?????????????????????????
//	    	 */
//	        if (mInstance == null) {
//	        	synchronized (DBHelper.class) {
//	        		if (mInstance == null) {
//	        		mInstance = new DBHelper(context);
//	        		}
//				}
//	        }
//	        return mInstance;
//	    };

        //????
        @Override
        public void onCreate(SQLiteDatabase db) {
//	    	try {
//	    		//autoincrement
//	    		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE
//	    				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
//	    				+ "mName String,"
//	    				+ "mAge INTEGER)");
//	    	} catch (SQLiteException e) {
//	    		e.printStackTrace();
//	    	}
                String sql = "create table " + Cons.TABLE_NAME + "(" +
                        Cons.ID + " integer primary key autoincrement," +
                        Cons.NAME + " varchar(20)," +
                        Cons.AGE + " integer)";
                db.execSQL(sql);//执行sql语句
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // ?????????????
                db.execSQL("DROP TABLE IF EXISTS " + TABLE);
                // ???????
                onCreate(db);
        }


}
