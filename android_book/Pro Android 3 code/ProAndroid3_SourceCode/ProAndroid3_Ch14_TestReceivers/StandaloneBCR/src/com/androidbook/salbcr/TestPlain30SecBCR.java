package com.androidbook.salbcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
public class TestPlain30SecBCR
extends BroadcastReceiver
{
	private static final String tag = "TestPlain30SecBCR"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Log.d(tag,"Receiver started");
    	startService(context,intent);
    	Log.d(tag,"Receiver finished");
    }
    private void startService(Context context, Intent intent)
    {
    	Intent serviceIntent = new Intent(context,TestPlain30SecBCRService.class);
    	context.startService(serviceIntent);
    }
}
