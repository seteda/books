package ch04.common;

public class Rectangle {

	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle() {
		this(0, 0, 0, 0);
	}

	/**
	 * Constructs a new <code>Rectangle</code> whose top-left corner is
	 * specified as (<code>x</code>,&nbsp;<code>y</code>) and whose width and
	 * height are specified by the arguments of the same name.
	 * 
	 * @param x
	 *            the specified x coordinate
	 * @param y
	 *            the specified y coordinate
	 * @param width
	 *            the width of the <code>Rectangle</code>
	 * @param height
	 *            the height of the <code>Rectangle</code>
	 */
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean contains(int X, int Y, int W, int H) {
		int w = this.width;
		int h = this.height;


		if ((w | h | W | H) < 0) {
			// At least one of the dimensions is negative...
			return false;
		}
		// Note: if any dimension is zero, tests below must return false...
		int x = this.x;
		int y = this.y;
		if (X < x || Y < y) {
			return false;
		}
		w += x;
		W += X;
		if (W <= X) {
			// X+W overflowed or W was zero, return false if...
			// either original w or W was zero or
			// x+w did not overflow or
			// the overflowed x+w is smaller than the overflowed X+W
			if (w >= x || W > w) {
				return false;
			}
		} else {
			// X+W did not overflow and W was not zero, return false if...
			// original w was zero or
			// x+w did not overflow and x+w is smaller than X+W
			if (w >= x && W > w) {
				return false;
			}
		}
		h += y;
		H += Y;
		if (H <= Y) {
			if (h >= y || H > h) {
				// System.out.println("r4");
				return false;
			}
		} else {
			if (h >= y && H > h) {
				// System.out.println("r5");
				return false;
			}
		}
		return true;
	}

	public boolean contains(int x, int y) {
		return inside(x, y);
	}

	public boolean inside(int X, int Y) {
		int w = this.width;
		int h = this.height;
		if ((w | h) < 0) {
			// At least one of the dimensions is negative...
			return false;
		}
		// Note: if either dimension is zero, tests below must return false...
		int x = this.x;
		int y = this.y;
		if (X < x || Y < y) {
			return false;
		}
		w += x;
		h += y;
		// overflow || intersect
		return ((w < x || w > X) && (h < y || h > Y));
	}

}
