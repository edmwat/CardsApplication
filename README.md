## CardsApplication
The API documentation is done using Swagger:
http://localhost:8082/swagger-ui/index.html#/
The application uses http basic with username and password to obtain access_token
## DUMMY DATA USED
Three accounts are created to demonstrate access management.
## Data for authentication
- Created 3 users. 1 admin account with ROLE_ADMIN, and 2 normal user accounts with ROLE_MEMBER.<br>
-username: "admin@gmail.com" with password: "password"<br>
-username: "user1@gmail.com" with password: "password"<br>
-username: "user2@gmail.com" with password: "password"<br>
- use the endpoint "localhost:8080/authenticate" with username and password provided above to get access token.
