// Copyright 2016 Sebastian Kuerten
//
// This file is part of jts-drawing.
//
// jts-drawing is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jts-drawing is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jts-drawing. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.jts.drawing;

import org.locationtech.jts.geom.Geometry;

import de.topobyte.chromaticity.ColorCode;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public interface GeometryDrawer
{

	/**
	 * Set the color to use for lines in future drawing operations.
	 * 
	 * @param c
	 *            the color for lines.
	 */
	public void setColorForeground(ColorCode c);

	/**
	 * Set the color to use for filling in future drawing operations.
	 * 
	 * @param c
	 *            the color for fillings.
	 */
	public void setColorBackground(ColorCode c);

	/**
	 * Set the width of lines in future drawing operations.
	 * 
	 * @param width
	 *            the width of lines.
	 */
	public void setLineWidth(double width);

	/**
	 * Set the cap style used in future drawing operations-
	 * 
	 * @param cap
	 *            the cap style to use.
	 */
	public void setCap(Cap cap);

	/**
	 * Set the join style used in future drawing operations-
	 * 
	 * @param join
	 *            the join style to use.
	 */
	public void setJoin(Join join);

	/**
	 * Draw the given geometry on this surface.
	 * 
	 * @param g
	 *            the geometry to draw.
	 * @param mode
	 *            the drawing mode
	 */
	public void drawGeometry(Geometry g, DrawMode mode);

	/**
	 * Draw a segment on the surface.
	 * 
	 * @param x1
	 *            the first coordinate's x component
	 * @param y1
	 *            the first coordinate's y component
	 * @param x2
	 *            the second coordinate's x component
	 * @param y2
	 *            the second coordinate's y component
	 */
	public void drawSegment(double x1, double y1, double x2, double y2);

	/**
	 * Draw a rectangle that is defined by it's absolute positions of each
	 * corner.
	 * 
	 * @param x1
	 *            the left x coordinate.
	 * @param y1
	 *            the top y coordinate.
	 * @param x2
	 *            the right x coordinate.
	 * @param y2
	 *            the bottom y coordinate.
	 * @param mode
	 *            the drawing mode
	 */
	public void drawRectangleAbsolute(double x1, double y1, double x2,
			double y2, DrawMode mode);

	/**
	 * Draw a rectangle that is defined by a top left position and a width and
	 * height.
	 * 
	 * @param x
	 *            the left x coordinate.
	 * @param y
	 *            the top y coordinate.
	 * @param width
	 *            the width of the rectangle.
	 * @param height
	 *            the height of the rectangle.
	 * @param mode
	 *            the drawing mode
	 */
	public void drawRectangle(double x, double y, double width, double height,
			DrawMode mode);

	/**
	 * Draw a rectangle that is defined by a center position and a width and
	 * height.
	 * 
	 * @param x
	 *            the center x coordinate.
	 * @param y
	 *            the center y coordinate.
	 * @param width
	 *            the width of the rectangle.
	 * @param height
	 *            the height of the rectangle.
	 * @param mode
	 *            the drawing mode
	 */
	public void drawRectangleCentered(double x, double y, double width,
			double height, DrawMode mode);

	/**
	 * Draw a circle.
	 * 
	 * @param x
	 *            the x coordinate of the circle's center.
	 * @param y
	 *            the y coordinate of the circle's center.
	 * @param radius
	 *            the radius.
	 * @param mode
	 *            the drawing mode
	 */
	public void drawCircle(double x, double y, double radius, DrawMode mode);

	/**
	 * Draw this string to the given position.
	 * 
	 * @param x
	 *            position coordinate
	 * @param y
	 *            position coordinate
	 * @param text
	 *            the text to draw
	 * @param fontsize
	 *            the size of font
	 * @param xoff
	 *            x offset in image space
	 * @param yoff
	 *            y offset in image space
	 */
	public void drawString(double x, double y, String text, int fontsize,
			int xoff, int yoff);

}
