AutoCompleteTextView actv = (AutoCompleteTextView) this.findViewById(R.id.ccactv);

ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
new String[] {"English", "Hebrew", "Hindi", "Spanish", "German", "Greek" });

actv.setAdapter(aa);

