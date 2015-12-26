package com.androidbook.services.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HttpActivity extends Activity {
    private DownloadImageTask diTask;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void doClick(View view) {
    	if(diTask != null) {
    		AsyncTask.Status diStatus = diTask.getStatus();
    		Log.v("doClick", "diTask status is " + diStatus);
    		if(diStatus != AsyncTask.Status.FINISHED) {
    			Log.v("doClick", "... no need to start a new task");
                return;
    		}
    		// Since diStatus must be FINISHED, we can try again.
    	}
  	    diTask = new DownloadImageTask(this);
   	    diTask.execute("http://chart.apis.google.com/chart?&cht=p&chs=460x250&chd=t:15.3,20.3,0.2,59.7,4.5&chl=Android%201.5%7CAndroid%201.6%7COther*%7CAndroid%202.1%7CAndroid%202.2&chco=c4df9b,6fad0c");
    }
}
