package pl.tomaszborecki.pip.domain;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureSource;
import org.springframework.stereotype.Component;

import pl.tomaszborecki.pip.domain.model.Algorithm;

@Component
public class PolygonMatcherFactory {

  public static PolygonMatcher create(SimpleFeatureSource featureSource, Algorithm algorithm) throws IOException {
    PolygonMatcher matcher = null;
    switch (algorithm) {
    case QUADTREE:
      matcher = new QuadtreePolygonMatcher();
      break;
    case RTREE:
      matcher = new STRtreePolygonMatcher();
      break;
    case FILTERING:
      matcher = new DumbPolygonMatcher();
      break;
    }
    matcher.init(featureSource);
    return matcher;
  }

}
