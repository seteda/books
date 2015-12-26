public static interface GLSurfaceView.Renderer
{
   void onDrawFrame(GL10 gl);
   void onSuraceChanged(GL10 gl, int width, int height);
   void onSurfaceCreated(GL10 gl, EGLConfig config);
}
