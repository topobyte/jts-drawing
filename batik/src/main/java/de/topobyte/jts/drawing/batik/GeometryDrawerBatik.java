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

package de.topobyte.jts.drawing.batik;

import java.awt.Dimension;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.topobyte.jgs.transform.CoordinateTransformer;
import de.topobyte.jts.drawing.DrawMode;
import de.topobyte.jts.drawing.awt.GeometryDrawerGraphics;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryDrawerBatik extends GeometryDrawerGraphics
{

	private SVGGraphics2D graphics;
	private final static double scale = 100;

	/**
	 * Create a drawer.
	 * 
	 * @param ct
	 *            a coordinate transformation to apply.
	 * @param filename
	 *            a file to write the image to.
	 * @param width
	 *            the width of the image in pixels.
	 * @param height
	 *            the height of the image in pixels.
	 */
	public GeometryDrawerBatik(CoordinateTransformer ct, int width, int height)
	{
		super(new ScaleCoordinateTransformer(ct), (int) (width * scale),
				(int) (height * scale));

		DOMImplementation domi = GenericDOMImplementation
				.getDOMImplementation();
		String svgNS = "http://www.w3.org/2000/svg";
		Document doc = domi.createDocument(svgNS, "svg", null);

		graphics = new SVGGraphics2D(doc);
		graphics.setSVGCanvasSize(new Dimension(width, height));
		graphics.scale(1 / scale, 1 / scale);
	}

	@Override
	public SVGGraphics2D getGraphics()
	{
		return graphics;
	}

	@Override
	public void setLineWidth(double width)
	{
		super.setLineWidth(width * scale);
	}

	@Override
	public void drawCircle(double x, double y, double radius, DrawMode mode)
	{
		super.drawCircle(x, y, radius * scale, mode);
	}

	@Override
	public void drawRectangle(double x, double y, double width, double height,
			DrawMode mode)
	{
		super.drawRectangle(x, y, width * scale, height * scale, mode);
	}

	@Override
	public void drawRectangleCentered(double x, double y, double width,
			double height, DrawMode mode)
	{
		super.drawRectangleCentered(x, y, width * scale, height * scale, mode);
	}

	@Override
	public void drawRectangleAbsolute(double x1, double y1, double x2,
			double y2, DrawMode mode)
	{
		super.drawRectangleAbsolute(x1, y1, x2, y2, mode);
	}

	@Override
	public void drawString(double x, double y, String text, int fontsize,
			int xoff, int yoff)
	{
		super.drawString(x, y, text, (int) (fontsize * scale),
				(int) (xoff * scale), (int) (yoff * scale));
	}

	private static final class ScaleCoordinateTransformer implements
			CoordinateTransformer
	{

		private CoordinateTransformer parent;

		ScaleCoordinateTransformer(CoordinateTransformer parent)
		{
			this.parent = parent;
		}

		@Override
		public double getX(double x)
		{
			return parent.getX(x) * scale;
		}

		@Override
		public double getY(double y)
		{
			return parent.getY(y) * scale;
		}

	}

}
