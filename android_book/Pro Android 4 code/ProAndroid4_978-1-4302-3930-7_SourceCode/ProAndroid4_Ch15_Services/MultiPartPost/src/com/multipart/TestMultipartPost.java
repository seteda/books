package com.multipart;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;

public class TestMultipartPost extends Activity
{
    public void executeMultipartPost()throws Exception
    {

        try {
            InputStream is = this.getAssets().open("data.xml");
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = 
             new HttpPost("http://192.178.10.131/WS2/Upload.aspx");

            byte[] data = IOUtils.toByteArray(is);

            InputStreamBody isb = new InputStreamBody(new 
ByteArrayInputStream(data),"uploadedFile");
            StringBody sb1 = new StringBody("someTextGoesHere");
            StringBody sb2 = new StringBody("someTextGoesHere too");

            MultipartEntity multipartContent = new MultipartEntity();
            multipartContent.addPart("uploadedFile", isb);
            multipartContent.addPart("one", sb1);
            multipartContent.addPart("two", sb2);

            postRequest.setEntity(multipartContent);
            HttpResponse res =httpClient.execute(postRequest);
            res.getEntity().getContent().close();
        } catch (Throwable e)
        {
            // handle exception here
         }
    }
}
