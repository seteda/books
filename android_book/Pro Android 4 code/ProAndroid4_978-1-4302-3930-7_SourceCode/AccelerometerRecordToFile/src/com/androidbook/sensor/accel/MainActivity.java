package com.androidbook.sensor.accel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "AccelerometerRecordToFile";
    private WakeLock mWakelock = null;
	private SensorManager mgr;
    private Sensor accel;
    private BufferedWriter log;
	final private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss - ");
	private int mSavedTimeout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        // Setup the log file to write to. We will append it just in case this
        // activity restarts in the middle of our experiment.
        try {
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() +
		        "/accel.log";
            log = new BufferedWriter(new FileWriter(filename, true));
        }
        catch(Exception e) {
        	Log.e(TAG, "Unable to initialize the logfile");
        	e.printStackTrace();
        	finish();
        }

        PowerManager pwrMgr = (PowerManager) this.getSystemService(POWER_SERVICE);
        mWakelock = pwrMgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Accel");
        mWakelock.acquire();
        
        // Save the current value of the screen timeout, then set it to 5 seconds
        try {
        	mSavedTimeout = Settings.System.getInt(getContentResolver(), 
        	    Settings.System.SCREEN_OFF_TIMEOUT);
        }
        catch(Exception e) {
        	mSavedTimeout = 120000;  // 2 minutes
        }
        Settings.System.putInt(getContentResolver(), 
            Settings.System.SCREEN_OFF_TIMEOUT, 5);  // really short but it's not zero
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                writeLog("The screen has turned off");
                // Unregisters the listener and registers it again.
                // Should only need to do this in Android 2.1 although
                // it doesn't hurt to do this in any version.
                /*
                mgr.unregisterListener(MainActivity.this);
                mgr.registerListener(MainActivity.this, accel,
                    SensorManager.SENSOR_DELAY_NORMAL);
                */
            }
        }
    };

    @Override
    protected void onStart() {
    	writeLog("starting...");
        mgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);

    	super.onStart();
    }

    @Override
    protected void onStop() {
    	writeLog("stopping...");
        mgr.unregisterListener(this, accel);
        unregisterReceiver(mReceiver);
        try {
			log.flush();
		} catch (IOException e) {
			// ignore any errors with the logfile
		}
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
    	writeLog("shutting down...");
    	try {
        	log.flush();
        	log.close();
    	}
    	catch(Exception e) {
    		// ignore any errors with the logfile
    	}

    	// Restore the screen off timeout to the previous value
        Settings.System.putInt(getContentResolver(), 
                Settings.System.SCREEN_OFF_TIMEOUT, mSavedTimeout);

        // Release the wake lock, we're done
        mWakelock.release();

        super.onDestroy();
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
		writeLog("Got a sensor event: " + event.values[0] + ", " +
				event.values[1] + ", " + event.values[2]);
	}

	private void writeLog(String str) {
		try {
		    Date now = new Date();
		    log.write(mTimeFormat.format(now));
			log.write(str);
			log.write("\n");
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}