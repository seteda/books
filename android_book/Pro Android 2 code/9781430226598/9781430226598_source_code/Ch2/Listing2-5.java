 private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + NOTES_TABLE_NAME + " ("
                    + Notes._ID + " INTEGER PRIMARY KEY,"
                    + Notes.TITLE + " TEXT,"
                    + Notes.NOTE + " TEXT,"
                    + Notes.CREATED_DATE + " INTEGER,"
                    + Notes.MODIFIED_DATE + " INTEGER"
                    + ");");
        }

        //…
}

