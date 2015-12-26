package com.androidbook.alarms;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SendAlarmOnceTester extends BaseTester 
{
	private static String tag = "SendAlarmOnceTester";
	SendAlarmOnceTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}

	/*
	 * An alarm can invoke a broadcast request
	 * at a specified time.
	 * The name of the broadcast receiver is explicitly
	 * specified in the intent.
	 */
    public void sendAlarmOnce()
    {
    	//Get the instance in time that is 
    	//30 secs from now.
    	Calendar cal = Utils.getTimeAfterInSecs(30);
    	
    	//If you want to point to 11:00 hours today.
    	//Calendar cal = Utils.getTodayAt(11);

    	//Print to the debug view that we are 
    	//scheduling at a specific time
    	String s = Utils.getDateTimeString(cal);
    	mReportTo.reportBack(tag, "Schdeduling alarm at: " + s);
    	
    	//Get an intent to invoke
    	//TestReceiver class
    	Intent intent = 
    		new Intent(mContext, TestReceiver.class);
    	intent.putExtra("message", "Single Shot Alarm");

    	PendingIntent pi = 
    		PendingIntent.getBroadcast(
    		  mContext, 	//context
              1, 		    //request id, used for disambiguating this intent
              intent, 	    //intent to be delivered 
              PendingIntent.FLAG_ONE_SHOT);  //pending intent flags
    	// Schedule the alarm!
    	AlarmManager am = 
    		(AlarmManager)
    		     mContext.getSystemService(Context.ALARM_SERVICE);
    	
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal.getTimeInMillis(), 
    			pi);
    }
}
