package projeto.marketplace.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SimNaoConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Sim" : "Não";
    }
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Sim".equalsIgnoreCase(dbData);
    }
}