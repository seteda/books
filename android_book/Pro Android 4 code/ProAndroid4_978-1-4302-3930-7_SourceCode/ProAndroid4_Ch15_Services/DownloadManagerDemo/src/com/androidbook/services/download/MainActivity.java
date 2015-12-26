package com.androidbook.services.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    protected static final String TAG = "DownloadMgr";
	private DownloadManager dMgr;
	private TextView tv;
	private long downloadId;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView)findViewById(R.id.tv);
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	dMgr = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    public void doClick(View view) {
    	DownloadManager.Request dmReq = new DownloadManager.Request(
            Uri.parse(
                "http://dl-ssl.google.com/android/repository/" +
                "platform-tools_r01-linux.zip"));
    	dmReq.setTitle("Platform Tools");
    	dmReq.setDescription("Download for Linux");
    	dmReq.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(mReceiver, filter);

    	downloadId = dMgr.enqueue(dmReq);

    	tv.setText("Download started... (" + downloadId + ")");
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            long doneDownloadId =
            	extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
            tv.setText(tv.getText() + "\nDownload finished (" +
            	doneDownloadId + ")");
            if(downloadId == doneDownloadId)
                Log.v(TAG, "Our download has completed.");
        }
    };

    @Override
    protected void onPause() {
    	super.onPause();
    	unregisterReceiver(mReceiver);
    	dMgr = null;
    }
}