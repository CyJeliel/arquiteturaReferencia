package referencia.web.managedbeans.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "propertiesLoaderMBean")
@ApplicationScoped
public class PropertiesLoaderMBean {

    public Properties loadProperties() throws IOException {

        String fileName = System.getProperty("jboss.server.config.dir") + "/referencia.properties";

        Path path = Paths.get(fileName);

        InputStream inputStream = Files.newInputStream(path);

        Properties properties = new Properties();

        properties.load(inputStream);

        return properties;
    }
}
