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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import de.topobyte.chromaticity.AwtColors;
import de.topobyte.chromaticity.ColorCode;
import de.topobyte.jgs.transform.CoordinateTransformer;
import de.topobyte.jts.drawing.DrawMode;
import de.topobyte.jts.drawing.GeometryDrawer;
import de.topobyte.jts2awt.Jts2Awt;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 */
public abstract class GeometryDrawerGraphics implements GeometryDrawer
{

	private CoordinateTransformer ct;
	private ColorCode codeFg = new ColorCode(0, 0, 0, 255);
	private ColorCode codeBg = new ColorCode(0, 0, 0, 255);

	private Color fg = AwtColors.convert(codeFg);
	private Color bg = AwtColors.convert(codeBg);

	/**
	 * @return the graphics objects used for drawing.
	 */
	public abstract Graphics2D getGraphics();

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
	public GeometryDrawerGraphics(CoordinateTransformer ct, int width,
			int height)
	{
		this.ct = ct;
	}

	private void setColor(Color c)
	{
		getGraphics().setColor(c);
	}

	@Override
	public void setColorBackground(ColorCode c)
	{
		codeBg = c;
		bg = AwtColors.convert(codeBg);
	}

	@Override
	public void setColorForeground(ColorCode c)
	{
		codeFg = c;
		fg = AwtColors.convert(codeFg);
	}

	@Override
	public void setLineWidth(double width)
	{
		getGraphics().setStroke(new BasicStroke((float) width));
	}

	@Override
	public void drawGeometry(Geometry geom, DrawMode mode)
	{
		if (geom instanceof LineString) {
			drawLineString((LineString) geom);
		} else if (geom instanceof Polygon) {
			drawPolygon((Polygon) geom, mode);
		} else if (geom instanceof MultiPolygon) {
			drawMultiPolygon((MultiPolygon) geom, mode);
		} else if (geom instanceof GeometryCollection) {
			GeometryCollection gc = (GeometryCollection) geom;
			for (int i = 0; i < gc.getNumGeometries(); i++) {
				Geometry geomN = gc.getGeometryN(i);
				drawGeometry(geomN, mode);
			}
		}
	}

	private void drawLineString(LineString line)
	{
		getGraphics().setColor(fg);
		CoordinateSequence seq = line.getCoordinateSequence();
		Coordinate prev = new Coordinate();
		for (int i = 0; i < seq.size(); i++) {
			Coordinate c = seq.getCoordinate(i);
			if (i > 0) {
				Line2D segment = new Line2D.Double(ct.getX(c.x), ct.getY(c.y),
						ct.getX(prev.x), ct.getY(prev.y));
				getGraphics().draw(segment);
			}
			prev = c;
		}
	}

	private void drawPolygon(Polygon p, DrawMode mode)
	{
		Area area = Jts2Awt.toShape(p, ct);

		switch (mode) {
		case FILL:
			setColor(bg);
			getGraphics().fill(area);
			break;
		case OUTLINE:
			setColor(fg);
			getGraphics().draw(area);
			break;
		case FILL_OUTLINE:
			setColor(bg);
			getGraphics().fill(area);
			setColor(fg);
			getGraphics().draw(area);
			break;
		case OUTLINE_FILL:
			setColor(fg);
			getGraphics().draw(area);
			setColor(bg);
			getGraphics().fill(area);
			break;
		}
	}

	private void drawMultiPolygon(MultiPolygon mp, DrawMode mode)
	{
		for (int i = 0; i < mp.getNumGeometries(); i++) {
			Geometry geom = mp.getGeometryN(i);
			if (geom instanceof Polygon) {
				drawPolygon((Polygon) geom, mode);
			}
		}
	}

	@Override
	public void drawCircle(double x, double y, double radius, DrawMode mode)
	{
		int sx = (int) (ct.getX(x) - radius);
		int sy = (int) (ct.getY(y) - radius);
		int wid = (int) (2 * radius);
		int hei = (int) (2 * radius);

		switch (mode) {
		case FILL:
			setColor(bg);
			getGraphics().fillArc(sx, sy, wid, hei, 0, 360);
			break;
		case OUTLINE:
			setColor(fg);
			getGraphics().drawArc(sx, sy, wid, hei, 0, 360);
			break;
		case FILL_OUTLINE:
			setColor(bg);
			getGraphics().fillArc(sx, sy, wid, hei, 0, 360);
			setColor(fg);
			getGraphics().drawArc(sx, sy, wid, hei, 0, 360);
			break;
		case OUTLINE_FILL:
			setColor(fg);
			getGraphics().drawArc(sx, sy, wid, hei, 0, 360);
			setColor(bg);
			getGraphics().fillArc(sx, sy, wid, hei, 0, 360);
			break;
		}
	}

	@Override
	public void drawSegment(double x1, double y1, double x2, double y2)
	{
		setColor(fg);
		getGraphics().draw(
				new Line2D.Double(ct.getX(x1), ct.getY(y1), ct.getX(x2), ct
						.getY(y2)));
	}

	@Override
	public void drawRectangleAbsolute(double x1, double y1, double x2,
			double y2, DrawMode mode)
	{
		double xA = ct.getX(x1);
		double xB = ct.getX(x2);
		double yA = ct.getY(y1);
		double yB = ct.getY(y2);
		double xS = xA < xB ? xA : xB;
		double yS = yA < yB ? yA : yB;
		double width = Math.abs(xA - xB);
		double height = Math.abs(yA - yB);

		Rectangle2D rect = new Rectangle2D.Double(xS, yS, width, height);
		drawRect(rect, mode);

	}

	@Override
	public void drawRectangle(double x, double y, double width, double height,
			DrawMode mode)
	{
		double rx = ct.getX(x);
		double ry = ct.getY(y);

		Rectangle2D rect = new Rectangle2D.Double(rx, ry, width, height);
		drawRect(rect, mode);
	}

	@Override
	public void drawRectangleCentered(double x, double y, double width,
			double height, DrawMode mode)
	{
		double rx = ct.getX(x) - width / 2d;
		double ry = ct.getY(y) - height / 2d;

		Rectangle2D rect = new Rectangle2D.Double(rx, ry, width, height);
		drawRect(rect, mode);
	}

	private void drawRect(Rectangle2D rect, DrawMode mode)
	{
		switch (mode) {
		case FILL:
			setColor(bg);
			getGraphics().fill(rect);
			break;
		case OUTLINE:
			setColor(fg);
			getGraphics().draw(rect);
			break;
		case FILL_OUTLINE:
			setColor(bg);
			getGraphics().fill(rect);
			setColor(fg);
			getGraphics().draw(rect);
			break;
		case OUTLINE_FILL:
			setColor(fg);
			getGraphics().draw(rect);
			setColor(bg);
			getGraphics().fill(rect);
			break;
		}
	}

	@Override
	public void drawString(double x, double y, String text, int fontsize,
			int xoff, int yoff)
	{
		Graphics2D g2d = getGraphics();
		g2d.setColor(fg);
		g2d.setFont(new Font("Verdana", Font.PLAIN, fontsize));
		float ix = (float) (ct.getX(x));
		float iy = (float) (ct.getY(y));
		g2d.drawString(text, ix + xoff, iy + yoff);
	}

}
