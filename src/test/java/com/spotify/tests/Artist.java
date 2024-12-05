package com.spotify.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.utility.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Artist extends BaseTest{
	
	protected static final Logger logger = LogManager.getLogger(Artist.class);
	private String invalidToken,invalidartistId,artistId;
	
	@BeforeClass
    public void setup() {
        // Read test data from JSON file
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode testData = objectMapper.readTree(new File("src/test/resources/config.json"));

            // Extract values from JSON file
            invalidToken=testData.get("invalidToken").asText();
            invalidartistId=testData.get("invalidartistId").asText();
            artistId=testData.get("invalidartistId").asText();
            logger.info("Test data loaded successfully from testData.json");
        } catch (IOException e) {
            logger.error("Failed to load test data from JSON file: " + e.getMessage());
            Assert.fail("Test aborted: Unable to load test data");
        }
    }
	
	//get artist albums with valid ID
			@Test(priority = 1,enabled=false)
			@Severity(SeverityLevel.NORMAL)
		    @Description("Verify the API returns 200 ")
		    @Step("Sending GET artists request with valid token")
			public void testGet() {
		        // Construct the endpoint to get the playlist
				String endpoint ="https://api.spotify.com/v1/artists/"+ artistId+ "/albums"  ;

		        // Send GET request using the invalid token
		        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
		        validateSuccessResponse(response, 200, Arrays.asList("items"));
		        
		        List<Map<String, Object>> albums = response.jsonPath().getList("items");

		        for (Map<String, Object> album : albums) {
		            Assert.assertNotNull(album.get("name"), "Album name should not be null");
		            Assert.assertNotNull(album.get("release_date"), "Release date should not be null");
		        }
		       
		    }
			
			//get artist with invalid ID and token
			@Test(priority = 2,enabled=false)
			@Severity(SeverityLevel.NORMAL)
		    @Description("Verify the API returns 401 ")
		    @Step("Sending GET artist albums request with invalid token")
			public void testGetArtistsAlbums() {
		        // Construct the endpoint to get the playlist
				String endpoint ="https://api.spotify.com/v1/artists/"+ invalidartistId+ "/albums"  ;

		        // Send GET request using the invalid token
		        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
		        validateErrorResponse(response, 401, "Invalid access token");
		       
		    }
			
			
			//get artist details
			@Test(priority = 3,enabled=false)
			@Severity(SeverityLevel.NORMAL)
		    @Description("Verify the API returns 200 ")
		    @Step("Sending GET artists request with valid token")
			public void testGetArtists() {
		        // Construct the endpoint to get the playlist
				String endpoint ="https://api.spotify.com/v1/artists/"+ artistId  ;

		        // Send GET request using the invalid token
		        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
		        validateSuccessResponse(response, 200, Arrays.asList("name", "genres", "popularity", "id"));
		       
		    }
			
		 //get artist details 
			@Test(priority = 3,enabled=false)
			@Severity(SeverityLevel.NORMAL)
		    @Description("Verify the API returns 401 ")
		    @Step("Sending GET artists request with invalid token")
			public void testGetArtistsInvalid() {
		        // Construct the endpoint to get the playlist
				String endpoint ="https://api.spotify.com/v1/artists/"+ artistId  ;

		        // Send GET request using the invalid token
		        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
		        validateErrorResponse(response, 401, "Invalid access token");
		       
		    }
			
			@Override
		    protected String getToken() {
		        // Return the invalid token for this test case
		        return invalidToken;
		    }

}
