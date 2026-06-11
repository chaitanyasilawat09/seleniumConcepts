# Contract Testing - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Contract Testing Fundamentals
2. Consumer-Driven Contract Testing
3. Provider-Driven Contract Testing
4. Pact Framework
5. Pact Contract Definition
6. Pact Consumer Testing
7. Pact Provider Testing
8. Pact Broker
9. Spring Cloud Contract
10. Contract Testing in CI/CD
11. Contract Testing Best Practices
12. Contract Testing Challenges
13. Contract Testing Tools
14. Contract Testing for REST APIs
15. Contract Testing for GraphQL
16. Contract Testing for gRPC
17. Contract Testing for Message Queues
18. Contract Versioning
19. Contract Testing vs Integration Testing
20. Common Interview Questions
21. Code Examples
22. Practice Scenarios

---

## 1. CONTRACT TESTING FUNDAMENTALS

### What is Contract Testing?
Contract testing ensures that services adhere to a shared contract. It validates that consumer expectations match provider implementation.

### Contract Testing Purpose

**Purpose:**
- Validate API contracts
- Ensure compatibility
- Enable independent development
- Prevent breaking changes
- Document API behavior

### Contract Types

**Types:**
- Consumer-driven: Consumer defines contract
- Provider-driven: Provider defines contract
- Bidirectional: Both define contract

### Contract Testing Benefits

**Benefits:**
- Early feedback
- Independent development
- Version compatibility
- Documentation
- Reduced integration issues

### Interview Questions

**Q1: What is contract testing?**
- Ensures services adhere to contract
- Consumer expectations match provider
- Validates API contracts
- Enables independent development

**Q2: What is the purpose of contract testing?**
- Validate API contracts
- Ensure compatibility
- Enable independent development
- Prevent breaking changes

**Q3: What are the types of contract testing?**
- Consumer-driven: Consumer defines
- Provider-driven: Provider defines
- Bidirectional: Both define

---

## 2. CONSUMER-DRIVEN CONTRACT TESTING

### What is Consumer-Driven Contract Testing?
Consumer-driven contract testing (CDCT) allows consumers to define the contract they expect from providers. Providers implement the contract and verify compliance.

### CDCT Process

**Process:**
1. Consumer defines contract
2. Consumer tests against contract
3. Consumer publishes contract
4. Provider implements contract
5. Provider verifies against contract

### CDCT Benefits

**Benefits:**
- Consumer-driven
- Early feedback
- Independent development
- Version compatibility
- Documentation

### CDCT Challenges

**Challenges:**
- Contract management
- Version compatibility
- Multiple consumers
- Contract evolution

### Interview Questions

**Q1: What is consumer-driven contract testing?**
- Consumer defines contract
- Provider implements contract
- Consumer tests against contract
- Provider verifies compliance

**Q2: What is the CDCT process?**
- Consumer defines contract
- Consumer tests against contract
- Consumer publishes contract
- Provider implements and verifies

**Q3: What are CDCT benefits?**
- Consumer-driven
- Early feedback
- Independent development
- Version compatibility

---

## 3. PROVIDER-DRIVEN CONTRACT TESTING

### What is Provider-Driven Contract Testing?
Provider-driven contract testing allows providers to define the contract they provide. Consumers verify their implementation against the contract.

### Provider-Driven Process

**Process:**
1. Provider defines contract
2. Provider publishes contract
3. Consumer implements client
4. Consumer verifies against contract
5. Provider ensures contract compliance

### Provider-Driven Benefits

**Benefits:**
- Provider control
- Consistent API
- Clear documentation
- Consumer compliance

### Provider-Driven Challenges

**Challenges:**
- Consumer feedback limited
- May not meet consumer needs
- Less flexibility
- Provider-centric

### Interview Questions

**Q1: What is provider-driven contract testing?**
- Provider defines contract
- Consumer verifies against contract
- Provider ensures compliance
- Provider control

**Q2: What is the provider-driven process?**
- Provider defines contract
- Provider publishes contract
- Consumer implements and verifies
- Provider ensures compliance

**Q3: What are provider-driven benefits?**
- Provider control
- Consistent API
- Clear documentation
- Consumer compliance

---

## 4. PACT FRAMEWORK

### What is Pact?
Pact is a contract testing framework that enables consumer-driven contract testing. It supports multiple languages and integrates with CI/CD.

### Pact Components

**Components:**
- Pact Consumer: Consumer testing
- Pact Provider: Provider testing
- Pact Broker: Contract storage
- Pact Foundation: Core library

### Pact Languages

**Languages:**
- Java
- JavaScript
- Python
- Ruby
- .NET
- Go
- PHP

### Pact Setup

**Maven Dependency:**
```xml
<dependency>
    <groupId>au.com.dius.pact.consumer</groupId>
    <artifactId>junit5</artifactId>
    <version>4.6.3</version>
    <scope>test</scope>
</dependency>
```

### Interview Questions

**Q1: What is Pact?**
- Contract testing framework
- Consumer-driven
- Multiple languages
- CI/CD integration

**Q2: What are Pact components?**
- Pact Consumer: Consumer testing
- Pact Provider: Provider testing
- Pact Broker: Contract storage
- Pact Foundation: Core library

**Q3: What languages does Pact support?**
- Java, JavaScript, Python
- Ruby, .NET, Go, PHP
- Wide language support

---

## 5. PACT CONTRACT DEFINITION

### Pact Contract Format

**Format:**
```json
{
  "consumer": "ConsumerService",
  "provider": "ProviderService",
  "interactions": [
    {
      "description": "Get user",
      "request": {
        "method": "GET",
        "path": "/api/users/1"
      },
      "response": {
        "status": 200,
        "body": {
          "id": 1,
          "name": "John Doe"
        }
      }
    }
  ]
}
```

### Pact DSL

**Java DSL:**
```java
@Pact(provider = "ProviderService", consumer = "ConsumerService")
public RequestResponsePact createPact(PactDslWithProvider builder) {
    return builder
        .uponReceiving("Get user")
        .path("/api/users/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("{\"id\":1,\"name\":\"John Doe\"}")
        .toPact();
}
```

### Pact Matching Rules

**Rules:**
- Exact match
- Regex match
- Type match
- Array match
- Min/Max match

### Interview Questions

**Q1: What is the Pact contract format?**
- JSON format
- Consumer and provider names
- Interactions array
- Request and response

**Q2: How do you define a Pact contract in Java?**
- Use @Pact annotation
- Use PactDslWithProvider
- Define request and response
- Return Pact

**Q3: What are Pact matching rules?**
- Exact match
- Regex match
- Type match
- Array match
- Min/Max match

---

## 6. PACT CONSUMER TESTING

### Consumer Test Setup

**Setup:**
```java
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProviderService")
public class ConsumerTest {
    
    @MockServer
    private MockServer mockServer;
    
    @Test
    public void testGetUser() {
        // Test against mock server
        String response = mockServer.getUrl() + "/api/users/1";
        // Verify response
    }
}
```

### Consumer Pact Generation

**Generation:**
```java
@Pact(provider = "ProviderService", consumer = "ConsumerService")
public RequestResponsePact createPact(PactDslWithProvider builder) {
    return builder
        .uponReceiving("Get user")
        .path("/api/users/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("{\"id\":1,\"name\":\"John Doe\"}")
        .toPact();
}
```

### Consumer Test Execution

**Execution:**
- Run consumer tests
- Generate Pact files
- Publish to Pact Broker
- Verify contract compliance

### Interview Questions

**Q1: How do you set up consumer tests in Pact?**
- Use @ExtendWith(PactConsumerTestExt.class)
- Use @PactTestFor
- Use @MockServer
- Test against mock server

**Q2: How do you generate Pact contracts?**
- Use @Pact annotation
- Use PactDslWithProvider
- Define request and response
- Return Pact

**Q3: How do you execute consumer tests?**
- Run consumer tests
- Generate Pact files
- Publish to Pact Broker
- Verify compliance

---

## 7. PACT PROVIDER TESTING

### Provider Test Setup

**Setup:**
```java
@Provider("ProviderService")
@PactFolder("pacts")
@PactBroker(host = "localhost", port = "9292")
public class ProviderTest {
    
    @TestTarget
    public final MockMvcTestTarget target = new MockMvcTestTarget();
    
    @BeforeEach
    public void setup() {
        // Setup provider
    }
    
    @TestTemplate
    @PactBrokerPacts
    public void testContract(PactFragment pactFragment) {
        // Test provider against contract
    }
}
```

### Provider Verification

**Verification:**
- Load Pact files
- Verify request matching
- Verify response matching
- Report results

### Provider State Management

**State:**
```java
@State("User exists")
public void userExists() {
    // Setup test data
    userRepository.save(new User(1, "John Doe"));
}
```

### Interview Questions

**Q1: How do you set up provider tests in Pact?**
- Use @Provider annotation
- Use @PactFolder
- Use @PactBroker
- Use @TestTarget

**Q2: How do you verify provider contracts?**
- Load Pact files
- Verify request matching
- Verify response matching
- Report results

**Q3: How do you manage provider state?**
- Use @State annotation
- Setup test data
- Define state methods
- Reset state after test

---

## 8. PACT BROKER

### What is Pact Broker?
Pact Broker is a repository for sharing Pact contracts between consumers and providers. It enables contract verification and version management.

### Pact Broker Features

**Features:**
- Contract storage
- Contract verification
- Version management
- Webhooks
- Dashboard

### Pact Broker Setup

**Docker:**
```bash
docker run -d -p 9292:9292 \
  -e PACT_BROKER_DATABASE_USERNAME=admin \
  -e PACT_BROKER_DATABASE_PASSWORD=admin \
  pactfoundation/pact-broker
```

### Pact Broker Usage

**Usage:**
- Publish contracts
- Verify contracts
- View contracts
- Manage versions

### Interview Questions

**Q1: What is Pact Broker?**
- Repository for Pact contracts
- Share contracts between services
- Contract verification
- Version management

**Q2: What are Pact Broker features?**
- Contract storage
- Contract verification
- Version management
- Webhooks
- Dashboard

**Q3: How do you set up Pact Broker?**
- Use Docker
- Run pact-broker image
- Configure database
- Expose port 9292

---

## 9. SPRING CLOUD CONTRACT

### What is Spring Cloud Contract?
Spring Cloud Contract is a contract testing framework for Spring Boot applications. It supports both consumer and provider testing.

### Spring Cloud Contract Setup

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-contract-verifier</artifactId>
    <version>4.0.4</version>
    <scope>test</scope>
</dependency>
```

### Contract Definition

**Groovy DSL:**
```groovy
org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/api/users/1'
    }
    response {
        status 200
        body([
            id: 1,
            name: 'John Doe'
        ])
    }
}
```

### Contract Testing

**Consumer Test:**
```java
@SpringBootTest
@AutoConfigureStubRunner
public class ConsumerTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testGetUser() {
        User user = userService.getUser(1);
        assertEquals("John Doe", user.getName());
    }
}
```

### Interview Questions

**Q1: What is Spring Cloud Contract?**
- Contract testing for Spring Boot
- Consumer and provider testing
- Groovy DSL
- Spring integration

**Q2: How do you set up Spring Cloud Contract?**
- Add Maven dependency
- Define contracts in Groovy
- Use @AutoConfigureStubRunner
- Run tests

**Q3: How do you define contracts in Spring Cloud Contract?**
- Use Groovy DSL
- Define request and response
- Use Contract.make()
- Store in contracts folder

---

## 10. CONTRACT TESTING IN CI/CD

### CI/CD Integration

**Integration:**
- Consumer tests in CI
- Provider tests in CD
- Publish contracts to Broker
- Verify contracts automatically

### Consumer Pipeline

**Stages:**
1. Build
2. Consumer tests
3. Generate contracts
4. Publish to Pact Broker
5. Deploy

### Provider Pipeline

**Stages:**
1. Build
2. Provider tests
3. Verify contracts
4. Deploy
5. Notify consumers

### CI/CD Tools

**Tools:**
- Jenkins
- GitHub Actions
- GitLab CI
- Azure DevOps

### Interview Questions

**Q1: How do you integrate contract testing in CI/CD?**
- Consumer tests in CI
- Provider tests in CD
- Publish to Pact Broker
- Verify automatically

**Q2: What are the consumer pipeline stages?**
- Build
- Consumer tests
- Generate contracts
- Publish to Pact Broker
- Deploy

**Q3: What are the provider pipeline stages?**
- Build
- Provider tests
- Verify contracts
- Deploy
- Notify consumers

---

## 11. CONTRACT TESTING BEST PRACTICES

### Version Contracts

**Practice:**
- Version contracts
- Use semantic versioning
- Maintain backward compatibility
- Document changes

### Test Early

**Practice:**
- Test during development
- Test in CI/CD
- Test before deployment
- Continuous testing

### Keep Contracts Simple

**Practice:**
- Define minimal contracts
- Focus on critical paths
- Avoid over-specification
- Keep contracts maintainable

### Interview Questions

**Q1: What are contract testing best practices?**
- Version contracts
- Test early
- Keep contracts simple
- Document changes

**Q2: Why version contracts?**
- Maintain compatibility
- Support multiple versions
- Enable gradual migration
- Document changes

**Q3: Why keep contracts simple?**
- Maintainable
- Focus on critical paths
- Avoid over-specification
- Easier to understand

---

## 12. CONTRACT TESTING CHALLENGES

### Contract Management

**Challenge:**
- Multiple consumers
- Multiple providers
- Contract evolution
- Version compatibility

### Contract Evolution

**Challenge:**
- Breaking changes
- Backward compatibility
- Consumer migration
- Provider updates

### Contract Synchronization

**Challenge:**
- Consumer-provider sync
- Contract updates
- Verification failures
- Communication

### Interview Questions

**Q1: What are contract testing challenges?**
- Contract management
- Contract evolution
- Contract synchronization
- Multiple consumers/providers

**Q2: How do you handle contract evolution?**
- Version contracts
- Maintain backward compatibility
- Communicate changes
- Gradual migration

**Q3: How do you handle multiple consumers?**
- Version contracts per consumer
- Use Pact Broker
- Manage versions
- Verify per consumer

---

## 13. CONTRACT TESTING TOOLS

### Pact

**Features:**
- Consumer-driven
- Multiple languages
- Pact Broker integration
- CI/CD integration

### Spring Cloud Contract

**Features:**
- Spring Boot integration
- Groovy DSL
- Consumer and provider testing
- Maven/Gradle plugins

### Other Tools

**Tools:**
- Hoverfly: Service virtualization
- WireMock: HTTP stubbing
- Postman: Contract testing
- Swagger/OpenAPI: Documentation

### Interview Questions

**Q1: What are common contract testing tools?**
- Pact: Consumer-driven
- Spring Cloud Contract: Spring Boot
- Hoverfly: Service virtualization
- WireMock: HTTP stubbing

**Q2: What are the features of Pact?**
- Consumer-driven
- Multiple languages
- Pact Broker integration
- CI/CD integration

**Q3: What are the features of Spring Cloud Contract?**
- Spring Boot integration
- Groovy DSL
- Consumer and provider testing
- Maven/Gradle plugins

---

## 14. CONTRACT TESTING FOR REST APIs

### REST API Contract

**Contract:**
```json
{
  "request": {
    "method": "GET",
    "path": "/api/users/1",
    "headers": {
      "Content-Type": "application/json"
    }
  },
  "response": {
    "status": 200,
    "headers": {
      "Content-Type": "application/json"
    },
    "body": {
      "id": 1,
      "name": "John Doe"
    }
  }
}
```

### REST API Testing

**Consumer Test:**
```java
@Test
public void testGetUser() {
    given()
        .contentType("application/json")
        .when()
            .get("/api/users/1")
        .then()
            .statusCode(200)
            .body("name", equalTo("John Doe"));
}
```

### Interview Questions

**Q1: How do you define REST API contracts?**
- Define request method
- Define request path
- Define request headers
- Define response

**Q2: How do you test REST API contracts?**
- Use RestAssured
- Verify status code
- Verify headers
- Verify body

**Q3: What are common REST API contract fields?**
- Method, path, headers
- Status code, headers
- Body, schema

---

## 15. CONTRACT TESTING FOR GRAPHQL

### GraphQL Contract

**Contract:**
```json
{
  "request": {
    "method": "POST",
    "path": "/graphql",
    "body": {
      "query": "query { user(id: 1) { name } }"
    }
  },
  "response": {
    "status": 200,
    "body": {
      "data": {
        "user": {
          "name": "John Doe"
        }
      }
    }
  }
}
```

### GraphQL Testing

**Consumer Test:**
```java
@Test
public void testGraphQL() {
    given()
        .contentType("application/json")
        .body("{\"query\":\"query { user(id: 1) { name } }\"}")
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.user.name", equalTo("John Doe"));
}
```

### Interview Questions

**Q1: How do you define GraphQL contracts?**
- Define POST method
- Define GraphQL query
- Define response data
- Verify response structure

**Q2: How do you test GraphQL contracts?**
- Use RestAssured
- Send GraphQL query
- Verify response data
- Verify response structure

**Q3: What are GraphQL contract fields?**
- Query, variables
- Response data
- Response structure

---

## 16. CONTRACT TESTING FOR gRPC

### gRPC Contract

**Contract:**
- Proto file
- Service definition
- Message definitions
- Method definitions

### gRPC Testing

**Consumer Test:**
```java
@Test
public void testGetUser() {
    UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
    UserRequest request = UserRequest.newBuilder().setId(1).build();
    UserResponse response = stub.getUser(request);
    assertEquals("John Doe", response.getName());
}
```

### Interview Questions

**Q1: How do you define gRPC contracts?**
- Use Proto file
- Define service
- Define messages
- Define methods

**Q2: How do you test gRPC contracts?**
- Use gRPC client
- Create request
- Call method
- Verify response

**Q3: What are gRPC contract components?**
- Proto file
- Service definition
- Message definitions
- Method definitions

---

## 17. CONTRACT TESTING FOR MESSAGE QUEUES

### Message Queue Contract

**Contract:**
```json
{
  "description": "User created event",
  "request": {
    "message": {
      "contentType": "application/json",
      "content": {
        "event": "USER_CREATED",
        "userId": 1,
        "userName": "John Doe"
      }
    }
  }
}
```

### Message Queue Testing

**Consumer Test:**
```java
@Test
public void testUserCreatedEvent() {
    Message message = new Message("USER_CREATED", "{\"userId\":1,\"userName\":\"John Doe\"}");
    consumer.consume(message);
    verify(consumer).consume(message);
}
```

### Interview Questions

**Q1: How do you define message queue contracts?**
- Define message content
- Define content type
- Define event type
- Define message structure

**Q2: How do you test message queue contracts?**
- Create message
- Consume message
- Verify content
- Verify structure

**Q3: What are message queue contract fields?**
- Content type
- Event type
- Message content
- Message structure

---

## 18. CONTRACT VERSIONING

### Versioning Strategies

**Strategies:**
- Semantic versioning
- Consumer versioning
- Provider versioning
- Contract versioning

### Version Compatibility

**Compatibility:**
- Backward compatibility
- Breaking changes
- Migration path
- Deprecation

### Version Management

**Management:**
- Use Pact Broker
- Tag contracts
- Manage versions
- Document changes

### Interview Questions

**Q1: What are contract versioning strategies?**
- Semantic versioning
- Consumer versioning
- Provider versioning
- Contract versioning

**Q2: How do you maintain version compatibility?**
- Backward compatibility
- Breaking changes
- Migration path
- Deprecation

**Q3: How do you manage contract versions?**
- Use Pact Broker
- Tag contracts
- Manage versions
- Document changes

---

## 19. CONTRACT TESTING VS INTEGRATION TESTING

### Contract Testing

**Focus:**
- API contract
- Consumer-provider contract
- Interface definition
- Early feedback

### Integration Testing

**Focus:**
- System integration
- End-to-end flows
- Real services
- Late feedback

### Comparison

| Aspect | Contract Testing | Integration Testing |
|--------|-----------------|-------------------|
- Contract: Interface definition
- Integration: System integration
- Contract: Early feedback
- Integration: Late feedback

### Interview Questions

**Q1: What is the difference between contract and integration testing?**
- Contract: API contract, interface
- Integration: System integration, flows
- Contract: Early feedback
- Integration: Late feedback

**Q2: When would you use contract testing?**
- Define API contract
- Consumer-provider contract
- Early development
- Independent testing

**Q3: When would you use integration testing?**
- System integration
- End-to-end flows
- Real services
- Pre-deployment

---

## 20. COMMON INTERVIEW QUESTIONS

### Fundamentals

**Q1: What is contract testing?**
- Ensures services adhere to contract
- Consumer expectations match provider
- Validates API contracts
- Enables independent development

**Q2: What is the purpose of contract testing?**
- Validate API contracts
- Ensure compatibility
- Enable independent development
- Prevent breaking changes

**Q3: What are the types of contract testing?**
- Consumer-driven: Consumer defines
- Provider-driven: Provider defines
- Bidirectional: Both define

### Consumer-Driven

**Q4: What is consumer-driven contract testing?**
- Consumer defines contract
- Provider implements contract
- Consumer tests against contract
- Provider verifies compliance

**Q5: What is the CDCT process?**
- Consumer defines contract
- Consumer tests against contract
- Consumer publishes contract
- Provider implements and verifies

**Q6: What are CDCT benefits?**
- Consumer-driven
- Early feedback
- Independent development
- Version compatibility

### Pact

**Q7: What is Pact?**
- Contract testing framework
- Consumer-driven
- Multiple languages
- CI/CD integration

**Q8: What are Pact components?**
- Pact Consumer: Consumer testing
- Pact Provider: Provider testing
- Pact Broker: Contract storage
- Pact Foundation: Core library

**Q9: What languages does Pact support?**
- Java, JavaScript, Python
- Ruby, .NET, Go, PHP
- Wide language support

### Pact Consumer

**Q10: How do you set up consumer tests in Pact?**
- Use @ExtendWith(PactConsumerTestExt.class)
- Use @PactTestFor
- Use @MockServer
- Test against mock server

**Q11: How do you generate Pact contracts?**
- Use @Pact annotation
- Use PactDslWithProvider
- Define request and response
- Return Pact

**Q12: How do you execute consumer tests?**
- Run consumer tests
- Generate Pact files
- Publish to Pact Broker
- Verify compliance

### Pact Provider

**Q13: How do you set up provider tests in Pact?**
- Use @Provider annotation
- Use @PactFolder
- Use @PactBroker
- Use @TestTarget

**Q14: How do you verify provider contracts?**
- Load Pact files
- Verify request matching
- Verify response matching
- Report results

**Q15: How do you manage provider state?**
- Use @State annotation
- Setup test data
- Define state methods
- Reset state after test

### Pact Broker

**Q16: What is Pact Broker?**
- Repository for Pact contracts
- Share contracts between services
- Contract verification
- Version management

**Q17: What are Pact Broker features?**
- Contract storage
- Contract verification
- Version management
- Webhooks
- Dashboard

**Q18: How do you set up Pact Broker?**
- Use Docker
- Run pact-broker image
- Configure database
- Expose port 9292

### Spring Cloud Contract

**Q19: What is Spring Cloud Contract?**
- Contract testing for Spring Boot
- Consumer and provider testing
- Groovy DSL
- Spring integration

**Q20: How do you set up Spring Cloud Contract?**
- Add Maven dependency
- Define contracts in Groovy
- Use @AutoConfigureStubRunner
- Run tests

### CI/CD

**Q21: How do you integrate contract testing in CI/CD?**
- Consumer tests in CI
- Provider tests in CD
- Publish to Pact Broker
- Verify automatically

**Q22: What are the consumer pipeline stages?**
- Build
- Consumer tests
- Generate contracts
- Publish to Pact Broker
- Deploy

**Q23: What are the provider pipeline stages?**
- Build
- Provider tests
- Verify contracts
- Deploy
- Notify consumers

### Best Practices

**Q24: What are contract testing best practices?**
- Version contracts
- Test early
- Keep contracts simple
- Document changes

**Q25: Why version contracts?**
- Maintain compatibility
- Support multiple versions
- Enable gradual migration
- Document changes

### Scenario-Based

**Q26: How do you handle breaking changes in contracts?**
- Version contracts
- Communicate changes
- Provide migration path
- Deprecate old version

**Q27: How do you handle multiple consumers?**
- Version contracts per consumer
- Use Pact Broker
- Manage versions
- Verify per consumer

**Q28: How do you handle contract verification failures?**
- Investigate failure
- Fix provider or consumer
- Update contract
- Re-verify

**Q29: How do you document contracts?**
- Use Pact Broker
- Use Swagger/OpenAPI
- Use comments
- Use README

**Q30: How do you ensure contract compliance across teams?**
- Use Pact Broker
- Automate verification
- CI/CD integration
- Notifications

---

## CONCLUSION

This comprehensive guide covers all essential contract testing topics for interview preparation. Key takeaways:

1. **Contract Testing**: Ensures services adhere to contract, validates API contracts
2. **Consumer-Driven**: Consumer defines contract, provider implements
3. **Provider-Driven**: Provider defines contract, consumer verifies
4. **Pact Framework**: Consumer-driven, multiple languages, Pact Broker
5. **Pact Contract**: JSON format, interactions, request/response
6. **Pact Consumer**: @PactTestFor, @MockServer, generate contracts
7. **Pact Provider**: @Provider, @PactFolder, verify contracts
8. **Pact Broker**: Contract storage, verification, version management
9. **Spring Cloud Contract**: Spring Boot, Groovy DSL, Maven/Gradle
10. **CI/CD**: Consumer tests in CI, provider tests in CD
11. **Best Practices**: Version contracts, test early, keep simple
12. **Challenges**: Management, evolution, synchronization
13. **Tools**: Pact, Spring Cloud Contract, Hoverfly, WireMock
14. **REST APIs**: Method, path, headers, status, body
15. **GraphQL**: Query, variables, response data, structure
16. **gRPC**: Proto file, service, messages, methods
17. **Message Queues**: Content type, event type, content, structure
18. **Versioning**: Semantic versioning, compatibility, management
19. **Contract vs Integration**: Interface vs system, early vs late
20. **Practice**: Version, test early, keep simple, document

Practice these concepts with real contract testing implementations and be prepared to explain the "why" behind each approach. Good luck with your interview!
