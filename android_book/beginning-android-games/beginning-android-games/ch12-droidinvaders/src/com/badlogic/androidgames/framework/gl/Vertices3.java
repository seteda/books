package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Vertices3 {
	final GLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final boolean hasNormals;
	final int vertexSize;
	final IntBuffer vertices;
	final int[] tmpBuffer;
	final ShortBuffer indices;
	
	public Vertices3(GLGraphics glGraphics, int maxVertices, int maxIndices,
			boolean hasColor, boolean hasTexCoords, boolean hasNormals) {
		this.glGraphics = glGraphics;
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.hasNormals = hasNormals;
		this.vertexSize = (3 + (hasColor ? 4 : 0) + (hasTexCoords ? 2 : 0) + (hasNormals ? 3
				: 0)) * 4;
		this.tmpBuffer = new int[maxVertices * vertexSize / 4];

		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asIntBuffer();

		if (maxIndices > 0) {
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		} else {
			indices = null;
		}
	}

	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.clear();
		int len = offset + length;
		for (int i = offset, j = 0; i < len; i++, j++)
			tmpBuffer[j] = Float.floatToRawIntBits(vertices[i]);
		this.vertices.put(tmpBuffer, 0, length);
		this.vertices.flip();
	}

	public void setIndices(short[] indices, int offset, int length) {
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}

	public void bind() {
		GL10 gl = glGraphics.getGL();

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, vertices);

		if (hasColor) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(3);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}

		if (hasTexCoords) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor ? 7 : 3);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}

		if (hasNormals) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			int offset = 3;
			if (hasColor)
				offset += 4;
			if (hasTexCoords)
				offset += 2;
			vertices.position(offset);
			gl.glNormalPointer(GL10.GL_FLOAT, vertexSize, vertices);
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
		GL10 gl = glGraphics.getGL();
		if (hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		if (hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

		if (hasNormals)
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
	
	public int getNumIndices() {
		return indices.limit();
	}
	
	public int getNumVertices() {
		return vertices.limit() / (vertexSize / 4);
	}
}
