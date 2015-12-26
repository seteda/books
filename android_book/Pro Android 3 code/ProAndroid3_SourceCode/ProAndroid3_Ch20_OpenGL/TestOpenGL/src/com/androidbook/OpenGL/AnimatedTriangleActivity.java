package com.androidbook.OpenGL;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

//filename: OpenGLTestHarnessActivity.java
public class AnimatedTriangleActivity extends Activity {
   private GLSurfaceView mTestHarness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTestHarness = new GLSurfaceView(this);
        mTestHarness.setEGLConfigChooser(false);
        mTestHarness.setRenderer(new AnimatedSimpleTriangleRenderer(this));
        //mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(mTestHarness);
    }
    @Override
    protected void onResume()    {
        super.onResume();
        mTestHarness.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mTestHarness.onPause();
    }
}
