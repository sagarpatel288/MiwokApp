package com.example.android.miwok.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;

import com.example.android.miwok.listeners.Callbacks;

public class MediaHelper {
    private final AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private AudioAttributes audioAttributes;
    private Callbacks.OnChangeAudioFocusState onChangeAudioFocusState;
    private int audioFocus;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    public MediaHelper(Context context, Callbacks.OnChangeAudioFocusState onChangeAudioFocusState) {
        this.onChangeAudioFocusState = onChangeAudioFocusState;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() {
                        @Override
                        public void onAudioFocusChange(int i) {
                            setAudioFocus(i);
                            handleAudioFocus(i, this);
                        }
                    }, new Handler()).build();

            if (audioManager != null) {
                audioManager.requestAudioFocus(audioFocusRequest);
            }
        } else {
            if (audioManager != null) {
                audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        setAudioFocus(i);
                        handleAudioFocus(i, this);
                    }
                }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            }
        }
    }

    private void handleAudioFocus(int audioFocus, AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.onAudioFocusChangeListener = onAudioFocusChangeListener;
        switch (audioFocus) {
            case AudioManager.AUDIOFOCUS_LOSS:
                onChangeAudioFocusState.onLoss();
                abandonAudioFocus();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                onChangeAudioFocusState.onTransientLoss();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                onChangeAudioFocusState.onTransientLossCanDuck();
                break;

            case AudioManager.AUDIOFOCUS_GAIN:
                onChangeAudioFocusState.onGain();
                break;
        }
    }

    public void abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(audioFocusRequest);
        } else {
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    public int getAudioFocus() {
        return audioFocus;
    }

    private void setAudioFocus(int audioFocus) {
        this.audioFocus = audioFocus;
    }

    public static void releaseMediaPlayer(MediaPlayer mediaPlayer, MediaHelper mediaHelper) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaHelper.abandonAudioFocus();
            mediaPlayer = null;
        }
    }

    public static void stopAndReleaseMediaPlayer(MediaPlayer mediaPlayer, MediaHelper mediaHelper) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaHelper.abandonAudioFocus();
        }
    }

    public static void pauseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    public static void resumeMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
