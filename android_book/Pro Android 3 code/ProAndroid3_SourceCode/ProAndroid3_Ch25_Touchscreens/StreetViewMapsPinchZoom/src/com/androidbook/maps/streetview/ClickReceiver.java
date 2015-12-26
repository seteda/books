package com.androidbook.maps.streetview;

// This file is ClickReceiver.java
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ClickReceiver extends Overlay {
	private static final String TAG = "ClickReceiver";
	private static final float ZOOMJUMP = 75f;
	private Context mContext;
	private boolean inZoomMode = false;
	private boolean ignoreLastFinger = false;
	private float mOrigSeparation;

	public ClickReceiver(Context context) {
        mContext = context;
	}

	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		Log.v(TAG, "Received a click at this point: " + p);
		
		if(mapView.isStreetView()) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
    		    ("google.streetview:cbll=" +
    		    (float)p.getLatitudeE6() / 1000000f +
    		    "," + (float)p.getLongitudeE6() / 1000000f
    	        +"&cbp=1,180,,0,1.0"
                ));
		    mContext.startActivity(myIntent);
		    return true;
		}
	    return false;
	}
	
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
        Log.v(TAG, "in onTouchEvent, action is " + e.getAction());
    	int action = e.getAction() & MotionEvent.ACTION_MASK;

    	if(e.getPointerCount() == 2) {
        	inZoomMode = true;
        }
        else {
        	inZoomMode = false;
        }
        
        if(inZoomMode) {
        	switch(action) {
        	case MotionEvent.ACTION_POINTER_DOWN:
        		// We may be starting a new pinch so get ready
        		mOrigSeparation = calculateSeparation(e);
        		break;
        	case MotionEvent.ACTION_POINTER_UP:
        		// We're ending a pinch so prepare to
        		// ignore the last finger while it's the
        		// only one still down.
        		ignoreLastFinger  = true;
        		break;
        	case MotionEvent.ACTION_MOVE:
        		// We're in a pinch so decide if we need to change
        		// the zoom level.
        		float newSeparation = calculateSeparation(e);
        		if(newSeparation - mOrigSeparation > ZOOMJUMP) {
        			// we got wider, zoom in
        			mapView.getController().zoomIn();
        			mOrigSeparation = newSeparation;
        		}
        		else if (mOrigSeparation - newSeparation > ZOOMJUMP) {
        			// we got narrower, zoom out
        			mapView.getController().zoomOut();
        			mOrigSeparation = newSeparation;
        		}
        		break;
        	}
        	// Don't pass these events to Android because we're
        	// taking care of them.
        	return true;
        }
        else {
        	// cleanup if necessary from zooming logic
        }

        // Throw away events if we're on the last finger
        // until the last finger goes up.
        if(ignoreLastFinger) {
            if(action == MotionEvent.ACTION_UP)
            	ignoreLastFinger = false;
        	return true;
        }

		return super.onTouchEvent(e, mapView);
	}

	private float calculateSeparation(MotionEvent e) {
		float x = e.getX(0) - e.getX(1);
		float y = e.getY(0) - e.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
}
