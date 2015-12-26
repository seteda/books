String getStringFromRawFile(Activity activity)
{
   Resources r = activity.getResources();
   InputStream is = r.openRawResource(R.raw.test);
   String myText = convertStreamToString(is);
   is.close();
   return myText;
}

String convertStreamToString(InputStream is)
{
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   int i = is.read();
   while (i != -1)
   {
      baos.write(i);
      i = baos.read();
   }
   return baos.toString();
}


