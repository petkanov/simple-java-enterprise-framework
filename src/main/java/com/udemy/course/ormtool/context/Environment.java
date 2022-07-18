package com.udemy.course.ormtool.context;

import com.udemy.course.ormtool.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {
    private static final Logger log = LoggerFactory.getLogger(Environment.class);
    private final Map<String, String> PROPERTIES = new ConcurrentHashMap<>();

    public Environment(String propertiesFile) {
        try (InputStream propertiesInputStream = App.class.getClassLoader().getResourceAsStream(propertiesFile)) {

            Properties parsedProperties = new Properties();
            parsedProperties.load(propertiesInputStream);

            parsedProperties.forEach((key, value) -> PROPERTIES.put(key.toString(), value.toString()));

        } catch (Exception e) {
            log.error("Exception <{}> while loading properties from {}", e.getMessage(), propertiesFile);
            throw new RuntimeException(e);
        }
    }

    public void setProperty(String key, String value) {
        this.PROPERTIES.put(key, value);
    }

    public String getProperty(String key) {
        return PROPERTIES.get(key);
    }

    public<T> T getBeanOfType(Class<?> beanType) {
        T beanInstance = null;
        try {
            beanInstance = Framework.getBeanInstance(beanType);
        } catch (Exception e) {
            log.warn("Missing Bean of Type {} in Application Context", beanType);
        }
        return beanInstance;
    }
}
