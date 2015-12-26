package opengl.scenes;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import opengl.jni.Natives.EventListener;

import android.view.SurfaceHolder;


/**
 * A generic GL Thread. Takes care of initializing EGL and GL. Delegates
 * to a Renderer instance to do the actual drawing.
 *
 */
public class GLThread extends Thread implements EventListener 
{
    public GLThread(Renderer renderer, SurfaceHolder holder) {
        super();
        mDone = false;
        mWidth = 0;
        mHeight = 0;
        mRenderer = renderer;
        mHolder = holder;
        setName("GLThread");
    }

    @Override
    public void run() {
        /*
         * When the android framework launches a second instance of
         * an activity, the new instance's onCreate() method may be
         * called before the first instance returns from onDestroy().
         *
         * This semaphore ensures that only one instance at a time
         * accesses EGL.
         */
        try {
            try {
            sEglSemaphore.acquire();
            } catch (InterruptedException e) {
                return;
            }
            guardedRun();
        } catch (InterruptedException e) {
            // fall thru and exit normally
           
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            sEglSemaphore.release();
        }
    }

    private void guardedRun() throws InterruptedException {
        mEglHelper = new EglHelper();
        /*
         * Specify a configuration for our opengl session
         * and grab the first configuration that matches is
         */
        int[] configSpec = mRenderer.getConfigSpec();
        mEglHelper.start(configSpec);

        GL10 gl = null;
        boolean tellRendererSurfaceCreated = true;
        boolean tellRendererSurfaceChanged = true;

        /*
         * This is our main activity thread's loop, we go until
         * asked to quit.
         */
        while (!mDone) {

            /*
             *  Update the asynchronous state (window size)
             */
            int w, h;
            boolean changed;
            boolean needStart = false;
            synchronized (this) {
                Runnable r;
                while ((r = getEvent()) != null) {
                    r.run();
                }
                if (mPaused) {
                    mEglHelper.finish();
                    needStart = true;
                }
                if(needToWait()) {
                    while (needToWait()) {
                        wait();
                    }
                }
                if (mDone) {
                    break;
                }
                changed = mSizeChanged;
                w = mWidth;
                h = mHeight;
                mSizeChanged = false;
            }
            if (needStart) {
                mEglHelper.start(configSpec);
                tellRendererSurfaceCreated = true;
                changed = true;
            }
            if (changed) {
                gl = (GL10) mEglHelper.createSurface(mHolder);
                tellRendererSurfaceChanged = true;
                System.out.println("Vendor:" + gl.glGetString(GL11.GL_VENDOR));
                System.out.println("Renderer:" + gl.glGetString(GL11.GL_RENDERER));
                System.out.println("Version:" + gl.glGetString(GL11.GL_VERSION));
            }
            if (tellRendererSurfaceCreated) {
                mRenderer.surfaceCreated(gl);
                tellRendererSurfaceCreated = false;
            }
            if (tellRendererSurfaceChanged) {
                mRenderer.sizeChanged(gl, w, h);
                tellRendererSurfaceChanged = false;
            }
            if ((w > 0) && (h > 0)) {
                /* draw a frame here */
                mRenderer.drawFrame(gl);
            	
                /*
                 * Once we're done with GL, we need to call swapBuffers()
                 * to instruct the system to display the rendered frame
                 */
                mEglHelper.swap();
            }
         }

        /*
         * clean-up everything...
         */
        mEglHelper.finish();
    }

    private boolean needToWait() {
        return (mPaused || (! mHasFocus) || (! mHasSurface) || mContextLost)
            && (! mDone);
    }

    public void surfaceCreated() {
        synchronized(this) {
            mHasSurface = true;
            mContextLost = false;
            notify();
        }
        
    }

    public void surfaceDestroyed() {
        synchronized(this) {
            mHasSurface = false;
            notify();
        }
    }

    public void onPause() {
        synchronized (this) {
            mPaused = true;
        }
    }

    public void onResume() {
        synchronized (this) {
            mPaused = false;
            notify();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        synchronized (this) {
            mHasFocus = hasFocus;
            if (mHasFocus == true) {
                notify();
            }
        }
    }
    public void onWindowResize(int w, int h) {
        synchronized (this) {
            mWidth = w;
            mHeight = h;
            mSizeChanged = true;
        }
    }

    public void requestExitAndWait() {
        // don't call this from GLThread thread or it is a guaranteed
        // deadlock!
        synchronized(this) {
            mDone = true;
            notify();
        }
        try {
            join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Queue an "event" to be run on the GL rendering thread.
     * @param r the runnable to be run on the GL rendering thread.
     */
    public void queueEvent(Runnable r) {
        synchronized(this) {
            mEventQueue.add(r);
        }
    }

    private Runnable getEvent() {
        synchronized(this) {
            if (mEventQueue.size() > 0) {
                return mEventQueue.remove(0);
            }

        }
        return null;
    }

//    public void setSurfaceHolder(SurfaceHolder holder) {
//        mHolder = holder;
//    }

    private static final Semaphore sEglSemaphore = new Semaphore(1);
    private boolean mSizeChanged = true;
    private SurfaceHolder mHolder;

    private boolean mDone;
    private boolean mPaused;
    private boolean mHasFocus;
    private boolean mHasSurface;
    private boolean mContextLost;
    private int mWidth;
    private int mHeight;
    private Renderer mRenderer;
    private ArrayList<Runnable> mEventQueue = new ArrayList<Runnable>();
    private EglHelper mEglHelper;
    
    
    
	@Override
	public void GLSwapBuffers() {
		if ( mEglHelper != null ) {
			mEglHelper.swap();
		}
	}

	@Override
	public void OnMessage(String text) {
		System.out.println("GLThread::OnMessage " + text);
	}
}
