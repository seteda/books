public class LayoutAnimationActivity extends Activity
{
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.list_layout);
      setupListView();
   }

   private void setupListView()
   {
      String[] listItems = new String[] 
      {
         "Item 1", "Item 2", "Item 3",
         "Item 4", "Item 5", "Item 6",
      };

      ArrayAdapter listItemAdapter =
      new ArrayAdapter(this
         ,android.R.layout.simple_list_item_1
         ,listItems);

      ListView lv = (ListView)this.findViewById(R.id.list_view_id);
      lv.setAdapter(listItemAdapter);
   }
}


