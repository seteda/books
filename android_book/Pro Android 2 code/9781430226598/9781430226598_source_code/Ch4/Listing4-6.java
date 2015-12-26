EditText et =(EditText)this.findViewById(R.id.cctvex5);
et.setText("Styling the content of an editText dynamically");
Spannable spn = et.getText();
spn.setSpan(new BackgroundColorSpan(Color.RED), 0, 7, 
Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
spn.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC)
, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

