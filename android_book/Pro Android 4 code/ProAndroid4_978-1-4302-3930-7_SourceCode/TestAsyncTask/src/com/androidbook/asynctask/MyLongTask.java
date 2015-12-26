package com.androidbook.asynctask;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
public class MyLongTask 
extends AsyncTask<String,Integer,Integer>
{
	IReportBack r;
	Context ctx;
	public String tag = null;
	ProgressDialog pd = null;
	MyLongTask(IReportBack inr, Context inCtx, String inTag)
	{
		r = inr;
		ctx = inCtx;
		tag = inTag;
	}
	protected void onPreExecute()
	{
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
		pd = ProgressDialog.show(ctx, "title", "In Progress...",true);
	}
    protected void onProgressUpdate(Integer... progress) 
    {
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
		this.reportThreadSignature();
		
		//will be called multiple times
		//triggered by onPostExecute
    	Integer i = progress[0];
    	r.reportBack(tag, "Progress:" + i.toString());
    }     
    protected void onPostExecute(Integer result) 
    {         
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
    	r.reportBack(tag, "onPostExecute result:" + result);
    	pd.cancel();
    }
    protected Integer doInBackground(String...strings)
    {
		//Runs on a worker thread
    	//May even be a pool if there are 
    	//more tasks.
		Utils.logThreadSignature(this.tag);
		
    	for(String s :strings)
    	{
    		Log.d(tag, "Processing:" + s);
    		//r.reportTransient(tag, "Processing:" + s);
    	}
    	for (int i=0;i<3;i++)
    	{
    		Utils.sleepForInSecs(2);
    		publishProgress(i);
    	}
    	return 1;
    }
    protected void reportThreadSignature()
    {
    	String s = Utils.getThreadSignature();
    	r.reportBack(tag,s);
    }
}
