package com.androidbook.library.testlibraryapp;

import com.androidbook.library.testlibraryapp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.androidbook.library.testlibrary.*;

public class TestAppActivity extends Activity 
{
	public static final String tag="HelloWorld";
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
 	   	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	appendMenuItemText(item);
    	if (item.getItemId() == R.id.menu_clear)
    	{
    		this.emptyText();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_library_activity)
    	{
    		this.invokeLibActivity(item.getItemId());
    		return true;
    	}
    	return true;
    }
    
    private void invokeLibActivity(int mid)
    {
		Intent intent = new Intent(this,TestLibActivity.class);
		intent.putExtra("com.ai.menuid", mid);
		startActivity(intent);
    }
    
    private TextView getTextView()
    {
        return (TextView)this.findViewById(R.id.text1);
    }
    public void appendText(String abc)
    {
        TextView tv = getTextView(); 
        tv.setText(tv.getText() + "\n" + abc);
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
}