private String getEventsFromAnXMLFile(Activity activity)
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


