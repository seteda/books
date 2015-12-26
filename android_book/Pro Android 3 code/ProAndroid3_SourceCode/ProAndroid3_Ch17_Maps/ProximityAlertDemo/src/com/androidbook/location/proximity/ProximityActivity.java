package com.androidbook.location.proximity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

public class ProximityActivity extends Activity {
	private final String PROX_ALERT = "com.androidbook.intent.action.PROXIMITY_ALERT";
	private ProximityReceiver proxReceiver = null;
	private LocationManager locMgr = null;
	PendingIntent pIntent1 = null;
	PendingIntent pIntent2 = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);

        double lat = 30.334954;		// Coordinates for Jacksonville, FL
        double lon = -81.5625;
        float radius = 5.0f * 1609.0f; // 5 miles times 1609 meters per mile

        String geo = "geo:"+lat+","+lon;

        Intent intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", "Jacksonville, FL");

        pIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
        		PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locMgr.addProximityAlert(lat, lon, radius, 2000L, pIntent1);

        lat = 28.54;		// Coordinates for Orlando, FL
        lon = -81.38;
        geo = "geo:"+lat+","+lon;

        intent = new Intent(PROX_ALERT, Uri.parse(geo));
        intent.putExtra("message", "Orlando, FL");

        pIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
        		PendingIntent.FLAG_CANCEL_CURRENT);

        locMgr.addProximityAlert(lat, lon, radius, 60000L, pIntent2);

        proxReceiver = new ProximityReceiver();

        IntentFilter iFilter = new IntentFilter(PROX_ALERT);
        iFilter.addDataScheme("geo");

        registerReceiver(proxReceiver, iFilter);
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(proxReceiver);
    	locMgr.removeProximityAlert(pIntent1);
    	locMgr.removeProximityAlert(pIntent2);
    }
}