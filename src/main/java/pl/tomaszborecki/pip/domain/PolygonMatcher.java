package pl.tomaszborecki.pip.domain;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.geom.Point;

import pl.tomaszborecki.pip.domain.model.PointInPolygon;

public interface PolygonMatcher {

  void init(SimpleFeatureSource featureSource) throws IOException;

  PointInPolygon match(Point point);
}
