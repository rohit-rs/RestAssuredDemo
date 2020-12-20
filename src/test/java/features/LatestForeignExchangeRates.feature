Feature: Latest Foreign Exchange rates

  @RegressionTest @GetLatestRates
  Scenario: Get the latest foreign exchange reference rates
    Given I want to execute latest exchange reference rates endpoint
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
    Then I verify that rates for all currencies are present in response
     
@RegressionTest @GetLatestRates
  Scenario: Get the latest foreign exchange reference rates with one symbol
    Given I want to execute latest exchange reference rates endpoint with symbols "JPY"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
		Then I verify that rates for currencies "JPY" are present in response
		
		@RegressionTest @GetLatestRates
  Scenario: Get the latest foreign exchange reference rates with more than one symbols
    Given I want to execute latest exchange reference rates endpoint with symbols "INR,AUD,GBP"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
		Then I verify that rates for currencies "INR,AUD,GBP" are present in response

		@RegressionTest @GetLatestRates
  Scenario: Get the latest foreign exchange reference rates with base
    Given I want to execute latest exchange reference rates endpoint with base "INR"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "INR"
    Then I verify that rates for all currencies are present in response
    
@RegressionTest @GetLatestRates
  Scenario: Get the latest foreign exchange reference rates with symbols and base
    Given I want to execute latest exchange reference rates endpoint with symbol "DKK" and base "CZK"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "CZK"
		Then I verify that rates for currencies "DKK" are present in response
		
		
    
    