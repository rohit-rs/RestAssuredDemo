package stepdefnitions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import runner.TestRunner;

public class CommonFunctions extends TestRunner {

	private static final String[] CURRENCIES = { "CHF", "HRK", "MXN", "ZAR", "INR", "THB", "CNY", "AUD", "ILS", "KRW",
			"JPY", "PLN", "GBP", "IDR", "HUF", "PHP", "TRY", "RUB", "HKD", "ISK", "DKK", "MYR", "CAD", "USD", "BGN",
			"NOK", "RON", "SGD", "CZK", "SEK", "NZD", "BRL" };

	public static void setURLForLatestRates() {
		url = BASE_URL + "latest";
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForLatestRatesWithSymbols(String symbols) {
		url = BASE_URL + "latest?symbols=" + symbols;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForLatestRatesWithBase(String base) {
		url = BASE_URL + "latest?base=" + base;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForLatestRates(String symbol, String base) {
		url = BASE_URL + "latest?base=" + base + "&symbols=" + symbol;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForHistoricalRates(String date) {
		url = BASE_URL + date;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForHistoricalRatesWithSymbols(String date, String symbols) {
		url = BASE_URL + date + "?symbols=" + symbols;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForHistoricalRatesWithBase(String date, String base) {
		url = BASE_URL + date + "?base=" + base;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForHistoricalRates(String date, String symbol, String base) {
		url = BASE_URL + date + "?base=" + base + "&symbols=" + symbol;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void setURLForHistoricalRatesWithFutureDate() {
		// Get a future date
		SimpleDateFormat formattter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 5);
		String futureDate = formattter.format(date.getTime());
		// Generate the URL
		url = BASE_URL + futureDate;
		Reporter.log(">>URL is set to: " + url, true);
	}

	public static void submitGETcall() {
		response = RestAssured.given().get(url);
		Reporter.log(">>GET call submitted to: " + url, true);
		Assert.assertEquals(response.getStatusCode(), 200);
		Reporter.log(response.getBody().asString(), true);
	}

	public static void verityStatusCode(int statusCode) {
		Reporter.log(">>Response status code is: " + response.getStatusCode(), true);
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}

	public static void verifyBaseCurrency(String baseCurrency) {
		Reporter.log(">>Base exchange currency is: " + response.jsonPath().getString("base"), true);
		Assert.assertEquals(response.jsonPath().getString("base"), baseCurrency);
	}

	public static void verifyRatesForAllCurrenciesArePresentInRespones() {
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

	public static void verifyRatesForCurrenciesArePresentInRespones(String currencies) {
		// In feature files, Enter currencies values separated by commas e.g.
		// "INR,USD,BKK".
		Reporter.log(">>Response: " + response.getBody().asString(), true);
		String[] currecnyArray = currencies.split(",");
		Set<String> actualCurrencyInResponse = new JSONObject(response.getBody().asString()).getJSONObject("rates")
				.keySet();
		Set<String> expectedCurrencyInResponse = new HashSet<>(Arrays.asList(currecnyArray));
		Assert.assertEquals(actualCurrencyInResponse, expectedCurrencyInResponse);
		Reporter.log(">>[Expected exchange currency: " + Arrays.toString(currecnyArray)
				+ " | Actual exchange currency: " + actualCurrencyInResponse.toString() + "]", true);
	}

	public static void verifyRatesAreDisplacyedForSpecifiedDate(String date) {
		// In feature file, please enter dates in "yyyy-MM-dd" format only.
		Reporter.log(">>[Expected exchange rate date: " + date + " | Actual exchange rate date: "
				+ response.jsonPath().getString("date") + "]", true);
		Assert.assertEquals(response.jsonPath().getString("date"), date);
	}

	public static void verifyRatesAreDisplacyedForCurrentDate() {
		RequestSpecification requestLatest = RestAssured.given();
		Response responseForLatestDate = requestLatest.get("https://api.ratesapi.io/api/latest");
		String latestRatesDate = responseForLatestDate.jsonPath().getString("date");
		RequestSpecification requestFutureDate = RestAssured.given();
		Response responseForFutureDate = requestFutureDate.get(url);
		String futureRatesDate = responseForFutureDate.jsonPath().getString("date");
		Reporter.log(">>[Latest exchange rate date: " + latestRatesDate + " | Exchange rate date for future dates: "
				+ futureRatesDate + "]", true);
		Assert.assertEquals(futureRatesDate, latestRatesDate);
	}

}
