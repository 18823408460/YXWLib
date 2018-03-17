package com.uurobot.yxwlib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uurobot.yxwlib.Strategy.FanType;
import com.uurobot.yxwlib.Strategy.MyFanType;
import com.uurobot.yxwlib.alarm.AlarmSet;
import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.fragment.FragmentMgr;
import com.uurobot.yxwlib.fragment.INoResutlNoParams;
import com.uurobot.yxwlib.fragment.IResultWithParams;
import com.uurobot.yxwlib.greendao.DaoMaster;
import com.uurobot.yxwlib.greendao.DaoSession;
import com.uurobot.yxwlib.greendao.People;
import com.uurobot.yxwlib.lingju.IChatListener;
import com.uurobot.yxwlib.lingju.LinJuChatUtil;
import com.uurobot.yxwlib.okhttp.OkHttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import top.zibin.luban.Luban;

public class MainActivity extends AppCompatActivity {

        private static final String TAG = "MainActivity";
        private Button  button ;
        LinJuChatUtil linJuChatUtil;
        EditText  editText ;
        private Handler mhandler = new Handler() ;
        private int m_nResult = 0 ;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                initData();
                testHttp();
               // testFinally();
               // editText = (EditText) findViewById(R.id.edit_query);
//               button = (Button) findViewById(R.id.btn);
//                button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                                testAlarm();
////                                testLingju();
////                                testFragmentParmas();
//                                testHttp();
//                        }
//                });


                testHttp();
//                testDesity();

//                testInner();
//                testObject(this);
              //  testFile();
//                testMyReceiver();
//                testString();
        }


        private void testString(){
                String[] temps = new String[]{"11","22"};
                for (String temp:
                     temps) {
                        Log.e(TAG, "testString: temp = "  +temp);
                }
                Log.e(TAG, "testString: "+ getResources().getString(R.string.remind_was_cancle) );
        }

        private void testInner(){
                new Manager().doWork();
        }


        private void testFile(){
                File file = new File("");
                try {
                        FileInputStream in = new FileInputStream(file);
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e(TAG, "testFile: fileNotFoundException..........");
                        return;
                }finally {
                        Log.e(TAG, "testFile: finally");
                }
        }


        private void testObject(Object object){
              new MyFanType().parse();
        }

        private class Manager{
                int temp = 0 ;
                public Manager(){
                        m_nResult = 0;
                }
                public void doWork(){
                        m_nResult = m_nResult++ ;
                        temp = m_nResult;
                        Log.e(TAG, "doWork: m_nResult  = " +m_nResult );
                        Log.e(TAG, "doWork: temp  = " +temp );
                }
        }



        private void initData(){
                linJuChatUtil = new LinJuChatUtil(getApplication(), new IChatListener() {

                @Override
                public void onResponseResult(boolean isSuccess, String result, String session) {
                        Logger.d(TAG, "resutl  ==" + result);
                }
        }, new Handler());

        }


        private void testHttp() {
                String url = "http://120.76.40.84:6026/api/urobot/sa.json?engineid=c898501b014e1000e0000000c0a8ba04&uuid=3443434554353&question=拔菠萝";

                String tt = "http://120.76.133.10:8080/mobile_pad/getAllVersion";

                OkHttpUtils.getInstance().getAsyn(this, TAG, tt, new OkHttpUtils.OkCallBack() {
                        @Override
                        public void onBefore(Request var1, Object var2) {
                                Log.e(TAG, "onBefore: ");
                        }

                        @Override
                        public void onAfter(Object var1) {
                                Log.e(TAG, "onAfter: ");
                        }

                        @Override
                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                Log.e(TAG, "onError: ");
                        }

                        @Override
                        public void onResponse(Object var1, Object tag) {
                                Log.e(TAG, "tag="+tag+"     onResponse: " + var1);
                        }
                });
        }

        private void testGreenDao(){
                DaoMaster.DevOpenHelper  devOpenHelper =new DaoMaster.DevOpenHelper(getApplication(),"test.db",null);
                SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
                DaoMaster daoMaster =  new DaoMaster(sqLiteDatabase);
                DaoSession daoSession = daoMaster.newSession();

                People people = new People(1L,"a","B");

        }

        private void testLuban(){
//                Luban.with(this).load()
        }


        private void testDesity(){
                float scale = getResources().getDisplayMetrics().density;
                Log.e(TAG, "testDesity:    scale =" + scale );
        }

        private void testAlarm(){
                Logger.e(TAG,"---------"+ System.currentTimeMillis());
                long  data=  System.currentTimeMillis() +60000;

                AlarmSet.registerAlarm(this,Long.toString(data),"","开会");
        }


        private void testLingju(){
                linJuChatUtil.onChat(editText.getText().toString());
        }


        private void testFinally(){
                try {
                        Thread.sleep(1);
                        Logger.e(TAG,"finally1111------------------1");
                        return;
                }
                catch (InterruptedException e) {
                        e.printStackTrace();
                        Logger.e(TAG,"finally1111------------------2");
                }finally {
                        Logger.e(TAG,"finally1111------------------0");
                }

                try {
                        Thread.sleep(1);
                        return;
                }
                catch (InterruptedException e) {
                        e.printStackTrace();
                }finally {
                        Logger.e(TAG,"finally111122222222------------------");
                }

                testMyReceiver();
        }

        private void testMyReceiver(){
                sendBroadcast(new Intent("com.uurobot.test"));

//                Intent  intent1 = new Intent("com.uurobot.myservice");
//                intent1.setPackage("com.uurobot.yxwlib");
//                bindService(intent1,service, Context.BIND_AUTO_CREATE);
        }

        private ServiceConnection service = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected: " );
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected: " );
                }
        };

        private void testFragment(){
                FragmentMgr.getInstance().addLisenter(INoResutlNoParams.TAG, new INoResutlNoParams() {
                        @Override
                        public void function() {
                                Toast.makeText(getApplicationContext(),"hello Fragment",Toast.LENGTH_SHORT).show();
                        }
                });

                FragmentMgr.getInstance().invoke(INoResutlNoParams.TAG);
        }

        private void testFragmentParmas(){
                FragmentMgr.getInstance().addResultWithParamsLisenter(IResultWithParams.TAG, new IResultWithParams<String,Integer>() {
                        @Override
                        public String function(Integer o) {
                                return String.format("%s, hello",Integer.toString(o));
                        }
                });
                String result =  FragmentMgr.getInstance().invokeResultParams(IResultWithParams.TAG,1024);
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }

        private void testRefreshActivity(){
                Intent  intent =  new Intent(this,RefreshActivity.class);
                startActivity(intent);
        }

        @Override
        protected void onPause() {
                super.onPause();
//                unbindService(service);
        }
}




