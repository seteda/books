package com.androidbook.commoncontrols;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.widget.EditText;
import android.widget.TextView;

public class StylesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.styles);
        
        TextView tv =(TextView)this.findViewById(R.id.tv);
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setText("Please visit http://www.androidbook.com or email me at davemac327@gmail.com.");

        TextView tv3 =(TextView)this.findViewById(R.id.tv3);
        tv3.setText("Styling the content of a TextView dynamically",
                TextView.BufferType.SPANNABLE);
        Spannable spn = (Spannable) tv3.getText();
        spn.setSpan(new BackgroundColorSpan(Color.RED), 0, 7, 
                     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
                     0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        EditText et =(EditText)this.findViewById(R.id.et);
        et.setText("Styling the content of an EditText dynamically");
        Spannable spn2 = (Spannable) et.getText();
        spn2.setSpan(new BackgroundColorSpan(Color.RED), 0, 7, 
                     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spn2.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
                     0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}