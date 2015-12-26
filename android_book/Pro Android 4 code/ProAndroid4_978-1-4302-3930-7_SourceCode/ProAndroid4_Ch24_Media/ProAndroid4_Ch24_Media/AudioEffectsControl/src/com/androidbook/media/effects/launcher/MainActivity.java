package com.androidbook.media.effects.launcher;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent intent = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
        startActivityForResult(intent, 0);
    }
}