package pl.tomaszborecki.pip.domain;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.tomaszborecki.pip.domain.model.PointInPolygon;
import pl.tomaszborecki.pip.infrastructure.DataHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DataHandler.class })
public class QuadtreePolygonMatcherTest {

  @Autowired
  private DataHandler dataHandler;

  private QuadtreePolygonMatcher matcher;

  @Before
  public void setUp() throws IOException {
    matcher = new QuadtreePolygonMatcher();
    matcher.init(dataHandler.getSimpleFeatureSource());
  }

  @Test
  public void testMatch_whenCentralPointIsChoosen_thenValidPolygon() {
    // given
    double longitude = 23.6;
    double latitude = 16.1;
    Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));

    // when
    PointInPolygon pointInPolygon = matcher.match(point);

    // then
    assertThat(pointInPolygon.getPolygon()).isNotNull();
    assertThat(pointInPolygon.getPolygonName()).isEqualTo("Chad");
  }

  @Test
  public void testMatch_whenNearToBoundaryPointIsChoosen_thenValidPolygon() {
    // given
    double longitude = -80.7949;
    double latitude = 25.3556;
    Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));

    // when
    PointInPolygon pointInPolygon = matcher.match(point);

    // then
    assertThat(pointInPolygon.getPolygon()).isNotNull();
    assertThat(pointInPolygon.getPolygonName()).isEqualTo("United States of America");
  }

  @Test
  public void testMatch_whenInnerBayPointIsChoosen_thenNone() {
    // given - Gulf of Mexico
    double longitude = -89.1799;
    double latitude = 30.0318;
    Point point = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));

    // when
    PointInPolygon pointInPolygon = matcher.match(point);

    // then
    assertThat(pointInPolygon.getPolygon()).isNull();
  }

}
