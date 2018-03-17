package com.uurobot.yxwlib.cameraEncode;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/7.
 */

public class LocalServer implements Runnable {
        public static final String address = "com.uurobot.yxwlib";
        private static final String TAG = "LocalServer";
        private LocalServerSocket localServerSocket ;
        private IReceiver iReceiver ;
        public LocalServer(IReceiver iReceiver ) {
                this.iReceiver = iReceiver ;
                try {
                        this.localServerSocket = new LocalServerSocket(address);
                }
                catch (IOException e) {
                        Log.e(TAG,"----"+e);
                        e.printStackTrace();
                }
        }

        @Override
        public void run() {
                if (localServerSocket!=null){
                        try {
                                LocalSocket accept = localServerSocket.accept();
                                accept.setReceiveBufferSize(1024*1024);
                                InputStream inputStream = accept.getInputStream();
                                DataInputStream dataInputStream = new DataInputStream(inputStream);
                                boolean flag = true;
                                while (flag){
                                        int b = dataInputStream.readByte()&0xff;
                                        if (b == 0xa0){
                                                byte b1 = dataInputStream.readByte();
                                                byte b2 = dataInputStream.readByte();
                                                byte b3 = dataInputStream.readByte();
                                                byte b4 = dataInputStream.readByte();


                                                int len = (((b3<<16)&0xff0000)|((b2<<8)&0xff00)|(b1&0xff)) ;





                                                Log.e("ff"+TAG,"1="+b1+"    2="+b2+"   3="+b3+"   4="+b4);
                                                Log.e("ff"+TAG,"1=====read====="+len  );
                                                if (len>0 && len<70000){
                                                        byte[] bytes = new byte[len];
                                                        dataInputStream.readFully(bytes);
//                                                        Log.e(TAG,"=====read====="+len  );
                                                        if (iReceiver != null){
                                                                iReceiver.onReceiver(bytes,bytes.length);
                                                        }
                                                }
                                        }
                                }
                        }
                        catch (IOException e) {
                                Log.e(TAG, "run: "+e );
                                e.printStackTrace();
                        }
                }
        }

        interface IReceiver {
                 void onReceiver(byte[] data,int len);
        }
}
