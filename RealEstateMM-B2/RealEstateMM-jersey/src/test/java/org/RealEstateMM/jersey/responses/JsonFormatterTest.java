package org.RealEstateMM.jersey.responses;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonFormatterTest {

	private static final String TITLE = "testTitle";
	private static final String FIELD = "myvalue";
	private static final String EXPECTED_JSON = "{\"testTitle\":\"myvalue\"}";
	
	@Test
	public void givenATitleAndAValueThenFieldToJsonShouldReturnAValidJsonString(){
		String json = JsonFormatter.fieldToJSON(TITLE, FIELD);
		assertEquals(EXPECTED_JSON, json);
	}
}
