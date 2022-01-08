package br.com.furafila.credentialsapp.model.converter;

import javax.persistence.AttributeConverter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class Bit2BooleanConverterTest {

	@Test
	public void shouldReturnTrueIfAttributeIsOne() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToDatabaseColumn(Boolean.TRUE);

		Assertions.assertEquals(attributeValue, 1);

	}

	@Test
	public void shouldReturnFalseIfAttributeIsZero() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToDatabaseColumn(Boolean.FALSE);

		Assertions.assertEquals(attributeValue, 0);

	}

	@Test
	public void shouldReturnFalseIfAttributeIsNull() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToDatabaseColumn(null);

		Assertions.assertEquals(attributeValue, 0);

	}

	@Test
	public void shouldReturnOneIfDatabaseColumnValueIsTrue() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToEntityAttribute(1);

		Assertions.assertTrue(columnValue);

	}

	@Test
	public void shouldReturnZeroIfDatabaseColumnValueIsNull() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToEntityAttribute(null);

		Assertions.assertFalse(columnValue);

	}

	@Test
	public void shouldReturnZeroIfDatabaseColumnValueIsFalse() {

		AttributeConverter<Boolean, Integer> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToEntityAttribute(0);

		Assertions.assertFalse(columnValue);

	}

}
