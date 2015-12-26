@Override
public int update(Uri uri, ContentValues values, String where, String[] whereArgs)
{
   SQLiteDatabase db = mOpenHelper.getWritableDatabase();
   int count;
   switch (sUriMatcher.match(uri)) 
   {
      case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
         count = db.update(BookTableMetaData.TABLE_NAME,
         values, where, whereArgs);
         break;
      case INCOMING_SINGLE_BOOK_URI_INDICATOR:
         String rowId = uri.getPathSegments().get(1);
         count = db.update(BookTableMetaData.TABLE_NAME
                           , values
                           , BookTableMetaData._ID + "=" + rowId
                           + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "")
                           , whereArgs);
         break;
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
   }
   getContext().getContentResolver().notifyChange(uri, null);
   return count;
}


