import java.util.ArrayList;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements OnGesturePerformedListener {
    private static final String TAG = "Gesture Revealer";
    GestureLibrary gestureLib = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        gestureLib = GestureLibraries.fromFile("/sdcard/gestures");
        if (!gestureLib.load()) {
            Toast.makeText(this, "Could not load /sdcard/gestures", Toast.LENGTH_SHORT).~CCC
                        show();
            finish();
        }
        
        // Let's take a look at the gesture library we have work with
        Log.v(TAG, "Library features:");
        Log.v(TAG, "  Orientation style: " + gestureLib.getOrientationStyle());
        Log.v(TAG, "  Sequence type: " + gestureLib.getSequenceType());
        for( String gestureName : gestureLib.getGestureEntries() ) {
            Log.v(TAG, "For gesture " + gestureName);
            int i = 1;
            for( Gesture gesture : gestureLib.getGestures(gestureName) ) {
                Log.v(TAG, "    " + i + ": ID: " + gesture.getID());
                Log.v(TAG, "    " + i + ": Strokes count: " + gesture.getStrokesCount());
                Log.v(TAG, "    " + i + ": Stroke length: " + gesture.getLength());
                i++;
            }
        }
        
        GestureOverlayView gestureView = ~CCC
                (GestureOverlayView) findViewById(R.id.gestureOverlay);
        gestureView.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView view, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLib.recognize(gesture);

        if (predictions.size() > 0) {
            Prediction prediction = (Prediction) predictions.get(0);
            if (prediction.score > 1.0) {
                Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
                for(int i=0;i<predictions.size();i++)
                    Log.v(TAG, "prediction " + predictions.get(i).name + 
                            " - score = " + predictions.get(i).score);
            }
        }
    }
}

