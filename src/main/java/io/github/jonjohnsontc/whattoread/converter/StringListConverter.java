package io.github.jonjohnsontc.whattoread.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null || attribute.isEmpty()) return null;
        return "{" + attribute.stream()
                .map(s -> "\"" + s.replace("\"", "\\\"") + "\"")
                .collect(Collectors.joining(",")) + "}";
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() < 2) return Collections.emptyList();
        String content = dbData.substring(1, dbData.length() - 1); // remove {}
        if (content.isEmpty()) return Collections.emptyList();
        return Arrays.stream(content.split(","))
                .map(s -> s.replaceAll("^\"|\"$", "").replace("\\\"", "\""))
                .collect(Collectors.toList());
    }
}