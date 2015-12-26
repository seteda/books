package com.ai.android.sampledialogs;

public interface IDialogFinishedCallBack 
{
	public static int OK_BUTTON = -1;
	public static int CANCEL_BUTTON = -2;
	public void dialogFinished(ManagedActivityDialog dialog, int buttonId);
}
