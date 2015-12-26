// This file is MainActivity.java
package com.androidbook.tts.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnInitListener {
	private EditText words = null;
	private Button speakBtn = null;
    private static final int REQ_TTS_STATUS_CHECK = 0;
	private static final String TAG = "TTS Demo";
    private TextToSpeech mTts;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        words = (EditText)findViewById(R.id.wordsToSpeak);
        speakBtn = (Button)findViewById(R.id.speak);

        // Check to be sure that TTS exists and is okay to use
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
    }
    
    public void doSpeak(View view) {
		mTts.speak(words.getText().toString(), TextToSpeech.QUEUE_ADD, null);
	};
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_TTS_STATUS_CHECK) {
            switch (resultCode) {
            case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                // TTS is up and running
                mTts = new TextToSpeech(this, this);
                Log.v(TAG, "Pico is installed okay");
                break;
            case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
            case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
                // missing data, install it
                Log.v(TAG, "Need language stuff: " + resultCode);
                Intent installIntent = new Intent();
                installIntent.setAction(
                    TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                break;
            case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
            default:
                Log.e(TAG, "Got a failure. TTS apparently not available");
            }
        }
        else {
        	// Got something else
        }
    }

	public void onInit(int status) {
		// Now that the TTS engine is ready, we enable the button
		if( status == TextToSpeech.SUCCESS) {
			speakBtn.setEnabled(true);
			// mTts.setPitch(0.6f);
		}
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// if we're losing focus, stop talking
		if( mTts != null)
			mTts.stop();
	}

	@Override
    public void onDestroy()
	{
        super.onDestroy();
        mTts.shutdown();
    }
}