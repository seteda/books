@Override
public Uri insert(Uri uri, ContentValues values) 
{
   // Validate the requested uri
   if (sUriMatcher.match(uri) != INCOMING_BOOK_COLLECTION_URI_INDICATOR) {
      throw new IllegalArgumentException("Unknown URI " + uri);
   }
   Long now = Long.valueOf(System.currentTimeMillis());
   
   //validate input fields
   // Make sure that the fields are all set
   if (values.containsKey(BookTableMetaData.CREATED_DATE) == false) {
      values.put(BookTableMetaData.CREATED_DATE, now);
   }
   
   if (values.containsKey(BookTableMetaData.MODIFIED_DATE) == false) {
      values.put(BookTableMetaData.MODIFIED_DATE, now);
   }
   if (values.containsKey(BookTableMetaData.BOOK_NAME) == false) {
      throw new SQLException(
         "Failed to insert row because Book Name is needed " + uri);
   }
   
   if (values.containsKey(BookTableMetaData.BOOK_ISBN) == false) {
      values.put(BookTableMetaData.BOOK_ISBN, "Unknown ISBN");
   }
   if (values.containsKey(BookTableMetaData.BOOK_AUTHOR) == false) {
      values.put(BookTableMetaData.BOOK_ISBN, "Unknown Author");
   }
   
   SQLiteDatabase db = mOpenHelper.getWritableDatabase();
   long rowId = db.insert(BookTableMetaData.TABLE_NAME
      , BookTableMetaData.BOOK_NAME, values);
      
   if (rowId > 0) {
      Uri insertedBookUri = ContentUris.withAppendedId(
      BookTableMetaData.CONTENT_URI, rowId);
      getContext().getContentResolver().notifyChange(insertedBookUri, null);
      return insertedBookUri;
   }
   throw new SQLException("Failed to insert row into " + uri);
}


