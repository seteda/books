package com.androidbook.actionbar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class TabNavigationActionBarActivity 
extends BaseActionBarActivity 
{
	private static String tag=
	  "Tab Navigation ActionBarActivity";
	public TabNavigationActionBarActivity()
	{
		super(tag);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        workwithTabbedActionBar();
    }
    
    public void workwithTabbedActionBar()
    {
    	ActionBar bar = this.getActionBar();
    	bar.setTitle(tag);
    	bar.setNavigationMode(
    	  ActionBar.NAVIGATION_MODE_TABS);
    	
		TabListener tl = new TabListener(this,this);
    	
    	Tab tab1 = bar.newTab();
    	tab1.setText("Tab1");
    	tab1.setTabListener(tl);
    	bar.addTab(tab1);
    	
    	Tab tab2 = bar.newTab();
    	tab2.setText("Tab2");
    	tab2.setTabListener(tl);
    	bar.addTab(tab2);
    }
}//eof-class