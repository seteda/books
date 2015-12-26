package com.androidbook.actionbar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;

public class TabListener extends BaseListener
implements ActionBar.TabListener
{
	private static String tag = "tc>";
	public TabListener(Context ctx, 
			    IReportBack target)
	{
		super(ctx, target);
	}
	public void onTabReselected(Tab tab, 
	              FragmentTransaction ft) 
	{
		this.mReportTo.reportBack(tag, 
		  "ontab re selected:" + tab.getText());
	}
	public void onTabSelected(Tab tab, 
			   FragmentTransaction ft) 
	{
		this.mReportTo.reportBack(tag, 
		  "ontab selected:" + tab.getText());
	}
	public void onTabUnselected(Tab tab, 
			     FragmentTransaction ft) 
	{
		this.mReportTo.reportBack(tag, 
		  "ontab un selected:" + tab.getText());
	}
}
