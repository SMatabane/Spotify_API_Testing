package com.spotify.utility;

import io.qameta.allure.restassured.AllureRestAssured;

import java.util.List;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseTest {
    

    public BaseTest() {
        RestAssured.filters(new AllureRestAssured()); // Enable Allure logging for all 
    }
    
    static {
	    // Initialize Log4j
	    DOMConfigurator.configure("logs4j.xml");
	}

    protected Response sendRequest(String endpoint, String method, String body) {
       Logs.info("Sending " + method + "request to:"+ endpoint);
        Response response = null;

        switch (method.toUpperCase()) {
            case "GET":
                response = RestAssured.given().header("Authorization", getToken()).get(endpoint);
                break;
            case "POST":
                response = RestAssured.given()
                        .header("Authorization", getToken())
                        .header("Content-Type", "application/json")
                        .body(body)
                        .post(endpoint);
                break;
                
            case "Put":
            	response = RestAssured.given()
                .header("Authorization", getToken())
                .header("Content-Type", "application/json")
                .body(body)
                .put(endpoint);
        break;
            default:
               // logger.error("Invalid HTTP method: {}", method);
            	Logs.error("Invalid HTTP method" + method);
        }

        Logs.info("Received response with status code: {}"+ response.getStatusCode());
        return response;
    }
    
    protected void validateErrorResponse(Response response, int expectedStatus, String expectedMessage) {
        JsonPath jsonPath = response.jsonPath();
        Logs.info("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code should match expected");

        Map<String, Object> error = jsonPath.getMap("error");
        Assert.assertNotNull(error, "'error' object should not be null");
        Assert.assertEquals((int) error.get("status"), expectedStatus, "Error status should match expected");
        Assert.assertEquals(error.get("message"), expectedMessage, "Error message should match expected");
    }
    
    protected void validateSuccessResponse(Response response, int expectedStatusCode, List<String> requiredFields) {
        // Validate HTTP status code
    	 Logs.info("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Unexpected status code!");

        // Parse JSON response
        JsonPath jsonPath = response.jsonPath();

        // Validate required fields
        for (String field : requiredFields) {
            Assert.assertNotNull(jsonPath.get(field), "Field '" + field + "' should not be null");
        }

       // logger.info("Response validation passed for status code: {}", expectedStatusCode);
        Logs.info("Response body: "+ response.getBody().asString());
    }



    protected String getToken() {
        // Token retrieval logic
        return "";
    }
}
