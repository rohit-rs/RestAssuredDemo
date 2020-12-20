package runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = {"src/test/java/features"},
		glue = {"stepdefnitions"},
		monochrome = true,
		dryRun = false
		)

public class TestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeClass
	public void beforeTest() {
		System.out.println(">>Test started...");
	}
	
	@AfterClass
	public void afterTest() {
		System.out.println(">>Test completed...");
	}

}
