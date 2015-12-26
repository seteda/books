package com.badlogic.androidgames.glbasics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class ColoredTriangleTest extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new ColoredTriangleScreen(this);
    }

	class ColoredTriangleScreen extends Screen {
	    final int VERTEX_SIZE = (2 + 4) * 4;
	    GLGraphics glGraphics;
	    FloatBuffer vertices;        
	
	    public ColoredTriangleScreen(Game game) {
	        super(game);
	        glGraphics = ((GLGame) game).getGLGraphics();
	
	        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
	        byteBuffer.order(ByteOrder.nativeOrder());
	        vertices = byteBuffer.asFloatBuffer();
	        vertices.put( new float[] {   0.0f,   0.0f, 1, 0, 0, 1,
	                                    319.0f,   0.0f, 0, 1, 0, 1,
	                                    160.0f, 479.0f, 0, 0, 1, 1});
	        vertices.flip();
	    }
	
	    @Override
	    public void present(float deltaTime) {
	        GL10 gl = glGraphics.getGL();
	        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        gl.glMatrixMode(GL10.GL_PROJECTION);
	        gl.glLoadIdentity();
	        gl.glOrthof(0, 320, 0, 480, 1, -1);
	        
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	        
	        vertices.position(0);
	        gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
	        vertices.position(2);            
	        gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
	        
	        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	    }

        @Override
        public void update(float deltaTime) {
            game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
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
