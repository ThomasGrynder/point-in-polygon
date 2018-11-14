package pl.tomaszborecki.pip.domain;

import java.io.IOException;

import org.geotools.data.collection.SpatialIndexFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

import pl.tomaszborecki.pip.domain.model.PointInPolygon;

public class STRtreePolygonMatcher implements PolygonMatcher {

  private SpatialIndexFeatureCollection indexedCollection;

  public void init(SimpleFeatureSource featureSource) throws IOException {
    indexedCollection = new SpatialIndexFeatureCollection(featureSource.getFeatures());
  }

  public PointInPolygon match(Point point) {
    try (SimpleFeatureIterator iterator = indexedCollection.features()) {
      while (iterator.hasNext()) {
        SimpleFeature feature = iterator.next();
        Geometry sourceGeometry = (Geometry) feature.getDefaultGeometry();
        if (sourceGeometry.contains(point)) {
          return PointInPolygon.of(point, feature);
        }
      }
    }
    return PointInPolygon.of(point, null);
  }

}
