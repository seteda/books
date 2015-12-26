package com.androidbook;

import android.os.Bundle;
import android.view.View;
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
    }

    public void myClickHandler(View target) {
        switch(target.getId()) {
        case R.id.zoomin:
            mapView.getController().zoomIn();
            break;
        case R.id.zoomout:
            mapView.getController().zoomOut();
            break;
        case R.id.sat:
            mapView.setSatellite(true);
            break;
        case R.id.street:
            mapView.setStreetView(true);
            break;
        case R.id.traffic:
            mapView.setTraffic(true);
            break;
        case R.id.normal:
            mapView.setSatellite(false);
            mapView.setStreetView(false);
            mapView.setTraffic(false);
            break;
        default:
        	break;
        }

        // The following line should not be required but it is,
        // at least up til Froyo.
        mapView.postInvalidateDelayed(2000);
    }

    @Override
    protected boolean isLocationDisplayed() {
        return false;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
