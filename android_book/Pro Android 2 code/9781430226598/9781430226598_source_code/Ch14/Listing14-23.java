//file: SearchActivity.java
public class SearchActivity extends Activity
{
   private final static String tag ="SearchActivity";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.d(tag,"I am being created");
      setContentView(R.layout.layout_test_search_activity);

      // get and process search query here
      final Intent queryIntent = getIntent();

      //query action
      final String queryAction = queryIntent.getAction();
      Log.d(tag,"Create Intent action:"+queryAction);

      final String queryString =
         queryIntent.getStringExtra(SearchManager.QUERY);
      Log.d(tag,"Create Intent query:"+queryString);

      if (Intent.ACTION_SEARCH.equals(queryAction))
      {
         this.doSearchQuery(queryIntent);
      }
      else if (Intent.ACTION_VIEW.equals(queryAction))
      {
         this.doView(queryIntent);
      }
      else {
         Log.d(tag,"Create intent NOT from search");
      }
      return;
   }

   @Override
   public void onNewIntent(final Intent newIntent)
   {
      super.onNewIntent(newIntent);
      Log.d(tag,"new intent calling me");

      // get and process search query here
      final Intent queryIntent = newIntent;

      //query action
      final String queryAction = queryIntent.getAction();
      Log.d(tag,"New Intent action:"+queryAction);

      final String queryString =
         queryIntent.getStringExtra(SearchManager.QUERY);
      Log.d(tag,"New Intent query:"+queryString);
      if (Intent.ACTION_SEARCH.equals(queryAction))
      {
         this.doSearchQuery(queryIntent);
      }
      else if (Intent.ACTION_VIEW.equals(queryAction))
      {
         this.doView(queryIntent);
      }
      else {
         Log.d(tag,"New intent NOT from search");
      }
      return;
   }

   private void doSearchQuery(final Intent queryIntent)
   {
      final String queryString =
         queryIntent.getStringExtra(SearchManager.QUERY);
      appendText("You are searching for:" + queryString);
   }

   private void appendText(String msg)
   {
      TextView tv = (TextView)this.findViewById(R.id.text1);
      tv.setText(tv.getText() + "\n" + msg);
   }
   private void doView(final Intent queryIntent)
   {
      Uri uri = queryIntent.getData();
      String action = queryIntent.getAction();
      Intent i = new Intent(action);
      i.setData(uri);
      startActivity(i);
      this.finish();
   }
}
