package doom.jni;

import android.util.Log;

/**
 * Copyright Vladimir Silva 2009
 * You are not authorized to change or distribute thos code without the author's consent.
 * 
 * @author Vladimir Silva
 *
 */
public class Natives 
{
	public static final String TAG = "Natives";
	
	private static EventListener listener;
	
	public static final int EV_KEYDOWN = 0;
	public static final int EV_KEYUP = 1;
	public static final int EV_MOUSE = 2;
	
	public static interface EventListener 
	{
		void OnMessage(String text, int level);
		void OnInitGraphics(int w, int h);
		void OnImageUpdate (int[] pixels);	
		void OnFatalError(String text);
		void OnQuit (int code);
		void OnStartSound(String name, int vol);
		void OnStartMusic (String name, int loop);
		void OnStopMusic (String name);
		void OnSetMusicVolume (int volume);
	}
	

	public static void setListener (EventListener l) {
		listener = l;
	}
	
	/**
	 * Send a key event to the native layer
	 * @param type
	 * @param sym
	 */
	public static void sendNativeKeyEvent (int type, int sym) {
		try {
			Natives.keyEvent(type, sym);
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * Native Main Doom Loop
	 * @param argv
	 * @return
	 */
	public static native int DoomMain(String[] argv);

	/**
	 * Key Event JNI func
	 * @param type event type: UP/DOWN
	 * @param key ASCII symbol
	 */
	public static native int keyEvent(int type, int key);
	
	/**
	 * Motion Event
	 * @param b Mouse button 1,2,4
	 * @param x X coord
	 * @param y Y coord
	 * @return
	 */
	public static native int motionEvent(int b, int x, int y);
	
	/**
	 * Set Video Mode
	 * @param w width
	 * @param h height
	 * @return
	 */
	public static native int setVideoMode(int w, int h);

	/***********************************************************
	 * C - Callbacks
	 ***********************************************************/
	
	/**
	 * This fires on messages from the C layer
	 * @param text
	 */
	@SuppressWarnings("unused")
	private static void OnMessage(String text, int level) {
		if ( listener != null)
			listener.OnMessage(text, level);
	}
	
	@SuppressWarnings("unused")
	private static void OnInitGraphics(int w, int h) {
		if ( listener != null)
			listener.OnInitGraphics(w, h);
	}
	
	@SuppressWarnings("unused")
	private static void OnImageUpdate(int[] pixels) {
		if ( listener != null)
			listener.OnImageUpdate(pixels);

	}
	
	/**
	 * Fires when the C lib calls exit()
	 * @param message
	 */
	@SuppressWarnings("unused")
	private static void OnFatalError(String message) {
		if ( listener != null)
			listener.OnFatalError(message);
	}

	/**
	 * Fires when Doom Quits
	 * TODO: No yet implemented in the JNI lib
	 * @param code
	 */
	@SuppressWarnings("unused")
	private static void OnQuit(int code) {
		if ( listener != null)
			listener.OnQuit(code);
	}
	
	/**
	 * Fires when a sound is played in the C layer.
	 * @param name Sound name (e.g pistol)
	 * @param volume (1-100)
	 */
	@SuppressWarnings("unused")
	private static void OnStartSound(byte[] name, int vol) {
		if ( listener != null)
			listener.OnStartSound(new String( name), vol);
	}
	
	/**
	 * Start background music callback
	 * @param name
	 * @param loop
	 */
	@SuppressWarnings("unused")
	private static void OnStartMusic(String name, int loop) {
		if ( listener != null)
			listener.OnStartMusic(name, loop);
	}

	/**
	 * Stop bg music
	 * @param name
	 */
	@SuppressWarnings("unused")
	private static void OnStopMusic(String name) {
		if ( listener != null)
			listener.OnStopMusic(name);
	}
	
	
	/**
	 * Set bg music volume
	 * @param volume Range: (0-15)
	 */
	@SuppressWarnings("unused")
	private static void OnSetMusicVolume(int volume) {
		if ( listener != null)
			listener.OnSetMusicVolume((int)(volume * 100.0/15.0));
	}
	
}
