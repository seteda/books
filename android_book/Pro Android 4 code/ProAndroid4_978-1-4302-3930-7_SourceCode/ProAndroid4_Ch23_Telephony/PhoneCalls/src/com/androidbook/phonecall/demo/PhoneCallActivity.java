// This file is PhoneCallActivity.java
package com.androidbook.phonecall.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PhoneCallActivity extends Activity {
    private TextView tv = null;
    private String logText = "";
    private TelephonyManager teleMgr = null;
    private MyPhoneStateListener myListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (TextView)findViewById(R.id.textView);

        teleMgr = 
                (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        myListener = new MyPhoneStateListener();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.d("PhoneCallDemo", "In onResume");
        teleMgr.listen(myListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.d("PhoneCallDemo", "In onPause");
        teleMgr.listen(myListener, PhoneStateListener.LISTEN_NONE);
    }
    
    public void doClick(View target) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:5551212"));
        startActivity(intent);
    }
    
    public class MyPhoneStateListener extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch(state)
            {
                case TelephonyManager.CALL_STATE_IDLE:
                    logText = "call state idle...incoming number is["+
                                incomingNumber+"]\n" + logText;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                	logText = "call state ringing...incoming number is["+
                                incomingNumber+"]\n" + logText;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                	logText = "call state Offhook...incoming number is["+
                                incomingNumber+"]\n" + logText;
                    break;
                default:
                	logText = "call state ["+state+"]incoming number is["+
                                incomingNumber+"]\n" + logText;
                    break;
            }
            tv.setText(logText);
        }
    }
}
