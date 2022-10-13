package com.demo.restassured;

public class API_Test_Objects {

	static final String GET_CALL_PATH = "https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java";
	static final String HTTP_REQUEST_RESPONSE_PATH = "https://httpbin.org/delay/{delay}";
	static final String GET_IMAGE_PATH = "https://httpbin.org/image/png";
	static final String IMAGE_COMPARE_PATH = "src/test/java/com/demo/image.png";
	
	static final int EXPECTED_SUGGESTIONS = 4;
	static final String LOCATION_OF_TERM = "suggestions.term";
	static final String EXPECTED_STRING_FORM = "<span class=\"search-highlight\">java</span>";
	static final int EXPECTED_PAGES_COUNT = 4;
	static final String LOCATION_OF_TITLE = "pages.title";
	static final String EXPECTED_WILEY = "Wiley";
	
	static final long EXPECTED_MAX_TIME = 10L; 
	static final int OK_STATUS_CODE = 200;
	static final int SERVER_ERROR_STATUS_CODE = 500;
	static final int BAD_GETWAY_STATUS_CODE = 502;
	static final int NOT_FOUND_STATUS_CODE = 404;
}
