package pl.tomaszborecki.pip.infrastructure;

import java.io.File;
import java.io.IOException;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataHandler {

  public SimpleFeatureSource getSimpleFeatureSource() throws IOException {
    File file = new ClassPathResource("data/ne_110m_admin_0_countries.shp").getFile();
    FileDataStore store = FileDataStoreFinder.getDataStore(file);
    return store.getFeatureSource();
  }
  
}
