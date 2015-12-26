package com.androidbook.actionbar;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public abstract class DebugActivity
extends Activity
implements IReportBack
{
	//Derived classes needs first
    protected abstract boolean 
    onMenuItemSelected(MenuItem item);
    
    //private variables set by constructor
	private static String tag=null;
	private int menuId = 0;
	private int layoutid = 0;
	private int debugTextViewId = 0;
	
	public DebugActivity(int inMenuId, 
			int inLayoutId,
			int inDebugTextViewId,
			String inTag)
	{
		tag = inTag;
		menuId = inMenuId;
		layoutid = inLayoutId;
		debugTextViewId = inDebugTextViewId;
		
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.layoutid);
        TextView tv = this.getTextView();
        tv.setMovementMethod(
          ScrollingMovementMethod.getInstance());
        //tv.setMovementMethod(
        //  new ScrollingMovementMethod());
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
    	boolean b = onMenuItemSelected(item);
    	if (b == true)
    	{
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    protected TextView getTextView(){
        return 
        (TextView)this.findViewById(this.debugTextViewId);
    }
    protected void appendMenuItemText(MenuItem menuItem){
       String title = menuItem.getTitle().toString();
       appendText("MenuItem:" + title);
    }
    protected void emptyText(){
       TextView tv = getTextView();
       tv.setText("");
    }
    protected void appendText(String s){
       TextView tv = getTextView(); 
       tv.setText(s + "\n" + tv.getText());
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
		Toast mToast = 
		  Toast.makeText(this, s, Toast.LENGTH_SHORT);
		mToast.show();
		reportBack(tag,message);
		Log.d(tag,message);
	}
}//eof-class