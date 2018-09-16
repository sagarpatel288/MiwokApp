package com.example.android.miwok.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.miwok.R;
import com.example.android.miwok.ui.fragments.NumbersFragment;
import com.example.android.miwok.utils.ViewUtils;

public class NumbersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        ViewUtils.loadFragment(R.id.container, getSupportFragmentManager(), new NumbersFragment());
    }
}
