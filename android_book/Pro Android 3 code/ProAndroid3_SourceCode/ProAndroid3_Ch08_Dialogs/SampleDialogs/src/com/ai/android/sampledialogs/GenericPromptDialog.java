package com.ai.android.sampledialogs;

import com.ai.android.sampledialogs.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class GenericPromptDialog extends ManagedActivityDialog
{
	private String mPromptMessage = null;
	private View promptView = null;
	String promptValue = null;
	
	private Context ctx = null;
	public GenericPromptDialog(ManagedDialogsActivity inActivity, 
			int dialogId, 
			String promptMessage)
	{
		super(inActivity,dialogId);
		mPromptMessage = promptMessage;
		ctx = inActivity;
	}
	public Dialog create()
	{
    	LayoutInflater li = LayoutInflater.from(ctx);
    	promptView = li.inflate(R.layout.promptdialog, null);
    	AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
    	builder.setTitle("prompt");
    	builder.setView(promptView);
    	builder.setPositiveButton("OK", this);
    	builder.setNegativeButton("Cancel", this);
    	AlertDialog ad = builder.create();
    	return ad;
	}
	
	public void prepare(Dialog dialog)
	{
		//nothing for now
	}
	public void onClickHook(int buttonId)
	{
		if (buttonId == DialogInterface.BUTTON1)
		{
			//ok button
			promptValue = getEnteredText();
		}
	}
	private String getEnteredText()	
	{
		EditText et = 
			(EditText)
			promptView.findViewById(R.id.editText_prompt);
		String enteredText = et.getText().toString();
		Log.d("xx",enteredText);
		return enteredText;
	}
	public String getPromptValue()
	{
		return this.promptValue;
	}
}
