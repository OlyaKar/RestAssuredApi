package com.demo.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TestCases {
	
	private final String GET_CALL_PATH = "https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java";
	private final String HTTP_REQUEST_RESPONSE_PATH = "https://httpbin.org/delay/{delay}";
	private final String GET_IMAGE_PATH = "https://httpbin.org/image/png";
	private final String IMAGE_COMPARE_PATH = "src/test/java/com/demo/image.png";
	
	private final String LOCATION_OF_TERM = "suggestions.term";
	private final String LOCATION_OF_TITLE = "pages.title";
	
	private final int EXPECTED_SUGGESTIONS = 4;
	private final String EXPECTED_STRING_FORM = "<span class=\"search-highlight\">java</span>";
	private final int EXPECTED_PAGES_COUNT = 4;
	private final String EXPECTED_WILEY = "Wiley";
	private final long EXPECTED_MAX_TIME = 10L; 
	
	private final int OK_STATUS_CODE = 200;
	private final int SERVER_ERROR_STATUS_CODE = 500;
	private final int BAD_GETWAY_STATUS_CODE = 502;
	private final int NOT_FOUND_STATUS_CODE = 404;
	
	@Test
	public void checkGetCallSuggestionsSize() {

		TestHelper.checkSizeOfGETCall(GET_CALL_PATH,"suggestions",EXPECTED_SUGGESTIONS);
	}
	
	@Test
	public void checkGetCallSuggestionsTermContainsString() {

		TestHelper.checkGETCallContainsString(GET_CALL_PATH,
				LOCATION_OF_TERM,
				EXPECTED_STRING_FORM);
	}
	
	@Test
	public void checkGetCallPagesSize() {

		TestHelper.checkSizeOfGETCall(GET_CALL_PATH,"pages",EXPECTED_PAGES_COUNT);
	}
	
	@Test
	public void checkGetCallPagesTitleContainsWiley() {

		TestHelper.checkGETCallContainsString(GET_CALL_PATH,
				LOCATION_OF_TITLE,
				EXPECTED_WILEY);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayInt() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"345",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayGreatMax() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"5177489647",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayLessMin() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"-4147453668",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWspaces() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"  78  ",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWdot() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"76.95",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberFloat() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"67.84f",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberLong() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"15000000000L",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberDouble() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"4556.54d",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
		
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWcomma() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"98,768",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayEmpty() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"",
				NOT_FOUND_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayNaN() {

		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"NaN",
				BAD_GETWAY_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayString() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"somestring",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayChar() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"s",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNull() {

		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"null",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayBoolean() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"true",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpecials() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"<(&",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpaces() {
		TestHelper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"     ",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkGetReturnImage() {

		String imageActual;
		
		try {
			imageActual = new String ( Files.readAllBytes(Paths.get(IMAGE_COMPARE_PATH)) );
			
			given()
			.when()
	        	.get(GET_IMAGE_PATH)
	        .then()
		        .assertThat()
		        .statusCode(OK_STATUS_CODE)
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
