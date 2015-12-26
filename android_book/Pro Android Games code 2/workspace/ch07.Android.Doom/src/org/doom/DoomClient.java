package org.doom;

import java.io.File;

import doom.audio.AudioManager;
import doom.jni.Natives;
import doom.util.DialogTool;
import doom.util.DoomTools;
import doom.util.LibraryLoader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Doom for Android Main Activity
 * @author vsilva
 *
 */
public class DoomClient extends Activity 
	implements Natives.EventListener
{
	private static final String TAG = "DoomClient";
	
	static private Bitmap mBitmap;
	static private ImageView mView;
	
	private int mWidth;
	private int mHeight;
	private static int mOrientation = 0; // portrait
	
	public static final Handler mHandler = new Handler();
	
	private int wadIdx = 0; 
	
	// Audio Cache Manager
	private AudioManager mAudioMgr;
	
	// Sound? 
	private boolean mSound = true;
	
	private static boolean mGameStarted = false;
	
	// multi
	private static boolean mMultiPlayer = false;
	
	// for Multi player
	private String mServerPort;
	
	// Navigation
	public static enum eNavMethod  {KBD, PANEL, ACC};
	public static eNavMethod mNavMethod = eNavMethod.KBD; 
	
	
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

        setContentView(R.layout.doom);

        mView = (ImageView)findViewById(R.id.doom_iv);
        
        if (mGameStarted) {
        	setGameUI();
        	setupPanControls();
        	return;
        }
        
        // Pan controls
        setupPanControls();
        
        if ( ! new File(DoomTools.DOOM_FOLDER).exists() ) {
        	MessageBox("Read this carefully"
        		, "You must install a game file. Tap Menu > Install Game. "
        			+ "A fast WIFI network and SDCARD are required.");
        }
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
     * App menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, 0, 0, "Start").setIcon(R.drawable.icon);
//    	menu.add(0, 1, 1, "Multi Player").setIcon(R.drawable.icon);
    	
    	menu.add(0, 2, 2, "Install Game").setIcon(R.drawable.install);
    	menu.add(0, 3, 3, "Navigation").setIcon(R.drawable.nav);
//    	menu.add(0, 4, 4, "Help").setIcon(R.drawable.help);
//    	menu.add(0, 5, 5, "Cleanup").setIcon(R.drawable.cleanup);
    	menu.add(0, 6, 6, "Exit").setIcon(R.drawable.exit);
    	return true;
    }
    
    /**
     * Menu selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case 0:
			if ( mGameStarted) {
				MessageBox("Game already in progress.");
				return true;
			}
			mMultiPlayer = false;
			showLauncherDialog(this, mMultiPlayer);
			
			return true;
			
		case 1:
			// multi-player
			if ( mGameStarted) {
				MessageBox("Game already in progress.");
				return true;
			}
			mMultiPlayer = true;
			showLauncherDialog(this, mMultiPlayer);
			return true;
			
		case 2:
			if ( mGameStarted) {
				MessageBox("Can't install while game in progress.");
				return true;
			}
			
			// sdcard required
			if ( ! DoomTools.checkSDCard(this) ) return true;

			// Download Game file
			DialogTool.showDownloadDialog(this);
			return true;

		case 3:
			DialogTool.showNavMethodDialog(this); //, mSensorManager, mSensor );
			return true;
			
		case 4:
			// Help
			DialogTool.launchBrowser(DoomClient.this
					, "http://playerx.sf.net/doom/controls.html");
			return true;
		case 5:
			// Cleanup
			if ( mGameStarted) {
				MessageBox("Can't cleanup while game in progress.");
				return true;
			}
			
			DoomTools.cleanUp(DoomClient.this, wadIdx);
			return true;
			
		case 6:
			// Exit
			DoomTools.hardExit(0);
			return true;

		}

    	return false;
    }

    
	/**
	 * Option dialog
	 * @param ctx
	 */
	public void showLauncherDialog(final Context ctx, final boolean multiPlayer) 
	{
        LayoutInflater factory = LayoutInflater.from(ctx);
        final View view = factory.inflate(R.layout.options, null);
        
        // load GUI data
        DialogTool.setLauncherDlgOptionsUI(ctx , view, multiPlayer);
        
        AlertDialog dialog = new AlertDialog.Builder(ctx)
	        .setIcon(R.drawable.icon)
	        .setTitle("Doom Launcher")
	        .setView(view)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	        		wadIdx = ((Spinner)view.findViewById(R.id.s_files)).getSelectedItemPosition();
	        		String wad;
	        		
	        		if ( wadIdx == 3 ) {
	        			// Other wad, use text box, wad must live in /sdcard/doom
	        			wad = ((EditText)view.findViewById(R.id.t_wad)).getText().toString();
	        		}
	        		else{
	        			wad = DoomTools.DOOM_WADS[wadIdx];
	        		}
	        		
	        		// Sound 0 = y, 1 == n
	        		//mSound = ((Spinner)view.findViewById(R.id.s_sound)).getSelectedItemPosition() == 0;
	        		
	        		// Size P = 320 x 320 L: 320 x 200
	        		//mPortrait = ((Spinner)view.findViewById(R.id.s_size)).getSelectedItemPosition() == 1;
	        		
	        		if ( multiPlayer) {
	        			mServerPort = ((EditText)view.findViewById(R.id.t_server)).getText().toString();
	        			
	        			if ( ! DoomTools.validateServerIP(mServerPort)) {
	        				DialogTool.Toast(mHandler, DoomClient.this, "Invalid Server IP: " + mServerPort);
	        				
	        				showLauncherDialog(ctx, multiPlayer);
	        				return;
	        			}
	        		}
	        		
	        		play(wad);
	            }
	        })
	        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                dialog.dismiss();
	            }
	        })
	        .create();

        dialog.show();
	
	}
    
	/**
	 * Play
	 */
	private void play (final String wad) 
	{
		if ( ! DoomTools.checkSDCard(this) ) return;
		
		// Make sure all required files are in place
		if  ( ! checkSanity(wad) ) {
			return;
		}
		
		// Load lib 
		if (!loadLibrary()) {
			// this should not happen
			return;
		}
		
		if ( ! DoomTools.hasSound() )  
			DialogTool.Toast(this, "Warning: Soundtrack is missing.");
	
    	// Hide download widgets/ show game IV
    	setGameUI();
		
		// start doom!
		startGame(wad);
	}

	/**
	 * Make sure all required stuff is in /sdcard/doom 
	 * @return
	 */
	private boolean checkSanity(final String wadName) {
		// check for game file
		if ( ! DoomTools.wadExists(wadName)) { 
			MessageBox("Missing Game file "  
					+ DoomTools.DOOM_FOLDER + wadName
					+ ". Try installing a game.");
			return false;
		}

		
		// check 4 prboom.wad
		File prboom = new File(DoomTools.DOOM_FOLDER + DoomTools.REQUIRED_DOOM_WAD);
		
		if ( ! prboom.exists()) {
			MessageBox("Missing required Game file "  + prboom);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Hide main layout/show image vire (game)
	 */
	private void setGameUI() {
		((ImageView)findViewById(R.id.doom_iv)).setBackgroundDrawable(null);
		switch (mNavMethod) {
		case KBD:
			findViewById(R.id.pan_ctls).setVisibility(View.GONE);
			findViewById(R.id.other_ctls).setVisibility(View.GONE);
			break;
		case PANEL:
			findViewById(R.id.pan_ctls).setVisibility(View.VISIBLE);
			findViewById(R.id.other_ctls).setVisibility(View.VISIBLE);
			break;
		case ACC:
			
			break;
		}
	}
	
	
	void MessageBox (String text) {
		DialogTool.MessageBox(this, getString(R.string.app_name), text);
	}

	void MessageBox (String title, String text) {
		DialogTool.MessageBox(this, title, text);
	}
	
    
    /******************************************************************
     * GAME subs
     ******************************************************************/
    private void startGame(final String wad) 
    {
        if ( wad == null) {
        	MessageBox(this, "Invalid game file! This is a bug.");
        	return;
        }
        
        if (mMultiPlayer && mServerPort == null) {
        	MessageBox(this, "Invalid Server Name for multi player game.");
        	return;
        }
        
        // Audio?
        if ( mSound)
        	mAudioMgr = AudioManager.getInstance(this, wadIdx);
        
		// Doom args
    	final String[] argv;
    	
    	// Window size: P 320x320 L: 320x200 (will autoscale to fit the screen)
    	mOrientation = getWindowManager().getDefaultDisplay().getOrientation();

    	// 0 == port, 1 == land
    	if (mOrientation == 0 ) { 
    		if ( mMultiPlayer)
    			argv = new String[]{"doom" , "-width", "320", "-height", "480", "-iwad", wad , "-net", mServerPort};
    		else
    			argv = new String[]{"doom" , "-width", "320", "-height", "480", "-iwad", wad};
    	}
    	else {
    		if ( mMultiPlayer)
    			argv = new String[]{"doom" , "-width", "480", "-height", "320", "-iwad", wad, "-net", mServerPort};
    		else
    			argv = new String[]{"doom" , "-width", "480", "-height", "320", "-iwad", wad};
    	}
        
        Log.d(TAG, "Starting doom thread with wad " + wad + " sound enabled? " + mSound 
        		+ " Orientation:" + mOrientation ); 
        
        new Thread(new Runnable() {
			public void run() {
				mGameStarted = true;
				Natives.DoomMain(argv);
			}
		}).start();
    	
    }
    
    
    /**
     * Message Box
     * @param ctx
     * @param text
     */
    static void MessageBox( final Context ctx, final String text) {
	    Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }
    
    boolean initialized = false;
    
    /**
     * Load JNI library. Lib must exist in /data/data/APP_PKG/files
     */
    private boolean loadLibrary() {
    	if ( initialized ) return true;
		
		
		Log.d(TAG, "Loading JNI librray from " + DoomTools.DOOM_LIB); //lib);
		LibraryLoader.load(DoomTools.DOOM_LIB); // lib.getAbsolutePath());
		
		// Listen for Doom events
		Natives.setListener(this);
		return true;
    }
    
    /*************************************************************
     * Android Events 
     *************************************************************/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	// Toggle nav ctls visibility when the menu key is pressed
    	if (keyCode == KeyEvent.KEYCODE_MENU) {
    		return false;
    	}

    	int sym = DoomTools.keyCodeToKeySym(keyCode);

    	try {
        	Natives.keyEvent(Natives.EV_KEYUP, sym);
        	//Log.d(TAG, "onKeyUp sent key " +  keyCode );
			
		} catch (UnsatisfiedLinkError e) {
			// Should not happen
			Log.e(TAG, e.toString());
		}
    	return false;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// Gotta ignore menu key (used to toggle pan ctls)
    	if (keyCode == KeyEvent.KEYCODE_MENU) {
    		return false;
    	}
    	
    	int sym = DoomTools.keyCodeToKeySym(keyCode);
    	
    	try {
        	Natives.keyEvent(Natives.EV_KEYDOWN, sym);  
        	//Log.d(TAG, "onKeyDown sent  key " + keyCode );
		} 
    	catch (UnsatisfiedLinkError e) {
			// Should not happen
			Log.e(TAG, e.toString());
		}
    	return false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	
    	try {
        	if ( event.getAction() == MotionEvent.ACTION_DOWN) {
            	// Fire on tap R-CTL
            	Natives.keyEvent(Natives.EV_KEYDOWN, (0x80+0x1d)); 
        	}
        	else if ( event.getAction() == MotionEvent.ACTION_UP) {
            	Natives.keyEvent(Natives.EV_KEYUP, (0x80+0x1d) );
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
    
    @Override
    public boolean onTrackballEvent(MotionEvent event) 
    {
    	final int MOUSE_HSENSITIVITY = 90;
        final int MOUSE_VSENSITIVITY = 40;
            	
    	if(event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX() * event.getXPrecision();
            float y = event.getY() * event.getYPrecision();
            
            if(mGameStarted)
                    Natives.motionEvent(0, (int)(x * MOUSE_HSENSITIVITY), -(int)(y * MOUSE_VSENSITIVITY));                  
            
            //return true;
    	}
    	return false;
    }
    
    /*************************************************************
     * Doom Event callbacks 
     *************************************************************/
    
    /**
     * Fires when there an image update from Doom lib
     */
	public void OnImageUpdate(int[] pixels) {
		mBitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);
		
		mHandler.post(new Runnable() {
			public void run() {
				mView.setImageBitmap( mBitmap);
			}
		});
	}

	/**
	 * Fires on LIB message
	 */
	public void OnMessage(String text, int level) {
		
		if ( level > 0)
			DialogTool.Toast(mHandler, DoomClient.this, text);
		else
			Log.d(TAG, "**Doom Message: " + text);
	}

	public void OnInitGraphics(int w, int h) {
		Log.d(TAG, "OnInitGraphics creating Bitmap of " + w + " by " + h);
		mBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		LayoutParams lp =  mView.getLayoutParams();
		mWidth = w;
		mHeight = h;
		lp.width = w;
		lp.height = h;
	}

	public void OnFatalError(final String text) {
		mHandler.post(new Runnable() {
			public void run() {
				MessageBox("Fatal Error", "Doom has terminated. " 
						+ "Reason: " + text + " - Please report this error.");
			}
		});

		// Wait for the user to read the box
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			
		}
		// Must quit here or the LIB will crash
		DoomTools.hardExit(-1);
	}

	public void OnStartSound(String name, int vol) 
	{
		if ( mSound && mAudioMgr == null)
			Log.e(TAG, "Bug: Audio Mgr is NULL but sound is enabled!");
		
		try {
			if ( mSound && mAudioMgr != null)
				mAudioMgr.startSound( name, vol); 
			
		} catch (Exception e) {
			Log.e(TAG, "OnStartSound: " +  e.toString());
		}
	}

	/**
	 * Fires on background music 
	 */
	public void OnStartMusic(String name, int loop) {
		if ( mSound && mAudioMgr != null)
			mAudioMgr.startMusic(DoomClient.this, name, loop);
	}

	/**
	 * Stop bg music
	 */
	public void OnStopMusic(String name) {
		if ( mSound &&  mAudioMgr != null)
			mAudioMgr.stopMusic( name);
	}

	public void OnSetMusicVolume(int volume) {
		if ( mSound &&  mAudioMgr != null)
			mAudioMgr.setMusicVolume(volume);
	}
	
	
    /**
     * Setup pan controls
     */
    private void setupPanControls() {
    	//up
        findViewById(R.id.btn_up).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_UPARROW);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_UPARROW);
				}
				return true;
			}
        });
        // Down
        findViewById(R.id.btn_down).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_DOWNARROW);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_DOWNARROW);
				}
				return true;
			}
        });
        // Right
        findViewById(R.id.btn_right).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_RIGHTARROW);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_RIGHTARROW);
				}
				return true;
			}
        });
        // Left
        findViewById(R.id.btn_left).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_LEFTARROW);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_LEFTARROW);
				}
				return true;
			}
        });
        
        // Open/pick up
        findViewById(R.id.btn_open).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_SPACE);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_SPACE);
				}
				return true;
			}
        });
        
        // Map
        findViewById(R.id.btn_map).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_TAB);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_TAB);
				}
				return true;
			}
        });
        
        // Strafe left
        findViewById(R.id.btn_stleft).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_COMMA);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_COMMA);
				}
				return true;
			}
        });
        
        // Strafe right
        findViewById(R.id.btn_stright).setOnTouchListener(new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent evt) {
				int action = evt.getAction();
				if ( action == MotionEvent.ACTION_DOWN)
					Natives.sendNativeKeyEvent(Natives.EV_KEYDOWN, DoomTools.KEY_PERIOD);
				else if ( action == MotionEvent.ACTION_UP) {
					Natives.sendNativeKeyEvent(Natives.EV_KEYUP, DoomTools.KEY_PERIOD);
				}
				return true;
			}
        });
        
    }

	public void OnQuit(int code) {
		// TODO Not yet implemented in the JNI lib
    	Log.d(TAG, "Doom Hard Stop.");
    	DoomTools.hardExit(0);
	}
	
}