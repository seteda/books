package com.androidbook.fragments.bard;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class DetailsActivity extends Activity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
    	Log.v(MainActivity.TAG, "in DetailsActivity onCreate");
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, it means
        	// that our MainActivity is being shown with both
        	// the titles and the text, so this activity is
        	// no longer needed. Bail out and let the MainActivity
        	// do all the work.
            finish();
            return;
        }

        if(getIntent() != null) {
            // This is another way to instantiate a details
            // fragment. 
            DetailsFragment details =
        	    DetailsFragment.newInstance(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                .add(android.R.id.content, details)
                .commit();
        }
    }
}
