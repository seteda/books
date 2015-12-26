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

public class MainMenuScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle playBounds;
	Rectangle settingsBounds;

	public MainMenuScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, 480, 320);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();
		playBounds = new Rectangle(240 - 112, 100, 224, 32);
		settingsBounds = new Rectangle(240 - 112, 100 - 32, 224, 32);
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
			if (OverlapTester.pointInRectangle(playBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
			}
			if (OverlapTester.pointInRectangle(settingsBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new SettingsScreen(game));
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
		batcher.drawSprite(240, 240, 384, 128, Assets.logoRegion);
		batcher.drawSprite(240, 100, 224, 64, Assets.menuRegion);
		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
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
