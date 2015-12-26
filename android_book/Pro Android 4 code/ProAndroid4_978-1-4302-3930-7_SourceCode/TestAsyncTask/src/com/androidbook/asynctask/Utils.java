package com.androidbook.asynctask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

public class Utils {
	public static Calendar getTimeAfterInSecs(int secs) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND,secs);
		return cal;
	}
	public static Calendar getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		return cal;
	}
	public static Calendar getTodayAt(int hours){
		Calendar today = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH);
		//represents the day of the month
		int day = today.get(Calendar.DATE);
		cal.set(year,month,day,hours,0,0);
		return cal;
	}
	public static String getDateTimeString(Calendar cal){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		df.setLenient(false);
		String s = df.format(cal.getTime());
		return s;
	}
	public static long getThreadId()
	{
		Thread t = Thread.currentThread();
		return t.getId();
	}
	public static String getThreadSignature()
	{
		Thread t = Thread.currentThread();
		long l = t.getId();
		String name = t.getName();
		long p = t.getPriority();
		String gname = t.getThreadGroup().getName();
		return (name + ":(id)" + l + ":(priority)" + p
				+ ":(group)" + gname);
	}
	public static void logThreadSignature(String tag)
	{
		Log.d(tag, getThreadSignature());
	}
	public static void sleepForInSecs(int secs)
	{
		try
		{
			Thread.sleep(secs * 1000);
		}
		catch(InterruptedException x)
		{
			throw new RuntimeException("interrupted",x);
		}
	}
}
