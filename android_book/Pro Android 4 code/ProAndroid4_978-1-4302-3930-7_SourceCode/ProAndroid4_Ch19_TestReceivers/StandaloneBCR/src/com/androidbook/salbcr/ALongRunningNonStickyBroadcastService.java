package com.androidbook.salbcr;

import android.content.Context;
import android.content.Intent;
import android.app.IntentService;
import android.app.Service;

public abstract class ALongRunningNonStickyBroadcastService 
extends IntentService
{
	public static String tag = "ALongRunningBroadcastService";
	protected abstract void 
	handleBroadcastIntent(Intent broadcastIntent);
	
	public ALongRunningNonStickyBroadcastService(String name){ 
		super(name);	
	}	
	/*
	 * This method can be invoked under two circumstances
	 * 1. When a broadcast receiver issues a "startService"
	 * 2. when android restarts it due to pending "startService" intents.
	 * 
	 * In case 1, the broadcast receiver has already
	 * setup the "lightedgreenroom".
	 * 
	 * In case 2, we need to do the same.
	 */
	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		//Set up the green room
		//The setup is capable of getting called multiple times.
		LightedGreenRoom.setup(this.getApplicationContext());

		//It is possible that there are more than one service 
		//of this type is running. 
		//Knowing the number will allow us to clean up 
		//the locks in ondestroy.
		LightedGreenRoom.s_registerClient();
	}
	@Override
	public int onStartCommand(Intent intent, int flag, int startId) 
	{
		//Call the IntetnService "onstart"
		super.onStart(intent, startId);
		
		//Tell the green room there is a visitor
		LightedGreenRoom.s_enter();
		
		//mark this as non sticky
		//Means: Don't restart the service if there are no
		//pending intents.
		return Service.START_NOT_STICKY;
	}
	/*
	 * Note that this method call runs
	 * in a secondary thread setup by the IntentService.
	 * 
	 * Override this method from IntentService.
	 * Retrieve the original broadcast intent.
	 * Call the derived class to handle the broadcast intent.
	 * finally tell the ligthed room that you are leaving.
	 * if this is the last visitor then the lock 
	 * will be released.
	 */
	@Override	
	final protected void onHandleIntent(Intent intent) 
	{		
		try 
		{			
			Intent broadcastIntent
			= intent.getParcelableExtra("original_intent");
			handleBroadcastIntent(broadcastIntent);
		}		
		finally 
		{
			LightedGreenRoom.s_leave();
		}	
	}
	/*
	 * If Android reclaims this process,
	 * this method will release the lock 
	 * irrespective of how many visitors there are.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		LightedGreenRoom.s_unRegisterClient();
	}
}
