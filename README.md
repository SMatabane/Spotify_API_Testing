# Spotify_API_Testing
This project tests various functionalities of the Spotify Web API using Rest Assured, TestNG, Log4j, and Allure Reporting. It includes positive and negative test scenarios for Player, Playlist, User, and Artists endpoints. **check master branch**

## Features
- Automated API Testing: Covers Spotify's core functionalities.
- Reusable Framework: Includes a BaseTest class for common utilities.
- Data-Driven Tests: Reads test data from JSON files for flexibility.
- Logging: Integrated with Log4j for detailed logs, saved in .logs files.
- Reporting: Generates Allure reports for detailed test execution results.

## Pre-Requisites
1. Spotify Developer Account
   - Sign up here.
   - Create a Spotify app to get your client ID and client secret.
   - Obtain your access token using Spotify's Authorization Code Flow
  
2. Tools
   - Java (JDK 8 or higher)
   -  Maven
   - Rest Assured
   - TestNG
   - Allure
   - Log4j
  
## Run Tests
 - **Using Maven** : mvn clean test
 - **Generate Allure Report** : allure serve allure-results

## Test Coverage
**Player**

*Positive Tests*:
   - Resume Playback
   - Skip to Next/Previous Track

*Negative Tests*:
- Invalid or expired token (401 Unauthorized).

**PlayList**

*Positive Tests*:
  - Create new playlist.
  - Add items to a playlist.
  - Get playlist details.
  - Update playlist details.
    
*Negative Tests*:
  - Invalid playlist ID (404 Not Found).
  - Invalid token (401 Unauthorized).
  - Invalid body parameters (400 Bad Request)

**User**

*Positive Tests*:
  - Retrieve user profile.
    
*Negative Tests*:
  - Invalid user ID (404 Not Found).
  - Missing authorization (401 Unauthorized).

**Artists**

*Positive Tests*:
 - Get artist details.
 - Retrieve artist albums.
   
*Negative Tests*:
  - Invalid artist ID (404 Not Found).
  - Invalid query parameters (400 Bad Request).

## Logging
- Log4j is configured to generate .logs files under the logs folder.
- Logs include detailed request and response information.

## Sample Allure Report
The Allure report provides a detailed overview of test results, including:

- Pass/Fail/Skip status.
- Severity levels for tests.
- Logs and screenshots for failed tests.

