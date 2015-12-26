if (cur.moveToFirst() == false)
{
   //no rows empty cursor
   return;
}

//The cursor is already pointing to the first row
//let's access a few columns
int nameColumnIndex = cur.getColumnIndex(People.NAME);
String name = cur.getString(nameColumnIndex);

//let's now see how we can loop through a cursor
while(cur.moveToNext())
{
   //cursor moved successfully
   //access fields
}


