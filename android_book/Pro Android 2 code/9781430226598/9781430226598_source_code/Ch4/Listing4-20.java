    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    
    
            setContentView(R.layout.gridview);
            GridView gv = (GridView)this.findViewById(R.id.dataGrid);
    
            Cursor c = getContentResolver().query(People.CONTENT_URI,
 null, null, null, null);
            startManagingCursor(c);
    
            String[] cols = new String[]{People.NAME};
            int[] names = new int[]{android.R.id.text1};
    
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1 ,c,cols,names);
    
            gv.setAdapter(adapter);

    }

