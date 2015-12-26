package com.androidbook.location.proximity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ProximityReceiver extends BroadcastReceiver {

	private static final String TAG = "ProximityReceiver";

	@Override
	public void onReceive(Context arg0, Intent intent) {
		Log.v(TAG, "Got intent");
		if(intent.getData() != null)
			Log.v(TAG, intent.getData().toString());
		Bundle extras = intent.getExtras();
		if(extras != null) {
			Log.v(TAG, "Message: " + extras.getString("message"));
			Log.v(TAG, "Entering? " + 
					extras.getBoolean(LocationManager.KEY_PROXIMITY_ENTERING));
		}
	}
}
