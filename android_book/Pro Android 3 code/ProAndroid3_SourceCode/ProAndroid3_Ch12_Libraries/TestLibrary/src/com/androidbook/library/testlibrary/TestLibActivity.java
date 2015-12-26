package com.androidbook.library.testlibrary;

import com.androidbook.library.testlibrary.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class TestLibActivity extends Activity 
{
	public static final String tag="HelloWorldLibActivity";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	//call the parent to attach any system level menus
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater(); //from activity
 	   	inflater.inflate(R.menu.lib_main_menu, menu);
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
    	return true;
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