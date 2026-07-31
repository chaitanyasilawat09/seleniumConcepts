# Selenium WebDriver & TestNG - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Selenium WebDriver Fundamentals
2. Wait Strategies in Selenium
3. Element Locators & Interactions
4. Advanced Interactions (Actions Class)
5. Page Object Model (POM)
6. TestNG Framework Deep Dive
7. TestNG Listeners
8. Parallel Execution
9. Retry Failed Tests
10. Reporting & Screenshots
11. WebDriver Event Listeners (Selenium 4)
12. File Operations
13. Cross-Browser Testing
14. Java 8 Streams & Collections for Testing
15. Java Programming for Interviews
16. Build Tools & Dependency Management
17. Testing Concepts & Methodologies
18. Advanced Selenium Concepts
19. Bug Reporting & Risk Management
20. Agile Methodology
21. Test Strategy Components
22. Best Practices & Design Patterns

**Note:** For comprehensive API Testing, Database Testing, and Advanced Automation topics, please refer to the companion document: **API_DATABASE_INTERVIEW_GUIDE.md**

---

## 1. SELENIUM WEBDRIVER FUNDAMENTALS

### What is Selenium WebDriver?
Selenium WebDriver is a browser automation framework that allows you to programmatically control web browsers. It directly communicates with the browser using browser-specific drivers.

### Driver Setup and Initialization

In your project, you have two approaches for driver setup:

**Approach 1: Direct Driver Setup (BaseTest.java)**
```java
System.setProperty("webdriver.chrome.driver","chromedriver1.exe");
System.setProperty("webdriver.chrome.logfile", "TestLog.txt");
driver = new ChromeDriver();
driver.get("https://demoqa.com/elements");
```

**Approach 2: WebDriverManager (Modern Approach)**
```java
WebDriverManager.chromedriver().arch64().setup();
WebDriver driver = new ChromeDriver();
```

**Why WebDriverManager is better?**
- Automatically downloads the correct driver version
- No need to manually manage driver executables
- Handles different browser versions automatically
- Cross-platform compatibility

### Thread-Safe Driver Management (DriverFactory.java)

When running tests in parallel, each thread needs its own WebDriver instance. Using a simple static variable will cause race conditions.

```java
public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver(){
        return driver.get();
    }
    
    public static void setDriver(){
        driver.set(new ChromeDriver());
    }
}
```

**Why ThreadLocal?**
- ThreadLocal provides thread-local variables
- Each thread gets its own independent copy of the driver
- Prevents interference between parallel test executions
- Essential for parallel test execution

**Interview Question:** What happens if you use a static WebDriver variable in parallel execution?
- Answer: Multiple threads will try to use the same driver instance, causing tests to interfere with each other, leading to flaky failures.

---

## 2. WAIT STRATEGIES IN SELENIUM

### Why Waits are Necessary?
Web applications are dynamic. Elements may load at different times due to:
- Network latency
- AJAX calls
- JavaScript rendering
- Animations

Without proper waits, tests will fail with NoSuchElementException or ElementNotInteractableException.

### Three Types of Waits

### 2.1 Implicit Wait

**What it does:** Sets a global timeout for all element finding operations.

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
```

**How it works:**
- Applied once globally
- When findElement() is called, if element not found immediately, WebDriver waits up to 30 seconds
- Polls the DOM every 500ms (default)
- Once element found, proceeds immediately
- If timeout reached, throws NoSuchElementException

**When to use:**
- Simple test scenarios
- When most elements load at similar speed
- Not suitable for AJAX-heavy applications

**Limitations:**
- Cannot wait for specific conditions (element clickable, visible, etc.)
- Applied globally - can slow down all tests
- Not flexible for different loading times

### 2.2 Explicit Wait

**What it does:** Waits for a specific condition to be met before proceeding.

```java
public void explicateWaitVisibleElement(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.visibilityOf(element));
    wait.until(ExpectedConditions.visibilityOfAllElements(element, element, element));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
}

public void explicateWaitElementClickable(WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.elementToBeClickable(element));
    wait.until(ExpectedConditions.alertIsPresent());
}
```

**Common ExpectedConditions:**
- `visibilityOf(element)` - Element visible on page
- `elementToBeClickable(element)` - Element enabled and clickable
- `presenceOfElementLocated(by)` - Element present in DOM (may not be visible)
- `alertIsPresent()` - Alert popup displayed
- `textToBePresentInElement(element, text)` - Specific text present
- `titleContains(title)` - Page title contains specific text
- `frameToBeAvailableAndSwitchToIt(frameId)` - Frame available

**When to use:**
- AJAX applications
- Elements with different loading times
- When you need specific conditions (clickable, visible)
- Dynamic content loading

**Advantages over Implicit Wait:**
- Targeted waiting for specific elements
- Can wait for specific conditions
- More efficient - doesn't wait unnecessarily
- Better for complex applications

### 2.3 Fluent Wait

**What it does:** Most flexible wait - allows custom polling interval and exception ignoring.

```java
public void fluentWait(WebElement element) {
    Wait<WebDriver> wait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(10))      // Maximum wait time
        .pollingEvery(Duration.ofMillis(500))    // Check every 500ms
        .ignoring(NoSuchElementException.class)    // Ignore these exceptions
        .ignoring(ElementClickInterceptedException.class, StaleElementReferenceException.class);
    
    wait.until(ExpectedConditions.visibilityOf(element));
}
```

**Parameters explained:**
- `withTimeout`: Maximum time to wait
- `pollingEvery`: How frequently to check the condition
- `ignoring`: Exceptions to ignore during polling

**When to use:**
- Elements that appear/disappear intermittently
- When you want to ignore specific exceptions
- Custom polling intervals needed
- Very dynamic applications with flaky elements

**Real-world scenario:**
A notification that appears and disappears multiple times before staying visible. Fluent Wait can ignore the intermittent NoSuchElementException and keep polling until the element stays visible.

### Page Load Timeout

```java
public void pageLoadWait() {
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
}
```

**What it does:** Sets maximum time for page to load before throwing exception.

**When to use:**
- Slow-loading pages
- Pages with heavy resources
- Network conditions vary

### Interview Questions on Waits

**Q1: Difference between Implicit and Explicit wait?**
- Implicit: Global timeout for all elements, simple conditions only
- Explicit: Targeted timeout for specific elements, can wait for complex conditions

**Q2: Which wait should I use and when?**
- Use Implicit for simple, stable applications
- Use Explicit for AJAX/dynamic applications
- Use Fluent for very dynamic/flaky elements

**Q3: Can we use Implicit and Explicit wait together?**
- Not recommended. They can interfere with each other causing unpredictable behavior.
- Best practice: Use only Explicit waits.

**Q4: What is StaleElementReferenceException?**
- Thrown when element reference is no longer valid
- Happens when DOM is refreshed after element was located
- Solution: Re-locate the element or use Fluent Wait ignoring this exception

---

## 3. ELEMENT LOCATORS & INTERACTIONS

### CSS Selectors - Complete Guide

CSS selectors are faster and more readable than XPath. Your project demonstrates various CSS selector patterns.

### Pattern 1: Tag + Class Name
```java
WebElement TextBox = driver.findElement(By.cssSelector("span.text"));
```
**Format:** `tag.className`
**Example:** Find `<span class="text">Click Me</span>`

### Pattern 2: Tag + ID
```java
WebElement fullNameField = driver.findElement(By.cssSelector("input#userName"));
```
**Format:** `tag#id`
**Example:** Find `<input id="userName">`

### Pattern 3: Tag + Attribute
```java
WebElement CurrentAddress = driver.findElement(By.cssSelector("textarea[id=currentAddress]"));
```
**Format:** `tag[attribute=value]`
**Example:** Find `<textarea id="currentAddress">`

### Pattern 4: Tag + Class + Attribute
```java
WebElement permanentAddress = driver.findElement(By.cssSelector("textarea.form-control[id=permanentAddress]"));
```
**Format:** `tag.className[attribute=value]`
**Example:** Find `<textarea class="form-control" id="permanentAddress">`

### Advanced CSS Selectors
```java
// Starts with
driver.findElement(By.cssSelector("input[name^='user']"))

// Ends with
driver.findElement(By.cssSelector("input[name$='name']"))

// Direct child
driver.findElement(By.cssSelector("div > p"))

// Nth child
driver.findElement(By.cssSelector("ul li:nth-child(2)"))
```

### JavaScriptExecutor - When and Why

Sometimes Selenium cannot interact with elements directly. JavaScriptExecutor bypasses Selenium and executes JavaScript directly in the browser.

### SendKeys using JavaScript
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
String fullName = "Chaitanya";
js.executeScript("arguments[0].value='"+ fullName +"';", fullNameField);
```

**When to use:**
- Element is hidden or obscured
- Regular sendKeys doesn't work
- Need to set value without triggering events
- Element is not in viewport

### Click using JavaScript
```java
js.executeScript("arguments[0].click();", permanentAddress);
```

**When to use:**
- Element is not clickable due to overlay
- Element is outside viewport
- Regular click fails intermittently

### Scroll Operations
```java
// Scroll down by 500 pixels
js.executeScript("window.scrollBy(0,500)");

// Scroll element into view
js.executeScript("arguments[0].scrollIntoView();", permanentAddress);

// Scroll to bottom of page
js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

// Scroll to top
js.executeScript("window.scrollTo(0, 0);");

// Refresh page
js.executeScript("history.go(0)");
```

### Alert Handling (A_1_Alerts.java)

Alerts are browser popups that require special handling.

### Simple Alert
```java
driver.findElement(By.xpath("//button[@id='alertButton']")).click();
Alert alerts = driver.switchTo().alert();
sleep(4000);
alerts.accept();  // Click OK
```

### Timed Alert (appears after delay)
```java
driver.findElement(By.xpath("//button[@id='timerAlertButton']")).click();
sleep(10000);  // Wait for alert to appear
Alert wait_5_second = driver.switchTo().alert();
wait_5_second.accept();
String alertText = wait_5_second.getText();  // Get alert text
```

### Confirmation Alert (OK/Cancel)
```java
driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
Alert cancleAlert = driver.switchTo().alert();
cancleAlert.dismiss();  // Click Cancel
// cancleAlert.accept();  // Click OK
```

### Prompt Alert (with input field)
```java
driver.findElement(By.xpath("//button[@id='promtButton']")).click();
Alert sendKeys = driver.switchTo().alert();
sendKeys.sendKeys("Chaitanya");  // Type in alert
sendKeys.accept();
```

### Alert with Explicit Wait
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
alert.accept();
```

**Important:** Always switch to alert before interacting with it using `driver.switchTo().alert()`

### Dropdown Handling (A_2_DropDown.java)

Selenium provides the Select class for handling dropdowns (only for `<select>` tags).

```java
WebElement elementDropDown = driver.findElement(By.id("oldSelectMenu"));
Select select = new Select(elementDropDown);
```

### Get All Options
```java
System.out.println(select.getOptions());
for (WebElement element : select.getOptions())
    System.out.println(element.getText());
```

### Check if Multi-select
```java
System.out.println(select.isMultiple());
```

### Select by Value
```java
select.selectByValue("red");
```
Selects option with `value="red"` attribute: `<option value="red">Red</option>`

### Select by Visible Text
```java
select.selectByVisibleText("Green");
```
Selects option with text "Green": `<option>Green</option>`

### Select by Index
```java
select.selectByIndex(5);
```
Selects the 6th option (0-based index)

### Deselect Options (for multi-select dropdowns)
```java
select.deselectByValue("red");
select.deselectByVisibleText("Green");
select.deselectByIndex(5);
select.deselectAll();
```

### Get Selected Options
```java
List<WebElement> selectedOptions = select.getAllSelectedOptions();
```

### Important Note
If element is not a `<select>` tag (e.g., custom dropdown with `<div>`), you cannot use Select class. You'll get `UnexpectedTagNameException`. For custom dropdowns, click the dropdown and then click the option.

### Frame Handling (A_4_Frames.java)

Frames are inline frames that embed another HTML document within the current document.

### Switch to Frame by ID/Name
```java
driver.switchTo().frame("frame1");
```

### Switch to Frame by Index
```java
driver.switchTo().frame(0);  // First frame
```

### Switch to Frame by WebElement
```java
WebElement frameElement = driver.findElement(By.xpath("//iframe"));
driver.switchTo().frame(frameElement);
```

### Switch Back to Main Content
```java
driver.switchTo().defaultContent();
```
This switches back to the main page from any nested frames.

### Switch to Parent Frame
```java
driver.switchTo().parentFrame();
```
This switches to the immediate parent frame (useful for nested frames).

### Working with Nested Frames
```java
// Switch to outer frame
driver.switchTo().frame("outerFrame");

// Switch to inner frame (nested inside outer)
driver.switchTo().frame("innerFrame");

// Interact with element in inner frame
WebElement element = driver.findElement(By.id("elementId"));

// Switch back to outer frame
driver.switchTo().parentFrame();

// Switch back to main content
driver.switchTo().defaultContent();
```

### Window/Tab Handling (A_6_Windows_Handel.java)

Modern web applications often open new tabs or windows. You need to switch between them.

### Get Current Window Handle
```java
String mainWindow = driver.getWindowHandle();
System.out.println("Main Window: " + mainWindow);
```
Returns a unique string identifier for the current window.

### Get All Window Handles
```java
Set<String> allWindows = driver.getWindowHandles();
ArrayList<String> tabs = new ArrayList<String>(allWindows);
```
Returns handles for all open windows/tabs.

### Switch to Specific Window
```java
driver.switchTo().window(tabs.get(1));  // Switch to second tab
```

### Switch Between Tabs
```java
String mainWindow = driver.getWindowHandle();
// Click link that opens new tab
driver.findElement(By.id("newTabButton")).click();

// Get all tabs and switch to new one
ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
driver.switchTo().window(tabs.get(1));

// Work in new tab
// ...

// Switch back to main tab
driver.switchTo().window(mainWindow);
```

### Open New Tab/Window Programmatically (Selenium 4)
```java
// Opens a new tab and switches to it
driver.switchTo().newWindow(WindowType.TAB);

// Opens a new window and switches to it
driver.switchTo().newWindow(WindowType.WINDOW);

// Switch back to original window
driver.switchTo().window(mainWindow);
```

### Close Specific Window
```java
driver.switchTo().window(windowHandle);
driver.close();
```

### Cookie Management

Cookies are small pieces of data stored by websites.

### Add Cookie
```java
Cookie ck = new Cookie("name", "value");
driver.manage().addCookie(ck);
```

### Get Specific Cookie
```java
Cookie cookie = driver.manage().getCookieNamed("name");
```

### Get All Cookies
```java
Set<Cookie> allCookies = driver.manage().getCookies();
```

### Delete Cookie
```java
driver.manage().deleteCookieNamed("name");
```

### Delete All Cookies
```java
driver.manage().deleteAllCookies();
```

---

## 4. ADVANCED INTERACTIONS (ACTIONS CLASS)

The Actions class enables advanced user interactions like mouse movements, keyboard events, and drag-and-drop.

### Mouse Hover (MouseHover.java)

Mouse hover is used to interact with dropdown menus that appear on hover.

```java
Actions action = new Actions(driver);
WebElement menu = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
action.moveToElement(menu).perform();

WebElement subMenu = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
action.moveToElement(subMenu).perform();

WebElement sub_subMenu = driver.findElement(By.xpath("//a[text()='Sub Sub Item 1']"));
action.moveToElement(sub_subMenu).perform();
```

**How it works:**
1. Create Actions object
2. Use moveToElement() to move mouse to element
3. Call perform() to execute the action
4. Chain multiple actions for multi-level menus

### Slider Movement
```java
WebElement slider = driver.findElement(By.id("slider"));
Actions actions = new Actions(driver);
actions.moveToElement(slider, 50, 0).perform();  // Move 50 pixels horizontally
slider.click();
```

### Keyboard Events (KeyBoardEventsTest.java)

Simulate keyboard operations like copy, paste, and special keys.

```java
Actions actions = new Actions(driver);

// Select text using CTRL + A
actions.keyDown(Keys.CONTROL);
actions.sendKeys("a");
actions.keyUp(Keys.CONTROL);
actions.build().perform();

// Copy using CTRL + C
actions.keyDown(Keys.CONTROL);
actions.sendKeys("c");
actions.keyUp(Keys.CONTROL);
actions.build().perform();

// Press TAB to switch focus
actions.sendKeys(Keys.TAB);
actions.build().perform();

// Paste using CTRL + V
actions.keyDown(Keys.CONTROL);
actions.sendKeys("v");
actions.keyUp(Keys.CONTROL);
actions.build().perform();
```

**Common Keys:**
- `Keys.ENTER` - Enter key
- `Keys.TAB` - Tab key
- `Keys.CONTROL` - Control key
- `Keys.SHIFT` - Shift key
- `Keys.ARROW_DOWN` - Down arrow
- `Keys.BACK_SPACE` - Backspace
- `Keys.DELETE` - Delete key

### Right Click (Context Click) (RightClick_Action.java)

```java
Actions actions = new Actions(driver);
actions.contextClick(element).perform();
```

### Double Click
```java
Actions actions = new Actions(driver);
actions.doubleClick(doubleClickButton).perform();
```

### Drag and Drop

**Method 1: Drag to another element**
```java
Actions actions = new Actions(driver);
actions.dragAndDrop(drag, outerDrop).perform();
```

**Method 2: Drag by offset**
```java
Actions actions = new Actions(driver);
actions.dragAndDropBy(drag, 100, 100);  // 100px right, 100px down
```

**Offset explanation:**
- First parameter: horizontal movement (x-axis)
- Second parameter: vertical movement (y-axis)
- Positive values: right/down
- Negative values: left/up

### Click and Hold
```java
Actions actions = new Actions(driver);
actions.clickAndHold(element).perform();
```

### Release
```java
Actions actions = new Actions(driver);
actions.release().perform();
```

### Build vs Perform
```java
// Build: Creates a composite action (doesn't execute)
actions.sendKeys("text").click(element).build();

// Perform: Executes the action
actions.perform();

// Or chain them:
actions.sendKeys("text").click(element).perform();
```

---

## 5. PAGE OBJECT MODEL (POM)

### What is POM?
Page Object Model is a design pattern that creates an object repository for web elements. Each page is represented by a separate class.

### Why Use POM?
- **Reusability:** WebElements are defined once, used multiple times
- **Maintainability:** If locator changes, update in one place
- **Readability:** Test code is cleaner and more readable
- **Separation of Concerns:** Test logic separate from element locators

### POM Implementation (ElementPage.java)

```java
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ElementPage {
    
    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_Without_Cache;
    
    @CacheLookup
    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_cache;
    
    public ElementPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
```

### @FindBy Annotation
Locates web elements using various strategies:

```java
@FindBy(id = "elementId")
@FindBy(name = "elementName")
@FindBy(className = "elementClass")
@FindBy(xpath = "//div[@id='test']")
@FindBy(cssSelector = "div.test")
@FindBy(tagName = "input")
@FindBy(linkText = "Click Here")
@FindBy(partialLinkText = "Click")
```

### @CacheLookup Annotation

```java
@CacheLookup
@FindBy(xpath = "//*[@id=\"firstName\"]")
private WebElement firstNameField_cache;
```

**What it does:**
- Caches the WebElement after first lookup
- Subsequent calls use cached reference instead of searching DOM again
- Improves performance by avoiding repeated DOM searches

**When to use:**
- Static elements that don't change during test execution
- Elements used multiple times in same test
- Performance-critical scenarios

**When NOT to use:**
- Dynamic elements that may be re-rendered
- Elements that may become stale
- AJAX-heavy applications with frequent DOM updates

**Performance comparison:**
Without @CacheLookup: Each access searches DOM (slower)
With @CacheLookup: First access searches, subsequent uses cache (faster)

### PageFactory.initElements()

```java
public ElementPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
}
```

**What it does:**
- Initializes all @FindBy annotated elements
- Creates proxy objects for lazy initialization
- Elements are actually located when first accessed

**Lazy Initialization:**
- Elements are not located immediately
- Located only when first used (first click/sendKeys)
- Saves time if element is never used

### Using POM in Tests

```java
@Test
public void test() {
    driver.get("https://demoqa.com/automation-practice-form");
    ElementPage page = PageFactory.initElements(driver, ElementPage.class);
    page.getFirstNameField_cache().sendKeys("ram");
    page.getFirstNameField_Without_Cache().getText();
}
```

### Lombok Annotations

Your project uses Lombok to reduce boilerplate code:

```java
@Setter      // Generates setter methods
@Getter      // Generates getter methods
@ToString    // Generates toString() method
@NoArgsConstructor  // Generates no-argument constructor
```

**Without Lombok:**
```java
public WebElement getFirstNameField_Without_Cache() {
    return firstNameField_Without_Cache;
}

public void setFirstNameField_Without_Cache(WebElement firstNameField_Without_Cache) {
    this.firstNameField_Without_Cache = firstNameField_Without_Cache;
}
// ... many more lines
```

**With Lombok:** Just annotations, methods generated automatically

---

## 6. TESTNG FRAMEWORK DEEP DIVE

### What is TestNG?
TestNG (Next Generation) is a testing framework inspired by JUnit but with more powerful features. It's designed for test automation requiring advanced features like data-driven testing, parallel execution, and reporting.

### TestNG Annotations

```java
@BeforeSuite    // Runs before all tests in suite
@BeforeTest     // Runs before all tests in test tag
@BeforeClass    // Runs before first test method in class
@BeforeMethod   // Runs before each test method
@Test           // Actual test method
@AfterMethod    // Runs after each test method
@AfterClass     // Runs after all test methods in class
@AfterTest      // Runs after all tests in test tag
@AfterSuite     // Runs after all tests in suite
```

### Execution Order
```
@BeforeSuite
  @BeforeTest
    @BeforeClass
      @BeforeMethod
        @Test (method1)
      @AfterMethod
      @BeforeMethod
        @Test (method2)
      @AfterMethod
    @AfterClass
  @AfterTest
@AfterSuite
```

### @Test Annotation Parameters

```java
@Test(priority = 1)                    // Execution order
@Test(description = "Login test")      // Test description
@Test(groups = {"smoke", "regression"}) // Group classification
@Test(dependsOnMethods = "login")     // Dependency
@Test(dependsOnGroups = "smoke")       // Group dependency
@Test(enabled = false)                 // Disable test
@Test(timeOut = 5000)                  // Timeout in milliseconds
@Test(invocationCount = 5)             // Run test 5 times
@Test(threadPoolSize = 3)              // Threads for invocationCount
@Test(successPercentage = 60)           // Pass if 60% pass
@Test(dataProvider = "testData")       // Data provider name
@Test(dataProviderClass = DataProvider.class) // External data provider
@Test(retryAnalyzer = Retry.class)     // Retry failed tests
```

### Priority (TestNG_Priority.java)

Priority controls test execution order.

```java
@Test(priority = 1)
public void A_Test() {
    System.out.println("A");
}

@Test(priority = 2)
public void B_Test() {
    System.out.println("B");
}

@Test(priority = -1)
public void C_Test() {
    System.out.println("C");
}

@Test  // No priority = priority 0
public void D_Test() {
    System.out.println("D");
}
```

**Execution order:** C, D, A, B

**Rules:**
- Lower priority executes first
- Negative priorities execute before 0
- No priority = priority 0
- Same priority = alphabetical order

**Interview Question:** What is the execution order if priorities are same?
- Answer: Alphabetical order based on method name

### DataProvider (TestNg_DataProvider.java)

DataProvider enables data-driven testing - running same test with different data.

### Simple DataProvider - Single Parameter

```java
@DataProvider(name = "testDP")
public Object[][] test1() {
    return new Object[][]{{"ram"}, {"shiva"}, {"krishna"}, {"radha"}};
}

@Test(dataProvider = "testDP")
public void DP(String name) {
    System.out.println(name);
}
```

**Output:**
```
ram
shiva
krishna
radha
```

**Test runs 4 times** with different names.

### Multiple Parameters

```java
@DataProvider(name = "testDPMultipleData")
public Object[][] MultipleData() {
    return new Object[][]{
        {"ram", "sita"}, 
        {"shiva", "shakti"}, 
        {"krishna", "Radha"}, 
        {"radha", "Shayam"}
    };
}

@Test(dataProvider = "testDPMultipleData")
public void MultipleDataTest(String name, String name2) {
    System.out.println("Name :-" + name + ",  Name :-" + name2);
}
```

**Output:**
```
Name :-ram,  Name :-sita
Name :-shiva,  Name :-shakti
Name :-krishna,  Name :-Radha
Name :-radha,  Name :-Shayam
```

### Complex Data - HashMap/LinkedHashMap

```java
@DataProvider(name = "dp")
public Object[][] dataProviderS() {
    LinkedHashMap<String, String> map = new LinkedHashMap<>();
    map.put("Ram", "Sita");
    map.put("Shiv", "Shakti");
    map.put("Krishna", "Radha");
    
    return new Object[][]{new Object[]{map}};
}

@Test(dataProvider = "dp", dataProviderClass = TestNg_DataProvider.class)
public void test2(LinkedHashMap<String, String> testData) {
    System.out.println(testData.get("Shiv"));    // Output: Shakti
    System.out.println(testData.get("Ram"));     // Output: Sita
    System.out.println(testData.get("Krishna"));  // Output: Radha
}
```

**When to use complex data:**
- When test data has key-value pairs
- When you need structured data
- When data represents objects/entities

### DataProvider in Different Class

```java
@Test(dataProvider = "dp", dataProviderClass = TestNg_DataProvider.class)
public void test2(LinkedHashMap<String, String> testData) {
    // ...
}
```

Use `dataProviderClass` when DataProvider is in a different class.

### Parallel DataProvider

```java
@DataProvider(name = "dp1", parallel = true)
public Object[][] dp1() {
    return new Object[][]{
        {"shiv", "shakti"},
        {"Ram", "shakti"},
        {"krishna", "shakti"},
        {"lakshman", "urmila"}
    };
}

@Test(dataProvider = "dp1")
public void setup(String i, String j) {
    // Each data set runs in parallel
}
```

**parallel = true:** Each data set runs in separate thread

**Use case:** Speed up data-driven tests by running them in parallel

### Groups (TestNG_Group_N_DependsOnMethodsTest.java)

Groups allow categorization of tests for selective execution.

```java
@Test(groups = {"smoke"}, dependsOnMethods = "group2")
public void group1() {
    System.out.println("smoke group");
}

@Test(groups = {"regression"})
public void group2() {
    System.out.println("Regression Group");
}

@Test(groups = {"regression","smoke"}, dependsOnGroups = {"smoke","regression"})
public void group3() {
    System.out.println("Smoke and Regression Group");
}
```

### Running Groups via testng.xml

```xml
<groups>
    <run>
        <include name="smoke"/>
        <exclude name="regression"/>
    </run>
</groups>
```

### Group Dependencies

```xml
<groups>
    <dependencies>
        <group depends-on="smoke" name="regression"></group>
    </dependencies>
</groups>
```

**Meaning:** Regression tests run only after smoke tests complete.

### dependsOnMethods

```java
@Test(dependsOnMethods = "login")
public void dashboard() {
    // Runs only after login() completes
}
```

If `login()` fails, `dashboard()` is skipped.

### dependsOnGroups

```java
@Test(dependsOnGroups = "smoke")
public void regressionTest() {
    // Runs only after all smoke group tests complete
}
```

### Parameters (testNG_parameterTest.java)

Pass parameters from testng.xml to test methods.

### testng.xml
```xml
<parameter name="browser" value="chrome"/>
<parameter name="url" value="https://demoqa.com"/>

<classes>
    <class name="ParameterTest"/>
</classes>
```

### Test Class
```java
@Parameters({"browser", "url"})
@Test
public void test(String browser, String url) {
    System.out.println("Browser: " + browser);
    System.out.println("URL: " + url);
}
```

### Invocation Count

```java
@Test(invocationCount = 5)
public void test() {
    // Runs 5 times
}
```

### Thread Pool Size with Invocation Count

```java
@Test(invocationCount = 5, threadPoolSize = 3)
public void test() {
    // Runs 5 times using 3 threads
}
```

### Success Percentage

```java
@Test(successPercentage = 60, invocationCount = 5)
public void test() {
    // Passes if 3 out of 5 invocations pass (60%)
}
```

**Use case:** Flaky tests that may fail occasionally but are acceptable if they pass most of the time.

### Enabled/Disabled Tests

```java
@Test(enabled = false)
public void disabledTest() {
    // Will not run
}

@Test(enabled = true)  // Default
public void enabledTest() {
    // Will run
}
```

---

## 7. TESTNG LISTENERS

### What are Listeners?
Listeners are interfaces that allow you to customize TestNG behavior by intercepting test lifecycle events.

### ITestListener (ITestListener_Override_Methods.java)

ITestListener provides methods to monitor test execution.

### Implementing ITestListener

```java
public class ITestListener_Override_Methods implements ITestListener {
    
    public void onStart(ITestContext context) {
        System.out.println("onStart method started");
        // Runs when test suite starts
    }
    
    public void onFinish(ITestContext context) {
        System.out.println("onFinish method started");
        // Runs when test suite finishes
    }
    
    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" + result.getName());
        // Runs before each test method
    }
    
    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess Method" + result.getName());
        // Runs when test passes
    }
    
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure Method" + result.getName());
        // Runs when test fails
        // Good place for screenshot capture
    }
    
    public void onTestSkipped(ITestResult result) {
        System.out.println("onTestSkipped Method" + result.getName());
        // Runs when test is skipped
    }
    
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage" + result.getName());
        // Runs when successPercentage condition met
    }
}
```

### Using Listener in Test Class

```java
@Listeners(ITestListener_Override_Methods.class)
public class Implement_ITestListener {
    
    @Test
    public void test1() {
        // Listener methods will be called
    }
}
```

### Using Listener in testng.xml

```xml
<listeners>
    <listener class-name="Listeners.ITestListener_Override_Methods"/>
</listeners>
```

**Advantage:** Applies to all tests in suite without modifying each class.

### Real-World Use Cases for ITestListener

**1. Screenshot on Failure**
```java
public void onTestFailure(ITestResult result) {
    WebDriver driver = getDriverFromContext(result);
    takeScreenshot(driver, result.getName());
}
```

**2. Logging**
```java
public void onTestStart(ITestResult result) {
    log.info("Starting test: " + result.getName());
}

public void onTestSuccess(ITestResult result) {
    log.info("Test passed: " + result.getName());
}
```

**3. Report Generation**
```java
public void onFinish(ITestContext context) {
    generateCustomReport(context);
}
```

**4. Email Notification**
```java
public void onFinish(ITestContext context) {
    if (context.getFailedTests().size() > 0) {
        sendEmailNotification(context);
    }
}
```

### IReporter

IReporter is used to generate custom TestNG reports.

```java
public class IReporter_override_methods implements IReporter {
    
    public void generateReport(List<XmlSuite> xmlSuites, 
                               List<ISuite> suites, 
                               String outputDirectory) {
        // Generate custom report
        // Access test results, create HTML/Excel/PDF reports
    }
}
```

### ISuiteListener

Monitors suite-level events.

```java
public void onStart(ISuite suite) {
    // Before suite starts
}

public void onFinish(ISuite suite) {
    // After suite finishes
}
```

### IInvokedMethodListener

Monitors every method invocation (before and after).

```java
public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    // Before each method (configuration and test methods)
}

public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    // After each method
}
```

### Interview Questions on Listeners

**Q1: Difference between ITestListener and ISuiteListener?**
- ITestListener: Test-level events (test start, success, failure)
- ISuiteListener: Suite-level events (suite start, finish)

**Q2: How to take screenshot only on failure?**
- Implement onTestFailure() in ITestListener
- Capture screenshot in that method

**Q3: Can we have multiple listeners?**
- Yes, you can implement multiple listeners
- They execute in order defined in testng.xml

---

## 8. PARALLEL EXECUTION

### Why Parallel Execution?
- Faster test execution
- Better utilization of resources
- Reduced feedback time
- Essential for large test suites

### Types of Parallel Execution

### 1. Parallel by Methods (ParallelByMethods.java)

Each test method runs in separate thread.

```java
public class ParallelByMethods {
    
    @Test
    public void test1() {
        // Runs in thread 1
    }
    
    @Test
    public void test2() {
        // Runs in thread 2 (parallel with test1)
    }
}
```

### testng.xml Configuration
```xml
<suite name="Parallel Suite" parallel="methods" thread-count="2">
    <test name="Parallel Test">
        <classes>
            <class name="paralled_Executions.ParallelByMethods"/>
        </classes>
    </test>
</suite>
```

### 2. Parallel by Classes (ParallelByClass.java)

Each class runs in separate thread. Methods within class run sequentially.

```java
public class ParallelByClass {
    
    @Test
    public void test1() {
        // Runs in thread 1
    }
    
    @Test
    public void test2() {
        // Runs in thread 1 (after test1)
    }
}
```

### testng.xml Configuration
```xml
<suite name="Parallel Suite" parallel="classes" thread-count="2">
    <test name="Parallel Test">
        <classes>
            <class name="paralled_Executions.ParallelByClass"/>
            <class name="paralled_Executions.ParallelByMethods"/>
        </classes>
    </test>
</suite>
```

### 3. Parallel by Tests

Each `<test>` tag runs in separate thread.

```xml
<suite name="Parallel Suite" parallel="tests" thread-count="2">
    <test name="Test 1">
        <classes>
            <class name="TestClass1"/>
        </classes>
    </test>
    <test name="Test 2">
        <classes>
            <class name="TestClass2"/>
        </classes>
    </test>
</suite>
```

### 4. Parallel by Instances

Each instance of class runs in separate thread.

```xml
<suite name="Parallel Suite" parallel="instances" thread-count="2">
```

### 5. Parallel by DataProvider (Parallel_By_DataProvider.java)

Each data set runs in separate thread.

```java
@DataProvider(name = "dp1", parallel = true)
public Object[][] dp1() {
    return new Object[][]{
        {"shiv", "shakti"},
        {"Ram", "shakti"},
        {"krishna", "shakti"},
        {"lakshman", "urmila"}
    };
}

@Test(dataProvider = "dp1")
public void setup(String i, String j) {
    // Each data set runs in parallel
    // 4 browsers open simultaneously
}
```

### Thread Count

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="4">
```

**thread-count:** Maximum number of parallel threads.

**How to determine thread count:**
- Based on available CPU cores
- Based on available memory
- Based on browser resource requirements
- Start with 2-3, increase gradually

### Thread-Safe Driver Implementation

For parallel execution, each thread needs its own driver instance.

```java
public class ParallelByClass {
    
    @Test
    public void test1() {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();  // Each thread gets own driver
        driver.get("https://demoqa.com/elements");
        driver.quit();
    }
}
```

**Better approach:** Use ThreadLocal (see DriverFactory.java section)

### Challenges in Parallel Execution

**1. Shared Resources**
- Avoid sharing static variables
- Use ThreadLocal for thread-specific data
- Ensure test data isolation

**2. Database Conflicts**
- Each test should use unique data
- Clean up after test
- Use transactions that rollback

**3. File System Conflicts**
- Use unique file names
- Include thread ID or timestamp in file names
- Clean up temporary files

**4. Browser Resource Limits**
- Too many browsers can crash system
- Monitor memory usage
- Limit parallel threads based on resources

### Best Practices for Parallel Execution

1. **Use ThreadLocal for WebDriver**
2. **Ensure test independence** - no dependencies between tests
3. **Use unique test data** - avoid conflicts
4. **Clean up after each test** - close browsers, delete files
5. **Monitor system resources** - don't overload system
6. **Use appropriate thread count** - based on hardware
7. **Handle race conditions** - proper synchronization if needed

---

## 9. RETRY FAILED TESTS

### Why Retry Failed Tests?
- Flaky tests due to network issues
- Intermittent application issues
- Timing-related failures
- Improve test reliability

### IRetryAnalyzer Interface

Implement IRetryAnalyzer to retry failed tests.

```java
public class FailedTestRun_Using_IRetryAnalyzer implements IRetryAnalyzer {
    
    int count = 0;
    int retry = 4;  // Maximum retry attempts
    
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (count < retry) {
            count++;
            return true;  // Retry the test
        }
        return false;  // Stop retrying
    }
}
```

### Using RetryAnalyzer in Test

```java
@Test(retryAnalyzer = FailedTestRun_Using_IRetryAnalyzer.class)
public void test() {
    System.out.println("11111111");
    assertThat("true", false);  // Will fail and retry 4 times
}
```

### Retry Logic Explained

**First attempt:** Test fails, count = 0, count < 4 → return true (retry)
**Retry 1:** Test fails, count = 1, count < 4 → return true (retry)
**Retry 2:** Test fails, count = 2, count < 4 → return true (retry)
**Retry 3:** Test fails, count = 3, count < 4 → return true (retry)
**Retry 4:** Test fails, count = 4, count < 4 is false → return false (stop)

**Total executions:** 5 (1 initial + 4 retries)

### Configurable Retry Count

```java
public class ConfigurableRetryAnalyzer implements IRetryAnalyzer {
    
    int count = 0;
    int maxRetry = Integer.parseInt(System.getProperty("maxRetry", "3"));
    
    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;
            return true;
        }
        return false;
    }
}
```

Configure via command line:
```
-DmaxRetry=5
```

### Retry Based on Exception Type

```java
public class SmartRetryAnalyzer implements IRetryAnalyzer {
    
    int count = 0;
    int maxRetry = 3;
    
    @Override
    public boolean retry(ITestResult result) {
        Throwable throwable = result.getThrowable();
        
        // Retry only for specific exceptions
        if (throwable instanceof NoSuchElementException ||
            throwable instanceof StaleElementReferenceException ||
            throwable instanceof TimeoutException) {
            
            if (count < maxRetry) {
                count++;
                return true;
            }
        }
        return false;
    }
}
```

### Retry with Listener

```java
public class RetryListener implements IAnnotationTransformer {
    
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, 
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(FailedTestRun_Using_IRetryAnalyzer.class);
    }
}
```

Add to testng.xml:
```xml
<listeners>
    <listener class-name="Listeners.RetryListener"/>
</listeners>
```

**Advantage:** Applies retry logic to all tests without adding annotation to each test.

### Re-run Failed Tests from testng-failed.xml

TestNG automatically creates testng-failed.xml after test run.

```java
public class ReRunFailedTests {
    
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("test-output/testng-failed.xml");
        testng.setTestSuites(suites);
        testng.run();
    }
}
```

### Interview Questions on Retry

**Q1: When should we use retry mechanism?**
- For flaky tests due to external factors (network, timing)
- NOT for actual test failures (bugs in application)
- Use sparingly - can mask real issues

**Q2: What are the downsides of retry?**
- Increases test execution time
- Can mask real application issues
- Makes debugging harder
- False sense of test stability

**Q3: How to limit retry to specific exceptions?**
- Check exception type in retry() method
- Return true only for retry-worthy exceptions

---

## 10. REPORTING & SCREENSHOTS

### ExtentReports

ExtentReports is a popular HTML reporting library for TestNG.

### Basic Setup (ExtentReport_BaseTest.java)

```java
public class ExtentReport_BaseTest {
    
    public static ExtentReports reports;
    public static ExtentTest test;
    
    @BeforeMethod
    public void beforeTest() {
        reports = new ExtentReports(System.getProperty("user.dir") + "/testQA.html");
        
        reports.addSystemInfo("Host Name", "SoftwareTestingMaterial")
                .addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Rajkumar SM");
        
        driver = new ChromeDriver();
    }
    
    @AfterMethod
    public void test1() {
        reports.endTest(test);  // End the test
        reports.flush();        // Write to file
        driver.quit();          // Quit browser
    }
}
```

### Logging Test Steps

```java
@Test
public void loginTest() {
    test = reports.startTest("Login Test");
    
    test.log(LogStatus.INFO, "Navigating to login page");
    driver.get("https://example.com/login");
    
    test.log(LogStatus.INFO, "Entering username");
    driver.findElement(By.id("username")).sendKeys("testuser");
    
    test.log(LogStatus.INFO, "Entering password");
    driver.findElement(By.id("password")).sendKeys("password");
    
    test.log(LogStatus.INFO, "Clicking login button");
    driver.findElement(By.id("login")).click();
    
    test.log(LogStatus.PASS, "Login successful");
}
```

### Log Status Types

```java
test.log(LogStatus.PASS, "Test passed");
test.log(LogStatus.FAIL, "Test failed");
test.log(LogStatus.SKIP, "Test skipped");
test.log(LogStatus.INFO, "Information");
test.log(LogStatus.WARNING, "Warning");
test.log(LogStatus.FATAL, "Fatal error");
```

### Screenshot Capture

```java
public static String capture(WebDriver driver) {
    TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
    File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
    
    File Dest = new File("src/../ErrImages/" + System.currentTimeMillis() + ".png");
    String errflpath = Dest.getAbsolutePath();
    
    try {
        FileUtils.copyFile(scrFile, Dest);
    } catch (Exception e) {
        test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver)) + "Test Failed");
        e.printStackTrace();
    }
    return errflpath;
}
```

### Screenshot on Failure with Listener

```java
public void onTestFailure(ITestResult result) {
    Object testClass = result.getInstance();
    WebDriver driver = ((BaseTest) testClass).driver;
    
    String screenshotPath = capture(driver);
    test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath) + "Test Failed");
}
```

### ExtentReports Configuration (extent-config.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<extentreports>
    <configuration>
        <theme>standard</theme>
        <encoding>UTF-8</encoding>
        <protocol>https</protocol>
        <documentTitle>Extent Reports</documentTitle>
        <reportName>Automation Report</reportName>
        <timeline>true</timeline>
    </configuration>
</extentreports>
```

Load configuration:
```java
reports.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
```

### Full Page Screenshot with Ashot

```java
// Add dependency: ru.yandex.qatools.ashot:ashot
Screenshot s = new AShot()
    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
    .takeScreenshot(driver);

ImageIO.write(s.getImage(), "PNG", new File("fullPageScreenshot.png"));
```

**ShootingStrategies.viewportPasting(1000):**
- Scrolls and captures entire page
- 1000ms scroll delay for content loading
- Stitches screenshots together

### Interview Questions on Reporting

**Q1: Why do we need custom reporting when TestNG provides reports?**
- TestNG reports are basic and not visually appealing
- ExtentReports provides rich, interactive HTML reports
- Better for stakeholders to understand test results
- Supports screenshots, charts, logs

**Q2: How to capture screenshot only on failure?**
- Use ITestListener's onTestFailure() method
- Capture screenshot in that method
- Attach to report

---

## 11. WEBDRIVER EVENT LISTENERS (SELENIUM 4)

### What are WebDriver Event Listeners?

Selenium 4 introduced WebDriverListener interface to monitor and log all WebDriver operations. This is more powerful than the old WebDriverEventListener.

### Implementing WebDriverListener (EventHandler.java)

```java
public class EventHandler implements WebDriverListener {
    
    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("BEFORE navigating to: " + url);
    }
    
    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("AFTER navigating to: " + url);
    }
    
    @Override
    public void beforeClick(WebElement element) {
        System.out.println("BEFORE click on: " + element);
    }
    
    @Override
    public void afterClick(WebElement element) {
        System.out.println("AFTER click on: " + element);
    }
}
```

### Using Event Listener

```java
WebDriver driver = new ChromeDriver();
EventHandler handler = new EventHandler();

WebDriver eventDriver =
        new EventFiringDecorator<>(handler)
        .decorate(driver);

        eventDriver.get("https://toolsqa.com");


// Now all operations will be logged
eventDriver.get("https://example.com");
eventDriver.findElement(By.id("button")).click();
```

### Available Event Methods

### WebDriver Events
```java
beforeGet(WebDriver driver, String url)
afterGet(WebDriver driver, String url)
beforeGetCurrentUrl(WebDriver driver)
afterGetCurrentUrl(WebDriver driver, String result)
beforeGetTitle(WebDriver driver)
afterGetTitle(WebDriver driver, String result)
beforeFindElement(WebDriver driver, By locator)
afterFindElement(WebDriver driver, By locator, WebElement result)
beforeFindElements(WebDriver driver, By locator)
afterFindElements(WebDriver driver, By locator, List<WebElement> result)
beforeClose(WebDriver driver)
afterClose(WebDriver driver)
beforeQuit(WebDriver driver)
afterQuit(WebDriver driver)
```

### WebElement Events
```java
beforeClick(WebElement element)
afterClick(WebElement element)
beforeSendKeys(WebElement element, CharSequence... keysToSend)
afterSendKeys(WebElement element, CharSequence... keysToSend)
beforeClear(WebElement element)
afterClear(WebElement element)
beforeGetText(WebElement element)
afterGetText(WebElement element, String result)
beforeIsDisplayed(WebElement element)
afterIsDisplayed(WebElement element, boolean result)
```

### Navigation Events
```java
beforeBack(WebDriver.Navigation navigation)
afterBack(WebDriver.Navigation navigation)
beforeForward(WebDriver.Navigation navigation)
afterForward(WebDriver.Navigation navigation)
beforeRefresh(WebDriver.Navigation navigation)
afterRefresh(WebDriver.Navigation navigation)
```

### Alert Events
```java
beforeAccept(Alert alert)
afterAccept(Alert alert)
beforeDismiss(Alert alert)
afterDismiss(Alert alert)
beforeGetText(Alert alert)
afterGetText(Alert alert, String result)
beforeSendKeys(Alert alert, String text)
afterSendKeys(Alert alert, String text)
```

### Window Events
```java
beforeMaximize(WebDriver.Window window)
afterMaximize(WebDriver.Window window)
beforeFullscreen(WebDriver.Window window)
afterFullscreen(WebDriver.Window window)
beforeGetSize(WebDriver.Window window)
afterGetSize(WebDriver.Window window, Dimension result)
```

### Exception Handling

```java
@Override
public void onError(Object target, Method method, Object[] args, 
                    InvocationTargetException e) {
    System.out.println("Exception in: " + method.getName());
    System.out.println("Exception: " + e.getTargetException());
}
```

### Use Cases for Event Listeners

**1. Detailed Logging**
```java
public void beforeClick(WebElement element) {
    log.info("Clicking on element: " + element.toString());
}
```

**2. Performance Monitoring**
```java
long startTime;

public void beforeGet(WebDriver driver, String url) {
    startTime = System.currentTimeMillis();
}

public void afterGet(WebDriver driver, String url) {
    long endTime = System.currentTimeMillis();
    log.info("Page load time: " + (endTime - startTime) + "ms");
}
```

**3. Screenshot on Failure**
```java
public void onError(Object target, Method method, Object[] args, 
                    InvocationTargetException e) {
    takeScreenshot(driver);
}
```

**4. Element Highlighting**
```java
public void beforeClick(WebElement element) {
    highlightElement(element);
}

private void highlightElement(WebElement element) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.border='3px solid red'", element);
}
```

### WebDriverListener vs WebDriverEventListener

**WebDriverListener (Selenium 4):**
- More comprehensive event coverage
- Type-safe with generics
- Supports all new Selenium 4 features
- Recommended for new projects

**WebDriverEventListener (Legacy):**
- Older interface
- Limited event coverage
- Still supported for backward compatibility
- Use if maintaining old codebase

### Interview Questions

**Q1: Difference between WebDriverListener and ITestListener?**
- WebDriverListener: Monitors WebDriver operations (click, get, findElement)
- ITestListener: Monitors TestNG test lifecycle (test start, success, failure)

**Q2: When to use WebDriverListener?**
- For detailed logging of all WebDriver operations
- For performance monitoring
- For debugging test failures
- For custom behavior on specific operations

---

## 12. FILE OPERATIONS

### Upload Files (UploadFile.java)

Selenium can upload files by sending the file path to the file input element.

```java
@Test
public void upload() {
    WebDriver driver = new ChromeDriver();
    driver.get("http://the-internet.herokuapp.com/");
    
    WebElement uploadButton = driver.findElement(By.xpath("//li/a[contains(text(),'File Upload')]"));
    uploadButton.click();
    
    WebElement chooseFile = driver.findElement(By.xpath("//input[@id='file-upload']"));
    
    String filePath = System.getProperty("user.dir") + "/src/test/test.text";
    chooseFile.sendKeys(filePath);  // Send absolute file path
    
    WebElement submitFile = driver.findElement(By.xpath("//input[@id='file-submit']"));
    submitFile.click();
    
    WebElement uploadedFile = driver.findElement(By.xpath("//div[contains(text(),'test.text')]"));
    assertThat(uploadedFile.getText(), containsString("test.text"));
}
```

**Important Notes:**
- Use absolute file path
- Element must be `<input type="file">`
- Cannot upload to non-file input elements
- Works with local files only (not remote)

### Download Files

```java
@Test
public void downloadFile() {
    // Configure Chrome to download without prompt
    ChromeOptions options = new ChromeOptions();
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", "/path/to/download/folder");
    prefs.put("download.prompt_for_download", false);
    options.setExperimentalOption("prefs", prefs);
    
    WebDriver driver = new ChromeDriver(options);
    
    // Click download link
    driver.findElement(By.id("downloadLink")).click();
    
    // Wait for download to complete
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    wait.until(ExpectedConditions.numberOfFilesToBe(1));
}
```

### Read Text File (Read_text_file.java)

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public void readTextFile() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
    reader.close();
}
```

### Read PDF File (Read_PDF_File.java)

Using Apache PDFBox library.

```java
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;

public void read_PDF_FIle() throws Exception {
    PDDocument document = PDDocument.load(new File("document.pdf"));
    PDFTextStripper stripper = new PDFTextStripper();
    String text = stripper.getText(document);
    
    String[] lines = text.split(System.lineSeparator());
    for (String s : lines) {
        if (s.contains("Email: ")) {
            System.out.println(s.replace("Email: ", ""));
        }
    }
    document.close();
}
```

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.apache.pdfbox</groupId>
    <artifactId>pdfbox</artifactId>
    <version>2.0.27</version>
</dependency>
```

### Read Excel File

Using Apache POI.

```java
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;

public void readExcel() throws Exception {
    FileInputStream file = new FileInputStream(new File("test.xlsx"));
    Workbook workbook = WorkbookFactory.create(file);
    Sheet sheet = workbook.getSheetAt(0);
    
    for (Row row : sheet) {
        Cell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
    }
    workbook.close();
}
```

### Write to Text File

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public void writeToFile() throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
    writer.write("Test data");
    writer.newLine();
    writer.write("More data");
    writer.close();
}
   // [ or ]
    public void writeToFile() throws IOException {
        Files.write(
                Path.of("output.txt"),
                List.of("Test data", "More data")
        );
        
        //  Files.writeString( Path.of("output.txt"),
        //        "Test data%nMore data".formatted();
    }
}
```

---

## 13. CROSS-BROWSER TESTING

### Why Cross-Browser Testing?
- Different browsers render differently
- Ensure consistent user experience
- Browser-specific compatibility issues
- Market coverage (Chrome, Firefox, Safari, Edge)

### CrossBrowserTest.java

```java
public class CrossBrowserTest {
    
    @Parameters("browser")
    @Test
    public void test(String browser) {
        WebDriver driver;
        
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        
        driver.get("https://demoqa.com/elements");
        // Test logic
        driver.quit();
    }
}
```

### testng.xml Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="Cross-Browser Suite" parallel="tests">
    
    <test name="Chrome Test">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="Base.CrossBrowserTest"/>
        </classes>
    </test>
    
    <test name="Firefox Test">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="Base.CrossBrowserTest"/>
        </classes>
    </test>
    
    <test name="Edge Test">
        <parameter name="browser" value="edge"/>
        <classes>
            <class name="Base.CrossBrowserTest"/>
        </classes>
    </test>
    
</suite>
```

### Browser Factory Pattern

```java
public class BrowserFactory {
    
    public static WebDriver getDriver(String browser) {
        WebDriver driver;
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");  // Run without UI
                chromeOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
                
            case "safari":
                driver = new SafariDriver();
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        return driver;
    }
}
```

### Headless Browser Execution

Run tests without GUI (faster, suitable for CI/CD).

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
options.addArguments("--disable-gpu");
options.addArguments("--window-size=1920,1080");
WebDriver driver = new ChromeDriver(options);
```

### Browser-Specific Options

**Chrome Options:**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--start-maximized");
options.addArguments("--incognito");
options.addArguments("--disable-popup-blocking");
options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
```

**Firefox Options:**
```java
FirefoxOptions options = new FirefoxOptions();
options.addArguments("--private");
options.setHeadless(true);
```

### Grid Testing (Selenium Grid)

Run tests on remote machines/different browsers.

```java
WebDriver driver = new RemoteWebDriver(
    new URL("http://localhost:4444/wd/hub"),
    new ChromeOptions()
);
```

---

## 14. JAVA 8 STREAMS & COLLECTIONS FOR TESTING

### Why Streams in Testing?
- Concise code for data manipulation
- Functional programming style
- Easy to filter, map, reduce test data
- Process collections efficiently

### Stream Basics (Java_8_Code.java)

```java
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

// Convert array to list
List<Integer> list = Arrays.stream(numbers)
    .boxed()
    .collect(Collectors.toList());

// Filter even numbers
List<Integer> evenNumbers = list.stream()
    .filter(a -> a % 2 == 0)
    .collect(Collectors.toList());

// Filter using method reference
List<Integer> evenNumbers2 = list.stream()
    .filter(t2::numberIsEvenOrOdd)
    .collect(Collectors.toList());

// Check if any element matches
boolean hasEven = list.stream()
    .anyMatch(t2::numberIsEvenOrOdd);

// Map to uppercase
List<String> words = Arrays.asList("apple", "banana", "cherry");
List<String> upperCase = words.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Reduce to single value
int sum = list.stream()
    .reduce(0, (a, b) -> a + b);

// Find min/max
int min = list.stream()
    .reduce((a, b) -> a < b ? a : b)
    .get();
```

### Common Stream Operations

### Filter
```java
// Filter strings containing "auto"
String[] arr = {"automatic", "aautozone", "showauto"};
List<String> arrayList = Arrays.stream(arr).collect(Collectors.toList());

List<String> filtered = arrayList.stream()
    .filter(a -> a.contains("auto"))
    .collect(Collectors.toList());
```

### Map
```java
// Add suffix to each string
List<String> updated = arrayList.stream()
    .filter(a -> a.contains("auto"))
    .map(a -> a + "_updated")
    .collect(Collectors.toList());
```

### Reduce
```java
// Concatenate strings
String result = arrayList.stream()
    .filter(a -> a.contains("auto"))
    .map(a -> a + "_updated")
    .reduce("", (a, b) -> a + "5" + b + "3 \n")
    .toUpperCase();
```

### Collect
```java
// Collect to List
List<String> list = stream.collect(Collectors.toList());

// Collect to Set
Set<String> set = stream.collect(Collectors.toSet());

// Collect to Map
Map<String, Integer> map = stream.collect(
    Collectors.toMap(String::toString, String::length)
);

// Collect to LinkedHashMap (preserve order)
Map<String, Integer> linkedMap = stream.collect(
    Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (old, new) -> old,
        LinkedHashMap::new
    )
);
```

### Sorting with Streams

```java
// Sort by string length
Arrays.sort(arr, Comparator.comparing(String::length));

// Sort list by value
List<Integer> sorted = list.stream()
    .sorted()
    .collect(Collectors.toList());

// Sort in descending order
List<Integer> descending = list.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());

// Sort by custom comparator
List<String> sortedByLength = strings.stream()
    .sorted(Comparator.comparing(String::length))
    .collect(Collectors.toList());
```

### Optional Class

```java
// Avoid NullPointerException
Optional<String> optional = Optional.ofNullable(getValue());

// If present, perform action
optional.ifPresent(value -> System.out.println(value));

// Or else default
String result = optional.orElse("default");

// Or else throw exception
String result = optional.orElseThrow(() -> new RuntimeException("Value not present"));

// Map and filter
Optional<String> result = optional
    .map(String::toUpperCase)
    .filter(s -> s.length() > 5);
```

### Stream with WebElement

```java
// Get text from all elements
List<WebElement> elements = driver.findElements(By.xpath("//div[@class='item']"));
List<String> texts = elements.stream()
    .map(WebElement::getText)
    .collect(Collectors.toList());

// Filter visible elements
List<WebElement> visibleElements = elements.stream()
    .filter(WebElement::isDisplayed)
    .collect(Collectors.toList());

// Convert WebElement list to Map
Map<String, WebElement> elementMap = elements.stream()
    .collect(Collectors.toMap(
        WebElement::getText,
        Function.identity()
    ));
```

### HashMap Operations

```java
// Count character occurrences
String name = "duplicateCharacterinString";
Map<Character, Integer> storeMap = new LinkedHashMap<>();
char[] chArr = name.toCharArray();

for (char ch : chArr) {
    storeMap.put(ch, storeMap.getOrDefault(ch, 0) + 1);
}

// Filter duplicates
Map<Character, Integer> duplicates = storeMap.entrySet().stream()
    .filter(entry -> entry.getValue() > 1)
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue
    ));

// Sort by value
Map<Character, Integer> sorted = storeMap.entrySet()
    .stream()
    .sorted(Map.Entry.comparingByValue())
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (old, new) -> old,
        LinkedHashMap::new
    ));
```

### Merge Two Maps

```java
Map<String, Integer> map1 = new HashMap<>();
map1.put("A", 1);
map1.put("B", 2);

Map<String, Integer> map2 = new HashMap<>();
map2.put("B", 3);
map2.put("C", 4);

Map<String, Integer> merged = new HashMap<>(map1);
map2.forEach((k, v) -> merged.merge(k, v, Integer::sum));

// Result: {A=1, B=5, C=4}
```

### Interview Questions on Streams

**Q1: Difference between map() and flatMap()?**
- map(): Transforms each element to another object (1-to-1)
- flatMap(): Transforms each element to a stream and flattens (1-to-many)

**Q2: What is the difference between Collection and Stream?**
- Collection: In-memory data structure, can be iterated multiple times
- Stream: Sequence of data for computation, can be iterated only once

**Q3: What is terminal vs intermediate operation?**
- Intermediate: Returns stream, lazy evaluation (filter, map, sorted)
- Terminal: Returns result or void, triggers execution (collect, reduce, forEach)

---

## 15. API TESTING WITH RESTASSURED

### What is API Testing?
Testing APIs directly without UI. Faster, more reliable than UI testing.

### RestAssured Basics

RestAssured is a Java library for testing REST services.

### Serialization and Deserialization (Serialization_Deserialization.java)

```java
public class Serialization_Deserialization {
    
    // Serialization = Java Object → JSON
    // Deserialization = JSON → Java Object
    
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        // Serialization
        People people = new People(1, "Name1", "Name2", new ArrayList<>());
        String json = mapper.writeValueAsString(people);
        
        // Deserialization
        People p1 = mapper.readValue(json, People.class);
    }
}
```

### POJO Class for API

```java
public class People {
    private int id;
    private String firstName;
    private String lastName;
    private List<String> hobbies;
    
    // Constructor
    public People(int id, String firstName, String lastName, List<String> hobbies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hobbies = hobbies;
    }
    
    // Getters and Setters
}
```

### GET Request

```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public void getUsers() {
    RestAssured.baseURI = "https://api.example.com";
    
    Response response = given()
        .queryParam("page", 1)
        .header("Content-Type", "application/json")
        .when()
        .get("/users")
        .then()
        .statusCode(200)
        .extract()
        .response();
    
    // Get response as string
    String responseBody = response.asString();
    System.out.println(responseBody);
    
    // Get response as POJO
    People[] users = response.as(People[].class);
}
```

### POST Request

```java
public void createUser() {
    People user = new People(1, "John", "Doe", Arrays.asList("reading", "coding"));
    
    given()
        .header("Content-Type", "application/json")
        .body(user)
        .when()
        .post("/users")
        .then()
        .statusCode(201)
        .body("message", equalTo("User created"));
}
```

### PUT Request

```java
public void updateUser() {
    People user = new People(1, "John", "Smith", Arrays.asList("reading"));
    
    given()
        .header("Content-Type", "application/json")
        .body(user)
        .pathParam("id", 1)
        .when()
        .put("/users/{id}")
        .then()
        .statusCode(200);
}
```

### DELETE Request

```java
public void deleteUser() {
    given()
        .pathParam("id", 1)
        .when()
        .delete("/users/{id}")
        .then()
        .statusCode(204);
}
```

### Response Validation

```java
given()
    .when()
    .get("/users/1")
    .then()
    .statusCode(200)
    .body("firstName", equalTo("John"))
    .body("lastName", equalTo("Doe"))
    .body("hobbies", hasItem("reading"));
```

### JSON Path

```java
Response response = given().get("/users");

// Get specific field
String firstName = response.path("firstName");

// Get array element
String firstHobby = response.path("hobbies[0]");

// Get all matching elements
List<String> allHobbies = response.path("hobbies");
```

### Authentication

```java
// Basic Auth
given()
    .auth().basic("username", "password")
    .when()
    .get("/secure-data");

// Bearer Token
given()
    .header("Authorization", "Bearer " + token)
    .when()
    .get("/secure-data");

// API Key
given()
    .header("x-api-key", "your-api-key")
    .when()
    .get("/data");
```

### File Upload in API

```java
given()
    .multiPart("file", new File("test.txt"))
    .when()
    .post("/upload")
    .then()
    .statusCode(200);
```

### Interview Questions on API Testing

**Q1: When to do API testing vs UI testing?**
- API testing: Faster, more reliable, test business logic
- UI testing: Test user interface, user experience
- Best practice: Test at API level, UI for critical user flows

**Q2: What is serialization/deserialization?**
- Serialization: Convert object to JSON/XML for transmission
- Deserialization: Convert JSON/XML back to object

**Q3: Common HTTP status codes?**
- 200: OK
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

---

## 16. JAVA PROGRAMMING FOR INTERVIEWS

### Integer Operations (InterviewCode_WRT_Integers.java)

### Even/Odd Check
```java
public static void no_Even_Odd(int no) {
    if (no % 2 == 0) {
        System.out.println("no. is even");
    } else
        System.out.println("no is odd");
}
```

### Prime Number Check
```java
public static boolean isPrime(int no) {
    if (no <= 1) {
        return false; // 0 and 1 are not prime numbers
    }
    for (int i = 2; i <= Math.sqrt(no); i++) {
        if (no % i == 0) {
            return false; // Divisible by another number → not prime
        }
    }
    return true; // No divisors found → prime
}
```

**Optimization:** Check only up to square root of number - if no divisor found up to √n, none exist beyond.

### Fibonacci Series
```java
public static void fibonacciSeries(int no) {
    int first = 0;
    int second = 1;
    int finalNo;
    for (int i = 0; i <= no; i++) {
        System.out.println(first);
        finalNo = first + second;
        first = second;
        second = finalNo;
    }
}
```

**Output for n=5:** 0, 1, 1, 2, 3, 5

### Swap Numbers Without Third Variable
```java
public static void swap_No_Without_Third_No() {
    int a = 10;
    int b = 20;
    a = a + b; // 30
    b = a - b; // 10
    a = a - b; // 20
    System.out.println(a + " " + b); // 20 10
}
```

### Factorial (Recursive)
```java
public static long factorial(long n) {
    if (n == 0 || n == 1)
        return 1;
    return n * factorial(n - 1);
}
```

**Example:** factorial(5) = 5 * 4 * 3 * 2 * 1 = 120

### Reverse Number
```java
public static int reverce_No(int no) {
    int reverse = 0;
    while (no != 0) {
        int reminder = no % 10;
        reverse = reverse * 10 + reminder;
        no = no / 10;
    }
    System.out.println("reverse no is :-" + reverse);
    return reverse;
}
```

**Example:** 1234 → 4321

### Armstrong Number Check
```java
public static void Armstrong_no_Check(int no) {
    int actualNo = no;
    int sum = 0;
    int length = String.valueOf(no).length();
    while (no > 0) {
        int digit = no % 10;
        sum += (int) Math.pow(digit, length);
        no = no / 10;
    }
    if (sum == actualNo) {
        System.out.println("No is Armstrong");
    } else {
        System.out.println("No is not Armstrong");
    }
}
```

**Armstrong Number:** Sum of digits raised to power of number of digits equals the number.
**Example:** 153 = 1³ + 5³ + 3³ = 1 + 125 + 27 = 153 ✓

### Palindrome Number
```java
public static void no_Is_Palindrome() {
    int no = 123431;
    int actualNo = no;
    int reverse = 0;
    while (no != 0) {
        int reminder = no % 10;
        reverse = reverse * 10 + reminder;
        no = no / 10;
    }
    if (actualNo == reverse)
        System.out.println("no is Palindrom");
    else
        System.out.println("No is not palindrom");
}
```

### Sum of Digits
```java
public static void sum_of_Digit_Of_Given_No() {
    int no = 12343;
    int a = 0, b = 0;
    while (no != 0) {
        a = no % 10;
        b = b + a;
        no = no / 10;
    }
    System.out.println(b); // 1+2+3+4+3 = 13
}
```

### Random Number Generation
```java
public static void palindrom_No() {
    Random random = new Random();
    
    // Random odd number between 1000-2000
    System.out.println(random.ints(1000, 2000)
        .filter(a -> a % 2 != 0)
        .findAny()
        .getAsInt());
    
    // Random number between 100-200
    System.out.println(random.ints(100, 200)
        .findFirst()
        .getAsInt());
}
```

### Find Max/Min from List
```java
@Test
public void findMaxMinFromList() {
    List<Integer> list = Arrays.asList(5, 2, 8, 1, 9, 3);
    
    int max = list.stream().max(Integer::compareTo).orElse(-1);
    int min = list.stream().min(Integer::compareTo).orElse(-1);
    
    System.out.println("List: " + list);
    System.out.println("Max: " + max + ", Min: " + min);
    // Output: Max: 9, Min: 1
}
```

### String Manipulation (InterviewCode_WRT_Strings.java)

### First Non-Repeating Character
```java
public static char firstNonRepeat(String s) {
    Map<Character, Integer> map = new LinkedHashMap<>();
    
    for (char c : s.toCharArray())
        map.put(c, map.getOrDefault(c, 0) + 1);
    
    for (char c : map.keySet())
        if (map.get(c) == 1)
            return c;
    
    return '_';
}
```

**Example:** "swiss" → 'w' (s=2, w=1, i=1 - w is first with count 1)

### String Compression
```java
public void a2b3c4() {
    String name = "aa2b3ccc4D1";
    String output = "";
    char[] chArr = name.toCharArray();
    
    for (int i = 0; i < chArr.length; i++) {
        char newChar = chArr[i];
        if (Character.isDigit(chArr[i + 1])) {
            int repeat = Character.getNumericValue(chArr[i + 1]);
            for (int j = 0; j < repeat; j++) {
                output = output + newChar;
            }
            i++;
        } else {
            output = output + newChar;
        }
    }
    System.out.println(output);
}
```

### Longest Substring Without Repeating Characters
```java
public void longest_substring_without_repeating_characters() {
    String str = "abcabbcd";
    String longestString = "";
    
    for (int i = 0; i < str.length(); i++) {
        for (int j = i + 1; j <= str.length(); j++) {
            String subString = str.substring(i, j);
            
            Map<Character, Integer> map = new HashMap<>();
            for (Character c : subString.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
            
            boolean hasDuplicates = map.entrySet().stream()
                .anyMatch(entry -> entry.getValue() > 1);
            
            if (!hasDuplicates && subString.length() > longestString.length()) {
                longestString = subString;
            }
        }
    }
    System.out.println(longestString);  // "abcd"
}
```

### Find Common String in String Array
```java
public void Find_Common_String_In_String_Array() {
    String[] arr = {"automatic", "aautozone", "showauto", "moboleauto", 
                   "myautoriksha", "yourautoShoq", "automation"};
    int mainArrayLen = arr.length;
    String firstString = arr[0];
    Arrays.sort(arr, Comparator.comparing(String::length));
    int firstStringLen = firstString.length();
    String repString = "";
    
    for (int i = 0; i < firstStringLen; i++) {
        for (int j = i + 1; j <= firstStringLen; j++) {
            String subString = firstString.substring(i, j);
            int k = (int) Arrays.stream(arr).filter(a -> a.contains(subString)).count();
            if (k == arr.length && repString.length() < subString.length())
                repString = subString;
        }
    }
    System.out.println(repString);  // "auto"
}
```

### Longest Palindromic Substring
```java
public static void find_longest_subString_and_Polendrom_SubString_from_Given_String() {
    String s = "w12aaddaamadamaadaads232";
    int strLength = s.length();
    boolean plndrCheck = false;
    String reps = "";
    String rep = "";
    
    for (int i = 0; i < strLength; i++) {
        for (int j = i + 1; j <= strLength; j++) {
            String subString = s.substring(i, j);
            
            // Find longest repetitive string
            Pattern p = Pattern.compile(subString);
            Matcher m = p.matcher(s);
            int count = 0;
            while (m.find()) {
                count++;
            }
            if (count > 1 && reps.length() < subString.length()) {
                reps = subString;
            }
            
            // Find longest palindrome
            plndrCheck = plndrCheck(subString);
            if (plndrCheck && rep.length() < subString.length())
                rep = subString;
        }
    }
    System.out.println("Longest repetitive: " + reps);
    System.out.println("Longest palindrome: " + rep);
}

public static boolean plndrCheck(String str) {
    char[] ch = str.toCharArray();
    int count = 0;
    int i = 0;
    for (i = 0; i < ch.length / 2; i++) {
        if (ch[i] == ch[ch.length - 1 - i]) {
            count++;
        }
    }
    return i == count;
}
```

### Reverse String

```java
public void reverseString() {
    String name = "ChaitanyaSilawat";
    String revString = "";
    char[] ch = name.toCharArray();
    
    for (int i = ch.length - 1; i >= 0; i--) {
        revString = revString + ch[i];
    }
    System.out.println(revString);  // "tawalISaynatiahC"
}
```

### Reverse Each Word at Same Position

```java
public void reverse_eachWord_at_Same_PLace() {
    String name = "My Name is Chaitanya";
    String[] StringArr = name.split("\\s");
    String revString = "";
    
    for (String eachWord : StringArr) {
        char[] chArr = eachWord.toCharArray();
        String revWord = "";
        for (int i = chArr.length - 1; i >= 0; i--) {
            revWord = revWord + chArr[i];
        }
        revString = revString + revWord + " ";
    }
    System.out.println(revString);  // "yM emaN si aynataihC "
}
```

### Find Duplicate Characters

```java
public void find_Dup_Char_In_String() {
    String name = "duplicateCharacterinString";
    Map<Character, Integer> storeMap = new LinkedHashMap<>();
    char[] chArr = name.toCharArray();
    
    for (char ch : chArr) {
        storeMap.put(ch, storeMap.getOrDefault(ch, 0) + 1);
    }
    
    // Filter duplicates
    Map<Character, Integer> duplicates = storeMap.entrySet().stream()
        .filter(entry -> entry.getValue() > 1)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
    System.out.println(duplicates);
}
```

### Count Words in String

```java
public void count_Of_Word_In_String() {
    String name = "My Name is chaitanya   silawat";
    String[] chArr = name.split("\\s");
    System.out.println(Arrays.stream(chArr).filter(a -> !a.isEmpty()).count());
}
```

### Palindrome Check

```java
public void palindram() {
    String name = "daamnmmaad";
    char[] chArr = name.toCharArray();
    int count = 0;
    
    for (int i = 0; i <= chArr.length / 2; i++) {
        if (chArr[i] == chArr[chArr.length - 1 - i]) {
            count++;
        }
    }
    
    if (count == chArr.length / 2 + 1)
        System.out.println("Palindrome");
    else
        System.out.println("Not Palindrome");
}
```

### Anagram Check

```java
public void verify_Two_String_Anagrams() {
    String name1 = "listen";
    String name2 = "silent";
    
    char[] ch1 = name1.replace("\\s", "").toCharArray();
    char[] ch2 = name2.replace("\\s", "").toCharArray();
    
    Arrays.sort(ch1);
    Arrays.sort(ch2);
    
    System.out.println(Arrays.equals(ch1, ch2));  // true
}
```

### Count Vowels

```java
public void count_vowel_In_String() {
    String name = "chaitanya";
    
    // Count vowels
    long vowelCount = name.toLowerCase().chars()
        .filter(c -> "aeiou".indexOf(c) != -1)
        .count();
    
    // Print vowels
    List<Character> vowels = name.chars()
        .mapToObj(c -> (char) c)
        .filter(c -> "aeiou".indexOf(c) != -1)
        .collect(Collectors.toList());
}
```

### Unique Characters

```java
public void unique_Charactor_In_Stirg() {
    String name = "mynameisChaitanya";
    String output = "";
    
    for (char c : name.toCharArray()) {
        if (!output.contains(Character.toString(c))) {
            output = output + c;
        }
    }
    System.out.println(output);
}
```

### Swap Strings Without Third Variable

```java
public void swap_string_without_3rd_String() {
    String s1 = "chaitanya";
    String s2 = "aayu";
    
    s1 = s1 + s2;
    s2 = s1.substring(0, s1.length() - s2.length());
    s1 = s1.substring(s2.length());
    
    System.out.println(s1);  // "aayu"
    System.out.println(s2);  // "chaitanya"
}
```

### Bracket Balancing

```java
public void BrackerOpenClose() {
    String str = "{[()]}";
    Stack<Character> stack = new Stack<>();
    Map<Character, Character> charMap = new HashMap<>();
    charMap.put('}', '{');
    charMap.put(']', '[');
    charMap.put(')', '(');
    
    for (Character ch : str.toCharArray()) {
        if (charMap.containsValue(ch))
            stack.push(ch);
        else if (charMap.containsKey(ch)) {
            if (stack.isEmpty() || stack.pop() != charMap.get(ch)) {
                break;
            }
        }
    }
    
    boolean isBalanced = stack.isEmpty();
    System.out.println(isBalanced);  // true
}
```

### Longest Substring Without Repeating Characters

```java
public void longest_substring_without_repeating_characters() {
    String str = "abcabbcd";
    String longestString = "";
    
    for (int i = 0; i < str.length(); i++) {
        for (int j = i + 1; j <= str.length(); j++) {
            String subString = str.substring(i, j);
            
            Map<Character, Integer> map = new HashMap<>();
            for (Character c : subString.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
            
            boolean hasDuplicates = map.entrySet().stream()
                .anyMatch(entry -> entry.getValue() > 1);
            
            if (!hasDuplicates && subString.length() > longestString.length()) {
                longestString = subString;
            }
        }
    }
    System.out.println(longestString);  // "abcd"
}
```

### Array Operations (InterviewCode_WRT_Array.java)

### Rotate Array Left
```java
public int[] rotateLeft(int[] arr, int k) {
    int n = arr.length;
    k = k % n;  // Handle k > n
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        result[i] = arr[(i + k) % n];
    }
    return result;
}
```

**Example:** [1,2,3,4,5], k=2 → [3,4,5,1,2]

### Rotate Array Right
```java
public int[] rotateRight(int[] arr, int k) {
    int n = arr.length;
    k = k % n;
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        result[(i + k) % n] = arr[i];
    }
    return result;
}
```

**Example:** [1,2,3,4,5], k=2 → [4,5,1,2,3]

### Merge Two Arrays
```java
public int[] mergeArrays(int[] arr1, int[] arr2) {
    int[] merged = new int[arr1.length + arr2.length];
    System.arraycopy(arr1, 0, merged, 0, arr1.length);
    System.arraycopy(arr2, 0, merged, arr1.length, arr2.length);
    Arrays.sort(merged);
    return merged;
}
```

**Example:** [1,3,5,7] + [2,4,6,8] → [1,2,3,4,5,6,7,8]

### Print Missing Numbers in Array
```java
public static void main(String[] args) {
    List<Integer> l1 = Arrays.asList(1, 3, 6, 8, 19);
    Collections.sort(l1);
    int maxNo = l1.get(l1.size() - 1);
    
    List<Integer> missingList = IntStream.range(1, maxNo)
        .filter(e -> !(l1.contains(e)))
        .boxed()
        .collect(Collectors.toList());
    
    System.out.println("Missing numbers: " + missingList);
    // Output: [2, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
}
```

### Common and Distinct Values in Two Lists (Common_And_Distinct_Value_In_2_Different_List.java)

```java
public static void main(String[] args) {
    List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
    
    // Distinct values (not common to both)
    System.out.println(
        Stream.concat(list1.stream(), list2.stream())
            .filter(e -> !(list1.contains(e) && list2.contains(e)))
            .collect(Collectors.toList())
    );
    // Output: [1, 2, 3, 6, 7, 8]
    
    // Common values
    System.out.println(
        Stream.concat(list1.stream(), list2.stream())
            .filter(e -> (list1.contains(e) && list2.contains(e)))
            .distinct()
            .collect(Collectors.toList())
    );
    // Output: [4, 5]
}
```

### Type Conversions (Conversion.java)

### Convert int[] to ArrayList<Integer>
```java
int[] array2 = {4, 5, 6, 7, 8};
ArrayList<Integer> list = Arrays.stream(array2)
    .boxed()
    .collect(Collectors.toCollection(ArrayList::new));
```

### Convert Integer to int[]
```java
int test = 12345;
int[] testArray = Integer.toString(test).chars().map(c -> c - '0').toArray();
```

### Find Max from List
```java
List<Integer> listq = new ArrayList<>(Arrays.asList(1, 4, 7, 8, 9, 14, 2));
Optional<Integer> max = listq.stream().max(Integer::compareTo);
int i = list.stream().max(Integer::compare).get();
```

### Find Max from Array
```java
int[] st = {1, 3, 4, 3, 2, 6, 15};
Arrays.sort(st);
System.out.println(st[st.length - 1]);  // 15
```

### Common Elements in Two Arrays

```java
public void common_Element_In_2_Array() {
    List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    List<Integer> list2 = Arrays.asList(3, 1, 6, 8, 9);
    
    // Common elements
    List<Integer> common = Stream.concat(list1.stream(), list2.stream())
        .filter(a -> list1.contains(a) && list2.contains(a))
        .distinct()
        .collect(Collectors.toList());
    
    // Distinct elements
    List<Integer> distinct = Stream.concat(list1.stream(), list2.stream())
        .filter(a -> !(list1.contains(a) && list2.contains(a)))
        .collect(Collectors.toList());
}
```

### Remove Duplicates from Array

```java
public void remove_duplicate_from_array() {
    int[] array = {5, 2, 9, 1, 6, 2, 5};
    List<Integer> newList = new ArrayList<>();
    
    for (int i : array) {
        if (!newList.contains(i)) {
            newList.add(i);
        } else {
            System.out.println("Duplicate: " + i);
        }
    }
    System.out.println(newList);  // [5, 2, 9, 1, 6]
}
```

### Find Missing Number

```java
public void find_missing_no() {
    int[] array = {1, 2, 4, 5, 6, 9};
    Arrays.sort(array);
    int maxNo = array[array.length - 1];
    List<Integer> list = new ArrayList<>(Arrays.stream(array).boxed().collect(Collectors.toList()));
    
    for (int i = 1; i < maxNo; i++) {
        if (!list.contains(i)) {
            System.out.println("Missing: " + i);  // 3, 7, 8
        }
    }
}
```

### Find Largest and Second Largest

```java
public void find_SecondLarge_and_second_small_Value() {
    int[] arr = {0, 2, 1, 4};
    
    int large = Integer.MIN_VALUE;
    int large2 = Integer.MIN_VALUE;
    int small = Integer.MAX_VALUE;
    int small2 = Integer.MAX_VALUE;
    
    for (int k : arr) {
        // Largest two
        if (k > large) {
            large2 = large;
            large = k;
        } else if (k > large2) {
            large2 = k;
        }
        
        // Smallest two
        if (k < small) {
            small2 = small;
            small = k;
        } else if (k < small2) {
            small2 = k;
        }
    }
    
    System.out.println("Largest: " + large);
    System.out.println("Second Largest: " + large2);
    System.out.println("Smallest: " + small);
    System.out.println("Second Smallest: " + small2);
}
```

### Kadane's Algorithm (Maximum Subarray Sum)

```java
public void maximumSubarraySum_Kadane() {
    int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    
    int maxSoFar = arr[0];
    int maxEndingHere = arr[0];
    
    for (int i = 1; i < arr.length; i++) {
        maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    
    System.out.println(maxSoFar);  // 6 (subarray: [4, -1, 2, 1])
}
```

### Equilibrium Element (Left Sum = Right Sum)

```java
public void getMiddleValueFromLetAndRightSum() {
    ArrayList<Integer> list = new ArrayList(Arrays.asList(2, 4, 3, 6, 0));
    
    int totalSum = 0;
    int leftSum = 0;
    
    for (int num : list) {
        totalSum += num;
    }
    
    for (int i = 0; i < list.size(); i++) {
        totalSum = totalSum - list.get(i);  // Right sum
        if (leftSum == totalSum) {
            System.out.println("Equilibrium element: " + list.get(i));  // 3
            return;
        }
        leftSum = leftSum + list.get(i);
    }
}
```

### Integer Operations (Interview_Code.java)

### Reverse Number

```java
public static void ReverseNumber() {
    int no = 123423;
    int reverse = 0;
    
    while (no != 0) {
        int reminder = no % 10;
        reverse = reverse * 10 + reminder;
        no = no / 10;
    }
    System.out.println(reverse);  // 324321
}
```

### Remove Special Characters

```java
public static void removeSpecialChar() {
    String str = "Cha&%i@%tany#@a";
    String s = str.replaceAll("[^a-zA-Z]", "");
    System.out.println(s);  // "Chaitanya"
}
```

### Print Largest and Second Largest

```java
public static void printLargest() {
    int num[] = {900, 90, 6, 7, 5000, 4, 60000, 20, 3};
    int largest = num[0];
    int secondLargest = num[1];
    
    for (int i = 1; i < num.length; i++) {
        if (largest < num[i]) {
            secondLargest = largest;
            largest = num[i];
        } else if (secondLargest < num[i]) {
            secondLargest = num[i];
        }
    }
    System.out.println("Largest: " + largest);        // 60000
    System.out.println("Second Largest: " + secondLargest);  // 5000
}
```

### Get Integer Values from String

```java
@Test
public void getIntValueFromString() {
    String s = "java3selenium5sql6!@#%";
    char[] ch = s.toCharArray();
    int a = 0;
    String s1 = "";  // Letters
    String s2 = "";  // Special characters
    
    for (int i = 0; i < ch.length; i++) {
        if (Character.isDigit(ch[i])) {
            a = a + Character.getNumericValue(ch[i]);
        }
        if (Character.isLetter(ch[i])) {
            s1 = s1 + ch[i];
        }
        if (!(Character.isLetter(ch[i])) && !(Character.isDigit(ch[i]))) {
            s2 = s2 + ch[i];
        }
    }
    System.out.println(a);   // 14 (3+5+6)
    System.out.println(s1);  // "javaseleniumsql"
    System.out.println(s2);  // "!@#%"
}
```

---

## 17. BUILD TOOLS & DEPENDENCY MANAGEMENT

### Maven vs Gradle

**Apache Maven:**
- Dependency management and build automation tool for Java applications
- Uses XML files for configuration (pom.xml)
- Convention over configuration approach
- Widely used, mature ecosystem
- Declarative build process

**Gradle:**
- Built upon concepts of Ant and Maven
- Does not use XML files - uses Groovy or Kotlin DSL
- Allows writing build scripts with programming language
- High performance and scalable builds
- Easier integration process
- Supports multi-project structure
- Easy to migrate from Maven

**Key Benefits of Gradle:**
- Write build scripts with Java programming language
- Easy to use and maintain
- Supports dependency management
- Provides high performance
- Supports multi-project structure

**When to use Maven:**
- Standard Java projects
- Need for mature ecosystem
- Team already familiar with Maven
- Simple dependency management

**When to use Gradle:**
- Complex build requirements
- Need for custom build logic
- Multi-module projects
- Performance critical builds

---

## 18. TESTING CONCEPTS & METHODOLOGIES

### Verification vs Validation

| Aspect | Verification | Validation |
|--------|-------------|------------|
| **Type** | Static Testing | Dynamic Testing |
| **Code Execution** | Does not require code execution | Requires code execution |
| **What it checks** | Documents, languages, designs, programming things | Actual product |
| **Who performs** | Humans (reviewing documents) | Computer (executing program) |
| **Goal** | Are we building the product right? | Are we building the right product? |

**Example:**
- **Verification:** Reviewing requirements document, code review, design review
- **Validation:** Running test cases, user acceptance testing

### Authentication vs Authorization

**Authentication:**
- Process of verifying who a user is
- Checks user identity
- Done before authorization
- Needs user login details (username, password, face recognition, retina scan, fingerprints)
- Determines whether the person is a user or not
- Transmits information through ID Token
- Visible at user end
- Protocol: OpenID Connect (OIDC)

**Authorization:**
- Process of verifying what they have access to
- Checks user permissions
- Done after authentication
- Needs user privilege or security levels
- Determines what permission the user has
- Transmits information through Access Token
- Not visible at user end
- Protocol: OAuth 2.0

**Example:**
- **Authentication:** Employees authenticate through network before accessing company email
- **Authorization:** After authentication, system determines what information employees can access

**Popular Authentication Techniques:**
- Password-Based Authentication
- Passwordless Authentication
- 2FA/MFA (Two-Factor/Multi-Factor Authentication)
- Single Sign-On (SSO)
- Social Authentication

**Popular Authorization Techniques:**
- Role-Based Access Controls (RBAC)
- JSON Web Token (JWT) Authorization
- SAML Authorization
- OpenID Authorization
- OAuth 2.0 Authorization

### Functional vs Non-Functional Testing

**Functional Testing:**
- Ensures functions and features work properly
- Tests what the system does
- Based on requirements

**Types:**
- Unit testing
- Component testing
- Smoke testing
- Sanity testing
- Regression testing
- Integration testing
- API testing
- UI testing

**Non-Functional Testing:**
- Examines how well the application works
- Tests how the system performs
- Based on performance attributes

**Types:**
- Performance testing
- Load testing
- Stress testing
- Security testing
- Usability testing
- Compatibility testing
- Reliability testing

### Smoke vs Sanity Testing

| Aspect | Smoke Testing | Sanity Testing |
|--------|---------------|---------------|
| **Goal** | Verify "stability" | Verify "rationality" |
| **Performed by** | Developers or testers | Testers only |
| **What it tests** | Critical functionalities | New functionality/bug fixes |
| **Type** | Subset of acceptance testing | Subset of regression testing |
| **Documentation** | Documented/scripted | Not documented |
| **Scope** | Entire system end-to-end | Particular component only |
| **When** | New build deployment | After bug fixes |

**Smoke Testing Example:**
- Verify login works
- Verify main navigation works
- Verify database connection
- Verify critical user flows

**Sanity Testing Example:**
- Verify specific bug fix works
- Verify new feature works
- Verify related functionality not broken

### Testing Types Explained

**Unit Testing:**
- Individual units or components tested in isolation
- Performed by developers
- Fast execution
- Tests smallest testable parts

**Integration Testing:**
- Tests how different components work together
- Performed after unit testing
- Tests interfaces between components
- Can be top-down or bottom-up approach

**End-to-End Testing:**
- Tests application flow from start to end
- Simulates real user scenarios
- Validates entire system
- Confirms data integrity between components

**Grey Box Testing:**
- Combination of White Box and Black Box testing
- Testers have access to design documents
- Partial knowledge of internal structure
- Creates better test cases

**Acceptance Testing:**
- Performed by end-user or client
- Verifies software meets requirements
- Done before moving to production
- User acceptance criteria validation

**Client-Side Validation:**
- Validation done at browser level
- User input validated without server involvement
- Provides immediate feedback
- Faster response time

### Performance Testing Types

**Load Testing:**
- Testing under heavy but expected load
- Large volume of users, messages, requests, data
- Verifies system handles normal peak load
- Example: 1000 concurrent users on e-commerce site

**Stress Testing:**
- Testing beyond normal load range
- Load raised or accelerated beyond normal
- Finds breaking point
- Example: 10,000 concurrent users on site designed for 1000

**Volume Testing:**
- Checks if system can handle required amounts of data
- Tests with large data volumes
- Verifies database performance
- Example: Testing with 1 million records in database

### Application Architectures

**Standalone Application:**
- One-tier architecture
- Presentation, Business, and Database layers in one system
- Single user
- Example: Desktop calculator

**Client-Server Application:**
- Two-tier architecture
- Presentation and Business layer on client system
- Database layer on server
- Works mainly in Intranet
- Example: Banking application on local network

**Web Application:**
- Three-tier or n-tier architecture
- Presentation layer on client (browser)
- Business layer on application server
- Database layer on database server
- Internet-based
- Example: Gmail, Facebook

### Build vs Release

**Build:**
- Software given to testing team by development team
- Internal version
- May have known issues
- Not customer-facing
- Example: Build v1.0.5 for QA testing

**Release:**
- Software handed over to customer by tester/developer
- Customer-facing version
- Should be stable
- Production-ready
- Example: Release v1.0 for production deployment

### Bug Leakage vs Bug Release

**Bug Release:**
- Software handed to testing team knowing defect is present
- Priority and severity of bug is low
- Can be removed before final handover
- Deliberate decision
- Example: Minor UI issue not blocking release

**Bug Leakage:**
- Bug discovered by end users or customers
- Not detected by testing team during testing
- Unintentional
- Indicates testing gap
- Example: Critical bug found by customer after release

### Cross-Browser Testing

**What it is:**
- Testing web application on multiple browsers
- Ensures consistent user experience
- Tests on different operating systems

**Popular Browsers:**
- Google Chrome
- Mozilla Firefox
- Internet Explorer/Edge
- Safari
- Opera

**Why Important:**
- Users don't know which browser they'll use
- Different rendering engines
- CSS/JavaScript compatibility issues
- Ensures broader user base

**Tools:**
- BrowserStack
- Sauce Labs
- CrossBrowserTesting
- Selenium Grid

### CAPTCHA Testing

**Can it be automated?**
- **NO** - By definition, CAPTCHA cannot be automated
- That's the goal behind any good CAPTCHA strategy
- If a computer could automate it, it's not a good challenge

**Testing Approach:**
- Test with CAPTCHA disabled in test environment
- Mock CAPTCHA service
- Use test accounts that bypass CAPTCHA
- Manual verification only

### Test Environment

**What it is:**
- Computer or server where tester tests software
- Mirrors production environment
- Has all dependencies installed
- Real-world scenario testing

**Types:**
- Development Environment
- QA/Staging Environment
- Production Environment

**Importance:**
- Catches environment-specific issues
- Reduces production bugs
- Safe testing space
- Configuration validation

### Use Case Documentation

**What it includes:**
- Revision history
- Table of contents
- Flow of events
- Cover page
- Special requirements
- Pre-conditions
- Post-conditions

**Purpose:**
- Describes user action and system response
- For particular functionality
- Business requirements documentation
- Development and testing reference

---

## 19. ADVANCED SELENIUM CONCEPTS

### Selenese

Selenese is the set of Selenium commands used to test web applications with Selenium IDE.

**Types of Selenese Commands:**

**1. Actions:**
- Used for performing operations
- Generates events (click, select, type)
- If action fails, execution stops
- Example: click, type, select

**2. Accessors:**
- Used to store values in variables
- Examine application state
- Store results in variables
- Example: storeText, storeValue

**3. Assertions:**
- Used as checkpoints
- Verify application state
- Three modes: assert, verify, waitFor
- Example: assertText, verifyElementPresent

### Breakpoints and Start Points

**Breakpoints:**
- Used to stall execution of test
- Execution stops at breakpoint
- Helps verify code works properly
- Debugging tool
- Can set multiple breakpoints

**Start Points:**
- Points from where execution should begin
- Run test from middle of code
- Used after breakpoints
- Skip initial steps
- Useful for debugging specific sections

### setSpeed() vs sleep()

**setSpeed():**
- Sets execution speed with delay in milliseconds
- Delay followed by Selenium operation
- Default delay is 0 milliseconds
- Affects entire script
- Example: `selenium.setSpeed("1000")`

**sleep():**
- Suspends current thread execution
- For specified period
- Static wait time
- Used for specific wait
- Example: `Thread.sleep(5000)`

**Difference:**
- `setSpeed()` affects all subsequent commands
- `sleep()` only affects current thread
- `setSpeed()` is Selenium RC specific
- `sleep()` is Java method

### StaleElementReferenceException

**What it is:**
- Exception when web element detached from current DOM
- Element no longer present in web page
- Different from ElementNotVisibleException

**Root Causes:**
- JavaScript or JS library deleted element
- Element replaced with same ID/attributes
- Navigation to another page
- DOM has refreshed
- Frame or window switch

**Example Scenario:**
```java
WebElement firstName = driver.findElement(By.id("firstname"));
driver.switchTo().window(Child_Window);
firstName.sendKeys("Aaron"); // Throws StaleElementReferenceException
```

**Handling Strategies:**

**1. Use Dynamic XPath:**
```java
try {
    driver.findElement(By.xpath("//*[contains(@id,'firstname')]")).sendKeys("Aaron");
} catch (StaleElementReferenceException e) {
    // Handle exception
}
```

**2. Re-find Element:**
```java
WebElement element = driver.findElement(By.id("username"));
// DOM refreshes
element = driver.findElement(By.id("username")); // Re-find
element.sendKeys("text");
```

**3. Use Explicit Wait:**
```java
WebElement element = new WebDriverWait(driver, 10)
    .until(ExpectedConditions.refreshed(
        ExpectedConditions.stalenessOf(oldElement)
    ));
```

**4. Confirm Correct Window:**
```java
// Ensure we're in correct window before acting
if (driver.getWindowHandle().equals(expectedWindow)) {
    element.sendKeys("text");
}
```

### Cookies in Selenium

**Add Cookie:**
```java
driver.manage().addCookie(new Cookie("foo", "bar"));
```

**Get Cookie by Name:**
```java
Cookie cookie = driver.manage().getCookieNamed("foo");
System.out.println(cookie.getValue());
```

**Get All Cookies:**
```java
Set<Cookie> cookies = driver.manage().getCookies();
for (Cookie cookie : cookies) {
    System.out.println(cookie.getName() + " = " + cookie.getValue());
}
```

**Delete Cookie by Name:**
```java
driver.manage().deleteCookieNamed("test1");
```

**Delete Cookie by Object:**
```java
Cookie cookie = driver.manage().getCookieNamed("foo");
driver.manage().deleteCookie(cookie);
```

**Delete All Cookies:**
```java
driver.manage().deleteAllCookies();
```

**Use Cases:**
- Test authentication scenarios
- Test session management
- Test cookie-based features
- Clear cookies between tests
- Test cookie expiration

### Text Without sendKeys()

**Using JavaScriptExecutor:**
```java
JavascriptExecutor jse = (JavascriptExecutor) driver;
jse.executeScript("document.getElementById('Login').value='Test text without sendkeys'");
```

**When to use:**
- sendKeys() not working
- Element not interactable
- Need to set value directly
- Testing JavaScript functionality

### Authentication Popup Handling

**Basic Authentication:**
```java
WebDriverWait wait = new WebDriverWait(driver, 10);
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
alert.authenticateUsing(new UserAndPassword("username", "password"));
```

**URL-based Authentication:**
```java
String url = "http://username:password@example.com";
driver.get(url);
```

**When to use:**
- Browser authentication popup
- Basic auth required
- No UI for credentials
- Automated login scenarios

### Driver Navigation Differences

**driver.get() vs driver.navigate().to():**

| Aspect | get() | navigate().to() |
|--------|-------|-----------------|
| **Page Load** | Waits for page to load | Does not wait |
| **History** | Does not retain history | Retains browsing history |
| **Usage** | Simple navigation | Advanced navigation |

**Navigation Commands:**
```java
// Back to previous page
driver.navigate().back();

// Forward to next page
driver.navigate().forward();

// Refresh current page
driver.navigate().refresh();

// Navigate to URL
driver.navigate().to("https://example.com");
```

### findElement() vs findElements()

**findElement():**
- Finds first matching element
- Returns single WebElement
- Throws NoSuchElementException if not found
- Used when expecting single element

```java
WebElement element = driver.findElement(By.id("username"));
```

**findElements():**
- Finds all matching elements
- Returns List<WebElement>
- Returns empty list if not found (no exception)
- Used when expecting multiple elements

```java
List<WebElement> elements = driver.findElements(By.className("button"));
```

### close() vs quit()

**close():**
- Closes current browser window
- Does not close other windows
- WebDriver session continues
- Returns void

```java
driver.close();
```

**quit():**
- Closes all browser windows
- Ends WebDriver session
- Releases driver resources
- Returns void

```java
driver.quit();
```

**When to use:**
- `close()`: Multiple windows, want to close one
- `quit()`: End of test, cleanup

### getWindowHandle() vs getWindowHandles()

**getWindowHandle():**
- Returns handle of current window
- Return type: String
- Single window handle
- Used for current window operations

```java
String currentWindow = driver.getWindowHandle();
```

**getWindowHandles():**
- Returns handles of all open windows
- Return type: Set<String>
- Multiple window handles
- Used for window switching

```java
Set<String> allWindows = driver.getWindowHandles();
```

---

## 20. BUG REPORTING & RISK MANAGEMENT

### Bug Report Fields

**Essential Fields:**
1. **Unique ID** - Auto-generated identifier
2. **Defect Description** - Short description of what the bug is
3. **Steps to Reproduce** - Detailed steps to arrive at error
4. **Test Data** - Exact test data used
5. **Time Found** - When defect was found (if applicable)
6. **Environment** - Information to re-encounter issue
7. **Module/Section** - Application section where bug found
8. **Severity** - Impact on functionality
9. **Priority** - How fast to fix
10. **Screenshot** - Visual evidence
11. **Responsible QA** - For follow-up questions
12. **Expected Result** - What should happen
13. **Actual Result** - What actually happened

### Severity vs Priority

**Severity:**
- How defect affects functionality
- Related to quality standard
- Technical impact
- Categories: Critical, High, Medium, Low

**Priority:**
- How fast defect has to be fixed
- Related to scheduling
- Business impact
- Categories: P1, P2, P3, P4

**Example:**
- Company name misspelled on home page
  - **Severity:** Low (doesn't affect functionality)
  - **Priority:** High (customer-facing, brand image)

- System crash on login
  - **Severity:** Critical (blocks functionality)
  - **Priority:** P1 (must fix immediately)

### Risk Management in Test Plan

**Common Risks:**

**1. Timelines**
- Most common risk
- Downtime or requirement changes impact timelines
- Mitigation: Add buffer time, prioritize testing

**2. Resourcing**
- Skilled employees availability
- Required hardware/software
- Mitigation: Cross-training, backup resources

**3. Scope**
- Scope changes during project
- Need analysis and assessment
- Mitigation: Change control process, impact analysis

**4. Third-Party Dependencies**
- Availability of third-party systems
- Team availability
- Mitigation: Mock services, SLAs, backup plans

**5. Environment**
- Environmental downtime
- Configuration issues
- Mitigation: Multiple environments, environment monitoring

### Handling Timeline Crunch

**Strategies:**

**1. Planning Phase:**
- Consider buffer for usual delays
- Realistic estimation
- Risk assessment

**2. Execution Phase:**
- Extended and weekend working hours
- Prioritize test efforts
- Communicate to stakeholders
- Test as much as possible in given timeline

**3. Communication:**
- Highlight delays to buy extra time
- Convey situation as-is to stakeholders
- Ask for time to complete testing

**4. Post-Testing:**
- Retrospective look to avoid delays next time
- Document lessons learned
- Improve estimation process

**5. Efficient Use:**
- Plan and make efficient use of working hours
- Focus on critical paths
- Use automation where possible

---

## 21. AGILE METHODOLOGY

### What is Agile Testing?

Agile testing is a software practice started from the beginning of the project with continuous integration between development and testing, unlike the waterfall method.

**Characteristics:**
- Continuous development methodology
- Requirements evolve between customer and self-organizing teams
- Testing integrated with development
- Short iterations (sprints)

### Benefits of Agile Testing

- Less documentation required
- Issues determined at earlier stage through daily meetings
- Saves time and money
- Regular feedback from end-users
- Faster time to market
- Adaptability to changes

### Basic Fundamentals of Agile Testing

**1. Test Documentation Reduced:**
- Reduces length of documentation
- Testers focus on testing rather than details
- Just enough documentation

**2. Not a Phase:**
- Team performs testing continuously
- Continuous testing provides continuous progress
- Testing throughout development

**3. Implementation:**
- Performed while implementation
- Unlike other methods (after implementation)
- Shift-left testing

**4. Fixing Defects:**
- Defects raised during iteration fixed in same iteration
- Keeps code clean
- Reduces defect accumulation

### Agile Testing Strategies

**Four Stages:**

**1. Iteration 0:**
- Identify people for testing
- Install testing tools
- Setup environment
- Define test strategy

**2. Construction Iterations:**
- Most testing performed here
- Continuous integration
- Automated regression
- Feature testing

**3. Transition Phase:**
- Deploy system successfully to production
- Final testing
- User acceptance
- Production readiness

**4. Production Phase:**
- Product moved to production
- Monitoring
- Support
- Feedback collection

### Automation in Agile

**When Automation is Useful:**
- Regression testing
- Smoke testing
- Sanity testing
- Continuous integration

**Why Important in Agile:**
- Every iteration requires regression testing
- New functionality added each sprint
- Regression suite grows after each sprint
- Functional test cases of current sprint added to regression suite
- Achieves maximum test coverage in less time

**When Not Useful:**
- Requirements always changing
- Exhaustive documentation required
- Only suitable for regression tests

### Manual vs Automation in Agile

**Prefer Manual When:**
- Project is short-term
- Flexibility required
- Usability testing
- Newly developed applications
- Ad-hoc or exploratory testing

**Prefer Automation When:**
- Repetitive tasks
- Smoke and sanity tests
- Multiple data sets
- Regression test cases
- Time constraints

---

## 22a.
    Dimension	   | Test Strategy	                                  |       Test Plan
    Scope	           | High-level, organization/project-wide	          | Specific to a particular project/release
    Purpose	           | Defines the overall testing approach	          | Details what to test, how, and when
    Audience	   | Stakeholders, project managers, QA leads	          | Testers, QA team, developers
    Level of detail    | Broad principles, objectives, and techniques         | Granular steps, resources, schedules, and responsibilities
    Stability	   | Long-term; rarely changes per project	          | Short-term; created per release/iteration
    Content	           | Testing objectives, scope, tools, 	                  | Test cases, schedules, deliverables, roles, 
                       | risk approach, environments, entry/exit criteria     | dependencies, and pass/fail criteria

## 22. TEST STRATEGY COMPONENTS

### What Test Strategy Includes

**1. Introduction**
- Purpose of testing
- Scope overview
- Objectives

**2. Resources**
- Team structure
- Skills required
- Training needs
- Infrastructure

**3. Scope and Schedule**
- Test activities scope
- Timeline
- Milestones
- Dependencies

**4. Test Tools**
- Automation tools
- Test management tools
- Defect tracking tools
- CI/CD tools

**5. Test Priorities**
- Critical path testing
- Risk-based testing
- Business impact assessment

**6. Test Planning**
- Test types to perform
- Entry and exit criteria
- Test approach
- Test environment

**7. Types of Tests**
- Functional testing
- Non-functional testing
- Regression testing
- Integration testing

### Automation Test Plan Strategy

**1. Preparation of Automation Test Plan**
- Define scope
- Identify tools
- Resource planning
- Timeline estimation

**2. Recording the Scenario**
- Capture test scenarios
- Document test cases
- Identify test data

**3. Error Handler Incorporation**
- Exception handling
- Recovery scenarios
- Logging mechanisms

**4. Script Enhancement**
- Insert checkpoints
- Add looping constructs
- Parameterization
- Data-driven approach

**5. Debugging the Script**
- Identify issues
- Fix bugs
- Validate fixes

**6. Rerunning the Script**
- Verify fixes
- Regression testing
- Stability testing

**7. Reporting the Result**
- Generate reports
- Analyze results
- Communicate findings

---

## 23. BEST PRACTICES & DESIGN PATTERNS

### Selenium Best Practices

**1. Use Explicit Waits, Not Thread.sleep()**
```java
// Bad
Thread.sleep(5000);

// Good
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOf(element));
```

**2. Use Page Object Model**
- Separate locators from test logic
- Reusable page classes
- Easier maintenance

**3. Use Descriptive Test Names**
```java
// Bad
@Test
public void test1() {}

// Good
@Test
public void verifyUserLoginWithValidCredentials() {}
```

**4. Handle Exceptions Properly**
```java
try {
    element.click();
} catch (ElementClickInterceptedException e) {
    // Handle overlay
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
}
```

**5. Clean Up After Tests**
```java
@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

**6. Use CSS Selectors Over XPath**
- CSS is faster
- More readable
- Better browser support

**7. Avoid Hard-Coded Values**
```java
// Bad
driver.get("https://example.com");
driver.findElement(By.id("username")).sendKeys("testuser");

// Good
String url = config.getProperty("base.url");
String username = config.getProperty("test.username");
driver.get(url);
driver.findElement(By.id("username")).sendKeys(username);
```

**8. Use Assertions Properly**
```java
// Bad
if (element.isDisplayed()) {
    System.out.println("Test passed");
}

// Good
Assert.assertTrue(element.isDisplayed(), "Element should be displayed");
```

### Design Patterns

**1. Page Object Model (POM)**
- Each page is a class
- Elements as fields
- Methods for interactions

**2. Factory Pattern**
```java
public class WebDriverFactory {
    public static WebDriver getDriver(BrowserType type) {
        switch (type) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException();
        }
    }
}
```

**3. Singleton Pattern**
```java
public class DriverManager {
    private static WebDriver driver;
    
    private DriverManager() {}
    
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
```

**4. Builder Pattern**
```java
public class TestDataBuilder {
    private String username;
    private String password;
    private String email;
    
    public TestDataBuilder withUsername(String username) {
        this.username = username;
        return this;
    }
    
    public TestDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
    
    public TestData build() {
        return new TestData(username, password, email);
    }
}
```

### Test Organization

**1. Package Structure**
```
src/test/java/
├── base/
│   ├── BaseTest.java
│   └── DriverFactory.java
├── pages/
│   ├── LoginPage.java
│   ├── HomePage.java
│   └── DashboardPage.java
├── tests/
│   ├── LoginTest.java
│   └── DashboardTest.java
├── utils/
│   ├── ConfigReader.java
│   └── ScreenshotUtil.java
└── listeners/
    └── TestListener.java
```

**2. Naming Conventions**
- Test classes: `*Test.java`
- Page classes: `*Page.java`
- Utility classes: `*Util.java`
- Methods: `verbNoun()` (e.g., `clickLoginButton()`)

### CI/CD Integration

**Jenkins Pipeline**
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report'
                ])
            }
        }
    }
}
```

### Performance Tips

**1. Use Headless Mode for CI/CD**
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
```

**2. Parallel Execution**
- Run tests in parallel
- Use appropriate thread count
- Ensure thread safety

**3. Reuse Browser Sessions**
- Use same browser for multiple tests
- Close only at suite end
- Faster execution

**4. Optimize Locators**
- Use ID when available
- Avoid complex XPath
- Use relative XPath

---

## 18. ADVANCED JAVA TOPICS FOR SENIOR QA (InterviewCode_WRT_Advanced.java)

### Multithreading - Essential for QA Lead

### Create Thread Using Runnable
```java
@Test
public void createThreadUsingRunnable() throws InterruptedException {
    Thread thread = new Thread(new MyRunnable());
    thread.start();
    thread.join();
}

static class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread is running");
    }
}
```

### Synchronization Example
```java
@Test
public void synchronizationExample() throws InterruptedException {
    Counter counter = new Counter();
    
    Thread t1 = new Thread(() -> {
        for (int i = 0; i < 1000; i++) counter.increment();
    });
    
    Thread t2 = new Thread(() -> {
        for (int i = 0; i < 1000; i++) counter.increment();
    });
    
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    
    System.out.println("Counter: " + counter.getCount());  // 2000
}

static class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
    
    public int getCount() { return count; }
}
```

**Why synchronized?** Without it, both threads could read and increment count simultaneously, causing race condition and incorrect count.

### Producer-Consumer Problem
```java
@Test
public void producerConsumerProblem() throws InterruptedException {
    Buffer buffer = new Buffer();
    
    Thread producer = new Thread(() -> {
        try {
            for (int i = 0; i < 5; i++) {
                buffer.produce(i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    });
    
    Thread consumer = new Thread(() -> {
        try {
            for (int i = 0; i < 5; i++) {
                buffer.consume();
                Thread.sleep(150);
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    });
    
    producer.start();
    consumer.start();
    producer.join();
    consumer.join();
}

static class Buffer {
    private int data;
    private boolean empty = true;
    
    public synchronized void produce(int value) throws InterruptedException {
        while (!empty) wait();
        data = value;
        empty = false;
        System.out.println("Produced: " + data);
        notifyAll();
    }
    
    public synchronized void consume() throws InterruptedException {
        while (empty) wait();
        System.out.println("Consumed: " + data);
        empty = true;
        notifyAll();
    }
}
```

### ThreadLocal Usage
```java
@Test
public void threadLocalUsage() {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    
    Thread t1 = new Thread(() -> {
        threadLocal.set("Thread-1 value");
        System.out.println("Thread 1: " + threadLocal.get());
    });
    
    Thread t2 = new Thread(() -> {
        threadLocal.set("Thread-2 value");
        System.out.println("Thread 2: " + threadLocal.get());
    });
    
    t1.start();
    t2.start();
    // Each thread gets its own copy of the variable
}
```

**QA Lead Relevance:** ThreadLocal is used in test automation for parallel execution - each thread gets its own WebDriver instance.

### ExecutorService Example
```java
@Test
public void executorServiceExample() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(3);
    
    for (int i = 0; i < 5; i++) {
        final int taskNum = i;
        executor.submit(() -> {
            System.out.println("Task " + taskNum + " executed by " + 
                Thread.currentThread().getName());
        });
    }
    
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.SECONDS);
}
```

**QA Lead Relevance:** ExecutorService manages thread pools efficiently - useful for running multiple test suites in parallel.

### Design Patterns - Senior Level Expectation

### Singleton Pattern (Thread-Safe)
```java
static class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

**QA Lead Relevance:** Singleton used for WebDriver manager, configuration reader, database connection pool.

### Immutable Class
```java
static final class ImmutablePerson {
    private final String name;
    private final int age;
    
    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
}
```

**Benefits:** Thread-safe, no side effects, ideal for test data objects.

### Factory Design Pattern
```java
interface Shape {
    void draw();
}

static class Circle implements Shape {
    public void draw() { System.out.println("Drawing Circle"); }
}

static class Rectangle implements Shape {
    public void draw() { System.out.println("Drawing Rectangle"); }
}

static class ShapeFactory {
    public static Shape getShape(String shapeType) {
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        return null;
    }
}
```

**QA Lead Relevance:** Factory pattern used for creating different browser instances, test data objects, page objects.

### Dependency Injection Example
```java
interface Repository {
    void save(String data);
}

static class DatabaseRepository implements Repository {
    public void save(String data) {
        System.out.println("Saving to database: " + data);
    }
}

static class Service {
    private final Repository repository;
    
    public Service(Repository repository) {
        this.repository = repository;
    }
    
    public void saveData(String data) {
        repository.save(data);
    }
}
```

**QA Lead Relevance:** DI makes code testable, loosely coupled - essential for maintainable test frameworks.

### Advanced Stream Operations

### Flatten Nested List
```java
@Test
public void flattenNestedList() {
    List<List<Integer>> nested = Arrays.asList(
        Arrays.asList(1, 2),
        Arrays.asList(3, 4),
        Arrays.asList(5, 6)
    );
    
    List<Integer> flattened = nested.stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
    
    System.out.println("Flattened: " + flattened);  // [1, 2, 3, 4, 5, 6]
}
```

### Remove Null Values
```java
@Test
public void removeNullValues() {
    List<String> list = Arrays.asList("apple", null, "banana", null, "cherry");
    
    List<String> withoutNulls = list.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    
    System.out.println("Without nulls: " + withoutNulls);  // [apple, banana, cherry]
}
```

### Parallel Stream Example
```java
@Test
public void parallelStreamExample() {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    
    long start = System.currentTimeMillis();
    List<Integer> evenNumbers = list.parallelStream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());
    long end = System.currentTimeMillis();
    
    System.out.println("Even numbers (parallel): " + evenNumbers);
    System.out.println("Time taken: " + (end - start) + "ms");
}
```

**QA Lead Relevance:** Parallel streams speed up data processing for large test datasets.

### Thread-Safe Collections
```java
@Test
public void threadSafeCollectionExample() {
    List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    CopyOnWriteArrayList<Integer> copyOnWriteList = new CopyOnWriteArrayList<>();
    
    synchronizedList.add(1);
    copyOnWriteList.add(3);
    
    System.out.println("Synchronized List: " + synchronizedList);
    System.out.println("CopyOnWriteArrayList: " + copyOnWriteList);
}
```

**QA Lead Relevance:** Thread-safe collections essential for parallel test execution and shared test data.

---

## 19. SELENIUM 4 DEVTOOLS & NETWORK LOGGING

### DevTools for Network Monitoring (Selenium_Questions.java)

Selenium 4 introduced DevTools API to capture network traffic, performance metrics, and browser console logs.

### Enable DevTools Session
```java
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.chrome.ChromeDriver;

public void get_Network_log() {
    ChromeDriver driver = new ChromeDriver();
    DevTools devTools = driver.getDevTools();
    devTools.createSession();
    
    // Now you can monitor network requests, performance, etc.
}
```

### Capture Network Requests
```java
DevTools devTools = driver.getDevTools();
devTools.createSession();

devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

devTools.addListener(Network.responseReceived(), response -> {
    System.out.println("URL: " + response.getResponse().getUrl());
    System.out.println("Status: " + response.getResponse().getStatus());
    System.out.println("Type: " + response.getResponse().getMimeType());
});

driver.get("https://example.com");
```

### Capture Console Logs
```java
devTools.send(Log.enable());
devTools.addListener(Log.entryAdded(), entry -> {
    System.out.println("Level: " + entry.getLevel());
    System.out.println("Text: " + entry.getText());
});
```

### Performance Monitoring
```java
devTools.send(Performance.enable());
devTools.addListener(Performance.metrics(), metrics -> {
    metrics.getMetrics().forEach(metric -> {
        System.out.println(metric.getName() + ": " + metric.getValue());
    });
});
```

### Broken Links Detection (BrokenLinks.java)

```java
public class BrokenLinks {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/broken");
        
        // Store all links
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("No of links are " + links.size());
        
        // Check each link
        for (int i = 0; i < links.size(); i++) {
            WebElement E1 = links.get(i);
            String url = E1.getAttribute("href");
            verifyLinks(url);
        }
        
        driver.quit();
    }
    
    public static void verifyLinks(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            
            if (httpURLConnect.getResponseCode() >= 400) {
                System.out.println(linkUrl + " - " + 
                    httpURLConnect.getResponseMessage() + " is a broken link");
            } else {
                System.out.println(linkUrl + " - " + 
                    httpURLConnect.getResponseMessage());
            }
        } catch (Exception e) {
            System.out.println(linkUrl + " - Exception occurred");
        }
    }
}
```

**QA Lead Relevance:** Broken link detection is important for regression testing and ensuring site quality.

---

## 20. QA LEAD - STRATEGIC & LEADERSHIP TOPICS

### Test Strategy & Planning

### What is Test Strategy?
A test strategy is a high-level document that defines the testing approach, resources, and timeline for a project.

### Key Components of Test Strategy

**1. Scope of Testing**
- What to test (in-scope)
- What not to test (out-of-scope)
- Test types (functional, regression, performance, security)

**2. Test Environment**
- Number of environments (dev, staging, prod-like)
- Environment setup and maintenance
- Data requirements

**3. Test Automation Approach**
- Tool selection (Selenium, Cypress, Playwright)
- Framework design (POM, Data-Driven, Hybrid)
- CI/CD integration

**4. Resource Planning**
- Team size and skills
- Training requirements
- Infrastructure needs

**5. Risk Management**
- Identify risks (timeline, resources, technology)
- Mitigation strategies
- Contingency plans

**6. Schedule & Milestones**
- Sprint-wise testing plan
- Release criteria
- Sign-off process

### Test Metrics & Reporting

### Key Test Metrics

**1. Execution Metrics**
- Total tests executed
- Tests passed/failed/skipped
- Execution time
- Test coverage percentage

**2. Defect Metrics**
- Defect density (defects per test case)
- Defect leakage (bugs found in production)
- Defect rejection rate
- Mean Time to Detect (MTTD)
- Mean Time to Resolve (MTTR)

**3. Automation Metrics**
- Automation coverage percentage
- Automation ROI (time saved vs effort invested)
- Flaky test rate
- Maintenance effort

**4. Trend Metrics**
- Defect trend over time
- Test execution trend
- Automation progress trend

### Risk Management

### Risk Identification

**Common Testing Risks:**
1. **Timeline Risks** - Delays in development, scope creep
2. **Resource Risks** - Team turnover, skill gaps
3. **Technical Risks** - Tool limitations, environment instability
4. **Integration Risks** - Third-party API changes, data inconsistencies
5. **Coverage Risks** - Incomplete test coverage, edge cases missed

### Risk Mitigation Strategies

**1. Timeline Risks**
- Prioritize critical test cases
- Use risk-based testing
- Plan buffer time

**2. Resource Risks**
- Cross-train team members
- Document knowledge
- Use automation to reduce manual effort

**3. Technical Risks**
- Proof of concept for new tools
- Have backup tools/approaches
- Regular environment maintenance

**4. Integration Risks**
- Mock external dependencies
- Contract testing with APIs
- Regular integration testing

### Team Management & Leadership

### Building an Effective QA Team

**1. Skill Assessment**
- Identify current skills (manual, automation, performance)
- Identify skill gaps
- Create training plans

**2. Role Definition**
- QA Engineer (manual + automation)
- SDET (software development engineer in test)
- QA Lead (strategy + management)
- Performance Engineer
- Security Tester

**3. Performance Management**
- Set clear goals and objectives
- Regular 1-on-1 meetings
- Feedback and coaching
- Career growth planning

### Code Review Best Practices for QA

**1. Review Checklist**
- Framework design and architecture
- Code readability and maintainability
- Test coverage and edge cases
- Error handling and logging
- Performance considerations
- Security best practices

**2. Review Process**
- Peer review (team members review each other's code)
- Lead review (QA lead reviews critical code)
- Automated code quality checks (SonarQube)
- Review frequency (before merging to main branch)

### Mentoring Junior Team Members

**1. Onboarding Plan**
- Framework overview and architecture
- Coding standards and best practices
- Tool training (Selenium, TestNG, CI/CD)
- Pair programming sessions
- Gradual responsibility increase

**2. Knowledge Sharing**
- Weekly tech talks
- Documentation (wiki, confluence)
- Code walkthroughs
- Bug triage sessions

### CI/CD Pipeline for QA

### CI/CD Pipeline Stages

**1. Build Stage**
- Compile code
- Run unit tests
- Code quality checks (SonarQube)

**2. Test Stage**
- Smoke tests (quick sanity check)
- Regression tests (full suite)
- Parallel execution for speed
- Generate test reports

**3. Deploy Stage**
- Deploy to staging environment
- Run integration tests
- Performance tests
- Security scans

**4. Release Stage**
- Deploy to production
- Smoke tests on production
- Monitor for issues
- Rollback plan if needed

### Test Environment Management

### Environment Strategy

**1. Development Environment**
- Used by developers for unit testing
- Quick feedback cycle
- Not stable, frequent changes

**2. QA/Staging Environment**
- Mirror of production
- Used for integration and regression testing
- Stable, controlled deployments

**3. Production Environment**
- Live environment
- Smoke tests only
- Monitoring and alerting

### Test Data Management

**1. Test Data Approaches**
- Static test data (hardcoded in tests)
- Dynamic test data (generated at runtime)
- Database snapshots (copy of production data)
- Synthetic data (generated using tools)

**2. Test Data Challenges**
- Data privacy (PII, GDPR compliance)
- Data consistency across environments
- Data setup and cleanup
- Test data versioning

**3. Best Practices**
- Use anonymized production data
- Create data factories for consistent test data
- Clean up test data after execution
- Version test data with code

### Performance Testing Basics

### Performance Test Types

**1. Load Testing**
- Test system under expected load
- Verify response times
- Identify bottlenecks

**2. Stress Testing**
- Test beyond expected load
- Find breaking point
- Test system recovery

**3. Spike Testing**
- Sudden increase in load
- Test system handling of traffic spikes

**4. Endurance Testing**
- Sustained load over time
- Test for memory leaks
- Test system stability

### Performance Metrics

**1. Response Time**
- Average response time
- 90th/95th/99th percentile
- Time to First Byte (TTFB)

**2. Throughput**
- Requests per second
- Transactions per second
- Concurrent users

**3. Resource Utilization**
- CPU usage
- Memory usage
- Disk I/O
- Network bandwidth

### Security Testing Basics

### Common Security Vulnerabilities

**1. OWASP Top 10**
- Injection (SQL, NoSQL, OS command)
- Broken Authentication
- Sensitive Data Exposure
- XML External Entities (XXE)
- Broken Access Control
- Security Misconfiguration
- Cross-Site Scripting (XSS)
- Insecure Deserialization
- Using Components with Known Vulnerabilities
- Insufficient Logging & Monitoring

**2. Security Testing Tools**
- OWASP ZAP (vulnerability scanner)
- Burp Suite (penetration testing)
- SonarQube (static code analysis)
- Dependency-Check (vulnerability scanning)

### Defect Management Process

### Defect Lifecycle

```
New → Triaged → In Progress → Ready for QA → Verified → Closed
                                    ↓
                                  Reopened
```

### Defect Reporting Best Practices

**1. Defect Template**
- Summary (clear, concise)
- Steps to reproduce
- Expected vs Actual behavior
- Severity and Priority
- Environment details
- Screenshots/videos
- Logs

**2. Severity vs Priority**
- **Severity:** Impact on system (Critical, High, Medium, Low)
- **Priority:** Urgency to fix (P1, P2, P3, P4)

**Example:**
- Severity: Critical (system crash)
- Priority: P2 (fix in next sprint, not blocking release)

### Root Cause Analysis (RCA)

**1. 5 Whys Technique**
- Ask "why" 5 times to find root cause
- Example: Bug → Why? → Why? → Why? → Why? → Root cause

**2. Fishbone Diagram**
- Identify contributing factors
- Categories: People, Process, Technology, Environment

### Interview Questions for QA Lead Position

**Strategic Questions:**
1. How do you create a test strategy for a new project?
2. How do you measure test automation ROI?
3. How do you handle tight deadlines with limited resources?
4. How do you decide what to automate vs manual test?
5. How do you handle flaky tests in CI/CD?

**Technical Questions:**
1. How do you design a scalable test automation framework?
2. How do you handle test data management?
3. How do you implement parallel execution efficiently?
4. How do you integrate testing in CI/CD pipeline?
5. How do you monitor and improve test execution time?

**Leadership Questions:**
1. How do you handle conflict in the team?
2. How do you mentor junior team members?
3. How do you convince management to invest in automation?
4. How do you handle underperforming team members?
5. How do you stay updated with latest testing trends?

**Scenario-Based Questions:**
1. Production bug found - how do you handle it?
2. Developer says "it works on my machine" - how do you respond?
3. Stakeholders want to skip testing to meet deadline - what do you do?
4. Team member is resistant to automation - how do you handle?
5. Automation framework is slow - how do you optimize?

---

## SUMMARY CHECKLIST FOR INTERVIEW

### Selenium WebDriver
- [ ] Difference between implicit, explicit, and fluent wait
- [ ] When to use JavaScriptExecutor
- [ ] How to handle alerts, frames, windows
- [ ] Actions class usage
- [ ] Cookie management
- [ ] Screenshot capture

### TestNG
- [ ] Annotations and execution order
- [ ] DataProvider implementation
- [ ] Groups and dependencies
- [ ] Listeners (ITestListener, IReporter)
- [ ] Parallel execution types
- [ ] Retry mechanism

### Page Object Model
- [ ] @FindBy annotations
- [ ] @CacheLookup usage
- [ ] PageFactory.initElements()
- [ ] Benefits of POM

### Java Programming
- [ ] String manipulation (reverse, palindrome, anagram)
- [ ] Array operations (sort, find missing, duplicates)
- [ ] HashMap operations
- [ ] Java 8 Streams (filter, map, reduce)
- [ ] Exception handling

### API Testing
- [ ] GET, POST, PUT, DELETE requests
- [ ] Serialization/deserialization
- [ ] Response validation
- [ ] Authentication methods

### Best Practices
- [ ] Explicit waits over Thread.sleep
- [ ] POM implementation
- [ ] Clean code principles
- [ ] Exception handling
- [ ] Test organization

### Advanced Java (Senior QA)
- [ ] Multithreading (Thread, Runnable, synchronization)
- [ ] ThreadLocal for parallel execution
- [ ] ExecutorService and thread pools
- [ ] Design patterns (Singleton, Factory, DI)
- [ ] Immutable classes
- [ ] Thread-safe collections

### QA Lead Strategic Topics
- [ ] Test strategy creation
- [ ] Test metrics and reporting
- [ ] Risk management
- [ ] Team management and mentoring
- [ ] CI/CD pipeline integration
- [ ] Test environment management
- [ ] Test data management
- [ ] Performance testing basics
- [ ] Security testing basics
- [ ] Defect management process

### Selenium 4 Features
- [ ] DevTools API for network monitoring
- [ ] Performance metrics capture
- [ ] Console log capture
- [ ] Broken link detection

---

## FINAL TIPS

1. **Practice explaining concepts** - Teach someone else to reinforce learning
2. **Write code from memory** - Don't just read, practice writing
3. **Understand "why" not just "how"** - Interviewers ask reasoning questions
4. **Connect concepts** - Understand how topics relate (e.g., POM + TestNG)
5. **Stay updated** - Selenium 4 features, latest TestNG versions
6. **Build a project** - Your project is excellent - be ready to explain it
7. **Be honest** - If you don't know, say you'll learn it

---

**Good luck with your interview! This guide covers everything in your project and more.**
