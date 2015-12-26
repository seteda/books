@Override
public void onCreate(Bundle savedInstanceState) 
{
   super.onCreate(savedInstanceState);
   setContentView(R.layout.main);
   
   TextView tv = (TextView)this.findViewById(R.id.textViewId);
   registerForContextMenu(this.getTextView());
}


