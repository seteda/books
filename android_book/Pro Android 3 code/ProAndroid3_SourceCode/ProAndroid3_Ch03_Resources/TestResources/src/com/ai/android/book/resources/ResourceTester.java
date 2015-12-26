package com.ai.android.book.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.Spanned;

public class ResourceTester extends BaseTester 
{
	private static String tag = "ResourceTester";
	ResourceTester(Context ctx, IReportBack target)
	{
		super(ctx, target);
	}
	public void testEnStrings()
	{
		
		String msg = "available in all en/us/root/port/en_port: test_en_us";
		reportString(msg, R.string.teststring_all);
		//The one in file _port is not picked up
		//The precedence is given to the en_us over _port and also over en_port

		msg = "available in only root/en and port: t1_enport";
		reportString(msg, R.string.t1_enport);
		//t1 is in en and port at a parallel level
		//The one in file _port is not picked up
		//The precedence is given to the file in en
		//if you have "t1" in values_en_port, then that will be picked up
		
		msg = "available in only root/en/port: t1_en_port";
		reportString(msg, R.string.t1_1_en_port);
		//Value from _en_port will be picked up
		//value from _port ignored
		//value from _en ignored
		
		msg = "available in only root: t2";
		reportString(msg, R.string.t2);

		msg = "available in only port/root: testport_port";
		reportString(msg, R.string.testport_port);
		//this shows that the _port directory is considered
		//even though there are other directories with a
		//higher precedence
	}
	public void testStringArray()
	{
		reportArray(R.array.test_array);
	}
	public void testPlurals()
	{
		reportPlural(R.plurals.test_plurals,0);
		reportPlural(R.plurals.test_plurals,1);
		reportPlural(R.plurals.test_plurals,2);
		reportPlural(R.plurals.test_plurals,3);
	}
	private void reportPlural(int plural_id, int amount)
	{
		Resources res = this.mContext.getResources();
		String s = res.getQuantityString(plural_id, amount);
		this.mReportTo.reportBack(tag, s);
	}
	private void reportArray(int arrayId)
	{
		Resources res = this.mContext.getResources();
		String strings[] = res.getStringArray(arrayId);
		for (String s: strings)
		{
			this.mReportTo.reportBack(tag, s);
		}
	}
	public void testColor()
	{
		Resources res = this.mContext.getResources();
		int mainBackGroundColor 
	     =  res.getColor(R.color.main_back_ground_color);
		reportString("mainBackGroundColor:" + mainBackGroundColor);
	}
	public void testDimensions()
	{
		Resources res = this.mContext.getResources();
		reportString("dimen:" + res.getDimension(R.dimen.medium_size));
		reportString("dimen:" + res.getDimension(R.dimen.mysize_in_dp));
		reportString("dimen:" + res.getDimension(R.dimen.mysize_in_pixels));
	}
	public void testStringVariations()
	{
		Context activity = this.mContext;
		//Read a simple string and set it in a text view
		String simpleString = activity.getString(R.string.simple_string);
		reportString(simpleString);

		//Read a quoted string and set it in a text view
		String quotedString = activity.getString(R.string.quoted_string);
		reportString(quotedString);

		//Read a double quoted string and set it in a text view
		String doubleQuotedString = activity.getString(R.string.double_quoted_string);
		reportString(doubleQuotedString);

		//Read a Java format string
		String javaFormatString = activity.getString(R.string.java_format_string);
		//Convert the formatted string by passing in arguments
		String substitutedString = String.format(javaFormatString, "Hello" , "Android");
		//set the output in a text view
		reportString(substitutedString);

		//Read an html string from the resource and set it in a text view
		String htmlTaggedString = activity.getString(R.string.tagged_string);
		//Convert it to a text span so that it can be set in a text view
		//android.text.Html class allows painting of "html" strings
		//This is strictly an Android class and does not support all html tags
		Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
		//Set it in a text view
		//this.getTextView().setText(textSpan);
		
	}
	private void report(int stringid)
	{
		this.mReportTo.reportBack(tag,this.mContext.getString(stringid));
	}
	private void reportString(String s)
	{
		this.mReportTo.reportBack(tag,s);
	}
	private void reportString(String s, int stringid)
	{
		this.mReportTo.reportBack(tag,s);
		report(stringid);
	}
	public void testXML()
	{
		try
		{
			String x = getEventsFromAnXMLFile(this.mContext);
			reportString(x); 
		}
		catch(Throwable t)
		{
			reportString("error reading xml file:" + t.getMessage());
		}
		
	}
	private String getEventsFromAnXMLFile(Context activity)
	throws XmlPullParserException, IOException
	{
	   StringBuffer sb = new StringBuffer();
	   Resources res = activity.getResources();
	   XmlResourceParser xpp = res.getXml(R.xml.test);
	   
	   xpp.next();
	   int eventType = xpp.getEventType();
	    while (eventType != XmlPullParser.END_DOCUMENT) 
	    {
	        if(eventType == XmlPullParser.START_DOCUMENT) 
	        {
	           sb.append("******Start document");
	        } 
	        else if(eventType == XmlPullParser.START_TAG) 
	        {
	           sb.append("\nStart tag "+xpp.getName());
	        } 
	        else if(eventType == XmlPullParser.END_TAG) 
	        {
	           sb.append("\nEnd tag "+xpp.getName());
	        } 
	        else if(eventType == XmlPullParser.TEXT) 
	        {
	           sb.append("\nText "+xpp.getText());
	        }
	        eventType = xpp.next();
	    }//eof-while
	    sb.append("\n******End document");
	    return sb.toString();
	}//eof-function
	
	public void testRawFile()
	{
		try
		{
			String s = getStringFromRawFile(this.mContext);
			this.reportString(s);
		}
		catch(Throwable t)
		{
			this.reportString("error:" + t.getMessage());
		}
	}
	private String getStringFromRawFile(Context activity)
	   throws IOException
	   {
	      Resources r = activity.getResources();
	      InputStream is = r.openRawResource(R.raw.test);
	      String myText = convertStreamToString(is);
	      is.close();
	      return myText;
	   }

	   private String convertStreamToString(InputStream is)
	   throws IOException
	   {
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      int i = is.read();
	      while (i != -1)
	      {
	         baos.write(i);
	         i = is.read();
	      }
	      return baos.toString();
	   }
	   public void testAssets()
	   {
		   try
		   {
			   String s = getStringFromAssetFile(this.mContext);
			   reportString(s);
		   }
		   catch(Throwable t)
		   {
			   reportString("error:" + t.getMessage());
		   }
	   }
	   
	 //Note: Exceptions are not shown in the code
	   String getStringFromAssetFile(Context activity)
	   throws IOException
	   {
	       AssetManager am = activity.getAssets();
	       InputStream is = am.open("test.txt");
	       String s = convertStreamToString(is);
	       is.close();
	       return s;
	   }
}
