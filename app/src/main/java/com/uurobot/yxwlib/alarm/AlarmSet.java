package com.uurobot.yxwlib.alarm;


import java.text.SimpleDateFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;


public class AlarmSet {

        public static final String TAG = "AlarmSet";


        public static final String ACTION_ALARM_ON = "com.uurobot.yxwlib.action.ALARM_ON_OK";

        public static final String REMINDER_ID = "reminder_id";

        public static final String ALARM_RAW_DATA = "alarm_raw_data";

        public static final String ACTION_REGISTER_ALARM = "com.uurobot.yxwlib.action.REGISTER_ALARM";

        public static final String ACTION_REGISTER_NEXT_ALARM = "com.uurobot.yxwlib.action.REGISTER_NEXT_ALARM";

        public static final String ACTION_PLAY_ALARM = "com.uurobot.yxwlib.action.PLAY_ALARM";

        public static final String ACTION_CANCEL_ALARM = "com.uurobot.yxwlib.action.CANCEL_ALARM";

        public static final String ACTION_CANCEL_REMINDER = "com.uurobot.yxwlib.action.CANCEL_REMINDER";

        public static final String ALARM_LABLE = "alarm_lable";

        public static final String ALARM_TIME = "alarm_time";

        public static final String ALARM_REPEAT_DATE = "alarm_repeat_date";

        public static final int oneYearAgain = 1;

        public static final int oneMonthAgain = 2;

        public static final int oneDayAgain = 3;

        public static final int NotAgain = 0;

        public static final int defaultAlarmCode = 1;

        public static final int cancelError = 0;

        public static final int cancelSuccess = 1;

        private Context mContext;

        private AlarmManager mAlarmManager;

        public AlarmSet(Context context) {
                mContext = context;
                mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        @SuppressLint("Recycle")
        public void register(Alarm alarm) {
                if (alarm != null) {
                        Long triggerAtTime = alarm.time;
                        int requestCode = alarm.id;
                        Intent intent = new Intent(ACTION_ALARM_ON);
                        intent.putExtra(REMINDER_ID, requestCode);
                        Parcel out = Parcel.obtain();
                        alarm.writeToParcel(out, 0);
                        out.setDataPosition(0);
                        intent.putExtra(ALARM_RAW_DATA, out.marshall());
                        String nowtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS", Locale.CHINA)).format(triggerAtTime);
                        Logger.e(TAG, "register alarm.id = " + alarm.id + "    triggerAtTime =" + triggerAtTime + "     date=" + nowtime);
                        PendingIntent sender = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//                        if (getAlarm(mContext.getContentResolver(), defaultAlarmCode) == null) {
//                                CreateAlarmValues(alarm, mContext);
//                        }
//                        else {
//                                updateAlarmValues(alarm, mContext);
//                        }
                        CreateAlarmValues(alarm, mContext);
                        mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, sender);
                }

        }

        public int cancel(int requestCode) {
                if (getAlarm(mContext.getContentResolver(), requestCode) == null) {
                        return cancelError;
                }
                else {
                        Intent intent = new Intent(ACTION_ALARM_ON);
                        PendingIntent sender = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        mAlarmManager.cancel(sender);
                        deleteAlarm(mContext, requestCode);
                        Logger.d(TAG, "cancel requestCode = " + requestCode);
                        return cancelSuccess;
                }
        }


        public static void CreateAlarmValues(Alarm alarm, Context context) {
                ContentValues values = new ContentValues(5);
                values.put(Alarm.Columns.ISYMD, alarm.isYMD);
                values.put(Alarm.Columns.TIME, alarm.time);
                values.put(Alarm.Columns.DAYS_OF_WEEK, alarm.daysOfWeek.getCoded());
                values.put(Alarm.Columns.LABEL, alarm.label);
                values.put(Alarm.Columns.ALERT, alarm.alert == null ? null : alarm.alert.toString());

                ContentResolver resolver = context.getContentResolver();
                Uri muri = resolver.insert(Alarm.Columns.CONTENT_URI, values);
                Logger.d(TAG, "CreateAlarmValues muri = " + muri);
        }

        public static void updateAlarmValues(Alarm alarm, Context context) {
                ContentValues values = new ContentValues(5);
                values.put(Alarm.Columns.ISYMD, alarm.isYMD);
                values.put(Alarm.Columns.TIME, alarm.time);
                values.put(Alarm.Columns.DAYS_OF_WEEK, alarm.daysOfWeek.getCoded());
                values.put(Alarm.Columns.LABEL, alarm.label);
                values.put(Alarm.Columns.ALERT, alarm.alert == null ? null : alarm.alert.toString());

                ContentResolver resolver = context.getContentResolver();
                resolver.update(ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, alarm.id), values, null, null);
                Logger.d(TAG, "updateAlarmValues Id = " + alarm.id);
        }

        public static void deleteAlarm(Context context, int alarmId) {
                if (alarmId == -1) return;

                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, alarmId);
                contentResolver.delete(uri, "", null);
                Logger.d(TAG, "deleteAlarm Id = " + alarmId);

        }

        public static Alarm getAlarm(ContentResolver contentResolver, int alarmId) {
                Logger.e(TAG,"Alarm11111111======");
                Cursor cursor = contentResolver.query(
                        ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, alarmId),
                        Alarm.Columns.ALARM_QUERY_COLUMNS,
                        null, null, null);
                Alarm alarm = null;
                if (cursor != null) {
                        if (cursor.moveToFirst()) {
                                alarm = new Alarm(cursor);
                        }
                        cursor.close();
                }
                return alarm;
        }



        /**
         * 注册闹钟。
         * @param mContext
         * @param mTimes
         * @param mRepeatDate
         * @param mLable
         */
        public static void registerAlarm(Context mContext,String mTimes,String mRepeatDate,String mLable){
                Intent intent = new Intent(mContext, AlarmService.class);
                intent.setAction(AlarmSet.ACTION_REGISTER_ALARM);
                intent.putExtra(AlarmSet.ALARM_TIME, mTimes);
                intent.putExtra(AlarmSet.ALARM_REPEAT_DATE, mRepeatDate);
                intent.putExtra(AlarmSet.ALARM_LABLE, mLable);
                mContext.startService(intent);
        }

        /**
         * 取消闹钟
         * @param mContext
         */
        public static void  cancelAlarm(Context mContext){
                Intent intent = new Intent(mContext, AlarmService.class);
                intent.setAction(AlarmSet.ACTION_CANCEL_REMINDER);
                mContext.startService(intent);
        }


}
