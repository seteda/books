package ch03.game.sb;

import java.util.Timer;
import java.util.TimerTask;

import ch03.common.AudioClip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.LinearLayout;
/**
 * Base class for all games. Extends {@link LinearLayout}
 * and uses a {@link TimerTask} to invalidate the view
 * @author V. Silva
 *
 */
public abstract class ArcadeGame extends LinearLayout
{
	// App context
	private Context mContex;
	
	// Update timer used to invalidate the view
	private Timer mUpdateTimer;
	
	// Timer period
	private long mPeriod = 1000;
	
	/**
	 * C
	 * @param context
	 */
	public ArcadeGame(Context context) {
		super(context);
		mContex = context;
	}
	public ArcadeGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContex = context;
	}

	/**
	 * Fires on layout
	 */
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		try {
			//Init game
			initialize();
			
			/**
			 * start update task. Which will fire onDraw in the future
			 */
			startUpdateTimer();
		} 
		catch (Exception e) {
			// bug
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the update period
	 * @param period
	 */
	public void setUpdatePeriod(long period) {
		mPeriod = period;
	}
	
	/**
	 * A timer is used to move the sprite around
	 */
	protected void startUpdateTimer() {
		mUpdateTimer = new Timer();
		mUpdateTimer.schedule(new UpdateTask(), 0, mPeriod);
	}

	protected void stopUpdateTimer() {
		if (mUpdateTimer != null) {
			mUpdateTimer.cancel();
		}
	}

	public Context getContex() {
		return mContex;
	}
	
	/**
	 * Load an image
	 * 
	 * @param id
	 * @return
	 */
	protected Bitmap getImage(int id) {
		return BitmapFactory.decodeResource(mContex.getResources(), id);
	}

	/**
	 * Get AudioClip
	 * 
	 * @param id
	 * @return
	 */
	protected AudioClip getAudioClip(int id) {
		return new AudioClip(mContex, id);
	}
	
	/**
	 * Overload this to update the sprites on the game
	 */
	abstract protected void updatePhysics();
	
	/**
	 * Overload to initialize the game
	 */
	abstract protected void initialize();
	
	abstract protected boolean gameOver();
	
	abstract protected long getScore();
	
	/**
	 * Canvas update task
	 * 
	 * @author vsilva
	 * 
	 */
	private class UpdateTask extends TimerTask {

		@Override
		public void run() {
			updatePhysics();
			
			/**
			 * Cause an invalidate to happen on a subsequent cycle through the
			 * event loop. Use this to invalidate the View from a non-UI thread.
			 * onDraw will be called sometime in the future.
			 */
			postInvalidate();
		}

	}
	
	/**
	 * Halt game. Stops the update task. Called by a parent activity to halt
	 * 
	 */
	public void halt() {
		stopUpdateTimer();
	}

	/**
	 * Resume Game
	 */
	public void resume() {
		initialize();
		startUpdateTimer();
	}
	
}
