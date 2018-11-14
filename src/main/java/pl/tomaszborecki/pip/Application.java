package pl.tomaszborecki.pip;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.tomaszborecki.pip.domain.PolygonMatcher;
import pl.tomaszborecki.pip.domain.PolygonMatcherFactory;
import pl.tomaszborecki.pip.domain.model.Algorithm;
import pl.tomaszborecki.pip.infrastructure.DataHandler;
import pl.tomaszborecki.pip.service.RandomPointsGenerator;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private RandomPointsGenerator randomPointGenerator;

  @Autowired
  private DataHandler dataHandler;

  @Value("${pip.algorithm}")
  private Algorithm algorithm;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<Point> points = randomPointGenerator.generateRandomPoints();

    SimpleFeatureSource featureSource = dataHandler.getSimpleFeatureSource();

    Instant start = Instant.now();
    PolygonMatcher matcher = PolygonMatcherFactory.create(featureSource, algorithm);
    points.stream()
          .parallel()
          .map(p -> matcher.match(p))
          .forEach(System.out::println);
    Instant finish = Instant.now();
    System.out.println("time elapsed [ms]: " + Duration.between(start, finish).toMillis());
  }

}
