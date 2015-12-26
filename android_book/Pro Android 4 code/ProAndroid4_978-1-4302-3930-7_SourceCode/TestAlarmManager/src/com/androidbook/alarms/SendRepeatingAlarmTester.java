package com.androidbook.alarms;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SendRepeatingAlarmTester 
extends SendAlarmOnceTester 
{
	private static String tag = "SendRepeatingAlarmTester";
	SendRepeatingAlarmTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}

	/*
	 * An alarm can invoke a broadcast request
	 * starting at a specified time and at 
	 * regular intervals.
	 * 
	 * Uses the same intent as above
	 * but a distinct request id to avoid conflicts
	 * with the single shot alarm above.
	 * 
	 * Uses getDistinctPendingIntent() utility.
	 */
    public void sendRepeatingAlarm()
    {
    	Calendar cal = Utils.getTimeAfterInSecs(30);
    	//Calendar testcal = Utils.getTodayAt(11);
    	String s = Utils.getDateTimeString(cal);
    	this.mReportTo.reportBack(tag,
    		"Schdeduling Repeating alarm in 5 sec interval starting at: " + s);
    	
    	//Get an intent to invoke
    	//TestReceiver class
    	Intent intent = 
    		new Intent(this.mContext, TestReceiver.class);
    	intent.putExtra("message", "Repeating Alarm");

    	PendingIntent pi = this.getDistinctPendingIntent(intent, 2);
    	// Schedule the alarm!
    	AlarmManager am = 
    		(AlarmManager)
    		    this.mContext.getSystemService(Context.ALARM_SERVICE);
    	
    	am.setRepeating(AlarmManager.RTC_WAKEUP, 
    			cal.getTimeInMillis(), 
    			5*1000, //5 secs 
    			pi);
    }
    
    protected PendingIntent getDistinctPendingIntent(Intent intent, int requestId)
    {
    	PendingIntent pi = 
    		PendingIntent.getBroadcast(
    		  mContext, 	//context
              requestId, 	//request id
              intent, 		//intent to be delivered
              0);

        //pending intent flags
        //PendingIntent.FLAG_ONE_SHOT);    	
    	return pi;
    }
}
