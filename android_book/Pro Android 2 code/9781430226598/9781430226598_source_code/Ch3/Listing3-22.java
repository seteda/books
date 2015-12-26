for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
{
  int nameColumn = cur.getColumnIndex(People.NAME);
  int phoneColumn = cur.getColumnIndex(People.NUMBER);
  String name = cur.getString(nameColumn);
  String phoneNumber = cur.getString(phoneColumn);
}


