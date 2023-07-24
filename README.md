# MetricsApp
MetricsApp is a Java application that exposes a REST API for recording numerical metric values. It allows users to manage metrics, view specific metrics by ID, and retrieve metric summaries.

Prerequisites
-------------
Java JDK (11 or higher)
Spring Tool Suite (STS) or any IDE of your choice
SOAP UI or any API testing tool

INSTRUCTIONS
------------
1.Running the Application
-
-Clone the MetricsApp repository from GitHub
-Import the project into your IDE (e.g., Spring Tool Suite).
-Build the application using Maven
-The application will start on http://localhost:8080.
-You can import, build and run directly through your IDE

2.H2 Database Console
-
-Access the H2 database console at http://localhost:8080/h2-console.
-Use the following JDBC URL to connect to the database: jdbc:h2:mem:testdb
-No username or password is required.

3.Endpoints
-
1.Create a Metric
-Endpoint: POST /metrics
-http://localhost:8080/metrics 
Sample Input:
{
  "system": "Software",
  "name": "Clarity Software",
  "date": 1670660096000,
  "value": 89
}

2.Get Metric by ID
-Endpoint: GET /metrics/{id}
-http://localhost:8080/metrics/1
Sample Output:
{
  "id": 1,
  "system": "Software",
  "name": "Clarity Software",
  "date": 1670660096000,
  "value": 1
}

3.Get All Metrics
-Endpoint: GET /metrics
-http://localhost:8080/metrics
Sample Output:

[
{
"id": 1,
"system": "System",
"name": "Software Name",
"date": 1670660096000,
"value": 89
},
{
"id": 2,
"system": "System",
"name": "Software Name",
"date": 1670660096000,
 "value": 1
}
]

4.Update a Metric
-Endpoint: GET /metrics
-http://localhost:8080/metrics/1 
Sample Input:
{
  "system": "System",
  "name": "Software Name",
  "date": 1670660096000,
  "value": 89
}

5.Get Metric Summary
-Endpoint: GET /metrics/metricsummary
-http://localhost:8080/metrics/metricsummary
Sample Output:
{
  "system": null,
  "name": null,
  "from": null,
  "to": null,
  "value": 90
}

4.Testing
-
To test the application's API endpoints, you can use SOAP UI or any other API testing tool of your choice.
