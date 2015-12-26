package com.androidbook.alarms;

/*
 * An interface implemented typically by an activity
 * so that a worker class can report back
 * on what happened.
 */
public interface IReportBack 
{
	public void reportBack(String tag, String message);
}
