package com.spotify.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.utility.BaseTest;
import com.spotify.utility.Logs;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class User extends BaseTest{
	
	private String invalidToken,userID;
	
	@BeforeClass
    public void setup() {
        // Read test data from JSON file
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode testData = objectMapper.readTree(new File("src/test/resources/config.json"));

            // Extract values from JSON file
            invalidToken=testData.get("invalidToken").asText();
            userID=testData.get("userId").asText();
            Logs.info("Test data loaded successfully from testData.json");
        } catch (IOException e) {
            Logs.error("Failed to load test data from JSON file: " + e.getMessage());
            Assert.fail("Test aborted: Unable to load test data");
        }
    }
	
	//get user tracks
		@Test(priority = 1,enabled=false)
		@Severity(SeverityLevel.NORMAL)
	    @Description("Verify the API returns 200 ")
	    @Step("Sending GET tracks request with valid token")
		public void testGet() {
	        // Construct the endpoint to get the playlist
			String endpoint ="https://api.spotify.com/v1/me/tracks"  ;

	        // Send GET request using the invalid token
	        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
	        validateSuccessResponse(response, 200, Arrays.asList("items"));
	        List<Map<String, Object>> tracks = response.jsonPath().getList("items");

	        for (Map<String, Object> trackss : tracks) {
	            Assert.assertNotNull(trackss.get("album"), "Album name should not be null");
	            Assert.assertNotNull(trackss.get("artists"), "artists should not be null");
	        }
	       
	    }
		
		//get user tracks
				@Test(priority = 2,enabled=true)
				@Severity(SeverityLevel.NORMAL)
			    @Description("Verify the API returns 401 ")
			    @Step("Sending GET tracks request with invalid token")
				public void testGetInvalid() {
			        // Construct the endpoint to get the playlist
					String endpoint ="https://api.spotify.com/v1/me/tracks"  ;
			        // Send GET request using the invalid token
			        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
			        // Log the response details
			        validateErrorResponse(response, 401, "Invalid access token");
			        
			        
			       
			    }
       //GET User profile with valid token and userID
				@Test(priority = 3,enabled=false)
				@Severity(SeverityLevel.NORMAL)
			    @Description("Verify the API returns 200 ")
			    @Step("Sending GET tracks request with invalid token")
				public void testGetvalid() {
			        // Construct the endpoint to get the playlist
					String endpoint ="https://api.spotify.com/v1/users/" + userID  ;

			        // Send GET request using the invalid token
			        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
			        validateSuccessResponse(response, 200, Arrays.asList("display_name", "followers", "id"));
			       
			    }
				
				
	
		
				
				@Override
			    protected String getToken() {
			        // Return the invalid token for this test case
			        return invalidToken;
			    }
}
