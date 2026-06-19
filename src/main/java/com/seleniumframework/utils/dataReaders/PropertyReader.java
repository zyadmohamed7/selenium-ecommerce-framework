package com.seleniumframework.utils.dataReaders;

import com.seleniumframework.utils.logs.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class PropertyReader {
    public static final String PROPERTIES_PATH = "src/test/resources/test-data/";
    private static final Properties properties = loadProperties();

    private PropertyReader() {}

    public static Properties loadProperties() {
        Properties props = new Properties();
        Path resourcesPath = Paths.get(PROPERTIES_PATH);
        Logs.info("Starting to load property files from: " + resourcesPath.toAbsolutePath());
        
        if (Files.exists(resourcesPath) && Files.isDirectory(resourcesPath)) {
            try (Stream<Path> paths = Files.walk(resourcesPath)) {
                paths.filter(Files::isRegularFile)
                     .filter(p -> p.toString().endsWith(".properties"))
                     .forEach(path -> {
                         Logs.info("Loading property file: " + path.getFileName());
                         try (InputStream is = Files.newInputStream(path)) {
                             props.load(is);
                         } catch (IOException e) {
                             Logs.error("Failed to read file: " + path + " — " + e.getMessage());
                             throw new RuntimeException("Failed to read file: " + path, e);
                         }
                     });
            } catch (IOException e) {
                Logs.error("Failed to traverse resources directory: " + e.getMessage());
                throw new RuntimeException("Failed to traverse resources directory", e);
            }
        } else {
            Logs.warn("Resources directory does not exist: " + resourcesPath.toAbsolutePath());
        }
        
        Logs.info("Successfully loaded all property files");
        return props;
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            Logs.error("Key '" + key + "' not found in loaded properties");
            throw new RuntimeException("Key '" + key + "' not found in loaded properties");
        }
        return value;
    }

    public static String get(String key) {
        return getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
