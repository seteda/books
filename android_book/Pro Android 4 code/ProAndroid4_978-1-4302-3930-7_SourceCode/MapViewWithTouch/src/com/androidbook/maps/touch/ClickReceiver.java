package com.androidbook.maps.touch;

import android.content.Context;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ClickReceiver extends Overlay{
	private Context mContext;

	public ClickReceiver(Context context) {
        mContext = context;
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
        String msg = "Got a touch at lat,lon: " +
    	    (float)p.getLatitudeE6() / 1000000f +
    	    "," + (float)p.getLongitudeE6() / 1000000f;
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        // Of course, now you could do a GeoCoder call to find
        // out what is at this location.
		return true;
	}
}
