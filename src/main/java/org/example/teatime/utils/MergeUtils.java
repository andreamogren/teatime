package org.example.teatime.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.util.Map;

public class MergeUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T merge(T target, Map<String, Object> updates) {
        ObjectReader updater = objectMapper.readerForUpdating(target);
        try {
            return updater.readValue(objectMapper.writeValueAsString(updates));
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge objects", e);
        }
    }
}
