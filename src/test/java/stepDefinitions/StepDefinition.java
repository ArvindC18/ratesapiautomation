package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class StepDefinition {
	
	
	private static Logger log = LogManager.getLogger(StepDefinition.class); 
	Response response ;
    private static JsonPath jsonPath;
    static String baseURL;
    
    @Before
    public static void setup() {

        try {
        	baseURL="https://api.ratesapi.io";
            RestAssured.baseURI = baseURL;

        } catch (Exception FileNotFoundException) {

          log.error("Environment Properties file not found");

        }

    }
	
    @Given("^execute service \"([^\"]*)\" API for Latest Foreign Exchange rates$")
    public void execute_service_Ratesapi_api_for_latest_foreign_exchange_rates(String service) {
		
    	log.info("We are going to execute service : "+service);
    		    
    }

    @When("^Submit the get request for endpoint \"([^\"]*)\"$")
    public void submit_the_get_request_for_endpoint(String url) {
	    
    	try {
    		
    		RequestSpecification request = RestAssured.given();
    		response = request.when().get(url);
    		jsonPath = new JsonPath(response.asString());
    		
    		log.info("Submitted get request: -> " + baseURL + url);
    	}
    	catch(Exception e){
    		log.error("failed to hit get request");
    		fail();    		
    		
    	}
	}

    @Then("^Response code \"([^\"]*)\" is expected$")
	public void verifyResponseStatusCode(int statusCD) {
	    	try {	
	    	assertEquals(statusCD, response.statusCode());
	    	log.info("Response status code is " + response.statusCode() + "And it matches with the expected status code");
	    	}
	    	catch(Exception e) {
	    		fail();
	    		log.error("Response status code is " + response.statusCode() + "and failed to match with expected status code");
	    	}
	    			       
		    
	}
    
    @Then("^Validate mandatory tag in response$")
    public void validate_mandatory_tag_in_response(DataTable data) {
    	try {
    		
    		List<List<String>> list = data.raw();
    		for(int i=0;i<list.get(0).size();i++) {
    		Assert.assertTrue(response.getBody().asString().contains(list.get(0).get(i)));
    		}    	
    		log.info("All mandatory tags are present in response body");
    		
    	}catch(Exception e) {
    		log.error("Mandatory tags are missing ");
    	}
    	
    		
    }
    
    @Then("^Verify below currencies are present in response$")
    public void verify_below_currencies_are_present_in_response(DataTable data) {
    		try {
    			List<List<String>> list = data.raw();
        		for(int i=0;i<list.get(0).size();i++) {
        			
        			String actualValue = jsonPath.getString(list.get(0).get(i)); //If rates.GDP is present in response then its value is returned else returned null
        			assertNotNull(actualValue);// If actualvalue is null then it will fail        	
        			
        		}
        		log.info("All currencies are present in response body");
    		}
    		catch(Exception e) {
    			fail();
    			log.error("some or anyone of the mentioned currencies are missing in response body");
    		}
    		    		
    }
    
    @And("^expected value \"([^\"]*)\" in response body \"([^\"]*)\"$")
    public void Verify_expected_response_with_Actual(String expected, String location) throws Throwable {
    	 try {

             String actualValue = jsonPath.getString(location);
             assertTrue(actualValue.contains(expected));
             log.info("Expected response are present in response body");

         } catch (Exception E) {

             fail();
             log.error("Expected response are missing in response body");

         }
    }

    
    @Then("^Verify returned date in response$")
    public void verify_returned_date_in_response() {	
        	
    	try {
    		
    		log.info("Verifying date in response...");
    		Calendar c = Calendar.getInstance();
        	Date Expdate = c.getTime();
        	c.setTime(Expdate);
        	
        	log.info("Today is " + c.getTime());
        	
        	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);        	
			
        	switch(dayOfWeek) {
        	case 0: 	//If todays date is sunday then compare date with friday
        			c.add(Calendar.DAY_OF_YEAR, -2);   
        			Expdate = c.getTime();
        			log.info("As today(or given date in request) is Sunday and currency market is closed, comparing response with friday...");
        			break;
        			
        	case 6: //If todays date is saturday then compare date with friday
        			c.add(Calendar.DAY_OF_YEAR, -1);
        			Expdate = c.getTime();
        			log.info("As today(or given date in request) is Saturday and currency market is closed, comparing response with friday...");
        			break;
        	default: 
        			
        	}
        	
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	String expectedDate = format.format(Expdate);
        	String actualDate = jsonPath.getString("date");
        	
            assertEquals(expectedDate, actualDate);	
            log.info("Date Verified successfully");
            
    	}catch(Exception e) {
    		fail();
    		log.error("failed to validate date");
    	}
    	
        	
     
    }    
  
}

