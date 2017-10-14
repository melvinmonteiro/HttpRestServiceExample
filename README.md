# HttpRestServiceExample

REST API storing invoices and searching in a SQL database. (Java and Spring)

## Functionality
```
Insertion of an invoice. The response must return the result of the insertion (the invoice entity data itself)
```

```
Search of invoice(s) by invoice_number or po_number criteria, supporting pagination by limit and offset. 
This will return the list of invoices matching the criteria sorted by created_at from newest to oldest.
```

## Getting Started

Download the zip from below url and unzip the project on your local machine
https://github.com/melvinmonteiro/HttpRestServiceExample

### Prerequisites
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
* [Maven 3.2](https://maven.apache.org/download.cgi) and above

```
Verify on command line/terminal for the version
For java run 
java -version

For maven run
mvn --version
```

### Starting the Application

Run the following steps
```
mvn clean verify cargo:run
```
You should see the tomcat8 container started successfully.
Make sure there are no errors 

Open url for client services to the rest api.
http://localhost:8080/client-index.html

You can also open the client-index.html in a browser.
The left section of the page creates a new Invoice. Below you can see the json format that is sent and received.
The right section allows you to search. 

Opening url
http://localhost:8080/v1/invoices
will give you all the list of urls.

The search api using below url 
http://localhost:8080/v1/invoices/offset/0/limit/1?searchQuery=ABC

The searchQuery will search for invoices or po numbers that contains "ABC"
The offset is page number and limit is number of results you want to see.

## Running the tests

The test cases will automatically run with the maven command.

### com.example.restservice.controller.InvoiceRestControllerTest
Above test case tests the json format for input and output.

### com.example.restservice.repository.InvoiceRepositoryTest
This class will test the Repository api.

## Structure of the packages.

### com.example.restservice.config
Contains the beans for jdbc connections and configuring dsl
### com.example.restservice.validation
Validation on the app using spring advice controller. Also used for BadRequests
### com.example.restservice.controller
Rest endpoint configuration
### com.example.restservice.repository
Query to databases 

## Built With

* [Tomcat](https://tomcat.apache.org) - The web container used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://projects.spring.io/spring-framework/)  - The web framework used
* [Database](http://www.h2database.com/html/main.html) - The embedded database
* [Query DSL](http://www.querydsl.com/) - SQL persistence framework
