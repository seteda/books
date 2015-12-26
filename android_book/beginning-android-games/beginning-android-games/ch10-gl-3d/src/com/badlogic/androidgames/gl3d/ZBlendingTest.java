package com.badlogic.androidgames.gl3d;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Vertices3;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLScreen;

public class ZBlendingTest extends GLGame {

    @Override
    public Screen getStartScreen() {
        return new ZBlendingScreen(this);
    }
    
    class ZBlendingScreen extends GLScreen {
        Vertices3 vertices;        
        
        public ZBlendingScreen(Game game) {
            super(game);
            
            vertices = new Vertices3(glGraphics, 6, 0, true, false);
            vertices.setVertices(new float[] { -0.5f, -0.5f, -3, 1, 0, 0, 0.5f,
                                                0.5f, -0.5f, -3, 1, 0, 0, 0.5f,
                                                0.0f,  0.5f, -3, 1, 0, 0, 0.5f,
                                                0.0f,  -0.5f, -5, 0, 1, 0, 1,
                                                1.0f,  -0.5f, -5, 0, 1, 0, 1,
                                                0.5f,  0.5f, -5, 0, 1, 0, 1}, 0, 7 * 6);
        }        
        
        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);            
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();           
            GLU.gluPerspective(gl, 67, 
                    glGraphics.getWidth() / (float)glGraphics.getHeight(), 
                    0.1f, 10f);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();    
            
            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            
            vertices.bind();
            vertices.draw(GL10.GL_TRIANGLES, 0, 3);
            vertices.draw(GL10.GL_TRIANGLES, 3, 3);
            vertices.unbind();
            
            gl.glDisable(GL10.GL_BLEND);           
            gl.glDisable(GL10.GL_DEPTH_TEST);
        }
        
        @Override
        public void update(float deltaTime) {
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {          
        }

        @Override
        public void dispose() {           
        }        
    }
}
