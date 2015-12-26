package com.ai.android.sampledialogs;

import android.app.Dialog;
import android.util.SparseArray;

public class DialogRegistry 
{
	SparseArray<IDialogProtocol> idsToDialogs
	= new SparseArray();
	public void registerDialog(IDialogProtocol dialog)
	{
		idsToDialogs.put(dialog.getDialogId(),dialog);
	}
	
	public Dialog create(int id)
	{
		IDialogProtocol dp = idsToDialogs.get(id);
		if (dp == null) return null;
		
		return dp.create();
	}
	public void prepare(Dialog dialog, int id)
	{
		IDialogProtocol dp = idsToDialogs.get(id);
		if (dp == null)
		{
			throw new RuntimeException("Dialog id is not registered:" + id);
		}
		dp.prepare(dialog);
	}
}
