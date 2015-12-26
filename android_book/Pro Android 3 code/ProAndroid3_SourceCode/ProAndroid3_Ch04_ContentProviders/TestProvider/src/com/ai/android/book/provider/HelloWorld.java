package com.ai.android.book.provider;

import android.util.Log;
import android.view.MenuItem;

public class HelloWorld extends MonitoredDebugActivity
implements IReportBack
{
	public static final String tag="HelloWorld";
	private ProviderTester providerTester = null;
	
	public HelloWorld()
	{
		super(R.menu.main_menu,tag);
		this.retainState();
		providerTester = new ProviderTester(this,this);
	}
    protected boolean onMenuItemSelected(MenuItem item)
    {
    	Log.d(tag,item.getTitle().toString());
    	if (item.getItemId() == R.id.menu_add)
    	{
    		providerTester.addBook();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_display_table)
    	{
    		providerTester.showBooks();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_delete)
    	{
    		providerTester.removeBook();
    		return true;
    	}
    	return true;
    }
}