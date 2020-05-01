Feature: This feature is to check Latest Foreign Exchange rates

@SmokeTest
@RegressionTest
Scenario: Check if user is able to submit GET API request

Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/latest" 
Then Response code "200" is expected

@RegressionTest
Scenario: Validate mandatory tag in response 

Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/latest" 
Then Validate mandatory tag in response
|base|rates|EUR|date|
# We can add more tags to verify in JSON response

@RegressionTest
Scenario: Validate all currencies are present in response body
Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/latest" 
Then Verify below currencies are present in response
|rates.GBP|rates.HKD|rates.IDR|rates.ILS|rates.DKK|rates.INR|rates.CHF|rates.MXN|rates.CZK|rates.SGD|rates.THB|rates.HRK|rates.MYR|rates.NOK|rates.CNY|rates.BGN|rates.PHP|rates.SEK|rates.PLN|rates.ZAR|rates.CAD|rates.ISK|rates.BRL|rates.RON|rates.NZD|rates.TRY|rates.JPY|rates.RUB|rates.KRW|rates.USD|rates.HUF|rates.AUD|

@RegressionTest
Scenario: Verify date returned date in response when endpoint is latest
Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/latest" 
Then Verify returned date in response

@RegressionTest
Scenario: An incorrect url or incomplete url is provided then expect response code as 400
Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/incorrecturl"  
Then Response code "400" is expected
