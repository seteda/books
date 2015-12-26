package com.androidbook.gestures;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements OnGesturePerformedListener {
    private static final String TAG = "Gesture Revealer";
	GestureLibrary gestureLib = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures_letters);
        gestureLib = GestureLibraries.fromFile("/sdcard/gestures");

        // Here's where we can change the default values before loading gestures
        //gestureLib.setOrientationStyle(GestureStore.ORIENTATION_INVARIANT);
        //gestureLib.setSequenceType(GestureStore.SEQUENCE_INVARIANT);

        if (!gestureLib.load()) {
            Toast.makeText(this, "Could not load /sdcard/gestures", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Let's take a look at the gesture library we have to work with
        Log.v(TAG, "Library features:");
        Log.v(TAG, "  Orientation style: " + gestureLib.getOrientationStyle());
        Log.v(TAG, "  Sequence type: " + gestureLib.getSequenceType());
        for( String gestureName : gestureLib.getGestureEntries() ) {
        	Log.v(TAG, "For gesture " + gestureName);
        	int i = 1;
            for( Gesture gesture : gestureLib.getGestures(gestureName) ) {
        	    Log.v(TAG, "    " + i + ": ID: " + gesture.getID());
        	    Log.v(TAG, "    " + i + ": Strokes count: " + gesture.getStrokesCount());
        	    Log.v(TAG, "    " + i + ": Stroke length: " + gesture.getLength());
        	    i++;
            }
        }

        GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        gestureView.addOnGesturePerformedListener(this);

        // We could call this if we didn't set android:gestureStrokeType="multiple"
        // in the layout file.
        //gestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
    }

	public void onGesturePerformed(GestureOverlayView view, Gesture gesture) {
	    ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
	    Log.v(TAG, "in onGesturePerformed");

	    if (predictions.size() > 0) {
	        Prediction prediction = (Prediction) predictions.get(0);
	        if (prediction.score > 1.0) {
	            Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
	            for(int i=0;i<predictions.size();i++)
	            	Log.v(TAG, "prediction " + predictions.get(i).name +
	            			" - score = " + predictions.get(i).score);
	        }
	    }
	}
}