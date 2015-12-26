package com.androidbook.services.applicationex;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;

import android.app.Application;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class ApplicationEx extends Application
{
    private static final String TAG = "ApplicationEx";
	private AndroidHttpClient httpClient;

    @Override
    public void onCreate()
    {
        super.onCreate();
        httpClient = createHttpClient();
    }

    private AndroidHttpClient createHttpClient()
    {
    	AndroidHttpClient httpClient = AndroidHttpClient.newInstance("Mozilla/5.0 (Linux; U; Android 2.1; en-us; ADR6200 Build/ERD79) AppleWebKit/530.17 (KHTML, like Gecko) Version/ 4.0 Mobile Safari/530.17");

    	ClientConnectionManager conMgr = httpClient.getConnectionManager();
    	Log.v(TAG, "Connection manager is " + conMgr.getClass().getName());

    	SchemeRegistry schReg = conMgr.getSchemeRegistry();
    	for(String scheme : schReg.getSchemeNames()) {
    		Log.v(TAG, "Scheme: " + scheme + ", port: " +
    		    schReg.getScheme(scheme).getDefaultPort() +
    		    ", factory: " +
    		    schReg.getScheme(scheme).getSocketFactory().getClass().getName());
    	}

    	HttpParams params = httpClient.getParams();

    	Log.v(TAG, "http.protocol.version: " + params.getParameter("http.protocol.version"));
    	Log.v(TAG, "http.protocol.content-charset: " + params.getParameter("http.protocol.content-charset"));
    	Log.v(TAG, "http.protocol.handle-redirects: " + params.getParameter("http.protocol.handle-redirects"));
    	Log.v(TAG, "http.conn-manager.timeout: " + params.getParameter("http.conn-manager.timeout"));
    	Log.v(TAG, "http.socket.timeout: " + params.getParameter("http.socket.timeout"));
    	Log.v(TAG, "http.connection.timeout: " + params.getParameter("http.connection.timeout"));

        return httpClient;
    }

    public AndroidHttpClient getHttpClient() {
    	if(httpClient == null)
    		httpClient = createHttpClient();
        return httpClient;
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        shutdownHttpClient();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        shutdownHttpClient();
    }

    private void shutdownHttpClient()
    {
        if(httpClient!=null) {
        	if(httpClient.getConnectionManager()!=null) {
                httpClient.getConnectionManager().shutdown();
        	}
            httpClient.close();
            httpClient = null;
        }
    }
}