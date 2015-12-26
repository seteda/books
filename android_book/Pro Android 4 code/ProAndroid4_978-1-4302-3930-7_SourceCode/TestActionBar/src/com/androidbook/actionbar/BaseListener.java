package com.androidbook.actionbar;

import android.content.Context;

public class BaseListener 
{
	protected IReportBack mReportTo;
	protected Context mContext;
	public BaseListener(Context ctx, IReportBack target)
	{
		mReportTo = target;
		mContext = ctx;
	}
}
