package com.androidbook.commoncontrols;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListViewActivity3 extends ListActivity
{
    private static final String TAG = "ListViewActivity3";
    private ListView lv = null;
    private Cursor cursor = null;
    private int idCol = -1;
    private int nameCol = -1;
    private int timesContactedCol = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        lv = getListView();
        
        cursor = managedQuery(Contacts.CONTENT_URI, 
                        null, null, null, Contacts.DISPLAY_NAME + " ASC");

        String[] cols = new String[]{Contacts.DISPLAY_NAME};
        idCol = cursor.getColumnIndex(Contacts._ID);
        nameCol = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
        timesContactedCol = cursor.getColumnIndex(Contacts.TIMES_CONTACTED);
        
        int[] views = new int[]{android.R.id.text1};
        
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
        		android.R.layout.simple_list_item_multiple_choice,
        		cursor, cols, views);
        
        this.setListAdapter(adapter);
        
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    public void doClick(View view) {
        int count=lv.getCount();
        SparseBooleanArray viewItems = lv.getCheckedItemPositions();
        for(int i=0; i<count; i++) {
        	if(viewItems.get(i)) {
        		// CursorWrapper cw = (CursorWrapper) lv.getItemAtPosition(i);
        		cursor.moveToPosition(i);
        		long id = cursor.getLong(idCol);
        		String name = cursor.getString(nameCol);
        		int timesContacted = cursor.getInt(timesContactedCol);
        		Log.v(TAG, name + " is checked. Times contacted = " + timesContacted +
        				". Position = " + i + ". Id = " + id);
        	}
        }
    }
}
