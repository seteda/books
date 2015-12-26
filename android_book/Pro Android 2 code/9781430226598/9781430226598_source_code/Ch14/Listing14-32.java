Bundle applicationData =
   queryIntent.getBundleExtra(SearchManager.APP_DATA);
   
if (applicationData != null)
{
   String s = applicationData.getString("string_key");
   long l = applicationData.getLong("long_key");
   float f = applicationData.getFloat("float_key");
}
