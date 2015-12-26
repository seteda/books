package com.ai.android.book.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ai.android.book.provider.BookProviderMetaData.BookTableMetaData;

public class BookProvider extends ContentProvider
{
	//Logging helper tag. No significance to providers.
    private static final String TAG = "BookProvider";

    //Projection maps are similar to "as" construct
    //in an sql statement where by you can rename the 
    //columns.
    private static HashMap<String, String> sBooksProjectionMap;
    static 
    {
    	sBooksProjectionMap = new HashMap<String, String>();
    	sBooksProjectionMap.put(BookTableMetaData._ID, 
    			                BookTableMetaData._ID);
    	
    	//name, isbn, author
    	sBooksProjectionMap.put(BookTableMetaData.BOOK_NAME, 
    			                BookTableMetaData.BOOK_NAME);
    	sBooksProjectionMap.put(BookTableMetaData.BOOK_ISBN, 
    			                BookTableMetaData.BOOK_ISBN);
    	sBooksProjectionMap.put(BookTableMetaData.BOOK_AUTHOR, 
    			                BookTableMetaData.BOOK_AUTHOR);
    	
    	//created date, modified date
    	sBooksProjectionMap.put(BookTableMetaData.CREATED_DATE, 
    			                BookTableMetaData.CREATED_DATE);
    	sBooksProjectionMap.put(BookTableMetaData.MODIFIED_DATE, 
    			                BookTableMetaData.MODIFIED_DATE);
    }

    //Provide a mechanism to identify
    //all the incoming uri patterns.
    private static final UriMatcher sUriMatcher;
    private static final int INCOMING_BOOK_COLLECTION_URI_INDICATOR = 1;
    private static final int INCOMING_SINGLE_BOOK_URI_INDICATOR = 2;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(BookProviderMetaData.AUTHORITY, "books", 
        		          INCOMING_BOOK_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(BookProviderMetaData.AUTHORITY, "books/#", 
        		          INCOMING_SINGLE_BOOK_URI_INDICATOR);

    }

    /**
     * This class helps open, create, and upgrade the database file.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, 
            	BookProviderMetaData.DATABASE_NAME, 
            	null, 
            	BookProviderMetaData.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	Log.d(TAG,"inner oncreate called");
            db.execSQL("CREATE TABLE " + BookTableMetaData.TABLE_NAME + " ("
                    + BookTableMetaData._ID + " INTEGER PRIMARY KEY,"
                    + BookTableMetaData.BOOK_NAME + " TEXT,"
                    + BookTableMetaData.BOOK_ISBN + " TEXT,"
                    + BookTableMetaData.BOOK_AUTHOR + " TEXT,"
                    + BookTableMetaData.CREATED_DATE + " INTEGER,"
                    + BookTableMetaData.MODIFIED_DATE + " INTEGER"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
        {
        	Log.d(TAG,"inner onupgrade called");
            Log.w(TAG, "Upgrading database from version " 
            		+ oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + 
            		 BookTableMetaData.TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() 
    {
    	Log.d(TAG,"main onCreate called");
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, 
    		String[] selectionArgs,  String sortOrder) 
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
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
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, 
        		   selectionArgs, null, null, orderBy);
        
        //example of getting a count
        int i = c.getCount();

        // Tell the cursor what uri to watch, 
        // so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
            return BookTableMetaData.CONTENT_TYPE;

        case INCOMING_SINGLE_BOOK_URI_INDICATOR:
            return BookTableMetaData.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) 
        		!= INCOMING_BOOK_COLLECTION_URI_INDICATOR) 
        {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        Long now = Long.valueOf(System.currentTimeMillis());

        // Make sure that the fields are all set
        if (values.containsKey(BookTableMetaData.CREATED_DATE) == false) 
        {
            values.put(BookTableMetaData.CREATED_DATE, now);
        }

        if (values.containsKey(BookTableMetaData.MODIFIED_DATE) == false) 
        {
            values.put(BookTableMetaData.MODIFIED_DATE, now);
        }

        if (values.containsKey(BookTableMetaData.BOOK_NAME) == false) 
        {
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
        long rowId = db.insert(BookTableMetaData.TABLE_NAME, 
        		BookTableMetaData.BOOK_NAME, values);
        if (rowId > 0) {
            Uri insertedBookUri = 
            	ContentUris.withAppendedId(
            			BookTableMetaData.CONTENT_URI, rowId);
            getContext()
               .getContentResolver()
                    .notifyChange(insertedBookUri, null);
            
            return insertedBookUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
            count = db.delete(BookTableMetaData.TABLE_NAME, 
            		where, whereArgs);
            break;

        case INCOMING_SINGLE_BOOK_URI_INDICATOR:
            String rowId = uri.getPathSegments().get(1);
            count = db.delete(BookTableMetaData.TABLE_NAME, 
            		BookTableMetaData._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), 
                    whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, 
    		String where, String[] whereArgs) 
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case INCOMING_BOOK_COLLECTION_URI_INDICATOR:
            count = db.update(BookTableMetaData.TABLE_NAME, 
            		values, where, whereArgs);
            break;

        case INCOMING_SINGLE_BOOK_URI_INDICATOR:
            String rowId = uri.getPathSegments().get(1);
            count = db.update(BookTableMetaData.TABLE_NAME, 
            		values, BookTableMetaData._ID + "=" + rowId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), 
                    whereArgs);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
