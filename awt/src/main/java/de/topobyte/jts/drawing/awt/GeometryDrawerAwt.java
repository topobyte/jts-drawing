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

package de.topobyte.jts.drawing.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.topobyte.awt.util.GraphicsUtil;
import de.topobyte.awt.util.ImageUtil;
import de.topobyte.jgs.transform.CoordinateTransformer;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryDrawerAwt extends GeometryDrawerGraphics
{

	private BufferedImage img;
	private CoordinateTransformer ct;
	private Graphics2D g;

	@Override
	public Graphics2D getGraphics()
	{
		return g;
	}

	/**
	 * Create a new implementation of the PolygonDrawer interface.
	 * 
	 * @param ct
	 *            the transformation applied to coordinates.
	 * @param width
	 *            the width of the image in pixels.
	 * @param height
	 *            the height of the image in pixels.
	 */
	public GeometryDrawerAwt(CoordinateTransformer ct, int width, int height)
	{
		this(ct, width, height, false);
	}

	/**
	 * Create a new implementation of the PolygonDrawer interface.
	 * 
	 * @param ct
	 *            the transformation applied to coordinates.
	 * @param image
	 *            the image to draw upon.
	 * @param antialiase
	 *            use antialiasing during rendering.
	 */
	public GeometryDrawerAwt(CoordinateTransformer ct, BufferedImage image,
			boolean antialiase)
	{
		super(ct, image.getWidth(), image.getHeight());
		this.ct = ct;
		this.img = image;
		g = img.createGraphics();
		GraphicsUtil.useAntialiasing(g, antialiase);
	}

	/**
	 * Create a new implementation of the PolygonDrawer interface.
	 * 
	 * @param ct
	 *            the transformation applied to coordinates.
	 * @param width
	 *            the width of the image in pixels.
	 * @param height
	 *            the height of the image in pixels.
	 * @param antialiase
	 *            use antialiasing during rendering.
	 */
	public GeometryDrawerAwt(CoordinateTransformer ct, int width, int height,
			boolean antialiase)
	{
		super(ct, width, height);
		this.ct = ct;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = img.createGraphics();
		GraphicsUtil.useAntialiasing(g, antialiase);
	}

	/**
	 * Retrieve the internal underlying BufferedImage to perform custom drawing
	 * operations.
	 * 
	 * @return the underlying BufferedImage.
	 */
	public BufferedImage getImage()
	{
		return img;
	}

	/**
	 * Get a duplicate of this drawer.
	 * 
	 * @return a duplicate.
	 */
	public GeometryDrawerAwt duplicate()
	{
		GeometryDrawerAwt copy = new GeometryDrawerAwt(ct, img.getWidth(),
				img.getHeight());
		copy.img = ImageUtil.duplicate(img);
		copy.g = copy.img.createGraphics();
		return copy;
	}

}
