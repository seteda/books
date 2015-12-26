package com.androidbook.record.audio;

// RecorderActivity.java
import java.io.File;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

public class RecorderActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        OUTPUT_FILE = Environment.getExternalStorageDirectory() +
                        "/recordaudio.3gpp";
    }

    public void doClick(View view) {
    	switch(view.getId()) {
    	case R.id.beginBtn:
            try {
                beginRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
    	case R.id.stopBtn:
            try {
                stopRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
    	case R.id.playRecordingBtn:
            try {
                playRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
    	case R.id.stopPlayingRecordingBtn:
            try {
                stopPlayingRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
    	}
    }

    private void beginRecording() throws Exception {
        killMediaRecorder();

        File outFile = new File(OUTPUT_FILE);

        if(outFile.exists()) {
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
        if(mediaPlayer != null) {
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
