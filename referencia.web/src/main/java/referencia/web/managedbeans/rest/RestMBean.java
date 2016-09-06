package referencia.web.managedbeans.rest;

import java.io.IOException;
import java.util.Properties;

import javax.faces.bean.ManagedProperty;

import lombok.Setter;

public abstract class RestMBean {

    @ManagedProperty(value = "#{propertiesLoaderMBean}")
    @Setter
    protected PropertiesLoaderMBean propertiesLoaderMBean;

    protected String getServidor() throws IOException {

        Properties properties = propertiesLoaderMBean.loadProperties();

        return properties.getProperty("servidor");
    }

}
