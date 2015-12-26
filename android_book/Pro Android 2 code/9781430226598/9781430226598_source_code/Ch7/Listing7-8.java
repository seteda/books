package com.client.cust.perm;
// This file is ClientCustPermMainActivity.java

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ClientCustPermMainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {

    
                Intent intent = new Intent();
    
                intent.setClassName("com.cust.perm","com.cust.perm.PrivActivity");
                startActivity(intent);
            }});

    }
}

