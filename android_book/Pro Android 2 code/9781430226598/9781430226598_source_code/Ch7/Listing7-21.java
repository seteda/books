import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationDemoActivity extends MapActivity {

    MapView mapView = null;
    MyLocationOverlay whereAmI = null;
    LocationManager locMgr = null;
    LocationListener locListener = null;
    
    @Override
    protected boolean isLocationDisplayed() {
        return whereAmI.isMyLocationEnabled();
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapView = (MapView)findViewById(R.id.geoMap);
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(15);
        
        whereAmI = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(whereAmI);
        mapView.postInvalidate();

        locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locListener = new LocationListener()
        {
            public void  onLocationChanged(Location location)
            {
            	showLocation(location);
            }

            public void  onProviderDisabled(String provider)
            {
            }

            public void  onProviderEnabled(String provider)
            {
            }

            public void  onStatusChanged(String provider, 
                int status, Bundle extras)
            {
            }
        };
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Location lastLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        showLocation(lastLoc);
        locMgr.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,			// minTime in ms
            0,			// minDistance in meters
            locListener);
        whereAmI.enableMyLocation();
        whereAmI.runOnFirstFix(new Runnable() {
            public void run() {
                mapView.getController().setCenter(whereAmI.getMyLocation());
            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();
        locMgr.removeUpdates(locListener);
        whereAmI.disableMyLocation();
    }
    
    private void showLocation(Location location) {
        if (location != null)
        {
        	double lat = location.getLatitude();
        	double lng = location.getLongitude();
            GeoPoint myLocation = new GeoPoint(
                (int)(lat*1000000),
                (int)(lng*1000000));
            Toast.makeText(getBaseContext(),
                "New location latitude [" + 
                lat + "] longitude [" + lng +"]",
                Toast.LENGTH_SHORT).show();
            mapView.getController().animateTo(myLocation);
        }
    }
}

