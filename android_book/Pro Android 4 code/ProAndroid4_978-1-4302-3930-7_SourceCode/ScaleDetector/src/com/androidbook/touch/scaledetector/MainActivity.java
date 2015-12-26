package com.androidbook.touch.scaledetector;

// This file is MainActivity.java
import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final String TAG = "ScaleDetector";
	private ImageView image;
	private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;
	private Matrix mMatrix = new Matrix();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        image = (ImageView)findViewById(R.id.image);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
    	Log.v(TAG, "in onTouchEvent");
        // Give all events to ScaleGestureDetector
        mScaleDetector.onTouchEvent(ev);

        return true;
	}

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
        public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();

			// Make sure we don't get too small or too big
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

			Log.v(TAG, "in onScale, scale factor = " + mScaleFactor);
            mMatrix.setScale(mScaleFactor, mScaleFactor);

            image.setImageMatrix(mMatrix);
            image.invalidate();
            return true;
        }
    }
}