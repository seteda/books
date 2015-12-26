package com.androidbook.media.soundpool;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements SoundPool.OnLoadCompleteListener {
	private static final int SRC_QUALITY = 0;
    private static final int PRIORITY = 1;
	private SoundPool soundPool = null;
    private AudioManager aMgr;

	private int sid_background;
	private int sid_roar;
	private int sid_bark;
	private int sid_chimp;
	private int sid_rooster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
    	soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, SRC_QUALITY);
    	soundPool.setOnLoadCompleteListener(this);

    	aMgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

    	sid_background = soundPool.load(this, R.raw.crickets, PRIORITY);
 
    	sid_chimp = soundPool.load(this, R.raw.chimp, PRIORITY);
    	sid_rooster = soundPool.load(this, R.raw.rooster, PRIORITY);
    	sid_roar = soundPool.load(this, R.raw.roar, PRIORITY);

    	try {
			AssetFileDescriptor afd = this.getAssets().openFd("dogbark.mp3");
			sid_bark = soundPool.load(afd.getFileDescriptor(), 0, afd.getLength(), PRIORITY);
			afd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//sid_bark = soundPool.load("/mnt/sdcard/dogbark.mp3", PRIORITY);

    	super.onResume();
    }

    public void doClick(View view) {
    	switch(view.getId()) {
    	case R.id.button:
    		if(((ToggleButton)view).isChecked()) {
    			soundPool.autoResume();
    		}
    		else {
    			soundPool.autoPause();
    		}
    		break;
    	}
    }

    @Override
    protected void onPause() {
    	soundPool.release();
    	soundPool = null;
    	super.onPause();
    }

	@Override
	public void onLoadComplete(SoundPool sPool, int sid, int status) {
		Log.v("soundPool", "sid " + sid + " loaded with status " + status);

    	final float currentVolume = ((float)aMgr.getStreamVolume(AudioManager.STREAM_MUSIC)) / 
    	        ((float)aMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

    	if(status != 0)
    		return;
    	if(sid == sid_background) {
    		if(sPool.play(sid, currentVolume, currentVolume,
    				PRIORITY, -1, 1.0f) == 0)
    		    Log.v("soundPool", "Failed to start sound");
    	} else if(sid == sid_chimp) {
    		queueSound(sid, 5000, currentVolume);
    	} else if(sid == sid_rooster) {
    		queueSound(sid, 6000, currentVolume);
    	} else if(sid == sid_roar) {
    		queueSound(sid, 12000, currentVolume);
    	} else if(sid == sid_bark) {
    		queueSound(sid, 7000, currentVolume);
    	}
	}
	
	private void queueSound(final int sid, final long delay, final float volume) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(soundPool == null) return;
	    		if(soundPool.play(sid, volume, volume,
	    				PRIORITY, 0, 1.0f) == 0)
	    		    Log.v("soundPool", "Failed to start sound (" + sid + ")");
	    		queueSound(sid, delay, volume);
			}}, delay);
	}
}