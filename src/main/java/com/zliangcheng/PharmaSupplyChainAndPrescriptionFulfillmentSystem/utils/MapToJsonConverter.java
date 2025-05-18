package com.zliangcheng.PharmaSupplyChainAndPrescriptionFulfillmentSystem.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter(autoApply = true)  // 自动应用转换器
public class MapToJsonConverter implements AttributeConverter<Map<Long, Integer>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Long, Integer> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }

    @Override
    public Map<Long, Integer> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(
                    dbData,
                    new TypeReference<Map<Long, Integer>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("JSON 反序列化失败", e);
        }
    }
}