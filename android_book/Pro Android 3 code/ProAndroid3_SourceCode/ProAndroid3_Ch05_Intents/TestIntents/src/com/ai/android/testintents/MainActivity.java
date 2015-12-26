package com.ai.android.testintents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	
	private final static String tag = "MainActivity";
	
	//Initialize this in onCreateOptions
	Menu myMenu = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setupButton();
        this.setupEditText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
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
    		Log.d(tag,t.getMessage(),t);
    		throw new RuntimeException("error",t);
    	}
    	return true;
    }
    private void handleMenus(MenuItem item)
    {
		this.appendMenuItemText(item);
		if (item.getItemId() == R.id.menu_clear)
		{
			this.emptyText();
		}
		else if (item.getItemId() == R.id.menu_basic_view)
		{
			IntentsUtils.invokeBasicActivity(this);
		}
		else if (item.getItemId() == R.id.menu_show_browser)
		{
			IntentsUtils.invokeWebBrowser(this);
		}
		else if (item.getItemId() == R.id.menu_dial)
		{
			IntentsUtils.dial(this);
		}
		else if (item.getItemId() == R.id.menu_call)
		{
			IntentsUtils.call(this);
		}
		else if (item.getItemId() == R.id.menu_map)
		{
			IntentsUtils.showMapAtLatLong(this);
		}
		else if (item.getItemId() == R.id.menu_testPick)
		{
			IntentsUtils.invokePick(this);
		}
		else if (item.getItemId() == R.id.menu_testGetContent)
		{
			IntentsUtils.invokeGetContent(this);
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
    private void dial()
    {
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	this.startActivity(intent);
    }
    private void setupButton()
    {
       Button b = (Button)this.findViewById(R.id.button1);
       b.setOnClickListener(
             new Button.OnClickListener(){
                public void onClick(View v)
                {
                   parentButtonClicked(v);
                }
             });
       
    }
    private void parentButtonClicked(View v)
    {
       this.appendText("\nbutton clicked");
       this.dialUsingEditText();
    }
    
    private void dialWithNumber(String tel)
    {
    	String  telUriString = "tel:" + tel;
    	Log.d(tag, telUriString);
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse(telUriString));
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	this.startActivity(intent);
    }
    
    private void dialUsingEditText()
    {
    	EditText etext = 
    		(EditText)this.findViewById(R.id.editTextId);
    	String text = etext.getText().toString();
    	if (PhoneNumberUtils.isGlobalPhoneNumber(text) == true)
    	{
    		dialWithNumber(text);
    	}
    }
    private EditText getEditText()
    {
    	EditText etext = 
    		(EditText)this.findViewById(R.id.editTextId);
    	return etext;
    }
    private void setupEditText()
    {
    	EditText etext = this.getEditText();
    	etext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }
    
    protected void onActivityResult(int requestCode
    	,int resultCode
    	,Intent outputIntent)
    {
    	super.onActivityResult(requestCode, resultCode, outputIntent);
    	IntentsUtils.parseResult(this, requestCode, resultCode, outputIntent);
    }
}//eof-class