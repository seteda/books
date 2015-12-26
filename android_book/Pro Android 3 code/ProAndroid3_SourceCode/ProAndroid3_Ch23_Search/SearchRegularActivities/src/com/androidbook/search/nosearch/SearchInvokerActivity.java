package com.androidbook.search.nosearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SearchInvokerActivity extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_invoker_activity);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater(); 
 	   	inflater.inflate(R.menu.search_invoker_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	appendMenuItemText(item);
    	if (item.getItemId() == R.id.mid_si_clear)
    	{
    		this.emptyText();
    		return true;
    	}
    	if (item.getItemId() == R.id.mid_si_search)
    	{
    		this.invokeSearch();
    		return true;
    	}
    	return true;
    }
    
    private TextView getTextView()
    {
        return (TextView)this.findViewById(R.id.text1);
    }
    
    private void appendMenuItemText(MenuItem menuItem)
    {
       String title = menuItem.getTitle().toString();
       TextView tv = getTextView(); 
       tv.setText(tv.getText() + "\n" + title);
    }
    private void emptyText()
    {
          TextView tv = getTextView();
          tv.setText("");
    }
    private void invokeSearch()
    {
    	this.onSearchRequested();
    }
	@Override
	public boolean onSearchRequested() 
	{
		this.startSearch("test",true,null,true);
		return true;
	}
    
}