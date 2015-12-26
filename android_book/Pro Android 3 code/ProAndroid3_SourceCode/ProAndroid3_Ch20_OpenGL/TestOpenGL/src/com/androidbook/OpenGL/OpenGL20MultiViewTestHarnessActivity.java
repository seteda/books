package com.androidbook.OpenGL;

import com.androidbook.OpenGL.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

//filename: OpenGLTestHarnessActivity.java
public class OpenGL20MultiViewTestHarnessActivity extends Activity 
{
   final String tag="es20";
   private GLSurfaceView mTestHarness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (detectOpenGLES20())
        {
            mTestHarness = new GLSurfaceView(this);
            //mTestHarness.setEGLConfigChooser(false);
            mTestHarness.setEGLContextClientVersion(2);
        }
        else
        {
        	throw new RuntimeException("20 not supported");
        }
        
        Intent intent = getIntent();
        int mid = intent.getIntExtra("com.ai.menuid",0);
        if (mid == R.id.mid_es20_triangle)
        {
            mTestHarness.setRenderer(new ES20SimpleTriangleRenderer(this));
            //mTestHarness.setRenderer(new GLES20TestRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            //mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            setContentView(mTestHarness);
            return;
        }
        
        return;
    }
    private boolean detectOpenGLES20() {
        ActivityManager am =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
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
