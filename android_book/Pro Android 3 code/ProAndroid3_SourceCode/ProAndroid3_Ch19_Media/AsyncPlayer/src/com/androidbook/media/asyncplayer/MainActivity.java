package com.androidbook.media.asyncplayer;

import android.app.Activity;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

public class MainActivity extends Activity {
    private static final String TAG = "AsyncPlayerDemo";
    private AsyncPlayer mAsync = null;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAsync = new AsyncPlayer(TAG);
        mAsync.play(this, Uri.parse("file://" + 
        		Environment.getExternalStoragePublicDirectory(
        				Environment.DIRECTORY_RINGTONES) + 
        				"/my_ringtone.mp3"),			// use a suitable file for your device
                false, AudioManager.STREAM_MUSIC);
    }
    
    @Override
    protected void onPause() {
    	mAsync.stop();
    	super.onPause();
    }
}