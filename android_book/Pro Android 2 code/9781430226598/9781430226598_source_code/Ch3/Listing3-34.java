public static void invokePick(Activity activity)
{
   Intent pickIntent = new Intent(Intent.ACTION_PICK);
   int requestCode = 1;
   pickIntent.setData(Uri.parse(
            "content://com.google.provider.NotePad/notes"));
   activity.startActivityForResult(pickIntent, requestCode);
}
protected void onActivityResult(int requestCode
                        ,int resultCode
                        ,Intent outputIntent)
{
   super.onActivityResult(requestCode, resultCode, outputIntent);
   parseResult(this, requestCode, resultCode, outputIntent);
}
public static void parseResult(Activity activity
                        , int requestCode
                        , int resultCode
                        , Intent outputIntent)
{
   if (requestCode != 1)
   {
      Log.d("Test", "Some one else called this. not us");
      return;
   }
   if (resultCode != Activity.RESULT_OK)
   {
      Log.d("Result code is not ok:" + resultCode);
      return;
   }
   Log.d("Test", "Result code is ok:" + resultCode);
   Uri selectedUri = outputIntent.getData();
   Log.d("Test", "The output uri:" + selectedUri.toString());

   //Proceed to display the note
   outputIntent.setAction(Intent.VIEW);
   startActivity(outputIntent);
}

