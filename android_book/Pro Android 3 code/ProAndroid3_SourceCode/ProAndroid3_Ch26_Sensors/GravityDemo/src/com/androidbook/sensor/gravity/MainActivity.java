package com.androidbook.sensor.gravity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor accelerometer;
    private TextView text;
	private float[] gravity = new float[3];
	private float[] motion = new float[3];
	private double ratio;
	private double mAngle;
	private int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        accelerometer = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    	super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, accelerometer);
    	super.onPause();
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
		// Use a low-pass filter to get gravity. Motion is what's left over
		for(int i=0; i<3; i++) {
            gravity [i] = (float) (0.1 * event.values[i] + 0.9 * gravity[i]);
            motion[i] = event.values[i] - gravity[i];
		}

		// ratio is gravity on the Y axis compared to full gravity
		// should be no more than 1, no less than -1
    	ratio = gravity[1]/SensorManager.GRAVITY_EARTH;
    	if(ratio > 1.0) ratio = 1.0;
    	if(ratio < -1.0) ratio = -1.0;

    	// convert ratio to radians to degrees, make negative if facing up
    	mAngle = Math.toDegrees(Math.acos(ratio));
    	if(gravity[2] < 0) {
    		mAngle = -mAngle;
    	}

    	// Display every 10th value
    	if(counter++ % 10 == 0) {
            String msg = String.format(
                "Raw values\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
    			"Gravity\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
    			"Motion\nX: %8.4f\nY: %8.4f\nZ: %8.4f\nAngle: %8.1f",
	            event.values[0], event.values[1], event.values[2],
	            gravity[0], gravity[1], gravity[2],
	            motion[0], motion[1], motion[2],
	            mAngle);
		    text.setText(msg);
		    text.invalidate();
		    counter=1;
    	}
	}
}