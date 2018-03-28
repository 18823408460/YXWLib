package com.uurobot.yxwlib.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.TimedText;
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
                AssetFileDescriptor fd  = null;
                try {
                        fd = getAssets().openFd("test.mp4");
                        // 如果要截取音频，可以这个处理
                        mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength()/2);
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
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                Log.e(TAG, "onBufferingUpdate: persent==="+percent );
                        }
                });
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                Log.e(TAG, "onInfo: "+what + "   "+extra );
                                return false;
                        }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                                Log.e(TAG, "onCompletion: " );
                        }
                });
                mediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                        @Override
                        public void onTimedText(MediaPlayer mp, TimedText text) {
                                Log.e(TAG, "onTimedText: "+text.getText() );
                        }
                });

        }


}
