package com.seleniumframework.utils.dataReaders;

import com.seleniumframework.utils.logs.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = loadProperties();

    private ConfigReader() {}

    private static Properties loadProperties() {
        Properties loadedProperties = new Properties();

        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException(CONFIG_FILE + " was not found on the classpath");
            }

            loadedProperties.load(inputStream);
            Logs.info(CONFIG_FILE, "loaded successfully");
            return loadedProperties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + CONFIG_FILE, e);
        }
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return cleanValue(systemValue);
        }

        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key '" + key + "' not found in " + CONFIG_FILE);
        }

        return cleanValue(value);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    private static String cleanValue(String value) {
        String trimmedValue = value.trim();
        if (trimmedValue.length() >= 2 && trimmedValue.startsWith("\"") && trimmedValue.endsWith("\"")) {
            return trimmedValue.substring(1, trimmedValue.length() - 1);
        }
        return trimmedValue;
    }
}
