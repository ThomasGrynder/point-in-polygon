package pl.tomaszborecki.pip.domain.model;

import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

public class PointInPolygon {

	private Point point;
	private SimpleFeature polygon;

	private PointInPolygon(Point point, SimpleFeature polygon) {
		this.point = point;
		this.polygon = polygon;
	}

	public static PointInPolygon of(Point point, SimpleFeature polygon) {
		return new PointInPolygon(point, polygon);
	}

	@Override
	public String toString() {
		return point + ", POLYGON: " + getPolygonName();
	}

	public Point getPoint() {
		return point;
	}

	public SimpleFeature getPolygon() {
		return polygon;
	}

	public String getPolygonName() {
		return polygon != null ? (String) polygon.getAttribute("NAME") : "None";
	}

}
