# Mocking & Stubbing - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Mocking and Stubbing Fundamentals
2. Mock vs Stub
3. Mocking Frameworks
4. Mockito Fundamentals
5. Stubbing with Mockito
6. Mocking with Mockito
7. Argument Matchers
8. Verification
9. Partial Mocking
10. Spying
11. Mocking Static Methods
12. Mocking Final Classes
13. Mocking Private Methods
14. Mocking External Dependencies
15. Mocking in Unit Testing
16. Mocking in Integration Testing
17. Service Virtualization
18. WireMock
19. Mocking Best Practices
20. Common Interview Questions
21. Code Examples
22. Practice Scenarios

---

## 1. MOCKING AND STUBBING FUNDAMENTALS

### What is Mocking?
Mocking is a technique where a test substitutes a real component with a test double that mimics the behavior of the real component. It allows testing in isolation.

### What is Stubbing?
Stubbing is providing predefined responses to method calls. It's a simpler form of mocking where you define what a method should return.

### Test Doubles

**Types:**
- Dummy: Objects passed around but not used
- Stub: Provides predefined responses
- Mock: Verifies interactions
- Fake: Working implementation
- Spy: Partial mock

### When to Use Mocking

**Use Cases:**
- External dependencies
- Slow operations
- Non-deterministic behavior
- Complex state
- Third-party services

### Interview Questions

**Q1: What is mocking?**
- Substitute real component with test double
- Mimics behavior of real component
- Allows testing in isolation
- Verifies interactions

**Q2: What is stubbing?**
- Provide predefined responses
- Simpler form of mocking
- Define what method returns
- No verification

**Q3: What are the types of test doubles?**
- Dummy: Objects not used
- Stub: Predefined responses
- Mock: Verifies interactions
- Fake: Working implementation
- Spy: Partial mock

---

## 2. MOCK VS STUB

### Mock

**Characteristics:**
- Verifies interactions
- Can verify method calls
- Can verify call count
- Can verify arguments
- Behavior verification

### Stub

**Characteristics:**
- Provides predefined responses
- No verification
- Simple return values
- State-based
- Data provider

### Comparison

| Aspect | Mock | Stub |
|--------|------|------|
| Purpose | Verify interactions | Provide responses |
| Verification | Yes | No |
| Complexity | Higher | Lower |
| Use Case | Behavior verification | Data provision |

### Interview Questions

**Q1: What is the difference between mock and stub?**
- Mock: Verifies interactions
- Stub: Provides responses
- Mock: Behavior verification
- Stub: Data provision

**Q2: When would you use a mock?**
- Verify method calls
- Verify call count
- Verify arguments
- Behavior verification

**Q3: When would you use a stub?**
- Provide predefined responses
- Simple return values
- No verification needed
- Data provision

---

## 3. MOCKING FRAMEWORKS

### Java Frameworks

**Frameworks:**
- Mockito: Most popular
- PowerMock: Static/final methods
- EasyMock: Alternative to Mockito
- JMock: Behavior-driven

### Python Frameworks

**Frameworks:**
- unittest.mock: Built-in
- Mock: Popular library
- pytest-mock: Pytest integration

### JavaScript Frameworks

**Frameworks:**
- Sinon.js: Popular
- Jest: Built-in mocking
- testdouble: TDD-focused

### Interview Questions

**Q1: What are popular mocking frameworks in Java?**
- Mockito: Most popular
- PowerMock: Static/final methods
- EasyMock: Alternative
- JMock: Behavior-driven

**Q2: What are popular mocking frameworks in Python?**
- unittest.mock: Built-in
- Mock: Popular library
- pytest-mock: Pytest integration

**Q3: What are popular mocking frameworks in JavaScript?**
- Sinon.js: Popular
- Jest: Built-in mocking
- testdouble: TDD-focused

---

## 4. MOCKITO FUNDAMENTALS

### What is Mockito?
Mockito is a popular mocking framework for Java. It provides a simple API to create mocks, stub methods, and verify interactions.

### Mockito Setup

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.3.1</version>
    <scope>test</scope>
</dependency>
```

### Create Mock

```java
// Create mock
List<String> mockList = mock(List.class);

// Create mock with annotations
@Mock
private List<String> mockList;
```

### Mockito Annotations

**Annotations:**
- @Mock: Create mock
- @Spy: Create spy
- @InjectMocks: Inject mocks
- @Captor: Argument captor

### Interview Questions

**Q1: What is Mockito?**
- Popular mocking framework for Java
- Simple API
- Create mocks, stub methods
- Verify interactions

**Q2: How do you set up Mockito?**
- Add Maven dependency
- Add to test scope
- Use annotations
- Initialize mocks

**Q3: What are Mockito annotations?**
- @Mock: Create mock
- @Spy: Create spy
- @InjectMocks: Inject mocks
- @Captor: Argument captor

---

## 5. STUBBING WITH MOCKITO

### Basic Stubbing

```java
// Create mock
List<String> mockList = mock(List.class);

// Stub method
when(mockList.get(0)).thenReturn("first");
when(mockList.size()).thenReturn(1);

// Use stub
assertEquals("first", mockList.get(0));
assertEquals(1, mockList.size());
```

### Stubbing with Exceptions

```java
// Stub to throw exception
when(mockList.get(1))
    .thenThrow(new IndexOutOfBoundsException());

// Verify exception
assertThrows(IndexOutOfBoundsException.class, () -> mockList.get(1));
```

### Stubbing with Consecutive Calls

```java
// Stub consecutive calls
when(mockList.get(0))
    .thenReturn("first")
    .thenReturn("second")
    .thenThrow(new IndexOutOfBoundsException());

assertEquals("first", mockList.get(0));
assertEquals("second", mockList.get(0));
assertThrows(IndexOutOfBoundsException.class, () -> mockList.get(0));
```

### Interview Questions

**Q1: How do you stub a method in Mockito?**
- Use when().thenReturn()
- Define return value
- Example: when(mock.get(0)).thenReturn("value")
- Use stubbed mock

**Q2: How do you stub a method to throw exception?**
- Use when().thenThrow()
- Define exception
- Example: when(mock.get(1)).thenThrow(new Exception())
- Verify exception

**Q3: How do you stub consecutive calls?**
- Chain return values
- Use multiple thenReturn()
- Example: when().thenReturn().thenReturn()
- Each call returns next value

---

## 6. MOCKING WITH MOCKITO

### Verification

```java
// Create mock
List<String> mockList = mock(List.class);

// Use mock
mockList.add("item");
mockList.clear();

// Verify interactions
verify(mockList).add("item");
verify(mockList).clear();
```

### Verification Times

```java
// Verify exact number of calls
verify(mockList, times(2)).add("item");

// Verify at least once
verify(mockList, atLeastOnce()).add("item");

// Verify at most
verify(mockList, atMost(2)).add("item");

// Verify never called
verify(mockList, never()).add("item");
```

### Verification with Arguments

```java
// Verify with specific argument
verify(mockList).add("item");

// Verify with argument matcher
verify(mockList).add(anyString());

// Verify no more interactions
verifyNoMoreInteractions(mockList);
```

### Interview Questions

**Q1: How do you verify method calls in Mockito?**
- Use verify()
- Specify method
- Example: verify(mock).method()
- Verifies interaction

**Q2: How do you verify number of calls?**
- times(n): Exact number
- atLeastOnce(): At least once
- atMost(n): At most n
- never(): Never called

**Q3: How do you verify with arguments?**
- Specific argument: verify(mock).method(arg)
- Argument matcher: verify(mock).method(any())
- Example: verify(mockList).add("item")

---

## 7. ARGUMENT MATCHERS

### Built-in Matchers

**Matchers:**
- any(): Any object
- anyString(): Any string
- anyInt(): Any integer
- anyBoolean(): Any boolean
- eq(): Specific value

### Custom Matchers

```java
// Custom matcher
when(mockList.add(argThat(new ArgumentMatcher<String>() {
    @Override
    public boolean matches(String argument) {
        return argument.startsWith("test");
    }
}))).thenReturn(true);
```

### Argument Captor

```java
// Create captor
ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

// Use captor
verify(mockList).add(captor.capture());

// Get captured value
String capturedValue = captor.getValue();
```

### Interview Questions

**Q1: What are built-in argument matchers?**
- any(): Any object
- anyString(): Any string
- anyInt(): Any integer
- anyBoolean(): Any boolean

**Q2: How do you create custom matchers?**
- Use argThat()
- Implement ArgumentMatcher
- Define matches() method
- Return boolean

**Q3: What is an argument captor?**
- Capture argument value
- Use ArgumentCaptor
- Verify with captor
- Get captured value

---

## 8. VERIFICATION

### Verification Order

```java
// Verify order of calls
InOrder inOrder = inOrder(mockList, mockSet);

inOrder.verify(mockList).add("item");
inOrder.verify(mockSet).add("item");
```

### Verification Timeout

```java
// Verify with timeout
verify(mockList, timeout(1000)).add("item");

// Verify with timeout and times
verify(mockList, timeout(1000).times(2)).add("item");
```

### Verification Modes

**Modes:**
- times(n): Exact n times
- atLeast(n): At least n times
- atMost(n): At most n times
- never(): Never called
- only(): Only method called

### Interview Questions

**Q1: How do you verify order of calls?**
- Use InOrder
- Pass mocks to inOrder()
- Verify in order
- Example: inOrder.verify(mock1).method()

**Q2: How do you verify with timeout?**
- Use timeout()
- Specify milliseconds
- Example: verify(mock, timeout(1000)).method()
- Waits for call

**Q3: What are verification modes?**
- times(n): Exact n times
- atLeast(n): At least n times
- atMost(n): At most n times
- never(): Never called

---

## 9. PARTIAL MOCKING

### Partial Mock with Spy

```java
// Create spy
List<String> spyList = spy(new ArrayList<>());

// Use real method
spyList.add("item");

// Stub specific method
when(spyList.size()).thenReturn(100);

// Use stubbed method
assertEquals(100, spyList.size());
```

### Partial Mock with Mockito

```java
// Create partial mock
List<String> partialMock = mock(List.class, withSettings()
    .spiedInstance(new ArrayList<>()));

// Use real method
partialMock.add("item");

// Stub specific method
when(partialMock.size()).thenReturn(100);
```

### When to Use Partial Mocking

**Use Cases:**
- Legacy code
- Partial behavior override
- Real implementation needed
- Complex objects

### Interview Questions

**Q1: What is partial mocking?**
- Mock some methods
- Use real implementation for others
- Use spy or partial mock
- Override specific behavior

**Q2: How do you create a spy?**
- Use spy() method
- Pass real object
- Example: spy(new ArrayList<>())
- Can stub specific methods

**Q3: When would you use partial mocking?**
- Legacy code
- Partial behavior override
- Real implementation needed
- Complex objects

---

## 10. SPYING

### What is Spying?
Spying creates a real object and allows you to stub specific methods while keeping the real implementation for others.

### Spy Creation

```java
// Create spy
List<String> spyList = spy(new ArrayList<>());

// Use real method
spyList.add("item");

// Stub specific method
when(spyList.size()).thenReturn(100);
```

### Spy vs Mock

| Aspect | Spy | Mock |
|--------|-----|------|
| Implementation | Real object | No implementation |
- Use real implementation
- Stub specific methods
- Verify interactions
- Partial mocking

### Interview Questions

**Q1: What is spying?**
- Creates real object
- Allows stubbing specific methods
- Keeps real implementation
- Partial mocking

**Q2: How do you create a spy?**
- Use spy() method
- Pass real object
- Example: spy(new ArrayList<>())
- Can stub specific methods

**Q3: What is the difference between spy and mock?**
- Spy: Real object
- Mock: No implementation
- Spy: Partial behavior
- Mock: Full behavior

---

## 11. MOCKING STATIC METHODS

### PowerMock

**Setup:**
```java
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass.class})
public class StaticTest {
    
    @Test
    public void testStaticMethod() {
        mockStatic(StaticClass.class);
        when(StaticClass.staticMethod()).thenReturn("mocked");
        
        assertEquals("mocked", StaticClass.staticMethod());
        
        verifyStatic(StaticClass.class);
        StaticClass.staticMethod();
    }
}
```

### Mockito Inline

**Setup:**
```java
// Use mockito-inline for static mocking
mockStatic(StaticClass.class);
when(StaticClass.staticMethod()).thenReturn("mocked");
```

### Interview Questions

**Q1: How do you mock static methods?**
- Use PowerMock
- Use mockito-inline
- Annotate with @PrepareForTest
- Use mockStatic()

**Q2: What is PowerMock?**
- Extends Mockito
- Mocks static methods
- Mocks final classes
- Mocks private methods

**Q3: What is mockito-inline?**
- Mockito extension
- Mocks static methods
- No PowerMock needed
- Simpler setup

---

## 12. MOCKING FINAL CLASSES

### PowerMock

**Setup:**
```java
@RunWith(PowerMockRunner.class)
@PrepareForTest({FinalClass.class})
public class FinalTest {
    
    @Test
    public void testFinalClass() {
        FinalClass mock = mock(FinalClass.class);
        when(mock.method()).thenReturn("mocked");
        
        assertEquals("mocked", mock.method());
    }
}
```

### Mockito Inline

**Setup:**
```java
// Use mockito-inline for final classes
FinalClass mock = mock(FinalClass.class);
when(mock.method()).thenReturn("mocked");
```

### Interview Questions

**Q1: How do you mock final classes?**
- Use PowerMock
- Use mockito-inline
- Annotate with @PrepareForTest
- Use mock() on final class

**Q2: What is the difference between PowerMock and mockito-inline?**
- PowerMock: More features, complex
- mockito-inline: Simpler, limited
- PowerMock: Requires annotations
- mockito-inline: No annotations

**Q3: Why is mocking final classes challenging?**
- Final classes cannot be extended
- Mockito cannot mock by default
- Need PowerMock or mockito-inline
- Use bytecode manipulation

---

## 13. MOCKING PRIVATE METHODS

### PowerMock

**Setup:**
```java
@RunWith(PowerMockRunner.class)
@PrepareForTest({MyClass.class})
public class PrivateMethodTest {
    
    @Test
    public void testPrivateMethod() throws Exception {
        MyClass obj = new MyClass();
        MyClass spy = spy(obj);
        
        when(spy, "privateMethod", anyString()).thenReturn("mocked");
        
        Method method = MyClass.class.getDeclaredMethod("privateMethod", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(spy, "input");
        
        assertEquals("mocked", result);
    }
}
```

### Reflection

**Approach:**
- Use reflection to access private methods
- Set accessible to true
- Invoke method
- Test result

### Interview Questions

**Q1: How do you mock private methods?**
- Use PowerMock
- Use reflection
- Set accessible to true
- Invoke method

**Q2: Why is mocking private methods discouraged?**
- Tests implementation details
- Breaks encapsulation
- Refactoring breaks tests
- Test behavior, not implementation

**Q3: How do you use reflection to test private methods?**
- Get method with getDeclaredMethod()
- Set accessible to true
- Invoke method
- Test result

---

## 14. MOCKING EXTERNAL DEPENDENCIES

### Database Mocking

```java
// Mock database connection
Connection mockConnection = mock(Connection.class);
Statement mockStatement = mock(Statement.class);
ResultSet mockResultSet = mock(ResultSet.class);

when(mockConnection.createStatement()).thenReturn(mockStatement);
when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
when(mockResultSet.next()).thenReturn(true).thenReturn(false);
when(mockResultSet.getString("name")).thenReturn("John");
```

### API Mocking

```java
// Mock HTTP client
HttpClient mockHttpClient = mock(HttpClient.class);
HttpResponse mockResponse = mock(HttpResponse.class);

when(mockHttpClient.execute(any(HttpRequest.class))).thenReturn(mockResponse);
when(mockResponse.getStatusLine()).thenReturn(mockStatusLine);
when(mockStatusLine.getStatusCode()).thenReturn(200);
```

### File System Mocking

```java
// Mock file system
Path mockPath = mock(Path.class);
Files mockFiles = mock(Files.class);

when(mockPath.toFile()).thenReturn(mockFile);
when(Files.lines(mockPath)).thenReturn(Stream.of("line1", "line2"));
```

### Interview Questions

**Q1: How do you mock database connections?**
- Mock Connection
- Mock Statement
- Mock ResultSet
- Define return values

**Q2: How do you mock HTTP clients?**
- Mock HttpClient
- Mock HttpRequest
- Mock HttpResponse
- Define return values

**Q3: How do you mock file system?**
- Mock Path
- Mock Files
- Define return values
- Use test data

---

## 15. MOCKING IN UNIT TESTING

### Unit Testing with Mocks

```java
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testGetUser() {
        // Arrange
        User mockUser = new User(1, "John");
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        
        // Act
        User user = userService.getUser(1);
        
        // Assert
        assertEquals("John", user.getName());
        verify(userRepository).findById(1);
    }
}
```

### Benefits

**Benefits:**
- Test in isolation
- Fast execution
- No external dependencies
- Deterministic behavior

### Interview Questions

**Q1: How do you use mocks in unit testing?**
- Create mocks with @Mock
- Inject mocks with @InjectMocks
- Stub methods
- Verify interactions

**Q2: What are the benefits of mocking in unit testing?**
- Test in isolation
- Fast execution
- No external dependencies
- Deterministic behavior

**Q3: How do you initialize mocks?**
- Use @Mock annotation
- Use MockitoAnnotations.openMocks()
- Use @InjectMocks
- In @Before method

---

## 16. MOCKING IN INTEGRATION TESTING

### Integration Testing with Mocks

```java
@SpringBootTest
public class IntegrationTest {
    
    @MockBean
    private ExternalService externalService;
    
    @Test
    public void testIntegration() {
        // Mock external service
        when(externalService.getData()).thenReturn("mocked data");
        
        // Test integration
        String result = service.processData();
        
        // Verify
        assertEquals("processed: mocked data", result);
    }
}
```

### TestContainers

```java
@Testcontainers
public class DatabaseIntegrationTest {
    
    @Container
    private PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");
    
    @Test
    public void testDatabase() {
        // Use real database in container
        // Test integration
    }
}
```

### Interview Questions

**Q1: How do you use mocks in integration testing?**
- Use @MockBean in Spring Boot
- Mock external dependencies
- Test integration points
- Verify interactions

**Q2: What is TestContainers?**
- Run real services in containers
- Databases, message brokers
- Integration testing
- Real dependencies

**Q3: When to use mocks vs TestContainers?**
- Mocks: External dependencies
- TestContainers: Database, message broker
- Mocks: Fast, isolated
- TestContainers: Real, realistic

---

## 17. SERVICE VIRTUALIZATION

### What is Service Virtualization?
Service virtualization simulates the behavior of dependent services. It allows testing without actual services.

### WireMock

**Setup:**
```java
@Rule
public WireMockRule wireMockRule = new WireMockRule(8080);

@Test
public void testService() {
    // Stub service
    stubFor(get(urlEqualTo("/api/data"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{\"data\":\"value\"}")));
    
    // Test with virtualized service
    String result = service.getData();
    
    // Verify
    assertEquals("value", result);
}
```

### Mountebank

**Setup:**
```javascript
// Mountebank stub
{
  "port": 2525,
  "stubs": [
    {
      "responses": [
        {
          "is": {
            "body": "{\"data\":\"value\"}"
          }
        }
      ]
    }
  ]
}
```

### Interview Questions

**Q1: What is service virtualization?**
- Simulates service behavior
- Allows testing without actual services
- Reduces dependencies
- Faster feedback

**Q2: What is WireMock?**
- HTTP service virtualization
- Stub HTTP requests
- Simulate responses
- Java-based

**Q3: What is Mountebank?**
- Multi-protocol virtualization
- HTTP, TCP, etc.
- Configuration files
- Cross-platform

---

## 18. WIREMOCK

### WireMock Setup

**Maven Dependency:**
```xml
<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8</artifactId>
    <version>2.35.0</version>
    <scope>test</scope>
</dependency>
```

### WireMock Stubbing

```java
// Stub GET request
stubFor(get(urlEqualTo("/api/users/1"))
    .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody("{\"id\":1,\"name\":\"John\"}")));

// Stub POST request
stubFor(post(urlEqualTo("/api/users"))
    .willReturn(aResponse()
        .withStatus(201)
        .withBody("{\"id\":2,\"name\":\"Jane\"}")));
```

### WireMock Verification

```java
// Verify request
verify(getRequestedFor(urlEqualTo("/api/users/1")));

// Verify request count
verify(1, getRequestedFor(urlEqualTo("/api/users/1")));
```

### Interview Questions

**Q1: How do you set up WireMock?**
- Add Maven dependency
- Use @Rule or @ClassRule
- Configure port
- Start WireMock server

**Q2: How do you stub requests in WireMock?**
- Use stubFor()
- Define HTTP method
- Define URL
- Define response

**Q3: How do you verify requests in WireMock?**
- Use verify()
- Specify HTTP method
- Specify URL
- Verify request count

---

## 19. MOCKING BEST PRACTICES

### Test Behavior, Not Implementation

**Practice:**
- Test public API
- Don't test private methods
- Don't mock everything
- Test real behavior

### Use Real Objects When Possible

**Practice:**
- Use real objects for simple logic
- Mock only external dependencies
- Use spies for partial mocking
- Avoid over-mocking

### Keep Tests Simple

**Practice:**
- One assertion per test
- Clear test name
- Arrange-Act-Assert
- Avoid complex setup

### Interview Questions

**Q1: What are mocking best practices?**
- Test behavior, not implementation
- Use real objects when possible
- Keep tests simple
- Don't over-mock

**Q2: Why test behavior, not implementation?**
- Refactoring-safe
- Focus on what, not how
- More maintainable
- Better test coverage

**Q3: When should you avoid mocking?**
- Simple logic
- Value objects
- Real implementation needed
- Over-mocking

---

## 20. COMMON INTERVIEW QUESTIONS

### Fundamentals

**Q1: What is mocking?**
- Substitute real component with test double
- Mimics behavior of real component
- Allows testing in isolation
- Verifies interactions

**Q2: What is stubbing?**
- Provide predefined responses
- Simpler form of mocking
- Define what method returns
- No verification

**Q3: What are the types of test doubles?**
- Dummy: Objects not used
- Stub: Predefined responses
- Mock: Verifies interactions
- Fake: Working implementation
- Spy: Partial mock

### Mock vs Stub

**Q4: What is the difference between mock and stub?**
- Mock: Verifies interactions
- Stub: Provides responses
- Mock: Behavior verification
- Stub: Data provision

**Q5: When would you use a mock?**
- Verify method calls
- Verify call count
- Verify arguments
- Behavior verification

**Q6: When would you use a stub?**
- Provide predefined responses
- Simple return values
- No verification needed
- Data provision

### Mockito

**Q7: What is Mockito?**
- Popular mocking framework for Java
- Simple API
- Create mocks, stub methods
- Verify interactions

**Q8: How do you create a mock in Mockito?**
- Use mock() method
- Use @Mock annotation
- Example: mock(List.class)
- Initialize mocks

**Q9: How do you stub a method in Mockito?**
- Use when().thenReturn()
- Define return value
- Example: when(mock.get(0)).thenReturn("value")
- Use stubbed mock

### Verification

**Q10: How do you verify method calls in Mockito?**
- Use verify()
- Specify method
- Example: verify(mock).method()
- Verifies interaction

**Q11: How do you verify number of calls?**
- times(n): Exact number
- atLeastOnce(): At least once
- atMost(n): At most n
- never(): Never called

**Q12: How do you verify with arguments?**
- Specific argument: verify(mock).method(arg)
- Argument matcher: verify(mock).method(any())
- Example: verify(mockList).add("item")

### Advanced

**Q13: How do you mock static methods?**
- Use PowerMock
- Use mockito-inline
- Annotate with @PrepareForTest
- Use mockStatic()

**Q14: How do you mock final classes?**
- Use PowerMock
- Use mockito-inline
- Annotate with @PrepareForTest
- Use mock() on final class

**Q15: How do you mock private methods?**
- Use PowerMock
- Use reflection
- Set accessible to true
- Invoke method

### Service Virtualization

**Q16: What is service virtualization?**
- Simulates service behavior
- Allows testing without actual services
- Reduces dependencies
- Faster feedback

**Q17: What is WireMock?**
- HTTP service virtualization
- Stub HTTP requests
- Simulate responses
- Java-based

**Q18: How do you stub requests in WireMock?**
- Use stubFor()
- Define HTTP method
- Define URL
- Define response

### Best Practices

**Q19: What are mocking best practices?**
- Test behavior, not implementation
- Use real objects when possible
- Keep tests simple
- Don't over-mock

**Q20: Why test behavior, not implementation?**
- Refactoring-safe
- Focus on what, not how
- More maintainable
- Better test coverage

### Scenario-Based

**Q21: How do you mock a database in unit testing?**
- Mock Connection
- Mock Statement
- Mock ResultSet
- Define return values

**Q22: How do you mock an external API in unit testing?**
- Mock HttpClient
- Mock HttpRequest
- Mock HttpResponse
- Define return values

**Q23: How do you handle exceptions in mocking?**
- Use thenThrow()
- Define exception
- Test exception handling
- Verify exception

**Q24: How do you mock complex objects?**
- Use builder pattern
- Create test data
- Use @Builder annotation
- Simplify test setup

**Q25: How do you verify interactions with multiple mocks?**
- Verify each mock
- Use InOrder for order
- Verify call counts
- Verify arguments

---

## CONCLUSION

This comprehensive guide covers all essential mocking and stubbing topics for interview preparation. Key takeaways:

1. **Mocking**: Substitute real component, mimic behavior, test in isolation
2. **Stubbing**: Provide predefined responses, simpler form, data provision
3. **Test Doubles**: Dummy, Stub, Mock, Fake, Spy
4. **Mock vs Stub**: Mock verifies, Stub provides data
5. **Frameworks**: Mockito, PowerMock, EasyMock, JMock
6. **Mockito**: Popular Java framework, simple API, annotations
7. **Stubbing**: when().thenReturn(), throw exceptions, consecutive calls
8. **Mocking**: verify(), times(), atLeast(), argument matchers
9. **Argument Matchers**: any(), anyString(), custom matchers, captors
10. **Verification**: Order, timeout, modes, no more interactions
11. **Partial Mocking**: Spy, partial mock, real implementation
12. **Spying**: Real object, stub specific methods, partial behavior
13. **Static Methods**: PowerMock, mockito-inline, mockStatic()
14. **Final Classes**: PowerMock, mockito-inline, bytecode manipulation
15. **Private Methods**: PowerMock, reflection, discouraged
16. **External Dependencies**: Database, API, file system
17. **Unit Testing**: Isolation, fast, deterministic, no dependencies
18. **Integration Testing**: @MockBean, TestContainers, realistic
19. **Service Virtualization**: WireMock, Mountebank, simulate services
20. **WireMock**: HTTP virtualization, stub requests, verify requests
21. **Best Practices**: Test behavior, real objects, simple tests, no over-mocking

Practice these concepts with real projects and be prepared to explain the "why" behind each mocking decision. Good luck with your interview!
