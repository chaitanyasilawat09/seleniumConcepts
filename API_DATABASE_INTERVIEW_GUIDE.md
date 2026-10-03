# API Testing, Database Testing & Advanced Automation - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. REST API Testing Fundamentals
2. RestAssured Framework Deep Dive
3. API Testing Best Practices
4. Database Testing with SQL
5. Advanced XPath Locators
6. Chrome Options & DesiredCapabilities
7. Test Strategy vs Test Plan
8. API Automation Framework Design
9. Microservices Testing
10. SSL/TLS Handling in Automation
11. Advanced Interview Questions

---

## 1. REST API TESTING FUNDAMENTALS

### What is a REST API?

A REST API is a way to communicate between systems over the web using HTTP. It follows REST (Representational State Transfer) principles.

**Key Characteristics:**
- **Stateless:** Each request contains all information needed to understand it
- **Client-Server Architecture:** Clear separation between client and server
- **Cacheable:** Responses can be cached to improve performance
- **Uniform Interface:** Standardized way of communication

**Common HTTP Methods:**
- **GET:** Retrieve data
- **POST:** Create new data
- **PUT:** Update existing data (full update)
- **PATCH:** Partial update
- **DELETE:** Remove data

**Example Endpoints:**
```
GET /users
POST /orders
PUT /products/123
DELETE /users/456
```

**Interview Question:** What is the difference between PUT and PATCH?
- **PUT:** Updates the entire resource. If a field is not provided, it may be set to null/default
- **PATCH:** Partially updates a resource. Only the provided fields are updated

### What are Microservices?

Microservices is an architecture style where an application is broken down into small, independent services. Each microservice handles one specific business functionality (e.g., user service, payment service).

**Benefits:**
- **Scalability:** Each service can be scaled independently
- **Flexibility:** Different services can use different technologies
- **Resilience:** Failure in one service doesn't bring down the entire system
- **Faster Development:** Teams can work on different services in parallel

**Communication Methods:**
- REST APIs
- gRPC
- Message Queues (Kafka, RabbitMQ)
- GraphQL

**Testing Challenges:**
- Service dependencies
- Data consistency
- End-to-end testing complexity
- Mocking external services

---

## 2. RESTASSURED FRAMEWORK DEEP DIVE

### Basic GET Request

```java
given().
    get("https://reqres.in/api/users?page=2").
then().
    statusCode(200).
    body("data.id[1]", equalTo(8)).
    body("data.first_name", hasItems("Michael", "Lindsay"));
```

**Breaking it down:**
- `given()`: Start building the request specification
- `get()`: Send GET request to the endpoint
- `then()`: Start building response validation
- `statusCode(200)`: Validate HTTP status code
- `body()`: Validate response body using JSON path

### POST Request with Body

```java
given().
    contentType(ContentType.JSON).
    body("{ \"name\": \"John\", \"job\": \"leader\" }").
when().
    post("https://reqres.in/api/users").
then().
    statusCode(201).
    body("name", equalTo("John"));
```

### PUT Request (Update)

```java
given().
    contentType(ContentType.JSON).
    body("{ \"name\": \"John\", \"job\": \"manager\" }").
when().
    put("https://reqres.in/api/users/2").
then().
    statusCode(200).
    body("job", equalTo("manager"));
```

### DELETE Request

```java
given().
when().
    delete("https://reqres.in/api/users/2").
then().
    statusCode(204);
```

### Form Parameters

```java
given().
    formParams("username", "john", "password", "1234").
when().
    post("/login");
```

**When to use form parameters:**
- Content-Type: application/x-www-form-urlencoded
- Traditional form submissions
- Legacy APIs

### Query Parameters

```java
given().
    queryParam("page", 2).
    queryParam("per_page", 10).
when().
    get("https://reqres.in/api/users");
```

**URL Generated:** `https://reqres.in/api/users?page=2&per_page=10`

### Path Parameters

```java
given().
    pathParam("agreementId", "1234").
    queryParam("agreementType", "CHQ").
when().
    get("https://localhost/v1/agreement/{agreementId}");

// OR
given().
    pathParam("agreementId", "1234").
    queryParam("agreementType", "CHQ").
when().
    get("/{agreementId}");
```

**URL Generated:** `https://localhost/v1/agreement/1234?agreementType=CHQ`

**Interview Question:** Difference between queryParam and pathParam?
- **queryParam:** Appears in URL after `?` (e.g., `?page=2`)
- **pathParam:** Part of the URL path (e.g., `/users/{id}`)

### Multiple Headers

```java
given().
    headers("User-Agent", "MyAppName", "Accept-Charset", "utf-8").
when().
    get("/users");
```

### Add Cookies

```java
// Simple cookie
given().
    cookie("session_id", "1234").
when().
    get("/users/eugenp");

// Customized cookie using Cookie Builder
Cookie myCookie = new Cookie.Builder("session_id", "1234")
    .setSecured(true)
    .setComment("session id cookie")
    .build();

given().
    cookie(myCookie).
when().
    get("/users/eugenp").
then().
    statusCode(200);
```

### Extract Cookie from Response

```java
Cookie cookie = given()
    .contentType(JSON)
    .body(loginRequest)
.when()
    .post(String.format("%s://%s:%d%s%s", SCHEME, HOST, PORT, BASE_PATH, LOGIN_ENDPOINT))
.then()
    .statusCode(is(SC_NO_CONTENT))
    .cookie(COOKIE_NAME, not(isEmptyString()))
    .extract()
    .detailedCookie(COOKIE_NAME);
```

### Send File via API

```java
Response getResponse = given()
    .header("Connection", "keep-alive")
    .header("Pragma", "no-cache")
    .header("Cache-Control", "no-cache")
    .header("Accept", "application/json, text/plain, */*")
    .header("X-x15-sessionId", sessionId)
.when()
    .multiPart("file", new File(filePath))
    .multiPart("params", load)
    .request(Method.PUT, apiPath)
.then()
    .extract()
    .response();
```

**Use Cases:**
- File upload functionality
- Document management systems
- Profile picture uploads
- CSV/Excel data import

### Logging Requests and Responses

```java
given().
    log().all().
when().
    get("https://reqres.in/api/users").
then().
    log().all();
```

**Logging Levels:**
- `log().all()`: Log everything
- `log().ifError()`: Log only if error occurs
- `log().ifValidationFails()`: Log only if validation fails
- `log().body()`: Log only body
- `log().headers()`: Log only headers

### Authentication in RestAssured

### Basic Authentication

```java
given().
    auth().basic("username", "password").
when().
    get("/secure-endpoint");
```

### Bearer Token (OAuth2)

```java
given().
    auth().oauth2("access_token").
when().
    get("/secure-endpoint");

// OR
given().
    header("Authorization", "Bearer " + accessToken).
when().
    get("/secure-endpoint");
```

### API Key Authentication

```java
given().
    header("X-API-Key", "your-api-key").
when().
    get("/secure-endpoint");
```

### SSL/TLS Handling

```java
// For self-signed certificates or SSL issues
RestAssured.useRelaxedHTTPSValidation();

// Common error without this:
// javax.net.ssl.SSLHandshakeException:
// sun.security.validator.ValidatorException: PKIX path building failed
```

**When to use:**
- Development environments with self-signed certificates
- Testing internal APIs
- Staging environments with certificate issues

**Caution:** Never use in production or when security is critical.

### Response Validation

### Validate Status Code

```java
.then().
    statusCode(200);
```

### Validate Response Time

```java
.then().
    time(lessThan(5000L));  // Response time less than 5 seconds
```

### Validate Response Body Fields

```java
.then()
    .body("data.id", hasSize(6))
    .body("data[0].email", equalTo("george.bluth@reqres.in"))
    .body("data.first_name", hasItems("George", "Janet"));
```

### Validate Headers

```java
.then()
    .header("Content-Type", containsString("application/json"))
    .header("Server", notNullValue());
```

### Extract Response Data

```java
// Extract entire response
Response response = given().get("/api/users").then().extract().response();

// Extract specific field
String email = given().get("/api/users").then().extract().path("data[0].email");

// Extract as object
User user = given().get("/api/users/1").then().extract().as(User.class);
```

---

## 3. API TESTING BEST PRACTICES

### Testing Approach

### 1. Understand API Documentation
- Read API documentation (Swagger, Postman collection)
- Understand endpoints, methods, request/response schema
- Identify authentication requirements
- Note rate limits and constraints

### 2. Test Categories

**Positive Testing:**
- Valid inputs
- Correct parameters
- Happy path scenarios
- Expected success responses

**Negative Testing:**
- Invalid inputs
- Missing required parameters
- Wrong data types
- Boundary values
- Error handling

**Security Testing:**
- Authentication/authorization
- SQL injection attempts
- XSS attempts
- Rate limiting
- Sensitive data exposure

**Performance Testing:**
- Response time under load
- Concurrent requests
- Stress testing
- API degradation

### 3. Test Data Management

**Strategies:**
- Use test data factories
- Clean up test data after execution
- Use environment-specific data
- Mock external dependencies

**Example:**
```java
@DataProvider(name = "userData")
public Object[][] getUserData() {
    return new Object[][] {
        {validUser, 200},
        {invalidUser, 400},
        {missingEmail, 422}
    };
}
```

### 4. Assertion Strategy

**What to Validate:**
- Status code (200, 201, 400, 401, 404, 500)
- Response body (fields, data types, values)
- Response headers (Content-Type, Cache-Control)
- Response time (performance)
- Schema validation (JSON Schema)

### 5. Error Handling

**Common HTTP Status Codes:**
- **200 OK:** Request succeeded
- **201 Created:** Resource created successfully
- **204 No Content:** Request succeeded but no content returned
- **400 Bad Request:** Invalid request
- **401 Unauthorized:** Authentication required
- **403 Forbidden:** Insufficient permissions
- **404 Not Found:** Resource not found
- **422 Unprocessable Entity:** Semantic errors
- **500 Internal Server Error:** Server error

### 6. Test Organization

**Structure:**
```
api-tests/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   ├── BaseTest.java
│   │       │   └── APIBase.java
│   │       ├── models/
│   │       │   ├── User.java
│   │       │   └── Response.java
│   │       └── utils/
│   │           ├── APIUtils.java
│   │           └── TestDataGenerator.java
│   └── test/
│       └── java/
│           ├── tests/
│           │   ├── UserTests.java
│           │   └── ProductTests.java
│           └── suites/
│               └── regression.xml
```

### 7. CI/CD Integration

**Jenkins Pipeline Example:**
```groovy
pipeline {
    agent any
    stages {
        stage('API Tests') {
            steps {
                sh 'mvn test -Dtest=APITestSuite'
            }
            post {
                always {
                    publishHTML(target: [
                        reportDir: 'target/surefire-reports',
                        reportFiles: 'index.html',
                        reportName: 'API Test Report'
                    ])
                }
            }
        }
    }
}
```

---

## 4. DATABASE TESTING WITH SQL

### Why Database Testing?

Database testing ensures:
- Data integrity
- Data consistency
- Correct data storage and retrieval
- Performance of database operations
- Security of data

### Common SQL Queries for Testing

### 1. Count Records with Condition

```sql
-- Count employees in HR department
SELECT COUNT(*) FROM Employee
WHERE dept_name = 'HR';
```

### 2. String Manipulation

```sql
-- Get first 4 characters of employee name
SELECT SUBSTRING(empname, 1, 4)
FROM emp;
```

### 3. Create Table from Existing Table

```sql
-- Copy structure and data
CREATE TABLE NewEmp AS
SELECT * FROM emp;

-- Copy structure only
CREATE TABLE NewEmp AS
SELECT * FROM emp
WHERE 1 = 0;
```

### 4. Range Query

```sql
-- Find employees with salary between 50000 and 100000
SELECT * FROM emp
WHERE salary BETWEEN 50000 AND 100000;
```

### 5. Top N Records

```sql
-- Get top 3 highest salaries (SQL Server)
SELECT TOP 3 * FROM emp
ORDER BY salary DESC;

-- Get top 3 highest salaries (MySQL)
SELECT * FROM emp
ORDER BY salary DESC
LIMIT 3;

-- Get Nth highest salary (3rd highest)
SELECT TOP 1 salary
FROM (
    SELECT TOP 3 salary
    FROM employee_table
    ORDER BY salary DESC
) AS emp
ORDER BY salary ASC;
```

### 6. Concatenate Columns

```sql
-- Combine first and last name
SELECT CONCAT(EmpName, ' ', EmpLName) AS full_name
FROM emp;

-- OR
SELECT EmpName || ' ' || EmpLName AS full_name
FROM emp;
```

### 7. Sorting

```sql
-- Sort by multiple columns
SELECT * FROM emp
ORDER BY empLname DESC, dept ASC;
```

### 8. NOT IN Clause

```sql
-- Exclude specific records
SELECT * FROM EmployeeInfo
WHERE EmpFname NOT IN ('Sanjay', 'Sonia');
```

### 9. GROUP BY with COUNT

```sql
-- Department-wise employee count
SELECT Department, COUNT(EmpID) AS EmpDeptCount
FROM EmployeeInfo
GROUP BY Department
ORDER BY EmpDeptCount ASC;
```

### 10. Find Duplicate Records

```sql
-- Find duplicate employees
SELECT EmpID, EmpFname, Department, COUNT(*)
FROM EmployeeInfo
GROUP BY EmpID, EmpFname, Department
HAVING COUNT(*) > 1;
```

### 11. Self-Join

```sql
-- Find employees in same department
SELECT DISTINCT E.EmpID, E.EmpFname, E.Department
FROM EmployeeInfo E, EmployeeInfo E1
WHERE E.Department = E1.Department 
AND E.EmpID != E1.EmpID;
```

### 12. First and Last Records

```sql
-- First record
SELECT * FROM EmployeeInfo
WHERE EmpID = (SELECT MIN(EmpID) FROM EmployeeInfo);

-- Last record
SELECT * FROM EmployeeInfo
WHERE EmpID = (SELECT MAX(EmpID) FROM EmployeeInfo);
```

### 13. Email Validation

```sql
-- Find invalid emails
SELECT Email FROM EmployeeInfo 
WHERE NOT REGEXP_LIKE(Email, '[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}', 'i');
```

### 14. HAVING Clause

```sql
-- Departments with less than 2 employees
SELECT DEPARTMENT, COUNT(EmpID) as 'EmpNo'
FROM EmployeeInfo
GROUP BY DEPARTMENT 
HAVING COUNT(EmpID) < 2;
```

### 15. Aggregate Functions

```sql
-- Total salary by position
SELECT EmpPosition, SUM(Salary)
FROM EmployeePosition
GROUP BY EmpPosition;

-- Average salary by department
SELECT Department, AVG(Salary)
FROM EmployeeInfo
GROUP BY Department;
```

### 16. Fetch Percentage of Records

```sql
-- Fetch 50% of records
SELECT *
FROM EmployeeInfo
WHERE EmpID <= (SELECT COUNT(EmpID)/2 FROM EmployeeInfo);
```

### Database Testing Scenarios

### 1. Verify Data Insertion
```sql
-- Before test
SELECT COUNT(*) FROM users WHERE email = 'test@example.com';

-- After test
SELECT COUNT(*) FROM users WHERE email = 'test@example.com';
-- Should be 1 if insertion successful
```

### 2. Verify Data Update
```sql
-- Update user status
UPDATE users SET status = 'active' WHERE id = 123;

-- Verify update
SELECT status FROM users WHERE id = 123;
-- Should return 'active'
```

### 3. Verify Data Deletion
```sql
-- Delete user
DELETE FROM users WHERE id = 123;

-- Verify deletion
SELECT COUNT(*) FROM users WHERE id = 123;
-- Should return 0
```

### 4. Verify Data Integrity
```sql
-- Check foreign key constraints
SELECT * FROM orders 
WHERE user_id NOT IN (SELECT id FROM users);
-- Should return 0 if all orders have valid users
```

### 5. Verify Data Consistency
```sql
-- Check if total matches sum of individual records
SELECT total_amount, 
       (SELECT SUM(amount) FROM order_items WHERE order_id = orders.id) as calculated_total
FROM orders
WHERE id = 123;
-- Both should match
```

### Database Testing Best Practices

**1. Use Test Database**
- Never test on production database
- Use separate test environment
- Reset data before each test

**2. Data Cleanup**
```java
@AfterMethod
public void cleanup() {
    // Clean up test data
    executeSQL("DELETE FROM test_users WHERE created_by = 'automation'");
}
```

**3. Use Transactions**
```java
@Test
public void testUserCreation() {
    beginTransaction();
    try {
        // Perform test
        createUser();
        verifyUserInDB();
        commitTransaction();
    } catch (Exception e) {
        rollbackTransaction();
        throw e;
    }
}
```

**4. Parameterized Queries**
```java
// Prevent SQL injection
String query = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = connection.prepareStatement(query);
stmt.setInt(1, userId);
ResultSet rs = stmt.executeQuery();
```

---

## 5. ADVANCED XPATH LOCATORS

### Parent-Child Relationships

### Child to Parent Navigation

**HTML Structure:**
```html
<div class="aio UKr6le">
    <span class="nU n1">
        <a href="https://mail.google.com/x">Inbox</a>
    </span>
    <div class="bsU">2,437</div>
</div>
```

**XPath Methods:**

**Method 1: Using ancestor and following-sibling**
```java
// Get the div following the span containing "Inbox"
(//a[text()='Inbox']/ancestor::span)/following-sibling::div
```

**Method 2: Using parent and following-sibling**
```java
//a[text()='Inbox']/parent::span/following-sibling::div
```

**Method 3: Using parent shorthand**
```java
//a[text()='Inbox']/..//following-sibling::div
```

### Descendant Navigation

```java
// Get all span elements inside input
(//input[text()='Inbox']/descendant::span)

// Find button which has a child span
//button[child::span[@class="MuiIconButton-label"]]

// Find button with any child span
//button[child::span]

// Find label with span inside it
//label[span]
```

### Sibling Navigation

```java
// Get sibling element preceding to the current context node
//div[@class='sort']/preceding-sibling::input

// Get following sibling
//h1/following-sibling::ul

// Get immediate following sibling
//h1/following-sibling::ul[1]
```

### Common XPath Patterns

| Pattern | Description | Example |
|---------|-------------|---------|
| `//div//p` | Descendant (any level) | All p inside div |
| `//ul > li` | Direct child | Direct li children of ul |
| `//ul > li > a` | Direct child chain | a inside li inside ul |
| `//div > *` | All direct children | All direct children of div |
| `//*[@class="class"]` | Attribute selector | Elements with specific class |
| `//h1 ~ ul` | General sibling | Any ul sibling of h1 |
| `//h1 + ul` | Adjacent sibling | Immediate ul sibling of h1 |

### Multiple Conditions with OR

```java
// Find elements by either //a or //span
//a | //span

// Specific examples
//a[@id="favourites"] | //span[@class="checkmark"]
```

### Handling SVG Elements

SVG elements require special handling as they're in a different namespace.

```java
//*[name()='svg']//*[name()='span']
```

**Why?** SVG elements use XML namespace, so standard XPath won't match them directly.

### Advanced XPath Functions

### 1. Contains
```java
//a[contains(@href, 'login')]
//div[contains(text(), 'Welcome')]
```

### 2. Starts With
```java
//input[starts-with(@id, 'username')]
//div[starts-with(@class, 'btn')]
```

### 3. Text Content
```java
//button[text()='Submit']
//a[contains(text(), 'Click')]
```

### 4. Position
```java
//ul/li[1]  // First li
//ul/li[last()]  // Last li
//ul/li[position() > 2]  // All li after 2nd
```

### 5. Following and Preceding
```java
//div[@id='content']/following::p  // All p after content div
//div[@id='content']/preceding::h1  // All h1 before content div
```

### XPath vs CSS Selector

| Feature | XPath | CSS Selector |
|---------|-------|--------------|
| Parent navigation | ✓ (parent::) | ✗ |
| Sibling navigation | ✓ (following-sibling::) | ✗ (limited) |
| Text content | ✓ (text()) | ✗ |
| Contains | ✓ (contains()) | ✓ (contains()) |
| Starts with | ✓ (starts-with()) | ✓ (^=) |
| Position | ✓ (position()) | ✓ (:nth-child()) |
| Performance | Slower | Faster |

**When to use XPath:**
- Need to traverse up (parent)
- Need to find by text content
- Complex conditions
- SVG elements

**When to use CSS Selector:**
- Simple attribute matching
- Performance critical
- Direct child relationships

---

## 6. CHROME OPTIONS & DESIREDCAPABILITIES

### Chrome Options Class

The ChromeOptions class is a Selenium WebDriver class used to customize and control how the Chrome browser launches and behaves during automation.

**In simple words:** ChromeOptions = settings/configurations for Chrome browser before it starts.

### Common Chrome Options

### 1. Maximize Window
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--start-maximized");
WebDriver driver = new ChromeDriver(options);
```

### 2. Headless Mode
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriver driver = new ChromeDriver(options);
```

**Use Cases:**
- CI/CD pipelines (no UI needed)
- Faster execution
- Server environments without display

### 3. Disable Notifications
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notifications");
WebDriver driver = new ChromeDriver(options);
```

### 4. Incognito Mode
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--incognito");
WebDriver driver = new ChromeDriver(options);
```

### 5. Disable Info Bars
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-infobars");
WebDriver driver = new ChromeDriver(options);
```

### 6. Set Download Directory
```java
ChromeOptions options = new ChromeOptions();
Map<String, Object> prefs = new HashMap<>();
prefs.put("download.default_directory", "C:\\Downloads");
prefs.put("download.prompt_for_download", false);
options.setExperimentalOption("prefs", prefs);
WebDriver driver = new ChromeDriver(options);
```

### 7. Disable GPU
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-gpu");
WebDriver driver = new ChromeDriver(options);
```

### 8. Ignore SSL Errors
```java
ChromeOptions options = new ChromeOptions();
options.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(options);
```

**Use Case:** Testing with self-signed certificates

### 9. Set User Agent
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X)");
WebDriver driver = new ChromeDriver(options);
```

**Use Case:** Mobile emulation

### 10. Disable Extensions
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-extensions");
WebDriver driver = new ChromeDriver(options);
```

### Complete Example

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--start-maximized");
options.addArguments("--headless");
options.addArguments("--disable-notifications");
options.addArguments("--incognito");
options.addArguments("--disable-infobars");

Map<String, Object> prefs = new HashMap<>();
prefs.put("download.default_directory", "C:\\Downloads");
options.setExperimentalOption("prefs", prefs);

WebDriver driver = new ChromeDriver(options);
```

### DesiredCapabilities

DesiredCapabilities class is used to modify multiple properties of web drivers. It provides key-value pairs to change individual properties of web drivers such as browser name, browser platform, etc.

**Common use:** Selenium Grid - execute same test on different browsers.

### Example Usage

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setBrowserName("chrome");
capabilities.setPlatform(Platform.WINDOWS);
capabilities.setVersion("90");

WebDriver driver = new RemoteWebDriver(
    new URL("http://localhost:4444/wd/hub"), 
    capabilities
);
```

### Chrome Options vs DesiredCapabilities

| Feature | Chrome Options | DesiredCapabilities |
|---------|----------------|-------------------|
| Purpose | Chrome-specific settings | Cross-browser capabilities |
| Usage | Chrome only | All browsers |
| Modern | ✓ Recommended | ⚠️ Deprecated in Selenium 4 |
| Grid | Can be converted | Direct usage |

**Selenium 4 Note:** DesiredCapabilities is being deprecated. Use Options classes instead.

### Mobile Emulation with Chrome Options

```java
ChromeOptions options = new ChromeOptions();
Map<String, Object> mobileEmulation = new HashMap<>();
mobileEmulation.put("deviceName", "iPhone X");
options.setExperimentalOption("mobileEmulation", mobileEmulation);

WebDriver driver = new ChromeDriver(options);
```

### Proxy Settings

```java
ChromeOptions options = new ChromeOptions();
Proxy proxy = new Proxy();
proxy.setHttpProxy("myproxy.com:8080");
proxy.setSslProxy("myproxy.com:8080");
options.setProxy(proxy);

WebDriver driver = new ChromeDriver(options);
```

---

## 7. TEST STRATEGY VS TEST PLAN

### Key Difference

**Test Strategy = The rules and approach for testing (like a general game plan)**
**Test Plan = The step-by-step plan for testing a particular project**

**One-line summary:**
- Test Strategy = How we test in general
- Test Plan = How we test this specific project

### Detailed Comparison

| Aspect | Test Plan | Test Strategy |
|--------|-----------|---------------|
| **Meaning** | Document explaining how testing will be done for a specific project | Document explaining overall testing approach or method |
| **Scope** | Project-specific | Organization or program level (can be reused) |
| **Focus** | What to test, who will test, when testing will happen | Types of testing, tools, standards |
| **Prepared by** | Test Manager / Test Lead for a project | QA Manager / senior management |
| **Detail Level** | Detailed and operational | High-level and strategic |
| **Example** | Testing schedule, resources, test environment, deliverables | Test approach, automation strategy, risk management |
| **Duration** | Short-term (project duration) | Long-term (can span multiple projects) |
| **Changes** | Changes frequently based on project needs | Relatively stable |

### Test Strategy Components

**1. Testing Scope**
- In-scope features
- Out-of-scope features
- Types of testing (functional, non-functional)

**2. Testing Approach**
- Manual vs automation ratio
- Test levels (unit, integration, system, acceptance)
- Test techniques (boundary value, equivalence partitioning)

**3. Tools & Technologies**
- Test management tools
- Automation tools
- CI/CD tools
- Defect tracking tools

**4. Standards & Guidelines**
- Coding standards
- Naming conventions
- Documentation standards
- Review process

**5. Resource Planning**
- Team structure
- Skill requirements
- Training needs
- Infrastructure needs

**6. Risk Management**
- Identify risks
- Mitigation strategies
- Contingency plans

### Test Plan Components

**1. Introduction**
- Project overview
- Testing objectives
- References

**2. Test Scope**
- Features to test
- Features not to test
- In-scope and out-scope items

**3. Test Approach**
- Test types
- Test levels
- Entry and exit criteria

**4. Test Schedule**
- Milestones
- Deliverables
- Timeline

**5. Resources**
- Team members
- Roles and responsibilities
- Hardware and software requirements

**6. Test Environment**
- Environment details
- Configuration
- Setup and teardown

**7. Deliverables**
- Test cases
- Test scripts
- Test reports
- Defect reports

**8. Risk & Contingencies**
- Identified risks
- Mitigation plans

### Example Test Strategy Outline

```markdown
# Test Strategy for ABC Organization

## 1. Overview
This document defines the overall testing approach for all projects at ABC Organization.

## 2. Testing Levels
- Unit Testing: Developers
- Integration Testing: QA Team
- System Testing: QA Team
- UAT: Business Users

## 3. Automation Strategy
- 70% automation, 30% manual
- Selenium for web automation
- RestAssured for API automation
- Appium for mobile automation

## 4. Tools
- Test Management: JIRA
- Automation: Selenium, RestAssured
- CI/CD: Jenkins
- Reporting: ExtentReports

## 5. Standards
- Code review mandatory
- Test cases in Gherkin format
- Daily builds and smoke tests
```

### Example Test Plan Outline

```markdown
# Test Plan for E-Commerce Project

## 1. Introduction
Testing the E-Commerce application for XYZ client.

## 2. Scope
### In-Scope
- User registration and login
- Product browsing and search
- Shopping cart and checkout
- Payment integration

### Out-of-Scope
- Admin panel
- Third-party integrations (except payment)

## 3. Schedule
- Sprint 1: User module (2 weeks)
- Sprint 2: Product module (2 weeks)
- Sprint 3: Cart module (2 weeks)
- Sprint 4: Checkout module (2 weeks)

## 4. Resources
- QA Lead: 1
- QA Engineers: 3
- SDETs: 2

## 5. Environment
- Dev: Continuous
- QA: Weekly builds
- Staging: Before release
```

### Interview Question: When to create Test Strategy vs Test Plan?

**Answer:**
- **Test Strategy:** Created at organization level or at the start of a large program. It's reusable across projects and defines the overall approach.
- **Test Plan:** Created for each specific project. It's detailed and operational, defining what, when, and how testing will be done for that project.

---

## 8. API AUTOMATION FRAMEWORK DESIGN

### Building a Test Automation Framework from Scratch

### Key Components

**1. Project Structure**
```
api-tests/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   ├── BaseTest.java
│   │       │   └── APIBase.java
│   │       ├── config/
│   │       │   ├── ConfigReader.java
│   │       │   └── config.properties
│   │       ├── models/
│   │       │   ├── User.java
│   │       │   ├── Request.java
│   │       │   └── Response.java
│   │       ├── utils/
│   │       │   ├── APIUtils.java
│   │       │   ├── TestDataGenerator.java
│   │       │   └── FileUtils.java
│   │       └── constants/
│   │           ├── EndPoints.java
│   │           └── Constants.java
│   └── test/
│       └── java/
│           ├── tests/
│           │   ├── user/
│           │   │   ├── GetUserTests.java
│           │   │   ├── CreateUserTests.java
│           │   │   └── UpdateUserTests.java
│           │   └── product/
│           │       ├── GetProductTests.java
│           │       └── CreateProductTests.java
│           └── suites/
│               ├── regression.xml
│               └── smoke.xml
├── src/test/resources/
│   ├── testdata/
│   │   ├── user_data.json
│   │   └── product_data.json
│   └── schemas/
│       ├── user_schema.json
│       └── product_schema.json
└── pom.xml
```

**2. Base Test Class**

```java
public class BaseTest {
    
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeSuite
    public void setup() {
        // Configure RestAssured
        RestAssured.baseURI = ConfigReader.get("base.url");
        RestAssured.basePath = ConfigReader.get("base.path");
        
        // Request specification
        requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addHeader("Accept", "application/json")
            .log(LogDetail.ALL)
            .build();
        
        // Response specification
        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    }
    
    @AfterSuite
    public void teardown() {
        // Cleanup if needed
    }
}
```

**3. Configuration Management**

```java
public class ConfigReader {
    private static Properties properties;
    
    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
```

**config.properties:**
```properties
base.url=https://reqres.in
base.path=/api
username=testuser
password=testpass
```

**4. EndPoints Constants**

```java
public class EndPoints {
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
}
```

**5. POJO Models**

```java
public class User {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    // ... other getters/setters
}
```

**6. API Test Example**

```java
public class GetUserTests extends BaseTest {
    
    @Test
    public void testGetAllUsers() {
        given()
            .spec(requestSpec)
        .when()
            .get(EndPoints.USERS)
        .then()
            .spec(responseSpec)
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }
    
    @Test(dataProvider = "userData")
    public void testGetUserById(int userId) {
        given()
            .spec(requestSpec)
            .pathParam("id", userId)
        .when()
            .get(EndPoints.USER_BY_ID)
        .then()
            .spec(responseSpec)
            .body("data.id", equalTo(userId));
    }
    
    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][] {
            {1},
            {2},
            {3}
        };
    }
}
```

**7. Utility Classes**

```java
public class APIUtils {
    
    public static String getRandomEmail() {
        return "test" + System.currentTimeMillis() + "@example.com";
    }
    
    public static String getRandomName() {
        return "User" + System.currentTimeMillis();
    }
    
    public static String parseJSONResponse(Response response, String path) {
        return response.jsonPath().getString(path);
    }
}
```

**8. Logging & Reporting**

```java
@Listeners(ExtentReportListener.class)
public class BaseTest {
    // ... existing code
    
    @BeforeMethod
    public void beforeMethod(Method method) {
        ExtentTestManager.startTest(method.getName());
    }
    
    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
        }
        ExtentTestManager.endTest();
    }
}
```

**9. Error Handling & Retry**

```java
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3;
    
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}

@Test(retryAnalyzer = RetryAnalyzer.class)
public void flakyAPITest() {
    // Test that might fail intermittently
}
```

**10. Parallel Execution**

```xml
<!-- testng.xml -->
<suite name="API Test Suite" parallel="tests" thread-count="5">
    <test name="User Tests">
        <classes>
            <class name="tests.user.GetUserTests"/>
        </classes>
    </test>
    <test name="Product Tests">
        <classes>
            <class name="tests.product.GetProductTests"/>
        </classes>
    </test>
</suite>
```

### Framework Best Practices

**1. Separation of Concerns**
- Separate test logic from business logic
- Use Page Object Model equivalent for APIs (API Object Model)
- Keep configuration separate from code

**2. Reusability**
- Create reusable methods for common operations
- Use data providers for parameterized tests
- Create utility classes for common functions

**3. Maintainability**
- Use meaningful names for tests and methods
- Add comments for complex logic
- Keep test data separate from test code

**4. Scalability**
- Design for parallel execution
- Use environment-specific configurations
- Modular design for easy additions

**5. Reporting**
- Generate comprehensive reports
- Include request/response logs
- Capture screenshots (if applicable)

---

## 9. MICROSERVICES TESTING

### Microservices Architecture Challenges

**1. Service Dependencies**
- Multiple services depend on each other
- Testing one service may require others to be running
- Need to mock or stub dependencies

**2. Data Consistency**
- Data distributed across multiple databases
- Ensuring consistency is challenging
- Need distributed transaction management

**3. Network Latency**
- Services communicate over network
- Latency affects performance
- Need to handle timeouts and retries

**4. Deployment Complexity**
- Multiple services to deploy
- Version compatibility issues
- Rolling deployments

### Testing Strategies

**1. Unit Testing**
- Test individual service in isolation
- Mock all dependencies
- Fast feedback

```java
@Test
public void testUserService() {
    // Mock database and other services
    when(userRepository.findById(1)).thenReturn(testUser);
    
    User user = userService.getUser(1);
    
    assertEquals("John", user.getName());
}
```

**2. Integration Testing**
- Test service with real dependencies
- Use test database
- Test API contracts

```java
@Test
public void testUserAPIIntegration() {
    Response response = given()
        .contentType(ContentType.JSON)
        .body(testUser)
    .when()
        post("/api/users");
    
    assertEquals(201, response.getStatusCode());
}
```

**3. Contract Testing**
- Ensure API contracts are maintained
- Use tools like Pact
- Consumer-driven contracts

```java
// Consumer test
@Pact(provider = "UserService", consumer = "OrderService")
public RequestResponsePact createPact(PactDslWithProvider builder) {
    return builder
        .given("User exists")
        .uponReceiving("Request for user")
        .path("/api/users/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(PactDslJsonBody.create()
            .integerType("id", 1)
            .stringType("name", "John"))
        .toPact();
}
```

**4. End-to-End Testing**
- Test entire user journey
- All services running
- Realistic scenarios

```java
@Test
public void testCompleteOrderFlow() {
    // Create user
    User user = createUserService.createUser(testUser);
    
    // Create product
    Product product = productService.createProduct(testProduct);
    
    // Create order
    Order order = orderService.createOrder(user.getId(), product.getId());
    
    // Verify order
    assertEquals("CREATED", order.getStatus());
}
```

### Mocking External Services

**1. Using WireMock**

```java
@Rule
public WireMockRule wireMockRule = new WireMockRule(8080);

@Test
public void testWithMockService() {
    // Configure mock
    stubFor(get(urlEqualTo("/api/users/1"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{\"id\":1,\"name\":\"John\"}")));
    
    // Test your service
    User user = userService.getUser(1);
    assertEquals("John", user.getName());
}
```

**2. Using MockServer**

```java
ClientAndServer mockServer = ClientAndServer.startClientAndServer(8080);

mockServer.when(
    request()
        .withMethod("GET")
        .withPath("/api/users/1")
).respond(
    response()
        .withStatusCode(200)
        .withBody("{\"id\":1,\"name\":\"John\"}")
);
```

### Service Virtualization

**Benefits:**
- Test services independently
- Simulate various scenarios
- Reduce dependency on actual services
- Faster test execution

**Tools:**
- Hoverfly
- Mountebank
- Service Virtualization platforms

### Testing Distributed Transactions

**Challenges:**
- ACID properties across services
- Saga pattern implementation
- Compensation transactions

**Testing Approach:**
```java
@Test
public void testDistributedTransaction() {
    // Start transaction
    Transaction transaction = transactionService.beginTransaction();
    
    try {
        // Update user service
        userService.updateBalance(userId, amount);
        
        // Update order service
        orderService.createOrder(order);
        
        // Commit transaction
        transactionService.commit(transaction);
        
        // Verify both services updated
        assertEquals(newBalance, userService.getBalance(userId));
        assertEquals("CREATED", orderService.getStatus(orderId));
    } catch (Exception e) {
        // Rollback transaction
        transactionService.rollback(transaction);
    }
}
```

---

## 10. SSL/TLS HANDLING IN AUTOMATION

### SSL/TLS Basics

**SSL (Secure Sockets Layer)** and **TLS (Transport Layer Security)** are protocols for secure communication over the internet.

**Common Issues:**
- Self-signed certificates
- Expired certificates
- Certificate chain issues
- Hostname mismatch

### UI Automation - SSL Handling

### Firefox Options

```java
FirefoxOptions options = new FirefoxOptions();
options.setAcceptInsecureCerts(true);
WebDriver driver = new FirefoxDriver(options);
```

### Chrome Options

```java
ChromeOptions options = new ChromeOptions();
options.setAcceptInsecureCerts(true);
WebDriver driver = new ChromeDriver(options);
```

### DesiredCapabilities (Legacy)

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
WebDriver driver = new ChromeDriver(capabilities);
```

### API Automation - SSL Handling

### RestAssured

```java
// Accept all certificates (including self-signed)
RestAssured.useRelaxedHTTPSValidation();

// For specific SSL context
SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(null, new TrustManager[]{new X509TrustManager() {
    public X509Certificate[] getAcceptedIssuers() { return null; }
    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
}}, new SecureRandom());

RestAssured.config = RestAssured.config().sslConfig(
    SSLConfig.sslConfig().with().sslContext(sslContext).and().relaxedHTTPSValidation()
);
```

### Common SSL Errors

**1. PKIX Path Building Failed**
```
javax.net.ssl.SSLHandshakeException: 
sun.security.validator.ValidatorException: 
PKIX path building failed: 
sun.security.provider.certpath.SunCertPathBuilderException: 
unable to find valid certification path to requested target
```

**Solution:**
```java
RestAssured.useRelaxedHTTPSValidation();
```

**2. Certificate Expired**
```
javax.net.ssl.SSLHandshakeException: 
sun.security.validator.ValidatorException: 
PKIX path validation failed: 
certificate has expired
```

**Solution:** Update the certificate or use relaxed validation for testing.

**3. Hostname Verification Failed**
```
javax.net.ssl.SSLPeerUnverifiedException: 
Certificate for <localhost> doesn't match any of the subject alternative names
```

**Solution:**
```java
RestAssured.config = RestAssured.config().sslConfig(
    SSLConfig.sslConfig().with().hostnameVerifier(HostnameVerifier.ALLOW_ALL)
);
```

### Best Practices

**1. Development/Testing:**
- Use relaxed HTTPS validation
- Accept self-signed certificates
- Disable hostname verification

**2. Staging/Production:**
- Never use relaxed validation
- Use valid certificates
- Enable hostname verification
- Implement proper certificate validation

**3. Certificate Management:**
- Store certificates in keystore
- Use environment-specific configurations
- Monitor certificate expiration
- Implement certificate rotation

---

## 11. ADVANCED INTERVIEW QUESTIONS

### API Testing Questions

**Q1: How do you handle authentication in API testing?**

**Answer:**
- **Basic Auth:** `auth().basic("username", "password")`
- **Bearer Token:** `auth().oauth2("token")` or header `Authorization: Bearer token`
- **API Key:** Header `X-API-Key: key`
- **OAuth2:** Implement OAuth2 flow, get access token, use in requests
- **Session/Cookie:** Extract cookie from login response, use in subsequent requests

**Q2: How do you test API performance?**

**Answer:**
- Measure response time using RestAssured: `.time(lessThan(5000L))`
- Load testing with tools like JMeter, Gatling
- Test under concurrent load
- Monitor API degradation
- Test rate limiting
- Measure throughput (requests per second)

**Q3: How do you handle versioning in APIs?**

**Answer:**
- Include version in URL: `/api/v1/users`, `/api/v2/users`
- Include version in header: `Accept: application/vnd.api.v1+json`
- Test all supported versions
- Ensure backward compatibility
- Document version changes

**Q4: How do you test API security?**

**Answer:**
- Test authentication and authorization
- SQL injection attempts
- XSS attempts
- Rate limiting
- Sensitive data exposure in responses
- CORS configuration
- Input validation
- Test for common vulnerabilities (OWASP API Security Top 10)

**Q5: How do you mock external APIs in testing?**

**Answer:**
- Use WireMock for HTTP service stubbing
- Use MockServer for advanced mocking
- Use Hoverfly for service virtualization
- Mock based on contracts
- Simulate various scenarios (success, failure, timeout)
- Record and replay real API responses

### Database Testing Questions

**Q1: How do you verify data integrity in database testing?**

**Answer:**
- Check foreign key constraints
- Verify referential integrity
- Check data type constraints
- Verify unique constraints
- Check business rules
- Validate data relationships
- Use checksums for data verification

**Q2: How do you handle database transactions in testing?**

**Answer:**
- Begin transaction before test
- Rollback transaction after test (for isolation)
- Commit only for integration tests
- Use @Transactional annotation
- Clean up test data after each test
- Use test databases, never production

**Q3: How do you test stored procedures?**

**Answer:**
- Call stored procedure with test data
- Verify output parameters
- Verify database changes
- Test error handling
- Test with edge cases
- Verify performance for complex procedures

### Framework Design Questions

**Q1: How would you design a scalable test automation framework?**

**Answer:**
- Modular design with clear separation of concerns
- Page Object Model for UI, API Object Model for APIs
- Configuration management for environments
- Data-driven testing approach
- Parallel execution support
- Comprehensive reporting
- CI/CD integration
- Reusable utility methods
- Proper error handling and logging
- Version control friendly

**Q2: How do you handle flaky tests?**

**Answer:**
- Identify root cause (timing, dependency, environment)
- Add proper waits (explicit, not sleep)
- Implement retry mechanism
- Use test data factories for consistent data
- Isolate tests (no dependencies between tests)
- Mock external dependencies
- Run tests in random order
- Analyze and fix flaky tests regularly

**Q3: How do you measure test automation ROI?**

**Answer:**
- Time saved vs manual testing effort
- Defect detection rate (automation vs manual)
- Test coverage improvement
- Release cycle time reduction
- Maintenance effort
- Execution time reduction
- Cost of tools and infrastructure
- Training and onboarding time

### Leadership Questions

**Q1: How do you convince management to invest in test automation?**

**Answer:**
- Present data on time savings
- Show defect detection improvements
- Calculate ROI (time saved vs investment)
- Demonstrate reduced release cycle time
- Show improved test coverage
- Highlight risk reduction
- Present case studies from similar projects
- Start with pilot project to prove value

**Q2: How do you handle resistance to automation from team members?**

**Answer:**
- Understand their concerns
- Provide proper training and support
- Start with easy wins
- Show benefits through demos
- Pair programming sessions
- Make automation accessible (not just for experts)
- Celebrate successes
- Address technical challenges
- Provide time for learning

**Q3: How do you prioritize what to automate?**

**Answer:**
- Automate repetitive tasks first
- High-risk test cases
- Regression test suite
- Smoke tests
- Tests with stable requirements
- Tests that provide good ROI
- Consider maintenance cost
- Business critical paths
- Data-driven tests

---

## SUMMARY CHECKLIST FOR INTERVIEW

### REST API Testing
- [ ] HTTP methods (GET, POST, PUT, PATCH, DELETE)
- [ ] Status codes (200, 201, 400, 401, 404, 500)
- [ ] RestAssured syntax and usage
- [ ] Authentication methods (Basic, Bearer, OAuth2, API Key)
- [ ] Request/response validation
- [ ] Headers, cookies, query parameters, path parameters
- [ ] File upload via API
- [ ] SSL/TLS handling
- [ ] Logging and debugging

### Database Testing
- [ ] SQL queries (SELECT, INSERT, UPDATE, DELETE)
- [ ] Joins (inner, outer, self)
- [ ] Aggregate functions (COUNT, SUM, AVG)
- [ ] GROUP BY and HAVING
- [ ] Subqueries
- [ ] Data integrity verification
- [ ] Transaction handling
- [ ] Test data management

### Advanced Locators
- [ ] XPath parent-child navigation
- [ ] XPath sibling navigation
- [ ] XPath functions (contains, starts-with)
- [ ] CSS selectors
- [ ] SVG element handling
- [ ] Multiple conditions with OR

### Browser Configuration
- [ ] Chrome Options
- [ ] Headless mode
- [ ] Download directory configuration
- [ ] SSL certificate handling
- [ ] Proxy settings
- [ ] Mobile emulation
- [ ] DesiredCapabilities

### Framework Design
- [ ] Project structure
- [ ] Configuration management
- [ ] Base test class
- [ ] Utility classes
- [ ] Data providers
- [ ] Error handling
- [ ] Parallel execution
- [ ] Reporting

### Test Strategy vs Test Plan
- [ ] Difference between strategy and plan
- [ ] When to create each
- [ ] Components of test strategy
- [ ] Components of test plan

### Microservices Testing
- [ ] Unit testing
- [ ] Integration testing
- [ ] Contract testing
- [ ] End-to-end testing
- [ ] Service mocking
- [ ] Distributed transactions

### Leadership & Strategy
- [ ] Test automation ROI calculation
- [ ] Prioritizing automation
- [ ] Handling team resistance
- [ ] Convincing management
- [ ] Flaky test handling

---

## FINAL TIPS

1. **Practice with real APIs** - Use public APIs like reqres.in, jsonplaceholder.typicode.com
2. **Build your own framework** - Don't just read, implement
3. **Understand the "why"** - Know why certain approaches are used
4. **Stay updated** - Keep up with latest tools and practices
5. **Document your work** - Create documentation for your frameworks
6. **Share knowledge** - Teach others to reinforce your learning
7. **Think from business perspective** - Understand how testing adds value

---

**Good luck with your interview! This guide covers API testing, database testing, and advanced automation concepts essential for a senior QA automation engineer position.**
