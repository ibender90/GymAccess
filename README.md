
## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+

To build and run the project, follow these steps:

-download project
-build project with Maven
-create a local Postgres database
-application is using RunConfig, you need to create one
-edit configuration according to your database
-add the same config for junit tests

DATASOURCE_PASSWORD=Password;
DATASOURCE_URL=url;
DATASOURCE_USERNAME=postgres;
JWT_SECRET=makeSecret;
PROFILE=yorProfileName;
SERVER_PORT=8080

run the app
use http://localhost:8080/swagger-ui/index.html to see available endpoints
-> The application will be available at http://localhost:8080.
