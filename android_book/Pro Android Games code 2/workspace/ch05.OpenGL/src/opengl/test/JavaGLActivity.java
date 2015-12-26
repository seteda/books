package opengl.test;

import opengl.scenes.GLSurfaceView;
import opengl.scenes.cubes.CubeRenderer;
import android.app.Activity;
import android.os.Bundle;

public class JavaGLActivity extends Activity 
{
	private GLSurfaceView mGLSurfaceView;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        mGLSurfaceView = new GLSurfaceView(this);
        
        try {
        	mGLSurfaceView.setRenderer(new CubeRenderer(true, false));
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