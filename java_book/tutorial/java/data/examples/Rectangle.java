/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

public class Rectangle {
    /** The width of this rectangle. */
    public int width;

    /** The height of this rectangle. */
    public int height;

    /** The origin (lower-left corner) of this rectangle. */
    public Point origin;

    /**
     * Creates a rectangle with the specified size.
     * The origin is (0, 0).
     * @param w width of the rectangle
     * @param h height of the rectangle
     */
    public Rectangle(int w, int h) {
	this(new Point(0, 0), w, h);
    }

    /**
     * Creates a rectangle with the specified origin and
     * size.
     * @param p origin of the rectangle
     * @param w width of the rectangle
     * @param h height of the rectangle
     */
    public Rectangle(Point p, int w, int h) {
	origin = p;
	width = w;
	height = h;
    }

    /**
     * Moves the rectangle to the specified origin.
     */
    public void move(int x, int y) {
	origin.x = x;
	origin.y = y;
    }

    /**
     * Returns the computed area of the rectangle.
     */
    public int area() {
	return width * height;
    }
}
