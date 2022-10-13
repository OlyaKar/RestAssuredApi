package com.demo.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;

public class TestHelper {
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
