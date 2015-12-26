package opengl.jni;



public class Natives {

	private static EventListener listener;
	
	public static interface EventListener 
	{
		void OnMessage(String text);
		void GLSwapBuffers();
	}
	
	public static void setListener (EventListener l) {
		listener = l;
	}

	/**
	 * Native Render test
	 * @return
	 */
	public static native int NativeRender();
	
	@SuppressWarnings("unused")
	private static void OnMessage(String text) {
		if ( listener != null)
			listener.OnMessage(text);
	}
	
	@SuppressWarnings("unused")
	private static void GLSwapBuffers() {
		if ( listener != null)
			listener.GLSwapBuffers();
	}
	
}
