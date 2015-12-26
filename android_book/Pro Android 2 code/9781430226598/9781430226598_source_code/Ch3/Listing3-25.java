public class BookProvider extends ContentProvider
{
   //Create a Projection Map for Columns
   //Projection maps are similar to "as" construct in an sql
   //statement whereby you can rename the
   //columns.
   private static HashMap<String, String> sBooksProjectionMap;
   static
   {
      sBooksProjectionMap = new HashMap<String, String>();
      sBooksProjectionMap.put(BookTableMetaData._ID, BookTableMetaData._ID);
      //name, isbn, author
      sBooksProjectionMap.put(BookTableMetaData.BOOK_NAME
      , BookTableMetaData.BOOK_NAME);
      sBooksProjectionMap.put(BookTableMetaData.BOOK_ISBN
      , BookTableMetaData.BOOK_ISBN);
      sBooksProjectionMap.put(BookTableMetaData.BOOK_AUTHOR
      , BookTableMetaData.BOOK_AUTHOR);
      //created date, modified date
      sBooksProjectionMap.put(BookTableMetaData.CREATED_DATE
      , BookTableMetaData.CREATED_DATE);
      sBooksProjectionMap.put(BookTableMetaData.MODIFIED_DATE
      , BookTableMetaData.MODIFIED_DATE);
   }
   //Provide a mechanism to identify all the incoming uri patterns.
   private static final UriMatcher sUriMatcher;
   private static final int INCOMING_BOOK_COLLECTION_URI_INDICATOR = 1;

   private static final int INCOMING_SINGLE_BOOK_URI_INDICATOR = 2;
   static {
      sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      sUriMatcher.addURI(BookProviderMetaData.AUTHORITY
      , "books"
      , INCOMING_BOOK_COLLECTION_URI_INDICATOR);
      sUriMatcher.addURI(BookProviderMetaData.AUTHORITY
      , "books/#",
      INCOMING_SINGLE_BOOK_URI_INDICATOR);
   }
   // Deal with OnCreate call back
   private DatabaseHelper mOpenHelper;

   @Override
   public boolean onCreate() {
      mOpenHelper = new DatabaseHelper(getContext());
      return true;
   }

   private static class DatabaseHelper extends SQLiteOpenHelper 
   {
      DatabaseHelper(Context context) 
      {
         super(context, BookProviderMetaData.DATABASE_NAME, null
         , BookProviderMetaData.DATABASE_VERSION);
      }
   
      //Create the database
      @Override
      public void onCreate(SQLiteDatabase db) 
      {
         db.execSQL("CREATE TABLE " + BookTableMetaData.TABLE_NAME + " ("
         + BookProviderMetaData.BookTableMetaData._ID
         + " INTEGER PRIMARY KEY,"
         + BookTableMetaData.BOOK_NAME + " TEXT,"
         + BookTableMetaData.BOOK_ISBN + " TEXT,"
         + BookTableMetaData.BOOK_AUTHOR + " TEXT,"
         + BookTableMetaData.CREATED_DATE + " INTEGER,"
         + BookTableMetaData.MODIFIED_DATE + " INTEGER"
         + ");");
      }
      //Deal with version changes
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
      {
         Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
         + newVersion + ", which will destroy all old data");
         db.execSQL("DROP TABLE IF EXISTS " + BookTableMetaData.TABLE_NAME);
         onCreate(db);
      }
   }
   ....other code


