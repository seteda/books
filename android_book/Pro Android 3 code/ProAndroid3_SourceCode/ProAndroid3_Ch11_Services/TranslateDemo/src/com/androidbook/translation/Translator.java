package com.androidbook.translation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import android.util.Log;

public class Translator {
    private static final String ENCODING = "UTF-8";
    private static final String URL_BASE = "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=";
    private static final String INPUT_TEXT = "&q=";
	private static final String MY_SITE = "http://my.website.com";
	private static final String TAG = "Translator";

    public static String translate(String text, String from, String to) throws Exception {
        try {
            StringBuilder url = new StringBuilder();
            url.append(URL_BASE).append(from).append("%7C").append(to);
            url.append(INPUT_TEXT).append(URLEncoder.encode(text, ENCODING));

            HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
            conn.setRequestProperty("REFERER", MY_SITE);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            try {
                InputStream is= conn.getInputStream();
                String rawResult = makeResult(is);
                
                JSONObject json = new JSONObject(rawResult);
                String result = ((JSONObject)json.get("responseData")).getString("translatedText");
                return (StringEscapeUtils.unescapeXml(result));
            } finally {
                conn.getInputStream().close();
                if(conn.getErrorStream() != null)
                	conn.getErrorStream().close();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static String makeResult(InputStream inputStream) throws Exception {
        StringBuilder outputString = new StringBuilder();
        try {
            String string;
            if (inputStream != null) {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(inputStream, ENCODING));
                while (null != (string = reader.readLine())) {
                    outputString.append(string).append('\n');
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error reading translation stream.", ex);
        }
        return outputString.toString();
    }
}