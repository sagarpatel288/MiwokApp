/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.example.android.miwok.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.miwok.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.numbers)
    TextView numbers;
    @BindView(R.id.family)
    TextView family;
    @BindView(R.id.colors)
    TextView colors;
    @BindView(R.id.phrases)
    TextView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.numbers, R.id.family, R.id.colors, R.id.phrases})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.numbers:
                startActivity(NumbersActivity.class);
                break;
            case R.id.family:
                startActivity(FamilyActivity.class);
                break;
            case R.id.colors:
                startActivity(ColorsActivity.class);
                break;
            case R.id.phrases:
                startActivity(PhrasesActivity.class);
                break;
        }
    }

    private void startActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
