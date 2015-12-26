package com.ai.android.sampledialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

public class ManagedDialogsActivity extends Activity 
 				implements IDialogFinishedCallBack 
{
	//A registry for managed dialogs
    private DialogRegistry dr = new DialogRegistry();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.registerDialogs();
    }
    
    protected void registerDialogs()
    {
    	// does nothing
    	// have the derived classes override this method
    	// to register their dialogs
    	// example:
    	// registerDialog(this.DIALOG_ALERT_ID_3, gmad);
    	
    }
    public void registerDialog(IDialogProtocol dialog)
    {
    	this.dr.registerDialog(dialog);
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
            	return this.dr.create(id);
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        		this.dr.prepare(dialog, id);
    }

	public void dialogFinished(ManagedActivityDialog dialog, int buttonId)
	{
		//nothing to do
		//have derived classes override this
	}
}
