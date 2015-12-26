package com.ai.android.testmenus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

public class SampleMenusActivity extends Activity {
	
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
    	//call the parent to attach any system level menus
    	super.onCreateOptionsMenu(menu);
    	
    	this.myMenu = menu;
    	
    	//add a few normal menus
    	addRegularMenuItems(menu);
    	
    	//add a few secondary menus
    	add5SecondaryMenuItems(menu);
    	this.addSubMenu(menu);
    	
    	//it must return true to show the menu
    	//if it is false menu won't show
    	return true;
    }
    
    private void addRegularMenuItems(Menu menu)
    {
    	//Secondary items are shown just like everything else
    	int base=Menu.FIRST; // value is 1

    	MenuItem item1 = menu.add(base,base,base,"append");
    	menu.add(base,base+1,base+1,"XML Menus");
    	menu.add(base,base+2,base+2,"clear");
    	
    	menu.add(base,base+3,base+3,"hide secondary");
    	menu.add(base,base+4,base+4,"show secondary");

    	menu.add(base,base+5,base+5,"enable secondary");
    	menu.add(base,base+6,base+6,"disable secondary");
    	
    	menu.add(base,base+7,base+7,"check secondary");
    	MenuItem item8 = menu.add(base,base+8,base+8,"uncheck secondary");

    	//This will show the icon
    	//It might obscure the text
    	item1.setIcon(R.drawable.balloons);
    	
    	//But this does not
    	item8.setIcon(R.drawable.balloons);
    }
    
    private void add5SecondaryMenuItems(Menu menu)
    {
    	//Secondary items are shown just like everything else
    	int base=Menu.CATEGORY_SECONDARY;

    	menu.add(base,base+1,base+1,"sec. item 1");
    	menu.add(base,base+2,base+2,"sec. item 2");
    	menu.add(base,base+3,base+3,"sec. item 3");
    	menu.add(base,base+3,base+3,"sec. item 4");
    	menu.add(base,base+4,base+4,"sec. item 5");
    }

    private void addSubMenu(Menu menu)
    {
    	//Secondary items are shown just like everything else
    	int base=Menu.FIRST + 100;
    	SubMenu sm = menu.addSubMenu(base,base+1,Menu.NONE,"submenu");
    	MenuItem item1 = sm.add(base,base+2,base+2,"sub item1");
    	
    	sm.add(base,base+3,base+3, "sub item2");
    	sm.add(base,base+4,base+4, "sub item3");

    	//work the icons
    	//submenu item icons are not supported
    	item1.setIcon(R.drawable.icon48x48_2);
    	
    	//the following is ok
    	sm.setIcon(R.drawable.icon48x48_1);
    	//This will result in an exception
    	//sm.addSubMenu("try this");
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	if (item.getItemId() == 1)
    	{
        	appendText("\nhello");
    	}
    	else if (item.getItemId() == 2)
    	{
    		this.appendMenuItemText(item);
    		Intent intent = new Intent(this,XMLMenusActivity.class);
    		this.startActivity(intent);
    	}
    	else if (item.getItemId() == 3)
    	{
    		emptyText();
    	}
    	else if (item.getItemId() == 4)
    	{
    		//hide secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,false);
    	}
    	else if (item.getItemId() == 5)
    	{
    		//show secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,true);
    	}
    	else if (item.getItemId() == 6)
    	{
    		//enable secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,true);
    	}
    	else if (item.getItemId() == 7)
    	{
    		//disable secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,false);
    	}
    	else if (item.getItemId() == 8)
    	{
    		//check secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,true,false);
    	}
    	else if (item.getItemId() == 9)
    	{
    		//uncheck secondary
    		this.appendMenuItemText(item);
    		this.myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,false,false);
    	}
    	else
    	{
    		this.appendMenuItemText(item);
    	}
    	//should return true if the menu item
    	//is handled
    	return true;
    	
    	//If it is not our menu item
    	//let the base class handle it
        //return super.onOptionsItemSelected(item);

    }
    
    //Context menu support
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(200, 200, 200, "item1");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
		this.appendMenuItemText(item);
		//menu item has been handled
		return true;
    }
    //This method is here to demonstrate loading xml menu
    //You can call this method from the oncreateoptions menu
    //if you want to use the xml menu instead of programmatically
    //creating the menus.
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
}//eof-class