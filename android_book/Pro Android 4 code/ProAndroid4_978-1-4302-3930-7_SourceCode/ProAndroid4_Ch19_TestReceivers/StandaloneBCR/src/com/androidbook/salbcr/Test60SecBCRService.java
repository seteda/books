package com.androidbook.salbcr;

import android.content.Intent;
import android.util.Log;

/*
 * Uses IntentService as the base class
 * to make this work on a separate thread.
 */
public class Test60SecBCRService 
extends ALongRunningNonStickyBroadcastService
{
	public static String tag = "Test60SecBCRService";
	
	//Required by IntentService
	public Test60SecBCRService()
	{
		super("com.ai.android.service.Test60SecBCRService");
	}
	
	/*
	 * Perform long running operations in this method.
	 * This is executed in a separate thread. 
	 */
	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) 
	{
		Utils.logThreadSignature(tag);
		Log.d(tag,"Sleeping for 60 secs");
		Utils.sleepForInSecs(60);
		String message = 
			broadcastIntent.getStringExtra("message");
		Log.d(tag,"Job completed");
		Log.d(tag,message);
	}
}
