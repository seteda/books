package com.ai.android.sampledialogs;

import android.content.DialogInterface;

public abstract class ManagedActivityDialog	implements IDialogProtocol
	,android.content.DialogInterface.OnClickListener

{
	private ManagedDialogsActivity mActivity;
	private int mDialogId;
	public ManagedActivityDialog(ManagedDialogsActivity a, int dialogId)
	{
		mActivity = a;
		mDialogId = dialogId;
	}
	public int getDialogId()
	{
		return mDialogId;
	}
	public void show()
	{
		mActivity.showDialog(mDialogId);
	}
	public void onClick(DialogInterface v, int buttonId)
	{
		onClickHook(buttonId);
		this.mActivity.dialogFinished(this, buttonId);
	}
}
