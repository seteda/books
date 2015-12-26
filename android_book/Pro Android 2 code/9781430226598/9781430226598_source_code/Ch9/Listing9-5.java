// RecorderActivity.java
import java.io.File;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class RecorderActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private static final String OUTPUT_FILE= "/sdcard/recordoutput.3gpp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.record);

        Button startBtn = (Button) findViewById(R.id.bgnBtn);

        Button endBtn = (Button) findViewById(R.id.stpBtn);

        Button playRecordingBtn = (Button) findViewById(R.id.playRecordingBtn);

        Button stpPlayingRecordingBtn = 
(Button) findViewById(R.id.stpPlayingRecordingBtn);

        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    beginRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        endBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    stopRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        playRecordingBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    playRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stpPlayingRecordingBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    stopPlayingRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void beginRecording() throws Exception {
        killMediaRecorder();

        File outFile = new File(OUTPUT_FILE);

        if(outFile.exists())
        {
            outFile.delete();
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();

    }

    private void stopRecording() throws Exception {
        if (recorder != null) {
            recorder.stop();
        }
    }

    private void killMediaRecorder() {
        if (recorder != null) {
            recorder.release();
        }
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playRecording() throws Exception {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(OUTPUT_FILE);

        mediaPlayer.prepare();
        mediaPlayer.start();
    }
    private void stopPlayingRecording() throws Exception {
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        killMediaRecorder();
        killMediaPlayer();
    }

}

