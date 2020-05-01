Feature: This feature is to check Specific date Foreign Exchange rates

@RegressionTest
Scenario: Verify if user is able to submit GET API request for specific date or greater than 1999-01-04 
Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/2020-04-30" 
Then Response code "200" is expected
And Validate mandatory tag in response
|base|rates|EUR|date|
And Verify below currencies are present in response
|rates.GBP|rates.HKD|rates.IDR|rates.ILS|rates.DKK|rates.INR|rates.CHF|rates.MXN|rates.CZK|rates.SGD|rates.THB|rates.HRK|rates.MYR|rates.NOK|rates.CNY|rates.BGN|rates.PHP|rates.SEK|rates.PLN|rates.ZAR|rates.CAD|rates.ISK|rates.BRL|rates.RON|rates.NZD|rates.TRY|rates.JPY|rates.RUB|rates.KRW|rates.USD|rates.HUF|rates.AUD|

@RegressionTest
Scenario: Verify if user is able to submit GET API request for the date less than 1999-01-04 

Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/1999-01-03" 
Then Response code "400" is expected
And expected value "There is no data for dates older then 1999-01-04." in response body "error"

@RegressionTest
Scenario: Verify if user is able to submit GET API request for the incorrect date format 

Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/2019-24-03" 
Then Response code "400" is expected
And expected value "does not match format '%Y-%m-%d'" in response body "error"

@SmokeTest
@RegressionTest
Scenario: Verify if user is able to submit GET API request for future date and expected date in response date should be todays date or fridays date if in case todays date is saturday or sunday

Given execute service "ratesapi" API for Latest Foreign Exchange rates
When Submit the get request for endpoint "/api/2020-06-22" 
Then Response code "200" is expected
Then Verify returned date in response