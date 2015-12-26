package com.badlogic.androidgames.gladvanced;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.AmbientLight;
import com.badlogic.androidgames.framework.gl.DirectionalLight;
import com.badlogic.androidgames.framework.gl.Material;
import com.badlogic.androidgames.framework.gl.Vertices3;
import com.badlogic.androidgames.framework.gl.PointLight;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLScreen;

public class LightTest extends GLGame {

    @Override
    public Screen getStartScreen() {
        return new LightScreen(this);
    }

	class LightScreen extends GLScreen {
		float angle;
		Vertices3 cube;
		Texture texture;
		AmbientLight ambientLight;
		PointLight pointLight;
		DirectionalLight directionalLight;
		Material material;
	
		public LightScreen(Game game) {
			super(game);
	
			cube = createCube();
			texture = new Texture(glGame, "crate.png");
			ambientLight = new AmbientLight();
			ambientLight.setColor(0, 0.2f, 0, 1);
			pointLight = new PointLight();
			pointLight.setDiffuse(1, 0, 0, 1);
			pointLight.setPosition(3, 3, 0);
			directionalLight = new DirectionalLight();
			directionalLight.setDiffuse(0, 0, 1, 1);
			directionalLight.setDirection(1, 0, 0);
			material = new Material();
		}
	
		@Override
		public void resume() {
			texture.reload();
		}
	    
	    private Vertices3 createCube() {
	        float[] vertices = { -0.5f, -0.5f, 0.5f, 0, 1, 0, 0, 1,
	                              0.5f, -0.5f, 0.5f, 1, 1, 0, 0, 1,
	                              0.5f,  0.5f, 0.5f, 1, 0, 0, 0, 1,
	                             -0.5f,  0.5f, 0.5f, 0, 0, 0, 0, 1,
	                             
	                              0.5f, -0.5f,  0.5f, 0, 1, 1, 0, 0,
	                              0.5f, -0.5f, -0.5f, 1, 1, 1, 0, 0,
	                              0.5f,  0.5f, -0.5f, 1, 0, 1, 0, 0,
	                              0.5f,  0.5f,  0.5f, 0, 0, 1, 0, 0,
	                              
	                              0.5f, -0.5f, -0.5f, 0, 1, 0, 0, -1,
	                             -0.5f, -0.5f, -0.5f, 1, 1, 0, 0, -1,
	                             -0.5f,  0.5f, -0.5f, 1, 0, 0, 0, -1,
	                              0.5f,  0.5f, -0.5f, 0, 0, 0, 0, -1,
	                             
	                             -0.5f, -0.5f, -0.5f, 0, 1, -1, 0, 0,
	                             -0.5f, -0.5f,  0.5f, 1, 1, -1, 0, 0,
	                             -0.5f,  0.5f,  0.5f, 1, 0, -1, 0, 0,
	                             -0.5f,  0.5f, -0.5f, 0, 0, -1, 0, 0,
	                             
	                             -0.5f,  0.5f,  0.5f, 0, 1, 0, 1, 0,
	                              0.5f,  0.5f,  0.5f, 1, 1, 0, 1, 0,
	                              0.5f,  0.5f, -0.5f, 1, 0, 0, 1, 0,
	                             -0.5f,  0.5f, -0.5f, 0, 0, 0, 1, 0,
	                             
	                             -0.5f, -0.5f, -0.5f, 0, 1, 0, -1, 0,
	                              0.5f, -0.5f, -0.5f, 1, 1, 0, -1, 0,
	                              0.5f, -0.5f,  0.5f, 1, 0, 0, -1, 0,
	                             -0.5f, -0.5f,  0.5f, 0, 0, 0, -1, 0 };
	        short[] indices = { 0, 1, 2, 2, 3, 0,
	                            4, 5, 6, 6, 7, 4,
	                            8, 9, 10, 10, 11, 8,
	                            12, 13, 14, 14, 15, 12,
	                            16, 17, 18, 18, 19, 16,
	                            20, 21, 22, 22, 23, 20,
	                            24, 25, 26, 26, 27, 24 };
	        Vertices3 cube = new Vertices3(glGraphics, vertices.length / 8, indices.length, false, true, true);
	        cube.setVertices(vertices, 0, vertices.length);
	        cube.setIndices(indices, 0, indices.length);
	        return cube;
	    }
	    
		@Override
		public void update(float deltaTime) {
			angle += deltaTime * 20;
		}
	
		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
	
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, 67, glGraphics.getWidth()
					/ (float) glGraphics.getHeight(), 0.1f, 10f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			GLU.gluLookAt(gl, 0, 1, 3, 0, 0, 0, 0, 1, 0);
	
			gl.glEnable(GL10.GL_LIGHTING);
	
			ambientLight.enable(gl);
			pointLight.enable(gl, GL10.GL_LIGHT0);
			directionalLight.enable(gl, GL10.GL_LIGHT1);
			material.enable(gl);
	
			gl.glEnable(GL10.GL_TEXTURE_2D);
			texture.bind();
				
			gl.glRotatef(angle, 0, 1, 0);
			cube.bind();
			cube.draw(GL10.GL_TRIANGLES, 0, 6 * 2 * 3);
			cube.unbind();
	
			pointLight.disable(gl);
			directionalLight.disable(gl);
	
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL10.GL_DEPTH_TEST);
		}
	
		@Override
		public void pause() {
		}
	
		@Override
		public void dispose() {
		}
	}
}