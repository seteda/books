Button btn = (Button)this.findViewById(R.id.ccbtn1);
btn.setOnClickListener(new OnClickListener()
{
     public void onClick(View v)
     {
        Intent intent = getButtonIntent();
        intent.setAction("some intent data");
         setResult(RESULT_OK, intent);
         finish();
     }
});

