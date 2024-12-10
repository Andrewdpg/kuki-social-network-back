package com.kuki.social_networking.util.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuki.social_networking.request.IngredientDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class StringToIngredientDTOListConverter implements Converter<String, List<IngredientDTO>> {
    private final ObjectMapper objectMapper;

    public StringToIngredientDTOListConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<IngredientDTO> convert(@Nullable String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<List<IngredientDTO>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert String to List<IngredientDTO>", e);
        }
    }
}