package pl.tomaszborecki.pip.domain.model;

import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PointInPolygon {

  private Point point;
  private SimpleFeature polygon;
  
  @Override
  public String toString() {
    return point + ", POLYGON: " + getPolygonName();
  }
  
  public String getPolygonName() {
    return polygon != null ? (String) polygon.getAttribute("NAME") : "None";
  }
  
}
