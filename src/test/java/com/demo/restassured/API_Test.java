package com.demo.restassured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class API_Test {

	private static final String GET_CALL_PATH = "https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java";
	private static final String HTTP_REQUEST_RESPONSE_PATH = "https://httpbin.org/delay/{delay}";
	private static final String GET_IMAGE_PATH = "https://httpbin.org/image/png";
	private static final String IMAGE_COMPARE_PATH = "src/test/java/com/demo/image.png";
	
	private final int EXPECTED_SUGGESTIONS = 4;
	private final String LOCATION_OF_TERM = "suggestions.term";
	private final String EXPECTED_STRING_FORM = "<span class=\"search-highlight\">java</span>";
	private final int EXPECTED_PAGES_COUNT = 4;
	private final String LOCATION_OF_TITLE = "pages.title";
	private final String EXPECTED_WILEY = "Wiley";
	
	private final long EXPECTED_MAX_TIME = 10L; 
	private final int OK_STATUS_CODE = 200;
	private final int SERVER_ERROR_STATUS_CODE = 500;
	private final int BAD_GETWAY_STATUS_CODE = 502;
	private final int NOT_FOUND_STATUS_CODE = 404;
	
	public final static class Helper {
		public static void checkSizeOfGETCall(String urlPast, String locationBody, int sizeOf) {

			given()
		    .when()
		        .get(urlPast)
		    .then()
		        .assertThat()
		        .body(locationBody,hasSize(sizeOf))
		    ;
		} 
		
		public static void checkGETCallContainsString(String urlPast,String locationBody,String compareString) {
			
			given()
		    .when()
		        .get(urlPast)
		    .then()
		        .assertThat()
		        .body(locationBody, everyItem(containsString(compareString)))
		    ;
		}
		
		public static void httpRequestResponseService(String urlPast,String delay,int statusCode, long timeDelay) {
			
			given()
			.when()
				.post(urlPast, delay)
			.then()
				.assertThat()
	        	.statusCode(statusCode)
	        	.time(lessThan(timeDelay),TimeUnit.SECONDS)
			;
		}
	}
	
	@Test
	public void checkGetCallSuggestionsSize() {

		Helper.checkSizeOfGETCall(GET_CALL_PATH,"suggestions",EXPECTED_SUGGESTIONS);
	}
	
	@Test
	public void checkGetCallSuggestionsTermContainsString() {

		Helper.checkGETCallContainsString(GET_CALL_PATH,
				LOCATION_OF_TERM,
				EXPECTED_STRING_FORM);
	}
	
	@Test
	public void checkGetCallPagesSize() {

		Helper.checkSizeOfGETCall(GET_CALL_PATH,"pages",EXPECTED_PAGES_COUNT);
	}
	
	@Test
	public void checkGetCallPagesTitleContainsWiley() {

		Helper.checkGETCallContainsString(GET_CALL_PATH,
				LOCATION_OF_TITLE,
				EXPECTED_WILEY);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayInt() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"345",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayGreatMax() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"5177489647",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayLessMin() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"-4147453668",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWspaces() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"  78  ",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWdot() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"76.95",
				OK_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberFloat() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"67.84f",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberLong() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"15000000000L",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberDouble() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"4556.54d",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
		
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNumberWcomma() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"98,768",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayEmpty() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"",
				NOT_FOUND_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}

	@Test
	public void checkHttpRequestResponseServiceDelayNaN() {

		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"NaN",
				BAD_GETWAY_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayString() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"somestring",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayChar() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"s",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayNull() {

		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"null",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelayBoolean() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"true",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpecials() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
				"<(&",
				SERVER_ERROR_STATUS_CODE,
				EXPECTED_MAX_TIME);
	}
	
	@Test
	public void checkHttpRequestResponseServiceDelaySpaces() {
		Helper.httpRequestResponseService(HTTP_REQUEST_RESPONSE_PATH,
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
