package com.androidbook.search.custom;

import com.androidbook.search.custom.R;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

//filename: OpenGLTestHarnessActivity.java
public class SearchActivity extends Activity 
{
	private final static String tag ="SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(tag,"I am being created");
        setContentView(R.layout.layout_test_search_activity);
        //this.
        //otherwise do this
        //setContentView(R.layout.layout_test_search_activity);
    	//this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_GLOBAL);
    	//this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);
    	
        // get and process search query here
        final Intent queryIntent = getIntent();
        
        //query action
        final String queryAction = queryIntent.getAction();
        Log.d(tag,"Create Intent action:"+queryAction);

        final String queryString = 
        	queryIntent.getStringExtra(SearchManager.QUERY);
        Log.d(tag,"Create Intent query:"+queryString);
        
        if (Intent.ACTION_SEARCH.equals(queryAction)) 
        {
        	this.doSearchQuery(queryIntent);
        }
        else if (Intent.ACTION_VIEW.equals(queryAction)) 
        {
        	this.doView(queryIntent);
        }
        else {
        	Log.d(tag,"Create intent NOT from search");
        }
        return;
    }
    
    @Override
    public void onNewIntent(final Intent newIntent) 
    {
        super.onNewIntent(newIntent);
        Log.d(tag,"new intent calling me");
        
        // get and process search query here
        final Intent queryIntent = newIntent;
        
        //query action
        final String queryAction = queryIntent.getAction();
        Log.d(tag,"New Intent action:"+queryAction);

        final String queryString = 
        	queryIntent.getStringExtra(SearchManager.QUERY);
        Log.d(tag,"New Intent query:"+queryString);
        
        if (Intent.ACTION_SEARCH.equals(queryAction)) 
        {
        	this.doSearchQuery(queryIntent);
        }
        else if (Intent.ACTION_VIEW.equals(queryAction)) 
        {
        	this.doView(queryIntent);
        }
        else {
        	Log.d(tag,"New intent NOT from search");
        }
        return;
    }
    private void doSearchQuery(final Intent queryIntent) 
    {
        final String queryString = 
        	queryIntent.getStringExtra(SearchManager.QUERY);
        appendText("You are searching for:" + queryString);
    }
    private void appendText(String msg)
    {
        TextView tv = (TextView)this.findViewById(R.id.text1);
        tv.setText(tv.getText() + "\n" + msg);
    }
    private void doView(final Intent queryIntent) 
    {
        Uri uri = queryIntent.getData();
        String action = queryIntent.getAction();
        Intent i = new Intent(action);
        i.setData(uri);
        //i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(i);
        this.finish();
    }
}