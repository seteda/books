package com.androidbook.telephony;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TelephonyDemo extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }
	public void doSend(View view) {
        EditText addrTxt = 
                (EditText)findViewById(R.id.addrEditText);

        EditText msgTxt = 
                (EditText)findViewById(R.id.msgEditText);

        try {
            sendSmsMessage(
               	addrTxt.getText().toString(),
                msgTxt.getText().toString());
            Toast.makeText(this, "SMS Sent", 
                	Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", 
                	Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void sendSmsMessage(String address,String message)throws Exception
    {
        SmsManager smsMgr = SmsManager.getDefault();
        smsMgr.sendTextMessage(address, null, message, null, null);
    }
}
