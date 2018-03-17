package com.uurobot.yxwlib.alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.uurobot.yxwlib.R;

public class AlarmService extends Service {

        public static final String TAG = "AlarmService";

        private AlarmSet mAlarmManager;

        private Alarm mRegisterAlarm = null;

        private AlarmPlayer mAlarmPlayer = null;

        private static final int MSG_HANDLE_MEDIA_PALYER = 1;

        private static final int MSG_HANDLE_MEDIA_STOP = 2;

        private static final int MSG_HANDLE_REGISTER_ALARM = 3;

        private static final int MSG_HANDLE_CANCLE_ALARM = 4;

        private static boolean isFirstChange = true;

        @SuppressLint("HandlerLeak")
        private Handler mAlarmPlayerHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                        switch (msg.what) {
                                case MSG_HANDLE_MEDIA_PALYER:
                                        Logger.i(TAG, "-handleMessage-MSG_HANDLE_MEDIA_PALYER!!!");
                                        // Uri alarmUri = (Uri)msg.obj;
                                        mAlarmPlayer = new AlarmPlayer(AlarmService.this);
                                        mAlarmPlayer.init();
                                        mAlarmPlayer.play();
                                        mAlarmPlayerHandler.sendEmptyMessageDelayed(
                                                MSG_HANDLE_MEDIA_STOP, 60 * 1000);
                                        break;

                                case MSG_HANDLE_MEDIA_STOP:
                                        Logger.i(TAG, "-handleMessage-MSG_HANDLE_MEDIA_STOP!!!");
                                        if (mAlarmPlayer != null) {
                                                mAlarmPlayer.StopAlarm();
                                        }
                                        break;
                                case MSG_HANDLE_REGISTER_ALARM:
                                        Logger.i(TAG,
                                                "handleMessage-MSG_HANDLE_REGISTER_ALARM,   mRegisterAlarm="
                                                        + mRegisterAlarm);// 需要操作数据库
                                        if (mRegisterAlarm != null) {
                                                mAlarmManager.register(mRegisterAlarm);
                                        }
                                        break;
                                case MSG_HANDLE_CANCLE_ALARM:
                                        Logger.i(TAG, "-handleMessage-MSG_HANDLE_CANCLE_ALARM !!!!");// 需要操作数据库
                                        mAlarmManager.cancel(AlarmSet.defaultAlarmCode);
                                        break;
                                default:
                                        break;

                        }

                }
        };

        @Override
        public void onCreate() {
                // TODO Auto-generated method stub
                super.onCreate();
                mAlarmManager = new AlarmSet(this);
        }

        @Override
        public void onDestroy() {
                // TODO Auto-generated method stub
                super.onDestroy();
        }

        private void PlayTTS(String mlable) {// 到时间提醒播放相关的TTS
                Logger.e("test", "时间到了-=" + mlable + "=");
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onStart(Intent intent, int startId) {
                // TODO Auto-generated method stub
                super.onStart(intent, startId);

                if (intent != null) {
                        String action = intent.getAction();
                        Logger.d(TAG, "!--->onStartCommand: action " + action);
                        if (AlarmSet.ACTION_PLAY_ALARM.equals(action)) {
                                Alarm mAlarm = getAlarm();
                                boolean isRealAlarm = true;
                                if (mAlarm != null) {
                                        // 一旦播放提醒，取消掉当前的识别

                                        Calendar mCalendar = Calendar.getInstance();
                                        if (Math.abs(mCalendar.getTimeInMillis()
                                                - mAlarm.time) > 10000) {
                                                isRealAlarm = false;// 确认是否是由于系统时间重新设置导致的闹钟触发
                                        }
                                        else {
                                                String mLable = mAlarm.label;
                                                if (mLable != null && !"".equals(mLable)) {
                                                        String str = (new SimpleDateFormat(
                                                                this.getString(R.string.time_format), Locale.CHINA))
                                                                .format(mCalendar.getTime());
                                                        mLable = String.format(this.getString(R.string.it_is_time_to), str,
                                                                mLable);
                                                        // String tts="现在是"+str+",到时间"+mLable;
                                                        mLable = mLable.replaceAll("\r|\n", "");
                                                        PlayTTS(mLable);
                                                }
                                                else {
                                                        mAlarmPlayerHandler
                                                                .sendEmptyMessage(MSG_HANDLE_MEDIA_PALYER);
                                                }
                                                Logger.d(TAG, "AlarmPlayer start mLable=" + mLable);
                                                if (mAlarm != null) {
                                                        registerNexttime(mAlarm);
                                                }

                                        }
                                }
                                Logger.d(TAG, mAlarm != null ? mAlarm.toString()
                                        : "alarm = null" + "||| isRealAlarm=" + isRealAlarm);
                        }
                        else if (AlarmSet.ACTION_REGISTER_NEXT_ALARM.equals(action)) {
                                Alarm mAlarm = getAlarm();
                                if (mAlarm != null) {
                                        registerNexttime(mAlarm);
                                }
                        }
                        else if (AlarmSet.ACTION_REGISTER_ALARM.equals(action)) {

                                String mLable = intent.getStringExtra(AlarmSet.ALARM_LABLE);
                                String mRepeatDate = intent
                                        .getStringExtra(AlarmSet.ALARM_REPEAT_DATE);
                                String mTimes = intent.getStringExtra(AlarmSet.ALARM_TIME);
                                Logger.d(TAG, "!--->mLable=" + mLable + "  mRepeatDate="
                                        + mRepeatDate + "  mTimes=" + mTimes);
                                Alarm mAlarm = new Alarm();
                                mAlarm.id = AlarmSet.defaultAlarmCode;
                                mAlarm.label = mLable;
                                if ("WORKDAY".equals(mRepeatDate)) {
                                        mRepeatDate = "D1,D2,D3,D4,D5";
                                }
                                else if ("WEEKEND".equals(mRepeatDate)) {
                                        mRepeatDate = "D6,D7";
                                }

                                if ("OFF".equals(mRepeatDate)) {
                                        mAlarm.isYMD = 0;
                                }
                                else if ("DAY".equals(mRepeatDate)) {
                                        mAlarm.isYMD = AlarmSet.oneDayAgain;
                                }
                                else if ("MONTH".equals(mRepeatDate)) {
                                        mAlarm.isYMD = AlarmSet.oneMonthAgain;
                                }
                                else if ("YEAR".equals(mRepeatDate)) {
                                        mAlarm.isYMD = AlarmSet.oneYearAgain;
                                }
                                else {
                                        mAlarm.daysOfWeek.setRepateDay(mRepeatDate);
                                }
                                mAlarm.time = Long.parseLong(mTimes);
                                Logger.d(TAG,
                                        "!--->mAlarm.time = " + (new SimpleDateFormat(
                                                "yyyy-MM-dd HH:mm:ss:SS", Locale.CHINA))
                                                .format(mAlarm.time));
                                mRegisterAlarm = mAlarm;
                                if (mAlarmPlayerHandler != null) {
                                        mAlarmPlayerHandler
                                                .sendEmptyMessage(MSG_HANDLE_REGISTER_ALARM);
                                }
                        }
                        else if (AlarmSet.ACTION_CANCEL_ALARM.equals(action)) {
                                int returnCode = mAlarmManager
                                        .cancel(AlarmSet.defaultAlarmCode);
                                if (returnCode == AlarmSet.cancelError) {
                                        PlayTTS(this.getString(R.string.sorry_not_can_cancle_alarm));
                                }
                                else {
                                        PlayTTS(this.getString(R.string.alarm_was_cancle));
                                        Logger.d(TAG, "ACTION_CANCEL_ALARM SUCCESS");
                                }
                        }
                        else if (AlarmSet.ACTION_CANCEL_REMINDER.equals(action)) {
                                int returnCode = mAlarmManager
                                        .cancel(AlarmSet.defaultAlarmCode);
                                if (returnCode == AlarmSet.cancelError) {
                                        PlayTTS(this.getString(R.string.sorry_not_can_cancle_remind));
                                }
                                else {
                                        PlayTTS(this.getString(R.string.remind_was_cancle));
                                        Logger.d(TAG, "ACTION_CANCEL_REMINDER SUCCESS");
                                }

                        }

                }
        }

        @Override
        public IBinder onBind(Intent arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        private Alarm getAlarm() {
                Alarm mAlarm = AlarmSet.getAlarm(getContentResolver(),
                        AlarmSet.defaultAlarmCode);
                Logger.i(TAG,
                        "MSG_HANDLE_REGISTER_ALARM register 111mAlarm = " + mAlarm);
                return mAlarm;
        }

        private void registerNexttime(Alarm Ralarm) {
                Calendar mCalendar = Calendar.getInstance();
                Calendar mAlarmCalendar = Calendar.getInstance();
                Calendar mTempCalendar = Calendar.getInstance();
                long nowtimes = mCalendar.getTimeInMillis();
                long alarmtimes = Ralarm.time;
                String nowtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS",
                        Locale.CHINA)).format(nowtimes);
                String alarmtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS",
                        Locale.CHINA)).format(alarmtimes);

                mAlarmCalendar.setTimeInMillis(Ralarm.time);
                Logger.e(TAG, "registerNexttime nowtime : " + nowtime);
                Logger.e(TAG, "registerNexttime alarmtime : " + alarmtime);
                int mAlarmMonth = mAlarmCalendar.get(Calendar.MONTH);
                int mAlarmDay = mAlarmCalendar.get(Calendar.DAY_OF_MONTH);
                int mAlarmHour = mAlarmCalendar.get(Calendar.HOUR_OF_DAY);
                int mAlarmMinute = mAlarmCalendar.get(Calendar.MINUTE);
                int mAlarmSecond = mAlarmCalendar.get(Calendar.SECOND);

                int mTempMonth = mTempCalendar.get(Calendar.MONTH);
                int mTempDay = mTempCalendar.get(Calendar.DAY_OF_MONTH);
                int mTempHour = mTempCalendar.get(Calendar.HOUR_OF_DAY);
                int mTempMinute = mTempCalendar.get(Calendar.MINUTE);
                int mTempSecond = mTempCalendar.get(Calendar.SECOND);

                if (Ralarm.daysOfWeek.isRepeatSet()) {
                        int tempDays = Ralarm.daysOfWeek.getNextAlarm(mCalendar);
                        if (tempDays == 0) {
                                tempDays = +7;
                        }
                        if (isMissedTime(0, 0, mTempHour, mTempMinute, mTempSecond, 0, 0,
                                mAlarmHour, mAlarmMinute, mAlarmSecond)) {
                                mCalendar.add(Calendar.DAY_OF_YEAR, tempDays);
                        }
                        mCalendar.set(Calendar.HOUR_OF_DAY, mAlarmHour);
                        mCalendar.set(Calendar.MINUTE, mAlarmMinute);
                        mCalendar.set(Calendar.SECOND, mAlarmSecond);
                }
                else if (Ralarm.isYMD == AlarmSet.oneYearAgain) {
                        if (isMissedTime(mTempMonth, mTempDay, mTempHour, mTempMinute,
                                mTempSecond, mAlarmMonth, mAlarmDay, mAlarmHour,
                                mAlarmMinute, mAlarmSecond)) {
                                mCalendar.add(Calendar.YEAR, 1);
                        }
                        mCalendar.set(Calendar.MONTH, mAlarmMonth);
                        mCalendar.set(Calendar.DAY_OF_MONTH, mAlarmDay);
                        mCalendar.set(Calendar.HOUR_OF_DAY, mAlarmHour);
                        mCalendar.set(Calendar.MINUTE, mAlarmMinute);
                        mCalendar.set(Calendar.SECOND, mAlarmSecond);

                }
                else if (Ralarm.isYMD == AlarmSet.oneMonthAgain) {

                        if (isMissedTime(0, mTempDay, mTempHour, mTempMinute, mTempSecond,
                                0, mAlarmDay, mAlarmHour, mAlarmMinute, mAlarmSecond)) {
                                mCalendar.add(Calendar.MONTH, 1);
                        }
                        mCalendar.set(Calendar.DAY_OF_MONTH, mAlarmDay);
                        mCalendar.set(Calendar.HOUR_OF_DAY, mAlarmHour);
                        mCalendar.set(Calendar.MINUTE, mAlarmMinute);
                        mCalendar.set(Calendar.SECOND, mAlarmSecond);

                }
                else if (Ralarm.isYMD == AlarmSet.oneDayAgain) {
                        if (isMissedTime(0, 0, mTempHour, mTempMinute, mTempSecond, 0, 0,
                                mAlarmHour, mAlarmMinute, mAlarmSecond)) {
                                mCalendar.add(Calendar.DAY_OF_YEAR, 1);
                        }
                        mCalendar.set(Calendar.HOUR_OF_DAY, mAlarmHour);
                        mCalendar.set(Calendar.MINUTE, mAlarmMinute);
                        mCalendar.set(Calendar.SECOND, mAlarmSecond);
                }
                else {
                        if ((alarmtimes - nowtimes) < 10 * 1000) {
                                if (mAlarmPlayerHandler != null) {
                                        mAlarmPlayerHandler
                                                .sendEmptyMessage(MSG_HANDLE_CANCLE_ALARM);
                                }
                                Logger.d(TAG, "The alarm clock time has passed");
                        }
                        else {
                                if (isFirstChange) {
                                        Logger.d(TAG, "first re-register nowTime-alarm");
                                        mRegisterAlarm = Ralarm;
                                        if (mAlarmPlayerHandler != null) {
                                                mAlarmPlayerHandler
                                                        .sendEmptyMessage(MSG_HANDLE_REGISTER_ALARM);
                                        }
                                }
                                else {
                                        Logger.d(TAG, "Don't need to re-register nextTime-alarm");
                                }
                                isFirstChange = false;
                        }
                        return;
                }
                Long nextTime = mCalendar.getTimeInMillis();
                String mNexttime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS",
                        Locale.CHINA)).format(nextTime);
                Logger.d(TAG, "registerNexttime mNexttime : " + mNexttime);
                Ralarm.time = nextTime;
                mRegisterAlarm = Ralarm;
                if (mAlarmPlayerHandler != null) {
                        mAlarmPlayerHandler.sendEmptyMessage(MSG_HANDLE_REGISTER_ALARM);
                }
        }

        @SuppressWarnings("deprecation")
        private boolean isMissedTime(int nowMonth, int nowDay, int nowHour,
                                     int nowMinute, int nowSecond, int alarmMonth, int alarmDay,
                                     int alarmHour, int alarmMinute, int alarmSecond) {
                boolean isMissed = false;

                Date now_time = new Date(0, nowMonth, nowDay, nowHour, nowMinute,
                        nowSecond);
                Date alarm_time = new Date(0, alarmMonth, alarmDay, alarmHour,
                        alarmMinute, alarmSecond);
                isMissed = !alarm_time.after(now_time);
                return isMissed;
        }

}
