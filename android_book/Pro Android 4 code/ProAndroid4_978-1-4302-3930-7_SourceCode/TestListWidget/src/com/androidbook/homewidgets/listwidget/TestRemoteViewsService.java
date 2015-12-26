package com.androidbook.homewidgets.listwidget;

import android.content.Intent;

public class TestRemoteViewsService 
extends android.widget.RemoteViewsService 
{    
	@Override    
	public RemoteViewsFactory onGetViewFactory(Intent intent) 
	{
		return new TestRemoteViewsFactory(
				this.getApplicationContext(), intent);
	}
}