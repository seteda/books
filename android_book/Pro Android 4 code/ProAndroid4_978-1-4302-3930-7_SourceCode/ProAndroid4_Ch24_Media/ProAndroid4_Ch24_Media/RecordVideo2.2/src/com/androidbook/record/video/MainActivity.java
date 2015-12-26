package com.androidbook.record.video;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity implements
        SurfaceHolder.Callback, OnInfoListener, OnErrorListener {

	private static final String TAG = "RecordVideo";
	private MediaRecorder mRecorder = null;
	private Camera mCamera = null;
	private String OUTPUT_FILE;
	private VideoView mVideoView = null;
	private Button mStartBtn = null;
	private SurfaceHolder mHolder;
	private boolean isRecording = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mStartBtn = (Button) findViewById(R.id.beginBtn);

        mVideoView = (VideoView)this.findViewById(R.id.videoView);

        mHolder = mVideoView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    
    public void doClick(View view) {
    	switch(view.getId()) {
    	case R.id.beginBtn:
            beginRecording();
            break;
    	case R.id.stopBtn:
            stopRecording();
            break;
    	case R.id.playRecordingBtn:
            playRecording();
            break;
    	case R.id.stopPlayingRecordingBtn:
            stopPlayingRecording();
            break;
    	}
    }

    // Once we have a surface, we can start the previewing
    // Once we're previewing with Camera, we can setup the
    // MediaRecorder
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	Log.v(TAG, "surface created");
    	mStartBtn.setEnabled(true);
    	try {
			mCamera.setPreviewDisplay(holder);
	    	mCamera.startPreview();
	    	prepareForRecording(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
        Log.v(TAG, "Surface changed: width x Height = " + width + "x" + height);
	}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	Log.v(TAG, "surface destroyed");
    }

    private void beginRecording() {
    	mRecorder.setOnInfoListener(this);
    	mRecorder.setOnErrorListener(this);
        mRecorder.start();
        isRecording = true;
    }

    private void stopRecording() {
    	Log.v(TAG, "stop recording");
        if (mRecorder != null) {
        	mRecorder.setOnInfoListener(null);
        	mRecorder.setOnErrorListener(null);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        mCamera.release();
        mCamera = null;
        isRecording = false;
    }

	private void playRecording() {
        MediaController mc = new MediaController(this);
        mVideoView.setMediaController(mc);
        mVideoView.setVideoPath(OUTPUT_FILE);
        mVideoView.start();
    }

    private void stopPlayingRecording() {
    	mVideoView.stopPlayback();
    }

    @Override
    protected void onResume() {
    	Log.v(TAG, "resuming");
    	mCamera = Camera.open();
    	Parameters camParams = mCamera.getParameters();
    	//camera.setDisplayOrientation(90);
    	// We could set other parameters in camParams then:
    	// camera.setParameters(camParams);
    	
    	super.onResume();
    }

    @Override
    protected void onPause() {
    	Log.v(TAG, "pausing");
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if(mCamera != null) {
        	try {
				mCamera.reconnect();  // this also does a lock()
        	    mCamera.release();
        	    mCamera = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	super.onPause();
    }

    @Override
    protected void onDestroy() {
    	Log.v(TAG, "destroying");
        super.onDestroy();
    }

    private void prepareForRecording(SurfaceHolder holder) {
        if(mRecorder != null) {
            mRecorder.reset();
        }

        try {
        	mCamera.unlock();

        	mRecorder = new MediaRecorder();

            mRecorder.setCamera(mCamera);  // Must be done while MediaRecorder is idle
            mCamera.lock();

            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

            CamcorderProfile camProf = 
            	CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            // mRecorder.setProfile(camProf);

            String fileExtension = ".mp4";
            //if(camProf.fileFormat == MediaRecorder.OutputFormat.THREE_GPP)
            	
            	fileExtension = ".3gp";

            OUTPUT_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +
       	                     "/videooutput" + fileExtension;
            File outFile = new File(OUTPUT_FILE);
            if(outFile.exists()) {
                outFile.delete();
            }

            mRecorder.setOutputFile(OUTPUT_FILE);

            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            Log.i(TAG, "video output format is " + MediaRecorder.OutputFormat.THREE_GPP);
            
            mRecorder.setVideoFrameRate(camProf.videoFrameRate);
            Log.i(TAG, "Video Frame Rate: " + camProf.videoFrameRate);
            mRecorder.setVideoSize(camProf.videoFrameWidth,
                                   camProf.videoFrameHeight);
            Log.i(TAG, "Video size: " + camProf.videoFrameWidth +
            		"x" + camProf.videoFrameHeight);
            
            mRecorder.setVideoEncoder(camProf.videoCodec);
            Log.i(TAG, "Video Encoder: " + camProf.videoCodec);
            mRecorder.setAudioEncoder(camProf.audioCodec);
            Log.i(TAG, "Audio Encoder: " + camProf.audioCodec);

            mRecorder.setPreviewDisplay(holder.getSurface());
            
            mRecorder.setMaxDuration(30000); // limit to 30 seconds
                                            // Must implement onInfoListener
            mRecorder.prepare();
        }
        catch(Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

	@Override
	public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
		if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
			Log.i(TAG, "got a recording event");
			mCamera.stopPreview();
			Toast.makeText(this, "Recording limit has been reached. Stopping the recording",
					Toast.LENGTH_SHORT);
			isRecording = false;
		}
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		Log.e(TAG, "got a recording error");
		mCamera.stopPreview();
		Toast.makeText(this, "Recording error has occurred. Stopping the recording",
				Toast.LENGTH_SHORT);
		isRecording = false;
	}
}