package wolf.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/*******************************************************************************
 * Copyright (c) 2006 Vladimir Silva and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Vladimir Silva - initial API and implementation
 *******************************************************************************/

public class AudioClip 
{
	static final String TAG = "AudioClip";
	
	private MediaPlayer mPlayer;
	private String name;
	
	private boolean mPlaying = false;
	private boolean mLoop = false;
	
	public AudioClip(Context ctx, int resID) {
		name = ctx.getResources().getResourceName(resID);
		
		mPlayer = MediaPlayer.create(ctx, resID);
		//mPlayer.setVolume(1000, 1000);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlaying = false;
				if ( mLoop) {
					mp.start();
				}
			}
			
		});
	}

	public AudioClip(Context ctx, Uri uri) {
		name = uri.toString();
		
		mPlayer = MediaPlayer.create(ctx, uri);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlaying = false;
				if ( mLoop) {
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
	
	public synchronized void play (int vol) {
		if ( mPlaying) 
			return;
		
		if (mPlayer != null ) {
			mPlaying = true;
			
			//Log.d(TAG, "Play " + name + " vol=" + vol);
			mPlayer.setVolume( (float)Math.log10(vol), (float)Math.log(vol)); 
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
	
	public String getName() {
		return name;
	}
	
	/**
	 * Set volume
	 * @param vol (1-100)
	 */
	public void setVolume (int vol) {
		if ( mPlayer != null) {
			mPlayer.setVolume((float)Math.log10(vol), (float)Math.log10(vol));
		}
	}
}
