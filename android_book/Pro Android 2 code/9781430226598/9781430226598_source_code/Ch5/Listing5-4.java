@Override
public boolean onOptionsItemSelected(MenuItem item)
{
   switch(item.getItemId()) 
   {
     ......
   }
   //for items handled
   return true;
   
   //for the rest
   ...return super.onOptionsItemSelected(item);
}


