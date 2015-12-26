package doom.jni;


/**
 * Copyright Vladimir Silva 2009
 * You are not authorized to change or distribute thos code without the author's consent.
 * 
 * @author Vladimir Silva
 *
 */
public class ServerNatives 
{
	public static final String TAG = "ServerNatives";
	
	private static EventListener listener;
	
//	public static final int EV_KEYDOWN = 0;
//	public static final int EV_KEYUP = 1;
//	public static final int EV_MOUSE = 2;
	
	public static interface EventListener 
	{
		void OnMessage(String text, int level);
//		void OnInitGraphics(int w, int h);
//		void OnImageUpdate (int[] pixels);	
		void OnFatalError(String text);
		void OnQuit (int code);
//		void OnStartSound(String name, int vol);
//		void OnStartMusic (String name, int loop);
//		void OnStopMusic (String name);
//		void OnSetMusicVolume (int volume);
	}
	

	public static void setListener (EventListener l) {
		listener = l;
	}
	
	/**
	 * Send a key event to the native layer
	 * @param type
	 * @param sym
	 */
//	public static void sendNativeKeyEvent (int type, int sym) {
//		try {
//			ServerNatives.keyEvent(type, sym);
//		} catch (UnsatisfiedLinkError e) {
//			Log.e(TAG, e.toString());
//		}
//	}

	/**
	 * Native Main Doom Loop
	 * @param argv
	 * @return
	 */
	public static native int ServerMain(String[] argv);

	/**
	 * Key Event JNI func
	 * @param type event type: UP/DOWN
	 * @param key ASCII symbol
	 */
//	public static native int keyEvent(int type, int key);
	

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
	
	
	
	/**
	 * Fires when the C lib calls exit()
	 * @param message
	 */
	@SuppressWarnings("unused")
	private static void OnFatalError(String message) {
		if ( listener != null)
			listener.OnFatalError(message);
	}

	
}
