package stepdefnitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utility.CommonFunctions;

public class StepDefinitions {

	@Given("^I want to execute latest exchange reference rates endpoint$")
	public void i_want_to_execute_latest_exchange_reference_rates_endpoint() {
		CommonFunctions.setURLForLatestRates();
	}

	@Given("^I want to execute latest exchange reference rates endpoint with symbols \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_symbol_endpoint(String symbols) {
		CommonFunctions.setURLForLatestRatesWithSymbols(symbols);
	}

	@Given("^I want to execute latest exchange reference rates endpoint with base \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_base_endpoint(String base) {
		CommonFunctions.setURLForLatestRatesWithBase(base);
	}

	@Given("^I want to execute latest exchange reference rates endpoint with symbol \"([^\"]*)\" and base \"([^\"]*)\"$")
	public void i_want_to_execute_latest_exchange_reference_rates_with_symbol_and_base_endpoint(String symbol,
			String base) {
		CommonFunctions.setURLForLatestRates(symbol, base);		
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint(String date) {
		CommonFunctions.setURLForHistoricalRates(date);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with symbols \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_symbols(String date, String symbols) {
		CommonFunctions.setURLForHistoricalRatesWithSymbols(date, symbols);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with base \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_base(String date, String base) {
		CommonFunctions.setURLForHistoricalRatesWithBase(date, base);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint for date \"([^\"]*)\" with symbol \"([^\"]*)\" and base \"([^\"]*)\"$")
	public void i_want_to_execute_specific_date_exchange_rates_endpoint_with_symbols_and_base(String date, String symbol, String base) {
		CommonFunctions.setURLForHistoricalRates(date, symbol, base);
	}

	@Given("^I want to execute historical foreign exchange rates endpoint with future date$")
	public void i_want_to_execute_specific_date_exchange_rates_with_future_date() {
		CommonFunctions.setURLForHistoricalRatesWithFutureDate();
	}

	@When("^I submit the get call$")
	public void i_submit_the_get_call() {
		CommonFunctions.submitGETcall();	
	}

	@Then("^I should get (\\d+) status code$")
	public void i_should_get_the_status_code(int statusCode) {
		CommonFunctions.verityStatusCode(statusCode);
	}

	@Then("^I verify that the base currency is \"([^\"]*)\"$")
	public void i_verify_that_the_base_rate_is(String baseCurrency) {
		CommonFunctions.verifyBaseCurrency(baseCurrency);
	}

	@Then("^I verify that rates for all currencies are present in response$")
	public void i_verify_that_all_currencies_rates_are_present_in_response() {
		CommonFunctions.verifyRatesForAllCurrenciesArePresentInRespones();
	}

	@Then("^I verify that rates for currencies \"([^\"]*)\" are present in response$")
	public void i_verify_that_rate_for_single_currencies_are_present_in_response(String currencies) {
		CommonFunctions.verifyRatesForCurrenciesArePresentInRespones(currencies);
	}

	@Then("^I verify that rates are displayed for date \"([^\"]*)\"$")
	public void i_verify_that_rate_are_displayed_for_specified_date(String date) {
	CommonFunctions.verifyRatesAreDisplacyedForSpecifiedDate(date);
	}

	@Then("^I verify that rates are displayed for current date$")
	public void i_verify_that_rate_are_displayed_for_current_date() {
		CommonFunctions.verifyRatesAreDisplacyedForCurrentDate();
	}
}