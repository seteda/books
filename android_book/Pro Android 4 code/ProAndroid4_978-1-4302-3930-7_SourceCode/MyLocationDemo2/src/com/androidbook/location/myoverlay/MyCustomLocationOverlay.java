package com.androidbook.location.myoverlay;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyCustomLocationOverlay extends MyLocationOverlay {
    MapView mMapView = null;
    
	public MyCustomLocationOverlay(Context ctx, MapView mapView) {
		super(ctx, mapView);
		mMapView = mapView;
	}

	public void onLocationChanged(Location loc) {
		super.onLocationChanged(loc);
		GeoPoint newPt = new GeoPoint((int) (loc.getLatitude()*1E6),
				(int) (loc.getLongitude()*1E6));
		Log.v("MyCustomLocationOverlay", "Got new location: " + newPt);
		mMapView.getController().animateTo(newPt);
	}
}
