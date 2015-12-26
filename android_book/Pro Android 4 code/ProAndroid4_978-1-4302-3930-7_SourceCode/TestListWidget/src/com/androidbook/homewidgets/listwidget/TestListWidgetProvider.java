package com.androidbook.homewidgets.listwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class TestListWidgetProvider extends AppWidgetProvider 
{
	private static final String tag = "TestListWidgetProvider";
	
	public static final String ACTION_LIST_CLICK = 
		"com.androidbook.homewidgets.listclick";
	
	public static final String EXTRA_LIST_ITEM_TEXT = 
		"com.androidbook.homewidgets.list_item_text";
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
        super.onUpdate(context,appWidgetManager, appWidgetIds);
    }
    
    public void onDeleted(Context context, int[] appWidgetIds) 
    {
        Log.d(tag, "onDelete called");
        super.onDeleted(context,appWidgetIds);
    }
    
    public void onEnabled(Context context) 
    {
        Log.d(tag, "onEnabled called");
        super.onEnabled(context);
    }

    public void onDisabled(Context context) 
    {
        Log.d(tag, "onDisabled called");
        super.onEnabled(context);
    }

    private void updateAppWidget(Context context, 
                          AppWidgetManager appWidgetManager,
                           int appWidgetId) 
   {
        Log.d(tag, "onupdate called for widget:" + appWidgetId);
        
        final RemoteViews rv = 
        new RemoteViews(context.getPackageName(), 
        		R.layout.test_list_widget_layout);
        
        rv.setEmptyView(R.id.listwidget_list_view_id, 
        		R.id.listwidget_empty_view_id);
        
        // Specify the service to provide data for the 
        // collection widget. Note that we need to            
        // embed the appWidgetId via the data otherwise 
        // it will be ignored.            
        final Intent intent = 
        	new Intent(context, TestRemoteViewsService.class);            
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
        		                           appWidgetId);            
/*        
        intent.setData(
        		Uri.parse(
        				intent.toUri(Intent.URI_INTENT_SCHEME)));
*/        
        rv.setRemoteAdapter(appWidgetId, 
        		R.id.listwidget_list_view_id, intent);
        
        //setup a list view call back.
        //we need a pending intent that is unique 
        //for this widget id. Send a message to 
        //ourselves which we will catch in OnReceive.
        Intent onListClickIntent = 
        	new Intent(context,TestListWidgetProvider.class);
        
        //set an action so that this receiver can distinguish it
        //from other widget related actions
        onListClickIntent.setAction(
        		TestListWidgetProvider.ACTION_LIST_CLICK);
        
        //because this receiver serves all instances 
        //of this app widget. We need to know which 
        //specific instance this message is targeted for.
        onListClickIntent.putExtra(
        		AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        
        //Make this intent unique as we are getting ready
        //to create a pending intent with it.
        //The touri method loads the extras as 
        //part of the uri string.
        //Tthe data of this intent is not used at all except
        //to establish this intent as a unique pending intent.
        //See intent.filteEquals() method to see 
        //how intents are compared to see if they are unique.
        onListClickIntent.setData(
        	Uri.parse(
        		onListClickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        
        //we need to deliver this intent later when
        //the remote view is clicked as a broadcast intent
        //to this same receiver.
        final PendingIntent onListClickPendingIntent = 
        	PendingIntent.getBroadcast(context, 0,                    
        		onListClickIntent, 
        		PendingIntent.FLAG_UPDATE_CURRENT); 
        
        //Set this pending intent as a template for
        //the list item view.
        //Each view in the list will then need to specify
        //a set of additional extras to be appended 
        //to this template and then broadcast the 
        //final template.
        //See how the remoteviewsfactory() sets up 
        //the each item in the list remoteview.
        //See also docs for RemoteViews.setFillIntent()
        rv.setPendingIntentTemplate(R.id.listwidget_list_view_id, 
        		onListClickPendingIntent);
        
        //update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
   }

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction()
				.equals(TestListWidgetProvider.ACTION_LIST_CLICK))
		{
			//this action is not one widget actions
			//this is a specific action that is directed here
			dealwithListAction(context,intent);
			return;
		}
		
		//make sure you call this 
		super.onReceive(context, intent);
	}
	public void dealwithListAction(Context context, Intent  intent)
	{
		String clickedItemText = 
			intent.getStringExtra(
					TestListWidgetProvider.EXTRA_LIST_ITEM_TEXT);
		if (clickedItemText == null)
		{
			clickedItemText = "Error";
		}
		clickedItemText = 
			clickedItemText 
			+ "You have clicked on item:" 
			+ clickedItemText;
		
		Toast t = 
			Toast.makeText(context,clickedItemText,Toast.LENGTH_LONG);
		t.show();
	}
    
}//eof-class

