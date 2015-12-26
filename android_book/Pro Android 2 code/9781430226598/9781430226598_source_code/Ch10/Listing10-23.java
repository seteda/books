//A polygon with 4 sides and a radious of 0.5
//and located at (x,y,z) of (0,0,0)
RegularPolygon square = new RegularPolygon(0,0,0,0.5f,4);

//Let the polygon return the vertices
mFVertexBuffer = square.getVertexBuffer();

//Let the polygon return the triangles
mIndexBuffer = square.getIndexBuffer();

//you will need this for glDrawElements
numOfIndices = square.getNumberOfIndices();

//set the buffers to the start
this.mFVertexBuffer.position(0);
this.mIndexBuffer.position(0);

//set the vertex pointer
gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);

//draw it with the given number of Indices
gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices,
         GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
