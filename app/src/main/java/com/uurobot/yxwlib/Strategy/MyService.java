package com.uurobot.yxwlib.Strategy;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by Administrator on 2017/10/28.
 */

public class MyService  extends Service {

        private static final String TAG = "MyService";

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
                Log.e(TAG, "onBind: " );
                return new Binder();
        }

        private class MyBinder extends  Binder{

        }


        @Override
        public void onCreate() {
                super.onCreate();
                Log.e(TAG, "onCreate: -----------------------------------" );
        }
}
