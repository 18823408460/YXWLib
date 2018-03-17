package com.uurobot.yxwlib.cameraEncode;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2018/3/7.
 */

public class LocalClient implements Runnable {
        private LocalSocket localSocket ;
        private OutputStream outputStream ;

        private static final String TAG = "LocalClient";
        public LocalClient() {
                this.localSocket = new LocalSocket();

        }

        @Override
        public void run() {
                try {
                        localSocket.connect(new LocalSocketAddress(LocalServer.address));
                        outputStream = localSocket.getOutputStream();
                }
                catch (IOException e) {
                        Log.e(TAG, "run: "+e );
                        e.printStackTrace();
                }
        }

        public void sendData(byte[] data){
                if (outputStream!= null){
                        try {
                                outputStream.write(data);
                        }
                        catch (IOException e) {
                                Log.e(TAG, "sendData: "+e );
                                e.printStackTrace();
                        }
                }
        }
}
