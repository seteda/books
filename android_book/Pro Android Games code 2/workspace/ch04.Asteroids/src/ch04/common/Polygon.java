package ch04.common;

public class Polygon {

	public int npoints;
	public int[] ypoints;
	public int[] xpoints;

	protected Rectangle bounds;

	public Polygon() {
		xpoints = new int[4];
		ypoints = new int[4];
	}

	public Polygon(int xpoints[], int ypoints[], int npoints) {
		// Fix 4489009: should throw IndexOutofBoundsException instead
		// of OutofMemoryException if npoints is huge and > {x,y}points.length
		if (npoints > xpoints.length || npoints > ypoints.length) {
			throw new IndexOutOfBoundsException(
					"npoints > xpoints.length || npoints > ypoints.length");
		}
		this.npoints = npoints;
		this.xpoints = new int[npoints];
		this.ypoints = new int[npoints];
		System.arraycopy(xpoints, 0, this.xpoints, 0, npoints);
		System.arraycopy(ypoints, 0, this.ypoints, 0, npoints);
	}

	void calculateBounds(int xpoints[], int ypoints[], int npoints) {
		int boundsMinX = Integer.MAX_VALUE;
		int boundsMinY = Integer.MAX_VALUE;
		int boundsMaxX = Integer.MIN_VALUE;
		int boundsMaxY = Integer.MIN_VALUE;

		for (int i = 0; i < npoints; i++) {
			int x = xpoints[i];
			boundsMinX = Math.min(boundsMinX, x);
			boundsMaxX = Math.max(boundsMaxX, x);
			int y = ypoints[i];
			boundsMinY = Math.min(boundsMinY, y);
			boundsMaxY = Math.max(boundsMaxY, y);
		}

		bounds = new Rectangle(boundsMinX, boundsMinY, boundsMaxX - boundsMinX,
				boundsMaxY - boundsMinY);
	}

	public void reset() {
		npoints = 0;
		bounds = null;
	}

	/**
	 * Appends the specified coordinates to this <code>Polygon</code>.
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(int x, int y) {
		if (npoints == xpoints.length) {
			int tmp[];

			tmp = new int[npoints * 2];
			System.arraycopy(xpoints, 0, tmp, 0, npoints);
			xpoints = tmp;

			tmp = new int[npoints * 2];
			System.arraycopy(ypoints, 0, tmp, 0, npoints);
			ypoints = tmp;
		}
		xpoints[npoints] = x;
		ypoints[npoints] = y;
		npoints++;
		if (bounds != null) {
			updateBounds(x, y);
		}
	}

	void updateBounds(int x, int y) {
		if (x < bounds.x) {
			bounds.width = bounds.width + (bounds.x - x);
			bounds.x = x;
		} else {
			bounds.width = Math.max(bounds.width, x - bounds.x);
		}
		
		if (y < bounds.y) {
			bounds.height = bounds.height + (bounds.y - y);
			bounds.y = y;
		} else {
			bounds.height = Math.max(bounds.height, y - bounds.y);
		}
	}

	public Rectangle getBoundingBox() {
		if (npoints == 0) {
			return new Rectangle();
		}
		if (bounds == null) {
			calculateBounds(xpoints, ypoints, npoints);
		}
		return bounds;
	}

	
	/**
	 * Determines if the specified coordinates are inside this
	 * <code>Polygon</code>.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y) {
		if (npoints <= 2 || !getBoundingBox().contains(x, y)) {
			return false;
		}
		int hits = 0;

		int lastx = xpoints[npoints - 1];
		int lasty = ypoints[npoints - 1];
		int curx, cury;

		// Walk the edges of the polygon
		for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
			curx = xpoints[i];
			cury = ypoints[i];

			if (cury == lasty) {
				continue;
			}

			int leftx;
			if (curx < lastx) {
				if (x >= lastx) {
					continue;
				}
				leftx = curx;
			} else {
				if (x >= curx) {
					continue;
				}
				leftx = lastx;
			}

			double test1, test2;
			if (cury < lasty) {
				if (y < cury || y >= lasty) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - curx;
				test2 = y - cury;
			} else {
				if (y < lasty || y >= cury) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - lastx;
				test2 = y - lasty;
			}

			if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
				hits++;
			}
		}

		return ((hits & 1) != 0);
	}

	@Override
	public String toString() {
		if (npoints == 0)
			return null;
		String s = ""; // 

		for (int i = 0; i < xpoints.length; i++) {
			s += "(" + xpoints[i] + "," + ypoints[i] + ") ";
		}
		return s;
	}

	/**
	 * Get polygon points (x0y0, x1y1,.....) for rendering Each point pair will
	 * render 1 line so the total # of points must be num sides * 4
	 * 
	 * @return
	 */
	public float[] getPoints() {
		int size = npoints * 4;
		float[] points = new float[size];
		int j = 1;

		if ( size == 0 || xpoints == null || ypoints == null)
			return null;
		
		points[0] = xpoints[0];
		points[1] = ypoints[0];

		for (int i = 2; i < points.length - 2; i += 4) {

			points[i] = xpoints[j];
			points[i + 1] = ypoints[j];
			points[i + 2] = xpoints[j];
			points[i + 3] = ypoints[j];
			j++;
		}
		points[size - 2] = xpoints[0];
		points[size - 1] = ypoints[0];

		return points;
	}
}
