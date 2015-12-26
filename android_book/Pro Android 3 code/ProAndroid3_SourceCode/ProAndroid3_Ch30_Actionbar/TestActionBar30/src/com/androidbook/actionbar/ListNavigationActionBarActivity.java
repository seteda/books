package com.androidbook.actionbar;

import android.app.ActionBar;
import android.os.Bundle;

public class ListNavigationActionBarActivity 
extends BaseActionBarActivity 
{
	private static String tag=
	  "List Navigation ActionBarActivity";
	
	public ListNavigationActionBarActivity()
	{
		super(tag);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        workwithListActionBar();
    }
    public void workwithListActionBar()
    {
    	ActionBar bar = this.getActionBar();
    	bar.setTitle(tag);
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    	bar.setListNavigationCallbacks(
    	   new SimpleSpinnerArrayAdapter(this),
    	   new ListListener(this,this));
    }
}//eof-class