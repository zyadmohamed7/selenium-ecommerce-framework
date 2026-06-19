package com.seleniumframework.utils.dataReaders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seleniumframework.utils.logs.Logs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonReader {
    public static final String JSON_PATH = "src/test/resources/test-data/";

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonReader() {}

    public static Map<String, Object> readToMap(String relativeFilePath) {
        Logs.info("Reading JSON file to Map: " + relativeFilePath);
        File file = new File(relativeFilePath);
        if (!file.exists()) {
            Logs.error("JSON file not found: " + file.getAbsolutePath());
            throw new RuntimeException("JSON file not found: " + relativeFilePath);
        }
        try {
            return mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            Logs.error("Failed to parse JSON file: " + relativeFilePath + " — " + e.getMessage());
            throw new RuntimeException("Failed to parse JSON file: " + relativeFilePath, e);
        }
    }

    public static <T> T readToClass(String relativeFilePath, Class<T> clazz) {
        Logs.info("Reading JSON file to class " + clazz.getSimpleName() + ": " + relativeFilePath);
        File file = new File(relativeFilePath);
        if (!file.exists()) {
            Logs.error("JSON file not found: " + file.getAbsolutePath());
            throw new RuntimeException("JSON file not found: " + relativeFilePath);
        }
        try {
            return mapper.readValue(file, clazz);
        } catch (IOException e) {
            Logs.error("Failed to deserialize JSON file: " + relativeFilePath + " to class " + clazz.getSimpleName() + " — " + e.getMessage());
            throw new RuntimeException("Failed to deserialize JSON file: " + relativeFilePath, e);
        }
    }
}
