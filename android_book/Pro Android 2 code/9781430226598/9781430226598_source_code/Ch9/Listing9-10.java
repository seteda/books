import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelephonyDemo extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Button sendBtn = (Button)findViewById(R.id.sendSmsBtn);

        sendBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText addrTxt = 
                      (EditText)TelephonyDemo.this.findViewById(R.id.addrEditText);

                EditText msgTxt = 
                      (EditText)TelephonyDemo.this.findViewById(R.id.msgEditText);

                try {
                    sendSmsMessage(
                        addrTxt.getText().toString(),msgTxt.getText().toString());
                    Toast.makeText(TelephonyDemo.this, "SMS Sent", 
                	        Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(TelephonyDemo.this, "Failed to send SMS", 
                	        Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }});
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

