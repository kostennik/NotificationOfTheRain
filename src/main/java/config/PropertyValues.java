package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class PropertyValues {
    private String propFileName;
    private Properties prop;

    public PropertyValues(String propFileName) {
        this.propFileName = propFileName;
    }

    public String getPropValue(String value) {

        try {
            prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream(propFileName));


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        return prop.getProperty(value);
    }
}
