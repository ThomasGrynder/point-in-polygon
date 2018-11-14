# Point in polygon

PIP (point-in-polygon) problem for large number of points solved with usage of GeoTools library.

Running with Maven plugin:
mvn spring-boot:run
Example with optional parameters:
mvn spring-boot:run -Dspring-boot.run.arguments=--pip.points.amount=5,--pip.algorithm=QUADTREE
