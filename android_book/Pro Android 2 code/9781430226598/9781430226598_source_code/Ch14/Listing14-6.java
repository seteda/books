//filename: NoSearchActivity.java
public class NoSearchActivity extends Activity
{
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.no_search_activity);
      return;
   }
   @Override
   public boolean onSearchRequested()
   {
      return false;
   }
}
