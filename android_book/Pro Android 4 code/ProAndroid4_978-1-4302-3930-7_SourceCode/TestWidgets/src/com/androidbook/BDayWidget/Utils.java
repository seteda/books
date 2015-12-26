package com.androidbook.BDayWidget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class Utils 
{
	private static String tag = "Utils";
	public static Date getDate(String dateString)
	throws ParseException
	{
		DateFormat a = getDateFormat();
		Date date = a.parse(dateString);
		return date;
	}
	public static String test(String sdate)
	{
		try
		{
			Date d = getDate(sdate);
			DateFormat a = getDateFormat();
			String s = a.format(d);
			return s;
		}
		catch(Exception x)
		{
			Log.d(tag,"problem");
			return "problem with date:" + sdate;
		}
	}
	public static DateFormat getDateFormat()
	{
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		df.setLenient(false);
		return df;
	}
	
	//valid dates
	//1/1/2009
	//11/11/2009
	//invalid dates
	//13/1/2009
	//1/32/2009
	public static boolean validateDate(String dateString)
	{
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			df.setLenient(false);
			Date date = df.parse(dateString);
			return true;
		}
		catch(ParseException x)
		{
			return false;
		}
	}
	public static long howfarInDays(Date date)
	{
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		long today_ms = today.getTime();
		long target_ms = date.getTime();
		return (target_ms - today_ms)/(1000 * 60 * 60 * 24);
	}
	public static void testPrefSave(Context ctx)
	{
		BDayWidgetModel.clearAllPreferences(ctx);
		BDayWidgetModel m = new BDayWidgetModel(1,"Satya","1/2/2009");
		m.savePreferences(ctx);
		BDayWidgetModel m1 = BDayWidgetModel.retrieveModel(ctx, 1);
		if (m1 == null)
		{
			Log.d(tag,"Cant locate the wm");
			return;
		}
		Log.d(tag,m1.toString());
		m1.setName("Satya2");
		m1.setBday("1/3/2009");
		m1.savePreferences(ctx);
		m1.retrievePrefs(ctx);
		Log.d(tag,"Retrieved m1");
		Log.d(tag,m1.toString());
		
		BDayWidgetModel m3 = new BDayWidgetModel(3,"Satya3","1/3/2009");
		m3.savePreferences(ctx);
		BDayWidgetModel m3r = BDayWidgetModel.retrieveModel(ctx, 3);
		Log.d(tag,"Retrieved m3");
		Log.d(tag,m3.toString());
	}
}
