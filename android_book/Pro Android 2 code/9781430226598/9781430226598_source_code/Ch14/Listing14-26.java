private void doView(final Intent queryIntent)
{
   Uri uri = queryIntent.getData();
   String action = queryIntent.getAction();
   Intent i = new Intent(action);
   i.setData(uri);
   startActivity(i);
   this.finish();
}
