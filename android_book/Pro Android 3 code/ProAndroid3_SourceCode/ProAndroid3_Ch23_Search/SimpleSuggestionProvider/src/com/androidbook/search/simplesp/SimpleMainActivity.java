package com.androidbook.search.simplesp;

import com.androidbook.search.simplesp.R;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SimpleMainActivity extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	//call the parent to attach any system level menus
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater(); //from activity
 	   	inflater.inflate(R.menu.reset_suggestions_menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	if (item.getItemId() == R.id.mid_reset_suggestions)
    	{
            SearchRecentSuggestions suggestions = 
            	new SearchRecentSuggestions(this, 
            		SimpleSuggestionProvider.AUTHORITY, 
            		SimpleSuggestionProvider.MODE);
            suggestions.clearHistory();
    	}
    	return true;
    }
    
}