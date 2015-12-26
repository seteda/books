package com.ai.android.testmenus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;

public class XMLMenusActivity extends Activity {
	
	//Initialize this in onCreateOptions
	Menu myMenu = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TextView tv = new TextView(this);
        //tv.setText("Hello, Android. Say hello");
        //setContentView(tv);
        
        setContentView(R.layout.main);
        
        //Before calling this method make sure
        //set the content view
        registerForContextMenu(this.getTextView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {	
    	this.loadXMLMenu(menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
		this.appendMenuItemText(item);
    	if (item.getItemId() == R.id.xml_menu_popup)
    	{
    		this.showPopupMenu();
    	}
    	else if (item.getItemId() == R.id.menu_clear)
    	{
    		this.emptyText();
    	}
    	return true;
    }
    
    private void loadXMLMenu(Menu menu)
    {
	   MenuInflater inflater = getMenuInflater(); //from activity
	   inflater.inflate(R.menu.my_menu, menu);
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
    private void appendMenuItemText(MenuItem menuItem)
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
    private void showPopupMenu()
    {
    	TextView tv = this.getTextView();
        PopupMenu popup = new PopupMenu(this, tv);        
        //popup.getMenuInflater().inflate(
        //		R.menu.popup_menu, popup.getMenu());
        //Or in api 14
        popup.inflate(R.menu.popup_menu);        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() 
                {            
                    public boolean onMenuItemClick(MenuItem item) 
                    {
                    	appendMenuItemText(item);
                    	return true;
                    }        
                }
        );        
        popup.show();
    }
}//eof-class