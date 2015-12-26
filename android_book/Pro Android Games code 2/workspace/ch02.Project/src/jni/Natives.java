package jni;

public class Natives {

	/**
	 * Native Main Doom Loop
	 * @param argv
	 * @return
	 */
	public static native int LibMain(String[] argv);
	
	/**
	 * This fires on messages from the C layer
	 * @param text
	 */
	@SuppressWarnings("unused")
	private static void OnMessage(String text, int level) {
		System.out.println("OnMessage text:" + text + " level=" + level);
	}

}
