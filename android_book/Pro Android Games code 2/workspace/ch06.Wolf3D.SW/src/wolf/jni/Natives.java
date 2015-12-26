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

package wolf.jni;

import android.util.Log;

/**
 * Copyright Vladimir Silva 2009
 * You are not authorized to change or distribute this code without the author's consent.
 * 
 * @author Vladimir Silva
 *
 */

public class Natives 
{
	public static final String TAG = "Natives";
	
	public static final int EV_KEYDOWN = 0;
	public static final int EV_KEYUP = 1;
	public static final int EV_MOUSE = 2;
	
	private static EventListener listener;
	
	public static interface EventListener 
	{
		void OnMessage(String text);
		void OnInitGraphics(int w, int h);
		void OnImageUpdate (int[] pixels, int x ,int y, int w, int h);	
		void OnSysError(String text);
		void OnStartSound(int idx);
		void OnStartMusic(int idx);
	}
	
	public static void setListener (EventListener l) {
		listener = l;
	}
	
	/**
	 * 
	 * @param argv
	 * @return
	 */
	public static native int WolfMain(String[] argv);
	
	/**
	 * Key press
	 * @param key ascii code
	 * @return
	 */
	public static native int keyPress(int key);
	
	/**
	 * Key release
	 * @param key ascii code
	 * @return
	 */
	public static native int keyRelease(int key);
	
	/**
	 * Motion Event
	 * @param b Mouse button 1,2,4
	 * @param x X coord
	 * @param y Y coord
	 * @return
	 */
	public static native int motionEvent(int b, int x, int y);
	
	
//	public static native int strafeLeft();
//	public static native int strafeRight();
	

	/***********************************************************
	 * C - Callbacks
	 ***********************************************************/
	
	/**
	 * This fires on messages from the C layer
	 * @param text
	 */
	@SuppressWarnings("unused")
	private static void OnMessage(String text) {
		if ( listener != null)
			listener.OnMessage(text);
	}
	
	/**
	 * Fires on init graphics
	 * @param w width of the image
	 * @param h
	 */
	@SuppressWarnings("unused")
	private static void OnInitGraphics(int w, int h) {
		if ( listener != null)
			listener.OnInitGraphics(w, h);
	}
	
	/**
	 * Fires on image update
	 * @param pixels
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	@SuppressWarnings("unused")
	private static void OnImageUpdate(int[] pixels, int x ,int y, int w, int h) {
		if ( listener != null)
			listener.OnImageUpdate(pixels, x, y, w, h);

	}
	
	/**
	 * Fires when the C lib calls exit()
	 * @param message
	 */
	@SuppressWarnings("unused")
	private static void OnSysError(String message) {
		if ( listener != null)
			listener.OnSysError(message + " - Please report this error.");
	}
	
	/**
	 * Fires when a sound is played in the C layer.
	 * @param name Sound name (e.g pistol)
	 * @param volume (0-255)
	 */
	@SuppressWarnings("unused")
	//private static void OnStartSound(byte[] name, int vol) {
	private static void OnStartSound(int idx) {
		if ( listener != null)
			listener.OnStartSound(idx);
	}

	/**
	 * Fires when music is played in the C layer.
	 * @param name Sound name (e.g pistol)
	 * @param volume (0-255)
	 */
	@SuppressWarnings("unused")
	private static void OnStartMusic(int idx) {
		if ( listener != null)
			listener.OnStartMusic(idx);
	}

	/**
	 * Send a key event to the native layer
	 * @param type
	 * @param sym
	 */
	public static void sendNativeKeyEvent (int type, int sym) {
		try {
			if ( type == Natives.EV_KEYDOWN)
				Natives.keyPress(sym);
			else
				Natives.keyRelease(sym);
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, e.toString());
		}
	}
	
}
