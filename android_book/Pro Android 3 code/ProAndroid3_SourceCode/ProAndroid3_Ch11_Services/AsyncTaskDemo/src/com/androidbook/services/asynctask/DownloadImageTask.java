package com.androidbook.services.asynctask;

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
	private Context mContext;

	DownloadImageTask(Context context) {
		mContext = context;
	}

    protected void onPreExecute() {
        // We could do some setup work here before doInBackground() runs
    }
    
	protected Bitmap doInBackground(String... urls) {
		Log.v("doInBackground", "doing download of image");
        return downloadImage(urls);
    }

    protected void onProgressUpdate(Integer... progress) {
    	TextView mText = (TextView) ((Activity) mContext).findViewById(R.id.text);
    	mText.setText("Progress so far: " + progress[0]);
    }

    protected void onPostExecute(Bitmap result) {
    	if(result != null) {
        	ImageView mImage = (ImageView) ((Activity) mContext).findViewById(R.id.image);
            mImage.setImageBitmap(result);
    	}
    	else {
        	TextView errorMsg = (TextView) ((Activity) mContext).findViewById(R.id.errorMsg);
            errorMsg.setText("Problem downloading image. Please try again later.");
    	}
    }

    private Bitmap downloadImage(String... urls)
    {
      HttpClient httpClient = CustomHttpClient.getHttpClient();
      try {
    	HttpGet request = new HttpGet(urls[0]);
    	HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setSoTimeout(params, 60000);   // 1 minute
        request.setParams(params);

        publishProgress(25);

        HttpResponse response = httpClient.execute(request);

        publishProgress(50);

        byte[] image = EntityUtils.toByteArray(response.getEntity());

        publishProgress(75);

        Bitmap mBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        publishProgress(100);

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
}
