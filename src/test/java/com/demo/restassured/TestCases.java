package com.demo.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TestCases {
	@Test
	public void checkGetCallSuggestionsSize() {

		TestHelper.checkSizeOfGETCall(API_Test_Objects.GET_CALL_PATH,"suggestions",API_Test_Objects.EXPECTED_SUGGESTIONS);
	}
	
	@Test
	public void checkGetCallSuggestionsTermContainsString() {

		TestHelper.checkGETCallContainsString(API_Test_Objects.GET_CALL_PATH,
				API_Test_Objects.LOCATION_OF_TERM,
				API_Test_Objects.EXPECTED_STRING_FORM);
	}
	
	@Test
	public void checkGetCallPagesSize() {

		TestHelper.checkSizeOfGETCall(API_Test_Objects.GET_CALL_PATH,"pages",API_Test_Objects.EXPECTED_PAGES_COUNT);
	}
	
	@Test
	public void checkGetCallPagesTitleContainsWiley() {

		TestHelper.checkGETCallContainsString(API_Test_Objects.GET_CALL_PATH,
				API_Test_Objects.LOCATION_OF_TITLE,
				API_Test_Objects.EXPECTED_WILEY);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayInt() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"345",
				API_Test_Objects.OK_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayGreatMax() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"5177489647",
				API_Test_Objects.OK_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayLessMin() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"-4147453668",
				API_Test_Objects.OK_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWspaces() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"  78  ",
				API_Test_Objects.OK_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWdot() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"76.95",
				API_Test_Objects.OK_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberFloat() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"67.84f",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberLong() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"15000000000L",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberDouble() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"4556.54d",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
		
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWcomma() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"98,768",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayEmpty() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"",
				API_Test_Objects.NOT_FOUND_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayNaN() {

		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"NaN",
				API_Test_Objects.BAD_GETWAY_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayString() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"somestring",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayChar() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"s",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNull() {

		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"null",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayBoolean() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"true",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpecials() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"<(&",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpaces() {
		TestHelper.httpRequestResponseService(API_Test_Objects.HTTP_REQUEST_RESPONSE_PATH,
				"     ",
				API_Test_Objects.SERVER_ERROR_STATUS_CODE,
				API_Test_Objects.EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkGetReturnImage() {

		String imageActual;
		
		try {
			imageActual = new String ( Files.readAllBytes(Paths.get(API_Test_Objects.IMAGE_COMPARE_PATH)) );
			
			given()
			.when()
	        	.get(API_Test_Objects.GET_IMAGE_PATH)
	        .then()
		        .assertThat()
		        .statusCode(API_Test_Objects.OK_STATUS_CODE)
		       .and()
		        .contentType("image/png")
		       .and()
		        .header( "content-length", equalTo("8090") )
	        	.extract()	
	        	.response().getBody().asString().equals(imageActual)
			;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
