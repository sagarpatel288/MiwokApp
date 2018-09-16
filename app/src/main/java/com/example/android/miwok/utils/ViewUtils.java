package com.example.android.miwok.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.android.miwok.constants.AppConstants;

public class ViewUtils {
    public static void loadFragment(int containerResId, FragmentManager fragmentManager, Fragment fragment) {
        if (containerResId != 0 && containerResId != AppConstants.NULL) {
            if (fragmentManager != null) {
                if (fragment != null) {
                    fragmentManager.beginTransaction()
                            .replace(containerResId, fragment)
                            .commit();
                } else {
                    Log.d(AppConstants.TAG, "loadFragment: null fragment");
                }
            } else {
                Log.d(AppConstants.TAG, "loadFragment: null fragment manager");
            }
        } else {
            Log.d(AppConstants.TAG, "loadFragment: null container");
        }
    }
}
