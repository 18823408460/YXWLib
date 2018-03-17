package com.evcall.mobp2p.base;

import android.app.Application;

import com.avcon.base.AvcApp;

import org.utils.BuildConfig;
import org.utils.x;

/**
 * Created by ljx on 2016/12/30.
 */
public class App extends Application {
    public static MyCore CORE;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this); // 这一步之后, 我们就可以在任何地方使用x.app()来获取Application的实例了.
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志
        AvcApp.onCreate(this);
        CORE = new MyCore(this);
    }

    @Override
    public void onTerminate() {
        CORE.onLoginOut();
        AvcApp.onDestory();
        super.onTerminate();
    }
}
