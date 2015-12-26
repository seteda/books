package com.androidbook.salbcr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/*
 * The goal of this class is 
 * to test what happens when a simple service is
 * used to to test a 30sec load.
 * 
 * When you want to test this un-comment this receiver
 * in the manifest file.
 * 
 * You will see an ANR in the logcat the pid killed
 */
public class TestPlain30SecBCRService 
extends Service
{
	public static String tag = "TestPlain30SecBCRService";
	
    @Override
    public void onCreate() 
    {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) 
    {
        super.onStart(intent, startId);
		Utils.logThreadSignature(tag);
		Log.d(tag,"Sleeping for 30 secs");
		Utils.sleepForInSecs(30);
		Log.d(tag,"Job completed");
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
