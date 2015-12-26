package com.androidbook.services.httpsingleton;

// This file is HttpActivity.java
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HttpActivity extends Activity
{
    private HttpClient httpClient;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        httpClient = CustomHttpClient.getHttpClient();
        getHttpContent();
    }

    public void getHttpContent()
    {
        try {
            HttpGet request = new HttpGet("http://www.google.com/");
            HttpParams params = request.getParams();
            HttpConnectionParams.setSoTimeout(params, 60000);   // 1 minute
            request.setParams(params);
            Log.v("connection timeout", String.valueOf(HttpConnectionParams.getConnectionTimeout(params)));
            Log.v("socket timeout", String.valueOf(HttpConnectionParams.getSoTimeout(params)));

            String page = httpClient.execute(request, new BasicResponseHandler());
            System.out.println(page);
		} catch (IOException e) {
			// covers:
			//      ClientProtocolException
			//      ConnectTimeoutException
			//      ConnectionPoolTimeoutException
			//      SocketTimeoutException
			e.printStackTrace();
		}
    }
}
