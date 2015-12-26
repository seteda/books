package com.androidbook.media.scanner;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MediaScannerActivity extends Activity implements MediaScannerConnectionClient 
{
	private EditText editText = null;
    private String filename = null;
    private MediaScannerConnection conn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editText = (EditText)findViewById(R.id.fileName);
    }

    public void startScan(View view) {
        if(conn!=null) {
            conn.disconnect();
        }
        
        filename = editText.getText().toString();

        File fileCheck = new File(filename);
        if(fileCheck.isFile()) {
            conn = new MediaScannerConnection(this, this);
            conn.connect();
        }
        else {
            Toast.makeText(this,
                "That file does not exist",
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMediaScannerConnected() {
        conn.scanFile(filename, null);
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
                Log.e("MediaScannerDemo", "That file is no good");
            }
        } finally {
            conn.disconnect();
            conn = null;
        } 
    }
}
