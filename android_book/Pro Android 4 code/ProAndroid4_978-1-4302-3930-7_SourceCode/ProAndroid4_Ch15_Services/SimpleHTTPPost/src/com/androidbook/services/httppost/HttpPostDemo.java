package com.androidbook.services.httppost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;

public class HttpPostDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(
                    "http://mysomewebsite.com/services/doSomething.do");
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("first", "param value one"));
            postParameters.add(new BasicNameValuePair("issuenum", "10317"));
            postParameters.add(new BasicNameValuePair("username", "dave"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(
            		new InputStreamReader(
            				response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            System.out.println(result);
        } catch(Exception e) {
        	// Do something about exceptions
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}