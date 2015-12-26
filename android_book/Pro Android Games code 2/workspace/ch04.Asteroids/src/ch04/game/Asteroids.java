package ch04.game;

import ch04.common.AudioClip;
import ch04.common.Polygon;
import ch04.common.Tools;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Asteroids extends ArcadeGame  
{
	public static final String NAME = "Asteroids";
	static final String TAG = "Asteroids";

	// Background stars.
	int i;
	int numStars;
	Point[] stars;

	// Game data.
	int score;
	int highScore;
	int newShipScore;
	int newUfoScore;

	// Flags for game state and options.
	boolean loaded = false;
	boolean paused;
	boolean playing = false;
	boolean sound = true;
	boolean detail;

	// Key flags.
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;

	// Sprite objects.
	PolygonSprite ship;

	PolygonSprite fwdThruster, revThruster;
	PolygonSprite ufo;
	PolygonSprite missile;
	PolygonSprite[] photons = new PolygonSprite[Constants.MAX_SHOTS];
	PolygonSprite[] asteroids = new PolygonSprite[Constants.MAX_ROCKS];
	PolygonSprite[] explosions = new PolygonSprite[Constants.MAX_SCRAP];

	// Asteroid data.

	boolean[] asteroidIsSmall = new boolean[Constants.MAX_ROCKS]; // Asteroid
																	// size flag

	int asteroidsCounter; // Break-time counter.
	double asteroidsSpeed; // Asteroid speed.
	int asteroidsLeft; // Number of active asteroids.

	// Ship data.

	int shipsLeft; // Number of ships left in game, including current one.
	int shipCounter; // Timer counter for ship explosion.
	int hyperCounter; // Timer counter for hyperspace.

	// Photon data.

	int photonIndex; // Index to next available photon sprite.
	long photonTime; // Time value used to keep firing rate constant.

	// Flying saucer data.

	int ufoPassesLeft; // Counter for number of flying saucer passes.
	int ufoCounter; // Timer counter used to track each flying saucer pass.

	// Missile data.

	int missileCounter; // Counter for life of missile.

	// Explosion data.

	int[] explosionCounter = new int[Constants.MAX_SCRAP]; // Time counters for
															// explosions.
	int explosionIndex; // Next available explosion sprite.

	// Sound clips.

	AudioClip crashSound;
	AudioClip explosionSound;
	AudioClip fireSound;
	AudioClip missileSound;
	AudioClip saucerSound;
	AudioClip thrustersSound;
	AudioClip warpSound;

	// Flags for looping sound clips.

	boolean thrustersPlaying;
	boolean saucerPlaying;
	boolean missilePlaying;

	// Counter and total used to track the loading of the sound clips.

	int clipTotal = 0;
	int clipsLoaded = 0;

	// Android Vars
	private Paint mPaint;
	private Paint mGreenPaint;
	private Paint mRedPaint;

	private Context mContex;
	
	/**
	 * 
	 * @param context
	 */
	public Asteroids(Context context) {
		super(context);
		mContex = context;
		setUpdatePeriod(Constants.DELAY);
		initLayout();
	}

	public Asteroids(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContex = context;
		setUpdatePeriod(Constants.DELAY);
		initLayout();
	}


	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		keyReleased(event);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		keyPressed(event);
		return true;
	}

	/**
	 * OnTap Start Game
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (loaded && !playing) {
			initGame();
		}
		return true;
	}
	
	/**
	 * Draw Sprites
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// Get screen size
		int width = getWidth();
		int height = getHeight();

		// draw stars
		for (i = 0; i < numStars; i++) {
			canvas.drawPoint(stars[i].x, stars[i].y, mPaint);
		}

		// Draw photon bullets.

		for (i = 0; i < Constants.MAX_SHOTS; i++) {
			if (photons[i].active) {
				photons[i].draw(canvas, mPaint);
			}
		}
		// Draw the guided missile, counter is used to quickly fade color to
		// black when near expiration.
		if (missile.active) {
			missile.draw(canvas, mPaint);
		}

		// Draw the flying saucer.

		if (ufo.active) {
			ufo.draw(canvas, mRedPaint);
		}

		// Draw the ship
		if (ship.active) {
			// draw ship
			ship.draw(canvas, mPaint);

			// Draw thruster exhaust if thrusters are on. Do it randomly to get
			// a flicker effect.

			if (!paused && Math.random() < 0.5) { // && detail
				if (up) {
					fwdThruster.draw(canvas, mPaint);
				}
				if (down) {
					revThruster.draw(canvas, mPaint);
				}
			}
		}

		// Draw the asteroids.
		for (i = 0; i < Constants.MAX_ROCKS; i++) {
			if (asteroids[i].active) {
				asteroids[i].draw(canvas, mGreenPaint);
			}
		}

		// Draw any explosion debris, 

		for (i = 0; i < Constants.MAX_SCRAP; i++) {
			if (explosions[i].active) {
				explosions[i].draw(canvas, mGreenPaint);
			}
		}

		// Display status and messages.
		float fontSize = mPaint.getTextSize();

		canvas.drawText("Score: " + score, fontSize, mPaint.getTextSize(),
				mPaint);
		canvas.drawText("Ships: " + shipsLeft, fontSize, height - fontSize,
				mPaint);

		String str = "High: " + highScore;

		canvas.drawText("High: " + highScore, width
				- (fontSize / 1.2f * str.length()), fontSize, mPaint);

		if (!sound) {
			str = "Mute";
			canvas.drawText(str, width - (fontSize * str.length()), height
					- fontSize, mPaint);
		}

		if (!playing) {
			//if (!loaded) {
			//} else {
			if (loaded) {
				str = "Game Over";
				final float x = (width - (str.length() * fontSize / 2)) / 2;

				canvas.drawText(str, x, height / 4, mPaint);

//				str = "'S' to Start";
//				canvas.drawText(str, x, height / 4 + fontSize, mPaint);
			}
		} 
		else if (paused) {
			str = "Game Paused";

			final float x = (width - (str.length() * fontSize / 2)) / 2;
			canvas.drawText(str, x, height / 4, mPaint);
		}

	}


	/**
	 * Initialize Canvas layout
	 */
	void initLayout() {
		mPaint = new Paint();
		mPaint.setARGB(255, 255, 255, 255);

		// Asteroids
		mGreenPaint = new Paint();
		mGreenPaint.setARGB(255, 0, 255, 0);
		//mGreenPaint.setStyle(Paint.Style.FILL);
		
		// UFO
		mRedPaint = new Paint();
		mRedPaint.setARGB(255, 255, 0, 0);
		//mRedPaint.setStyle(Paint.Style.FILL);
	}
	
	/**
	 * Create Shapes
	 */
	protected void initialize () {

		// Load sounds
		try {
			loadSounds();
			loaded = true;
			
		} catch (Exception e) {
			Tools.MessageBox(mContex, "Sound Error: " + e.toString());
		}

		// create start background
		createStarts();

		// Create shape for the ship sprite.

		ship = new PolygonSprite(); 
		ship.shape.addPoint(0, -10);
		ship.shape.addPoint(7, 10);
		ship.shape.addPoint(-7, 10);

		// Create thruster shapes
		createThrusters();

		// Create shape for each photon sprites.

		for (i = 0; i < Constants.MAX_SHOTS; i++) {
			photons[i] = new PolygonSprite();
			photons[i].shape.addPoint(1, 1);
			photons[i].shape.addPoint(1, -1);
			photons[i].shape.addPoint(-1, 1);
			photons[i].shape.addPoint(-1, -1);
		}

		// Create shape for the flying saucer.

		ufo = new PolygonSprite();
		ufo.shape.addPoint(-15, 0);
		ufo.shape.addPoint(-10, -5);
		ufo.shape.addPoint(-5, -5);
		ufo.shape.addPoint(-5, -8);
		ufo.shape.addPoint(5, -8);
		ufo.shape.addPoint(5, -5);
		ufo.shape.addPoint(10, -5);
		ufo.shape.addPoint(15, 0);
		ufo.shape.addPoint(10, 5);
		ufo.shape.addPoint(-10, 5);

		// Create shape for the guided missile.

		missile = new PolygonSprite();
		missile.shape.addPoint(0, -4);
		missile.shape.addPoint(1, -3);
		missile.shape.addPoint(1, 3);
		missile.shape.addPoint(2, 4);
		missile.shape.addPoint(-2, 4);
		missile.shape.addPoint(-1, 3);
		missile.shape.addPoint(-1, -3);

		// Create asteroid sprites.

		for (i = 0; i < Constants.MAX_ROCKS; i++)
			asteroids[i] = new PolygonSprite();

		// Create explosion sprites.

		for (i = 0; i < Constants.MAX_SCRAP; i++)
			explosions[i] = new PolygonSprite();

		// Initialize game data and put us in 'game over' mode.

		highScore = 0;
		sound = true;
		detail = true;
		initGame();
		endGame();

	}

	void initGame() { 
		// Initialize game data and sprites.
		score = 0;
		shipsLeft = Constants.MAX_SHIPS;
		asteroidsSpeed = Constants.MIN_ROCK_SPEED;
		newShipScore = Constants.NEW_SHIP_POINTS;
		newUfoScore = Constants.NEW_UFO_POINTS;
		initShip();
		initPhotons();
		stopUfo();
		stopMissle();
		initAsteroids();
		initExplosions();
		playing = true;
		paused = false;
		photonTime = System.currentTimeMillis();

	}

	void createStarts() {
		int width = getWidth();
		int height = getHeight();
		
		// Generate the starry background.
		numStars = width * height / 5000;
		stars = new Point[numStars];

		for (i = 0; i < numStars; i++)
			stars[i] = new Point((int) (Math.random() * width), (int) (Math
					.random() * height));
	}

	/**
	 * Init Asteroids
	 */
	public void initAsteroids() 
	{
		int width = getWidth();
		int height = getHeight();

		int i, j;
		int s;
		double theta, r;
		int x, y;

		// Create random shapes, positions and movements for each asteroid.
		for (i = 0; i < Constants.MAX_ROCKS; i++) {

			// Create a jagged shape for the asteroid and give it a random
			// rotation.
			asteroids[i].shape = new Polygon();

			s = Constants.MIN_ROCK_SIDES
					+ (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));

			for (j = 0; j < s; j++) {
				theta = 2 * Math.PI / s * j;
				r = Constants.MIN_ROCK_SIZE
						+ (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE));
				x = (int) -Math.round(r * Math.sin(theta));
				y = (int) Math.round(r * Math.cos(theta));
				asteroids[i].shape.addPoint(x, y);
			}
			asteroids[i].active = true;
			asteroids[i].angle = 0.0;
			asteroids[i].deltaAngle = Math.random() * 2
					* Constants.MAX_ROCK_SPIN - Constants.MAX_ROCK_SPIN;

			// Place the asteroid at one edge of the screen.

			if (Math.random() < 0.5) {
				asteroids[i].x = -width / 2;
				if (Math.random() < 0.5)
					asteroids[i].x = width / 2;
				asteroids[i].y = (int) (Math.random() * height);
			} else {
				asteroids[i].x = (int) (Math.random() * width);
				asteroids[i].y = -height / 2;
				if (Math.random() < 0.5)
					asteroids[i].y = height / 2;
			}

			// Set a random motion for the asteroid.

			asteroids[i].deltaX = Math.random() * asteroidsSpeed;
			if (Math.random() < 0.5)
				asteroids[i].deltaX = -asteroids[i].deltaX;
			asteroids[i].deltaY = Math.random() * asteroidsSpeed;
			if (Math.random() < 0.5)
				asteroids[i].deltaY = -asteroids[i].deltaY;

			asteroids[i].render(width, height);
			asteroidIsSmall[i] = false;
		}

		asteroidsCounter = Constants.STORM_PAUSE;
		asteroidsLeft = Constants.MAX_ROCKS;
		if (asteroidsSpeed < Constants.MAX_ROCK_SPEED)
			asteroidsSpeed += 0.5;
	}

	void createThrusters() {
		// Create shapes for the ship thrusters.
		fwdThruster = new PolygonSprite();
		fwdThruster.shape.addPoint(0, 12);
		fwdThruster.shape.addPoint(-3, 16);
		fwdThruster.shape.addPoint(0, 26);
		fwdThruster.shape.addPoint(3, 16);
		revThruster = new PolygonSprite();
		revThruster.shape.addPoint(-2, 12);
		revThruster.shape.addPoint(-4, 14);
		revThruster.shape.addPoint(-2, 20);
		revThruster.shape.addPoint(0, 14);
		revThruster.shape.addPoint(2, 12);
		revThruster.shape.addPoint(4, 14);
		revThruster.shape.addPoint(2, 20);
		revThruster.shape.addPoint(0, 14);
	}

	public void initPhotons() {

		int i;

		for (i = 0; i < Constants.MAX_SHOTS; i++)
			photons[i].active = false;
		photonIndex = 0;
	}

	public void updatePhotons() {
		int width = getWidth();
		int height = getHeight();

		int i;

		// Move any active photons. Stop it when its counter has expired.
		for (i = 0; i < Constants.MAX_SHOTS; i++)
			if (photons[i].active) {
				if (!photons[i].advance(width, height))
					photons[i].render(width, height);
				else
					photons[i].active = false;
			}
	}

	public void keyReleased(KeyEvent e) {
		final int keyCode = e.getKeyCode();

		// Check if any cursor keys where released and set flags.

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT 
				|| keyCode == KeyEvent.KEYCODE_Q)
			left = false;
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_W)
			right = false;
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP
				|| keyCode == KeyEvent.KEYCODE_O)
			up = false;
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_L)
			down = false;

		if (!up && !down && thrustersPlaying) {
			thrustersSound.stop();
			thrustersPlaying = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		final int keyCode = e.getKeyCode();

		// Check if any cursor keys have been pressed and set flags.

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT 
				|| keyCode == KeyEvent.KEYCODE_Q)
			left = true;
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_W)
			right = true;
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP
				|| keyCode == KeyEvent.KEYCODE_O)
			up = true;
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_L)
			down = true;

		if ((up || down) && ship.active  && !thrustersPlaying) {
			if (sound && !paused) {
				thrustersSound.loop();
			}
			thrustersPlaying = true;
		}

		// Spacebar: fire a photon and start its counter.

		if ( ((keyCode == KeyEvent.KEYCODE_SPACE) || (keyCode == 23)) && ship.active) 
		{
			if (sound & !paused) {
				fireSound.play();
			}

			photonTime = System.currentTimeMillis();
			photonIndex++;

			if (photonIndex >= Constants.MAX_SHOTS)
				photonIndex = 0;
			
			photons[photonIndex].active = true;
			photons[photonIndex].x = ship.x;
			photons[photonIndex].y = ship.y;
			photons[photonIndex].deltaX = 2 * Constants.MAX_ROCK_SPEED
					* -Math.sin(ship.angle);
			photons[photonIndex].deltaY = 2 * Constants.MAX_ROCK_SPEED
					* Math.cos(ship.angle);
		}

		// Allow upper or lower case characters for remaining keys.

		// 'H' key: warp ship into hyperspace by moving to a random location and
		// starting counter.

		if (keyCode == KeyEvent.KEYCODE_H && ship.active && hyperCounter <= 0) {
			ship.x = (int) (Math.random() * getWidth());
			ship.y = (int) (Math.random() * getHeight());
			hyperCounter = Constants.HYPER_COUNT;

			if (sound & !paused)
				warpSound.play();
		}

		// 'P' key: toggle pause mode and start or stop any active looping sound
		// clips.

		if (keyCode == KeyEvent.KEYCODE_P) { 
			if (paused) {
				if (sound && missilePlaying)
					missileSound.loop();
				if (sound && saucerPlaying)
					saucerSound.loop();
				if (sound && thrustersPlaying)
					thrustersSound.loop();
			} else {
				if (missilePlaying)
					missileSound.stop();
				if (saucerPlaying)
					saucerSound.stop();
				if (thrustersPlaying)
					thrustersSound.stop();
			}
			paused = !paused;
		}

		// 'M' key: toggle sound on or off and stop any looping sound clips.

		if (keyCode == KeyEvent.KEYCODE_M && loaded) { 
			if (sound) {
				crashSound.stop();
				explosionSound.stop();
				fireSound.stop();
				missileSound.stop();
				saucerSound.stop();
				thrustersSound.stop();
				warpSound.stop();
			} else {
				if (missilePlaying && !paused)
					missileSound.loop();
				if (saucerPlaying && !paused)
					saucerSound.loop();
				if (thrustersPlaying && !paused)
					thrustersSound.loop();
			}
			sound = !sound;
		}

		// 'S' key: start the game, if not already in progress.
		if (keyCode == KeyEvent.KEYCODE_S && loaded && !playing) {
			initGame();
		}

		// 'E' Exit: Back to main menu
		if (keyCode == KeyEvent.KEYCODE_E ) {
			stopUpdateTimer();
			releaseSounds();
			
			System.exit(0); //Argg!!
			// start the arcade activity (w/ score)
//			Intent intent = new Intent(mContex, ArcadeActivity.class);
//			intent.putExtra("score", (long)score);
//			intent.putExtra("game", NAME);
//			
//			mContex.startActivity(intent);
		}
	}

	void initShip() 
	{
		int width = getWidth();
		int height = getHeight();

		// Reset the ship sprite at the center of the screen.
		ship.active = true;
		ship.angle = 0.0;
		ship.deltaAngle = 0.0;
		ship.x = 0;
		ship.y = 0;
		ship.deltaX = 0.0;
		ship.deltaY = 0.0;

		ship.render(width, height);

		// Initialize thruster sprites.
		createThrusters();

		fwdThruster.x = ship.x;
		fwdThruster.y = ship.y;
		fwdThruster.angle = ship.angle;
		fwdThruster.render(width, height);
		revThruster.x = ship.x;
		revThruster.y = ship.y;
		revThruster.angle = ship.angle;
		revThruster.render(width, height);

		if (loaded)
			thrustersSound.stop();

		thrustersPlaying = false;

		hyperCounter = 0;
	}

	public void updateShip() {  
		int width = getWidth();
		int height = getHeight();

		double dx, dy, speed;

		if (!playing)
			return;

		/**
		 * Rotate the ship if left or right cursor key is down.
		 */
		if (left) {
			ship.angle += Constants.SHIP_ANGLE_STEP;
			if (ship.angle > 2 * Math.PI)
				ship.angle -= 2 * Math.PI;
		}
		if (right) {
			ship.angle -= Constants.SHIP_ANGLE_STEP;
			if (ship.angle < 0)
				ship.angle += 2 * Math.PI;
		}

		/**
		 * Fire thrusters if up or down cursor key is down.
		 */
		dx = Constants.SHIP_SPEED_STEP * -Math.sin(ship.angle);
		dy = Constants.SHIP_SPEED_STEP * Math.cos(ship.angle);
		if (up) {
			ship.deltaX += dx;
			ship.deltaY += dy;
		}
		if (down) {
			ship.deltaX -= dx;
			ship.deltaY -= dy;
		}

		/**
		 * Don't let ship go past the speed limit.
		 */
		if (up || down) {
			speed = Math.sqrt(ship.deltaX * ship.deltaX + ship.deltaY
					* ship.deltaY);
			if (speed > Constants.MAX_SHIP_SPEED) {
				dx = Constants.MAX_SHIP_SPEED * -Math.sin(ship.angle);
				dy = Constants.MAX_SHIP_SPEED * Math.cos(ship.angle);
				if (up)
					ship.deltaX = dx;
				else
					ship.deltaX = -dx;
				if (up)
					ship.deltaY = dy;
				else
					ship.deltaY = -dy;
			}
		}

		/**
		 * Move the ship. If it is currently in hyper space, advance the
		 * count down.
		 */
		if (ship.active) {
			ship.advance(width, height);
			ship.render(width, height);
			if (hyperCounter > 0)
				hyperCounter--;

			// Update the thruster sprites to match the ship sprite.

			fwdThruster.x = ship.x;
			fwdThruster.y = ship.y;
			fwdThruster.angle = ship.angle;
			fwdThruster.render(width, height);
			revThruster.x = ship.x;
			revThruster.y = ship.y;
			revThruster.angle = ship.angle;
			revThruster.render(width, height);
		}

		/**
		 * Ship is exploding, advance the countdown or create a new ship if it
		 * is done exploding. The new ship is added as though it were in
		 * hyper space. This gives the player time to move the ship if it is 
		 * in imminent danger. If that was the last ship, end the game.
		 */
		else {
			if (--shipCounter <= 0) {
				if (shipsLeft > 0) {
					initShip();
					hyperCounter = Constants.HYPER_COUNT;
				} else {
					endGame();
				}
			}
		}
	}

	public void stopShip() {

		ship.active = false;
		shipCounter = Constants.SCRAP_COUNT;
		if (shipsLeft > 0)
			shipsLeft--;

		if (loaded)
			thrustersSound.stop();

		thrustersPlaying = false;
	}

	public void endGame() {
		// Stop ship, flying saucer, guided missile and associated sounds.
		playing = false;
		stopShip();
		stopUfo();
		stopMissle();
	}

	void loadSounds() {
		// Load all sound clips by playing and immediately stopping them. Update
		// counter and total for display.
		Context ctx = getContext();

		crashSound = new AudioClip(ctx, R.raw.a_crash);
		clipTotal++;
		explosionSound = new AudioClip(ctx, R.raw.a_explosion);
		clipTotal++;
		fireSound = new AudioClip(ctx, R.raw.a_fire); 
		clipTotal++;
		missileSound = new AudioClip(ctx, R.raw.a_missle); 
		clipTotal++;
		saucerSound = new AudioClip(ctx, R.raw.a_saucer); 
		clipTotal++;
		thrustersSound = new AudioClip(ctx, R.raw.a_thrusters); 
		clipTotal++;
		warpSound = new AudioClip(ctx, R.raw.a_warp); 
		clipTotal++;
	}

	/**
	 * Release audio resources
	 */
	private void releaseSounds() {
		crashSound.release();
		explosionSound.release();
		fireSound.release();
		missileSound .release();
		saucerSound.release();
		thrustersSound.release();
		warpSound.release();
	}
	
	public void updateAsteroids() 
	{
		int width = getWidth();
		int height = getHeight();
		int i, j;

		// Move any active asteroids and check for collisions.

		for (i = 0; i < Constants.MAX_ROCKS; i++)
			if (asteroids[i].active) {
				asteroids[i].advance(width, height);
				asteroids[i].render(width, height);

				// If hit by photon, kill asteroid and advance score. If
				// asteroid is
				// large, make some smaller ones to replace it.

				for (j = 0; j < Constants.MAX_SHOTS; j++)
					if (photons[j].active && asteroids[i].active
							&& asteroids[i].isColliding(photons[j])) {
						asteroidsLeft--;
						asteroids[i].active = false;
						photons[j].active = false;
						if (sound)
							explosionSound.play();
						explode(asteroids[i], width, height);
						if (!asteroidIsSmall[i]) {
							score += Constants.BIG_POINTS;
							initSmallAsteroids(i);
						} else
							score += Constants.SMALL_POINTS;
					}

				// If the ship is not in hyperspace, see if it is hit.

				if (ship.active && hyperCounter <= 0 && asteroids[i].active
						&& asteroids[i].isColliding(ship)) {
					if (sound)
						crashSound.play();
					explode(ship, width, height);
					stopShip();
					stopUfo();
					stopMissle();
				}
			}
	}

	public void explode(PolygonSprite s, int width, int height) {

		int c, i, j;
		int cx, cy;

		// Create sprites for explosion animation. The each individual line
		// segment
		// of the given sprite is used to create a new sprite that will move
		// outward from the sprite's original position with a random rotation.

		s.render(width, height);
		c = 2;
		if (detail || s.sprite.npoints < 6)
			c = 1;
		for (i = 0; i < s.sprite.npoints; i += c) {
			explosionIndex++;
			if (explosionIndex >= Constants.MAX_SCRAP)
				explosionIndex = 0;
			explosions[explosionIndex].active = true;
			explosions[explosionIndex].shape = new Polygon();
			j = i + 1;
			if (j >= s.sprite.npoints)
				j -= s.sprite.npoints;
			cx = (int) ((s.shape.xpoints[i] + s.shape.xpoints[j]) / 2);
			cy = (int) ((s.shape.ypoints[i] + s.shape.ypoints[j]) / 2);
			explosions[explosionIndex].shape.addPoint(s.shape.xpoints[i] - cx,
					s.shape.ypoints[i] - cy);
			explosions[explosionIndex].shape.addPoint(s.shape.xpoints[j] - cx,
					s.shape.ypoints[j] - cy);
			explosions[explosionIndex].x = s.x + cx;
			explosions[explosionIndex].y = s.y + cy;
			explosions[explosionIndex].angle = s.angle;
			explosions[explosionIndex].deltaAngle = 4 * (Math.random() * 2
					* Constants.MAX_ROCK_SPIN - Constants.MAX_ROCK_SPIN);
			explosions[explosionIndex].deltaX = (Math.random() * 2
					* Constants.MAX_ROCK_SPEED - Constants.MAX_ROCK_SPEED + s.deltaX) / 2;
			explosions[explosionIndex].deltaY = (Math.random() * 2
					* Constants.MAX_ROCK_SPEED - Constants.MAX_ROCK_SPEED + s.deltaY) / 2;
			explosionCounter[explosionIndex] = Constants.SCRAP_COUNT;
		}
	}

	public void updateExplosions() {
		int width = getWidth();
		int height = getHeight();
		int i;

		// Move any active explosion debris. Stop explosion when its counter has
		// expired.

		for (i = 0; i < Constants.MAX_SCRAP; i++)
			if (explosions[i].active) {
				explosions[i].advance(width, height);
				explosions[i].render(width, height);
				if (--explosionCounter[i] < 0)
					explosions[i].active = false;
			}
	}

	public void initSmallAsteroids(int n) {
		int width = getWidth();
		int height = getHeight();
		int count;
		int i, j;
		int s;
		int tempX, tempY;
		double theta, r;
		int x, y;

		// Create one or two smaller asteroids from a larger one using inactive
		// asteroids. The new asteroids will be placed in the same position as
		// the
		// old one but will have a new, smaller shape and new, randomly
		// generated
		// movements.

		count = 0;
		i = 0;
		tempX = asteroids[n].x;
		tempY = asteroids[n].y;
		do {
			if (!asteroids[i].active) {
				asteroids[i].shape = new Polygon();
				s = Constants.MIN_ROCK_SIDES
						+ (int) (Math.random() * (Constants.MAX_ROCK_SIDES - Constants.MIN_ROCK_SIDES));
				for (j = 0; j < s; j++) {
					theta = 2 * Math.PI / s * j;
					r = (Constants.MIN_ROCK_SIZE + (int) (Math.random() * (Constants.MAX_ROCK_SIZE - Constants.MIN_ROCK_SIZE))) / 2;
					x = (int) -Math.round(r * Math.sin(theta));
					y = (int) Math.round(r * Math.cos(theta));
					asteroids[i].shape.addPoint(x, y);
				}
				asteroids[i].active = true;
				asteroids[i].angle = 0.0;
				asteroids[i].deltaAngle = Math.random() * 2
						* Constants.MAX_ROCK_SPIN - Constants.MAX_ROCK_SPIN;
				asteroids[i].x = tempX;
				asteroids[i].y = tempY;
				asteroids[i].deltaX = Math.random() * 2 * asteroidsSpeed
						- asteroidsSpeed;
				asteroids[i].deltaY = Math.random() * 2 * asteroidsSpeed
						- asteroidsSpeed;
				asteroids[i].render(width, height);
				asteroidIsSmall[i] = true;
				count++;
				asteroidsLeft++;
			}
			i++;
		} while (i < Constants.MAX_ROCKS && count < 2);
	}

	public void stopUfo() {

		ufo.active = false;
		ufoCounter = 0;
		ufoPassesLeft = 0;
		if (loaded)
			saucerSound.stop();
		saucerPlaying = false;
	}

	public void stopMissle() {

		missile.active = false;
		missileCounter = 0;
		if (loaded)
			missileSound.stop();
		missilePlaying = false;
	}

	public void initExplosions() {

		int i;

		for (i = 0; i < Constants.MAX_SCRAP; i++) {
			explosions[i].shape = new Polygon();
			explosions[i].active = false;
			explosionCounter[i] = 0;
		}
		explosionIndex = 0;
	}

	public void initUfo() {
		int width = getWidth();
		int height = getHeight();

		double angle, speed;

		// Randomly set flying saucer at left or right edge of the screen.

		ufo.active = true;
		ufo.x = -width / 2;
		ufo.y = (int) (Math.random() * 2 * height - height);
		angle = Math.random() * Math.PI / 4 - Math.PI / 2;
		speed = Constants.MAX_ROCK_SPEED / 2 + Math.random()
				* (Constants.MAX_ROCK_SPEED / 2);
		ufo.deltaX = speed * -Math.sin(angle);
		ufo.deltaY = speed * Math.cos(angle);
		if (Math.random() < 0.5) {
			ufo.x = width / 2;
			ufo.deltaX = -ufo.deltaX;
		}
		if (ufo.y > 0)
			ufo.deltaY = ufo.deltaY;
		ufo.render(width, height);
		saucerPlaying = true;
		if (sound)
			saucerSound.loop();
		ufoCounter = (int) Math.abs(width / ufo.deltaX);
	}

	public void updateUfo() {
		int w = getWidth();
		int h = getHeight();

		int i, d;
		// boolean wrapped;

		// Move the flying saucer and check for collision with a photon. Stop it
		// when its counter has expired.

		if (ufo.active) {
			if (--ufoCounter <= 0) {
				if (--ufoPassesLeft > 0)
					initUfo();
				else
					stopUfo();
			}
			if (ufo.active) {
				ufo.advance(w, h);
				ufo.render(w, h);
				for (i = 0; i < Constants.MAX_SHOTS; i++)
					if (photons[i].active && ufo.isColliding(photons[i])) {
						if (sound)
							crashSound.play();
						explode(ufo, w, h);
						stopUfo();
						score += Constants.UFO_POINTS;
					}

				// On occassion, fire a missile at the ship if the saucer is not
				// too
				// close to it.

				d = (int) Math.max(Math.abs(ufo.x - ship.x), Math.abs(ufo.y
						- ship.y));
				if (ship.active && hyperCounter <= 0 && ufo.active
						&& !missile.active
						&& d > Constants.MAX_ROCK_SPEED * Constants.FPS / 2
						&& Math.random() < Constants.MISSLE_PROBABILITY)
					initMissle();
			}
		}
	}

	public void initMissle() {
		int w = getWidth();
		int h = getHeight();

		missile.active = true;
		missile.angle = 0.0;
		missile.deltaAngle = 0.0;
		missile.x = ufo.x;
		missile.y = ufo.y;
		missile.deltaX = 0.0;
		missile.deltaY = 0.0;
		missile.render(w, h);
		missileCounter = Constants.MISSLE_COUNT;
		if (sound)
			missileSound.loop();
		missilePlaying = true;
	}

	public void updateMissle() {
		int w = getWidth();
		int h = getHeight();

		int i;

		// Move the guided missile and check for collision with ship or photon.
		// Stop
		// it when its counter has expired.

		if (missile.active) {
			if (--missileCounter <= 0)
				stopMissle();
			else {
				guideMissle();
				missile.advance(w, h);
				missile.render(w, h);
				for (i = 0; i < Constants.MAX_SHOTS; i++)
					if (photons[i].active && missile.isColliding(photons[i])) {
						if (sound)
							crashSound.play();
						explode(missile, w, h);
						stopMissle();
						score += Constants.MISSLE_POINTS;
					}
				if (missile.active && ship.active && hyperCounter <= 0
						&& ship.isColliding(missile)) {
					if (sound)
						crashSound.play();
					explode(ship, w, h);
					stopShip();
					stopUfo();
					stopMissle();
				}
			}
		}
	}

	public void guideMissle() {

		double dx, dy, angle;

		if (!ship.active || hyperCounter > 0)
			return;

		// Find the angle needed to hit the ship.

		dx = ship.x - missile.x;
		dy = ship.y - missile.y;
		if (dx == 0 && dy == 0)
			angle = 0;
		if (dx == 0) {
			if (dy < 0)
				angle = -Math.PI / 2;
			else
				angle = Math.PI / 2;
		} else {
			angle = Math.atan(Math.abs(dy / dx));
			if (dy > 0)
				angle = -angle;
			if (dx < 0)
				angle = Math.PI - angle;
		}

		// Adjust angle for screen coordinates.

		missile.angle = angle - Math.PI / 2;

		// Change the missile's angle so that it points toward the ship.

		missile.deltaX = 0.75 * Constants.MAX_ROCK_SPEED
				* -Math.sin(missile.angle);
		missile.deltaY = 0.75 * Constants.MAX_ROCK_SPEED
				* Math.cos(missile.angle);
	}

	@Override
	protected void updatePhysics() {
		updateShip();
		updatePhotons();
		updateUfo();
		updateMissle();
		updateAsteroids();
		updateExplosions();

		// Check the score and advance high score,  
		if (score > highScore)
			highScore = score;
		
		// add a new ship if score reaches Constants.NEW_SHIP_POINTS
		if (score > newShipScore) {
			newShipScore += Constants.NEW_SHIP_POINTS;
			shipsLeft++;
		}
		
		// start the flying saucer as necessary.		
		if (playing && score > newUfoScore && ! ufo.active) {
			newUfoScore += Constants.NEW_UFO_POINTS;
			ufoPassesLeft = Constants.UFO_PASSES;
			initUfo();
		}

		// If all asteroids have been destroyed create a new batch.
		if (asteroidsLeft <= 0)
			if (--asteroidsCounter <= 0)
				initAsteroids();
		
	}

	@Override
	protected boolean gameOver() {
		return loaded && !playing;
	}
	
	@Override
	protected long getScore() {
		return score;
	}
}
