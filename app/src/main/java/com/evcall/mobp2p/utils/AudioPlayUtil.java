package com.evcall.mobp2p.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.uurobot.yxwlib.R;


public class AudioPlayUtil {

    static String TAG = "AudioPlayUtil";

    private static AudioPlayUtil audioPlayUtil;
    private MediaPlayer mediaPlayer;

    public static AudioPlayUtil getInstance() {
        if (audioPlayUtil == null) {
            audioPlayUtil = new AudioPlayUtil();
        }
        return audioPlayUtil;
    }

    public void startAudio(Context context) {
        stopAudio();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ring);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
