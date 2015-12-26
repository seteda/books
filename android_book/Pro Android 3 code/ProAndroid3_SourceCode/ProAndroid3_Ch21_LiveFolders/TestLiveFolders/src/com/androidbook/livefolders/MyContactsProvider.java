package com.androidbook.livefolders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.LiveFolders;
import android.util.Log;

public class MyContactsProvider extends ContentProvider {

    public static final String AUTHORITY = "com.androidbook.livefolders.contacts";
   
    //Uri that goes as input to the livefolder creation
    public static final Uri CONTACTS_URI = Uri.parse("content://" +
            AUTHORITY + "/contacts"   );

    //To distinguish this URI
    private static final int TYPE_MY_URI = 0;
    private static final UriMatcher URI_MATCHER;
    static{
      URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
      URI_MATCHER.addURI(AUTHORITY, "contacts", TYPE_MY_URI);
    }
    
    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int bulkInsert(Uri arg0, ContentValues[] values) {
      return 0; //nothing to insert
    }

    //Set of columns needed by a LiveFolder
    //This is the live folder contract
    private static final String[] CURSOR_COLUMNS = new String[]{
      BaseColumns._ID, 
      LiveFolders.NAME, 
      LiveFolders.DESCRIPTION, 
      LiveFolders.INTENT, 
      LiveFolders.ICON_PACKAGE, 
      LiveFolders.ICON_RESOURCE
    };
    
    //In case there are no rows
    //use this stand in as an error message
    //Notice it has the same set of columns of a live folder
    private static final String[] CURSOR_ERROR_COLUMNS = new String[]{
      BaseColumns._ID, 
      LiveFolders.NAME, 
      LiveFolders.DESCRIPTION
    };
    
    
    //The error message row
    private static final Object[] ERROR_MESSAGE_ROW = 
         new Object[]
         {
          -1, //id
          "No contacts found", //name 
          "Check your contacts database" //description
         };
    
    //The error cursor to use
    private static MatrixCursor sErrorCursor = new MatrixCursor(CURSOR_ERROR_COLUMNS);
    static {
      sErrorCursor.addRow(ERROR_MESSAGE_ROW);
    }

    //Columns to be retrieved from the contacts database
    private static final String[] CONTACTS_COLUMN_NAMES = new String[]{
      ContactsContract.Contacts._ID, 
      ContactsContract.Contacts.DISPLAY_NAME, 
      ContactsContract.Contacts.TIMES_CONTACTED, 
      ContactsContract.Contacts.STARRED
    };
    
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) 
    {
       //Figure out the uri and return error if not matching
      int type = URI_MATCHER.match(uri);
      if(type == UriMatcher.NO_MATCH)
      {
        return sErrorCursor;
      }

      Log.i("ss", "query called");
      
      try 
      {
       MatrixCursor mc = loadNewData(this);
        mc.setNotificationUri(getContext().getContentResolver(),  
              Uri.parse("content://contacts/people/"));
        MyCursor wmc = new MyCursor(mc,this);
        return wmc;
      } 
      catch (Throwable e) 
      {
        return sErrorCursor;
      }
    }
    
    public static MatrixCursor loadNewData(ContentProvider cp)
    {
       MatrixCursor mc = new MatrixCursor(CURSOR_COLUMNS);
        Cursor allContacts = null;
        try
        {
           allContacts = cp.getContext().getContentResolver().query(
              ContactsContract.Contacts.CONTENT_URI, 
              CONTACTS_COLUMN_NAMES, 
              null, //row filter 
              null, 
              ContactsContract.Contacts.DISPLAY_NAME); //order by
           
           while(allContacts.moveToNext())
           {
             String timesContacted = "Times contacted: "+allContacts.getInt(2);
             
             Object[] rowObject = new Object[]
             {
                 allContacts.getLong(0),    //id
                 allContacts.getString(1),    //name
                 timesContacted,          //description
                 Uri.parse("content://contacts/people/"+allContacts.getLong(0)), //intent
                 cp.getContext().getPackageName(), //package
                 R.drawable.icon   //icon
             };
             mc.addRow(rowObject);
           }
          return mc;
        }
        finally
        {
           allContacts.close();
        }
        
    }
    
    
    @Override
    public String getType(Uri uri) 
    {
      //indicates the MIME type for a given URI
      //targeted for this wrapper provider
      //This usually looks like 
      // "vnd.android.cursor.dir/vnd.google.note"
      return ContactsContract.Contacts.CONTENT_TYPE;
    }

    public Uri insert(Uri uri, ContentValues initialValues) {
      throw new UnsupportedOperationException(
            "no insert as this is just a wrapper");
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException(
        "no delete as this is just a wrapper");
    }

    public int update(Uri uri, ContentValues values, 
            String selection, String[] selectionArgs) 
   {
        throw new UnsupportedOperationException(
        "no update as this is just a wrapper");
    }
}

