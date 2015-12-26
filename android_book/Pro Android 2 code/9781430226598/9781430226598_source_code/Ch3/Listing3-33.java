public class HelloWorld extends Activity
{
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState);
      TextView tv = new TextView(this);
      tv.setText("Hello, Android. Say hello");
      setContentView(tv);
      registerMenu(this.getTextView());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) 
   {
      super.onCreateOptionsMenu(menu);
      int base=Menu.FIRST; // value is 1
      MenuItem item1 = menu.add(base,base,base,"Test");
      return true;
   }
   @Override
   public boolean onOptionsItemSelected(MenuItem item) 
   {
      if (item.getItemId() == 1) 
      {
         IntentUtils.tryOneOfThese(this);
      }
      else 
      {
         return super.onOptionsItemSelected(item);
      }
      return true;
   }
}


