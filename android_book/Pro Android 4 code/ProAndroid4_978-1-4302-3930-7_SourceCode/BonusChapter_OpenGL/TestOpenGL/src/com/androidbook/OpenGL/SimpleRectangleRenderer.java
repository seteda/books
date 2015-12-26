package com.androidbook.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

//filename: SimpleTriangleRenderer.java
public class SimpleRectangleRenderer extends AbstractRenderer
{
   //Number of points or vertices we want to use
    private final static int VERTS = 4;
    
    //A raw native buffer to hold the point coordinates
    private FloatBuffer mFVertexBuffer;
    
    //A raw native buffer to hold indices
    //allowing a reuse of points.
    private ShortBuffer mIndexBuffer;
    
    public SimpleRectangleRenderer(Context context) 
    {
        ByteBuffer vbb = ByteBuffer.allocateDirect(VERTS * 3 * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();

        ByteBuffer ibb = ByteBuffer.allocateDirect(6 * 2);
        ibb.order(ByteOrder.nativeOrder());
        mIndexBuffer = ibb.asShortBuffer();

        float[] coords = {
                -0.75f, -0.5f, 0, // (x1,y1,z1)
                 0.75f, -0.5f, 0,
                 0.75f,  0.5f, 0,
                -0.75f,  0.5f, 0,
        };
        
        for (int i = 0; i < VERTS; i++) {
            for(int j = 0; j < 3; j++) {
                mFVertexBuffer.put(coords[i*3+j]);
            }
        }
        short[] myIndecesArray = {0,1,2,0,2,3};
        for (int i=0;i<6;i++)
        {
           mIndexBuffer.put(myIndecesArray[i]);
        }
        mFVertexBuffer.position(0);
        mIndexBuffer.position(0);
    }

   //overriden method
    protected void draw(GL10 gl)
    {
    	RegularPolygon.test();
    	gl.glColor4f(1.0f, 0, 0, 0.5f);
    	gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
    	gl.glDrawElements(GL10.GL_TRIANGLES, 6,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
    }
}
