package com.androidbook.preferences.savestate;

// This file is MainActivity.java
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String TAG = "SavingState";
	final String INITIALIZED = "initialized";
	private String someString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);

        boolean hasPreferences = myPrefs.getBoolean(INITIALIZED, false);

        if(hasPreferences) {
            Log.v(TAG, "We've been called before");
            // Read other values as desired from preferences file…
            someString = myPrefs.getString("someString", "");
        }
        else {
            Log.v(TAG, "First time ever being called");
            // Set up initial values for what will end up
            // in the preferences file
            someString = "some default value";
        }
        
        Log.v(TAG, "someString = " + someString);

        // Later when ready to write out values
        Editor editor = myPrefs.edit();
        editor.putBoolean(INITIALIZED, true);
        editor.putString("someString", someString);
        // Write other values as desired
        editor.commit();
    }
}