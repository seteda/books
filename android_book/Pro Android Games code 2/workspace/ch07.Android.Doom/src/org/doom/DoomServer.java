package org.doom;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import doom.jni.ServerNatives;

public class DoomServer extends Activity 
	implements ServerNatives.EventListener
{
	private static final String TAG = "DoomServer";
	
	private int mPort = 5030;
	private int mNumPlayers = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		startserver();
	}
	
	private void startserver() {
		// Doom args
    	final String[] argv;

    	argv = new String[] { "server"
    			, "-p", new Integer(mPort).toString() 
    			, "-N", new Integer(mNumPlayers).toString()
    			};
    	
    	Log.d(TAG, "Starting Doom server");
    	
		new Thread(new Runnable() {
			public void run() {
				ServerNatives.ServerMain(argv);
			}
		}).start();
	}
	
	/**************************************************
	 * C  CALLBACKS
	 **************************************************/
	public void OnFatalError(String text) {
		// TODO Auto-generated method stub
		
	}

	public void OnMessage(String text, int level) {
		// TODO Auto-generated method stub
		
	}

	public void OnQuit(int code) {
		// TODO Auto-generated method stub
		
	}

}
