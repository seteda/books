public boolean onSearchRequested()
{
   Bundle applicationData = new Bundle();
   applicationData.putString("string_key","some string value");
   applicationData.putLong("long_key",290904);
   applicationData.putFloat("float_key",2.0f);
   startSearch(null, // Initial Search search query string
            false, //don't "select initial query"
            applicationData, // extra data
            false // don't force a global search
            );
   return true;
}
