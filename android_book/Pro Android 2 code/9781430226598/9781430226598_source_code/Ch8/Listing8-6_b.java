// HttpActivity.java

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HttpActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d("ServicesDemoActivity", "a debug statement");
        getHttpContent();
    }
    public void getHttpContent()
    {
        try {
            ApplicationEx app = (ApplicationEx)this.getApplication();
            HttpClient client = app.getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.google.com/"));
            HttpResponse response = client.execute(request);

            String page=EntityUtils.toString(response.getEntity());
            System.out.println(page);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

