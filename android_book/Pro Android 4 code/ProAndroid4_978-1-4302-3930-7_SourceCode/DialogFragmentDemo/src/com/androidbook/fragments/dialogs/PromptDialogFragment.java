package com.androidbook.fragments.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PromptDialogFragment 
extends DialogFragment 
implements View.OnClickListener
{
	private EditText et;

	public static PromptDialogFragment
	newInstance(String prompt)
	{
		PromptDialogFragment pdf = new PromptDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString("prompt",prompt);
		pdf.setArguments(bundle);
		
		return pdf;
	}

	@Override
	public void onAttach(Activity act) {
		// If the activity we're being attached to has
		// not implemented the OnDialogDoneListener
		// interface, the following line will throw a
		// ClassCastException. This is the earliest we
		// can test if we have a well-behaved activity.
		try {
            OnDialogDoneListener test = (OnDialogDoneListener)act;
		}
		catch(ClassCastException cce) {
			// Here is where we fail gracefully.
			Log.e(MainActivity.LOGTAG, "Activity is not listening");
		}
		super.onAttach(act);
	}

    @Override    
    public void onCreate(Bundle icicle)
    {
    	super.onCreate(icicle);
    	this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style,theme);
    }

    public View onCreateView(LayoutInflater inflater,            
    		ViewGroup container, 
    		Bundle icicle)
    {
        View v = inflater.inflate(
        		R.layout.prompt_dialog, container, false);

        TextView tv = (TextView)v.findViewById(
        		                   R.id.promptmessage);
        tv.setText(getArguments().getString("prompt"));

        Button dismissBtn = (Button)v.findViewById(
        		                       R.id.btn_dismiss);
        dismissBtn.setOnClickListener(this);

        Button saveBtn = (Button)v.findViewById(
        		                    R.id.btn_save);
        saveBtn.setOnClickListener(this);

        Button helpBtn = (Button)v.findViewById(
                R.id.btn_help);
        helpBtn.setOnClickListener(this);

        et = (EditText)v.findViewById(R.id.inputtext);
        if(icicle != null)
            et.setText(icicle.getCharSequence("input"));
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle icicle) {
    	icicle.putCharSequence("input", et.getText());
    	super.onPause();
    }

    @Override
    public void onCancel(DialogInterface di) {
    	Log.v(MainActivity.LOGTAG, "in onCancel() of PDF");
    	super.onCancel(di);
    }

    @Override
    public void onDismiss(DialogInterface di) {
    	Log.v(MainActivity.LOGTAG, "in onDismiss() of PDF");
    	super.onDismiss(di);
    }

    public void onClick(View v) 
	{
		OnDialogDoneListener act = (OnDialogDoneListener)getActivity();
		if (v.getId() == R.id.btn_save)
		{
			TextView tv = (TextView)getView().findViewById(R.id.inputtext);
			act.onDialogDone(this.getTag(), false, tv.getText());
			dismiss();
			return;
		}
		if (v.getId() == R.id.btn_dismiss)
		{
			act.onDialogDone(this.getTag(), true, null);
			dismiss();
			return;
		}
		if (v.getId() == R.id.btn_help)
		{
			FragmentTransaction ft = getFragmentManager().beginTransaction();
		    ft.remove(this);

		    // in this case, we want to show the help text, but
		    // come back to the previous dialog when we're done
		    ft.addToBackStack(null);
		    //null represents no name for the back stack transaction

		    HelpDialogFragment hdf =
		    	    HelpDialogFragment.newInstance(R.string.help1);
		    hdf.show(ft, MainActivity.HELP_DIALOG_TAG);
		    return;
		}
	}
}
