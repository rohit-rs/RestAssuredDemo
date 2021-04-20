Feature: Historical Foreign Exchange rates
	
	@RegressionTest @GetHistorialRates
  Scenario: Get the specific date foreign exchange reference rates
    Given I want to execute historical foreign exchange rates endpoint for date "2010-01-15"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
    Then I verify that rates are displayed for date "2010-01-15"
    
    @RegressionTest @GetHistorialRates
  Scenario: Get the specific date foreign exchange reference rates with one symbol
    Given I want to execute historical foreign exchange rates endpoint for date "2018-01-31" with symbols "RON"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
    Then I verify that rates are displayed for date "2018-01-31"
    Then I verify that rates for currencies "RON" are present in response
    
 @RegressionTest @GetHistorialRates
  Scenario: Get the specific date foreign exchange reference rates with multiple symbols
    Given I want to execute historical foreign exchange rates endpoint for date "2018-01-31" with symbols "USD,GBP"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
    Then I verify that rates are displayed for date "2018-01-31"
    Then I verify that rates for currencies "USD,GBP" are present in response
    
 @RegressionTest @GetHistorialRates
  Scenario: Get the specific date foreign exchange reference rates with base
    Given I want to execute historical foreign exchange rates endpoint for date "2013-10-10" with base "CZK"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "CZK"
    Then I verify that rates are displayed for date "2013-10-10"
    
    @RegressionTest @GetHistorialRates
  Scenario: Get the specific date foreign exchange reference rates with symbol and base
    Given I want to execute historical foreign exchange rates endpoint for date "2019-07-15" with symbol "GBP" and base "PLN"
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "PLN"
    Then I verify that rates are displayed for date "2019-07-15"
    Then I verify that rates for currencies "GBP" are present in response
    
    @RegressionTest @GetHistorialRates
  Scenario: Get the future dated foreign exchange reference rates
    Given I want to execute historical foreign exchange rates endpoint with future date
    When I submit the get call
    Then I should get 200 status code
    Then I verify that the base currency is "EUR"
    Then I verify that rates are displayed for current date
    