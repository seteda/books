package com.androidbook.actionbar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;

public class StandardNavigationActionBarActivity 
extends BaseActionBarActivity 
{
	private static String tag=
	  "Standard Navigation ActionBarActivity";
	public StandardNavigationActionBarActivity()
	{
		super(tag);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        workwithStandardActionBar();
    }
    
    public void workwithStandardActionBar()
    {
    	ActionBar bar = this.getActionBar();
    	bar.setTitle(tag);
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    	//test to see what happens if you were to attach tabs
    	attachTabs(bar);
    }
    public void attachTabs(ActionBar bar)
    {
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