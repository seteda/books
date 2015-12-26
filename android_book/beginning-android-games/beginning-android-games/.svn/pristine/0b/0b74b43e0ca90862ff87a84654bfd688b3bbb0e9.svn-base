package com.badlogic.androidgames.glbasics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class TexturedTriangleTest extends GLGame {
    @Override
    public Screen getStartScreen() {
        return new TexturedTriangleScreen(this);
    }

	class TexturedTriangleScreen extends Screen {
	    final int VERTEX_SIZE = (2 + 2) * 4;        
	    GLGraphics glGraphics;
	    FloatBuffer vertices;
	    int textureId;
	
	    public TexturedTriangleScreen(Game game) {
	        super(game);
	        glGraphics = ((GLGame) game).getGLGraphics();
	
	        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
	        byteBuffer.order(ByteOrder.nativeOrder());
	        vertices = byteBuffer.asFloatBuffer();
	        vertices.put( new float[] {    0.0f,   0.0f, 0.0f, 1.0f,
	                                     319.0f,   0.0f, 1.0f, 1.0f,
	                                     160.0f, 479.0f, 0.5f, 0.0f});
	        vertices.flip();            
	        textureId = loadTexture("bobrgb888.png");
	    }
	    
	    public int loadTexture(String fileName) {
	        try {
	            Bitmap bitmap = BitmapFactory.decodeStream(game.getFileIO().readAsset(fileName));
	            GL10 gl = glGraphics.getGL();
	            int textureIds[] = new int[1];
	            gl.glGenTextures(1, textureIds, 0);
	            int textureId = textureIds[0];
	            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);    
	            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);                
	            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	            bitmap.recycle();
	            return textureId;
	        } catch(IOException e) {
	            Log.d("TexturedTriangleTest", "couldn't load asset 'bobrgb888.png'!");
	            throw new RuntimeException("couldn't load asset '" + fileName + "'");
	        }
	    }
	
	    @Override
	    public void present(float deltaTime) {
	        GL10 gl = glGraphics.getGL();
	        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        gl.glMatrixMode(GL10.GL_PROJECTION);
	        gl.glLoadIdentity();
	        gl.glOrthof(0, 320, 0, 480, 1, -1);
	
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	        
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        
	        vertices.position(0);
	        gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
	        vertices.position(2);
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
	                    
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
