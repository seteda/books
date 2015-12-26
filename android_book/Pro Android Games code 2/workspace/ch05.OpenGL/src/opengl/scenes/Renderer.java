package opengl.scenes;

import javax.microedition.khronos.opengles.GL10;

/**
 * A generic renderer interface.
 */
public interface Renderer {
    /**
     * @return the EGL configuration specification desired by the renderer.
     */
    int[] getConfigSpec();

    /**
     * Surface created.
     * Called when the surface is created. Called when the application
     * starts, and whenever the GPU is reinitialized. This will
     * typically happen when the device awakes after going to sleep.
     * Set your textures here.
     */
    void surfaceCreated(GL10 gl);
    /**
     * Surface changed size.
     * Called after the surface is created and whenever
     * the OpenGL ES surface size changes. Set your viewport here.
     * @param gl
     * @param width
     * @param height
     */
    void sizeChanged(GL10 gl, int width, int height);
    /**
     * Draw the current frame.
     * @param gl
     */
    void drawFrame(GL10 gl);
}
