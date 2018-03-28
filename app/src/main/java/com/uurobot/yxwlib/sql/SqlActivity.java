package com.uurobot.yxwlib.sql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.sql.error.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/16.
 */
// 操作数据库是应该是一个数据库对应一个SQliteOpenHelper对象
public class SqlActivity extends Activity {

        private static final String TAG = "SqlActivity";

        @BindView(R.id.sqlTest)
        Button sqlTest;

        @BindView(R.id.sqlquery)
        Button sqlquery;

        private Context context;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_sql);
                ButterKnife.bind(this);
                context = this;
        }

        @OnClick(R.id.sqlTest)
        public void sqlBtn() {
                test();
        }

        @OnClick(R.id.sqlquery)
        public void sqlquery() {
                DB test = new DB(context);
                SQLiteDatabase writableDatabase = test.getWritableDatabase();
                String sql = "select * from " + Cons.TABLE_NAME  ;
                Cursor cursor = writableDatabase.rawQuery(sql, null);
                if (cursor !=null && cursor.getCount()>0){
                        while (cursor.moveToNext()){
                                int anInt = cursor.getInt(0);
                                String name = cursor.getString(1);
                                int age = cursor.getInt(2);
                                Log.e(TAG, "sqlquery:  anInt="+anInt +"  name="+name +"  age="+age );
                        }
                }
        }

        private void test2(){
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                DBHelper helper = new DBHelper(context);
                                SQLiteDatabase writableDatabase = helper.getWritableDatabase();
                                ContentValues valus = new ContentValues();
                                valus.put("name", "1");
                                valus.put("age", 1);
                                writableDatabase.insert(Cons.TABLE_NAME,null,valus);
                                writableDatabase.close();
                        }
                }).start();

                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                DBHelper helper = new DBHelper(context);
                                SQLiteDatabase writableDatabase = helper.getWritableDatabase();
                                ContentValues valus = new ContentValues();
                                valus.put("name", "2");
                                valus.put("age", 2);
                                writableDatabase.insert(Cons.TABLE_NAME,null,valus);
                                writableDatabase.close();
                        }
                }).start();
        }

        private void test() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG, "---------2------");
                                DB test = new DB(context.getApplicationContext());
                                SQLiteDatabase writableDatabase = test.getWritableDatabase();
                                try {
                                        Thread.sleep(60000);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                ContentValues valus = new ContentValues();
                                Log.e(TAG, "run: "+writableDatabase.toString() );
                                valus.put("name", "yxw");
                                valus.put("age", 1);
                                writableDatabase.insert(Cons.TABLE_NAME, null, valus);

                                writableDatabase.close();
                        }
                }).start();
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG, "---------1------");
                                DB test = new DB(context.getApplicationContext());
                                SQLiteDatabase writableDatabase = test.getWritableDatabase();

                                try {
                                        Thread.sleep(60000);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                Log.e(TAG, "run: "+writableDatabase.toString() );
                                ContentValues valus = new ContentValues();
                                valus.put("name", "yxw1");
                                valus.put("age", 3);
                                writableDatabase.insert(Cons.TABLE_NAME, null, valus);
                                writableDatabase.close();
                        }
                }).start();

//                TimerTask timerTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                                Logger.e(TAG, "timerTask.........." + Thread.currentThread().getName());
//                                new DB(context, context.getFilesDir().getAbsolutePath() + "/test.db", 1).query();
//                        }
//                };
//                new Timer().schedule(timerTask, 1000);
        }

        private void que(int id){
                String[] columns = {"_id","name","age"}; //需要查询的列
                String selection = "_id = ?"; // 选择条件，给null查询所有
                String[] selectionArgs = {id+""};//选择条件参数,会把选择条件中的？替换成这个数组中的值
                String groupBy = null; // 分组语句 group by name  注意些的时候需要要group by 去掉
                String having = null;  // 过滤语句
                String orderBy = null ; //排序

               // Cursor cursor = db.query("person", columns, selection, selectionArgs, groupBy, having, orderBy);
        }

}
