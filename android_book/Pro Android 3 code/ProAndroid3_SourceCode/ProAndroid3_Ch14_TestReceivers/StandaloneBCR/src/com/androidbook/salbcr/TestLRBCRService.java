package com.androidbook.salbcr;

import android.content.Intent;
import android.util.Log;

/*
 * Uses IntentService as the base class
 * to make this work on a separate thread.
 */
public class TestLRBCRService 
extends ALongRunningNonStickyBroadcastService
{
	public static String tag = "TestLRBCRService";
	//Required by IntentService
	public TestLRBCRService(){
		super("com.ai.android.service.TestLRBCRService");
	}
	/*
	 * Perform long running operations in this method.
	 * This is executed in a separate thread. 
	 */
	@Override
	protected void handleBroadcastIntent(Intent broadcastIntent) 
	{
		Utils.logThreadSignature(tag);
		String message = 
			broadcastIntent.getStringExtra("message");
		Log.d(tag,message);
	}
}
