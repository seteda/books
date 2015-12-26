package com.androidbook.tts.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnInitListener, OnUtteranceCompletedListener {
	private EditText words = null;
	private Button speakBtn = null;
    private static final int REQ_TTS_STATUS_CHECK = 0;
	private static final String TAG = "TTS Demo";
    private TextToSpeech mTts;

    private int uttCount = 0;
    private int lastUtterance = -1;
	private HashMap<String, String> params = new HashMap<String, String>();

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
		StringTokenizer st = new StringTokenizer(words.getText().toString(),",.");
		while (st.hasMoreTokens()) {
    		params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                    String.valueOf(uttCount++));
			mTts.speak(st.nextToken(), TextToSpeech.QUEUE_ADD, params);
	     }
	}

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
            ArrayList<String> available = data.getStringArrayListExtra("availableVoices");
            Log.v("languages count", String.valueOf(available.size()));
            Iterator<String> iter = available.iterator();
            while(iter.hasNext()) {
            	String lang = iter.next();
            	Locale locale = new Locale(lang);
                Log.v(TAG, "language: " + lang);
                Log.v(TAG, "language locale: " + locale.toString());
            }
            Locale[] locales = Locale.getAvailableLocales();
            Log.v(TAG, "available locales:");
            for(int i=0;i<locales.length;i++)
            	Log.v(TAG, "locale: " + locales[i].getDisplayName());
            
            Locale defloc = Locale.getDefault();
            Log.v(TAG, "current locale: " + defloc.getDisplayName());
        }
        else {
        	// Got something else
        }
    }

	public void onInit(int status) {
		// Now that the TTS engine is ready, we enable the button
		if( status == TextToSpeech.SUCCESS) {
			speakBtn.setEnabled(true);
			Locale loc = mTts.getLanguage();
            Log.v(TAG, "default engine: " + mTts.getDefaultEngine());
            Log.v(TAG, "current language/locale: " + loc.getDisplayName());
            Log.v(TAG, "current ISO3 language: " + loc.getISO3Language());
            Log.v(TAG, "current ISO3 country: " + loc.getISO3Country());
            Log.v(TAG, "current language: " + loc.getLanguage());
            Log.v(TAG, "current country: " + loc.getCountry());
            // mTts.setLanguage(new Locale("fr"));
			mTts.setOnUtteranceCompletedListener(this);
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

	public void onUtteranceCompleted(String uttId) {
		Log.v(TAG, "Got completed message for uttId: " + uttId);
		lastUtterance = Integer.parseInt(uttId);
	}
}