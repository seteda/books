public class ListDemoActivity extends ListActivity
{
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Cursor c = getContentResolver().query(People.CONTENT_URI,
 null, null, null, null);
        startManagingCursor(c);
        String[] cols = new String[]{People.NAME};
        int[] names = new int[]{R.id.row_tv};
        adapter = new SimpleCursorAdapter(this,R.layout.list_item,c,cols,names);
        this.setListAdapter(adapter);
    }
}

