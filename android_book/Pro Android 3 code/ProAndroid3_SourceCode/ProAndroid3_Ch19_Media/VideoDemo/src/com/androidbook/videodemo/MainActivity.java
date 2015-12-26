package com.androidbook.videodemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.main);

	    VideoView videoView = (VideoView)this.findViewById(R.id.videoView);
	    MediaController mc = new MediaController(this);
	    videoView.setMediaController(mc);
	    videoView.setVideoURI(Uri.parse(
	"http://www.androidbook.com/akc/filestorage/android/documentfiles/3389/movie.mp4"));
//      videoView.setVideoPath(
//   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +
//   "/movie.mp4");
//	    videoView.setVideoURI(Uri.parse("file://" +
//   Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +
//   "/movie.mp4"));
 
	    videoView.requestFocus();
	    videoView.start();
	}
}