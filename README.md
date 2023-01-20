# school-administration
Before starting up this app, please note the following:
- This app is developed using **MySQL** as database of choice
- Create a database named '_school-administration_', or otherwise can be configured in application.properties, under property _spring.datasource.url_ (along with database URL, _**localhost:3306**_ by default) 
- In application.properties file, configure database username and password as needed, under properties _spring.datasource.username_ and _spring.datasource.password_ respectively

You may run the app using the following command, via terminal
> mvn spring-boot:run

If database connection is configured properly, when the app is started, it will run Liquibase and create necessary tables for the app

Unless otherwise configured, you can access the app at **_localhost:8080/school-administration/api_** after successfully running it

Postman collection is provided in the repository

----------------------------------------------------
