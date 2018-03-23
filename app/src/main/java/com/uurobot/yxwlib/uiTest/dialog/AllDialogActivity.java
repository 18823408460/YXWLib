package com.uurobot.yxwlib.uiTest.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.alarm.Logger;
import com.uurobot.yxwlib.util.ToastUtil;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/23.
 */

public class AllDialogActivity extends Activity {

        private static final java.lang.String TAG = "AllDialogActivity";

        @BindView(R.id.button6)
        Button button6;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_all_dialog);
                ButterKnife.bind(this);
                play();
        }

        @OnClick(R.id.button6)
        public void setButton6(){
//                choiceDialog();
        }

        private void play(){
//                final MediaPlayer mMediaPlayer = new MediaPlayer();
//                if (mMediaPlayer != null) {
//                        try {
//                                String path = Environment.getExternalStorageDirectory()+"/ai/ai05res/a1/res/audio/dance/chisx.mp3";
////                                String path = Environment.getExternalStorageDirectory()+"/ai/chisx.mp3";
//
////                                AssetManager assets = getAssets();
////                                AssetFileDescriptor open = assets.openFd("vivi3.mp4");
////
////                                mMediaPlayer.setDataSource(open.getFileDescriptor(),0,open.getLength()/2);
//                                mMediaPlayer.setDataSource(path);
//
//                                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                        @Override
//                                        public void onPrepared(MediaPlayer mp) {
//                                                Log.e(TAG, "onPrepared: " );
//                                                mMediaPlayer.start();
//                                        }
//                                });
//                                mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                                        @Override
//                                        public boolean onError(MediaPlayer mp, int what, int extra) {
//                                                Log.e(TAG, "onError: ");
//                                                return false;
//                                        }
//                                });
//                                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                        @Override
//                                        public void onCompletion(MediaPlayer mp) {
//                                                Log.e(TAG, "onCompletion: " );
//                                        }
//                                });
//
//                        } catch (IOException e) {
//                                e.printStackTrace();
//                                Logger.e(TAG, "play Exception---"+e);
//                        }
//                }

                File file = new File(Environment.getExternalStorageDirectory()+"/ai/ai05res/a1/res/audio/dance/mj.wav");
                final MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.fromFile(file));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                                mediaPlayer.start();
                        }
                });

        }

        private void baseDialog() {
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .content(R.string.content)
                        .positiveText(R.string.comfirm)
                        .negativeText(R.string.cancel)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                        new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        ToastUtil.getToastUtil().show("cancel");
                                                }
                                        }).start();

                                }
                        })
                        .show();
        }

        private void baseDialogIcon() {
                //title 旁边有个 icon
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .content(R.string.content)
                        .positiveText(R.string.comfirm)
                        .negativeText(R.string.cancel)
                        .iconRes(R.drawable.icon_lawyer)
                        .neutralText(R.string.more)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                        new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        ToastUtil.getToastUtil().show("cancel");
                                                }
                                        }).start();

                                }
                        })
                        .show();
        }


        private void choiceDialog() {
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog)
                        .items(R.array.items)
                        .show();
        }

}
