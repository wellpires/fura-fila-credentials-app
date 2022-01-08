package br.com.furafila.credentialsapp.model.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Bit2BooleanConverter implements AttributeConverter<Boolean, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {

		Integer attributeValue = 0;
		if (Objects.nonNull(attribute) && attribute) {
			attributeValue = 1;
		}

		return attributeValue;

	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		return Objects.nonNull(dbData) && dbData == 1;
	}

}
