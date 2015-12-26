package com.androidbook.maps.streetview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ClickReceiver extends Overlay{
	private static final String TAG = "ClickReceiver";
	private Context mContext;

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
}
