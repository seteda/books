package com.androidbook.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.os.SystemClock;

//filename: SimpleTriangleRenderer.java
public class PolygonRenderer extends AbstractRenderer
{
   //Number of points or vertices we want to use
    private final static int VERTS = 4;
    
    //A raw native buffer to hold the point coordinates
    private FloatBuffer mFVertexBuffer;
    
    //A raw native buffer to hold indices
    //allowing a reuse of points.
    private ShortBuffer mIndexBuffer;
    
    private int numOfIndecies = 0;
    
    private long prevtime = SystemClock.uptimeMillis();
    
    private int sides = 3;
    
    public PolygonRenderer(Context context) 
    {
    	//EvenPolygon t = new EvenPolygon(0,0,0,1,3);
    	//EvenPolygon t = new EvenPolygon(0,0,0,1,4);
    	prepareBuffers(sides);
    }
    
    private void prepareBuffers(int sides)
    {
    	RegularPolygon t = new RegularPolygon(0,0,0,1,sides);
    	//RegularPolygon t = new RegularPolygon(1,1,0,1,sides);
    	this.mFVertexBuffer = t.getVertexBuffer();
    	this.mIndexBuffer = t.getIndexBuffer();
    	this.numOfIndecies = t.getNumberOfIndecies();
        this.mFVertexBuffer.position(0);
        this.mIndexBuffer.position(0);
    }

   //overriden method
    protected void draw(GL10 gl)
    {
        long curtime = SystemClock.uptimeMillis();
        if ((curtime - prevtime) > 2000)
        {
        	prevtime = curtime;
        	sides += 1;
        	if (sides > 20)
        	{
        		sides = 3;
        	}
            this.prepareBuffers(sides);
        }
    	//EvenPolygon.test();
    	gl.glColor4f(1.0f, 0, 0, 0.5f);
    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
    	gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndecies,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}
