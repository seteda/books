@Override
public boolean onOptionsItemSelected(MenuItem item)
{
   if (item.getItemId() == R.id.mid_OpenGL10_SimpleTriangle)
   {
      //..Direct this menu item locally to the main activity
      //..which you may be using for other purposes
      return true;
   }
   
   //These menu items, direct them to the multiview
   this.invokeMultiView(item.getItemId());
   return true;
}
   
//here is invoking the multiview through a loaded intent
//carrying the menu id
//mid: menu id
private void invokeMultiView(int mid)
{
   Intent intent = new Intent(this,MultiViewTestHarnessActivity.class);
   intent.putExtra("com.ai.menuid", mid);
   startActivity(intent);
}
