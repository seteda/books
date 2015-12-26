package com.androidbook.commoncontrols;

import android.app.ListActivity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListViewActivity4 extends ListActivity
{
    private static final String TAG = "ListViewActivity4";
    private static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
	private SimpleCursorAdapter adapter = null;
    private ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        lv = getListView();

		String[] projection = new String[]{ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME};
        Cursor c = managedQuery(CONTACTS_URI,
                        projection, null, null, ContactsContract.Contacts.DISPLAY_NAME);

        String[] cols = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[]   views = new int[]   {android.R.id.text1};

        adapter = new SimpleCursorAdapter(this,
        		android.R.layout.simple_list_item_multiple_choice,
        		c, cols, views);

        this.setListAdapter(adapter);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    public void doClick(View view) {
    	if(!adapter.hasStableIds()) {
    		Log.v(TAG, "Data is not stable");
    		return;
    	}
        long[] viewItems = lv.getCheckedItemIds();
        for(int i=0; i<viewItems.length; i++) {
    		Uri selectedPerson = ContentUris.withAppendedId(
    				CONTACTS_URI, viewItems[i]);

    		Log.v(TAG, selectedPerson.toString() + " is checked.");
        }
    }
}
