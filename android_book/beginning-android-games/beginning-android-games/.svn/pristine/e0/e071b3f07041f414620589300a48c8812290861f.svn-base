package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class VBOVertices3 extends Vertices3{	
	int vboHandle;
	
	public VBOVertices3(GLGraphics glGraphics, int maxVertices, int maxIndices,
			boolean hasColor, boolean hasTexCoords, boolean hasNormals) {
		super(glGraphics, maxVertices, maxIndices, hasColor, hasTexCoords, hasNormals);
		createHandle();
	}
	
	public void createHandle() {
		int[] tmp = new int[1];
		GL11 gl = (GL11)glGraphics.getGL();
		gl.glGenBuffers(1, tmp, 0);
		vboHandle = tmp[0];
	}

	public void setVertices(float[] vertices, int offset, int length) {
		super.setVertices(vertices, offset, length);
		
		GL11 gl = (GL11)glGraphics.getGL();
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboHandle);
		gl.glBufferData(GL11.GL_ARRAY_BUFFER, this.vertices.limit() * 4, this.vertices, GL11.GL_STATIC_DRAW);
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	public void bind() {
		GL11 gl = (GL11)glGraphics.getGL();

		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, vboHandle);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);			
		gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, 0);

		if (hasColor) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);			
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, 3*4);
		}

		if (hasTexCoords) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);			
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, (hasColor ? 7 : 3) * 4);
		}

		if (hasNormals) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			int offset = 3;
			if (hasColor)
				offset += 4;
			if (hasTexCoords)
				offset += 2;			
			gl.glNormalPointer(GL10.GL_FLOAT, vertexSize, offset * 4);
		}
	}

	public void draw(int primitiveType, int offset, int numVertices) {
		GL10 gl = glGraphics.getGL();

		if (indices != null) {
			indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices,
					GL10.GL_UNSIGNED_SHORT, indices);
		} else {
			gl.glDrawArrays(primitiveType, offset, numVertices);
		}
	}

	public void unbind() {
		GL11 gl = (GL11)glGraphics.getGL();
		if (hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		if (hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

		if (hasNormals)
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	public int getNumIndices() {
		return indices.limit();
	}
	
	public int getNumVertices() {
		return vertices.limit() / (vertexSize / 4);
	}
}
