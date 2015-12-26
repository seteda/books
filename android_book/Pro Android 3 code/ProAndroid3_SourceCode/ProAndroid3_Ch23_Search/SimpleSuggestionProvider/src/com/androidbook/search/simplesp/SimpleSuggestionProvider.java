package com.androidbook.search.simplesp;

import android.content.SearchRecentSuggestionsProvider;

public class SimpleSuggestionProvider 
extends SearchRecentSuggestionsProvider {
    
    final static String AUTHORITY = 
      "com.androidbook.search.simplesp.SimpleSuggestionProvider";
    final static int MODE = DATABASE_MODE_2LINES | DATABASE_MODE_QUERIES;
    //final static int MODE = DATABASE_MODE_QUERIES;
    
    public SimpleSuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}

