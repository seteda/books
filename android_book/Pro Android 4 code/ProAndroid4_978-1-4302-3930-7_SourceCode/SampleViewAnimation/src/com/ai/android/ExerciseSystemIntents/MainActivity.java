package com.ai.android.ExerciseSystemIntents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	
	private final static String L = "MainActivity";
	
	
	//Initialize this in onCreateOptions
	Menu myMenu = null;
	
    /** Called when the activity is first created. */
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
    	
    	this.myMenu = menu;

    	MenuInflater mi = this.getMenuInflater();
    	mi.inflate(R.menu.main_menu,menu);
    	
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	try
    	{
    		handleMenus(item);
    	}
    	catch(Throwable t)
    	{
    		Log.d(this.L,t.getMessage(),t);
    		throw new RuntimeException("error",t);
    	}
    	//should return true if the menu item
    	//is handled
    	return true;
    	
    	//If it is not our menu item
    	//let the base class handle it
        //return super.onOptionsItemSelected(item);

    }
    private void handleMenus(MenuItem item)
    {
		this.appendMenuItemText(item);
		if (item.getItemId() == R.id.menu_clear)
		{
			this.emptyText();
		}
		else if (item.getItemId() == R.id.menu_list_animation)
		{
			Intent intent = new Intent(this, ViewAnimationActivity.class);
			startActivity(intent);
		}
    }
    
    private TextView getTextView()
    {
       	TextView tv = 
       		(TextView)this.findViewById(R.id.textViewId);
       	return tv;
    }
    public void appendText(String text)
    {
       	TextView tv = 
       		(TextView)this.findViewById(R.id.textViewId);
       	tv.setText(tv.getText() + text);
    }
    public void appendMenuItemText(MenuItem menuItem)
    {
    	String title = menuItem.getTitle().toString();
       	TextView tv = 
       		(TextView)this.findViewById(R.id.textViewId);
       	tv.setText(tv.getText() + "\n" + title + ":" + menuItem.getItemId());
    }
    private void emptyText()
    {
       	TextView tv = 
       		(TextView)this.findViewById(R.id.textViewId);
       	tv.setText("");
    }
    
}//eof-class