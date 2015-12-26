package com.ai.android.sampledialogs;

import android.app.Dialog;

public interface IDialogProtocol 
{
	public Dialog create();
	public void prepare(Dialog dialog);
	public int getDialogId();
	public void show();
	public void onClickHook(int buttonId);
}
