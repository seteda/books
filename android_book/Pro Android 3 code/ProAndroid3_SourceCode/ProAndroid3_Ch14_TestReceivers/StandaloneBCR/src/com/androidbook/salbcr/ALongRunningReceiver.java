package com.androidbook.salbcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * This is a stand-in broadcast receiver
 * that delegates the work to a service
 * named by the derived class.
 * 
 * The original intent that the broadcast receiver
 * is invoked on is transferred to the 
 * delegated non-sticky service to be handled
 * as a parcellable.
 * 
 * On entry this will set up the 
 * lighted green room. Essentially making the device
 * on.
 * 
 * The service will do the same, if it were to 
 * be woken up due to pending intents.
 */
public abstract class  ALongRunningReceiver 
extends BroadcastReceiver 
{
	private static final String tag = "ALongRunningReceiver"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Log.d(tag,"Receiver started");
    	LightedGreenRoom.setup(context);
    	startService(context,intent);
    	Log.d(tag,"Receiver finished");
    }
    private void startService(Context context, Intent intent)
    {
    	Intent serviceIntent = new Intent(context,getLRSClass());
    	serviceIntent.putExtra("original_intent", intent);
    	context.startService(serviceIntent);
    }
    /*
     * Override this methode to return the 
     * "class" object belonging to the 
     * nonsticky service class.
     */
    public abstract Class getLRSClass();
}

