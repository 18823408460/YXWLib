package com.uurobot.yxwlib.cameraEncode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.uurobot.yxwlib.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class Client extends Activity {
        ImageView imageView  ;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.client);

                imageView= findViewById(R.id.img);

                new UDPClientThread(new UDPClientThread.IRev() {
                        @Override
                        public void onData(final byte[] data) {
                                runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                imageView.setImageBitmap(bitmap);
                                        }
                                });
                        }
                }).start();
        }
}
