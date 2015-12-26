package com.ai.android.sampledialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ManagedDialogsActivity 
implements IStringPrompterCallBack
{
	
	private final static String tag = "MainActivity";
	
	private GenericManagedAlertDialog gmad = 
		new GenericManagedAlertDialog(this,1,"Simple Sample Alert");
	
	private GenericPromptDialog gmpd = 
		new GenericPromptDialog(this,2,"Your text goes here");
	
	//Initialize this in onCreateOptions
	Menu myMenu = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        this.registerDialogs();
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
		else if (item.getItemId() == R.id.menu_simple_alert)
		{
	    	this.exerciseSimpleAlertDialog();
		}
		else if (item.getItemId() == R.id.menu_simple_prompt)
		{
	    	this.exerciseSimplePromptDialog();
		}
		else if (item.getItemId() == R.id.menu_toast)
		{
	    	this.testToast();
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
	public void promptCallBack(String promptedString, 
			int buttonPressed, //which button was pressed 
			int actionId) //To support multiple prompts
	{
		if (buttonPressed == IStringPrompterCallBack.OK_BUTTON)
		{
			this.appendText("\nprompted string is:" + promptedString);
		}
		else
		{
			this.appendText("\nCancel has been pressed instead");
		}
	}
	private void exercisePrompt()
	{
		Alerts.Prompt(this,"Please enter a filename", this,1);
	}
	
	private int alertDemoCounter=0;
	
    //set this 
    private String dynamicMessage = "Initial Message";
    private Dialog createAlertDialog()
    {
    	return Alerts.createAlertDialog(dynamicMessage, this);
    }
    
    private void prepareAlertDialog(Dialog d)
    {
    	AlertDialog ad = (AlertDialog)d;
    	ad.setMessage(dynamicMessage);
    }
    
    private void exerciseSimpleAlertDialog()
    {
    	this.gmad.show();
    }
    private void exerciseSimplePromptDialog()
    {
    	this.gmpd.show();
    }
    private void exerciseAlertDialog()
    {
    	this.dynamicMessage="Dynamic Message:" + alertDemoCounter++;
    	showDialog(this.gmad.getDialogId());
    }
    protected void registerDialogs()
    {
    	registerDialog(gmad);
    	registerDialog(this.gmpd);
    }
    private void exerciseDialogRegistry()
    {
    	gmad.setAlertMessage("some message:" + this.alertDemoCounter++);
    	gmad.show();
    }
    private void testToast()
    {
    	reportToast("Message1");
    	reportToast("Message2");
    	reportToast("Message3");
    }
	public void reportToast(String message)
	{
		String s = tag + ":" + message;
		Toast mToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		mToast.show();
		Log.d(tag,message);
	}
	public void dialogFinished(ManagedActivityDialog dialog, int buttonId)
	{
		if (dialog instanceof GenericPromptDialog)
		{
			this.appendText("\n" + this.gmpd.getPromptValue());
		}
	}
}//eof-class