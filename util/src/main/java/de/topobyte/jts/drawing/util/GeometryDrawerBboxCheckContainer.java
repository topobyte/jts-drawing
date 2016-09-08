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

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import de.topobyte.chromaticity.ColorCode;
import de.topobyte.jts.drawing.DrawMode;
import de.topobyte.jts.drawing.GeometryDrawer;
import de.topobyte.jts.utils.JtsHelper;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public class GeometryDrawerBboxCheckContainer implements GeometryDrawer
{

	private GeometryDrawer pd;

	private double mlon1, mlon2, mlat1, mlat2;
	private GeometryFactory gf = new GeometryFactory();
	private Polygon bbox;

	/**
	 * @param pd
	 *            an underlying PolygonDrawer to encapsulate.
	 * @param lon1
	 *            bbox lon1.
	 * @param lat1
	 *            bbox lat1.
	 * @param lon2
	 *            bbox lon2.
	 * @param lat2
	 *            bbox lat2.
	 * 
	 */
	public GeometryDrawerBboxCheckContainer(GeometryDrawer pd, double lon1,
			double lat1, double lon2, double lat2)
	{
		this.pd = pd;
		mlon1 = lon1;
		mlat1 = lat1;
		mlon2 = lon2;
		mlat2 = lat2;

		if (mlon1 > mlon2) {
			double tmp = mlon1;
			mlon1 = mlon2;
			mlon2 = tmp;
		}
		if (mlat1 < mlat2) {
			double tmp = mlat1;
			mlat1 = mlat2;
			mlat2 = tmp;
		}

		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		xs.add(mlon1);
		xs.add(mlon2);
		xs.add(mlon2);
		xs.add(mlon1);
		ys.add(mlat1);
		ys.add(mlat1);
		ys.add(mlat2);
		ys.add(mlat2);

		LinearRing ring = JtsHelper.toLinearRing(xs, ys, false);
		bbox = new GeometryFactory().createPolygon(ring, null);
	}

	@Override
	public void drawGeometry(Geometry g, DrawMode mode)
	{
		if (bbox.intersects(g)) {
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
	public void drawSegment(double x1, double y1, double x2, double y2)
	{
		// TODO: test for intersection
		pd.drawSegment(x1, x2, y1, y2);
	}

	@Override
	public void drawRectangleAbsolute(double x1, double y1, double x2,
			double y2, DrawMode mode)
	{
		Point p1 = gf.createPoint(new Coordinate(x1, y1));
		Point p2 = gf.createPoint(new Coordinate(x1, y2));
		Point p3 = gf.createPoint(new Coordinate(x2, y1));
		Point p4 = gf.createPoint(new Coordinate(x2, y2));
		// TODO: this does not work in every case
		if (bbox.covers(p1) || bbox.covers(p2) || bbox.covers(p3)
				|| bbox.covers(p4)) {
			pd.drawRectangleAbsolute(x1, y1, x2, y2, mode);
		}
	}

	@Override
	public void drawRectangle(double x, double y, double width, double height,
			DrawMode mode)
	{
		Point p = gf.createPoint(new Coordinate(x, y));
		// TODO: this does not take the size of the rectangle into account
		if (bbox.covers(p)) {
			pd.drawRectangle(x, y, width, height, mode);
		}
	}

	@Override
	public void drawRectangleCentered(double x, double y, double width,
			double height, DrawMode mode)
	{
		Point p = gf.createPoint(new Coordinate(x, y));
		// TODO: this does not take the size of the rectangle into account
		if (bbox.covers(p)) {
			pd.drawRectangleCentered(x, y, width, height, mode);
		}
	}

	@Override
	public void drawCircle(double x, double y, double radius, DrawMode mode)
	{
		Point p = gf.createPoint(new Coordinate(x, y));
		// TODO: this does not take radius into account
		if (bbox.covers(p)) {
			pd.drawCircle(x, y, radius, mode);
		}
	}

	@Override
	public void drawString(double x, double y, String text, int fontsize,
			int xoff, int yoff)
	{
		pd.drawString(x, y, text, fontsize, xoff, yoff);
	}

}
