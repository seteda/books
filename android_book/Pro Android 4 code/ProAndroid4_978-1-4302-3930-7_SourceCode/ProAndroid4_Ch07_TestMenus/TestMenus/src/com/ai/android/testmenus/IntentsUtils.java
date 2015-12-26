package com.ai.android.testmenus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtils 
{
    public static void invokeWebBrowser(Activity activity)
    {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse("http://www.google.com"));
    	activity.startActivity(intent);
    }
    public static void invokeWebSearch(Activity activity)
    {
    	Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
    	intent.setData(Uri.parse("http://www.google.com"));
    	activity.startActivity(intent);
    }
    public static void dial(Activity activity)
    {
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	activity.startActivity(intent);
    }
    
    public static void call(Activity activity)
    {
    	Intent intent = new Intent(Intent.ACTION_CALL);
    	intent.setData(Uri.parse("tel:904-905-5646"));
    	activity.startActivity(intent);
    }
    public static void showMapAtLatLong(Activity activity) 
    {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	//geo:lat,long?z=zoomlevel&q=question-string
    	intent.setData(Uri.parse("geo:0,0?z=4&q=business+near+city"));
    	activity.startActivity(intent);
    }
    
    public static void invokeBaseView(Activity activity)
    {
    	Intent intent = new Intent("com.androidbook.intent.action.ShowBasicView");
    	activity.startActivity(intent);
    }
    
    public static void tryOneOfThese(Activity activity)
    {
    	//IntentsUtils.call(activity);
    	IntentsUtils.invokeBaseView(activity);
    }
}
