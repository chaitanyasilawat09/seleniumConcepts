# API Testing with RestAssured - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. API Testing Fundamentals
2. REST API Concepts
3. HTTP Methods and Status Codes
4. JSON and XML
5. RestAssured Fundamentals
6. RestAssured Setup and Configuration
7. GET Requests
8. POST Requests
9. PUT and PATCH Requests
10. DELETE Requests
11. Request Headers and Parameters
12. Authentication in RestAssured
13. Response Validation
14. JSON Path Queries
15. XML Path Queries
16. File Upload and Download
17. Cookie Handling
18. Response Parsing
19. Data-Driven API Testing
20. API Testing with TestNG
21. API Testing Framework Design
22. Mock API Testing with WireMock
23. API Security Testing
24. Performance Testing with RestAssured
25. Common Interview Questions

---

## 1. API TESTING FUNDAMENTALS

### What is API Testing?
API (Application Programming Interface) testing involves testing APIs directly to ensure they meet functionality, reliability, performance, and security requirements. Unlike UI testing, API testing bypasses the user interface and communicates directly with the backend.

### Why API Testing?

**Advantages:**
- **Faster Execution**: No UI rendering, direct communication
- **Early Testing**: Can test before UI is ready
- **Language Independent**: Test in any language
- **Cost Effective**: Less maintenance than UI tests
- **Better Coverage**: Test all endpoints thoroughly
- **Integration Testing**: Test component interactions

**When to Use:**
- Testing microservices
- Testing backend logic
- Integration testing
- Contract testing
- Performance testing

### API Testing vs UI Testing

| Aspect | API Testing | UI Testing |
|--------|------------|------------|
| Speed | Fast | Slow |
| Maintenance | Low | High |
| Coverage | High | Medium |
| Early Testing | Yes | No |
| User Experience | No | Yes |
| Cost | Low | High |

### Types of API Testing

**1. Functional Testing:**
- Verify API functionality
- Test positive and negative scenarios
- Validate business logic

**2. Integration Testing:**
- Test API interactions
- Test data flow between components
- Test database integration

**3. Performance Testing:**
- Load testing
- Stress testing
- Response time validation

**4. Security Testing:**
- Authentication testing
- Authorization testing
- SQL injection testing
- XSS testing

**5. Contract Testing:**
- Verify API contract compliance
- Consumer-driven contract testing
- Provider contract testing

### Interview Questions

**Q1: What is API testing and why is it important?**
- Testing APIs directly without UI
- Faster, more reliable than UI testing
- Can test before UI is ready
- Better coverage of backend logic

**Q2: What are the advantages of API testing over UI testing?**
- Faster execution
- Lower maintenance
- Early testing capability
- Language independent
- Better coverage

**Q3: What are the different types of API testing?**
- Functional testing
- Integration testing
- Performance testing
- Security testing
- Contract testing

---

## 2. REST API CONCEPTS

### What is REST?
REST (Representational State Transfer) is an architectural style for designing networked applications. It uses HTTP requests to perform CRUD operations on resources.

### REST Principles

**1. Resource-Based:**
- Everything is a resource
- Resources are identified by URIs
- Examples: /users, /products, /orders

**2. Uniform Interface:**
- Consistent interface for all resources
- Standard HTTP methods
- Standard status codes

**3. Stateless:**
- Each request contains all information
- No server-side session state
- Scalable architecture

**4. Client-Server:**
- Separation of concerns
- Client and server independent
- Can evolve separately

**5. Cacheable:**
- Responses should be cacheable
- Improves performance
- Reduces server load

**6. Layered System:**
- Can have multiple layers
- Client doesn't know about layers
- Load balancing, caching, etc.

### REST Resources

**Resource Identification:**
```
/users          - Collection of users
/users/123      - Specific user
/users/123/orders - Orders for user 123
```

**Resource Representation:**
- JSON (most common)
- XML
- HTML
- Plain text

### REST vs SOAP

| Feature | REST | SOAP |
|---------|------|------|
| Protocol | HTTP | HTTP, SMTP, etc. |
| Format | JSON, XML | XML only |
| WSDL | No | Yes |
| State | Stateless | Can be stateful |
| Complexity | Simple | Complex |
| Bandwidth | Low | High |

### Interview Questions

**Q1: What is REST and its key principles?**
- Architectural style for APIs
- Resource-based, uniform interface
- Stateless, client-server
- Cacheable, layered system

**Q2: What is the difference between REST and SOAP?**
- REST: Simple, JSON, stateless
- SOAP: Complex, XML, can be stateful
- REST: No WSDL
- SOAP: Has WSDL

**Q3: What are REST resources?**
- Entities identified by URIs
- Examples: /users, /products
- Can be collections or individual
- Represented as JSON/XML

---

## 3. HTTP METHODS AND STATUS CODES

### HTTP Methods

**GET:**
- Retrieve data
- Idempotent (same result every time)
- Safe (doesn't modify server)
- Example: GET /users

**POST:**
- Create new resource
- Not idempotent
- Not safe
- Example: POST /users

**PUT:**
- Update entire resource
- Idempotent
- Not safe
- Example: PUT /users/123

**PATCH:**
- Partial update
- Not idempotent
- Not safe
- Example: PATCH /users/123

**DELETE:**
- Delete resource
- Idempotent
- Not safe
- Example: DELETE /users/123

**HEAD:**
- Get headers only
- Idempotent
- Safe
- Example: HEAD /users

**OPTIONS:**
- Get allowed methods
- Idempotent
- Safe
- Example: OPTIONS /users

### HTTP Status Codes

**1xx Informational:**
- 100 Continue
- 101 Switching Protocols

**2xx Success:**
- 200 OK
- 201 Created
- 204 No Content

**3xx Redirection:**
- 301 Moved Permanently
- 302 Found
- 304 Not Modified

**4xx Client Error:**
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 405 Method Not Allowed
- 409 Conflict
- 422 Unprocessable Entity
- 429 Too Many Requests

**5xx Server Error:**
- 500 Internal Server Error
- 502 Bad Gateway
- 503 Service Unavailable
- 504 Gateway Timeout

### Interview Questions

**Q1: What are the different HTTP methods?**
- GET: Retrieve data
- POST: Create resource
- PUT: Update entire resource
- PATCH: Partial update
- DELETE: Delete resource

**Q2: What is the difference between PUT and PATCH?**
- PUT: Update entire resource
- PATCH: Partial update
- PUT is idempotent
- PATCH is not idempotent

**Q3: What are common HTTP status codes?**
- 200: OK
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 404: Not Found
- 500: Internal Server Error

---

## 4. JSON AND XML

### JSON (JavaScript Object Notation)

**JSON Structure:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "address": {
    "street": "123 Main St",
    "city": "New York"
  },
  "hobbies": ["reading", "gaming"]
}
```

**JSON Data Types:**
- String: "text"
- Number: 123, 45.67
- Boolean: true, false
- Array: [1, 2, 3]
- Object: {"key": "value"}
- Null: null

### XML (eXtensible Markup Language)

**XML Structure:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<user>
  <id>1</id>
  <name>John Doe</name>
  <email>john@example.com</email>
  <address>
    <street>123 Main St</street>
    <city>New York</city>
  </address>
  <hobbies>
    <hobby>reading</hobby>
    <hobby>gaming</hobby>
  </hobbies>
</user>
```

### JSON vs XML

| Feature | JSON | XML |
|---------|------|-----|
| Readability | High | Medium |
| Size | Small | Large |
| Parsing | Fast | Slower |
| Attributes | No | Yes |
| Namespaces | No | Yes |
| Comments | No | Yes |

### Interview Questions

**Q1: What is JSON and its data types?**
- JavaScript Object Notation
- String, Number, Boolean, Array, Object, Null
- Lightweight data interchange format
- Easy to parse and generate

**Q2: What is the difference between JSON and XML?**
- JSON: Smaller, faster, no attributes
- XML: Larger, supports attributes, namespaces
- JSON: More readable
- XML: More features

**Q3: When would you use JSON vs XML?**
- JSON: Web APIs, modern applications
- XML: Legacy systems, document formats
- JSON: Simpler, faster
- XML: More structured, metadata

---

## 5. RESTASSURED FUNDAMENTALS

### What is RestAssured?
RestAssured is a Java library for testing RESTful APIs. It provides a DSL (Domain Specific Language) to write powerful and maintainable tests for REST services.

### Key Features

**Simple DSL:**
```java
given()
    .param("key", "value")
when()
    .get("/endpoint")
then()
    .statusCode(200)
    .body("field", equalTo("value"));
```

**JSON/XML Support:**
- Automatic parsing
- Path expressions
- Schema validation

**Authentication:**
- Basic auth
- OAuth
- API keys
- JWT

**Integration:**
- TestNG
- JUnit
- Maven
- Gradle

### RestAssured Architecture

```
Request Specification
    ↓
HTTP Client
    ↓
Response
    ↓
Response Specification
```

### Maven Dependency

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-path</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>xml-path</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>
```

### Interview Questions

**Q1: What is RestAssured?**
- Java library for testing REST APIs
- Provides DSL for API testing
- Supports JSON and XML
- Integrates with TestNG/JUnit

**Q2: What are the key features of RestAssured?**
- Simple DSL syntax
- JSON/XML support
- Multiple authentication methods
- Schema validation
- Integration with test frameworks

**Q3: How do you add RestAssured to your project?**
- Add Maven dependency
- Add json-path dependency
- Add xml-path dependency
- Scope: test

---

## 6. RESTASSURED SETUP AND CONFIGURATION

### Basic Setup

```java
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APITest {
    
    @BeforeClass
    public void setup() {
        // Set base URI
        RestAssured.baseURI = "https://api.example.com";
        
        // Set base path
        RestAssured.basePath = "/v1";
        
        // Set port
        RestAssured.port = 8080;
        
        // Enable logging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    @Test
    public void testAPI() {
        given()
            .when()
                .get("/users")
            .then()
                .statusCode(200);
    }
}
```

### Request Specification

```java
import io.restassured.specification.RequestSpecification;

public class APITest {
    
    RequestSpecification requestSpec;
    
    @BeforeClass
    public void setup() {
        requestSpec = given()
            .baseUri("https://api.example.com")
            .basePath("/v1")
            .contentType("application/json")
            .accept("application/json");
    }
    
    @Test
    public void testWithRequestSpec() {
        requestSpec
            .when()
                .get("/users")
            .then()
                .statusCode(200);
    }
}
```

### Response Specification

```java
import io.restassured.specification.ResponseSpecification;

public class APITest {
    
    ResponseSpecification responseSpec;
    
    @BeforeClass
    public void setup() {
        responseSpec = expect()
            .statusCode(200)
            .contentType("application/json")
            .time(lessThan(5000L));
    }
    
    @Test
    public void testWithResponseSpec() {
        given()
            .when()
                .get("/users")
            .then()
                .spec(responseSpec);
    }
}
```

### Configuration

```java
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.JsonConfig;

public class APITest {
    
    @BeforeClass
    public void setup() {
        RestAssured.config = RestAssuredConfig.config()
            .jsonConfig(JsonConfig.jsonConfig()
                .numberReturnType(JsonConfig.NumberReturnType.BIG_DECIMAL));
    }
}
```

### Interview Questions

**Q1: How do you set up RestAssured?**
- Add Maven dependencies
- Set base URI, base path, port
- Enable logging
- Configure request/response specs

**Q2: What is Request Specification in RestAssured?**
- Reusable request configuration
- Base URI, headers, auth
- Reduces code duplication
- Example: given().spec(requestSpec)

**Q3: What is Response Specification in RestAssured?**
- Reusable response validation
- Status code, content type, time
- Consistent validation
- Example: then().spec(responseSpec)

---

## 7. GET REQUESTS

### Basic GET Request

```java
@Test
public void testGetRequest() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200)
            .contentType("application/json");
}
```

### GET with Path Parameters

```java
@Test
public void testGetWithPathParam() {
    given()
        .pathParam("userId", 123)
        .when()
            .get("https://api.example.com/users/{userId}")
        .then()
            .statusCode(200);
}
```

### GET with Query Parameters

```java
@Test
public void testGetWithQueryParam() {
    given()
        .queryParam("page", 1)
        .queryParam("limit", 10)
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### GET with Multiple Path Parameters

```java
@Test
public void testGetWithMultiplePathParams() {
    given()
        .pathParam("userId", 123)
        .pathParam("orderId", 456)
        .when()
            .get("https://api.example.com/users/{userId}/orders/{orderId}")
        .then()
            .statusCode(200);
}
```

### GET and Validate Response

```java
@Test
public void testGetAndValidate() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .body("id", equalTo(123))
            .body("name", equalTo("John Doe"))
            .body("email", equalTo("john@example.com"));
}
```

### GET and Extract Response

```java
@Test
public void testGetAndExtract() {
    String name = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .extract()
            .path("name");
    
    System.out.println("Name: " + name);
}
```

### GET and Extract Full Response

```java
@Test
public void testGetAndExtractFullResponse() {
    Response response = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .extract()
            .response();
    
    String body = response.asString();
    System.out.println("Response: " + body);
}
```

### Interview Questions

**Q1: How do you send a GET request in RestAssured?**
- Use given().when().get()
- Can add path parameters
- Can add query parameters
- Example: given().get("/users")

**Q2: What is the difference between pathParam and queryParam?**
- pathParam: Part of URL path
- queryParam: After ? in URL
- pathParam: /users/{id}
- queryParam: /users?page=1

**Q3: How do you extract data from response in RestAssured?**
- Use extract().path()
- Use extract().response()
- Can extract specific fields
- Can extract full response

---

## 8. POST REQUESTS

### Basic POST Request

```java
@Test
public void testPostRequest() {
    String requestBody = "{\"name\":\"John Doe\",\"email\":\"john@example.com\"}";
    
    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201);
}
```

### POST with HashMap

```java
@Test
public void testPostWithHashMap() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "John Doe");
    requestBody.put("email", "john@example.com");
    
    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201);
}
```

### POST with POJO

```java
public class User {
    private String name;
    private String email;
    
    // Getters and setters
}

@Test
public void testPostWithPOJO() {
    User user = new User();
    user.setName("John Doe");
    user.setEmail("john@example.com");
    
    given()
        .contentType("application/json")
        .body(user)
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201);
}
```

### POST and Validate Response

```java
@Test
public void testPostAndValidate() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "John Doe");
    requestBody.put("email", "john@example.com");
    
    given()
        .contentType("application/json")
        .body(requestBody)
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("John Doe"))
            .body("email", equalTo("john@example.com"))
            .body("id", notNullValue());
}
```

### POST and Extract Created ID

```java
@Test
public void testPostAndExtractId() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "John Doe");
    requestBody.put("email", "john@example.com");
    
    int userId = given()
        .contentType("application/json")
        .body(requestBody)
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201)
            .extract()
            .path("id");
    
    System.out.println("Created user ID: " + userId);
}
```

### POST with Form Data

```java
@Test
public void testPostWithFormData() {
    given()
        .contentType("application/x-www-form-urlencoded")
        .formParam("name", "John Doe")
        .formParam("email", "john@example.com")
        .when()
            .post("https://api.example.com/users")
        .then()
            .statusCode(201);
}
```

### Interview Questions

**Q1: How do you send a POST request in RestAssured?**
- Use given().when().post()
- Set content type
- Provide request body
- Can use HashMap, POJO, or JSON string

**Q2: What are the different ways to send request body in POST?**
- JSON string
- HashMap
- POJO (Plain Old Java Object)
- Form data

**Q3: How do you validate POST response in RestAssured?**
- Use then().statusCode()
- Use then().body()
- Validate created resource
- Extract created ID

---

## 9. PUT AND PATCH REQUESTS

### PUT Request

```java
@Test
public void testPutRequest() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("name", "Jane Doe");
    requestBody.put("email", "jane@example.com");
    
    given()
        .contentType("application/json")
        .pathParam("userId", 123)
        .body(requestBody)
        .when()
            .put("https://api.example.com/users/{userId}")
        .then()
            .statusCode(200)
            .body("name", equalTo("Jane Doe"));
}
```

### PATCH Request

```java
@Test
public void testPatchRequest() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("email", "newemail@example.com");
    
    given()
        .contentType("application/json")
        .pathParam("userId", 123)
        .body(requestBody)
        .when()
            .patch("https://api.example.com/users/{userId}")
        .then()
            .statusCode(200)
            .body("email", equalTo("newemail@example.com"));
}
```

### PUT vs PATCH

```java
// PUT - Update entire resource
@Test
public void testPutEntireResource() {
    User user = new User();
    user.setName("Jane Doe");
    user.setEmail("jane@example.com");
    user.setAge(30);
    
    given()
        .contentType("application/json")
        .pathParam("userId", 123)
        .body(user)
        .when()
            .put("https://api.example.com/users/{userId}")
        .then()
            .statusCode(200);
}

// PATCH - Partial update
@Test
public void testPatchPartialUpdate() {
    Map<String, Object> update = new HashMap<>();
    update.put("email", "newemail@example.com");
    
    given()
        .contentType("application/json")
        .pathParam("userId", 123)
        .body(update)
        .when()
            .patch("https://api.example.com/users/{userId}")
        .then()
            .statusCode(200);
}
```

### Interview Questions

**Q1: What is the difference between PUT and PATCH?**
- PUT: Update entire resource
- PATCH: Partial update
- PUT is idempotent
- PATCH is not idempotent

**Q2: How do you send a PUT request in RestAssured?**
- Use given().when().put()
- Provide complete resource
- Update entire resource
- Example: put("/users/{id}")

**Q3: When would you use PATCH instead of PUT?**
- Partial update needed
- Only specific fields to update
- Smaller payload
- Better performance

---

## 10. DELETE REQUESTS

### Basic DELETE Request

```java
@Test
public void testDeleteRequest() {
    given()
        .pathParam("userId", 123)
        .when()
            .delete("https://api.example.com/users/{userId}")
        .then()
            .statusCode(204);
}
```

### DELETE and Validate

```java
@Test
public void testDeleteAndValidate() {
    given()
        .pathParam("userId", 123)
        .when()
            .delete("https://api.example.com/users/{userId}")
        .then()
            .statusCode(204)
            .body(isEmptyOrNullString());
}
```

### DELETE with Body

```java
@Test
public void testDeleteWithBody() {
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("reason", "User request");
    
    given()
        .contentType("application/json")
        .pathParam("userId", 123)
        .body(requestBody)
        .when()
            .delete("https://api.example.com/users/{userId}")
        .then()
            .statusCode(204);
}
```

### Verify Deletion

```java
@Test
public void testVerifyDeletion() {
    // Delete user
    given()
        .pathParam("userId", 123)
        .when()
            .delete("https://api.example.com/users/{userId}")
        .then()
            .statusCode(204);
    
    // Verify user is deleted
    given()
        .pathParam("userId", 123)
        .when()
            .get("https://api.example.com/users/{userId}")
        .then()
            .statusCode(404);
}
```

### Interview Questions

**Q1: How do you send a DELETE request in RestAssured?**
- Use given().when().delete()
- Provide resource identifier
- Usually returns 204 No Content
- Example: delete("/users/{id}")

**Q2: What status code should DELETE return?**
- 204 No Content (most common)
- 200 OK (with response body)
- 202 Accepted (async deletion)
- 404 Not Found (already deleted)

**Q3: How do you verify deletion in API testing?**
- Send DELETE request
- Verify 204 status
- Send GET request
- Verify 404 status

---

## 11. REQUEST HEADERS AND PARAMETERS

### Add Headers

```java
@Test
public void testWithHeaders() {
    given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer token")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Multiple Headers

```java
@Test
public void testWithMultipleHeaders() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("Accept", "application/json");
    headers.put("Authorization", "Bearer token");
    
    given()
        .headers(headers)
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Query Parameters

```java
@Test
public void testWithQueryParams() {
    given()
        .queryParam("page", 1)
        .queryParam("limit", 10)
        .queryParam("sort", "name")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Multiple Query Parameters

```java
@Test
public void testWithMultipleQueryParams() {
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("page", 1);
    queryParams.put("limit", 10);
    queryParams.put("sort", "name");
    
    given()
        .queryParams(queryParams)
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Form Parameters

```java
@Test
public void testWithFormParams() {
    given()
        .contentType("application/x-www-form-urlencoded")
        .formParam("username", "john")
        .formParam("password", "secret")
        .when()
            .post("https://api.example.com/login")
        .then()
            .statusCode(200);
}
```

### Path Parameters

```java
@Test
public void testWithPathParams() {
    given()
        .pathParam("userId", 123)
        .pathParam("orderId", 456)
        .when()
            .get("https://api.example.com/users/{userId}/orders/{orderId}")
        .then()
            .statusCode(200);
}
```

### Interview Questions

**Q1: How do you add headers in RestAssured?**
- Use header() method
- Use headers() for multiple
- Example: header("Authorization", "Bearer token")
- Can use Map for multiple headers

**Q2: What is the difference between queryParam and formParam?**
- queryParam: URL query string
- formParam: Form data in body
- queryParam: GET requests
- formParam: POST requests

**Q3: How do you add multiple parameters in RestAssured?**
- Use queryParams() for query params
- Use formParams() for form params
- Pass Map of parameters
- Example: queryParams(params)

---

## 12. AUTHENTICATION IN RESTASSURED

### Basic Authentication

```java
@Test
public void testBasicAuth() {
    given()
        .auth().basic("username", "password")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Preemptive Authentication

```java
@Test
public void testPreemptiveAuth() {
    given()
        .auth().preemptive().basic("username", "password")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### OAuth 2.0

```java
@Test
public void testOAuth2() {
    given()
        .auth().oauth2("access_token")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### API Key

```java
@Test
public void testAPIKey() {
    given()
        .header("X-API-Key", "your-api-key")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Bearer Token

```java
@Test
public void testBearerToken() {
    given()
        .header("Authorization", "Bearer your-jwt-token")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Digest Authentication

```java
@Test
public void testDigestAuth() {
    given()
        .auth().digest("username", "password")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Interview Questions

**Q1: What are the different authentication methods in RestAssured?**
- Basic authentication
- Preemptive authentication
- OAuth 2.0
- API key
- Bearer token
- Digest authentication

**Q2: What is the difference between basic and preemptive authentication?**
- Basic: Server requests auth
- Preemptive: Client sends auth immediately
- Preemptive: Faster, no extra request
- Basic: More secure

**Q3: How do you implement OAuth 2.0 in RestAssured?**
- Use auth().oauth2()
- Pass access token
- Token obtained from OAuth server
- Example: auth().oauth2("token")

---

## 13. RESPONSE VALIDATION

### Validate Status Code

```java
@Test
public void testStatusCode() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200)
            .statusLine("HTTP/1.1 200 OK");
}
```

### Validate Content Type

```java
@Test
public void testContentType() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200)
            .contentType("application/json");
}
```

### Validate Response Time

```java
@Test
public void testResponseTime() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200)
            .time(lessThan(5000L));
}
```

### Validate Response Body

```java
@Test
public void testResponseBody() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .body("id", equalTo(123))
            .body("name", equalTo("John Doe"))
            .body("email", equalTo("john@example.com"));
}
```

### Validate Nested Fields

```java
@Test
public void testNestedFields() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .body("address.street", equalTo("123 Main St"))
            .body("address.city", equalTo("New York"));
}
```

### Validate Array

```java
@Test
public void testArrayValidation() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200)
            .body("users.size()", equalTo(10))
            .body("users[0].name", equalTo("John Doe"))
            .body("users.name", hasItem("John Doe"));
}
```

### Validate Multiple Conditions

```java
@Test
public void testMultipleConditions() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .statusCode(200)
            .body("id", equalTo(123))
            .body("name", notNullValue())
            .body("email", containsString("@"))
            .body("age", greaterThan(18));
}
```

### Interview Questions

**Q1: How do you validate response in RestAssured?**
- Use then() assertions
- Validate status code
- Validate content type
- Validate response body

**Q2: How do you validate nested JSON fields?**
- Use dot notation
- Example: body("address.street", equalTo(...))
- Can validate multiple levels
- Works with arrays

**Q3: How do you validate arrays in response?**
- Use size() for length
- Use index for specific element
- Use hasItem() for contains
- Example: body("users.size()", equalTo(10))

---

## 14. JSON PATH QUERIES

### Basic JSON Path

```java
@Test
public void testJsonPath() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .body("$.name", equalTo("John Doe"))
            .body("$.email", equalTo("john@example.com"));
}
```

### JSON Path with Root

```java
@Test
public void testJsonPathRoot() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .root("user")
            .body("name", equalTo("John Doe"))
            .body("email", equalTo("john@example.com"));
}
```

### JSON Path with Arrays

```java
@Test
public void testJsonPathArray() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .body("users[0].name", equalTo("John Doe"))
            .body("users[1].name", equalTo("Jane Doe"))
            .body("users.size()", equalTo(10));
}
```

### JSON Path Filters

```java
@Test
public void testJsonPathFilter() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .body("users.find { it.age > 30 }.name", equalTo("John Doe"))
            .body("users.findAll { it.age > 30 }.size()", equalTo(5));
}
```

### JSON Path Wildcard

```java
@Test
public void testJsonPathWildcard() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .body("users[*].name", hasItem("John Doe"))
            .body("users[*].age", everyItem(greaterThan(18)));
}
```

### Extract with JSON Path

```java
@Test
public void testExtractJsonPath() {
    String name = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .jsonPath()
            .getString("name");
    
    System.out.println("Name: " + name);
}
```

### Interview Questions

**Q1: What is JSON Path?**
- Query language for JSON
- Similar to XPath for XML
- Used to extract data from JSON
- Supported in RestAssured

**Q2: How do you use JSON Path in RestAssured?**
- Use body() with JSON Path
- Example: body("$.name", equalTo(...))
- Can use filters
- Can extract data

**Q3: How do you filter arrays with JSON Path?**
- Use find() for single match
- Use findAll() for multiple matches
- Example: users.findAll { it.age > 30 }
- Can use conditions

---

## 15. XML PATH QUERIES

### Basic XML Path

```java
@Test
public void testXmlPath() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .body("user.name", equalTo("John Doe"))
            .body("user.email", equalTo("john@example.com"));
}
```

### XML Path with Attributes

```java
@Test
public void testXmlPathAttributes() {
    given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .body("user.@id", equalTo("123"))
            .body("user.name", equalTo("John Doe"));
}
```

### XML Path with Lists

```java
@Test
public void testXmlPathList() {
    given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .body("users.user[0].name", equalTo("John Doe"))
            .body("users.user.size()", equalTo(10));
}
```

### Extract with XML Path

```java
@Test
public void testExtractXmlPath() {
    String name = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .xmlPath()
            .getString("user.name");
    
    System.out.println("Name: " + name);
}
```

### Interview Questions

**Q1: What is XML Path?**
- Query language for XML
- Similar to JSON Path
- Used to extract data from XML
- Supported in RestAssured

**Q2: How do you access XML attributes in RestAssured?**
- Use @ symbol
- Example: user.@id
- Access attribute values
- Works with XML Path

**Q3: How do you extract XML data in RestAssured?**
- Use extract().xmlPath()
- Specify XML Path
- Extract specific fields
- Example: xmlPath().getString("user.name")

---

## 16. FILE UPLOAD AND DOWNLOAD

### File Upload

```java
@Test
public void testFileUpload() {
    File file = new File("test.txt");
    
    given()
        .multiPart("file", file)
        .when()
            .post("https://api.example.com/upload")
        .then()
            .statusCode(200);
}
```

### File Upload with Metadata

```java
@Test
public void testFileUploadWithMetadata() {
    File file = new File("test.txt");
    
    given()
        .multiPart("file", file, "text/plain")
        .formParam("description", "Test file")
        .when()
            .post("https://api.example.com/upload")
        .then()
            .statusCode(200);
}
```

### File Download

```java
@Test
public void testFileDownload() {
    byte[] file = given()
        .when()
            .get("https://api.example.com/download/test.txt")
        .then()
            .statusCode(200)
            .extract()
            .asByteArray();
    
    // Save file
    Files.write(Paths.get("downloaded.txt"), file);
}
```

### File Download and Validate

```java
@Test
public void testFileDownloadAndValidate() {
    given()
        .when()
            .get("https://api.example.com/download/test.txt")
        .then()
            .statusCode(200)
            .contentType("text/plain")
            .header("Content-Disposition", containsString("attachment"));
}
```

### Interview Questions

**Q1: How do you upload files in RestAssured?**
- Use multiPart() method
- Pass File object
- Can specify content type
- Example: multiPart("file", file)

**Q2: How do you download files in RestAssured?**
- Use GET request
- Extract as byte array
- Save to file system
- Validate content type

**Q3: How do you validate file download in API testing?**
- Check status code
- Check content type
- Check Content-Disposition header
- Validate file content

---

## 17. COOKIE HANDLING

### Add Cookie

```java
@Test
public void testWithCookie() {
    given()
        .cookie("session_id", "abc123")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Add Multiple Cookies

```java
@Test
public void testWithMultipleCookies() {
    given()
        .cookie("session_id", "abc123")
        .cookie("user_id", "456")
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Add Cookies from Map

```java
@Test
public void testWithCookieMap() {
    Map<String, String> cookies = new HashMap<>();
    cookies.put("session_id", "abc123");
    cookies.put("user_id", "456");
    
    given()
        .cookies(cookies)
        .when()
            .get("https://api.example.com/users")
        .then()
            .statusCode(200);
}
```

### Extract Cookie

```java
@Test
public void testExtractCookie() {
    String sessionId = given()
        .when()
            .get("https://api.example.com/login")
        .then()
            .statusCode(200)
            .extract()
            .detailedCookie("session_id")
            .getValue();
    
    System.out.println("Session ID: " + sessionId);
}
```

### Get All Cookies

```java
@Test
public void testGetAllCookies() {
    Map<String, String> cookies = given()
        .when()
            .get("https://api.example.com/login")
        .then()
            .statusCode(200)
            .extract()
            .cookies();
    
    cookies.forEach((key, value) -> 
        System.out.println(key + ": " + value)
    );
}
```

### Interview Questions

**Q1: How do you add cookies in RestAssured?**
- Use cookie() method
- Use cookies() for multiple
- Can use Map
- Example: cookie("session_id", "abc123")

**Q2: How do you extract cookies from response?**
- Use extract().detailedCookie()
- Use extract().cookies()
- Get specific or all cookies
- Example: detailedCookie("session_id")

**Q3: When would you use cookies in API testing?**
- Session management
- Authentication
- State maintenance
- User tracking

---

## 18. RESPONSE PARSING

### Parse to String

```java
@Test
public void testParseToString() {
    String response = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .asString();
    
    System.out.println(response);
}
```

### Parse to POJO

```java
@Test
public void testParseToPOJO() {
    User user = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .as(User.class);
    
    System.out.println(user.getName());
}
```

### Parse to List

```java
@Test
public void testParseToList() {
    List<User> users = given()
        .when()
            .get("https://api.example.com/users")
        .then()
            .extract()
            .jsonPath()
            .getList("users", User.class);
    
    System.out.println("Total users: " + users.size());
}
```

### Parse to Map

```java
@Test
public void testParseToMap() {
    Map<String, Object> user = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .jsonPath()
            .getMap("$");
    
    System.out.println(user.get("name"));
}
```

### Parse to Response Object

```java
@Test
public void testParseToResponse() {
    Response response = given()
        .when()
            .get("https://api.example.com/users/123")
        .then()
            .extract()
            .response();
    
    System.out.println("Status: " + response.getStatusCode());
    System.out.println("Body: " + response.asString());
}
```

### Interview Questions

**Q1: How do you parse response to POJO in RestAssured?**
- Use extract().as(Class.class)
- Automatic JSON to object mapping
- Requires matching fields
- Example: as(User.class)

**Q2: How do you parse response to List?**
- Use extract().jsonPath().getList()
- Specify list element type
- Example: getList("users", User.class)
- Returns List of POJOs

**Q3: How do you parse response to Map?**
- Use extract().jsonPath().getMap()
- Returns Map of String to Object
- Useful for dynamic JSON
- Example: getMap("$")

---

## 19. DATA-DRIVEN API TESTING

### DataProvider with TestNG

```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenAPITest {
    
    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][]{
            {"John Doe", "john@example.com"},
            {"Jane Doe", "jane@example.com"},
            {"Bob Smith", "bob@example.com"}
        };
    }
    
    @Test(dataProvider = "userData")
    public void testCreateUser(String name, String email) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);
        
        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://api.example.com/users")
            .then()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("email", equalTo(email));
    }
}
```

### DataProvider from Excel

```java
@DataProvider(name = "excelData")
public Object[][] getExcelData() throws IOException {
    // Read from Excel file
    // Return Object[][]
    return new Object[][]{
        {"John Doe", "john@example.com"},
        {"Jane Doe", "jane@example.com"}
    };
}
```

### DataProvider from JSON File

```java
@DataProvider(name = "jsonData")
public Object[][] getJsonData() throws IOException {
    // Read from JSON file
    // Parse and return Object[][]
    return new Object[][]{
        {"John Doe", "john@example.com"},
        {"Jane Doe", "jane@example.com"}
    };
}
```

### DataProvider from CSV

```java
@DataProvider(name = "csvData")
public Object[][] getCsvData() throws IOException {
    // Read from CSV file
    // Return Object[][]
    return new Object[][]{
        {"John Doe", "john@example.com"},
        {"Jane Doe", "jane@example.com"}
    };
}
```

### Interview Questions

**Q1: What is data-driven testing in API testing?**
- Run same test with different data
- Use DataProvider in TestNG
- Test various scenarios
- Improve test coverage

**Q2: How do you implement data-driven API testing?**
- Use TestNG DataProvider
- Provide test data as Object[][]
- Test runs for each data set
- Can read from Excel, JSON, CSV

**Q3: What are the advantages of data-driven testing?**
- Test multiple scenarios
- Reduce code duplication
- Easy to add new test cases
- Better coverage

---

## 20. API TESTING WITH TESTNG

### TestNG Setup

```java
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APITestWithTestNG {
    
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.example.com";
        RestAssured.basePath = "/v1";
    }
    
    @Test
    public void testGetUser() {
        given()
            .when()
                .get("/users/123")
            .then()
                .statusCode(200);
    }
}
```

### TestNG Groups

```java
@Test(groups = {"smoke"})
public void testGetUser() {
    given()
        .when()
            .get("/users/123")
        .then()
            .statusCode(200);
}

@Test(groups = {"regression"})
public void testCreateUser() {
    given()
        .contentType("application/json")
        .body("{\"name\":\"John\"}")
        .when()
            .post("/users")
        .then()
            .statusCode(201);
}
```

### TestNG Dependencies

```java
@Test
public void testCreateUser() {
    given()
        .contentType("application/json")
        .body("{\"name\":\"John\"}")
        .when()
            .post("/users")
        .then()
            .statusCode(201);
}

@Test(dependsOnMethods = "testCreateUser")
public void testGetUser() {
    given()
        .when()
            .get("/users/123")
        .then()
            .statusCode(200);
}
```

### TestNG Priority

```java
@Test(priority = 1)
public void testCreateUser() {
    // Create user
}

@Test(priority = 2)
public void testGetUser() {
    // Get user
}

@Test(priority = 3)
public void testUpdateUser() {
    // Update user
}
```

### TestNG Parallel Execution

```java
@Test(threadPoolSize = 3, invocationCount = 5)
public void testParallel() {
    given()
        .when()
            .get("/users")
        .then()
            .statusCode(200);
}
```

### Interview Questions

**Q1: How do you integrate RestAssured with TestNG?**
- Add RestAssured dependency
- Use @Test annotations
- Use @BeforeClass for setup
- Can use groups, priorities

**Q2: How do you run API tests in parallel with TestNG?**
- Use threadPoolSize
- Use invocationCount
- Configure in testng.xml
- Each test runs in separate thread

**Q3: How do you use TestNG groups in API testing?**
- Group related tests
- Run specific groups
- Example: smoke, regression
- Configure in testng.xml

---

## 21. API TESTING FRAMEWORK DESIGN

### Framework Structure

```
API_Framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── config/
│   │       │   ├── Config.java
│   │       │   └── Constants.java
│   │       ├── utils/
│   │       │   ├── RestUtils.java
│   │       │   └── FileUtils.java
│   │       └── pojo/
│   │           ├── User.java
│   │           └── Response.java
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   ├── tests/
│       │   │   ├── UserTests.java
│       │   │   └── ProductTests.java
│       │   └── listeners/
│       │       └── TestListener.java
│       └── resources/
│           ├── config.properties
│           └── testdata/
│               └── users.json
└── pom.xml
```

### Base Test Class

```java
public class BaseTest {
    
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;
    
    @BeforeClass
    public void setup() {
        requestSpec = given()
            .baseUri(Config.getBaseUrl())
            .basePath(Config.getBasePath())
            .contentType("application/json")
            .accept("application/json")
            .log(LogDetail.ALL);
        
        responseSpec = expect()
            .statusCode(200)
            .contentType("application/json")
            .time(lessThan(5000L));
    }
}
```

### Utility Class

```java
public class RestUtils {
    
    public static String getRandomEmail() {
        return "test" + System.currentTimeMillis() + "@example.com";
    }
    
    public static String getRandomName() {
        return "User" + System.currentTimeMillis();
    }
    
    public static Map<String, Object> getUserPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", getRandomName());
        payload.put("email", getRandomEmail());
        return payload;
    }
}
```

### POJO Class

```java
public class User {
    private int id;
    private String name;
    private String email;
    
    // Getters and setters
    // Constructors
}
```

### Test Class

```java
public class UserTests extends BaseTest {
    
    @Test
    public void testCreateUser() {
        Map<String, Object> payload = RestUtils.getUserPayload();
        
        given()
            .spec(requestSpec)
            .body(payload)
        .when()
            .post("/users")
        .then()
            .spec(responseSpec)
            .body("name", equalTo(payload.get("name")))
            .body("email", equalTo(payload.get("email")));
    }
}
```

### Interview Questions

**Q1: What is the structure of an API testing framework?**
- Base test class
- Utility classes
- POJO classes
- Test classes
- Configuration files

**Q2: What are the components of a good API testing framework?**
- Reusable request/response specs
- Utility methods
- POJO for request/response
- Configuration management
- Test data management

**Q3: How do you design a scalable API testing framework?**
- Modular design
- Reusable components
- Separation of concerns
- Easy to maintain
- Support for data-driven testing

---

## 22. MOCK API TESTING WITH WIREMOCK

### WireMock Setup

```xml
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8</artifactId>
    <version>2.35.0</version>
    <scope>test</scope>
</dependency>
```

### Start WireMock Server

```java
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockSetup {
    
    private WireMockServer wireMockServer;
    
    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();
    }
    
    @AfterClass
    public void tearDown() {
        wireMockServer.stop();
    }
}
```

### Mock GET Endpoint

```java
@Test
public void testMockGet() {
    wireMockServer.stubFor(get(urlEqualTo("/api/users/123"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\":123,\"name\":\"John Doe\"}")));
    
    given()
        .when()
            .get("http://localhost:8080/api/users/123")
        .then()
            .statusCode(200)
            .body("name", equalTo("John Doe"));
}
```

### Mock POST Endpoint

```java
@Test
public void testMockPost() {
    wireMockServer.stubFor(post(urlEqualTo("/api/users"))
        .willReturn(aResponse()
            .withStatus(201)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\":123,\"name\":\"John Doe\"}")));
    
    given()
        .contentType("application/json")
        .body("{\"name\":\"John Doe\"}")
        .when()
            .post("http://localhost:8080/api/users")
        .then()
            .statusCode(201);
}
```

### Mock with Request Matching

```java
@Test
public void testMockWithMatching() {
    wireMockServer.stubFor(post(urlPathEqualTo("/api/users"))
        .withRequestBody(equalToJson("{\"name\":\"John Doe\"}"))
        .willReturn(aResponse()
            .withStatus(201)));
    
    given()
        .contentType("application/json")
        .body("{\"name\":\"John Doe\"}")
        .when()
            .post("http://localhost:8080/api/users")
        .then()
            .statusCode(201);
}
```

### Interview Questions

**Q1: What is WireMock?**
- HTTP mock server
- Used for API testing
- Simulates API responses
- Useful for unit testing

**Q2: How do you use WireMock for API testing?**
- Start WireMock server
- Stub endpoints
- Define request/response
- Test against mock server

**Q3: When would you use WireMock?**
- When API is not ready
- For unit testing
- For contract testing
- To simulate errors

---

## 23. API SECURITY TESTING

### SQL Injection Testing

```java
@Test
public void testSQLInjection() {
    String maliciousInput = "1' OR '1'='1";
    
    given()
        .queryParam("id", maliciousInput)
        .when()
            .get("/api/users")
        .then()
            .statusCode(400); // Should reject
}
```

### XSS Testing

```java
@Test
public void testXSS() {
    String maliciousInput = "<script>alert('xss')</script>";
    
    given()
        .formParam("name", maliciousInput)
        .when()
            .post("/api/users")
        .then()
            .statusCode(400); // Should reject
}
```

### Authentication Testing

```java
@Test
public void testUnauthorizedAccess() {
    given()
        .when()
            .get("/api/admin/users")
        .then()
            .statusCode(401); // Should require auth
}

@Test
public void testInvalidToken() {
    given()
        .header("Authorization", "Bearer invalid-token")
        .when()
            .get("/api/users")
        .then()
            .statusCode(401); // Should reject
}
```

### Rate Limiting Testing

```java
@Test
public void testRateLimiting() {
    for (int i = 0; i < 100; i++) {
        given()
            .when()
                .get("/api/users")
            .then()
                .statusCode(anyOf(is(200), is(429))); // Should rate limit
    }
}
```

### Interview Questions

**Q1: What are common API security vulnerabilities?**
- SQL injection
- XSS
- Authentication bypass
- Rate limiting issues
- Data exposure

**Q2: How do you test for SQL injection in APIs?**
- Send malicious input
- Try SQL commands
- Check for error messages
- Validate rejection

**Q3: How do you test authentication in APIs?**
- Test without auth
- Test with invalid token
- Test with expired token
- Test authorization levels

---

## 24. PERFORMANCE TESTING WITH RESTASSURED

### Response Time Validation

```java
@Test
public void testResponseTime() {
    given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .time(lessThan(2000L)); // Should respond in < 2 seconds
}
```

### Load Testing

```java
@Test(invocationCount = 10, threadPoolSize = 5)
public void testLoad() {
    given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(200);
}
```

### Stress Testing

```java
@Test(invocationCount = 100, threadPoolSize = 20)
public void testStress() {
    given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(anyOf(is(200), is(503))); // May fail under stress
}
```

### Performance Metrics

```java
@Test
public void testPerformanceMetrics() {
    long startTime = System.currentTimeMillis();
    
    given()
        .when()
            .get("/api/users")
        .then()
            .statusCode(200);
    
    long endTime = System.currentTimeMillis();
    long responseTime = endTime - startTime;
    
    System.out.println("Response time: " + responseTime + "ms");
}
```

### Interview Questions

**Q1: How do you perform performance testing with RestAssured?**
- Validate response time
- Use invocationCount for load
- Use threadPoolSize for concurrency
- Measure metrics

**Q2: What is the difference between load and stress testing?**
- Load: Expected load
- Stress: Beyond expected load
- Load: Normal operation
- Stress: Breaking point

**Q3: How do you measure API performance?**
- Response time
- Throughput
- Error rate
- Resource utilization

---

## 25. COMMON INTERVIEW QUESTIONS

### API Testing Fundamentals

**Q1: What is API testing?**
- Testing APIs directly
- Bypasses UI
- Tests backend logic
- Faster than UI testing

**Q2: Why is API testing important?**
- Faster execution
- Early testing
- Better coverage
- Cost effective

**Q3: What are the types of API testing?**
- Functional testing
- Integration testing
- Performance testing
- Security testing
- Contract testing

### REST API

**Q4: What is REST?**
- Architectural style
- Resource-based
- Stateless
- Uses HTTP methods

**Q5: What are HTTP methods?**
- GET: Retrieve
- POST: Create
- PUT: Update entire
- PATCH: Partial update
- DELETE: Delete

**Q6: What are HTTP status codes?**
- 2xx: Success
- 3xx: Redirection
- 4xx: Client error
- 5xx: Server error

### RestAssured

**Q7: What is RestAssured?**
- Java library for API testing
- DSL for API testing
- Supports JSON/XML
- Integrates with TestNG

**Q8: How do you send a GET request?**
- given().when().get()
- Can add parameters
- Validate response
- Example: get("/users")

**Q9: How do you send a POST request?**
- given().body().post()
- Set content type
- Provide request body
- Validate response

### Advanced

**Q10: How do you handle authentication in RestAssured?**
- Basic auth
- OAuth 2.0
- Bearer token
- API key

**Q11: How do you validate JSON response?**
- Use body() assertions
- Use JSON Path
- Validate fields
- Validate arrays

**Q12: How do you implement data-driven testing?**
- Use TestNG DataProvider
- Provide test data
- Test runs for each data set
- Can read from files

**Q13: What is WireMock?**
- HTTP mock server
- Simulates API responses
- Used for unit testing
- Useful when API not ready

**Q14: How do you perform API security testing?**
- SQL injection testing
- XSS testing
- Authentication testing
- Rate limiting testing

**Q15: How do you perform API performance testing?**
- Validate response time
- Load testing
- Stress testing
- Measure metrics

### Scenario-Based

**Q16: How do you test an API that requires authentication?**
- Obtain valid credentials
- Use appropriate auth method
- Test with invalid credentials
- Test with expired token

**Q17: How do you test pagination in APIs?**
- Test with different page numbers
- Validate page size
- Validate total count
- Test edge cases

**Q18: How do you test error scenarios in APIs?**
- Send invalid data
- Test missing fields
- Test wrong data types
- Validate error messages

**Q19: How do you handle dynamic data in API testing?**
- Use random data generation
- Use timestamps
- Use UUIDs
- Extract from response

**Q20: How do you design an API testing framework?**
- Modular design
- Reusable components
- Configuration management
- Test data management
- Reporting

---

## CONCLUSION

This comprehensive guide covers all essential API testing with RestAssured topics for interview preparation. Key takeaways:

1. **API Testing Fundamentals**: Direct API testing, faster than UI
2. **REST Concepts**: Resource-based, stateless, HTTP methods
3. **HTTP Methods**: GET, POST, PUT, PATCH, DELETE
4. **Status Codes**: 2xx success, 4xx client error, 5xx server error
5. **RestAssured**: Java DSL for API testing
6. **Requests**: GET, POST, PUT, PATCH, DELETE
7. **Authentication**: Basic, OAuth, Bearer token
8. **Validation**: Status code, body, headers, time
9. **JSON/XML Path**: Query languages for data extraction
10. **Data-Driven**: TestNG DataProvider for multiple scenarios
11. **Framework Design**: Modular, reusable, maintainable
12. **Mock Testing**: WireMock for API simulation
13. **Security**: SQL injection, XSS, authentication
14. **Performance**: Response time, load, stress testing

Practice these concepts with real APIs and be prepared to explain the "why" behind each approach. Good luck with your interview!
