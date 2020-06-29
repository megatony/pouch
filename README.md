## pouch
Spring Boot REST API services with H2DB. An example for backend CRUD API to use on B2B order infrastructure.

## Technology
Spring Boot, Spring Security, H2DB, Docker, Swagger2

## Build
Build files may be changed according to running environments dev, test and prod. Instructions are based on dev environment.
After installation of Docker, you may run "docker-compose -f docker-compose.dev.yml build" from command line.

## Run
After a successful build, you should run "docker-compose -f docker-compose.dev.yml up" from command line. Application runs on http://localhost:8888 address.

## Authentication
API services and documentation requires authentication. Default admin username : admin, password : admin. Customer username : customer, password : customer. HTTP basic authentication is used.

## API documentation
http://localhost:8888/swagger-ui.html
