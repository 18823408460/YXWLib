/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uurobot.yxwlib.alarm;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class Alarm implements Parcelable {

    public int id;
    public int isYMD;
    public DaysOfWeek daysOfWeek;
    public long time;
    public String label;
    public Uri alert;
    private static final String TAG = "Alarm";

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel p) {
            return new Alarm(p);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int flags) {
        p.writeInt(id);
        p.writeInt(isYMD);
        ;
        p.writeInt(daysOfWeek.getCoded());
        p.writeLong(time);
        p.writeString(label);
        p.writeParcelable(alert, flags);
    }

    public Alarm(Cursor c) {
        id = c.getInt(Columns.ALARM_ID_INDEX);
        isYMD = c.getInt(Columns.ALARM_ISYMD_INDEX);
        daysOfWeek = new DaysOfWeek(c.getInt(Columns.ALARM_DAYS_OF_WEEK_INDEX));
        time = c.getLong(Columns.ALARM_TIME_INDEX);
        label = c.getString(Columns.ALARM_LABEL_INDEX);
        String alertString = c.getString(Columns.ALARM_ALERT_INDEX);
        if (alertString != null && alertString.length() != 0) {
            alert = Uri.parse(alertString);
        }
        Logger.i(TAG, "Alarm alertString = " + alertString + " alert=" + alert);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

    }

    public Alarm(Parcel p) {
        id = p.readInt();
        isYMD = p.readInt();
        daysOfWeek = new DaysOfWeek(p.readInt());
        time = p.readLong();
        label = p.readString();
        alert = (Uri) p.readParcelable(null);

    }

    public Alarm() {
        daysOfWeek = new DaysOfWeek(0);
    }

    @Override
    public String toString() {
        return "Alarm  id = " + id + " isYMD=" + isYMD + " daysOfWeek="
                + daysOfWeek.toString() + "  time=" + time + " label=" + label
                + "  alert=" + alert;
    }

    /*
     * Days of week code as a single int. 0x00: no day 0x01: Monday 0x02:
     * Tuesday 0x04: Wednesday 0x08: Thursday 0x10: Friday 0x20: Saturday 0x40:
     * Sunday
     */
    public static final class DaysOfWeek {

        private static int[] DAY_MAP = new int[] { Calendar.MONDAY,
                Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY,
                Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY, };

        // Bitmask of all repeating days
        private int mDays;

        DaysOfWeek(int days) {
            mDays = days;
        }

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            // no days
            if (mDays == 0) {
                return "不重复";
            }

            // every day
            if (mDays == 0x7f) {
                return "每天";
            }

            // count selected days
            int dayCount = 0, days = mDays;
            while (days > 0) {
                if ((days & 1) == 1)
                    dayCount++;
                days >>= 1;
            }

            // short or long form?
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] dayList = (dayCount > 1) ? dfs.getShortWeekdays()
                    : dfs.getWeekdays();

            // selected days
            for (int i = 0; i < 7; i++) {
                if ((mDays & (1 << i)) != 0) {
                    ret.append(dayList[DAY_MAP[i]]);
                    dayCount -= 1;
                    if (dayCount > 0)
                        ret.append(",");
                }
            }
            return ret.toString();
        }

        private boolean isSet(int day) {
            return ((mDays & (1 << day)) > 0);
        }

        public void set(int day, boolean set) {
            if (set) {
                mDays |= (1 << day);
            } else {
                mDays &= ~(1 << day);
            }
        }

        public void setRepateDay(String mRepeatDate) {
            if (mRepeatDate.contains("D1")) {
                set(0, true);
            }
            if (mRepeatDate.contains("D2")) {
                set(1, true);
            }
            if (mRepeatDate.contains("D3")) {
                set(2, true);
            }
            if (mRepeatDate.contains("D4")) {
                set(3, true);
            }
            if (mRepeatDate.contains("D5")) {
                set(4, true);
            }
            if (mRepeatDate.contains("D6")) {
                set(5, true);
            }
            if (mRepeatDate.contains("D7")) {
                set(6, true);
            }
        }


        public int getCoded() {
            return mDays;
        }


        public boolean isRepeatSet() {
            return mDays != 0;
        }

        /**
         * returns number of days from today until next alarm
         * 
         * @param c
         *            must be set to today
         */
        public int getNextAlarm(Calendar c) {
            if (mDays == 0) {
                return -1;
            }

            int today = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;

            int day = 0;
            int dayCount = 0;
            for (; dayCount < 7; dayCount++) {
                day = (today + dayCount) % 7;
                if (isSet(day)) {
                    break;
                }
            }
            return dayCount;
        }
    }

    public static class Columns implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://com.uurobot.yxwlib/alarm");
        public static final String ISYMD = "isYMD";

        public static final String DAYS_OF_WEEK = "daysofweek";

        public static final String TIME = "time";

        public static final String LABEL = "label";

        public static final String ALERT = "alert";

        static final String[] ALARM_QUERY_COLUMNS = { _ID, ISYMD, DAYS_OF_WEEK,
                TIME, LABEL, ALERT };

        /**
         * These save calls to cursor.getColumnIndexOrThrow() THEY MUST BE KEPT
         * IN SYNC WITH ABOVE QUERY COLUMNS
         */
        public static final int ALARM_ID_INDEX = 0;
        public static final int ALARM_ISYMD_INDEX = 1;
        public static final int ALARM_DAYS_OF_WEEK_INDEX = 2;
        public static final int ALARM_TIME_INDEX = 3;
        public static final int ALARM_LABEL_INDEX = 4;
        public static final int ALARM_ALERT_INDEX = 5;
    }

}
