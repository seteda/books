package com.androidbook.record.image;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements MediaScannerConnectionClient {
    
	private static final String TAG = "CameraWithIntent";
	private File myImageFile = null;
	private Uri myPicture = null;
	private MediaScannerConnection conn = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void captureImage(View view)
    {
        myImageFile = new File(Environment.getExternalStorageDirectory()+"/imageCaptureIntent.jpg");
        myImageFile.delete();
        myPicture = Uri.fromFile(myImageFile);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, myPicture);

        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0 && resultCode==Activity.RESULT_OK)
        {
        	// Get the MediaScanner to catalog our new image file
        	startScan();
        }
    }
    
    private void startScan()
    {
        if(conn !=null)
        {
            conn.disconnect();
        }
        
        if(myImageFile.isFile()) {
            conn = new MediaScannerConnection(this, this);
            conn.connect();
        }
        else {
            Toast.makeText(this,
                "Image file does not exist?!?!",
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMediaScannerConnected() {
        conn.scanFile(myImageFile.getPath(), null);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        try {
            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
            else {
                Log.e(TAG, "That file is no good");
            }
        } finally {
            conn.disconnect();
            conn = null;
        } 
    }
}
