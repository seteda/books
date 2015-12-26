package com.androidbook.salbcr;

import com.androidbook.salbcr.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver 
{
	private static final String tag = "Notification Receiver"; 
    @Override
    public void onReceive(Context context, Intent intent) 
    {
    	Utils.logThreadSignature(tag);
        Log.d(tag, "intent=" + intent);
        String message = intent.getStringExtra("message");
        Log.d(tag, message);
        this.sendNotification(context, message);
    }
    private void sendNotification(Context ctx, String message)
    {
    	//Get the notification manager
    	String ns = Context.NOTIFICATION_SERVICE;
    	NotificationManager nm = 
    		(NotificationManager)ctx.getSystemService(ns);
    	
    	//Create Notification Object
		int icon = R.drawable.robot;
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();
		
		Notification notification = 
			new Notification(icon, tickerText, when);

		//Set ContentView using setLatestEvenInfo
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.setData(Uri.parse("http://www.google.com"));
	    PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, 0);
	    notification.setLatestEventInfo(ctx, "title", "text", pi);
	  		
	    //Send notification
		nm.notify(1, notification);
    }
}

