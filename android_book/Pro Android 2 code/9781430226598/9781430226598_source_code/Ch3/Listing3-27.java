@Override
public Cursor query(Uri uri, String[] projection, String selection
   , String[] selectionArgs, String sortOrder)
{
   SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
   switch (sUriMatcher.match(uri))
   {
      case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
         qb.setTables(BookTableMetaData.TABLE_NAME);
         qb.setProjectionMap(sBooksProjectionMap);
         break;
         
      case INCOMING_SINGLE_BOOK_URI_INDICATOR:
         qb.setTables(BookTableMetaData.TABLE_NAME);
         qb.setProjectionMap(sBooksProjectionMap);
         qb.appendWhere(BookTableMetaData._ID + "="
               + uri.getPathSegments().get(1));
         break;
         
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
   }
   
   // If no sort order is specified use the default
   String orderBy;
   if (TextUtils.isEmpty(sortOrder)) {
      orderBy = BookTableMetaData.DEFAULT_SORT_ORDER;
   } else {
      orderBy = sortOrder;
   }
   
   // Get the database and run the query
   SQLiteDatabase db =
   mOpenHelper.getReadableDatabase();
   
   Cursor c = qb.query(db, projection, selection,
      selectionArgs, null, null, orderBy);
   
   int i = c.getCount();
   // Tell the cursor what uri to watch,
   // so it knows when its source data changes
   c.setNotificationUri(getContext().getContentResolver(), uri);
   return c;
}


