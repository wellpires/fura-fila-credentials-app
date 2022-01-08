package br.com.furafila.credentialsapp.model.converter;

import javax.persistence.AttributeConverter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class Bit2BooleanConverterTest {

	@Test
	public void shouldReturnTrueIfAttributeIsOne() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToDatabaseColumn(1);

		Assertions.assertTrue(columnValue);

	}

	@Test
	public void shouldReturnFalseIfAttributeIsZero() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToDatabaseColumn(0);

		Assertions.assertFalse(columnValue);

	}

	@Test
	public void shouldReturnFalseIfAttributeIsNull() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Boolean columnValue = converter.convertToDatabaseColumn(null);

		Assertions.assertFalse(columnValue);

	}

	@Test
	public void shouldReturnOneIfDatabaseColumnValueIsTrue() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToEntityAttribute(Boolean.TRUE);

		Assertions.assertEquals(attributeValue, 1);

	}	
	
	@Test
	public void shouldReturnZeroIfDatabaseColumnValueIsNull() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToEntityAttribute(null);

		Assertions.assertEquals(attributeValue, 0);

	}
	
	@Test
	public void shouldReturnZeroIfDatabaseColumnValueIsFalse() {

		AttributeConverter<Integer, Boolean> converter = new Bit2BooleanConverter();

		Integer attributeValue = converter.convertToEntityAttribute(Boolean.FALSE);

		Assertions.assertEquals(attributeValue, 0);

	}
	
}
