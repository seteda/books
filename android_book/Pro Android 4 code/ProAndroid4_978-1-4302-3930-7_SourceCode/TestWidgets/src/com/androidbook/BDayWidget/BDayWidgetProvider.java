package com.androidbook.BDayWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class BDayWidgetProvider extends AppWidgetProvider 
{
	private static final String tag = "BDayWidgetProvider";
    public void onUpdate(Context context, 
                        AppWidgetManager appWidgetManager, 
                        int[] appWidgetIds) 
   {
        Log.d(tag, "onUpdate called");
        final int N = appWidgetIds.length;
        Log.d(tag, "Number of widgets:" + N);
        for (int i=0; i<N; i++) 
        {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    public void onDeleted(Context context, int[] appWidgetIds) 
    {
        Log.d(tag, "onDelete called");
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) 
        {
        	Log.d(tag,"deleting:" + appWidgetIds[i]);
        	BDayWidgetModel bwm =
        		BDayWidgetModel.retrieveModel(context, appWidgetIds[i]);
        	bwm.removePrefs(context);
        }
    }
    @Override 
    public void onReceive(Context context, Intent intent) 
    { 
        final String action = intent.getAction(); 
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) 
        {
        	Bundle extras = intent.getExtras();
        	
            final int appWidgetId = extras.getInt 
                              (AppWidgetManager.EXTRA_APPWIDGET_ID, 
                               AppWidgetManager.INVALID_APPWIDGET_ID);
            
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) 
            { 
                this.onDeleted(context, new int[] { appWidgetId }); 
            } 
        } 
        else
        { 
            super.onReceive(context, intent); 
        } 
    }
    public void onEnabled(Context context) 
   {
        Log.d(tag, "onEnabled called");
    	BDayWidgetModel.clearAllPreferences(context);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("com.androidbook.BDayWidget", 
                       ".BDayWidgetProvider"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void onDisabled(Context context) 
    {
        Log.d(tag, "onDisabled called");
    	BDayWidgetModel.clearAllPreferences(context);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("com.androidbook.BDayWidget", 
                       ".BDayWidgetProvider"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void updateAppWidget(Context context, 
                          AppWidgetManager appWidgetManager,
                           int appWidgetId) 
   {
    	BDayWidgetModel bwm = 
    		BDayWidgetModel.retrieveModel(context, appWidgetId);
    	if (bwm == null)
    	{
    		Log.d(tag,"No widget model found for:" + appWidgetId);
    		return;
    	}
    	ConfigureBDayWidgetActivity
    	    .updateAppWidget(context, appWidgetManager, bwm);
   }
}

