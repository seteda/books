package com.androidbook.OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;

//filename: SimpleTriangleRenderer.java
public class ES20SimpleTriangleRenderer extends ES20AbstractRenderer
{
    //A raw native buffer to hold the point coordinates
    private FloatBuffer mFVertexBuffer;
    private static final int FLOAT_SIZE_BYTES = 4;
    private final float[] mTriangleVerticesData = {
            // X, Y, Z, U, V
            -1.0f, -0.5f, 0, 
            1.0f, -0.5f, 0, 
            0.0f,  1.11803399f, 0 };

    public ES20SimpleTriangleRenderer(Context context) 
    {
        ByteBuffer vbb = ByteBuffer.allocateDirect(mTriangleVerticesData.length
                * FLOAT_SIZE_BYTES);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(mTriangleVerticesData);
        mFVertexBuffer.position(0);
    }

    protected void draw(GL10 gl, int positionHandle)
    {
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false,
                0, mFVertexBuffer);
        checkGlError("glVertexAttribPointer maPosition");
        //mFVertexBuffer.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(positionHandle);
        checkGlError("glEnableVertexAttribArray maPositionHandle");

        this.setupMatrices();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        checkGlError("glDrawArrays");
    }
}
