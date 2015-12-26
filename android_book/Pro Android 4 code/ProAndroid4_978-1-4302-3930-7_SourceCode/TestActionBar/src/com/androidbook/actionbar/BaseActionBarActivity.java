package com.androidbook.actionbar;

import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

public abstract class BaseActionBarActivity
extends DebugActivity 
{
	private String tag=null;
	public BaseActionBarActivity(String inTag)
	{
		super(R.menu.menu,
			R.layout.main,
			R.id.textViewId,
		inTag);
		tag = inTag;
	}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        TextView tv = this.getTextView();
        tv.setText(tag);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ 
    	super.onCreateOptionsMenu(menu);
    	this.setupSearchView(menu);
    	return true;
    }
    
    protected boolean onMenuItemSelected(MenuItem item)
    {
    	if (item.getItemId() == android.R.id.home) {
    		this.reportBack(tag,"Home Pressed");
    		return true;
    	}
    	
    	if (item.getItemId() == R.id.menu_invoke_tabnav){
    		if (getNavMode() == 
    		  ActionBar.NAVIGATION_MODE_TABS)
    		{
    			this.reportBack(tag, 
    			  "You are already in tab nav");
    		}
    		else {
    			this.invokeTabNav();
    		}
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_invoke_listnav){
    		if (getNavMode() == 
    		ActionBar.NAVIGATION_MODE_LIST)
    		{
    			this.reportBack(tag, 
    			"You are already in list nav");
    		}
    		else{
    			this.invokeListNav();
    		}
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_invoke_standardnav){
    		if (getNavMode() == 
    		ActionBar.NAVIGATION_MODE_STANDARD)
    		{
    			this.reportBack(tag, 
    			"You are already in standard nav");
    		}
    		else{
    			this.invokeStandardNav();
    		}
    		return true;
    	}
    	return false;
    }
    private int getNavMode(){
    	ActionBar bar = this.getActionBar();
    	return bar.getNavigationMode();
    }
    private void invokeTabNav(){
    	Intent i = new Intent(this,
    	  TabNavigationActionBarActivity.class);
    	startActivity(i);
    }
    private void invokeListNav(){
    	Intent i = new Intent(this,
    	  ListNavigationActionBarActivity.class);
    	startActivity(i);
    }
    private void invokeStandardNav(){
    	Intent i = new Intent(this,
    	  StandardNavigationActionBarActivity.class);
    	startActivity(i);
    }
    private void setupSearchView(Menu menu)
    {
    	SearchView searchView = 
    		(SearchView) menu.findItem(R.id.menu_search).getActionView();
    	if (searchView == null)
    	{
    		this.reportBack(tag, "Failed to get search view");
    		return;
    	}
    	//setup searchview
    	SearchManager searchManager = 
    		(SearchManager) getSystemService(Context.SEARCH_SERVICE);
    	ComponentName cn = 
    		new ComponentName(this,SearchResultsActivity.class);
    	SearchableInfo info = 
    		searchManager.getSearchableInfo(cn);
    	if (info == null)
    	{
    		this.reportBack(tag, "Failed to get search info");
    		return;
    	}

    	searchView.setSearchableInfo(info);

		// Do not iconify the widget; expand it by default
		searchView.setIconifiedByDefault(false); 
    	
    }
}//eof-class