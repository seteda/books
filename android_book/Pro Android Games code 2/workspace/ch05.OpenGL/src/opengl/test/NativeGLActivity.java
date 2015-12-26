package opengl.test;

import opengl.scenes.GLSurfaceView;
import opengl.scenes.cubes.CubeRenderer;
import android.app.Activity;
import android.os.Bundle;

public class NativeGLActivity extends Activity 
{
	private GLSurfaceView mGLSurfaceView;
	
	{
		final String LIB_PATH = "/data/libgltest_jni.so";
		
		System.out.println("Loading JNI lib using abs path:" + LIB_PATH);
		System.load(LIB_PATH);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        mGLSurfaceView = new GLSurfaceView(this);
        
        try {
        	mGLSurfaceView.setRenderer(new CubeRenderer(true, true));
        	setContentView(mGLSurfaceView);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }
    
}