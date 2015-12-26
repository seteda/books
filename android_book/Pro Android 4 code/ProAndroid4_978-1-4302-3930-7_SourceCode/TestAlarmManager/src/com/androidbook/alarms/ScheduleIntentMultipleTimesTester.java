package com.androidbook.alarms;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ScheduleIntentMultipleTimesTester 
extends CancelRepeatingAlarmTester 
{
	private static String tag = "ScheduleIntentMultipleTimesTester";
	ScheduleIntentMultipleTimesTester(Context ctx, IReportBack target){
		super(ctx, target);
	}
	/*
	 * Same intent cannot be scheduled multiple times.
	 * If you do, only the last one will take affect.
	 * 
	 * Notice you are using the same request id.
	 */
    public void scheduleSameIntentMultipleTimes()
    {
    	//Get the instance in time that is 
    	//30 secs from now.
    	Calendar cal = Utils.getTimeAfterInSecs(30);
    	Calendar cal2 = Utils.getTimeAfterInSecs(35);
    	Calendar cal3 = Utils.getTimeAfterInSecs(40);
    	Calendar cal4 = Utils.getTimeAfterInSecs(45);
    	
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

    	PendingIntent pi = this.getDistinctPendingIntent(intent, 1);
    	
    	// Schedule the alarm!
    	AlarmManager am = 
    		(AlarmManager)
    		     mContext.getSystemService(Context.ALARM_SERVICE);
    	
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal.getTimeInMillis(), 
    			pi);
    	
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal2.getTimeInMillis(), 
    			pi);
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal3.getTimeInMillis(), 
    			pi);
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal4.getTimeInMillis(), 
    			pi);
    }
	/*
	 * Same intent can be scheduled multiple times
	 * if you change the request id on the pending intent.
	 * Request id identifies an intent as a unique intent.
	 */
    public void scheduleDistinctIntents()
    {
    	//Get the instance in time that is 
    	//30 secs from now.
    	Calendar cal = Utils.getTimeAfterInSecs(30);
    	Calendar cal2 = Utils.getTimeAfterInSecs(35);
    	Calendar cal3 = Utils.getTimeAfterInSecs(40);
    	Calendar cal4 = Utils.getTimeAfterInSecs(45);
    	
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

    	// Schedule the alarms!
    	AlarmManager am = 
    		(AlarmManager)
    		     mContext.getSystemService(Context.ALARM_SERVICE);
    	
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal.getTimeInMillis(), 
    			getDistinctPendingIntent(intent,1));
    	
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal2.getTimeInMillis(), 
    			getDistinctPendingIntent(intent,2));
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal3.getTimeInMillis(), 
    			getDistinctPendingIntent(intent,3));
    	am.set(AlarmManager.RTC_WAKEUP, 
    			cal4.getTimeInMillis(), 
    			getDistinctPendingIntent(intent,4));
    }
}
