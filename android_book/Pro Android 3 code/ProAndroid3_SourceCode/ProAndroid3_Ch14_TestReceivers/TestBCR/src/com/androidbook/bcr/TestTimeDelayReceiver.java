package com.androidbook.bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/*
 * This receiver is introduced to see 
 * how the main thread schedules broad cast receivers
 * 
 * it helps answer such questions as 
 * 1. Do they get invoked in the order they are specificed?
 * 2. Do they get invoked one after the other? or do they get invoked parallel
 * 
 * The time delay here shows that the main thread 
 * gets halted for those many secs. You can see this
 * in the Log.d output 
 */
public class TestTimeDelayReceiver extends BroadcastReceiver 
{
	private static final String tag = "TestTimeDelayReceiver"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Utils.logThreadSignature(tag);
        Log.d(tag, "intent=" + intent);
        Log.d(tag, "going to sleep for 2 secs");
        Utils.sleepForInSecs(2);
        Log.d(tag, "wake up");
        String message = intent.getStringExtra("message");
        Log.d(tag, message);
    }
}

