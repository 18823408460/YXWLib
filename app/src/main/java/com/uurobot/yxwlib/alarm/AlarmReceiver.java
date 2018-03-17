/*
 * Copyright (C) 2007 The Android Open Source Project
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

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "AlarmReceiver";
    private static boolean isFirst = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Logger.e(TAG, action);
        if (AlarmSet.ACTION_ALARM_ON.equals(action)) {
            Logger.d(TAG, "ACTION SYSTEM ALARM_ON");
            playMedia(context);
        } else if (Intent.ACTION_TIMEZONE_CHANGED.equals(action)) {
            Logger.d(TAG, "SYSYTEM TIMEZONE_CHANGED");
            registerNexttime(context);
        } else if (Intent.ACTION_TIME_CHANGED.equals(action)) {
            Logger.d(TAG, "SYSYTEM TIME_CHANGED isFirst=" + isFirst);
            if (isFirst) {
                isFirst = false;
                registerNexttime(context);
            } else {
                isFirst = true;
            }
        }
    }

    private void registerNexttime(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(AlarmSet.ACTION_REGISTER_NEXT_ALARM);
        context.startService(intent);
    }

    private void playMedia(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(AlarmSet.ACTION_PLAY_ALARM);
        context.startService(intent);
    }

}
