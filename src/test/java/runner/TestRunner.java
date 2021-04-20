package runner;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.restassured.response.Response;

@CucumberOptions(features = { "src/test/java/features" }, 
			glue = {"stepdefnitions" },
			monochrome = true, 
			dryRun = false)

public class TestRunner extends AbstractTestNGCucumberTests {

	protected static final String BASE_URL = "https://api.ratesapi.io/api/";
	protected static Response response;
	protected static String url;
	
	
	@BeforeClass
	public void beforeTest() {
		System.out.println(">>Test started...");
	}
	
	@AfterClass
	public void afterTest() {
		System.out.println(">>Test completed...");
	}

}
