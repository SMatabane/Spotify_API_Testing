package com.spotify.tests;

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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;



@Epic("Spotify API Tests")
@Feature("Playlist Management")
public class PlayList extends BaseTest{
	
	 private String validplaylistID,invalidplaylistID,validToken,userID;
	    private JsonNode playlistBody,updateBody,addItems,removeItems;
	
	@BeforeClass
    public void setup() {
        // Read test data from JSON file
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode testData = objectMapper.readTree(new File("src/test/resources/config.json"));

            // Extract values from JSON file
            validToken=testData.get("validToken").asText();
            userID=testData.get("userId").asText();
            validplaylistID=testData.get("validplaylistID").asText();
            invalidplaylistID=testData.get("invalidplayeListID").asText();
            removeItems=testData.get("removeItems");
            addItems=testData.get("addItems");
            playlistBody = testData.get("playlistBody");
            updateBody = testData.get("updateBody");

            Logs.info("Test data loaded successfully from testData.json");
        } catch (IOException e) {
            Logs.error("Failed to load test data from JSON file: " + e.getMessage());
            Assert.fail("Test aborted: Unable to load test data");
        }
    }
	
	//get Playlist
	@Test(priority = 1,enabled=false)
	@Severity(SeverityLevel.MINOR)
    @Description("Verify the API returns 200 ")
    @Step("Sending GET playlist request with valid token")
	public void testGetPlaylistWithvalidToken() {
        // Construct the endpoint to get the playlist
		String endpoint ="https://api.spotify.com/v1/playlists/" + validplaylistID   ;

        // Send GET request using the invalid token
        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
        validateSuccessResponse(response, 200, Arrays.asList("collaborative", "description", "followers"));


    }
	
	@Test(priority = 2,enabled=false)
	@Severity(SeverityLevel.MINOR)
    @Description("Verify the API returns 400 error")
    @Step("Sending GET request with invalid token")
	public void testGetPlaylistWithinvalidToken() {
        // Construct the endpoint to get the playlist
		String endpoint ="https://api.spotify.com/v1/playlists/" + invalidplaylistID ;

        // Send GET request using the invalid token
        Response response = sendRequest(endpoint, "GET", null); // No body for GET request
        validateErrorResponse(response, 400, "Bad Request invalid token and playlistID");

       
    }
	
	@Override
    protected String getToken() {
        // Return the invalid token for this test case
        return validToken;
    }
	
	//create new Playlist invalid userID
	@Test(priority = 3,enabled=true)
	@Severity(SeverityLevel.MINOR)
    @Description("Verify the API returns 400 error")
    @Step("Sending Post request with invalid userID")
	public void testCreatePlaylist() {
		 // Construct the endpoint to get the playlist
		String endpoint ="https://api.spotify.com/v1/users/" + userID + "/playlists";
		// Send Post request using the invalid token
        Response response = sendRequest(endpoint, "Post", playlistBody.toString()); 
        validateErrorResponse(response, 400, "Only valid bearer authentication supported");
		
	}
	
	//create new Playlist valid playID and valid token
		@Test(priority = 4,enabled=false)
		@Severity(SeverityLevel.MINOR)
	    @Description("Verify the API returns 201 error")
	    @Step("Sending Post request with valid playlist")
		public void testCreatePlaylistvalid() {
			 // Construct the endpoint to get the playlist
			String endpoint ="https://api.spotify.com/v1/users/" + userID + "/playlists";
			// Send Post request using the invalid token
	        Response response = sendRequest(endpoint, "Post", playlistBody.toString()); // No body for GET request
	        validateSuccessResponse(response, 201, Arrays.asList("collaborative", "description", "followers"));
			
		}
		
		//add items to playlist valid credentials 
		@Test(priority = 5,enabled=false)
		@Severity(SeverityLevel.MINOR)
	    @Description("Verify the API returns 201 sucess when adding new item to playlist")
	    @Step("Sending Post request add items to playlist with valid credentials")
		public void testAddPlaylist() {
			 // Construct the endpoint to get the playlist
			String endpoint ="https://api.spotify.com/v1/playlists/"+ validplaylistID+ "/tracks";
			// Send Post request using the valid token
	        Response response = sendRequest(endpoint, "Post", addItems.toString()); 
	        validateSuccessResponse(response, 200, Arrays.asList("snapshot_id"));
			
		}
		
		//add items to playlist invalid credentials 
				@Test(priority = 5,enabled=true)
				@Severity(SeverityLevel.MINOR)
			    @Description("Verify the API returns 400 bad request when adding new item to playlist")
			    @Step("Sending Post request add items to playlist with invalid credentials")
				public void testAddPlaylistInvalid() {
					 // Construct the endpoint to get the playlist
					String endpoint ="https://api.spotify.com/v1/playlists/"+ invalidplaylistID+ "/tracks";
					// Send Post request using the invalid token
			        Response response = sendRequest(endpoint, "Post", addItems.toString()); 
			        validateErrorResponse(response, 400, "Only valid bearer authentication supported");
					
				}
		
		
	//update the playlist
		@Test(priority = 6,enabled=false)
		@Severity(SeverityLevel.MINOR)
	    @Description("Verify the API returns 200 success when updating platlist")
	    @Step("Sending Post request with ivalid playlistId and token")
		public void testUpdatePlaylist() {
			 // Construct the endpoint to get the playlist
			String endpoint ="https://api.spotify.com/v1/playlists/"+ validplaylistID+ "/tracks";
			// Send Post request using the valid token
	        Response response = sendRequest(endpoint, "Post", updateBody.toString()); 
	        validateSuccessResponse(response, 200, Arrays.asList("snapshot_id"));
			
		}
    
	//delete items on playlist using valid credentials
				@Test(priority = 7,enabled=false)
				@Severity(SeverityLevel.MINOR)
			    @Description("Verify the API returns 200 success when deleting the item on playlist")
			    @Step("Sending Post request with ivalid playlistId and token")
				public void testDeletePlaylist() {
					 // Construct the endpoint to get the playlist
					String endpoint ="https://api.spotify.com/v1/playlists/"+ validplaylistID+ "/tracks";
					// Send Post request using the invalid token
			        Response response = sendRequest(endpoint, "Post", removeItems.toString()); 
			        validateSuccessResponse(response, 200, Arrays.asList("snapshot_id"));
					
				}
		
}

	
	
	
	
	
	


