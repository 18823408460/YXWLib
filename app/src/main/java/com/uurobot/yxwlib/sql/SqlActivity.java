package com.uurobot.yxwlib.sql;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.StateSet;
import android.widget.Button;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/16.
 */
// 操作数据库是应该是一个数据库对应一个SQliteOpenHelper对象

        // 1. 为什么使用事务 insert会快？？？(如果未启用事务，sqlite会每插入一条数据，就往磁盘上面写一次，
// 在整个执行过程中我也观察到未开启事务时程序执行期间硬盘灯
// 一直是亮的，这也映证了这一点。而在开启事务的情况下，其应该是在对数据全部处理完之后才需要执行
// 一次IO操作，时间自然非常快) ----------------- 字节操作和 块 操作 ？？

public class SqlActivity extends Activity {

        private static final String TAG = "SqlActivity";

        @BindView(R.id.sqlTest)
        Button sqlTest;

        @BindView(R.id.sqlquery)
        Button sqlquery;

        @BindView(R.id.sqltranct)
        Button sqltranct;

        private Context context;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_sql);
                ButterKnife.bind(this);
                context = this;
        }
        @OnClick(R.id.sqltranct)
        public void sqltranct() {
                testTranct();
        }


        @OnClick(R.id.sqlTest)
        public void sqlBtn() {
                testAyn();
        }

        @OnClick(R.id.sqlquery)
        public void sqlquery() {
                DB test = DB.getInstance();
                SQLiteDatabase writableDatabase = test.getWritableDatabase();
                String sql = "select * from " + Cons.TABLE_NAME;
                Cursor cursor = writableDatabase.rawQuery(sql, null);
                if (cursor != null && cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                                int anInt = cursor.getInt(0);
                                String name = cursor.getString(1);
                                int age = cursor.getInt(2);
                                Log.e(TAG, "sqlquery:  anInt=" + anInt + "  name=" + name + "  age=" + age);
                        }
                }
                cursor.close();
                writableDatabase.close();
        }

        /**
         *
         */
        private void testTranct() {
                DB test =DB.getInstance();
                SQLiteDatabase writableDatabase = test.getWritableDatabase();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                String format = dateFormat.format(new Date());
                Log.e(TAG, "testTranct: "+ format );  // 03:13:20
                writableDatabase.beginTransaction();
                ContentValues valus = new ContentValues();
                for (int i = 0; i < 2; i++) {
                        valus.put("name", "yxw=" + i);
                        valus.put("age", i);
                        writableDatabase.insert(Cons.TABLE_NAME, null, valus);
                }
                writableDatabase.setTransactionSuccessful(); // 这个调用了，才可以插入成功
                writableDatabase.endTransaction(); // 这里调用了，
                writableDatabase.close();
                DateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
                String format1 = dateFormat1.format(new Date());
                Log.e(TAG, "testTranct1: "+ format1 );  // 03:13:42

        }

        /**
         * 测试并发
         */
        private void testAyn() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG, "---------2------");
                                DB test = new DB(context.getApplicationContext());
                                SQLiteDatabase writableDatabase = test.getWritableDatabase();
                                try {
                                        Thread.sleep(6000);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                ContentValues valus = new ContentValues();
                                Log.e(TAG, "run: " + writableDatabase.toString());
                                valus.put("name", "yxw");
                                valus.put("age", 1);
                                writableDatabase.insert(Cons.TABLE_NAME, null, valus);
                                Logger.e(TAG, "---------2------end");
                                // writableDatabase.close();
                        }
                }).start();


                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG, "---------1------");
                                try {
                                        Thread.sleep(1000);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                DB test = new DB(context.getApplicationContext());
                                SQLiteDatabase writableDatabase = test.getWritableDatabase();


                                Log.e(TAG, "run: " + writableDatabase.toString());
                                ContentValues valus = new ContentValues();
                                valus.put("name", "yxw1");
                                valus.put("age", 3);
                                writableDatabase.insert(Cons.TABLE_NAME, null, valus);
                                Logger.e(TAG, "---------1------end");
                                // writableDatabase.close();
                        }
                }).start();

//                TimerTask timerTask = new TimerTask() {
//                        @Override
//                        public void run() {
//                                Logger.e(TAG, "timerTask.........." + Thread.currentThread().getName());
//                                new DB(context, context.getFilesDir().getAbsolutePath() + "/testAyn.db", 1).query();
//                        }
//                };
//                new Timer().schedule(timerTask, 1000);
        }

        private void que(int id) {
                String[] columns = {"_id", "name", "age"}; //需要查询的列
                String selection = "_id = ?"; // 选择条件，给null查询所有
                String[] selectionArgs = {id + ""};//选择条件参数,会把选择条件中的？替换成这个数组中的值
                String groupBy = null; // 分组语句 group by name  注意些的时候需要要group by 去掉
                String having = null;  // 过滤语句
                String orderBy = null; //排序

                // Cursor cursor = db.query("person", columns, selection, selectionArgs, groupBy, having, orderBy);
        }

}
