package com.androidbook.preferences.sample;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class FoodPreferenceActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.foodoptions1);
    }
}
