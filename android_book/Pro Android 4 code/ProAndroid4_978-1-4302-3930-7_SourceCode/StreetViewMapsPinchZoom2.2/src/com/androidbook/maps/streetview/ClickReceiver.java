package com.androidbook.maps.streetview;

// This file is ClickReceiver.java
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ClickReceiver extends Overlay {
	private static final String TAG = "ClickReceiver";
	private static final float ZOOMJUMP = 75f;
	private Context mContext;
	private ScaleGestureDetector mScaleDetector;

	public ClickReceiver(Context context) {
        mContext = context;
        
        //mScaleDetector = new ScaleGestureDetector(context,
        //		new MyScaleGestureListener());
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
/*        mScaleDetector.onTouchEvent(e);
        
        if(!mScaleDetector.isInProgress())
            Log.v(TAG, "in onTouchEvent, action is " + e.getAction());
*/
        return super.onTouchEvent(e, mapView);
	}

	private class MyScaleGestureListener
	    extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		private float previousSpan;
		private MapController mapController = null;

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			Log.v(TAG, "in onScaleBegin, midpoint is (" + detector.getFocusX() +
	    			"," + detector.getFocusY() + ")");
			mapController = ((MainActivity)mContext).mapView.getController();
			previousSpan = detector.getPreviousSpan();
			Log.v(TAG, "previous span = " + previousSpan);
			return true;
		}

	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	    	float currentSpan = detector.getCurrentSpan();
			Log.v(TAG, "in onScale: current span = " + currentSpan +
					", difference = " + Math.abs(currentSpan - previousSpan));
    		if(currentSpan - previousSpan > ZOOMJUMP) {
    			// we got wider, zoom in
    			mapController.zoomIn();
    		}
       		else if (previousSpan - currentSpan > ZOOMJUMP) {
        		// we got narrower, zoom out
       			mapController.zoomOut();
       		}
       		else {
                return true;
       		}
			previousSpan = currentSpan;
			Log.v(TAG, "previous span is now " + previousSpan);
    		return true;
	    }

	    @Override
	    public void onScaleEnd(ScaleGestureDetector detector) {
			Log.v(TAG, "in onScaleEnd, midpoint is (" + detector.getFocusX() +
	    			"," + detector.getFocusY() + ")");
			mapController = null;
	    	super.onScaleEnd(detector);
	    }
	}
}
