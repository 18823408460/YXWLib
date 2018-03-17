/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uurobot.yxwlib.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.uurobot.yxwlib.R;

/**
 * Manages alarms and vibe. Runs as a service so that it can continue to play if
 * another activity_card overrides the AlarmAlert dialog.
 */
public class AlarmPlayer {

    private static final String TAG = "AlarmPlayer";

    private boolean mPlaying = false;
    private MediaPlayer mMediaPlayer;
    private TelephonyManager mTelephonyManager;
    private int mInitialCallState;
    private AudioManager mAudioManager = null;
    private PhoneStateListener mPhoneStateListener = null;
    private OnAudioFocusChangeListener mAudioFocusListener = null;
    private boolean mCurrentStates = true;
    private Context mContext;
    private Uri alertUri = null;
    private static final float IN_CALL_VOLUME = 0.125f;
    // Internal messages
    private static final int FOCUSCHANGE = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case FOCUSCHANGE:
                switch (msg.arg1) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    Logger.v(TAG, "AlarmPlayer AUDIOFOCUS_LOSS");
                    if (!mPlaying && mMediaPlayer != null) {
                        stop();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    Logger.v(TAG,
                            "AlarmPlayer AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Logger.v(TAG, "AlarmPlayer AUDIOFOCUS_LOSS_TRANSIENT");
                    if (!mPlaying && mMediaPlayer != null) {
                        mMediaPlayer.pause();
                        mCurrentStates = false;
                    }
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    Logger.v(TAG, "AlarmPlayer AUDIOFOCUS_GAIN");
                    if (mPlaying && !mCurrentStates) {
                        play();
                    }
                    break;
                default:

                    break;
                }
            default:
                break;

            }
        }
    };

    public AlarmPlayer(Context context) {
        mContext = context;
        alertUri = null;

    }

    public void init() {
        Logger.v(TAG, "AlarmPlayer init");
        mAudioManager = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);

        // Listen for incoming calls to kill the alarm.
        mTelephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);

        mInitialCallState = mTelephonyManager.getCallState();

        mPhoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String ignored) {
                Logger.v(TAG, "onCallStateChanged state= " + state);
                if (state != TelephonyManager.CALL_STATE_IDLE
                        && state != mInitialCallState) {
                    StopAlarm();
                }
            }
        };

        mTelephonyManager.listen(mPhoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);
        // Volume suggested by media team for in-call alarms.

        mAudioFocusListener = new OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                Logger.v(TAG, "onAudioFocusChange focusChange= " + focusChange);
                // mHandler.obtainMessage(FOCUSCHANGE, focusChange,
                // 0).sendToTarget();
                StopAlarm();
            }
        };
    }

    public void play() {

        Logger.v(TAG, "AlarmPlayer play() ");
        // stop() checks to see if we are already playing.
        mAudioManager.requestAudioFocus(mAudioFocusListener,
                AudioManager.STREAM_ALARM,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        stop();
        mInitialCallState = mTelephonyManager.getCallState();
        // Fall back on the default alarm if the database does not have an
        // alarm stored.
        if (alertUri == null) {
            alertUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);
            Logger.v(TAG, "Using default alarm: " + alertUri.toString());
        }

        // TODO: Reuse mMediaPlayer instead of creating a new one and/or use
        // RingtoneManager.
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Logger.v(TAG, "Error occurred while playing audio.");
                mp.stop();
                mp.release();
                mMediaPlayer = null;
                return true;
            }
        });

        try {
            // Check if we are in a call. If we are, use the in-call alarm
            // resource at a low volume to not disrupt the call.
            if (mTelephonyManager
                    .getCallState() != TelephonyManager.CALL_STATE_IDLE) {
                Logger.v(TAG, "Using the in-call alarm");
                mMediaPlayer.setVolume(IN_CALL_VOLUME, IN_CALL_VOLUME);
                setDataSourceFromResource(mContext.getResources(), mMediaPlayer,
                        R.raw.in_call_alarm);
            } else {
                mMediaPlayer.setDataSource(mContext, alertUri);
            }
            startAlarm(mMediaPlayer);
        } catch (Exception ex) {
            Logger.v(TAG, "Using the fallback ringtone");
            // The alert may be on the sd card which could be busy right
            // now. Use the fallback ringtone.
            try {
                // Must reset the media player to clear the error state.
                mMediaPlayer.reset();
                setDataSourceFromResource(mContext.getResources(), mMediaPlayer,
                        R.raw.fallbackring);
                startAlarm(mMediaPlayer);
            } catch (Exception ex2) {
                // At this point we just don't play anything.
                Logger.v(TAG, "Failed to play fallback ringtone" + ex2);
            }
        }

        mPlaying = true;

    }

    // Do the common stuff when starting the alarm.
    private void startAlarm(MediaPlayer player) throws java.io.IOException,
            IllegalArgumentException, IllegalStateException {
        final AudioManager audioManager = (AudioManager) mContext
                .getSystemService(Context.AUDIO_SERVICE);
        // do not play alarms if stream volume is 0
        // (typically because ringer mode is silent).
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            player.prepare();
            player.start();
        }
    }

    private void setDataSourceFromResource(Resources resources,
            MediaPlayer player, int res) throws java.io.IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());
            afd.close();
        }
    }

    public void StopAlarm() {
        stop();
        // Stop listening for incoming calls.
        mTelephonyManager.listen(mPhoneStateListener, 0);
        mAudioManager.abandonAudioFocus(mAudioFocusListener);
    }

    /**
     * Stops alarm audio and disables alarm if it not snoozed and not repeating
     */
    private void stop() {
        Logger.v(TAG, "AlarmPlayer.stop()");
        if (mPlaying) {
            mPlaying = false;
            // Stop audio playing
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }

    }

}
