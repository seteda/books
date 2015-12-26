public class NotePadProvider extends ContentProvider
{

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
 String[] selectionArgs,String sortOrder) {}

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {}

    @Override
    public int update(Uri uri, ContentValues values, String where, 
String[] whereArgs) {}

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {}

    @Override
    public String getType(Uri uri) {}

    @Override
    public boolean onCreate() {}


    private static class DatabaseHelper extends SQLiteOpenHelper {}

    @Override
        public void onCreate(SQLiteDatabase db) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, 
int oldVersion, int newVersion) {
            //...
        }
    }
}

