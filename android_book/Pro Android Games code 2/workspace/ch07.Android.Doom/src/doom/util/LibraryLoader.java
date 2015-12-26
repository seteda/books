package doom.util;

import android.util.Log;

/**
 * LoadLib Helper
 * @author user
 *
 */
public class LibraryLoader {

	static final String TAG = "LibLoader";
	
	static public void load (String name) {
		final String LD_PATH = System.getProperty("java.library.path");
		
		Log.d(TAG, "Trying to load library " + name + " from LD_PATH: " + LD_PATH);
		
		try {
			System.loadLibrary(name);
		} catch (UnsatisfiedLinkError e) {
			Log.e(TAG, e.toString());
		}
		
//		Log.d(TAG, "Trying to load " + name + " using its absolute path.");
//		System.load(name);
	}
}
