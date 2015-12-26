package ch03.common;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioClip 
{
	private MediaPlayer mPlayer;
	private String name;
	
	private boolean mPlaying = false;
	private boolean mLoop = false;
	
	public AudioClip(Context ctx, int resID) {
		name = ctx.getResources().getResourceName(resID);
		
		mPlayer = MediaPlayer.create(ctx, resID);
		mPlayer.setVolume(1000, 1000);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlaying = false;
				if ( mLoop) {
					System.out.println("AudioClip loop " + name);
					mp.start();
				}
			}
			
		});
	}
	
	public synchronized void play () {
		if ( mPlaying) 
			return;
		
		if (mPlayer != null ) {
			mPlaying = true;
			mPlayer.start();
		}
	}
	
	public synchronized void stop() {
		try {
			mLoop = false;
			if ( mPlaying ) { 
				mPlaying = false;
				mPlayer.pause();
			}
			
		} catch (Exception e) {
			System.err.println("AduioClip::stop " + name + " " + e.toString());
		}
	}
	
	public synchronized void loop () {
		mLoop = true;
		mPlaying = true;
		mPlayer.start();		
		
	}
	
	public void release () {
		if (mPlayer != null) { 
			mPlayer.release();
			mPlayer = null;
		}
	}
}
