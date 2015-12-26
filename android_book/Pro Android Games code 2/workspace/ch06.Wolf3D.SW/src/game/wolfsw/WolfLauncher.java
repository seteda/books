package game.wolfsw;

import game.controller.ControllerListener;
import game.controller.SNESController;
import game.wolfsw.R;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import wolf.audio.AudioManager;
import wolf.jni.Natives;
import wolf.util.DialogTool;
import wolf.util.LibraryLoader;
import wolf.util.ScanCodes;
import wolf.util.WolfTools;

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

public class WolfLauncher extends Activity 
	implements Natives.EventListener, ControllerListener 
{
	private static final String TAG = "Wolf3D";
	
	public static final Handler mHandler = new Handler();

	private static boolean mGameStarted = false;

	private static Bitmap mBitmap;
	private static ImageView mView;
	
	// Audio Manager
	private AudioManager mAudioMgr;
	
	// Sound? ( yes by default)
	private boolean mSound = true;
	
	private String mGameDir = WolfTools.WOLF_FOLDER ;
	
    // Timer to fetch track information
//    private Timer mLockTimer;

    // Period for the lock timer
//	private static final int LOCK_TIMEOUT_PERIOD	= 60000;
    
	// Unlocked app?
//	private boolean mIsAppUnlocked = true;

	// Accelerometer
//	private SensorManager mSensorManager;
//	private NavSensor mSensor;

	// Navigation
	public static enum eNavMethod  {KBD, PANEL} ; //, ACC};
	public static eNavMethod mNavMethod = eNavMethod.KBD; 

	private SNESController controller;
	
    /** 
     * Called when the activity is first created. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // No title
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        setContentView(R.layout.wolf);
        
        mView = (ImageView)findViewById(R.id.wolf_iv);

        initController();
                
        if (mGameStarted) {
        	setGameUI();
        	return;
        }
        
        // lock timer
//        startLockTimer();
        
        mGameDir += getString(R.string.pkg_name) + File.separator + "files" + File.separator;
        
        if ( ! mGameStarted )
        	DialogTool.Toast(this, "Menu for options");
        
        // Image size
        setImageSize(320, 200);
        
        // Start Game
        startGame(mGameDir, WolfTools.GAME_ID, true);
    }

    private void initController() {
    	// No controller in landscape
    	if ( !isPortrait() ) {
    		return;
    	}
    	
        // init controller 
    	if ( controller == null) {
    		controller = new SNESController(this);
    		controller.setListener(this);
    	}
    	
    	findViewById(R.id.snes).setVisibility(View.VISIBLE);
    	mNavMethod = eNavMethod.PANEL;
    }
    
    public boolean isPortrait() {
    	return getWindowManager().getDefaultDisplay().getOrientation() == 0;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
//        loadSensors();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
//    	unLoadSensors();
    }

    
    /**
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, 0, 0, "Toggle Screen").setIcon(R.drawable.view);
    	menu.add(0, 1, 1, "Navigation").setIcon(R.drawable.nav);
    	menu.add(0, 2, 2, "Exit").setIcon(R.drawable.exit);

    	return true;
    }
    
    /**
     * Menu actions
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch (item.getItemId()) {
		case 0:
			// Screen size
			LayoutParams lp =  mView.getLayoutParams();
			
			//0 == prt, 1 = land
			int orien = getWindowManager().getDefaultDisplay().getOrientation();
			
			if ( orien == 1) 
			{
				if ( lp.width < 0 || lp.height < 0) {
					lp.width = 480;
					lp.height = 320;
					return true;
				}

				lp.width = lp.width == 320 ? 480 : 320 ;
				lp.height = lp.height == 200 ? 320 : 200;
			}
			else {
				DialogTool.Toast(this, "Gotta be in landscape.");
			}
			return true;
    	
		case 1:
			// navigation method
			DialogTool.showNavMethodDialog(this); //, mSensorManager, mSensor );
			return true;
    	
		case 2:
			// Exit
			WolfTools.hardExit(0);
			return true;
			
    	}
    	return false;
    }

    /**
     * This will set the size of the image view
     * @param w
     * @param h
     */
    private void setImageSize(int w, int h ) {
    	LayoutParams lp = mView.getLayoutParams();
    	lp.width = w;
    	lp.height = h;
    }
    
    /**
     * Key down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// Ignore MENU
    	if (keyCode == KeyEvent.KEYCODE_MENU) 
    		return false;

    	try {
    		int sym = ScanCodes.keySymToScancode(keyCode);
    		
    		//System.out.println("onKeyDown code=" + keyCode + " sym=" + sym);
    		
    		Natives.keyPress(sym);
    		
		} catch (UnsatisfiedLinkError e) {
			System.err.println(e.toString());
		}
    	return false;
    }
    
    /**
     * Key Released
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	// Ignore MENU
    	if (keyCode == KeyEvent.KEYCODE_MENU) 
    		return false;
    	
    	try {
    		int sym = ScanCodes.keySymToScancode(keyCode);
    		
    		//System.out.println("onKeyUp code=" + keyCode + " sym=" + sym);
    		Natives.keyRelease(sym);
    		
		} catch (UnsatisfiedLinkError e) {
			System.err.println(e.toString());
		}
    	return false;
    }
    
    /**
     * Touch event
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	try {
        	if ( event.getAction() == MotionEvent.ACTION_DOWN) {
            	// Fire on tap R-CTL
            	Natives.keyPress(ScanCodes.sc_Control);  
        	}
        	else if ( event.getAction() == MotionEvent.ACTION_UP) {
            	Natives.keyRelease(ScanCodes.sc_Control);  
        	}
        	else if ( event.getAction() == MotionEvent.ACTION_MOVE) {
        		// Motion event
        	}
        	return true;
		} 
    	catch (UnsatisfiedLinkError e) {
			// Should not happen!
    		Log.e(TAG, e.toString());
    		return false;
		}
    }

    /**
     * Trackball == Mouse
     */
    @Override
    public boolean onTrackballEvent(MotionEvent event) 
    {
    	/*
    	final int MOUSE_HSENSITIVITY = 90;
        final int MOUSE_VSENSITIVITY = 40;
            	
    	if(event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX() * event.getXPrecision() * MOUSE_HSENSITIVITY;
            float y = event.getY() * event.getYPrecision() * MOUSE_VSENSITIVITY;

            try {
                if(mGameStarted) {
                	System.out.println("onTrackballEvent x=" +x + "y=" + y);
                	Natives.motionEvent(0, (int)x , -(int)y );
                }
				
			} catch (UnsatisfiedLinkError e) {
				System.err.println("onTrackballEvent " + e);
			}
    	}
    	*/
    	return false;
    }
    
    
	/**
	 * 
	 * @param baseDir
	 * @param game
	 * @param portrait
	 */
    private void startGame(String baseDir, String game, boolean portrait) 
    {
    	File dir = new File(baseDir);
    	
    	if (! dir.exists() ) {
    		if (!dir.mkdir() ) {
        		MessageBox("Invalid game base folder: " + baseDir);
        		return;
    		}
    	}
    	
    	
    	// setup game files
    	try {
    		WolfTools.installGame(this, dir);
		} catch (Exception e) {
			MessageBox("Fatal Error", "Unable to set game files:" + e.toString());
			return;
		}
    	
    	
    	Log.d(TAG, "Start game base dir: " + baseDir + " game=" + game + " port:" + portrait);
    	
        // Args
        final String argv[] = {"wolf3d", game, "basedir" , baseDir}; //, "x2"};
        
		// Load lib 
		if (!loadLibrary()) {
			// this should not happen
			return;
		}

        Natives.setListener(this);
        
        // get rid of imageview background
        setGameUI();
        
        // Audio?
        if ( mSound)
        	mAudioMgr = AudioManager.getInstance(this);
        
        new Thread(new Runnable() {
			public void run() {
				mGameStarted = true;
		        Natives.WolfMain(argv);
			}
		}).start();
    }
    
    
    
	/**
	 * Hide main layout/show image vire (game)
	 */
	private void setGameUI() {
		switch (mNavMethod) {
		case KBD:
//			findViewById(R.id.pan_ctls).setVisibility(View.GONE);
//			findViewById(R.id.other_ctls).setVisibility(View.GONE);
			break;
		case PANEL:
//			findViewById(R.id.pan_ctls).setVisibility(View.VISIBLE);
//			findViewById(R.id.other_ctls).setVisibility(View.VISIBLE);
			break;
//		case ACC:
//			
//			break;
		}
	}

	
    /******************************************************
     * Native Events
     ******************************************************/
	@Override
	public void OnSysError(final String text) 
	{
		//System.out.println("OnFatalError: " + text);
		
		mHandler.post(new Runnable() {
			public void run() {
				MessageBox("System Message",  
						text);
			}
		});

		// Wait for the user to read the box
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			
		}
		// Ouch !
		WolfTools.hardExit(-1);
	}

	@Override
	public void OnImageUpdate(int[] pixels, int x ,int y, int w, int h) {
		mBitmap.setPixels(pixels, 0, w, x, y, w, h);
		
		
		mHandler.post(new Runnable() {
			public void run() {
				try {
					mView.setImageBitmap(mBitmap);
					
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	@Override
	public void OnInitGraphics(int w, int h) {
		Log.d(TAG, "OnInitGraphics creating Bitmap of " + w + " by " + h);
		mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	}

	@Override
	public void OnMessage(String text) {
		System.out.println("** Wolf Message: " + text);
	}
	
	
    /**
     * Load JNI library. Lib must exist in /data/data/APP_PKG/files
     */
    private boolean loadLibrary() {
		
//    	File lib = new File (mGameDir + WolfTools.WOLF_LIB); 
//    	
//    	if ( ! lib.exists()) {
//    		MessageBox("Unable to find library " + lib);
//    		return false;
//    	}
//			
//    	if ( lib.length() == 0) {
//    		MessageBox("Invalid library " + WolfTools.WOLF_LIB + " (size=0)");
//    		return false;
//    	}
		
		Log.d(TAG, "Loading JNI librray from " + WolfTools.WOLF_LIB) ;//lib);
		LibraryLoader.load(WolfTools.WOLF_LIB); //lib.getAbsolutePath());
		
		// Listen for Doom events
		Natives.setListener(this);
		return true;
    }
	
	void MessageBox (String text) {
		WolfTools.MessageBox(this, getString(R.string.app_name), text);
	}

	void MessageBox (String title, String text) {
		WolfTools.MessageBox(this, title, text);
	}
 

	@Override
	public void OnStartSound(int idx) {
		if ( mSound && mAudioMgr == null)
			Log.e(TAG, "Bug: Audio Mgr is NULL but sound is enabled!");
		
		try {
			if ( mSound && mAudioMgr != null)
				mAudioMgr.startSound( idx); 
			
		} catch (Exception e) {
			Log.e(TAG, "OnStartSound: " +  e.toString());
		}
	}
	
	public void OnStartMusic(int idx) {
		if ( mSound && mAudioMgr == null)
			Log.e(TAG, "Bug: Audio Mgr is NULL but sound is enabled!");
		
		try {
			if ( mSound && mAudioMgr != null)
				mAudioMgr.startMusic(this, idx); 
			
		} catch (Exception e) {
			Log.e(TAG, "OnStartSound: " +  e.toString());
		}
	}
	
	
	/**
	 * App Locking logic
	 */
//    private void startLockTimer() { 
//    	if ( ! mIsAppUnlocked ) {
//    		mLockTimer = new Timer();
//    		mLockTimer.schedule(new LockTask() ,0, LOCK_TIMEOUT_PERIOD); 
//    	}
//    }

//    private void stopLockTimer() {
//    	if ( mLockTimer != null) {
//    		Log.d(TAG, "Stoping lock timer.");
//    		mLockTimer.cancel();
//    	}
//    }
    
    /**
     * Lock task
     * @author vsilva
     *
     */
//    class LockTask extends TimerTask 
//    {
//    	// Counter limit
//    	private static final int LOCK_TIMEOUT_TICKS = 10;
//    	
//    	// When zero the app stops
//    	private int timeoutCounter = LOCK_TIMEOUT_TICKS;
//
//    	public LockTask() { 
//		}
//    	
//		@Override
//		public void run() {
//			timeoutCounter--;
//
//			if (timeoutCounter <= 0) {
//				if ( mLockTimer != null )
//					mLockTimer.cancel();
//				
//				OnSysError("Thanks for playing. Send BUGS to android.apps01@gmail.com.");
//			}
//		}
//    }

    /**
     * Load Accelerometer
     */
//	private void loadSensors() {
//		// TODO: Sensor sim (remove it)
//		/*
//		// Before calling any of the Simulator data, 
//		// the Content resolver has to be set !! 
//		org.openintents.provider.Hardware.mContentResolver = getContentResolver();  
//		
//		// Link sensor manager to OpenIntents Sensor simulator 
//		mSensorManager = (SensorManager) new org.openintents.hardware.SensorManagerSimulator((SensorManager)getSystemService(SENSOR_SERVICE));
//		*/
//		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//		mSensor = new NavSensor();
//		
//		if ( mNavMethod != eNavMethod.ACC )  return;
//		
//		if (!mSensorManager.registerListener(mSensor, SensorManager.SENSOR_ACCELEROMETER)) {
//			DialogTool.MessageBox(this, "Unable to register the accelerometer. Using panel arrows by default.");
//			return;
//		}
//	}
//    
//	private void unLoadSensors() {
//		if ( mSensorManager != null)
//			mSensorManager.unregisterListener(mSensor);
//	}

	@Override
	public void ControllerDown(int btnCode) {
		switch (btnCode) {
		case KeyEvent.KEYCODE_Y:
			// strafe left
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_Alt);
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_LeftArrow);
			break;

		case KeyEvent.KEYCODE_X:
			// strafe right
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_Alt);
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_RightArrow);
			break;

		case KeyEvent.KEYCODE_B:
			// Fire
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_Control);
			break;

		case KeyEvent.KEYCODE_A:
			// Rshift (Run)
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.sc_RShift);
			break;
			
		default:
			Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, ScanCodes.keySymToScancode(btnCode));
			break;
		}
	}

	@Override
	public void ControllerUp(int btnCode) {
		switch (btnCode) {
		case KeyEvent.KEYCODE_Y:
			// strafe left
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_Alt);
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_LeftArrow);
			break;

		case KeyEvent.KEYCODE_X:
			// strafe right
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_Alt);
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_RightArrow);
			break;

		case KeyEvent.KEYCODE_B:
			// Fire
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_Control);
			break;
			
		case KeyEvent.KEYCODE_A:
			// shift (Run)
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.sc_RShift);
			break;
			
		default:
			Natives.sendNativeKeyEvent(Natives.EV_KEYUP, ScanCodes.keySymToScancode(btnCode));
			break;
		}
	}
	
}