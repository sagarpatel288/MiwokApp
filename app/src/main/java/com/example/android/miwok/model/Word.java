package com.example.android.miwok.model;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mRawAudioId;

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mRawAudioId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mRawAudioId = mRawAudioId;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mRawAudioId, int mImageResourceId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mRawAudioId = mRawAudioId;
        this.mImageResourceId = mImageResourceId;
    }

    public int getmRawAudioId() {
        return mRawAudioId;
    }

    public void setmRawAudioId(int mRawAudioId) {
        this.mRawAudioId = mRawAudioId;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public void setmDefaultTranslation(String mDefaultTranslation) {
        this.mDefaultTranslation = mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public void setmMiwokTranslation(String mMiwokTranslation) {
        this.mMiwokTranslation = mMiwokTranslation;
    }
}
