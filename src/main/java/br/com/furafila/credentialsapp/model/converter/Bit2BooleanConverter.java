package br.com.furafila.credentialsapp.model.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Bit2BooleanConverter implements AttributeConverter<Integer, Boolean> {

	@Override
	public Boolean convertToDatabaseColumn(Integer attribute) {
		return Objects.nonNull(attribute) && attribute == 1;
	}

	@Override
	public Integer convertToEntityAttribute(Boolean dbData) {

		Integer attributeValue = 0;
		if (Objects.nonNull(dbData) && dbData) {
			attributeValue = 1;
		}

		return attributeValue;
	}

}
