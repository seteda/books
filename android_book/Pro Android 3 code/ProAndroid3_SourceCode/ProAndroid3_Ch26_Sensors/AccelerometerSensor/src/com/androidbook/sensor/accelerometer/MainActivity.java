package com.androidbook.sensor.accelerometer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor accelerometer;
    private TextView text;
	private int mRotation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        accelerometer = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        text = (TextView) findViewById(R.id.text);
        
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
		String msg = String.format("X: %8.4f\nY: %8.4f\nZ: %8.4f\nRotation: %d",
            event.values[0], event.values[1], event.values[2], mRotation);
		text.setText(msg);
		text.invalidate();
	}
}