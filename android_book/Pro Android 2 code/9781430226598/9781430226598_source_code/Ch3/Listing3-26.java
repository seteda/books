@Override
public String getType(Uri uri) 
{
   switch (sUriMatcher.match(uri)) 
   {
      case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
         return BookTableMetaData.CONTENT_TYPE;
         
      case INCOMING_SINGLE_BOOK_URI_INDICATOR:
         return BookTableMetaData.CONTENT_ITEM_TYPE;
         
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
   }
}


