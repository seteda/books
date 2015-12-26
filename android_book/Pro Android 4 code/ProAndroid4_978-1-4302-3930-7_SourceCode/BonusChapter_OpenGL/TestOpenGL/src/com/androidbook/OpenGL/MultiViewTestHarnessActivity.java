package com.androidbook.OpenGL;

import com.androidbook.OpenGL.R;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

//filename: MultiViewTestHarnessActivity.java
public class MultiViewTestHarnessActivity extends Activity {
   private GLSurfaceView mTestHarness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mTestHarness = new GLSurfaceView(this);
        mTestHarness.setEGLConfigChooser(false);
        
        Intent intent = getIntent();
        int mid = intent.getIntExtra("com.ai.menuid", R.id.mid_OpenGL_Current);
        if (mid == R.id.mid_OpenGL_SimpleTriangle)
        {
            mTestHarness.setRenderer(new SimpleTriangleRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_OpenGL_SimpleTriangle2)
        {
            mTestHarness.setRenderer(new SimpleTriangleRenderer2(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_OpenGL_AnimatedTriangle)
        {
            mTestHarness.setRenderer(new AnimatedSimpleTriangleRenderer(this));
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_rectangle)
        {
            mTestHarness.setRenderer(new SimpleRectangleRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_square_polygon)
        {
            mTestHarness.setRenderer(new SquareRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_polygon)
        {
            mTestHarness.setRenderer(new PolygonRenderer(this));
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_textured_square)
        {
            mTestHarness.setRenderer(new TexturedSquareRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_multiple_figures)
        {
            mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            setContentView(mTestHarness);
            return;
        }
        if (mid == R.id.mid_OpenGL_Current)
        {
            mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
            mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            setContentView(mTestHarness);
            return;
        }
        //otherwise do this
        mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
        mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(mTestHarness);
        return;
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
