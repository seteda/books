package doom.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.doom.DoomClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Owner
 *
 */
public class GameFileDownloader 
{
	public static final String TAG = "GameFileDownloader";
	
	private ProgressDialog mProgressDialog;
	
	/**
	 * Download game files
	 * 1. libdoom_jni.so.gz (gzipped) -> /data/data/org.doom/files
	 * 2. prboom.wad.gz (gzipped) -> /sdacard/doom
	 * 3. game wad : doom1.wad.gz, plutonia.wad or tnt.wad (gzziped) -> /sdcard/doom
	 * 4. sound track: soundtrack.zip (zipped) -> /sdcard/doom/soundtrack
	 * @param ctx
	 * @param wadIdx
	 */
	public void downloadGameFiles (final Context ctx, final int wadIdx, final boolean force) {
		new Thread(new Runnable() {
			public void run() 
			{
				Log.d(TAG, "Calling doDownload with wad: " + wadIdx + " force:" + force);
				boolean ok = doDownload(ctx, wadIdx, force);
				
				if (ok) {
					// ready to go!
					DialogTool.Toast(DoomClient.mHandler, ctx, "Ready. Tap Menu > Start");
				}
				
			}
		}).start();
		
		// Show progress
		mProgressDialog = new ProgressDialog(ctx);
        mProgressDialog.setMessage("Downloading files to the sdcard (required once)."
        		+ " This may take some time depending on your connection."
        		+ " Please wait and do not cancel!");
        
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
	}
	
	/*
	 * Fetch file
	 */
	private boolean doDownload(Context ctx, int wadIdx, boolean force) {
		boolean ok = false;
		
		try {
			// Lib: Required
			// downloadLib(ctx, force);
//			DoomTools.installJniLib(ctx, true);
			
			// prboom.wad (required)
			downloadFile(DoomTools.DOWNLOAD_BASE + DoomTools.REQUIRED_DOOM_WAD + ".gz"
					, new File(DoomTools.DOOM_FOLDER + DoomTools.REQUIRED_DOOM_WAD)
					, "gzip", null, force);
			
			// game wad (required)
			downloadFile(DoomTools.DOWNLOAD_BASE + DoomTools.DOOM_WADS[wadIdx] + ".gz"
					, new File(DoomTools.DOOM_FOLDER + DoomTools.DOOM_WADS[wadIdx])
					, "gzip", null, force);
			
			if ( DoomTools.hasSound()) {
				Log.d(TAG, "Sound folder " + DoomTools.getSoundFolder() + " already exists!");
				return true;
			}
			
			ok = true;
			
			// fetch soundtrack (optional)
			/*
			downloadFile(DoomTools.SOUND_BASE + "soundtrack.zip"
					, new File(DoomTools.DOOM_FOLDER + "soundtrack.zip")
					, "zip"		// this is a ZIP !
					, new File( DoomTools.DOOM_SOUND_FOLDER + File.separator) // dest folder
					, force);
			*/
			File folder = new File( DoomTools.DOOM_SOUND_FOLDER);
			
        	if ( ! folder.mkdirs() )
        		throw new IOException("Unable to create sound folder " + folder);
			
			DoomTools.installSoundTrack(ctx , folder );
			
		} catch (Exception e) {
			if ( ok) {
				DialogTool.PostMessageBox(ctx, "Soundtrack install failed: " + e.getMessage());
			}
			else{
				DialogTool.PostMessageBox(ctx, e.toString());
			}
		}
		finally {
			mProgressDialog.dismiss();
		}
		return ok;
	}
	
	/*
	 * Download JNI library
	 */
/*	
	public void downloadLib(Context ctx, boolean force) throws Exception 
	{
		// JNI lib /data/data/game.doom/files
		File lib = ctx.getFileStreamPath(DoomTools.DOOM_LIB);
		File parent = lib.getParentFile();
		
		if ( !parent.exists()) {
			// Gotta create parent /data/data/game.doom/files 
			if (  !parent.mkdirs() ) {
				// This should not happen!
				throw new Exception("Unable to create game folder:" + parent);
			}
		}
		
		downloadFile(DoomTools.DOWNLOAD_BASE + DoomTools.DOOM_LIB + ".gz"
				, ctx.getFileStreamPath(DoomTools.DOOM_LIB)
				, "gzip", null, force);
		
	}
*/
	
	/**
	 * Download a file
	 * @param url URL
	 * @param dest File destination
	 * @param type one of gzip, zip
	 * @param folder destination folder (File)
	 * @param force force download?
	 * @throws Exception
	 */
	public void downloadFile (String url, File dest, String type, File folder, boolean force)	throws Exception 
	{
		Log.d(TAG, "Download " + url + " -> " + dest + " type: " + type + " folder=" + folder + " force:" + force);

		if ( ! dest.exists() || force) 
		{
			if ( force ) 
				Log.d(TAG, "Forcing download!");
			
	    	WebDownload wd = new WebDownload(url);
	    	wd.doGet(new FileOutputStream(dest), type.equalsIgnoreCase("gzip"));
	    	
	    	// If ZIP file unzip into folder
	    	if ( type.equalsIgnoreCase("zip")) {
	        	if ( folder == null)
	        		throw new Exception("Invalid destination folder for ZIP " + dest);
	        	
	        	if ( ! folder.mkdirs() )
	        		throw new IOException("Unable to create local folder " + folder);
	        	
	        	DoomTools.unzip(new FileInputStream(dest), folder);
	        	
	        	// cleanup
	        	dest.delete();
	    	}
		}
		else {
			Log.d(TAG, "Not fetching " + dest + " already exists.");
		}
	}
}
