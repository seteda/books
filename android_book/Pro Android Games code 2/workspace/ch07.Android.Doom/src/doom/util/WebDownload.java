/*******************************************************************************
 * Copyright (c) 2006 Vladimir Silva and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Vladimir Silva - initial API and implementation
 *******************************************************************************/
package doom.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import android.util.Log;

/**
 * Web Download tool. This tool will impersonate WMP by settings the HTTP headers:
 * <pre>
 * User-Agent: NSPlayer/8.0.0.4477
 * 
 * @author Owner
 *
 */
public class WebDownload 
{
	private static final String TAG = "WebDownload";
	
	public static final String USER_AGENT = "NSPlayer/8.0.0.4477";

	private static final int READ_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;
    
	private Map<String, List<String>> headers;
	private URL url;
    private int status;
    private HttpURLConnection uc;
    private String responseMessage;
    
    private DownloadListener listener;
    private boolean cancel = false;
    
    /**
     * Listener
     * @author Owner
     *
     */
    static public interface DownloadListener {
    	public void onDownload(final int numBytes); 
    	public void onDownloadComplete(int totalBytes, URL url, boolean cancelled);
    }
    
    /**
     * C
     * @param url
     * @throws MalformedURLException
     */
	public WebDownload(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	public WebDownload(URL url) throws MalformedURLException {
		this.url = url;
	}
	
	public void setDownloadLsitener(DownloadListener l) {
		listener = l;
	}
	
    /**
     * Simple HTTP Get for URLs that return text data
     * @param os stream where the output will be written
     * @throws MalformedURLException
     * @throws IOException
     */
    public void doGet (OutputStream os, boolean gzip)
		throws  IOException 
	{
    	BufferedOutputStream bos = new BufferedOutputStream(os, 12288);
    	try {
    	    uc = (HttpURLConnection)url.openConnection();

    	    // This will impersonate WMP for ASX URL downloads
    	    uc.setRequestProperty("User-Agent", USER_AGENT);
    	    uc.setConnectTimeout(CONNECT_TIMEOUT);
    	    uc.setReadTimeout(READ_TIMEOUT);
    	    
    	    BufferedInputStream buffer  = gzip 
    	    		? new BufferedInputStream(new GZIPInputStream(uc.getInputStream()))
    	    		: new BufferedInputStream(uc.getInputStream(), 12288);   

		    //dumpHeaders();

    	    int c;
    	    byte[] buf = new byte[1024];
    	    int numBytes = 0;
    	    
    	    while ((c = buffer.read(buf)) != -1 && ! cancel) 
    	    {
    	    	numBytes += c;
    	    	bos.write(buf, 0, c);
    	    	
    	    	if ( listener != null)
    	    		listener.onDownload(numBytes);
    	    } 
    	    
    	    // send totals
    	    if( listener != null)
    	    	listener.onDownloadComplete(numBytes, url, cancel);
    	    
    	    responseMessage = uc.getResponseMessage();
		} 
    	catch (Exception e) {
			//e.printStackTrace();
			responseMessage = e.getMessage();
			throw new IOException(e.getMessage());
		}
		finally {
			bos.close();
    	    headers 	= ( uc != null ) ? uc.getHeaderFields() : null;
    	    status 		= ( uc != null ) ? uc.getResponseCode() : null;
			uc.disconnect();
		}
	}

    public void cancel() {
		this.cancel = true;
	}
    
    /**
     * Simple HTTP get request
     * @return HTTP response text
     * @throws MalformedURLException
     * @throws IOException
     */
    public String doGet () throws MalformedURLException, IOException
	{
	    uc = (HttpURLConnection)url.openConnection();

	    uc.setRequestProperty("User-Agent", USER_AGENT);
	    
	    uc.setReadTimeout(READ_TIMEOUT);
	    uc.setConnectTimeout(CONNECT_TIMEOUT);

	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
		    BufferedInputStream buffer  = new BufferedInputStream(uc.getInputStream(), 12288);   
		    
		    
		    int c;
		    while ((c = buffer.read()) != -1) 
		    {
		      bos.write(c);
		    } 
		    bos.close();
			
		} 
	    catch (IOException e) {
		}
		finally {
			headers = uc.getHeaderFields();
			status = uc.getResponseCode();
		}
	    return bos.toString();
	}
    
    void dumpHeaders() {
    	Set<String> keys = uc.getHeaderFields().keySet();
    	for (String key : keys) {
			Log.d(TAG, key + "=" + uc.getHeaderFields().get(key).toString());
		}
    }
    
    public int getResponseCode() {
    	return status;
    }
    
    public InputStream getInputStream() throws IOException {
	    URLConnection uc = url.openConnection();
	    return uc.getInputStream();
    }
    
	public Map<String, List<String>> getHeaders(){
		return headers;
	}
	
	public String getResponseMessage () throws IOException {
		return responseMessage;
	}
	
	public String getContentType() {
		return uc.getContentType();
	}
	

	public void close () {
		uc.disconnect();
		uc = null;
		headers = null;
		url = null;
	}
	
}
