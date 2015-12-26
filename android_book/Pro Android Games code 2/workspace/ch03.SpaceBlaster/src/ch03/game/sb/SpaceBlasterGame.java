package ch03.game.sb;

import ch03.common.AudioClip;
import ch03.common.Tools;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class SpaceBlasterGame extends ArcadeGame 
{ 
	// Game name
	public static final String NAME = "SpaceBlaster";
	
	// Refresh rate (ms)
	private static final long UPDATE_DELAY = 40; 

	private Context mContext;

	// For text
	private Paint mTextPaint = new Paint();

	// For Bitmaps
	private Paint mBitmapPaint = new Paint();

	private Paint mLaserBarPaint = new Paint();
	private Paint mShieldBarPaint = new Paint();
	private Paint mShieldPaint = new Paint();
	
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public SpaceBlasterGame(Context context) {
		super(context);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}

	public SpaceBlasterGame(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		super.setUpdatePeriod(UPDATE_DELAY);
	}


	/**
	 * Android Key events
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		keyUp(keyCode); // event, 
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		keyDown(keyCode); // event, 
		return true;
	}
	
	/**
	 * Main Draw Sub
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.dispatchDraw(canvas);
		paint(canvas);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int tx = (int)event.getX();
		int ty = (int)event.getY();
//		System.out.println("tx=" + tx 
//				+ " ty=" + ty + " ship x=" + x + " y=" + y 
//				+ " ship wh=" + ship.getWidth() + " " + ship.getHeight()
//				+ " edt=" + event.getDownTime() +  " ep=" + event.getPressure());

		
		// Has the ship been touched. if so move it
		if ( tx >= x && tx <= x + ship.getWidth()
			 && ty >= y && ty <= y + ship.getHeight()){
			x = tx - (ship.getWidth()/2); 
			y = ty - (ship.getHeight()/2);
			
		}
		// else start game if not already started
		else if ( event.getAction() == MotionEvent.ACTION_UP)
		{
			if ( !ingame ) {
				ingame = true;
				GameStart();
			}
			else {
				// fire gun
				keyDown(KeyEvent.KEYCODE_SPACE);
			}
		}
		return true;
	}

	/*************************************************************
	 * GAME Code
	 ************************************************************/
	boolean ingame = false;

	int x, y, mousex, mousey, oldx, oldy, dx = 0, dy = 0, count, shield = 0;
	boolean showtitle = true;
	Bitmap ship;
	Bitmap[] fire;
	int firecnt = 0;

	// Bullet variables
	Bitmap bullet;
	int[] bx;
	int[] by;
	final int bmy = 16, bul_xs = 54, bul_ys = 8;

	// Meteor variables
	Bitmap meteor;
	int maxmet, metcount, mtotal, mrenew, metmy;
	int[] metx;
	int[] mety;
	int[] metf;
	boolean[] metr;
	final int sxmet = 80, symet = 84;

	// These are for the star field
	public int starsX[];
	public int starsY[];
	public Paint starsC[];
	public int numStars = 30;
	public int speed = 6, xSize, ySize;

	// Variables for big boom
	Bitmap[] boom;
	int rndbx, rndby, rndcnt = 777;
	final int sxbom = 71, sybom = 100, bframes = 4;

	// Global Variables
	int distance = 0, maxdist = 2000;
	int slevel, blevel, difflev, bosslevel;
	int smax, bmax;
	int scur, bcur, renew, rcnt = 0, sstretch, txtalign = 100;
	long score;

	// Sounds
	AudioClip blast, crash, kill;

	// Bosses here
	// Sunbird
	boolean sunbird, sbefore, safter;
	int sbx, sby, sbmove, maxtribe, tribe;
	int[] sbfx, sbfy;

	// Game Stuff
	final int maxshield = 9;
	final int backcol = 0x102040;
	final int fireframe = 2;
	final int borderwidth = 0;
	final int sxsize = 90, sysize = 39, sxfire = 11, syfire = 6;
	final int movex = 10, movey = 5;
	final int scoreheight = 45;
	final int screendelay = 300;

	/**
	 * Overload to initialize game elements
	 */
	public void initialize() {
		int n;
		
		// Screen size
		int width = getWidth();
		int height = getHeight();

		// Text Paints
		mTextPaint.setARGB(255, 255, 255, 255);
		mShieldPaint.setARGB(125, 0, 255, 255);
		
		// Laser Bar Energy
		mLaserBarPaint.setARGB(255, 0, 255, 96);
		mLaserBarPaint.setStyle(Paint.Style.FILL);
		
		// Shield Bar Energy
		mShieldBarPaint.setARGB(255, 0, 255, 255);
		mShieldBarPaint.setStyle(Paint.Style.FILL);
		
		ship = getImage(R.drawable.sb_ship); 
		bullet = getImage(R.drawable.sb_bullet); 
		fire = new Bitmap[fireframe];

		// Load fire image sprites
		int[] ids = new int[] { R.drawable.sb_fire0, R.drawable.sb_fire1 };

		for (n = 0; n < fireframe; n++) {
			fire[n] = getImage(ids[n]); 
		}

		// Load meteor explosion sequence sprites
		ids = new int[] { R.drawable.sb_boom0, R.drawable.sb_boom1,
				R.drawable.sb_boom2, R.drawable.sb_boom3,
				R.drawable.sb_boom4 };

		boom = new Bitmap[bframes + 1];
		
		for (n = 0; n <= bframes; n++) {
			boom[n] = getImage(ids[n]); 
		}

		xSize = width - borderwidth * 2;
		ySize = height - borderwidth * 2 - scoreheight;

		x = (xSize - sxsize) / 2;
		y = ySize - sysize - scoreheight - borderwidth;
		mousex = -1;

		blevel = 3;
		slevel = 3;

		bx = new int[blevel * 10];
		by = new int[blevel * 10];

		for (n = 0; n < blevel * 10; n++) {
			bx[n] = -1;
		}

		// Meteor initialize
		meteor = getImage(R.drawable.sb_meteor); 

		maxmet = height / symet + 1;
		maxmet = maxmet * 10;
		metx = new int[maxmet];
		mety = new int[maxmet];
		metf = new int[maxmet];
		metr = new boolean[maxmet];

		// Load Audio clips
		try {
			blast = getAudioClip(R.raw.sb_blast); 
			crash = getAudioClip(R.raw.sb_collisn); 
			kill = getAudioClip(R.raw.sb_mdestr); 
		} catch (Exception e) {
			Tools.MessageBox(mContext, "Audio Error: " + e.toString());
		}

		initStars();

		rndcnt = 777;

		// Bosses
		// Sunbird
		sbfx = new int[11];
		sbfy = new int[11];
		sbfx[0] = 10;
		sbfy[0] = 0;
		sbfx[1] = 15;
		sbfy[1] = 10;
		sbfx[2] = 0;
		sbfy[2] = 10;
		sbfx[3] = 3;
		sbfy[3] = 15;
		sbfx[4] = 17;
		sbfy[4] = 15;
		sbfx[5] = 20;
		sbfy[5] = 20;
		sbfx[6] = 23;
		sbfy[6] = 15;
		sbfx[7] = 37;
		sbfy[7] = 15;
		sbfx[8] = 40;
		sbfy[8] = 10;
		sbfx[9] = 25;
		sbfy[9] = 10;
		sbfx[10] = 30;
		sbfy[10] = 0;

	}

	/**
	 * create the starfield in the background
	 */
	public void initStars() {
		starsX = new int[numStars];
		starsY = new int[numStars];
		starsC = new Paint[numStars];
		for (int i = 0; i < numStars; i++) {
			starsX[i] = (int) ((Math.random() * xSize - 1) + 1);
			starsY[i] = (int) ((Math.random() * ySize - 1) + 1);
			starsC[i] = newColor();
		}
	}

	/**
	 * Process key down event
	 * @param key Android Key code
	 * @return
	 */
	public boolean keyDown( int key) { 
		if (ingame) {
			mousex = -1;
			if (key == KeyEvent.KEYCODE_DPAD_LEFT || key == KeyEvent.KEYCODE_Q)
				dx = -1;
			if (key == KeyEvent.KEYCODE_DPAD_RIGHT || key == KeyEvent.KEYCODE_W)
				dx = 1;
			if (key == KeyEvent.KEYCODE_DPAD_UP || key == KeyEvent.KEYCODE_O)
				dy = -1;
			if (key == KeyEvent.KEYCODE_DPAD_DOWN || key == KeyEvent.KEYCODE_L)
				dy = 1;
			if ( (key ==  KeyEvent.KEYCODE_SPACE) || (key == 23) ) {
				if (bcur > 0) {
					fireGun();
				}
			}
		} else {
			if (key == KeyEvent.KEYCODE_S) { 
				ingame = true;
				GameStart();
			}
		}
		if (key == KeyEvent.KEYCODE_E){ 
			ingame = false;
			//stopUpdateTimer();
		}
		
		if (key == KeyEvent.KEYCODE_Q){
			// Arggg!! There should be a better wayt to quit! 
			System.exit(0);
		}		
		return true;
	}

	/**
	 * Process key up event
	 * @param e Key event
	 * @param key key code
	 * @return
	 */
	public boolean keyUp(int key) { // KeyEvent e, 
		if (key == KeyEvent.KEYCODE_DPAD_LEFT
				|| key == KeyEvent.KEYCODE_DPAD_RIGHT
				|| key == KeyEvent.KEYCODE_Q
				|| key == KeyEvent.KEYCODE_W)
			dx = 0;
		if (key == KeyEvent.KEYCODE_DPAD_UP
				|| key == KeyEvent.KEYCODE_DPAD_DOWN
				|| key == KeyEvent.KEYCODE_O
				|| key == KeyEvent.KEYCODE_L)
			dy = 0;
		return true;
	}

	public void paint(Canvas g) {
		if (ingame)
			playGame(g);
		else
			showIntroScreen(g);
	}

	public void playGame(Canvas c) {
		newMeteor();
		moveShip();
		drawPlayField(c);

		// Big bosses here
		if (sunbird)
			SunBird(c);

		showScore(c);
		distance++;
		score += 100;
		if (distance % maxdist == 0) {
			difflev++;
			if (difflev > 2 & difflev < 10) {
				renew -= 20;
				bmax += 1;
				smax += 1;
				metmy++;
				mrenew--;
			}
			if (difflev > 3 & difflev < 11) {
				maxtribe++;
				sbmove++;
			}
			if (difflev > 3) {
				sunbird = true;
				tribe = maxtribe;
			}
		}

		// Renew Ship Energy
		rcnt++;
		if (rcnt % (renew / blevel) == 0) {
			bcur++;
			if (bcur > bmax)
				bcur = bmax;
		}
		if (distance % 500 == 0) {
			scur++;
			if (scur > smax)
				scur = smax;
		}
		if (rcnt > renew)
			rcnt = 0;
	}

	public void showIntroScreen(Canvas canvas) {
		String s;
		int width = getWidth();
		int height = getHeight();
		
		drawPlayField(canvas);

		if (rndcnt > bframes) {
			rndbx = (int) (Math.random() * (xSize - sxbom) + 1);
			rndby = (int) (Math.random() * (ySize - sybom) + 1);
			rndcnt = 0;
		}

		canvas.drawBitmap(boom[rndcnt], rndbx, rndby, mBitmapPaint);
		rndcnt++;
//		for (int i = 0; i < xSize / bul_xs; i++) {
//			goff.drawBitmap(bullet, i * bul_xs, 0, mBitmapPaint);
//			goff.drawBitmap(bullet, i * bul_xs, ySize - bul_ys, mBitmapPaint);
//		}

		s = "Game Over"; 
		
		canvas.drawText(s
					, (width - (s.length() * mTextPaint.getTextSize()/2) ) / 2
					, (height - scoreheight - borderwidth)/2 - 20, mTextPaint);
		count--;
		if (count <= 0) {
			count = screendelay;
			showtitle = !showtitle;
		}
	}

	public void drawPlayField(Canvas canvas) {

		// Show stars
		moveStars();
		for (int a = 0; a < numStars; a++) {
			// canvas.setColor(starsC[a]);
			// Draw starts
			canvas.drawRect(starsX[a], starsY[a], starsX[a] + 2, starsY[a] + 2,
					starsC[a]);
		}

		showMeteors(canvas);
		KillEmAll(canvas);
		canvas.drawBitmap(ship, x, y, mBitmapPaint); // paint ship

		if (firecnt != 0) {
			canvas.drawBitmap(fire[firecnt - 1], x + ((sxsize - sxfire) / 2), y
					+ sysize, mBitmapPaint); // engine fire
		}
		firecnt++;
		if (firecnt > 2)
			firecnt = 0;
		
		processCollisions();

		if (shield > 0) {
			float left = x - shield;
			float top = y - shield;
			float right = left + sxsize + shield * 2;
			float bottom = top + sysize + shield * 2;
			canvas.drawOval(new RectF(left, top, right, bottom), mShieldPaint);
			shield--;
		}
	}

	/**
	 * Show the laser shield and score values in the lower part of the screen
	 * @param goff
	 */
	public void showScore(Canvas goff) {
		String s;
		int my;
		int height = getHeight();

		sstretch = (xSize - txtalign * 2) / Math.max(bmax, smax);
		// Laser bar
		my = height - scoreheight + 10;

		goff.drawRect(txtalign, my - 10, txtalign + bmax * sstretch, my, mTextPaint);

		// goff.setFont(smallfont);
		s = "Laser: " + bcur + "/" + bmax;

		// fill rect
		goff.drawRect(txtalign, my - 10, txtalign + bcur * sstretch, my, mLaserBarPaint);
		goff.drawText(s, 10, my, mTextPaint);

		// Shield bar
		my += 15;
		goff.drawRect(txtalign, my - 10, txtalign + smax * sstretch, my, mTextPaint);

		// Fill rect
		s = "Shield: " + scur + "/" + smax;
		goff.drawRect(txtalign, my - 10, txtalign + scur * sstretch, my, mShieldBarPaint);
		goff.drawText(s, 10, my, mTextPaint);

		// Score
		my += 20;
		
		s = "Score: " + score;
		goff.drawText(s, 10, my, mTextPaint);
	}

	public void moveShip() {
		int width = getWidth();
		int height = getHeight();

		int xx, yy;
		oldx = x;
		oldy = y;

		xx = mousex;
		if (xx > 0) {
			yy = mousey;
			if (xx < x)
				dx = -1;
			if (xx > x + sxsize)
				dx = 1;
			if (yy < y)
				dy = -1;
			if (yy > y + sysize)
				dy = 1;
			//if (xx > x & xx < x + sxsize & yy > y & yy < y + sysize) {
			if (xx > x && xx < x + sxsize && yy > y && yy < y + sysize) {
				dx = 0;
				dy = 0;
				mousex = -1;
			}
		}

		x += dx * movex;
		y += dy * movey;

		if (y <= borderwidth || y >= (height - sysize - scoreheight)) {
			dy = 0;
			y = oldy;
		}
		if (x >= (width - borderwidth - sxsize) || x <= borderwidth) {
			dx = 0;
			x = oldx;
		}
	}

	public void fireGun() {
		int n = 0, f = -1;
		while (n < blevel * 10 && bx[n] >= 0)
			n++;
		if (n < blevel * 10)
			f = n;
		if (f >= 0) {
			bx[f] = x + ((sxsize - bul_xs) / 2);
			by[f] = y;
			bcur--;
			blast.play();
		}
	}

	public void KillEmAll(Canvas canvas) {
		for (int n = 0; n < blevel * 10; n++) {
			if (bx[n] > 0) {
				by[n] -= bmy;
				if (by[n] < borderwidth | MetHit(n) | BirdHit(bx[n], by[n])) {
					bx[n] = -1;
				} else {
					canvas.drawBitmap(bullet, bx[n], by[n], mBitmapPaint); 
				}
			}
		}
	}

	public boolean MetHit(int f) {
		for (int n = 0; n < maxmet; n++) {
			if (metx[n] >= 0) {
				if (metr[n] & bx[f] + bul_xs > metx[n]
						& bx[f] < metx[n] + sxmet & by[f] + bul_ys > mety[n]
						& by[f] < mety[n] + symet) {
					deleteMeteor(n);
					kill.play();
					return true;
				}
			}
		}
		return false;
	}

	public void showMeteors(Canvas goff) {
		int n;
		int height = getHeight();

		mtotal = 0;
		for (n = 0; n < maxmet; n++) {
			if (metx[n] >= 0) {
				mtotal++;
				mety[n] += metmy;
				if (mety[n] > height - borderwidth - scoreheight) {
					deleteMeteor(n);
				} else {
					if (metr[n]) {
						goff.drawBitmap(meteor, metx[n], mety[n], mBitmapPaint); 
						// meteor
					} else {
						goff.drawBitmap(boom[bframes - metf[n]], metx[n]
								+ (sxmet - sxbom) / 2, mety[n]
								+ (symet - sybom) / 2, mBitmapPaint); 
																		
						metf[n]--;
						if (metf[n] < 0)
							deleteMeteor(n);
					}
				}
			}
		}
	}

	public void newMeteor() {
		int n = 0, f = -1;
		metcount++;
		if (metcount > mrenew / metmy) {
			metcount = 0;
			while (n < maxmet & metx[n] >= 0)
				n++;
			if (n < maxmet)
				f = n;
			if (f >= 0) {
				metx[f] = (int) (Math.random() * (xSize - sxmet) + 1);
				mety[f] = borderwidth - symet;
				metr[f] = true;
				metf[f] = bframes;
			}
		}
	}

	// If a star in the background reaches the bottom then it will go back to
	// the top
	public void moveStars() {
		for (int i = 0; i < numStars; i++) {
			if (starsY[i] + 1 > ySize - (speed * 2)) {
				starsY[i] = 0;
				starsX[i] = (int) ((Math.random() * xSize - 1) + 1);
				starsC[i] = newColor();
			} else {
				starsY[i] += speed;
			}
		}
	}

	public void processCollisions() {

		for (int n = 0; n < maxmet; n++) {
			if (metx[n] >= 0) {
				if (metr[n] & x + sxsize > metx[n] & x < metx[n] + sxmet
						& y + sysize > mety[n] & y < mety[n] + symet) {
					hitShip();
					deleteMeteor(n);
				}
			}
		}

	}

	public void hitShip() {
		crash.play();
		shield = maxshield;
		scur--;
		if (scur < 0)
			GameOver();
	}

	public void deleteMeteor(int n) {
		if (metr[n]) {
			metr[n] = false;
			metf[n] = bframes;
		} else {
			metx[n] = -1;
			metr[n] = true;
			metf[n] = 0;
		}
	}

	public Paint newColor() {
		int[] rgb;
		int t;
		rgb = new int[3];
		for (int i = 0; i < 3; i++)
			rgb[i] = 0;
		t = (int) (Math.random() * 3);
		rgb[t] = (int) (Math.random() * 128 + 1) + 127;
		Paint p = new Paint();
		p.setARGB(255, rgb[0], rgb[1], rgb[2]);
		return p; 
	}

	// Game Start
	public void GameStart() {
		// Set Up Ship variables
		bmax = blevel * blevel;
		bcur = bmax;
		smax = slevel * slevel;
		scur = smax;
		difflev = 3;
		distance = 0;
		score = 0;
		renew = 250;
		for (int n = 0; n < maxmet; n++) {
			metx[n] = -1;
			metf[n] = 0;
			metr[n] = true;
		}
		metcount = 0;
		metmy = 2;
		mrenew = 60;

		// Bosses init
		// #1 - SunBird;
		sbx = -1;
		sbmove = 2;
		maxtribe = 1;
		sunbird = false;
		sbefore = true;
		safter = false;
	}

	// Game Over
	public void GameOver() {
		ingame = false;
	}

	// Boss #1 - Sunbird's pack
	public void SunBird(Canvas canvas) {
		int[] xcur, ycur;
		xcur = new int[11];
		ycur = new int[11];
		
		if (sbx < 0) {
			sbx = (int) ((Math.random() * xSize - 40) + 1);
			sby = -5;
			sbefore = true;
			safter = false;
		}
		sby += sbmove;
		if (y + sysize / 2 < sby)
			safter = true;
		
		//
		if (sbefore & safter) {
			// hit ship
			canvas.drawRect(0, sby + 15, xSize, sby + 17, mBitmapPaint);
			hitShip();
		}
		for (int i = 0; i < 11; i++) {
			xcur[i] = sbfx[i] + sbx;
			ycur[i] = sbfy[i] + sby;
		}
		
		// TODO: canvas.fillPolygon(xcur, ycur, 11);
		if (sby > xSize + 20) {
			sbx = -1;
			sbefore = true;
			safter = false;
		}
		sbefore = false;
		if (y + sysize / 2 > sby)
			sbefore = true;
	}

	public boolean BirdHit(int blx, int bly) {
		if (sunbird) {
			if (blx + bul_xs > sbx & blx < sbx + 40 & bly + bul_ys > sby
					& bly < sby + 20) {
				tribe--;
				if (tribe < 0)
					sunbird = false;
				sbx = -1;
				sbefore = true;
				safter = false;
				return true;
			}
		}
		return false;
	}

	@Override
	protected void updatePhysics() {
	}

	@Override
	protected boolean gameOver() {
		return ingame;
	}
	
	@Override
	protected long getScore() {
		return score;
	}
}
