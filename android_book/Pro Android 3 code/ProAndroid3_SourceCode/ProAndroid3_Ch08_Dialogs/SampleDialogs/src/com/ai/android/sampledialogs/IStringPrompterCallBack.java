package com.ai.android.sampledialogs;

public interface IStringPrompterCallBack 
{
	public static int OK_BUTTON = -1;
	public static int CANCEL_BUTTON = -2;
	public void promptCallBack(String promptedString, 
			int buttonPressed, //which button was pressed 
			int actionId); //To support multiple prompts
}
