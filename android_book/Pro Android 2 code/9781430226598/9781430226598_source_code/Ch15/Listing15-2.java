import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends Activity implements OnInitListener, OnUtteranceCompletedListener

    private int uttCount = 0;
    private int lastUtterance = -1;
    private HashMap<String, String> params = new HashMap<String, String>();

        @Override
        public void onClick(View view) {
            StringTokenizer st = new StringTokenizer(words.getText().toString(),",.");
            while (st.hasMoreTokens()) {
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                            String.valueOf(uttCount++));
                mTts.speak(st.nextToken(), TextToSpeech.QUEUE_ADD, params);;
           }

            @Override
            public void onInit(int status) {
                // Now that the TTS engine is ready, we enable the button
                if( status == TextToSpeech.SUCCESS) {
                    speakBtn.setEnabled(true);
                    mTts.setOnUtteranceCompletedListener(this);
                }
            }

            @Override
            public void onUtteranceCompleted(String uttId) {
                Log.v(TAG, "Got completed message for uttId: " + uttId);
                lastUtterance = Integer.parseInt(uttId);
            }

