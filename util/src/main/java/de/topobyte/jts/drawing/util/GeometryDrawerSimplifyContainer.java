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

package de.topobyte.jts.drawing.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.DouglasPeuckerSimplifier;

import de.topobyte.chromaticity.ColorCode;
import de.topobyte.jts.drawing.Cap;
import de.topobyte.jts.drawing.DrawMode;
import de.topobyte.jts.drawing.GeometryDrawer;
import de.topobyte.jts.drawing.Join;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryDrawerSimplifyContainer implements GeometryDrawer
{

	private GeometryDrawer pd;
	private double tolerance;

	/**
	 * @param pd
	 *            an underlying PolygonDrawer to encapsulate.
	 * @param tolerance
	 *            a tolerance value to use for simplification.
	 */
	public GeometryDrawerSimplifyContainer(GeometryDrawer pd, double tolerance)
	{
		this.pd = pd;
		this.tolerance = tolerance;
	}

	@Override
	public void drawGeometry(Geometry g, DrawMode mode)
	{
		if (tolerance > 0) {
			DouglasPeuckerSimplifier simplifier = new DouglasPeuckerSimplifier(
					g);
			simplifier.setDistanceTolerance(tolerance);
			Geometry simple = simplifier.getResultGeometry();
			pd.drawGeometry(simple, mode);
		} else {
			pd.drawGeometry(g, mode);
		}
	}

	@Override
	public void setColorForeground(ColorCode c)
	{
		pd.setColorForeground(c);
	}

	@Override
	public void setColorBackground(ColorCode c)
	{
		pd.setColorBackground(c);
	}

	@Override
	public void setLineWidth(double width)
	{
		pd.setLineWidth(width);
	}

	@Override
	public void setCap(Cap cap)
	{
		pd.setCap(cap);
	}

	@Override
	public void setJoin(Join join)
	{
		pd.setJoin(join);
	}

	@Override
	public void drawSegment(double x1, double y1, double x2, double y2)
	{
		pd.drawSegment(x1, x2, y1, y2);
	}

	@Override
	public void drawRectangleAbsolute(double x1, double y1, double x2,
			double y2, DrawMode mode)
	{
		pd.drawRectangleAbsolute(x1, y1, x2, y2, mode);
	}

	@Override
	public void drawRectangle(double x, double y, double width, double height,
			DrawMode mode)
	{
		pd.drawRectangle(x, y, width, height, mode);
	}

	@Override
	public void drawRectangleCentered(double x, double y, double width,
			double height, DrawMode mode)
	{
		pd.drawRectangleCentered(x, y, width, height, mode);
	}

	@Override
	public void drawCircle(double x, double y, double radius, DrawMode mode)
	{
		pd.drawCircle(x, y, radius, mode);
	}

	@Override
	public void drawString(double x, double y, String text, int fontsize,
			int xoff, int yoff)
	{
		pd.drawString(x, y, text, fontsize, xoff, yoff);
	}

}
