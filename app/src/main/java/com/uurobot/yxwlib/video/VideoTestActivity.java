package com.uurobot.yxwlib.video;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;


/**
 * Created by Administrator on 2017/12/5.
 */

public class VideoTestActivity extends Activity {

        private SurfaceView surfaceView;

        private static final String TAG = "VideoTestActivity";

        Surface surface;

        private ByteBuffer[] buf;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.videotest);
                surfaceView = findViewById(R.id.surfaceView);
                surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {
                                 surface = holder.getSurface();
                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                try {
                                        initPlay();
                                }
                                catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                }
                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {

                        }
                });

        }

        private void initPlay() throws FileNotFoundException {
                final MediaExtractor mediaExtractor = new MediaExtractor();
                AssetManager assets = getAssets();
                try {
                        AssetFileDescriptor file = assets.openFd("vivi3.mp4");
                        mediaExtractor.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                }
                catch (IOException e) {
                        e.printStackTrace();
                        Logger.e(TAG, "----" + e.toString());
                }
                int track = 0;
                Logger.e(TAG, "trackFormat======before");
                MediaCodec mediaCodec = null;
                for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                        MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                        String string = mediaFormat.getString(MediaFormat.KEY_MIME);
                        if (string.contains("video")) {
                                track = i;
                                //视频长度:秒
                                float time = mediaFormat.getLong(MediaFormat.KEY_DURATION) / 1000000;
                                Logger.e(TAG, "trackFormat======" + mediaFormat + "  time=" + time);
                                mediaExtractor.selectTrack(i);
                                try {
                                        mediaCodec = MediaCodec.createDecoderByType(string);
                                        mediaCodec.configure(mediaFormat, surface, null, 0);
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                }
                                break;
                        }

                }

                if (mediaCodec==null){
                        Logger.e(TAG,"mediaCodec is null.............");
                        return;
                }
                mediaCodec.start();
//                buf = ByteBuffer.allocate(1024 * 1024); // 这里不能太小，否则会报错
                final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                buf = mediaCodec.getInputBuffers();

                Logger.e(TAG,"...........start............");
                final MediaCodec finalMediaCodec = mediaCodec;
                new Thread(new Runnable() {
                        boolean isVideoEOS = false;
                        @Override
                        public void run() {
                                while (true) {
                                        if (!isVideoEOS) {
                                                isVideoEOS = putBufferToCoder(mediaExtractor, finalMediaCodec, buf);
                                        }
                                        int outputBufferIndex = finalMediaCodec.dequeueOutputBuffer(bufferInfo, 10000);
                                }

                        }
                }).start();

        }

        //将缓冲区传递至解码器
        private boolean putBufferToCoder(MediaExtractor extractor, MediaCodec decoder, ByteBuffer[] inputBuffers) {
                boolean isMediaEOS = false;
                int inputBufferIndex = decoder.dequeueInputBuffer(10000);
                if (inputBufferIndex >= 0) {
                        ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                        int sampleSize = extractor.readSampleData(inputBuffer, 0);
                        if (sampleSize < 0) {
                                decoder.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                                isMediaEOS = true;
                                Logger.v(TAG, "media eos");
                        } else {
                                decoder.queueInputBuffer(inputBufferIndex, 0, sampleSize, extractor.getSampleTime(), 0);
                                extractor.advance();
                                Logger.v(TAG, "....next.......");
                        }
                }
                return isMediaEOS;
        }
}
