package com.androidbook.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.content.Context;
import android.os.SystemClock;

//filename: SimpleTriangleRenderer.java
public class TexturedSquareRenderer extends AbstractSingleTexturedRenderer
{
   //Number of points or vertices we want to use
    private final static int VERTS = 4;
    
    //A raw native buffer to hold the point coordinates
    private FloatBuffer mFVertexBuffer;
    
    //A raw native buffer to hold the point coordinates
    private FloatBuffer mFTextureBuffer;
    
    //A raw native buffer to hold indices
    //allowing a reuse of points.
    private ShortBuffer mIndexBuffer;
    
    private int numOfIndecies = 0;
    
    private int sides = 4;
    
    public TexturedSquareRenderer(Context context) 
    {
    	super(context,com.androidbook.OpenGL.R.drawable.robot);
    	//EvenPolygon t = new EvenPolygon(0,0,0,1,3);
    	//EvenPolygon t = new EvenPolygon(0,0,0,1,4);
    	prepareBuffers(sides);
    }
    
    private void prepareBuffers(int sides)
    {
    	RegularPolygon t = new RegularPolygon(0,0,0,0.5f,sides);
    	//RegularPolygon t = new RegularPolygon(1,1,0,1,sides);
    	this.mFVertexBuffer = t.getVertexBuffer();
    	this.mFTextureBuffer = t.getTextureBuffer();
    	this.mIndexBuffer = t.getIndexBuffer();
    	this.numOfIndecies = t.getNumberOfIndecies();
        this.mFVertexBuffer.position(0);
        this.mIndexBuffer.position(0);
        this.mFTextureBuffer.position(0);
        
    }

   //overriden method
    protected void draw(GL10 gl)
    {
        prepareBuffers(sides);
        gl.glEnable(GL10.GL_TEXTURE_2D);
    	//EvenPolygon.test();
    	//gl.glColor4f(1.0f, 0, 0, 0.5f);
    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mFTextureBuffer);
    	gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndecies,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}
