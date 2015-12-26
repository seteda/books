package com.androidbook.email.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent emailIntent=new Intent(Intent.ACTION_SEND);

        String subject = "Hi!";
        String body = "hello from android....";

        String[] extra = new String[]{"aaa@bbb.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, extra);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        emailIntent.setType("message/rfc822");

        startActivity(emailIntent);
    }
}