package com.androidbook.location.update;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationUpdateDemoActivity extends Activity
{
	LocationManager locMgr = null;
	LocationListener locListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locListener = new LocationListener()
        {
            public void  onLocationChanged(Location location)
            {
                if (location != null)
                {
                    Toast.makeText(getBaseContext(),
                        "New location latitude [" +
                        location.getLatitude() +
                        "] longitude [" +
                        location.getLongitude()+"]",
                        Toast.LENGTH_SHORT).show();
                }
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
    public void onResume() {
    	super.onResume();

        locMgr.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            60000,			// minTime in ms
            100,			// minDistance in meters
            locListener);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,
            0,
            new Intent("com.androidbook.location.NEW_SINGLE"),
            PendingIntent.FLAG_UPDATE_CURRENT);

        locMgr.requestSingleUpdate(
            LocationManager.GPS_PROVIDER,
            pIntent);
    }

    @Override
    public void onPause() {
    	super.onPause();
    	locMgr.removeUpdates(locListener);
    }
}

