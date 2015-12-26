package com.badlogic.androidgames.glbasics;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GLSurfaceViewTest extends Activity {
	GLSurfaceView glView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(new SimpleRenderer());
		setContentView(glView);
	}

	@Override
	public void onResume() {
		super.onPause();
		glView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		glView.onPause();
	}

	static class SimpleRenderer implements Renderer {
		Random rand = new Random();

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			Log.d("GLSurfaceViewTest", "surface created");
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			Log.d("GLSurfaceViewTest", "surface changed: " + width + "x"
					+ height);
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClearColor(rand.nextFloat(), rand.nextFloat(),
					rand.nextFloat(), 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
	}
}
