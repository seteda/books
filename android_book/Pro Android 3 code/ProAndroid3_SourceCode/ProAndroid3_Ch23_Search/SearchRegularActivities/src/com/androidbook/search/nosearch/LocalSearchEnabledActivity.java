package com.androidbook.search.nosearch;

import com.androidbook.search.nosearch.R;

import android.app.Activity;
import android.os.Bundle;

//filename: NoSearchActivity.java
public class LocalSearchEnabledActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_search_enabled_activity);
        return;
    }
}
