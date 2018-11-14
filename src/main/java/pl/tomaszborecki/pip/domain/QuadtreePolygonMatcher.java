package pl.tomaszborecki.pip.domain;

import java.io.IOException;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.util.NullProgressListener;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.opengis.feature.Feature;
import org.opengis.feature.FeatureVisitor;
import org.opengis.feature.simple.SimpleFeature;

import pl.tomaszborecki.pip.domain.model.PointInPolygon;

public class QuadtreePolygonMatcher implements PolygonMatcher {

  private Quadtree quadtree;

  public void init(SimpleFeatureSource featureSource) throws IOException {
    quadtree = new Quadtree();
    featureSource.getFeatures().accepts(new FeatureVisitor() {
      @Override
      public void visit(Feature feature) {
        SimpleFeature simpleFeature = (SimpleFeature) feature;
        Geometry geom = (MultiPolygon) simpleFeature.getDefaultGeometry();
        if (geom != null) {
          Envelope env = geom.getEnvelopeInternal(); // TODO internal?
          if (!env.isNull()) {
            quadtree.insert(env, simpleFeature);
          }
        }
      }
    }, new NullProgressListener());
  }

  @SuppressWarnings("unchecked")
  public PointInPolygon match(Point point) {
    Envelope envelope = new Envelope(point.getCoordinate());
    List<SimpleFeature> collection = quadtree.query(envelope);
    for (SimpleFeature feature : collection) {
      Geometry sourceGeometry = (Geometry) feature.getDefaultGeometry();
      if (sourceGeometry.contains(point)) {
        return PointInPolygon.of(point, feature);
      }
    }
    return PointInPolygon.of(point, null);
  }

}
