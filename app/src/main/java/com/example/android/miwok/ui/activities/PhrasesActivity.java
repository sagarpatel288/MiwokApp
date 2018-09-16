package com.example.android.miwok.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.miwok.R;
import com.example.android.miwok.ui.fragments.PhraseFragment;
import com.example.android.miwok.utils.ViewUtils;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        ViewUtils.loadFragment(R.id.container, getSupportFragmentManager(), new PhraseFragment());
    }
}
