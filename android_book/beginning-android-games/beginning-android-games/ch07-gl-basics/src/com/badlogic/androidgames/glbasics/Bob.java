package com.badlogic.androidgames.glbasics;

import java.util.Random;

class Bob {
	static final Random rand = new Random();
	public float x, y;
	float dirX, dirY;

	public Bob() {
		x = rand.nextFloat() * 320;
		y = rand.nextFloat() * 480;
		dirX = 50;
		dirY = 50;
	}

	public void update(float deltaTime) {
		x = x + dirX * deltaTime;
		y = y + dirY * deltaTime;

		if (x < 0) {
			dirX = -dirX;
			x = 0;
		}

		if (x > 320) {
			dirX = -dirX;
			x = 320;
		}

		if (y < 0) {
			dirY = -dirY;
			y = 0;
		}

		if (y > 480) {
			dirY = -dirY;
			y = 480;
		}
	}
}