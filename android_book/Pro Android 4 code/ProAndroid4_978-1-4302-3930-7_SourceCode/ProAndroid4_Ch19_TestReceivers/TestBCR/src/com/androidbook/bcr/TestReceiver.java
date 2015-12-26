package com.androidbook.bcr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestReceiver extends BroadcastReceiver 
{
	private static final String tag = "TestReceiver"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Utils.logThreadSignature(tag);
        Log.d("TestReceiver", "intent=" + intent);
        String message = intent.getStringExtra("message");
        Log.d(tag, message);
    }
}

