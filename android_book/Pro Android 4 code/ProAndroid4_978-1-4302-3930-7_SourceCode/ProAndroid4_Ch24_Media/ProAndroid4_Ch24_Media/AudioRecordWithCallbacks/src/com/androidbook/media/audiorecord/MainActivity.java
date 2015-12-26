package com.androidbook.media.audiorecord;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static final String TAG = "AudioRecordCallbacks";
	private int mAudioBufferSize;
	private int mAudioBufferSampleSize;
	private AudioRecord mAudioRecord;
	private boolean inRecordMode = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initAudioRecord();
	}


	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "Resuming...");
		inRecordMode = true;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				getSamples();
			}
		});
		t.start();
	}

	protected void onPause() {
		Log.v(TAG, "Pausing...");
		inRecordMode = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "Destroying...");
		if(mAudioRecord != null) {
			mAudioRecord.release();
            Log.v(TAG, "Released AudioRecord");			
		}
		super.onDestroy();
	}

    public OnRecordPositionUpdateListener mListener = new OnRecordPositionUpdateListener() {

		public void onPeriodicNotification(AudioRecord recorder) {
			Log.v(TAG, "in onPeriodicNotification");
	    }

        public void onMarkerReached(AudioRecord recorder) {
		    Log.v(TAG, "in onMarkerReached");
		    inRecordMode = false;
        }
    };

	private void initAudioRecord() {
		try {
			int sampleRate = 8000;
			int channelConfig = AudioFormat.CHANNEL_IN_MONO;
			int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
			mAudioBufferSize = 2 * AudioRecord.getMinBufferSize(sampleRate,
					channelConfig, audioFormat);
			mAudioBufferSampleSize = mAudioBufferSize / 2;
			mAudioRecord = new AudioRecord(
					MediaRecorder.AudioSource.MIC,
					sampleRate,
					channelConfig,
					audioFormat,
					mAudioBufferSize);
			Log.v(TAG, "Setup of AudioRecord okay. Buffer size = " + mAudioBufferSize);
			Log.v(TAG, "   Sample buffer size = " + mAudioBufferSampleSize);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		mAudioRecord.setNotificationMarkerPosition(10000);
		mAudioRecord.setPositionNotificationPeriod(1000);
		mAudioRecord.setRecordPositionUpdateListener(mListener);
		
		int audioRecordState = mAudioRecord.getState();
		if(audioRecordState != AudioRecord.STATE_INITIALIZED) {
			Log.e(TAG, "AudioRecord is not properly initialized");
			finish();
		}
		else {
			Log.v(TAG, "AudioRecord is initialized");
		}
	}
		
	private void getSamples() {
		if(mAudioRecord == null) return;

		short[] audioBuffer = new short[mAudioBufferSampleSize];

		mAudioRecord.startRecording();

		int audioRecordingState = mAudioRecord.getRecordingState();
		if(audioRecordingState != AudioRecord.RECORDSTATE_RECORDING) {
			Log.e(TAG, "AudioRecord is not recording");
			finish();
		}
		else {
			Log.v(TAG, "AudioRecord has started recording...");
		}

		while(inRecordMode) {
		    int samplesRead = mAudioRecord.read(audioBuffer, 0, mAudioBufferSampleSize);
		    Log.v(TAG, "Got samples: " + samplesRead);
		    Log.v(TAG, "First few sample values: " +
		    		audioBuffer[0] + ", " +
		    		audioBuffer[1] + ", " +
		    		audioBuffer[2] + ", " +
		    		audioBuffer[3] + ", " +
		    		audioBuffer[4] + ", " +
		    		audioBuffer[5] + ", " +
		    		audioBuffer[6] + ", " +
		    		audioBuffer[7] + ", " +
		    		audioBuffer[8] + ", " +
		    		audioBuffer[9] + ", "
		    		);
		}
		
		mAudioRecord.stop();
		Log.v(TAG, "AudioRecord has stopped recording");
	}
}