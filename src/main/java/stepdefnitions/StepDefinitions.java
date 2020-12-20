package stepdefnitions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions {

	private static final String BASE_URL = "https://api.ratesapi.io/api/";
	private static Response response;
	private static String URL;
	private static final String[] CURRENCIES = { "CHF", "HRK", "MXN", "ZAR", "INR", "THB", "CNY", "AUD", "ILS", "KRW",
			"JPY", "PLN", "GBP", "IDR", "HUF", "PHP", "TRY", "RUB", "HKD", "ISK", "DKK", "MYR", "CAD", "USD", "BGN",
			"NOK", "RON", "SGD", "CZK", "SEK", "NZD", "BRL" };

	@Given("^I want to execute latest exchange reference rates endpoint$")
	public void i_want_to_execute_latest_exchange_reference_rates_endpoint() {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + "latest";
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute latest exchange reference rates endpoint with symbols \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_symbol_endpoint(String symbol) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + "latest?symbols=" + symbol;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute latest exchange reference rates endpoint with base \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_base_endpoint(String base) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + "latest?base=" + base;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute latest exchange reference rates endpoint with symbol \"([^\"]*)\" and base \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_symbol_and_base_endpoint(String symbol,
			String base) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + "latest?base=" + base + "&symbols=" + symbol;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint(String date) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + date;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with symbols \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_symbols(String date, String symbols) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + date + "?symbols=" + symbols;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with base \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_base(String date, String base) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + date + "?base=" + base;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with symbol \"([^\"]*)\" and base \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_symbols_and_base(String date,
			String symbol, String base) {
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + date + "?base=" + base + "&symbols=" + symbol;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint with future date$")
	public void i_want_to_execute_specific_date_exchange_rates_with_future_date() {
		// Get a future date
		SimpleDateFormat formattter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 5);
		String futureDate = formattter.format(date.getTime());
		// Generate the URL
		RestAssured.baseURI = BASE_URL;
		URL = BASE_URL + futureDate;
		Reporter.log(">>URL is set to: " + URL, true);
	}

	@When("^I submit the get call$")
	public void i_submit_the_get_call() {
		response = RestAssured.given().get(URL);
		Reporter.log(">>GET call submitted to: " + URL, true);
	}

	@Then("^I should get (\\d+) status code$")
	public void i_should_get_the_status_code(int statusCode) {
		Reporter.log(">>Response status code is: " + response.getStatusCode(), true);
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}

	@Then("^I verify that the base currency is \"([^\"]*)\"$")
	public void i_verify_that_the_base_rate_is(String baseCurrency) {
		Reporter.log(">>Base exchange currency is: " + response.jsonPath().getString("base"), true);
		Assert.assertEquals(response.jsonPath().getString("base"), baseCurrency);
	}

	@Then("^I verify that rates for all currencies are present in response$")
	public void i_verify_that_all_currencies_rates_are_present_in_response() {
		Reporter.log(">>Response: " + response.getBody().asString(), true);
		Set<String> actualCurrenciesInResponse = new JSONObject(response.getBody().asString()).getJSONObject("rates")
				.keySet();
		if (response.jsonPath().getString("base").equals("EUR")) {
			Set<String> expectedCurrenciesInResponse = new HashSet<>(Arrays.asList(CURRENCIES));
			Assert.assertEquals(actualCurrenciesInResponse, expectedCurrenciesInResponse);
		} else {
			Set<String> expectedCurrenciesInResponse = new HashSet<>(Arrays.asList(CURRENCIES));
			expectedCurrenciesInResponse.add("EUR");
			Assert.assertEquals(actualCurrenciesInResponse, expectedCurrenciesInResponse);
		}
	}

	@Then("^I verify that rates for currencies \"([^\"]*)\" are present in response$")
	// In feature files, Enter currencies values separated by commas e.g.
	// "INR,USD,BKK".
	public void i_verify_that_rate_for_single_currencies_are_present_in_response(String currencies) {
		Reporter.log(">>Response: " + response.getBody().asString(), true);
		String[] currecnyArray = currencies.split(",");
		Set<String> actualCurrencyInResponse = new JSONObject(response.getBody().asString()).getJSONObject("rates")
				.keySet();
		Set<String> expectedCurrencyInResponse = new HashSet<>(Arrays.asList(currecnyArray));
		Assert.assertEquals(actualCurrencyInResponse, expectedCurrencyInResponse);
		Reporter.log(">>[Expected exchange currency: " + Arrays.toString(currecnyArray)
				+ " | Actual exchange currency: " + actualCurrencyInResponse.toString() + "]", true);
	}

	@Then("^I verify that rates are displayed for date \"([^\"]*)\"$")
	public void i_verify_that_rate_are_displayed_for_specified_date(String date) {
		// In feature file, please enter dates in "yyyy-MM-dd" format only.
		Reporter.log(">>[Expected exchange rate date: " + date + " | Actual exchange rate date: "
				+ response.jsonPath().getString("date") + "]", true);
		Assert.assertEquals(response.jsonPath().getString("date"), date);
	}

	@Then("^I verify that rates are displayed for current date$")
	public void i_verify_that_rate_are_displayed_for_current_date() {
		RequestSpecification requestLatest = RestAssured.given();
		Response responseForLatestDate = requestLatest.get("https://api.ratesapi.io/api/latest");
		String latestRatesDate = responseForLatestDate.jsonPath().getString("date");
		RequestSpecification requestFutureDate = RestAssured.given();
		Response responseForFutureDate = requestFutureDate.get(URL);
		String futureRatesDate = responseForFutureDate.jsonPath().getString("date");
		Reporter.log(">>[Latest exchange rate date: " + latestRatesDate + " | Exchange rate date for future dates: "
				+ futureRatesDate, true);		
		Assert.assertEquals(futureRatesDate, latestRatesDate);
	}
}
