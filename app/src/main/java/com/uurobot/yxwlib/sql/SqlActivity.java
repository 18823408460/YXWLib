package com.uurobot.yxwlib.sql;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/16.
 */

public class SqlActivity extends Activity {

        private static final String TAG = "SqlActivity";

        private Context context;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_sql);
                context = this ;


                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG,"---------2------");
                                new DB(context, context.getFilesDir().getAbsolutePath() + "/test.db",  1).query();
                                SystemClock.sleep(6000);
                        }
                }).start();
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Logger.e(TAG,"---------1------");
                                new DB(context, context.getFilesDir().getAbsolutePath() + "/test.db",  1).query();
                        }
                }).start();

                TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                                Logger.e(TAG,"timerTask.........."+Thread.currentThread().getName());
                                new DB(context, context.getFilesDir().getAbsolutePath() + "/test.db",  1).query();
                        }
                };
                new Timer().schedule(timerTask,1000);
        }
}
