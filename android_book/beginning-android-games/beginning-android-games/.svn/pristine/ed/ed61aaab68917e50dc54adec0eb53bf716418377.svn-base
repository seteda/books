package com.badlogic.androidgames.droidinvaders;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class SettingsScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle touchBounds;
	Rectangle accelBounds;
	Rectangle soundBounds;
	Rectangle backBounds;

	public SettingsScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();

		touchBounds = new Rectangle(120 - 32, 160 - 32, 64, 64);
		accelBounds = new Rectangle(240 - 32, 160 - 32, 64, 64);
		soundBounds = new Rectangle(360 - 32, 160 - 32, 64, 64);
		backBounds = new Rectangle(32, 32, 64, 64);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			if (OverlapTester.pointInRectangle(touchBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = true;
				Settings.save(game.getFileIO());
			}
			if (OverlapTester.pointInRectangle(accelBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.touchEnabled = false;
				Settings.save(game.getFileIO());
			}
			if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled) {
					Assets.music.play();
				} else {
					Assets.music.pause();
				}
				Settings.save(game.getFileIO());
			}
			if (OverlapTester.pointInRectangle(backBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		batcher.drawSprite(240, 280, 224, 32, Assets.settingsRegion);
		batcher.drawSprite(120, 160, 64, 64,
				Settings.touchEnabled ? Assets.touchEnabledRegion
						: Assets.touchRegion);
		batcher.drawSprite(240, 160, 64, 64,
				Settings.touchEnabled ? Assets.accelRegion
						: Assets.accelEnabledRegion);
		batcher.drawSprite(360, 160, 64, 64,
				Settings.soundEnabled ? Assets.soundEnabledRegion
						: Assets.soundRegion);
		batcher.drawSprite(32, 32, 64, 64, Assets.leftRegion);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
