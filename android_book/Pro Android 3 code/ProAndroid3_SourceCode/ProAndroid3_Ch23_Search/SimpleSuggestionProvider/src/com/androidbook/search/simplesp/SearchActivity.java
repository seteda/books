package com.androidbook.search.simplesp;

import com.androidbook.search.simplesp.R;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;


//filename: OpenGLTestHarnessActivity.java
public class SearchActivity extends Activity 
{
	private final static String tag ="SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(tag,"I am being created");
        //otherwise do this
        setContentView(R.layout.layout_test_search_activity);
    	//this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_GLOBAL);
    	this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);
    	
        // get and process search query here
        final Intent queryIntent = getIntent();
        final String queryAction = queryIntent.getAction();
        if (Intent.ACTION_SEARCH.equals(queryAction)) 
        {
        	Log.d(tag,"new intent for search");
        	this.doSearchQuery(queryIntent);
        }
        else {
        	Log.d(tag,"new intent NOT for search");
        }
        return;
    }
    
    @Override
    public void onNewIntent(final Intent newIntent) 
    {
        super.onNewIntent(newIntent);
        Log.d(tag,"new intent calling me");
        
        // get and process search query here
        final Intent queryIntent = getIntent();
        final String queryAction = queryIntent.getAction();
        if (Intent.ACTION_SEARCH.equals(queryAction)) 
        {
        	this.doSearchQuery(queryIntent);
        	Log.d(tag,"new intent for search");
        }
        else {
        	Log.d(tag,"new intent NOT for search");
        }
    }
    private void doSearchQuery(final Intent queryIntent) 
    {
        final String queryString = 
        	queryIntent.getStringExtra(SearchManager.QUERY);
        
        // Record the query string in the recent queries suggestions provider.
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, 
        		SimpleSuggestionProvider.AUTHORITY, 
        		SimpleSuggestionProvider.MODE);
        suggestions.saveRecentQuery(queryString, "SSSP");
    }
}
    
