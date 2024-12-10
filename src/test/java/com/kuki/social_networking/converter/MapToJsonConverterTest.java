package com.kuki.social_networking.converter;

import com.kuki.social_networking.util.converter.MapToJsonConverter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapToJsonConverterTest {

    @Test
    public void testConvertToDatabaseColumn() {
        MapToJsonConverter mapToJsonConverter = new MapToJsonConverter();
        Map<String, Object> map = Map.of("key1", "value1");
        String result = mapToJsonConverter.convertToDatabaseColumn(map);
        assertEquals("{\"key1\":\"value1\"}", result);
    }

    @Test
    public void testConvertToEntityAttribute() {
        MapToJsonConverter mapToJsonConverter = new MapToJsonConverter();
        String json = "{\"key1\":\"value1\"}";
        Map<String, Object> result = mapToJsonConverter.convertToEntityAttribute(json);
        assertEquals(Map.of("key1", "value1"), result);
    }
}
