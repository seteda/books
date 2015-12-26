package com.ai.android.testintents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtils 
{
	public static void invokeBasicActivity(Activity activity)
	{
		   String actionName= "com.androidbook.intent.action.ShowBasicView";
		   Intent intent = new Intent(actionName);
		   activity.startActivity(intent);
	}
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
    public static void invokePick(Activity activity)
    {
      Intent pickIntent = new Intent(Intent.ACTION_PICK);
      //pickIntent.setData(Contacts.CONTENT_URI);
      pickIntent.setData(Uri.parse(
         "content://com.google.provider.NotePad/notes"));
      activity.startActivityForResult(pickIntent, 1);
    }
    
    public static void invokeGetContent(Activity activity)
    {
      Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
      pickIntent.setType("vnd.android.cursor.item/vnd.google.note");
      activity.startActivityForResult(pickIntent, 2);
    }
    
    public static void parseResult(MainActivity activity
    		, int requestCode
    		, int resultCode
    		, Intent outputIntent)
    	{
    		activity.appendText("parseResult called");
    		if (resultCode != Activity.RESULT_OK)
    		{
    			activity.appendText("Result code is not ok:" + resultCode);
    			return;
    		}
    		
    		if (requestCode == 1)
    		{
    			parseResultForPick(activity,requestCode,resultCode,outputIntent);
    		}
    		else if (requestCode == 2)
    		{
    			parseResultForContent(activity,requestCode,resultCode,outputIntent);
    		}
    		else
    		{
    			activity.appendText("Wrong request code:" + requestCode);
    			return;
    		}
    	}
    
    public static void parseResultForPick(MainActivity activity
    		, int requestCode
    		, int resultCode
    		, Intent outputIntent)
    	{
    		activity.appendText("parseResult called for pick");
    		activity.appendText("Result code is ok:" + resultCode);
    		activity.appendText("The output uri:");
    		activity.appendText(outputIntent.getData().toString());
    	}
        public static void parseResultForContent(MainActivity activity
    		, int requestCode
    		, int resultCode
    		, Intent outputIntent)
    	{
    		activity.appendText("parseResult called for Content");
    		activity.appendText("Result code is ok:" + resultCode);
    		activity.appendText("The output uri:");
    		activity.appendText(outputIntent.getData().toString());
    	}
        
        public static void tryOneOfThese(Activity activity)
        {
        	IntentsUtils.call(activity);
        }
}
