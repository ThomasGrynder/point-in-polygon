package pl.tomaszborecki.pip.domain;

import java.io.IOException;

import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.locationtech.jts.geom.Point;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.tomaszborecki.pip.domain.model.PointInPolygon;

public class DumbPolygonMatcher implements PolygonMatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(DumbPolygonMatcher.class);
	
  SimpleFeatureSource featureSource;

  public void init(SimpleFeatureSource featureSource) throws IOException {
    this.featureSource = featureSource;
  }

  public PointInPolygon match(Point point) {
    FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
    Filter filter = ff.contains(ff.property("the_geom"), ff.literal(point));
    Query query = new Query("polygons", filter, 1, new String[] { "NAME" }, null);
    try (SimpleFeatureIterator iterator = featureSource.getFeatures(query).features()) {
      if (iterator.hasNext()) {
        return PointInPolygon.of(point, iterator.next());
      }
    } catch (IOException e) {
      LOGGER.error("Error occurred during filtering feature collection.", e);
    }
    return PointInPolygon.of(point, null);
  }

}
