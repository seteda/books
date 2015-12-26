package com.androidbook.touch.velocity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class MainActivity extends Activity {
    private static final String TAG = "VelocityTracker";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    private VelocityTracker vTracker = null;

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if(vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                }
                else {
                    vTracker.clear();
                }
                vTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(1000);
                Log.v(TAG, "X velocity is " + vTracker.getXVelocity() + 
                       " pixels per second");
                Log.v(TAG, "Y velocity is " + vTracker.getYVelocity() + 
                       " pixels per second");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                vTracker.recycle();
                break;
        }
        return true;
    }
}