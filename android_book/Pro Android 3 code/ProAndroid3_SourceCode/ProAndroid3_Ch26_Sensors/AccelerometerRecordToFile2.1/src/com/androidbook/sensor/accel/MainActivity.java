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
	private SensorManager mMgr;
    private Sensor mAccel;
    private BufferedWriter mLog;
	final private SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss - ");
	private int mSavedTimeout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        mAccel = mMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Setup the log file to write to. We will append it just in case this
        // activity restarts in the middle of our experiment.
        try {
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() +
		        "/accel.log";
            mLog = new BufferedWriter(new FileWriter(filename, true));
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
            Settings.System.SCREEN_OFF_TIMEOUT, 5000);  // 5 seconds
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                writeLog("The screen has turned off");
                // Unregisters the listener and registers it again.

                mMgr.unregisterListener(MainActivity.this);
                mMgr.registerListener(MainActivity.this, mAccel,
                    SensorManager.SENSOR_DELAY_NORMAL);

            }
        }
    };

    @Override
    protected void onStart() {
    	writeLog("starting...");
        mMgr.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);

    	super.onStart();
    }

    @Override
    protected void onStop() {
    	writeLog("stopping...");
        mMgr.unregisterListener(this, mAccel);
        unregisterReceiver(mReceiver);
        try {
			mLog.flush();
		} catch (IOException e) {
			// ignore any errors with the logfile
		}
    	super.onStop();
    }

    @Override
    protected void onDestroy() {
    	writeLog("shutting down...");
    	try {
        	mLog.flush();
        	mLog.close();
    	}
    	catch(Exception e) {
    		// ignore any errors with the logfile
    	}

    	// Restore the screen off timeout to the previous value
        Settings.System.putInt(getContentResolver(), 
                Settings.System.SCREEN_OFF_TIMEOUT, mSavedTimeout);

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
		    mLog.write(mTimeFormat.format(now));
			mLog.write(str);
			mLog.write("\n");
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}