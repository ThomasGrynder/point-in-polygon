package pl.tomaszborecki.pip.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RandomPointsGenerator {

  private static double LATITUDE_LEFT_LIMIT = -90.0;
  private static double LATITUDE_RIGHT_LIMIT = 90.0;
  private static double LONGITUDE_LEFT_LIMIT = -180.0;
  private static double LONGITUDE_RIGHT_LIMIT = 180.0;

  @Value("${pip.points.amount}")
  private Integer amountOfPoints;

  public List<Point> generateRandomPoints() {
    return IntStream.range(0, amountOfPoints).mapToObj(i -> generateRandomPoint()).collect(Collectors.toList());
  }

  public Point generateRandomPoint() {
    return new GeometryFactory().createPoint(new Coordinate(randomLongitude(), randomLatitude()));
  }

  private double randomLatitude() {
    return LATITUDE_LEFT_LIMIT + (Math.random() * (LATITUDE_RIGHT_LIMIT - LATITUDE_LEFT_LIMIT));
  }

  private double randomLongitude() {
    return LONGITUDE_LEFT_LIMIT + (Math.random() * (LONGITUDE_RIGHT_LIMIT - LONGITUDE_LEFT_LIMIT));
  }

}
