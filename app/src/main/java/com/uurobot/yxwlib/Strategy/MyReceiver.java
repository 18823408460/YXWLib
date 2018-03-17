package com.uurobot.yxwlib.Strategy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/28.
 */

public class MyReceiver extends BroadcastReceiver {

        private static final String TAG = "MyReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "onReceive: " );

                Intent  intent1 = new Intent("com.uurobot.myservice");
                intent1.setPackage("com.uurobot.yxwlib");
                context.getApplicationContext().bindService(intent1,service,Context.BIND_AUTO_CREATE);
        }


        private ServiceConnection  service = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected: " );
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected: " );
                }
        };
}
