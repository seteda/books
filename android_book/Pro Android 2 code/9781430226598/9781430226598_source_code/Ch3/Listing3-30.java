@Override
public int delete(Uri uri, String where, String[] whereArgs) 
{
   SQLiteDatabase db = mOpenHelper.getWritableDatabase();
   int count;
   switch (sUriMatcher.match(uri)) 
   {
      case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
         count = db.delete(BookTableMetaData.TABLE_NAME, where, whereArgs);
         break;
      case INCOMING_SINGLE_BOOK_URI_INDICATOR:
         String rowId = uri.getPathSegments().get(1);
         count = db.delete(BookTableMetaData.TABLE_NAME
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


