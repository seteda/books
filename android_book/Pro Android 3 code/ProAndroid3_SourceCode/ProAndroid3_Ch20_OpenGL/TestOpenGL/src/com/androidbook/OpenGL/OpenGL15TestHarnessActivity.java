package com.androidbook.OpenGL;

import com.androidbook.OpenGL.R;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

//filename: OpenGLTestHarnessActivity.java
public class OpenGL15TestHarnessActivity extends Activity {
   private GLSurfaceView mTestHarness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sat","onCreate called");
        mTestHarness = new GLSurfaceView(this);
        mTestHarness.setEGLConfigChooser(false);
//        mTestHarness.setRenderer(new SimpleRectRenderer(this));
//        mTestHarness.setRenderer(new SimpleTriangleRenderer(this));
//      mTestHarness.setRenderer(new PolygonRenderer(this));
//        mTestHarness.setRenderer(new SquareRenderer(this));
        mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
//        mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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
