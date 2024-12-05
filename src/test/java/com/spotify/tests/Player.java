package com.spotify.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.utility.BaseTest;
import com.spotify.utility.Logs;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

@Epic("Spotify API Tests")
@Feature("Player Management")
public class Player extends BaseTest{
	 private String invalidToken;
	 private JsonNode updatePlayer;
	 
	 @BeforeClass
	    public void setup() {
	        // Read test data from JSON file
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode testData = objectMapper.readTree(new File("src/test/resources/config.json"));

	            // Extract values from JSON file
	            invalidToken=testData.get("invalidToken").asText();
	          //  validToken=testData.get("validToken").asText();
	            updatePlayer = testData.get("updatePlayer");

	            Logs.info("Test data loaded successfully from testData.json");
	        } catch (IOException e) {
	            Logs.error("Failed to load test data from JSON file: " + e.getMessage());
	            Assert.fail("Test aborted: Unable to load test data");
	        }
	    }
	 
	 //Start/Resume Playback
	 @Test(priority = 1,enabled=false)
		@Severity(SeverityLevel.BLOCKER)
	    @Description("Verify the API returns 204 ")
	    @Step("Sending PUT request with valid token and deviceID")
		public void testStartPlayback() {
	        // Construct the endpoint to get the playlist
			String endpoint ="https://api.spotify.com/v1/me/player/play" ;
	        Response response = sendRequest(endpoint, "Put", updatePlayer.toString()); 
	        validateSuccessResponse(response, 204, Arrays.asList("{}"));
	       
	    }
	 
	 //Start/Resume Playback
	 @Test(priority = 2,enabled=false)
		@Severity(SeverityLevel.CRITICAL)
	    @Description("Verify the API returns 401 ")
	    @Step("Sending PUT request with invalid token and deviceID")
	 public void testStartPlaybackInvalid() {
		 String endpoint ="https://api.spotify.com/v1/me/player/play" ;
	        Response response = sendRequest(endpoint, "Put", updatePlayer.toString()); 
	        validateErrorResponse(response, 401, "Invalid access token");
		 
	 }
	 
	 //skip next
	 @Test(priority = 3,enabled=true)
		@Severity(SeverityLevel.CRITICAL)
	    @Description("Verify the API returns 401 ")
	    @Step("Sending Post request with invalid token and deviceID")
	 public void testSkipNext() {
		 String endpoint ="https://api.spotify.com/v1/me/player/next" ;
	        
	        Response response = sendRequest(endpoint, "Post", " "); 
	        validateErrorResponse(response, 401, "Invalid access token");
	 }
	 
	 @Test(priority = 3,enabled=false)
		@Severity(SeverityLevel.CRITICAL)
	    @Description("Verify the API returns 204 ")
	    @Step("Sending Post request with valid token and deviceID")
	 public void testSkipNextValid() {
		 String endpoint ="https://api.spotify.com/v1/me/player/next" ;
	        Response response = sendRequest(endpoint, "Post", " "); 
	        validateSuccessResponse(response, 204, Arrays.asList("{}"));
		 
	 }
	 
	 //skip previous
	 @Test(priority = 5,enabled=true)
		@Severity(SeverityLevel.NORMAL)
	    @Description("Verify the API returns 401 ")
	    @Step("Sending Post request with invalid token and deviceID to skip previous songs")
	 public void testSkipprevious() {
		 String endpoint ="https://api.spotify.com/v1/me/player/previous" ;
	        Response response = sendRequest(endpoint, "Post", " "); 
	        validateErrorResponse(response, 401, "Invalid access token");
		 
	 }
	 
	 
	 //Get User Queue
	@Test(priority = 6,enabled=false)
	@Severity(SeverityLevel.NORMAL)
	@Description("Verify the API returns 401 ")
	@Step("Sending Post request with invalid token to get user queue")
	public void testGetQueue() {
		 String endpoint ="https://api.spotify.com/v1/me/player/queue" ;

	        
	        Response response = sendRequest(endpoint, "Get", null); 
	        validateErrorResponse(response, 401, "Invalid access token");
		 
	 }
	 
	//Get User Queue valid tokens
		@Test(priority = 6,enabled=true)
		@Severity(SeverityLevel.NORMAL)
		@Description("Verify the API returns 200 ")
		@Step("Sending Post request with valid token to get user queue")
		public void testGetQueueValid() {
			 String endpoint ="https://api.spotify.com/v1/me/player/queue" ;

		        
		        Response response = sendRequest(endpoint, "Get", null); 
		        validateSuccessResponse(response, 204, Arrays.asList("{}"));
			 
		 }
	
	
	
	 @Override
	    protected String getToken() {
	        // Return the invalid token for this test case
	        return invalidToken;
	    }
}
