package com.androidbook.asynctask;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class MyApplication extends Application
{
	public final static String tag="MyApplication";

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d(tag,"configuration changed");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(tag,"oncreate");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Log.d(tag,"onLowMemory");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(tag,"onTerminate");
	}

}
