//calculate aspect ratio first
float ratio = (float) w / h;

//indicate that we want a perspective projection
glMatrixMode(GL10.GL_PROJECTION);

//Specify the frustum: the viewing volume
gl.glFrustumf(
   -ratio, // Left side of the viewing box
   ratio,  // right side of the viewing box
   1,      // top of the viewing box
   -1,     // bottom of the viewing box
   3,      // how far is the front of the box from the camera
   7);     // how far is the back of the box from the camera
