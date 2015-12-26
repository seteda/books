package ch02.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jni.Natives;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity 
{
	private static final String LIB = "libch02.so";
	private static final String LIB_PATH  = "/data/data/ch02.project/files/" + LIB;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
        	// Install lib
        	System.out.println("Installing LIB: " +LIB);
        	
        	// Copy lib from assests folder to files folder: /data/data/PKG_NAME/files
        	writeToStream(getAssets().open(LIB)
        			, openFileOutput(LIB, 0));
			
        	// Load Lib
        	System.load(LIB_PATH);
        	
        	// Run it
			String[] argv = {"MyLib", "arg1", "arg2"};
			
			Natives.LibMain(argv);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Write to a stream
     * @param in
     * @param out
     * @throws IOException
     */
    public static void writeToStream(InputStream in , OutputStream out) 
    	throws IOException 
    {
		byte[] bytes = new byte[2048];
		
        for (int c = in.read(bytes); c != -1; c = in.read(bytes)) {
          out.write(bytes,0, c);
        }
        in.close();
        out.close();    		

    }

}