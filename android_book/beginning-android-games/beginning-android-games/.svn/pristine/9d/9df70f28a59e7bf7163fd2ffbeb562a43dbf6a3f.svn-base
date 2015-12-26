package com.badlogic.androidgames.droidinvaders;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.droidinvaders.World.WorldListener;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameScreen extends GLScreen {
	static final int GAME_RUNNING = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_OVER = 2;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle shotBounds;
	int lastScore;
	int lastLives;
	int lastWaves;
	String scoreString;
	FPSCounter fpsCounter;

	public GameScreen(Game game) {
		super(game);

		state = GAME_RUNNING;
		guiCam = new Camera2D(glGraphics, 480, 320);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 100);
		world = new World();
		worldListener = new WorldListener() {
			@Override
			public void shot() {
				Assets.playSound(Assets.shotSound);
			}

			@Override
			public void explosion() {
				Assets.playSound(Assets.explosionSound);
			}
		};
		world.setWorldListener(worldListener);
		renderer = new WorldRenderer(glGraphics);
		pauseBounds = new Rectangle(480 - 64, 320 - 64, 64, 64);
		resumeBounds = new Rectangle(240 - 80, 160, 160, 32);
		quitBounds = new Rectangle(240 - 80, 160 - 32, 160, 32);
		shotBounds = new Rectangle(480 - 64, 0, 64, 64);
		leftBounds = new Rectangle(0, 0, 64, 64);
		rightBounds = new Rectangle(64, 0, 64, 64);
		lastScore = 0;
		lastLives = world.ship.lives;
		lastWaves = world.waves;
		scoreString = "lives:" + lastLives + " waves:" + lastWaves + " score:"
				+ lastScore;
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {
		switch (state) {
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updatePaused() {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
			}

			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_DOWN)
				continue;

			guiCam.touchToWorld(touchPoint.set(event.x, event.y));

			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
			}
			if (OverlapTester.pointInRectangle(shotBounds, touchPoint)) {
				world.shot();
			}
		}

		world.update(deltaTime, calculateInputAcceleration());
		if (world.ship.lives != lastLives || world.score != lastScore
				|| world.waves != lastWaves) {
			lastLives = world.ship.lives;
			lastScore = world.score;
			lastWaves = world.waves;
			scoreString = "lives:" + lastLives + " waves:" + lastWaves
					+ " score:" + lastScore;
		}
		if (world.isGameOver()) {
			state = GAME_OVER;
		}
	}

	private float calculateInputAcceleration() {
		float accelX = 0;
		if (Settings.touchEnabled) {
			for (int i = 0; i < 2; i++) {
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					if (OverlapTester.pointInRectangle(leftBounds, touchPoint)) {
						accelX = -Ship.SHIP_VELOCITY / 5;
					}
					if (OverlapTester.pointInRectangle(rightBounds, touchPoint)) {
						accelX = Ship.SHIP_VELOCITY / 5;
					}
				}
			}
		} else {
			accelX = game.getInput().getAccelY();
		}
		return accelX;
	}

	private void updateGameOver() {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		batcher.endBatch();
		gl.glDisable(GL10.GL_TEXTURE_2D);

		renderer.render(world, deltaTime);

		switch (state) {
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_OVER:
			presentGameOver();
		}

		fpsCounter.logFrame();
	}

	private void presentPaused() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.items);
		Assets.font.drawText(batcher, scoreString, 10, 320 - 20);
		batcher.drawSprite(240, 160, 160, 64, Assets.pauseRegion);
		batcher.endBatch();

		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentRunning() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.items);
		batcher.drawSprite(480 - 32, 320 - 32, 64, 64, Assets.pauseButtonRegion);
		Assets.font.drawText(batcher, scoreString, 10, 320 - 20);
		if (Settings.touchEnabled) {
			batcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
			batcher.drawSprite(96, 32, 64, 64, Assets.rightRegion);
		}
		batcher.drawSprite(480 - 40, 32, 64, 64, Assets.fireRegion);
		batcher.endBatch();

		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentGameOver() {
		GL10 gl = glGraphics.getGL();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.items);
		batcher.drawSprite(240, 160, 128, 64, Assets.gameOverRegion);
		Assets.font.drawText(batcher, scoreString, 10, 320 - 20);
		batcher.endBatch();

		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		state = GAME_PAUSED;
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
