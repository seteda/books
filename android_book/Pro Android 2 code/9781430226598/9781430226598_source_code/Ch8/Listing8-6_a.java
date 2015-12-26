// ApplicationEx.java
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Application;
import android.util.Log;

public class ApplicationEx extends Application
{
    private static final String TAG = "ApplicationEx";
    private HttpClient httpClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        httpClient = createHttpClient();

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


    private HttpClient createHttpClient()
    {
        Log.d(TAG,"createHttpClient()...");
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

    
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", 
       PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", 
        SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new 
         ThreadSafeClientConnManager(params,schReg);

        return new DefaultHttpClient(conMgr, params);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    private void shutdownHttpClient()
    {
        if(httpClient!=null && httpClient.getConnectionManager()!=null)
        {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
