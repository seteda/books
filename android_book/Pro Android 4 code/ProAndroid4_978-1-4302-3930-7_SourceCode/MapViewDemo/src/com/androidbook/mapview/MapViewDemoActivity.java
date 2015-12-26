package com.androidbook.mapview;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class MapViewDemoActivity extends MapActivity
{
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mapview);

        mapView = (MapView)findViewById(R.id.mapview);

        mapView.setBuiltInZoomControls(true); // getZoomControls() deprecated
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}
