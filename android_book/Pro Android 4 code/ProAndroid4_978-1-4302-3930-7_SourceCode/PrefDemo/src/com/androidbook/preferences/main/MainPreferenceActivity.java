package com.androidbook.preferences.main;

import com.androidbook.preferences.main.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainPreferenceActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);
    }
}
