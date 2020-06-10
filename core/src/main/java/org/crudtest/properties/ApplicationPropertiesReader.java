package org.crudtest.properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApplicationPropertiesReader {

    static Properties prop = new Properties();

    static {
        String properties = System.getProperty("crud-test.properties.path");
        if (properties != null) {
            Path path = Paths.get(properties);
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                prop.load(br);
            } catch (IOException e) {
                throw new RuntimeException("Failed load properties file for crud-test.properties.path.", e);
            }

        } else {
            try (InputStream is = ClassLoader.getSystemResourceAsStream("crud-test.properties")) {
                prop.load(is);
            } catch (IOException e) {
                throw new RuntimeException("Failed load properties file.", e);
            }
        }

        Map<Object, String> map = new HashMap<>();
        prop.keySet().forEach(key -> {
            String value = System.getProperty((String)key);
            if (value != null) {
                map.put(key, value);
            }
        });

        prop.putAll(map);
    }

}
