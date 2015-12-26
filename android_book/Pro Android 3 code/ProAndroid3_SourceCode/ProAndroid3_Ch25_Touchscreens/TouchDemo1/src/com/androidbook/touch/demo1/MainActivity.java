package com.androidbook.touch.demo1;

// This file is MainActivity.java
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnTouchListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout1.setOnTouchListener(this);
        Button trueBtn1 = (Button)findViewById(R.id.trueBtn1);
        trueBtn1.setOnTouchListener(this);
        Button falseBtn1 = (Button)findViewById(R.id.falseBtn1);
        falseBtn1.setOnTouchListener(this);

        RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.layout2);
        layout2.setOnTouchListener(this);
        Button trueBtn2 = (Button)findViewById(R.id.trueBtn2);
        trueBtn2.setOnTouchListener(this);
        Button falseBtn2 = (Button)findViewById(R.id.falseBtn2);
        falseBtn2.setOnTouchListener(this);
    }

	public boolean onTouch(View v, MotionEvent event) {
		String myTag = v.getTag().toString();
		Log.v(myTag, "-----------------------------");
		Log.v(myTag, "Got view " + myTag + " in onTouch");
		Log.v(myTag, describeEvent(v, event));
		if( "true".equals(myTag.substring(0, 4))) {
		/*	Log.v(myTag, "*** calling my onTouchEvent() method ***");
			v.onTouchEvent(event);
			Log.v(myTag, "*** back from onTouchEvent() method ***"); */
			Log.v(myTag, "and I'm returning true");
			return true;
		}
		else {
			Log.v(myTag, "and I'm returning false");
			return false;
		}
	}
	
	protected static String describeEvent(View view, MotionEvent event) {
		StringBuilder result = new StringBuilder(300);
		result.append("Action: ").append(event.getAction()).append("\n");
		result.append("Location: ").append(event.getX()).append(" x ").append(event.getY()).append("\n");
        if(     event.getX() < 0 || event.getX() > view.getWidth() ||
        		event.getY() < 0 || event.getY() > view.getHeight()) {
        	result.append(">>> Touch has left the view <<<\n");
        }
		result.append("Edge flags: ").append(event.getEdgeFlags()).append("\n");
		result.append("Pressure: ").append(event.getPressure()).append("   ");
		result.append("Size: ").append(event.getSize()).append("\n");
		result.append("Down time: ").append(event.getDownTime()).append("ms\n");
		result.append("Event time: ").append(event.getEventTime()).append("ms");
		result.append("  Elapsed: ").append(event.getEventTime()-event.getDownTime());
		result.append(" ms\n");
		return result.toString();
	}
}