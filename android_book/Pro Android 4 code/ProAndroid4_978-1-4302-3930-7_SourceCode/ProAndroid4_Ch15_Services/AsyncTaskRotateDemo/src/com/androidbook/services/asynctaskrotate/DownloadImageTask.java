package com.androidbook.services.asynctaskrotate;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
	private Context mContext;  // reference to the calling Activity
	int progress = -1;
	Bitmap downloadedImage = null;

	DownloadImageTask(Context context) {
		mContext = context;
	}

	// Called from main thread to re-attach
	protected void setContext(Context context) {
		mContext = context;
		if(progress >= 0) {
            publishProgress(this.progress);
		}
	}

    protected void onPreExecute() {
    	progress = 0;
        // We could do some other setup work here before doInBackground() runs
    }
    
	protected Bitmap doInBackground(String... urls) {
		Log.v("doInBackground", "doing download of image...");
        return downloadImage(urls);
    }

    protected void onProgressUpdate(Integer... progress) {
    	TextView mText = (TextView)
                ((Activity) mContext).findViewById(R.id.text);
    	mText.setText("Progress so far: " + progress[0]);
    }

    protected void onPostExecute(Bitmap result) {
    	if(result != null) {
    		downloadedImage = result;
    		setImageInView();
    	}
    	else {
        	TextView errorMsg = (TextView)
                ((Activity) mContext).findViewById(R.id.errorMsg);
            errorMsg.setText("Problem downloading image. Please try later.");
    	}
    }

    public Bitmap downloadImage(String... urls)
    {
      HttpClient httpClient = CustomHttpClient.getHttpClient();
      try {
    	HttpGet request = new HttpGet(urls[0]);
    	HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setSoTimeout(params, 60000);   // 1 minute
        request.setParams(params);

        setProgress(25);

        HttpResponse response = httpClient.execute(request);

        setProgress(50);

        sleepFor(5000);    // five second sleep

        byte[] image = EntityUtils.toByteArray(response.getEntity());

        setProgress(75);

        Bitmap mBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        setProgress(100);

        return mBitmap;
	  } catch (IOException e) {
		// covers:
        //      ClientProtocolException
        //      ConnectTimeoutException
        //      ConnectionPoolTimeoutException
        //      SocketTimeoutException
        e.printStackTrace();
      }
      return null;
    }

    private void setProgress(int progress) {
    	this.progress = progress;
    	publishProgress(this.progress);
    }

    protected void setImageInView() {
    	if(downloadedImage != null) {
    	    ImageView mImage = (ImageView)
    	        ((Activity) mContext).findViewById(R.id.image);
            mImage.setImageBitmap(downloadedImage);
    	}
    }

    private void sleepFor(long msecs) {
        try {
			Thread.sleep(msecs);
		} catch (InterruptedException e) {
			Log.v("sleep", "interrupted");
		}
    }
}
