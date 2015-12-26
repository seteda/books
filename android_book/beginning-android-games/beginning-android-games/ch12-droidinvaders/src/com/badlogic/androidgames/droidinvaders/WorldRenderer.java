package com.badlogic.androidgames.droidinvaders;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.AmbientLight;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.DirectionalLight;
import com.badlogic.androidgames.framework.gl.LookAtCamera;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.Vector3;

public class WorldRenderer {
	GLGraphics glGraphics;
	LookAtCamera camera;
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	SpriteBatcher batcher;
	float invaderAngle = 0;

	public WorldRenderer(GLGraphics glGraphics) {
		this.glGraphics = glGraphics;
		camera = new LookAtCamera(67, glGraphics.getWidth()
				/ (float) glGraphics.getHeight(), 0.1f, 100);
		camera.getPosition().set(0, 6, 2);
		camera.getLookAt().set(0, 0, -4);
		ambientLight = new AmbientLight();
		ambientLight.setColor(0.2f, 0.2f, 0.2f, 1.0f);
		directionalLight = new DirectionalLight();
		directionalLight.setDirection(-1, -0.5f, 0);
		batcher = new SpriteBatcher(glGraphics, 10);
	}

	public void render(World world, float deltaTime) {
		GL10 gl = glGraphics.getGL();
		camera.getPosition().x = world.ship.position.x;
		camera.getLookAt().x = world.ship.position.x;
		camera.setMatrices(gl);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);					
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
//		ambientLight.enable(gl);
		directionalLight.enable(gl, GL10.GL_LIGHT0);

		renderShip(gl, world.ship);
		renderInvaders(gl, world.invaders, deltaTime);

		gl.glDisable(GL10.GL_TEXTURE_2D);

		renderShields(gl, world.shields);
		renderShots(gl, world.shots);

		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);		
	}

	private void renderShip(GL10 gl, Ship ship) {
		if (ship.state == Ship.SHIP_EXPLODING) {
			gl.glDisable(GL10.GL_LIGHTING);
			renderExplosion(gl, ship.position, ship.stateTime);
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			Assets.shipTexture.bind();
			Assets.shipModel.bind();
			gl.glPushMatrix();
			gl.glTranslatef(ship.position.x, ship.position.y, ship.position.z);
			gl.glRotatef(ship.velocity.x / Ship.SHIP_VELOCITY * 90, 0, 0, -1);
			Assets.shipModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.shipModel.getNumVertices());
			gl.glPopMatrix();
			Assets.shipModel.unbind();
		}
	}

	private void renderInvaders(GL10 gl, List<Invader> invaders, float deltaTime) {
		invaderAngle += 45 * deltaTime;

		Assets.invaderTexture.bind();
		Assets.invaderModel.bind();
		int len = invaders.size();
		for (int i = 0; i < len; i++) {
			Invader invader = invaders.get(i);
			if (invader.state == Invader.INVADER_DEAD) {
				gl.glDisable(GL10.GL_LIGHTING);
				Assets.invaderModel.unbind();
				renderExplosion(gl, invader.position, invader.stateTime);
				Assets.invaderTexture.bind();
				Assets.invaderModel.bind();
				gl.glEnable(GL10.GL_LIGHTING);
			} else {
				gl.glPushMatrix();
				gl.glTranslatef(invader.position.x, invader.position.y,
						invader.position.z);
				gl.glRotatef(invaderAngle, 0, 1, 0);
				Assets.invaderModel.draw(GL10.GL_TRIANGLES, 0,
						Assets.invaderModel.getNumVertices());
				gl.glPopMatrix();
			}
		}
		Assets.invaderModel.unbind();
	}

	private void renderShields(GL10 gl, List<Shield> shields) {
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
		gl.glColor4f(0, 0, 1, 0.4f);
		Assets.shieldModel.bind();
		int len = shields.size();
		for (int i = 0; i < len; i++) {
			Shield shield = shields.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(shield.position.x, shield.position.y,
					shield.position.z);
			Assets.shieldModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.shieldModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.shieldModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);		
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderShots(GL10 gl, List<Shot> shots) {
		gl.glColor4f(1, 1, 0, 1);
		Assets.shotModel.bind();
		int len = shots.size();
		for (int i = 0; i < len; i++) {
			Shot shot = shots.get(i);
			gl.glPushMatrix();
			gl.glTranslatef(shot.position.x, shot.position.y, shot.position.z);
			Assets.shotModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.shotModel.getNumVertices());
			gl.glPopMatrix();
		}
		Assets.shotModel.unbind();
		gl.glColor4f(1, 1, 1, 1);
	}

	private void renderExplosion(GL10 gl, Vector3 position, float stateTime) {
		TextureRegion frame = Assets.explosionAnim.getKeyFrame(stateTime,
				Animation.ANIMATION_NONLOOPING);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glPushMatrix();
		gl.glTranslatef(position.x, position.y, position.z);		
		batcher.beginBatch(Assets.explosionTexture);
		batcher.drawSprite(0, 0, 2, 2, frame);
		batcher.endBatch();
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_BLEND);
	}
}