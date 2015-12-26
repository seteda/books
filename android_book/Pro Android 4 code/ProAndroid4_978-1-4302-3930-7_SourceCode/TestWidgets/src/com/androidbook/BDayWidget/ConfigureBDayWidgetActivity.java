package com.androidbook.BDayWidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class ConfigureBDayWidgetActivity extends Activity 
{
	private static String tag = "ConfigureBDayWidgetActivity";
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bday_widget);
        setupButton();
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, 
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }
    private void setupButton()
    {
       Button b = 
    	   (Button)this.findViewById(R.id.bdw_button_update_bday_widget);
       b.setOnClickListener(
             new Button.OnClickListener(){
                public void onClick(View v)
                {
                   parentButtonClicked(v);
                }
             });
       
    }
    private void parentButtonClicked(View v)
    {
    	String name = this.getName();
    	String date = this.getDate();
    	if (Utils.validateDate(date) == false)
    	{
    		this.setDate("wrong date:" + date);
    		return;
    	}
    	if (this.mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
    	{
    		Log.d(tag, "invalid app widget id");
    		return;
    	}
    	updateAppWidgetLocal(name,date);
    	Intent resultValue = new Intent();
    	resultValue.putExtra(
    			AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    	setResult(RESULT_OK, resultValue);
    	finish();
    }
    private String getName()
    {
        EditText nameEdit = 
        	(EditText)this.findViewById(R.id.bdw_bday_name_id);
        String name = nameEdit.getText().toString();
        return name;
    }
    private String getDate()
    {
        EditText dateEdit = 
        	(EditText)this.findViewById(R.id.bdw_bday_date_id);
        String dateString = dateEdit.getText().toString();
        return dateString;
    }
    private void setDate(String errorDate)
    {
        EditText dateEdit = 
        	(EditText)this.findViewById(R.id.bdw_bday_date_id);
        dateEdit.setText("error");
        dateEdit.requestFocus();
    }
    private void updateAppWidgetLocal(String name, String dob)
    {
    	BDayWidgetModel m = 
    		new BDayWidgetModel(mAppWidgetId,name,dob);
    	updateAppWidget(this,
    			AppWidgetManager.getInstance(this),m);
    	m.savePreferences(this);
    }

    public static void updateAppWidget(Context context, 
            AppWidgetManager appWidgetManager,
            BDayWidgetModel widgetModel) 
	{
		RemoteViews views = new RemoteViews(context.getPackageName(), 
		              R.layout.bday_widget);
		
		views.setTextViewText(R.id.bdw_w_name
			, widgetModel.getName() + ":" + widgetModel.iid);
		
		views.setTextViewText(R.id.bdw_w_date
				, widgetModel.getBday());
		
		//update the name
		views.setTextViewText(
				R.id.bdw_w_days,Long.toString(widgetModel.howManyDays()));
		
        Intent defineIntent = new Intent(Intent.ACTION_VIEW, 
        		Uri.parse("http://www.google.com"));
        PendingIntent pendingIntent = 
        	PendingIntent.getActivity(context,
        				0 /* no requestCode */, 
        				defineIntent, 
        				0 /* no flags */);
        views.setOnClickPendingIntent(
        		R.id.bdw_w_button_buy, pendingIntent);
		
		// Tell the widget manager
		appWidgetManager.updateAppWidget(widgetModel.iid, views);
	}
}