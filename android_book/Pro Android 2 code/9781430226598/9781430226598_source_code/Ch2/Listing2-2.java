public class NotesList extends ListActivity {
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Notes.CONTENT_URI);
        }

        getListView().setOnCreateContextMenuListener(this);
        
        Cursor cursor = managedQuery(getIntent().getData(), 
PROJECTION, null, null,
                Notes.DEFAULT_SORT_ORDER);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
R.layout.noteslist_item, cursor, new String[] { Notes.TITLE }, 
new int[] { android.R.id.text1 });
        setListAdapter(adapter);
}
}

