package com.androidbook.commoncontrols;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * I added this activity to this app to present a nice menu of the choices. For
 * a fancy way of determining what activities are within an app, and to build
 * a listview menu dynamically, check out the ApiDemos app from Google, and in
 * particular, take a look at ApiDemos.java. For my purposes here, I want to
 * keep it simple, so I create an ArrayAdapter from a resource array, and use
 * a corresponding array to list the appropriate activity name. The item click
 * handler below uses the activity name with the package name to construct an
 * intent to launch the appropriate activity.
 */
public class MainActivity extends ListActivity implements OnItemClickListener {
	private String activities[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

	    Resources res = getResources();
        activities = res.getStringArray(R.array.list_activities);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
            R.array.list_titles, android.R.layout.simple_list_item_1);

        this.setListAdapter(adapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View target, int position, long id) {
		try {
            Intent intent = new Intent(this, Class.forName(
				"com.androidbook.commoncontrols." + activities[position]));
            startActivity(intent);
		}
		catch(Exception e) {
			Log.e("CommonControls", "Problem with activity list for main menu");
		}
	}
}
