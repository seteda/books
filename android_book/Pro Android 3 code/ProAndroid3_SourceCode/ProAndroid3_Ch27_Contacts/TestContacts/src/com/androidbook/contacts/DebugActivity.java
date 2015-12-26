package com.androidbook.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public abstract class DebugActivity extends Activity
implements IReportBack
{
	//Derived classes needs first
    protected abstract boolean onMenuItemSelected(MenuItem item);
    
    //private variables set by constructor
	private static String tag=null;
	private int menuId = 0;
	
	public DebugActivity(int inMenuId, String inTag)
	{
		tag = inTag;
		menuId = inMenuId;
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity_layout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ 
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater();
 	   	inflater.inflate(menuId, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){ 
    	appendMenuItemText(item);
    	if (item.getItemId() == R.id.menu_da_clear){
    		this.emptyText();
    		return true;
    	}
    	return onMenuItemSelected(item);
    }
    private TextView getTextView(){
        return (TextView)this.findViewById(R.id.text1);
    }
    protected void appendMenuItemText(MenuItem menuItem){
       String title = menuItem.getTitle().toString();
       TextView tv = getTextView(); 
       tv.setText(tv.getText() + "\n" + title);
    }
    protected void emptyText(){
       TextView tv = getTextView();
       tv.setText("");
    }
    private void appendText(String s){
       TextView tv = getTextView(); 
       tv.setText(tv.getText() + "\n" + s);
       Log.d(tag,s);
    }
	public void reportBack(String tag, String message)
	{
		this.appendText(tag + ":" + message);
		Log.d(tag,message);
	}
	public void reportTransient(String tag, String message)
	{
		String s = tag + ":" + message;
		Toast mToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		mToast.show();
		reportBack(tag,message);
		Log.d(tag,message);
	}
}