package com.androidbook.commoncontrols;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CheckBoxActivity extends Activity {
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.checkbox);
	        
	        // No handling in here for the Chicken checkbox
	        
	        CheckBox fishCB = (CheckBox)findViewById(R.id.fishCB);

	        if(fishCB.isChecked())
                fishCB.toggle();        // flips the checkbox to unchecked if it was checked

            fishCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
					Log.v("CheckBoxActivity", "The fish checkbox is now "
							+ (isChecked?"checked":"not checked"));					
				}});
	    }
	    
	    public void doClick(View view) {
	    	Log.v("CheckBoxActivity", "The steak checkbox is now " +
	    			(((CheckBox)view).isChecked()?"checked":"not checked"));
	    }
}
