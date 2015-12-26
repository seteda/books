package wolf.audio;
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

import game.wolfsw.R;

import java.util.HashMap;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

/**
 * Audio manager. Caches sounds for performance
 *
 */
public class AudioManager 
{
	static final String TAG = "AudioMgr";
	
	static private AudioManager am ;

	// Game sound (WAVs)
	private volatile HashMap<String, AudioClip> mSounds = new HashMap<String, AudioClip>();
	
	private int MAX_CLIPS = 20;
	private int mClipCount = 0;
	private Context mContext;
	

	// BG music
	private AudioClip music;
	
	/**
	 * get Instance
	 * @param ctx
	 * @param wadIdx
	 * @return
	 */
	static public AudioManager getInstance(Context ctx) {
		if ( am == null) return new AudioManager(ctx);
		return am;
	}

	private AudioManager(Context ctx) {
		mContext = ctx;
		preloadSounds(ctx);
	}
	
	/**
	 * Start a sound by name & volume
	 * @param name example "pistol" when firing the gun
	 * @param vol
	 */
	public synchronized void startSound( int sidx) 
	{ 
		if (sidx == 0 ) return;
		
		if ( sidx < 0 ||  sidx > SoundNames.Sounds.length) {
			return;
		}

		
		// The sound key
		int id = SoundNames.Sounds[sidx]; 
		String key;
		
		if ( id == 0 ) return;
		
		try {
			key =  mContext.getResources().getResourceName(id);//  name.toLowerCase();
		} catch (NotFoundException e) {
			return;
		}
		
		
		if ( mSounds.containsKey(key)) {
			//Log.d(TAG, "Playing " + key + " from cache"); 
			mSounds.get(key).play(); 
		}
		else {
			// load clip from disk
			
			// If the sound table is full remove a random entry
			if ( mClipCount > MAX_CLIPS) {
				// Remove a last key
				int idx = mSounds.size() - 1; 
				
				//Log.d(TAG, "Removing cached sound " + idx 
				//		+ " HashMap size=" + mSounds.size());
				
				String k = (String)mSounds.keySet().toArray()[idx];
				AudioClip clip = mSounds.remove(k);
				clip.release();
				clip = null;
				mClipCount--;
			}
			
			//Log.d(TAG, "Play & Cache " + key + " id:" + id);
			
			AudioClip clip = new AudioClip(mContext, id); 
			clip.play(); 
			
			mSounds.put(key, clip);
			mClipCount++;
		}
	}
	

	/**
	 * PreLoad the most used sounds into a hash map
	 * @param ctx
	 * @param wadIdx
	 * @return
	 */
	public void preloadSounds(Context ctx)   
	{
		int[] IDS = new int[] {
				R.raw.doropn, R.raw.dorcls, 
				R.raw.pistol, R.raw.wpnup
		};
		
		// WAVs
		Resources res = mContext.getResources();
		
		for (int i = 0; i < IDS.length; i++ ) { 
			final int id = IDS[i];
			final String key = res.getResourceName(id);
			
			Log.d(TAG,  "PreLoading sound " + key + " ID " + id);
			mSounds.put(key, new AudioClip(ctx, id));
			
		}
	}
	
	/**
	 * Start background music
	 * @param ctx
	 * @param key music key (e.g intro, e1m1)
	 */
	public void startMusic (Context ctx , int midx) {
		// Sound folder
		int id = SoundNames.Music[midx];

		if ( id == 0 ) return;
		
		try {
			mContext.getResources().getResourceName(id);
		} catch (NotFoundException e) {
			System.err.println("Music resID for idx " + midx + " no found");
			return;
		}
		
		if ( music != null) {
			music.stop();
			music.release();
		}
		
		Log.d(TAG, "Starting music " + id);
		music = new AudioClip(ctx, id); //Uri.fromFile( sound ));
		
		music.setVolume(100);
		music.play();
	}

	/**
	 * Stop background music
	 * @param key
	 */
//	public void stopMusic (String key) {
		// Sound folder
//		File folder = DoomTools.getSoundFolder(); 
//		Uri sound = Uri.fromFile(new File(folder +  File.separator + "d1" + key + ".ogg"));
//		
//		if ( music != null  ) {
//			if ( !sound.equals(Uri.parse(music.getName()))) {
//				Log.w(TAG, "Stop music uri "  + sound  + " different than " + music.getName());
//			}
//			music.stop();
//			music.release();
//			music = null;
//		}
//	}

	public void setMusicVolume (int vol) {
		if ( music != null)
			music.setVolume(vol);
		else
			Log.e(TAG, "setMusicVolume " + vol + " called with NULL music player");
	}
	

}
