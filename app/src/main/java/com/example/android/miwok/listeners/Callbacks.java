package com.example.android.miwok.listeners;

public abstract class Callbacks {

    public interface OnChangeAudioFocusState {
        void onTransientLoss();

        void onLoss();

        void onTransientLossCanDuck();

        void onGain();
    }
}
