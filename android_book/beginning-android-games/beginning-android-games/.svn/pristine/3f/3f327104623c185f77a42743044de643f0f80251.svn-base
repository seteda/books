package com.badlogic.androidgames.droidinvaders;

import com.badlogic.androidgames.framework.DynamicGameObject3D;

public class Invader extends DynamicGameObject3D {
	static final int INVADER_ALIVE = 0;
	static final int INVADER_DEAD = 1;
	static final float INVADER_EXPLOSION_TIME = 1.6f;
	static final float INVADER_RADIUS = 0.75f;
	static final float INVADER_VELOCITY = 1;
	static final int MOVE_LEFT = 0;
	static final int MOVE_DOWN = 1;
	static final int MOVE_RIGHT = 2;

	int state = INVADER_ALIVE;
	float stateTime = 0;
	int move = MOVE_LEFT;
	boolean wasLastStateLeft = true;
	float movedDistance = World.WORLD_MAX_X / 2;

	public Invader(float x, float y, float z) {
		super(x, y, z, INVADER_RADIUS);
	}

	public void update(float deltaTime, float speedMultiplier) {
		if (state == INVADER_ALIVE) {
			movedDistance += deltaTime * INVADER_VELOCITY * speedMultiplier;
			if (move == MOVE_LEFT) {
				position.x -= deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (movedDistance > World.WORLD_MAX_X) {
					move = MOVE_DOWN;
					movedDistance = 0;
					wasLastStateLeft = true;
				}
			}
			if (move == MOVE_RIGHT) {
				position.x += deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (movedDistance > World.WORLD_MAX_X) {
					move = MOVE_DOWN;
					movedDistance = 0;
					wasLastStateLeft = false;
				}
			}
			if (move == MOVE_DOWN) {
				position.z += deltaTime * INVADER_VELOCITY * speedMultiplier;
				if (movedDistance > 1) {
					if (wasLastStateLeft)
						move = MOVE_RIGHT;
					else
						move = MOVE_LEFT;
					movedDistance = 0;
				}
			}

			bounds.center.set(position);
		}

		stateTime += deltaTime;
	}

	public void kill() {
		state = INVADER_DEAD;
		stateTime = 0;
	}
}
