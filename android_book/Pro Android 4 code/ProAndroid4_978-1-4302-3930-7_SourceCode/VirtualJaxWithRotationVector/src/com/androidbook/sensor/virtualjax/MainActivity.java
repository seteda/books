package com.androidbook.sensor.virtualjax;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "VirtualJax";
	private SensorManager mgr;
	private Sensor accel;
    private Sensor compass;
	private Sensor orient;
	private Sensor rotvec;
    private TextView preferred;
	private TextView orientation;
	private TextView rotationvector;
	private int ready = 0;
	private float[] accelValues = new float[3];
	private float[] compassValues = new float[3];
	private float[] inR = new float[9];
	private float[] rotvecR = new float[9];
	private float[] inclineMatrix = new float[9];
	private float[] orientationValues = new float[3];
	private float[] rotvecValues = null;
	private float[] prefValues = new float[3];
	private float[] rotvecOrientValues = new float[3];
	private float mAzimuth;
	private double mInclination;
	private int counter;
	private int mRotation;
	private int rotvecLength;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        preferred = (TextView)findViewById(R.id.preferred);
        orientation = (TextView)findViewById(R.id.orientation);
        rotationvector = (TextView)findViewById(R.id.rotationvector);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compass = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orient = mgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        rotvec = mgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        WindowManager window = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        int apiLevel = Integer.parseInt(Build.VERSION.SDK);
        if(apiLevel < 8) {
            mRotation = window.getDefaultDisplay().getOrientation();
        }
        else {
        	mRotation = window.getDefaultDisplay().getRotation();
        }
    }

    @Override
    protected void onResume() {
    	super.onResume();
        registerListener(accel, SensorManager.SENSOR_DELAY_GAME);
        registerListener(compass, SensorManager.SENSOR_DELAY_GAME);
        registerListener(orient, SensorManager.SENSOR_DELAY_GAME);
        registerListener(rotvec, SensorManager.SENSOR_DELAY_GAME);
    }

	private void registerListener(Sensor sensor, int speed) {
	    if(sensor != null) {
		    mgr.registerListener(this, sensor, speed);
		}
	}
	
    @Override
    protected void onPause() {
    	mgr.unregisterListener(this);
    	super.onPause();
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
		// Need to get both accelerometer and compass
		// before we can determine our orientationValues
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			for(int i=0; i<3; i++) {
	            accelValues[i] = event.values[i];
			}
            if(compassValues[0] != 0)
            	ready = ready | 1;
            break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			for(int i=0; i<3; i++) {
				compassValues[i] = event.values[i];
			}
            if(accelValues[2] != 0)
            	ready = ready | 2;
            break;
		case Sensor.TYPE_ORIENTATION:
			for(int i=0; i<3; i++) {
				orientationValues[i] = event.values[i];
			}
		    break;
		case Sensor.TYPE_ROTATION_VECTOR:
			if(rotvecValues == null) {
				rotvecLength = event.values.length;
				rotvecValues = new float[rotvecLength];
			}
			for(int i=0; i<rotvecLength; i++) {
				rotvecValues[i] = event.values[i];
			}
		    break;
		}
		
        if(ready != 3)
        	return;
        
        if(SensorManager.getRotationMatrix(
                inR, inclineMatrix, accelValues, compassValues)) {
        	// got a good rotation matrix

        	mInclination = SensorManager.getInclination(inclineMatrix);

        	SensorManager.getOrientation(inR, prefValues);
        	
        	// Display every 10th value
        	if(counter++ % 10 == 0) {
        		doUpdate(null);
        		counter = 1;
        	}
        }
		
        if(rotvecValues != null) {
            SensorManager.getRotationMatrixFromVector(rotvecR, rotvecValues);

		    SensorManager.getOrientation(rotvecR, rotvecOrientValues);
        }
	}

    public void doUpdate(View view) {
    	if(ready != 3)
    		return;

    	mAzimuth = (float) Math.toDegrees(prefValues[0]);
    	if(mAzimuth < 0) {
    		mAzimuth += 360.0f;
    	}

        String msg = String.format(
        		"Preferred:\nazimuth (Z): %7.3f \npitch (X): %7.3f\nroll (Y): %7.3f",
        		mAzimuth,
                Math.toDegrees(prefValues[1]),
       			Math.toDegrees(prefValues[2]));
    	preferred.setText(msg);

        msg = String.format(
        		"Orientation Sensor:\nazimuth (Z): %7.3f\npitch (X): %7.3f\nroll (Y): %7.3f",
        		orientationValues[0],
        		orientationValues[1],
       			orientationValues[2]);
        orientation.setText(msg);

        if(rotvecValues != null) {
        	mAzimuth = (float) Math.toDegrees(rotvecOrientValues[0]);
        	if(mAzimuth < 0) {
        		mAzimuth += 360.0f;
        	}
            msg = String.format(
        		"Rotation Vector Sensor:\nazimuth (Z): %7.3f\npitch (X): %7.3f\nroll (Y): %7.3f",
        		mAzimuth,
        		Math.toDegrees(rotvecOrientValues[1]),
        		Math.toDegrees(rotvecOrientValues[2]));
            rotationvector.setText(msg);
            rotationvector.invalidate();
        }

       	preferred.invalidate();
       	orientation.invalidate();
    }

    public void doShow(View view) {
    	// google.streetview:cbll=30.32454,-81.6584&cbp=1,yaw,,pitch,1.0
    	// yaw = degrees clockwise from North
    	// For yaw we can use either mAzimuth or orientationValues[0].
    	//
    	// pitch = degrees up or down. -90 is looking straight up,
    	// +90 is looking straight down
    	// except that pitch doesn't work properly
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(
            "google.streetview:cbll=30.32454,-81.6584&cbp=1," +
            Math.round(orientationValues[0]) + ",,0,1.0"
            ));
    	startActivity(intent);
    	return;
    }
}