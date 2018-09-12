package com.example.android.miwok.utils;

import android.media.MediaPlayer;

public class Utils {

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
