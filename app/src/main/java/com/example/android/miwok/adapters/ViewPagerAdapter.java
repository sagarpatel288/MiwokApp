package com.example.android.miwok.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.miwok.ui.fragments.ColorsFragment;
import com.example.android.miwok.ui.fragments.FamilyFragment;
import com.example.android.miwok.ui.fragments.NumbersFragment;
import com.example.android.miwok.ui.fragments.PhraseFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new NumbersFragment();
        } else if (i == 1) {
            return new FamilyFragment();
        } else if (i == 2) {
            return new ColorsFragment();
        } else if (i == 3) {
            return new PhraseFragment();
        }
        return new NumbersFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
