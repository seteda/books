private static final UriMatcher sUriMatcher;
//define ids for each uri type
private static final int INCOMING_BOOK_COLLECTION_URI_INDICATOR = 1;
private static final int INCOMING_SINGLE_BOOK_URI_INDICATOR = 2;

static 
{
   sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

   //Register pattern for the books
   sUriMatcher.addURI(BookProviderMetaData.AUTHORITY
         , "books"
         , INCOMING_BOOK_COLLECTION_URI_INDICATOR);

   //Register pattern for a single book
   sUriMatcher.addURI(BookProviderMetaData.AUTHORITY
         , "books/#",
         INCOMING_SINGLE_BOOK_URI_INDICATOR);
}


