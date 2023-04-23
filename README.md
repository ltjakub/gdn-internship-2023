# gdn-internship-2023
Jakub Śliwiński
This project is my solution for internship recruitment task coded in java. 
This application that provides a REST API for retrieving currency exchange rates that retrives data from the National Bankf of Poland (NBP) API. 
## Installation
### Clone the repository:
```
git clone https://github.com/ltjakub/gdn-internship-2023.git
```
### Navigate to the project directory:
```
cd gdn-internship-2023
```
### Build the project:
```
mvn clean install
```
## Running the Application
You can run the application by running the main class com.dynatrace.gdninternship2023.ExchangeRatesApplication in your IDE.
## Using the API
The API provides the following endpoints:
```
GET /api/nbp/{currencyCode}/date
```
Returns the exchange rates for the specified date.
```
GET /api/nbp/{currencyCode}/last/{quotations}/extremum
```
Returns minimum and maximum rate for the specified curreny over the last quotations.
```
GET /api/nbp/{currencyCode}/last/{quotations}/difference
```
Returns the highest difference in buy and ask rates for the specified currency over the last quotations.


To use the API, simply make a GET request to the desired endpoint. The currency code should be provided as a 3-letter code (e.g. USD, EUR). The quotations parameter should be an integer. The date parameters should be provided in the format yyyy-mm-dd.

## Swagger
Swagger is available at:
```
http://localhost:8080/swagger-ui/index.html
```
