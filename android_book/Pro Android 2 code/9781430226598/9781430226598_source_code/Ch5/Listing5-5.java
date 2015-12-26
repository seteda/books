//Step 1
public class MyResponse implements OnMenuClickListener
{
   //some local variable to work on
   //...
   //Some constructors
   @override
   boolean onMenuItemClick(MenuItem item)
   {
      //do your thing
      return true;
   }
}

//Step 2
MyResponse myResponse = new MyResponse(...);
menuItem.setOnMenuItemClickListener(myResponse);
...


