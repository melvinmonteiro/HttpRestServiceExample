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

Download the zip from below url and unzip the project on your local machine.
cd to the folder (HttpRestServiceExample-1.0) that contains the pom.xml
https://github.com/melvinmonteiro/HttpRestServiceExample/archive/1.0.zip

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

Run the following step
```
mvn clean verify cargo:run
```
You should see the tomcat8 container started successfully.
Make sure there are no errors as it will run test cases. The application uses a embedded H2 database. 

Open url for client services to the rest api.
http://localhost:8080/client-index.html

The client service is build using angular js and bootstrap.
You can also open the client-index.html directly in a browser.

![Client UI](/screenshot.jpg?raw=true "Client UI")

The left section of the page allows you to create a new Invoice. Below the left section you can see the json format that will be sent as you update the input field. After you add the invoice, you will see a message and a json output format.

The right section allows you to search after clicking the Search Invoice(s) button. Note that the search is a contains search on the po number or invoice number and sorted by created date. After you hit search you can   

Opening url http://localhost:8080/v1/invoices will give you all the list of urls.

#### Saving
Internally the save api uses below url http://localhost:8080/v1/invoices
The json body will look like
```
{
  "invoice_number": "ABC12345",
  "po_number": "X1B23C4D5E",
  "amount_cents": "100000",
  "due_date": "2017-03-15"
}
```
After saving the resulting json

```
{
  "id": 1,
  "invoice_number": "ABC12345",
  "po_number": "X1B23C4D5E",
  "due_date": "2017-03-15",
  "amount_cents": 100000,
  "created_at": "2017-10-13T22:23:08Z"
}
```

#### Searching
Internally the search api uses below url http://localhost:8080/v1/invoices/offset/0/limit/1?searchQuery=ABC

The searchQuery will search for invoices or po numbers that contains "ABC"
The offset is page number and limit is number of results you want to see.

## Structure of the packages.

### com.example.restservice.config
Contains the beans for jdbc connections and configuring dsl

### com.example.restservice.validation
Validation on the app using spring advice controller. Also used for BadRequests

### com.example.restservice.controller
Rest endpoint configuration

### com.example.restservice.repository
Query to databases 

### com.example.restservice.model
A java bean for the invoice 

### com.example.restservice.model.serializers
Serializers for the json input

### com.example.restservice.model.deserializers
Serializers for the json output

## Running the tests

The test cases will automatically run with the maven command.
You can also use a editor to run the test cases. The test cases use a embedded H2 database in memory and does not need the application running

### com.example.restservice.controller.InvoiceRestControllerTest
Above test case tests the json format for input and output.

### com.example.restservice.repository.InvoiceRepositoryTest
This class will test the Repository api.


## Built With

* [Tomcat](https://tomcat.apache.org) - The web container used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://projects.spring.io/spring-framework/)  - The web framework used
* [Database](http://www.h2database.com/html/main.html) - The embedded database
* [Query DSL](http://www.querydsl.com/) - SQL persistence framework
