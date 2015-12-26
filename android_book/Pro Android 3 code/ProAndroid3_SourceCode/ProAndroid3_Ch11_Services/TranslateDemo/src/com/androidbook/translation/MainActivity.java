package com.androidbook.translation;
// This file is MainActivity.java
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    static final String TAG = "Translator";
    private EditText inputText = null;
    private TextView outputText = null;
    private Spinner fromLang = null;
    private Spinner toLang = null;
    private Button translateBtn = null;
    private String[] langShortNames = null;
    private Handler mHandler = new Handler();
    
    private ITranslate mTranslateService;

    private ServiceConnection mTranslateConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTranslateService = ITranslate.Stub.asInterface(service);
            if (mTranslateService != null) {
                translateBtn.setEnabled(true);
            } else {
                translateBtn.setEnabled(false);
                Log.e(TAG, "Unable to acquire TranslateService");
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            translateBtn.setEnabled(false);
            mTranslateService = null;
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.main);
        inputText = (EditText) findViewById(R.id.input);
        outputText = (EditText) findViewById(R.id.translation);
        fromLang = (Spinner) findViewById(R.id.from);
        toLang = (Spinner) findViewById(R.id.to);

        langShortNames = getResources().getStringArray(R.array.language_values);

        translateBtn = (Button) findViewById(R.id.translateBtn);
        translateBtn.setOnClickListener(this);
        
        ArrayAdapter<?> fromAdapter = ArrayAdapter.createFromResource(this,
        		R.array.languages, android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        fromLang.setAdapter(fromAdapter);
        fromLang.setSelection(1); // English
        
        ArrayAdapter<?> toAdapter = ArrayAdapter.createFromResource(this,
        		R.array.languages,android.R.layout.simple_spinner_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        toLang.setAdapter(toAdapter);
        toLang.setSelection(3); // German
        
        inputText.selectAll();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        bindService(intent, mTranslateConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mTranslateConn);
    }

    public void onClick(View v) {
        if (inputText.getText().length() > 0) {
            doTranslate();
        }
    }
    
    private void doTranslate() {
        mHandler.post(new Runnable() {
            public void run() {
                String result = "";
                try {
                	int fromPosition = fromLang.getSelectedItemPosition();
                	int toPosition = toLang.getSelectedItemPosition();
                    String input = inputText.getText().toString();
                    if(input.length() > 5000)
                    	input = input.substring(0,5000);
                    Log.v(TAG,"Translating from " + langShortNames[fromPosition] + " to " + langShortNames[toPosition]);
                    result = mTranslateService.translate(input, langShortNames[fromPosition], langShortNames[toPosition]);
                    if (result == null) {
                        throw new Exception("Failed to get a translation");
                    }
                    outputText.setText(result);
                    inputText.selectAll();
                } catch (Exception e) {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }
}
