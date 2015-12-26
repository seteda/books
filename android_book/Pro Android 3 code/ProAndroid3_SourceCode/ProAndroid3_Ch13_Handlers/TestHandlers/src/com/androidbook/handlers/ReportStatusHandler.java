package com.androidbook.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ReportStatusHandler extends Handler
{
	public static final String tag = "TestHandler2";
	private TestHandlersDriverActivity parentActivity = null; 
	
	public ReportStatusHandler(TestHandlersDriverActivity inParentActivity)
	{
		parentActivity = inParentActivity;
	}

	@Override
	public void handleMessage(Message msg) 
	{
		String pm = Utils.getStringFromABundle(msg.getData());
				
		Log.d(tag,pm);
		this.printMessage(pm);
	}

	private void printMessage(String xyz)
	{
		parentActivity.appendText(xyz);
	}
}
