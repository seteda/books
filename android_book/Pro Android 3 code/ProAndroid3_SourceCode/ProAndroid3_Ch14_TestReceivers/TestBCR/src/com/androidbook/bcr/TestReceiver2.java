package com.androidbook.bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestReceiver2 extends BroadcastReceiver 
{
	private static final String tag = "TestReceiver2"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Utils.logThreadSignature(tag);
        Log.d(tag, "intent=" + intent);
        String message = intent.getStringExtra("message");
        Log.d(tag, message);
    }
}

