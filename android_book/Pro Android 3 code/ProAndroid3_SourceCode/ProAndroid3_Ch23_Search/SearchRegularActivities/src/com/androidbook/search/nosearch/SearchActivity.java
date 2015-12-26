package com.androidbook.search.nosearch;

import com.androidbook.search.nosearch.R;

import android.app.Activity;
import android.os.Bundle;

//filename: NoSearchActivity.java
public class SearchActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        return;
    }
	@Override
	public boolean onSearchRequested() 
	{
		return false;
	}
}
