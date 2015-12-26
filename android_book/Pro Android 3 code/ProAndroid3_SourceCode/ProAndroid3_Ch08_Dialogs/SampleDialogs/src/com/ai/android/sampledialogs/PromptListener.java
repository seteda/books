package com.ai.android.sampledialogs;

import com.ai.android.sampledialogs.R;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PromptListener 
implements android.content.DialogInterface.OnClickListener
{
	private IStringPrompterCallBack cb = null;
	private int actionId = 0;
	private View promptView;
	public PromptListener(View view
			, Context ctx
			, IStringPrompterCallBack inCb
			, int inActionId)
	{
		promptView = view;
		cb = inCb;
		actionId = inActionId;
	}
	public void onClick(DialogInterface v, int buttonId)
	{
		if (buttonId == DialogInterface.BUTTON1)
		{
			//ok button
			String promptValue = getEnteredText();
			cb.promptCallBack(promptValue 
					,IStringPrompterCallBack.OK_BUTTON
					,actionId);
		}
		else
		{
			String promptValue = getEnteredText();
			cb.promptCallBack(null 
					,IStringPrompterCallBack.CANCEL_BUTTON
					,actionId);
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
}
