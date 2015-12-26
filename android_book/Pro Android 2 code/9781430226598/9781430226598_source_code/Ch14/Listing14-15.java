//SimpleSuggestionProvider.java
public class SimpleSuggestionProvider
extends SearchRecentSuggestionsProvider {
   final static String AUTHORITY =
         "com.ai.android.search.simplesp.SimpleSuggestionProvider";
   final static int MODE = DATABASE_MODE_QUERIES;

   public SimpleSuggestionProvider() {
      super();
      setupSuggestions(AUTHORITY, MODE);
   }
}
