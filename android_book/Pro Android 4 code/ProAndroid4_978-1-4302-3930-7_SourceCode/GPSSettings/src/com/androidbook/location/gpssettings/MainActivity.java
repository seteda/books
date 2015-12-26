package com.androidbook.location.gpssettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForResult(
            new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
                ), 0);
    }
}