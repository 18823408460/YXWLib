package com.uurobot.yxwlib.aildlTest;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by WEI on 2018/4/1.
 */

public class AIDLService extends Service {
    private static AIDLService instance ;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //*********方法一*******************************
    // aidl其实就是一个 RPC 远程方法的调用，当用户binder service的时候，就可以返回
    // service 端的 IBinder，这个自定义的Binder中有很多的方法可以被调用，client有了
    // 这个binder对象就可以调用里面的的方法了。。 如果需要service去回调Client，只需要在
    // Binder的其中一个方法中增加一个参数（aidl Interface类型），这样service就可以将参数异步返回给client。。。
    private class LocalBinder extends Binder{
        public AIDLService getService(){
            return AIDLService.this ;
        }
    }

    //**********方法2 ***************************
    // public static abstract class Stub extends android.os.Binder implements com.unisrobot.uurobot_u5.IMyAidlInterface

}
