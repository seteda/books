package com.androidbook.commoncontrols;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class TextViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        
        TextView tv =(TextView)findViewById(R.id.tv);
        EditText et = (EditText)findViewById(R.id.et);
        AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.actv);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        new String[] {"English", "Hebrew", "Hindi", "Spanish", "German", "Greek" });

        actv.setAdapter(aa);
        
        MultiAutoCompleteTextView mactv = (MultiAutoCompleteTextView)findViewById(R.id.mactv);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                new String[] {"English", "Hebrew", "Hindi", "Spanish", "German", "Greek" });

        mactv.setAdapter(aa2);

        mactv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }
}