package com.uurobot.yxwlib.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.car.BaseActivity;

import java.io.FileDescriptor;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MediaPlayerTest extends BaseActivity {

        private static final String TAG = "MediaPlayerTest";
        @BindView(R.id.button7)
        Button button7;

        @OnClick(R.id.button7)
        public void play(){
                testMediaplayer();
        }

        @OnClick(R.id.button8)
        public void reset(){
                mediaPlayer.reset();
        }


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_media);
                ButterKnife.bind(this);

        }
        MediaPlayer mediaPlayer;
        private void testMediaplayer() {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                                Log.e(TAG, "onError: "+what + "   extra=="+extra );
                                return false;
                        }
                });
//                mediaPlayer.stop();// (new --- stop =  -38)

//              mediaPlayer.reset();(随时可以调用)

                boolean playing = mediaPlayer.isPlaying();//(随时调用)
                Log.e(TAG, "testMediaplayer: "+playing );
                FileDescriptor fd  = null;
                try {
                        fd = getAssets().openFd("vivi3.mp4").getFileDescriptor();
                        mediaPlayer.setDataSource(fd);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "testMediaplayer: e= "+e);
                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                                Log.e(TAG, "onPrepared: " );
                                mediaPlayer.start();
                        }
                });
                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                Log.e(TAG, "onBufferingUpdate: persent==="+percent );
                        }
                });


        }


}
