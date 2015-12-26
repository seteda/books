package com.androidbook.sensor.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor compass;
    private TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        compass = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
    	super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, compass);
    	super.onPause();
    }

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}

	public void onSensorChanged(SensorEvent event) {
            String msg = String.format("X: %8.4f\nY: %8.4f\nZ: %8.4f",
	            event.values[0], event.values[1], event.values[2]);
    		text.setText(msg);
    		text.invalidate();
	}
}