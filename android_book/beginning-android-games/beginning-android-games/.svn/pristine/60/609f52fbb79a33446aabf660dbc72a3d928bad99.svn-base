package com.badlogic.androidgames.gladvanced;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.EulerCamera;
import com.badlogic.androidgames.framework.gl.ObjLoader;
import com.badlogic.androidgames.framework.gl.PointLight;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.gl.Vertices3;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.androidgames.framework.math.Vector3;

public class ObjTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new ObjScreen(this);
	}

	class ObjScreen extends GLScreen {

		Texture crateTexture;
		Vertices3 cube;
		PointLight light;
		EulerCamera camera;
		Texture buttonTexture;
		SpriteBatcher batcher;
		Camera2D guiCamera;
		TextureRegion buttonRegion;
		Vector2 touchPos;
		float lastX = -1;
		float lastY = -1;

		public ObjScreen(Game game) {
			super(game);

			crateTexture = new Texture(glGame, "crate.png", true);
			cube = ObjLoader.load(glGame, "cube.obj");
			light = new PointLight();
			light.setPosition(3, 3, -3);
			camera = new EulerCamera(67, glGraphics.getWidth()
					/ (float) glGraphics.getHeight(), 1, 100);
			camera.getPosition().set(0, 1, 3);

			buttonTexture = new Texture(glGame, "button.png");
			batcher = new SpriteBatcher(glGraphics, 1);
			guiCamera = new Camera2D(glGraphics, 480, 320);
			buttonRegion = new TextureRegion(buttonTexture, 0, 0, 64, 64);
			touchPos = new Vector2();
		}

		@Override
		public void resume() {
			crateTexture.reload();
		}

		@Override
		public void update(float deltaTime) {
			game.getInput().getTouchEvents();
			float x = game.getInput().getTouchX(0);
			float y = game.getInput().getTouchY(0);
			guiCamera.touchToWorld(touchPos.set(x, y));

			if (game.getInput().isTouchDown(0)) {
				if (touchPos.x < 64 && touchPos.y < 64) {
					Vector3 direction = camera.getDirection();
					camera.getPosition().add(direction.mul(deltaTime));
				} else {
					if (lastX == -1) {
						lastX = x;
						lastY = y;
					} else {
						camera.rotate((x - lastX) / 10, (y - lastY) / 10);
						lastX = x;
						lastY = y;
					}
				}
			} else {
				lastX = -1;
				lastY = -1;
			}
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());

			camera.setMatrices(gl);

			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_LIGHTING);

			crateTexture.bind();
			cube.bind();
			light.enable(gl, GL10.GL_LIGHT0);

			for (int z = 0; z >= -8; z -= 2) {
				for (int x = -4; x <= 4; x += 2) {
					gl.glPushMatrix();
					gl.glTranslatef(x, 0, z);
					cube.draw(GL10.GL_TRIANGLES, 0, cube.getNumVertices());
					gl.glPopMatrix();
				}
			}

			cube.unbind();

			gl.glDisable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_DEPTH_TEST);

			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

			guiCamera.setViewportAndMatrices();
			batcher.beginBatch(buttonTexture);
			batcher.drawSprite(32, 32, 64, 64, buttonRegion);
			batcher.endBatch();

			gl.glDisable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void pause() {

		}

		@Override
		public void dispose() {
		}
	}
}
