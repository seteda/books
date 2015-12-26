package ch04.game;

public class Constants {
	  // Constants

	  static final int DELAY = 25;             // Milliseconds between screen and
	  static final int FPS   =                 // the resulting frame rate.
	    Math.round(1000 / DELAY);

	  static final int MAX_SHOTS =  8;          // Maximum number of sprites
	  static final int MAX_ROCKS =  8;          // for photons, asteroids and
	  static final int MAX_SCRAP = 40;          // explosions.

	  static final int SCRAP_COUNT  = 2 * FPS;  // Timer counter starting values
	  static final int HYPER_COUNT  = 3 * FPS;  // calculated using number of
	  static final int MISSLE_COUNT = 4 * FPS;  // seconds x frames per second.
	  static final int STORM_PAUSE  = 2 * FPS;

	  static final int    MIN_ROCK_SIDES =   6; // Ranges for asteroid shape, size
	  static final int    MAX_ROCK_SIDES =  16; // speed and rotation.
	  static final int    MIN_ROCK_SIZE  =  20;
	  static final int    MAX_ROCK_SIZE  =  40;
	  static final double MIN_ROCK_SPEED =  40.0 / FPS;
	  static final double MAX_ROCK_SPEED = 240.0 / FPS;
	  static final double MAX_ROCK_SPIN  = Math.PI / FPS;

	  static final int MAX_SHIPS = 3;           // Starting number of ships for
	                                            // each game.
	  static final int UFO_PASSES = 3;          // Number of passes for flying
	                                            // saucer per appearance.

	  // Ship's rotation and acceleration rates and maximum speed.

	  static final double SHIP_ANGLE_STEP = Math.PI / FPS;
	  static final double SHIP_SPEED_STEP = 15.0 / FPS;
	  static final double MAX_SHIP_SPEED  = 1.25 * MAX_ROCK_SPEED;

	  static final int FIRE_DELAY = 50;         // Minimum number of milliseconds
	                                            // required between photon shots.

	  // Probablility of flying saucer firing a missile during any given frame
	  // (other conditions must be met).

	  static final double MISSLE_PROBABILITY = 0.45 / FPS;

	  static final int BIG_POINTS    =  25;     // Points scored for shooting
	  static final int SMALL_POINTS  =  50;     // various objects.
	  static final int UFO_POINTS    = 250;
	  static final int MISSLE_POINTS = 500;

	  // Number of points the must be scored to earn a new ship or to cause the
	  // flying saucer to appear.

	  static final int NEW_SHIP_POINTS = 5000;
	  static final int NEW_UFO_POINTS  = 2750;

}
