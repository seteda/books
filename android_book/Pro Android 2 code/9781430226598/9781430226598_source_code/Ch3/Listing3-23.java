Activity someActivity;
//..initialize someActivity
String noteUri = "content://com.google.provider.NotePad/notes/23";
Cursor managedCursor = someActivity.managedQuery( noteUri,
    projection, //Which columns to return.
    null, // WHERE clause
    null); // Order-by clause.


