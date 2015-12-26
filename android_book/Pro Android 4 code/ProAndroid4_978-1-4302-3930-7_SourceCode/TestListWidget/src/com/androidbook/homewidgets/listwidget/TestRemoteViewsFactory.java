package com.androidbook.homewidgets.listwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

class TestRemoteViewsFactory 
implements RemoteViewsService.RemoteViewsFactory 
{    
	private Context mContext;    
	private int mAppWidgetId;
	private static String tag="TRVF";
	public TestRemoteViewsFactory(Context context, Intent intent) 
	{        
		mContext = context;        
		mAppWidgetId = 
			intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,                
				AppWidgetManager.INVALID_APPWIDGET_ID);
		
		Log.d(tag,"factory created");
	}

	//Called when your factory is first constructed. 
	//The same factory may be shared across multiple 
	//RemoteViewAdapters depending on the intent passed.	
	public void onCreate() 
	{        
		Log.d(tag,"onCreate called for widget id:" + mAppWidgetId);
	}
	
	//Called when the last RemoteViewsAdapter that is 
	//associated with this factory is unbound. 
	public void onDestroy() 
	{        
		Log.d(tag,"destroy called for widget id:" + mAppWidgetId);
	}    
	   
	//The total number of items
	//in this list
	public int getCount() 
	{ 
	   return 20; 
	}
	   
   public RemoteViews getViewAt(int position) 
   {        
		Log.d(tag,"getview called:" + position);
		RemoteViews rv = 
			new RemoteViews(
				this.mContext.getPackageName(),
				R.layout.list_item_layout);
		String itemText  = "Item:" + position; 
		rv.setTextViewText(
			R.id.textview_widget_list_item_id, itemText);
		
		this.loadItemOnClickExtras(rv, position);
		return rv;
   }    
   private void loadItemOnClickExtras(RemoteViews rv, int position)
   {
	   Intent ei = new Intent();
	   ei.putExtra(TestListWidgetProvider.EXTRA_LIST_ITEM_TEXT,
			   "Position of the item Clicked:" + position);
	   rv.setOnClickFillInIntent(R.id.textview_widget_list_item_id, ei);
	   return;
   }

   //This allows for the use of a custom loading view 
   //which appears between the time that getViewAt(int) 
   //is called and returns. If null is returned, 
   //a default loading view will be used.
   public RemoteViews getLoadingView() 
   {        
	   return null;    
   }
   
   //Not sure how this matters.
   //How many different types of views
   //are there in this list.
   public int getViewTypeCount() 
   {        
	   return 1;    
   }
   
   //The internal id of the item
   //at this position
   public long getItemId(int position) 
   {        
	   return position;    
   }
   
   //True if the same id 
   //always refers to the same object. 
   public boolean hasStableIds() 
   {        
	   return true;    
   }
   
   //Called when notifyDataSetChanged() is triggered 
   //on the remote adapter. This allows a RemoteViewsFactory 
   //to respond to data changes by updating 
   //any internal references. 
   //Note: expensive tasks can be safely performed 
   //synchronously within this method. 
   //In the interim, the old data will be displayed 
   //within the widget.
   public void onDataSetChanged() 
   {        
		Log.d(tag,"onDataSetChanged");
   }
}
