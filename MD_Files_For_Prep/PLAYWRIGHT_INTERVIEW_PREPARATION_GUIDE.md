# Playwright with Java - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Playwright Fundamentals
2. Playwright vs Selenium Comparison
3. Browser Setup and Initialization
4. Locators in Playwright
5. Wait Strategies in Playwright
6. Element Interactions
7. Advanced Interactions (Mouse & Keyboard)
8. Frame Handling
9. Window and Tab Handling
10. Shadow DOM Handling
11. File Upload and Download
12. Popups and Dialogs
13. Web Tables
14. Screenshots and Video Recording
15. Page Object Model (POM)
16. TestNG Framework Integration
17. Playwright Listeners
18. Reporting with ExtentReports
19. Auto Login and State Management
20. Network Interception
21. API Testing with Playwright
22. Parallel Execution
23. Java Programming for Interviews
24. Best Practices & Design Patterns
25. Common Interview Questions

---

## 1. PLAYWRIGHT FUNDAMENTALS

### What is Playwright?
Playwright is a modern end-to-end testing framework developed by Microsoft. It allows you to automate Chromium, Firefox, and WebKit browsers with a single API. It's designed for modern web applications with built-in support for:

- Cross-browser testing (Chrome, Firefox, Safari, Edge)
- Auto-waiting for elements (no explicit waits needed)
- Network interception and mocking
- Shadow DOM support
- Multi-tab and multi-context testing
- Trace viewer for debugging
- Video recording and screenshots

### Key Advantages Over Selenium
- **Auto-waiting**: No need for explicit waits, Playwright automatically waits for elements to be ready
- **Faster execution**: More efficient browser control
- **Better reliability**: Less flaky tests due to smart waiting
- **Modern web support**: Shadow DOM, network interception, etc.
- **Cross-browser**: Single API for all browsers
- **Headless execution by default**: Better for CI/CD

### Playwright Architecture
```
Playwright (Node.js)
    ↓
Browser (Chromium, Firefox, WebKit)
    ↓
BrowserContext (Isolated session)
    ↓
Page (Tab/Window)
    ↓
Locator (Element reference)
```

---

## 2. PLAYWRIGHT VS SELENIUM COMPARISON

### Feature Comparison

| Feature | Playwright | Selenium |
|---------|-----------|----------|
| Auto-waiting | ✅ Built-in | ❌ Manual waits required |
| Shadow DOM | ✅ Native support | ❌ Requires JavaScript |
| Network interception | ✅ Built-in | ❌ Requires proxy |
| Multi-tab handling | ✅ Easy API | ❌ Complex window handles |
| Speed | ⚡ Faster | 🐢 Slower |
| Cross-browser | ✅ All browsers | ✅ All browsers |
| Headless mode | ✅ Default | ❌ Manual setup |
| Video recording | ✅ Built-in | ❌ Third-party tools |
| Trace viewer | ✅ Built-in | ❌ Not available |
| Learning curve | 📈 Moderate | 📈 Easy |

### Code Comparison

**Selenium:**
```java
WebDriver driver = new ChromeDriver();
driver.get("https://example.com");
WebDriverWait wait = new WebDriverWait(driver, 10);
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
element.click();
```

**Playwright:**
```java
Playwright playwright = Playwright.create();
Browser browser = playwright.chromium().launch();
Page page = browser.newPage();
page.navigate("https://example.com");
page.locator("#submit").click(); // Auto-waits for element
```

---

## 3. BROWSER SETUP AND INITIALIZATION

### Basic Browser Setup

```java
import com.microsoft.playwright.*;

public class BaseTest {
    public Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterMethod
    public void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
```

### Launch Options

```java
// Headless mode
browser = playwright.chromium().launch(
    new BrowserType.LaunchOptions().setHeadless(true)
);

// With specific channel (Chrome, MS Edge)
browser = playwright.chromium().launch(
    new BrowserType.LaunchOptions().setChannel("chrome")
);

// Slow motion for debugging
browser = playwright.chromium().launch(
    new BrowserType.LaunchOptions().setSlowMo(100)
);

// With specific viewport
browserContext = browser.newContext(
    new Browser.NewContextOptions().setViewportSize(1920, 1080)
);

// With device emulation
browserContext = browser.newContext(
    new Browser.NewContextOptions().setDeviceScaleFactor(2)
);

// Accept downloads
browserContext = browser.newContext(
    new Browser.NewContextOptions().setAcceptDownloads(true)
);

// With locale and timezone
browserContext = browser.newContext(
    new Browser.NewContextOptions()
        .setLocale("en-US")
        .setTimezoneId("America/New_York")
);

// With geolocation
browserContext = browser.newContext(
    new Browser.NewContextOptions()
        .setGeolocation(52.52, 13.39)
        .setPermissions(Arrays.asList("geolocation"))
);

// With storage state (cookies, localStorage)
browserContext = browser.newContext(
    new Browser.NewContextOptions()
        .setStorageStatePath(Paths.get("state.json"))
);
```

### BrowserContext vs Page

**BrowserContext:**
- Incognito-like session
- Isolated cookies, localStorage, sessionStorage
- Can have multiple pages
- Useful for testing multi-user scenarios

```java
BrowserContext context1 = browser.newContext();
Page page1 = context1.newPage();
Page page2 = context1.newPage(); // Same context, shared cookies

BrowserContext context2 = browser.newContext(); // Different context, isolated
Page page3 = context2.newPage();
```

**Page:**
- Represents a single tab or window
- Belongs to a BrowserContext
- Has its own navigation history

### Thread-Safe Driver Management

For parallel execution, each thread needs its own Playwright instance:

```java
public class DriverFactory {
    private static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    public static void initBrowser() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(browser);
        contextThreadLocal.set(context);
        pageThreadLocal.set(page);
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static void tearDown() {
        pageThreadLocal.get().close();
        contextThreadLocal.get().close();
        browserThreadLocal.get().close();
        playwrightThreadLocal.get().close();
    }
}
```

### Interview Questions

**Q1: What is the difference between Browser and BrowserContext?**
- Browser: Represents the browser instance (Chrome, Firefox, etc.)
- BrowserContext: Represents an isolated session (like incognito mode)
- Multiple contexts can exist within one browser
- Each context has its own cookies, storage, and cache

**Q2: Why use BrowserContext instead of creating multiple browsers?**
- Faster: Contexts are lighter than full browser instances
- Isolated: Each context has independent cookies and storage
- Efficient: Better resource utilization
- Use case: Testing multiple users in parallel

**Q3: How does Playwright handle browser drivers?**
- Playwright bundles browser binaries
- No need to download separate drivers like chromedriver
- Automatic version matching
- Cross-platform support out of the box

---

## 4. LOCATORS IN PLAYWRIGHT

### Locator Philosophy
Playwright uses locators that represent elements on the page. Locators are:
- Lazy: Elements are located only when needed
- Reusable: Can be stored and used multiple times
- Auto-waiting: Automatically wait for element to be ready
- Resilient: Retried if element is not immediately available

### 4.1 CSS Selectors

```java
// By ID
page.locator("#elementId").click();

// By class
page.locator(".className").click();

// By tag
page.locator("div").click();

// By attribute
page.locator("[name='username']").click();
page.locator("[data-testid='submit']").click();

// Combined selectors
page.locator("input#username").fill("test");
page.locator("button.submit-btn").click();

// CSS with comma (OR condition)
page.locator("input[placeholder='email'], input#userEmail").fill("test@example.com");

// Starts with
page.locator("input[name^='user']")

// Ends with
page.locator("input[name$='name']")

// Contains
page.locator("input[name*='name']")

// Direct child
page.locator("div > p")

// Nth child
page.locator("ul li:nth-child(2)")

// First/Last
page.locator("li:first-child")
page.locator("li:last-child")
```

### 4.2 XPath Selectors

```java
// Basic XPath
page.locator("//input[@id='username']").fill("test");

// By text
page.locator("//button[text()='Submit']").click();

// Contains text
page.locator("//button[contains(text(),'Submit')]").click();

// By attribute
page.locator("//input[@placeholder='Enter email']").fill("test@example.com");

// Index-based
page.locator("(//div[@class='item'])[1]").click();

// Parent-child relationship
page.locator("//div[@class='parent']/child::input").fill("test");

// Following sibling
page.locator("//input[@id='field1']/following-sibling::input").fill("test");

// Preceding sibling
page.locator("//input[@id='field2']/preceding-sibling::input").fill("test");

// Ancestor
page.locator("//input[@id='field1']/ancestor::div[@class='form']")
```

### 4.3 getByRole - Accessibility-First Locators

```java
import com.microsoft.playwright.options.AriaRole;

// By role and name
page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();

// By textbox
page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email")).fill("test@example.com");

// By checkbox
page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Remember me")).check();

// By link
page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

// By heading
page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Welcome")).textContent();

// By radio
page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Male")).check();

// By combobox (dropdown)
page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Country")).selectOption("India");

// Exact match
page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setNameExact("Submit")).click();
```

**Advantages of getByRole:**
- Accessibility-aligned
- Resilient to styling changes
- Works with Shadow DOM
- Human-readable
- Recommended by Playwright

### 4.4 Text-Based Locators

```java
// By exact text
page.getByText("Submit").click();

// By partial text
page.getByText("Subm", new Page.GetByTextOptions().setExact(false)).click();

// By text with selector
page.locator("div").getByText("Welcome").click();

// Case-insensitive
page.getByText("submit", new Page.GetByTextOptions().setIgnoreCase(true)).click();
```

### 4.5 Label-Based Locators

```java
// By label text
page.getByLabel("Email").fill("test@example.com");

// By label with exact match
page.getByLabel("Email Address", new Page.GetByLabelOptions().setExact(true)).fill("test@example.com");

// Useful for form inputs associated with labels
```

### 4.6 Placeholder Locators

```java
// By placeholder text
page.getByPlaceholder("Enter email").fill("test@example.com");

// By placeholder with exact match
page.getByPlaceholder("Email Address", new Page.GetByPlaceholderOptions().setExact(true)).fill("test@example.com");
```

### 4.7 Alt Text Locators

```java
// By alt text (for images)
page.getByAltText("Logo").click();

// By alt text with exact match
page.getByAltText("Company Logo", new Page.GetByAltTextOptions().setExact(true)).click();
```

### 4.8 Title Locators

```java
// By title attribute
page.getByTitle("Tooltip text").click();

// By title with exact match
page.getByTitle("Submit Form", new Page.GetByTitleOptions().setExact(true)).click();
```

### 4.9 Test ID Locators

```java
// By data-testid attribute (default)
page.getByTestId("submit-button").click();

// Configure custom attribute
browserContext = browser.newContext(
    new Browser.NewContextOptions().setTestIdAttributeName("data-test-id")
);
page.getByTestId("submit-button").click();
```

### 4.10 Chaining Locators

```java
// Chain multiple locators
page.locator("div.form-group").getByRole(AriaRole.TEXTBOX).fill("test");

// Filter by text
page.locator("li").filter(new Locator.FilterOptions().setHasText("Item 1")).click();

// Filter by another locator
page.locator("div").filter(new Locator.FilterOptions().setHas(page.locator(".active"))).click();

// First/Last/Nth
page.locator("li").first().click();
page.locator("li").last().click();
page.locator("li").nth(2).click();

// And/Or conditions
page.locator("button").and(new Locator.LocatorOptions().setHasText("Submit")).click();
```

### 4.11 Shadow DOM Locators

```java
// Single shadow DOM
page.locator("css=div#userName >> text='Learning Hub'").textContent();
page.locator("css=div#userName").getByText("Learning Hub").textContent();

// Multiple nested shadow DOM
page.locator("css=div#userName div #pizza").fill("My Pizza");
// Alternative syntax
page.locator("css=div#userName >> div >> #pizza").fill("My Pizza");

// Using >> operator (penetrates shadow DOM)
page.locator("div#shadow-host >> .shadow-element").click();
```

### 4.12 Frame Locators

```java
// Frame by selector
page.frameLocator("#myFrame").locator("#button").click();

// Nested frames
page.frameLocator("#outerFrame")
    .frameLocator("#innerFrame")
    .locator("#button").click();

// Shadow DOM + Frame
page.locator("#userName").frameLocator("#pact1").locator("#connect").textContent();
```

### 4.13 Conditional Locators

```java
// Locator with hasText
page.locator("div", new Locator.LocatorOptions().setHasText("Welcome")).click();

// Locator with has (contains another locator)
page.locator("div", new Locator.LocatorOptions().setHas(page.locator(".active"))).click();

// Locator with hasNot
page.locator("div", new Locator.LocatorOptions().setHasNotText("Disabled")).click();
```

### 4.14 React Locators

```java
// By React component name (requires experimental feature)
// Note: This requires the @playwright/experimental-rects package
page.locator("_react=SubmitButton").click();
```

### Locator Best Practices

1. **Prefer getByRole** for accessibility and resilience
2. **Use test IDs** for stable, dedicated test attributes
3. **Avoid brittle selectors** like complex XPath
4. **Chain locators** for more specific targeting
5. **Use user-visible attributes** (text, labels) over implementation details
6. **Prefer CSS over XPath** for better performance and readability

### Interview Questions

**Q1: What is the difference between page.locator() and page.getByRole()?**
- `page.locator()`: Generic locator using CSS/XPath
- `page.getByRole()`: Accessibility-first locator using ARIA roles
- getByRole is more resilient and recommended

**Q2: How does Playwright handle Shadow DOM?**
- Native support with `>>` operator
- Automatically penetrates shadow boundaries
- No need for JavaScript execution

**Q3: What are the advantages of using getByRole over CSS selectors?**
- Accessibility-aligned
- Resilient to styling changes
- Works with Shadow DOM
- Human-readable
- Recommended by Playwright team

**Q4: How do you handle dynamic locators?**
- Use stable attributes (data-testid)
- Use user-visible text (getByText, getByLabel)
- Use relative locators (filter, and, or)
- Use getByRole for accessibility

---

## 5. WAIT STRATEGIES IN PLAYWRIGHT

### Auto-Waiting (The Playwright Advantage)

Playwright automatically waits for elements to be:
- Visible in the DOM
- Stable (not animating)
- Receiving events (not covered by other elements)
- Enabled (for clickable elements)

**No explicit waits needed in most cases:**

```java
// Playwright automatically waits for:
page.locator("#submit").click(); // Waits for element to be clickable
page.locator("#result").textContent(); // Waits for element to be visible
```

### 5.1 Explicit Waits

```java
// Wait for navigation
page.waitForNavigation(() -> {
    page.locator("#link").click();
});

// Wait for selector
page.waitForSelector("#result", new Page.WaitForSelectorOptions()
    .setState("attached")); // attached, detached, visible, hidden

// Wait for function
page.waitForFunction("document.title.includes('Done')");

// Wait for load state
page.waitForLoadState(LoadState.NETWORKIDLE);
page.waitForLoadState(LoadState.DOMCONTENTLOADED);
page.waitForLoadState(LoadState.LOAD);

// Wait for timeout
page.waitForTimeout(5000); // Not recommended, use sparingly
```

### 5.2 Wait for Response

```java
// Wait for specific response
Response response = page.waitForResponse("**/api/users", () -> {
    page.locator("#load-users").click();
});
System.out.println(response.status());

// Wait for response with condition
page.waitForResponse(resp -> resp.url().contains("api") && resp.status() == 200, () -> {
    page.locator("#submit").click();
});
```

### 5.3 Wait for Request

```java
// Wait for request to be sent
Request request = page.waitForRequest("**/api/data", () -> {
    page.locator("#load-data").click();
});
System.out.println(request.url());
```

### 5.4 Wait for Popup

```java
// Wait for new page/tab
Page newPage = page.waitForPopup(() -> {
    page.locator("#open-new-tab").click();
});
newPage.waitForLoadState();
System.out.println(newPage.title());
```

### 5.5 Wait for Download

```java
// Wait for download to start
Download download = page.waitForDownload(() -> {
    page.locator("#download").click();
});
Path path = download.path();
System.out.println("Downloaded to: " + path);
```

### 5.6 Wait for File Chooser

```java
// Wait for file chooser
FileChooser fileChooser = page.waitForFileChooser(() -> {
    page.locator("#upload").click();
});
fileChooser.setFiles(Paths.get("test.txt"));
```

### 5.7 Timeout Configuration

```java
// Set default timeout for page
page.setDefaultTimeout(30_000); // 30 seconds

// Set default navigation timeout
page.setDefaultNavigationTimeout(60_000); // 60 seconds

// Set timeout for specific action
page.locator("#submit").click(new Locator.ClickOptions().setTimeout(10_000));

// Set timeout for wait
page.waitForSelector("#result", new Page.WaitForSelectorOptions().setTimeout(5_000));
```

### 5.8 Load States

```java
// Wait for different load states
page.waitForLoadState(LoadState.LOAD); // Page load event
page.waitForLoadState(LoadState.DOMCONTENTLOADED); // DOM ready
page.waitForLoadState(LoadState.NETWORKIDLE); // No network activity (recommended)
```

### Interview Questions

**Q1: How does Playwright's auto-waiting work?**
- Automatically waits for element to be actionable
- Checks visibility, stability, and event reception
- No need for explicit waits in most cases
- Reduces flaky tests

**Q2: When should you use explicit waits in Playwright?**
- Waiting for specific network responses
- Waiting for navigation to complete
- Waiting for custom conditions
- Waiting for popups/downloads

**Q3: What is the difference between waitForLoadState states?**
- LOAD: Window load event fired
- DOMCONTENTLOADED: DOM fully parsed
- NETWORKIDLE: No network activity for 500ms (most reliable)

**Q4: How do you handle timeout in Playwright?**
- Set default timeout at page/context level
- Override timeout for specific actions
- Use setTimeout options in action methods

---

## 6. ELEMENT INTERACTIONS

### 6.1 Click

```java
// Simple click
page.locator("#button").click();

// Click with options
page.locator("#button").click(new Locator.ClickOptions()
    .setButton(MouseButton.RIGHT) // Left, Right, Middle
    .setClickCount(2) // Double click
    .setDelay(100) // Delay between clicks
    .setForce(true) // Force click even if not visible
    .setModifiers(Arrays.asList(KeyboardModifier.SHIFT)) // With modifier keys
    .setPosition(10, 20) // Click at specific position
    .setTrial(true) // Dry run, doesn't actually click
);

// Double click
page.locator("#button").dblclick();

// Triple click
page.locator("#button").click(new Locator.ClickOptions().setClickCount(3));
```

### 6.2 Fill (Text Input)

```java
// Fill text input
page.locator("#username").fill("testuser");

// Fill with options
page.locator("#username").fill("testuser", new Locator.FillOptions()
    .setForce(true) // Force fill even if not editable
    .setNoWaitBefore(false) // Skip actionability checks
);

// Clear and fill
page.locator("#username").clear();
page.locator("#username").fill("newvalue");
```

### 6.3 Type (Simulate Real Typing)

```java
// Type character by character
page.locator("#username").type("testuser");

// Type with delay between keystrokes
page.locator("#username").type("testuser", new Locator.TypeOptions().setDelay(100));

// Type with options
page.locator("#username").type("testuser", new Locator.TypeOptions()
    .setDelay(50)
);
```

### 6.4 Check/Uncheck (Checkboxes and Radio)

```java
// Check checkbox
page.locator("#remember").check();

// Uncheck checkbox
page.locator("#remember").uncheck();

// Check with force
page.locator("#remember").check(new Locator.CheckOptions().setForce(true));

// Set checked state
page.locator("#remember").setChecked(true);
page.locator("#remember").setChecked(false);
```

### 6.5 Select Option (Dropdowns)

```java
// Select by visible text
page.locator("#country").selectOption("India");

// Select by value
page.locator("#country").selectOption(new SelectOption().setValue("IN"));

// Select by index
page.locator("#country").selectOption(new SelectOption().setIndex(0));

// Select by label
page.locator("#country").selectOption(new SelectOption().setLabel("India"));

// Select multiple options
page.locator("#colors").selectOption(new String[]{"Red", "Blue"});
```

### 6.6 Get Text Content

```java
// Get text content
String text = page.locator("#message").textContent();

// Get inner text (visible text)
String innerText = page.locator("#message").innerText();

// Get all text contents
List<String> allTexts = page.locator("li").allTextContents();

// Get all inner texts
List<String> allInnerTexts = page.locator("li").allInnerTexts();
```

### 6.7 Get Attribute

```java
// Get attribute value
String href = page.locator("#link").getAttribute("href");
String id = page.locator("#button").getAttribute("id");

// Get multiple attributes
String classValue = page.locator("#div").getAttribute("class");
```

### 6.8 Get Input Value

```java
// Get input value
String value = page.locator("#username").inputValue();

// Get textarea value
String text = page.locator("#message").inputValue();
```

### 6.9 Hover

```java
// Hover over element
page.locator("#menu").hover();

// Hover with options
page.locator("#menu").hover(new Locator.HoverOptions()
    .setPosition(10, 20)
    .setModifiers(Arrays.asList(KeyboardModifier.SHIFT))
);
```

### 6.10 Focus

```java
// Focus on element
page.locator("#username").focus();

// Focus and type
page.locator("#username").focus();
page.keyboard().type("testuser");
```

### 6.11 Scroll

```java
// Scroll element into view
page.locator("#footer").scrollIntoViewIfNeeded();

// Scroll to specific position
page.evaluate("window.scrollTo(0, 500)");

// Scroll to bottom
page.evaluate("window.scrollTo(0, document.body.scrollHeight)");

// Scroll to top
page.evaluate("window.scrollTo(0, 0)");
```

### 6.12 Drag and Drop

```java
// Drag to another element
page.locator("#source").dragTo(page.locator("#target"));

// Drag with options
page.locator("#source").dragTo(page.locator("#target"), new Locator.DragToOptions()
    .setSourcePosition(10, 10)
    .setTargetPosition(20, 20)
);
```

### 6.13 Press (Keyboard Shortcuts)

```java
// Press key on element
page.locator("#input").press("Enter");
page.locator("#input").press("Control+A");
page.locator("#input").press("Control+C");

// Press with options
page.locator("#input").press("Enter", new Locator.PressOptions()
    .setDelay(100)
);
```

### 6.14 Screenshot

```java
// Take full page screenshot
page.screenshot(new Page.ScreenshotOptions()
    .setPath(Paths.get("screenshot.png"))
);

// Take element screenshot
page.locator("#header").screenshot(new Locator.ScreenshotOptions()
    .setPath(Paths.get("header.png"))
);

// Screenshot with options
page.screenshot(new Page.ScreenshotOptions()
    .setPath(Paths.get("screenshot.png"))
    .setFullPage(true)
    .setType(ScreenshotType.PNG)
    .setQuality(90)
);
```

### 6.15 PDF Generation

```java
// Generate PDF
page.pdf(new Page.PdfOptions()
    .setPath(Paths.get("page.pdf"))
    .setFormat(PaperFormat.A4)
);
```

### Interview Questions

**Q1: What is the difference between fill() and type()?**
- `fill()`: Sets value directly, faster
- `type()`: Simulates real typing with keystrokes, slower but more realistic
- Use fill() for speed, type() for realism

**Q2: How do you handle hidden elements in Playwright?**
- Use `setForce(true)` option
- Use JavaScript execution
- Use `setNoWaitBefore(true)` to skip checks

**Q3: What is the difference between textContent() and innerText()?**
- `textContent()`: Gets all text including hidden
- `innerText()`: Gets only visible text (rendered)

**Q4: How do you select dropdown options in Playwright?**
- Use `selectOption()` with text, value, index, or label
- Works for `<select>` elements
- For custom dropdowns, use click on option

---

## 7. ADVANCED INTERACTIONS (MOUSE & KEYBOARD)

### 7.1 Mouse Actions

```java
// Click at specific coordinates
page.mouse().click(100, 200);

// Double click
page.mouse().dblclick(100, 200);

// Mouse down
page.mouse().down();

// Mouse up
page.mouse().up();

// Move mouse
page.mouse().move(100, 200);

// Wheel scroll
page.mouse().wheel(0, 500); // Scroll down
page.mouse().wheel(0, -500); // Scroll up
page.mouse().wheel(500, 0); // Scroll right
page.mouse().wheel(-500, 0); // Scroll left
```

### 7.2 Keyboard Actions

```java
// Press key
page.keyboard().press("Enter");
page.keyboard().press("Control+A");
page.keyboard().press("Shift+Tab");

// Type text
page.keyboard().type("Hello World");

// Type with delay
page.keyboard().type("Hello", new Keyboard.TypeOptions().setDelay(100));

// Key down
page.keyboard().down("Shift");

// Key up
page.keyboard().up("Shift");

// Insert text (bypasses typing events)
page.keyboard().insertText("text");

// Common key combinations
page.keyboard().press("Control+A"); // Select all
page.keyboard().press("Control+C"); // Copy
page.keyboard().press("Control+V"); // Paste
page.keyboard().press("Control+X"); // Cut
page.keyboard().press("Control+Z"); // Undo

// Platform-specific (Mac vs Windows)
String modifier = System.getProperty("os.name").toLowerCase().contains("mac")
    ? "Meta"
    : "Control";
page.keyboard().press(modifier + "+A");
```

### 7.3 Special Keys

```java
// Special keys
page.keyboard().press("Enter");
page.keyboard().press("Tab");
page.keyboard().press("Escape");
page.keyboard().press("Backspace");
page.keyboard().press("Delete");
page.keyboard().press("ArrowUp");
page.keyboard().press("ArrowDown");
page.keyboard().press("ArrowLeft");
page.keyboard().press("ArrowRight");
page.keyboard().press("Home");
page.keyboard().press("End");
page.keyboard().press("PageUp");
page.keyboard().press("PageDown");
page.keyboard().press("F1"); // F1-F12
```

### 7.4 Element-Specific Keyboard Actions

```java
// Press key on specific element
page.locator("#input").press("Enter");

// Type on specific element
page.locator("#input").type("text");

// Focus and press
page.locator("#input").focus();
page.keyboard().press("Control+A");
```

### 7.5 Copy-Paste Example

```java
// Select all and copy
page.locator("#source").focus();
page.keyboard().press("Control+A");
page.keyboard().press("Control+C");

// Paste to target
page.locator("#target").focus();
page.keyboard().press("Control+V");
```

### 7.6 Modifier Keys

```java
// With Shift
page.keyboard().down("Shift");
page.keyboard().press("KeyA"); // Types 'A'
page.keyboard().up("Shift");

// With Control
page.keyboard().down("Control");
page.keyboard().press("KeyA"); // Select all if in text field
page.keyboard().up("Control");
```

### Interview Questions

**Q1: What is the difference between page.keyboard() and locator.press()?**
- `page.keyboard()`: Global keyboard, affects focused element
- `locator.press()`: Element-specific, focuses element first

**Q2: How do you handle platform-specific keyboard shortcuts?**
- Detect OS using `System.getProperty("os.name")`
- Use "Meta" for Mac, "Control" for Windows/Linux
- Create helper method for cross-platform support

**Q3: When would you use mouse actions over locator actions?**
- SVG elements without DOM support
- Canvas-based applications
- Coordinates-based interactions
- Custom UI components

---

## 8. FRAME HANDLING

### 8.1 Frame by Selector

```java
// Switch to frame by selector
page.frameLocator("#myFrame").locator("#button").click();

// Chain frame locators
page.frameLocator("#outerFrame")
    .frameLocator("#innerFrame")
    .locator("#button").click();
```

### 8.2 Frame by Name

```java
// Get frame by name
Frame frame = page.frame("frameName");
frame.locator("#button").click();
```

### 8.3 Frame by URL

```java
// Get frame by URL
Frame frame = page.frameByUrl("**/iframe.html");
frame.locator("#button").click();
```

### 8.4 Nested Frames

```java
// Access nested frames
page.frameLocator("#frame1")
    .frameLocator("#frame2")
    .frameLocator("#frame3")
    .locator("#button").click();
```

### 8.5 Frame with Shadow DOM

```java
// Frame inside shadow DOM
page.locator("#shadowHost").frameLocator("#myFrame").locator("#button").click();

// Shadow DOM inside frame
page.frameLocator("#myFrame").locator("#shadowHost >> #button").click();
```

### 8.6 Frame Content

```java
// Get frame content
Frame frame = page.frame("myFrame");
String content = frame.content();
```

### Interview Questions

**Q1: How does Playwright handle frames differently from Selenium?**
- Playwright uses frameLocator for seamless frame access
- No need to switch back to default content
- Can chain frame locators for nested frames
- Works with Shadow DOM

**Q2: How do you handle nested frames in Playwright?**
- Chain frameLocator calls
- Each frameLocator represents a frame level
- No need to switch back to parent

**Q3: Can you interact with elements across frames?**
- No, each frame is isolated
- Must use frameLocator to access frame elements
- Cannot directly access parent frame from child

---

## 9. WINDOW AND TAB HANDLING

### 9.1 Open New Tab

```java
// Open new tab in same context
Page newTab = browserContext.newPage();
newTab.navigate("https://example.com");
```

### 9.2 Open New Window

```java
// Open new window (new context)
BrowserContext newContext = browser.newContext();
Page newWindow = newContext.newPage();
newWindow.navigate("https://example.com");
```

### 9.3 Handle Popup

```java
// Wait for popup and handle it
Page popup = page.waitForPopup(() -> {
    page.locator("#open-popup").click();
});
popup.waitForLoadState();
System.out.println(popup.title());
```

### 9.4 Get All Pages

```java
// Get all pages in context
List<Page> pages = browserContext.pages();
for (Page p : pages) {
    System.out.println(p.title());
}
```

### 9.5 Switch Between Pages

```java
// Get all pages
List<Page> pages = browserContext.pages();

// Switch to specific page
Page secondPage = pages.get(1);
secondPage.bringToFront();
secondPage.locator("#button").click();

// Switch back to first page
Page firstPage = pages.get(0);
firstPage.bringToFront();
```

### 9.6 Close Page

```java
// Close specific page
browserContext.pages().get(1).close();

// Close current page
page.close();
```

### 9.7 Bring to Front

```java
// Bring page to front (activate)
page.bringToFront();
```

### Interview Questions

**Q1: What is the difference between tabs and windows in Playwright?**
- No technical difference - both are Page objects
- Tabs share BrowserContext (cookies, storage)
- Windows have separate BrowserContext (isolated)
- Use tabs for same session, windows for different sessions

**Q2: How do you handle multiple tabs in Playwright?**
- Use `browserContext.pages()` to get all pages
- Use `bringToFront()` to activate specific page
- Use `waitForPopup()` to handle new tabs

**Q3: How do you handle new windows opened by JavaScript?**
- Use `waitForPopup()` to capture new window
- Returns the new Page object
- Can interact with it immediately

---

## 10. SHADOW DOM HANDLING

### 10.1 Single Shadow DOM

```java
// Access shadow DOM element
page.locator("css=div#shadowHost >> .shadowElement").click();

// Using getByText within shadow DOM
page.locator("css=div#shadowHost").getByText("Click Me").click();
```

### 10.2 Multiple Nested Shadow DOM

```java
// Access nested shadow DOM
page.locator("css=div#host1 >> div#host2 >> .element").click();

// Alternative syntax
page.locator("css=div#host1 >> div >> .element").click();
```

### 10.3 Shadow DOM with Frame

```java
// Frame inside shadow DOM
page.locator("#shadowHost").frameLocator("#myFrame").locator("#button").click();

// Shadow DOM inside frame
page.frameLocator("#myFrame").locator("#shadowHost >> .element").click();
```

### 10.4 Shadow DOM with getByRole

```java
// getByRole works with shadow DOM
page.locator("#shadowHost").getByRole(AriaRole.BUTTON).click();
```

### Interview Questions

**Q1: How does Playwright handle Shadow DOM compared to Selenium?**
- Playwright has native Shadow DOM support
- Uses `>>` operator to penetrate shadow boundaries
- Selenium requires JavaScript execution
- getByRole works seamlessly with Shadow DOM

**Q2: What is the `>>` operator in Playwright?**
- Shadow DOM piercing operator
- Penetrates shadow boundaries
- Works with CSS selectors
- Automatic with getByRole, getByText, etc.

**Q3: How do you handle nested shadow DOM?**
- Chain `>>` operators
- Each `>>` penetrates one shadow boundary
- Can access deeply nested elements

---

## 11. FILE UPLOAD AND DOWNLOAD

### 11.1 File Upload

```java
// Upload single file
page.setInputFiles("#fileInput", Paths.get("test.txt"));

// Upload multiple files
page.setInputFiles("#fileInput", new Path[]{
    Paths.get("file1.txt"),
    Paths.get("file2.txt")
});

// Clear file input
page.setInputFiles("#fileInput", new Path[0]);

// Upload with file chooser
FileChooser fileChooser = page.waitForFileChooser(() -> {
    page.locator("#upload").click();
});
fileChooser.setFiles(Paths.get("test.txt"));
```

### 11.2 Dynamic File Upload

```java
// Create file dynamically and upload
page.setInputFiles("#fileInput", new FilePayload(
    "dynamic.txt",
    "text/plain",
    "This is dynamic content".getBytes(StandardCharsets.UTF_8)
));
```

### 11.3 File Download

```java
// Wait for download
Download download = page.waitForDownload(() -> {
    page.locator("#download").click();
});

// Get download path
Path path = download.path();
System.out.println("Downloaded to: " + path);

// Save to specific location
download.saveAs(Paths.get("downloads/file.txt"));

// Get download URL
String url = download.url();
```

### 11.4 Download with Options

```java
// Set download behavior in context
browserContext = browser.newContext(
    new Browser.NewContextOptions()
        .setAcceptDownloads(true)
        .setDownloadBehavior("keep-folder", Paths.get("downloads"))
);
```

### Interview Questions

**Q1: How does Playwright handle file uploads?**
- Use `setInputFiles()` method
- Can upload single or multiple files
- Can create files dynamically with FilePayload
- Works with file chooser dialogs

**Q2: How do you handle file downloads in Playwright?**
- Use `waitForDownload()` to capture download
- Get download path or save to specific location
- Configure download behavior in BrowserContext
- Set `setAcceptDownloads(true)` in context

**Q3: What is FilePayload used for?**
- Create files dynamically in memory
- Upload without physical file on disk
- Useful for testing file upload functionality
- Specify name, MIME type, and content

---

## 12. POPUPS AND DIALOGS

### 12.1 Alert Handling

```java
// Accept alert
page.onDialog(Dialog::accept);
page.locator("#alertButton").click();

// Dismiss alert
page.onDialog(Dialog::dismiss);
page.locator("#alertButton").click();

// Accept with prompt text
page.onDialog(dialog -> dialog.accept("Input text"));
page.locator("#promptButton").click();
```

### 12.2 Dialog with Message

```java
// Get dialog message
page.onDialog(dialog -> {
    System.out.println("Dialog message: " + dialog.message());
    dialog.accept();
});
page.locator("#alertButton").click();
```

### 12.3 Dialog Types

```java
// Handle different dialog types
page.onDialog(dialog -> {
    switch (dialog.type()) {
        case "alert":
            dialog.accept();
            break;
        case "confirm":
            dialog.accept(); // or dialog.dismiss()
            break;
        case "prompt":
            dialog.accept("input text");
            break;
        case "beforeunload":
            dialog.accept();
            break;
    }
});
```

### 12.4 Auto-Handle Dialogs

```java
// Playwright auto-handles dialogs by default
// You can disable this
page.onDialog(dialog -> {
    // Custom handling
    dialog.accept();
});
```

### Interview Questions

**Q1: How does Playwright handle alerts compared to Selenium?**
- Playwright auto-handles alerts by default
- Selenium requires explicit switch to alert
- Playwright uses `onDialog` listener
- More flexible and less error-prone

**Q2: What are the different dialog types in Playwright?**
- alert: Simple alert with OK button
- confirm: Confirmation with OK/Cancel
- prompt: Alert with input field
- beforeunload: Before page unload

**Q3: How do you handle timed alerts in Playwright?**
- Playwright auto-waits for alert
- No need for explicit waits
- Use `onDialog` listener to handle

---

## 13. WEB TABLES

### 13.1 Get All Table Data

```java
// Get all rows
page.locator("table tbody tr").allInnerTexts().forEach(System.out::println);

// Get all text contents
List<String> allData = page.locator("table tbody tr").allTextContents();
```

### 13.2 Get Specific Row

```java
// Get row by index
String row1 = page.locator("table tbody tr").nth(0).textContent();

// Get row by text content
String specificRow = page.locator("table tbody tr")
    .filter(new Locator.FilterOptions().setHasText("John Doe"))
    .textContent();
```

### 13.3 Iterate Through Table

```java
// Iterate through rows
Locator rows = page.locator("table tbody tr");
for (int i = 0; i < rows.count(); i++) {
    List<String> cells = rows.nth(i).locator("td").allInnerTexts();
    System.out.println(String.join(" | ", cells));
}
```

### 13.4 Get Cell Data

```java
// Get specific cell
String cell = page.locator("table tbody tr").nth(0).locator("td").nth(1).textContent();

// Get cell by row and column
String cellData = page.locator("table tbody tr")
    .nth(2)
    .locator("td")
    .nth(3)
    .textContent();
```

### 13.5 Filter Table Data

```java
// Filter rows by text
Locator filteredRows = page.locator("table tbody tr")
    .filter(new Locator.FilterOptions().setHasText("Active"));

// Get count
int count = filteredRows.count();
```

### 13.6 Table with Headers

```java
// Get headers
List<String> headers = page.locator("table thead th").allTextContents();

// Get data by header
String emailColumn = page.locator("table tbody tr")
    .nth(0)
    .locator("td")
    .nth(headers.indexOf("Email"))
    .textContent();
```

### Interview Questions

**Q1: How do you handle dynamic tables in Playwright?**
- Use `filter()` to find specific rows
- Use `nth()` for index-based access
- Use `all()` to get all elements
- Iterate through rows and cells

**Q2: What is the difference between allTextContents() and allInnerTexts()?**
- `allTextContents()`: Returns text content of all elements
- `allInnerTexts()`: Returns inner text (visible text)
- Similar to textContent() vs innerText()

**Q3: How do you get table data efficiently?**
- Use `allInnerTexts()` for bulk data
- Use `filter()` for specific rows
- Avoid nested loops when possible
- Use `nth()` for direct access

---

## 14. SCREENSHOTS AND VIDEO RECORDING

### 14.1 Full Page Screenshot

```java
// Take full page screenshot
page.screenshot(new Page.ScreenshotOptions()
    .setPath(Paths.get("screenshot.png"))
    .setFullPage(true)
);
```

### 14.2 Element Screenshot

```java
// Take element screenshot
page.locator("#header").screenshot(new Locator.ScreenshotOptions()
    .setPath(Paths.get("header.png"))
);
```

### 14.3 Screenshot Options

```java
// Screenshot with options
page.screenshot(new Page.ScreenshotOptions()
    .setPath(Paths.get("screenshot.png"))
    .setFullPage(true)
    .setType(ScreenshotType.PNG) // or JPEG
    .setQuality(90) // For JPEG only
    .setOmitBackground(false) // Transparent background
);
```

### 14.4 Video Recording

```java
// Enable video recording
BrowserContext context = browser.newContext(
    new Browser.NewContextOptions()
        .setRecordVideoDir(Paths.get("videos"))
        .setRecordVideoSize(1280, 720)
);

Page page = context.newPage();
// ... perform actions ...

// Video is saved when context closes
context.close();
```

### 14.5 Video Path

```java
// Get video path
Page page = context.newPage();
String videoPath = page.video().path();
```

### 14.6 Trace Viewer

```java
// Start tracing
context.tracing().start(new Tracing.StartOptions()
    .setScreenshots(true)
    .setSnapshots(true)
);

// ... perform actions ...

// Stop tracing
context.tracing().stop(new Tracing.StopOptions()
    .setPath(Paths.get("trace.zip"))
);
```

### Interview Questions

**Q1: How do you take screenshots in Playwright?**
- Use `page.screenshot()` for full page
- Use `locator.screenshot()` for elements
- Options: fullPage, type, quality, path

**Q2: How do you enable video recording in Playwright?**
- Set `setRecordVideoDir()` in BrowserContext
- Set `setRecordVideoSize()` for dimensions
- Video saved when context closes
- Access with `page.video().path()`

**Q3: What is Trace Viewer in Playwright?**
- Advanced debugging tool
- Records screenshots, snapshots, network
- Use `context.tracing().start()` and `stop()`
- Open trace.zip with Playwright CLI

---

## 15. PAGE OBJECT MODEL (POM)

### 15.1 Basic POM Structure

```java
public class LoginPage {
    private Page page;
    
    // Locators
    private Locator usernameInput;
    private Locator passwordInput;
    private Locator loginButton;
    
    public LoginPage(Page page) {
        this.page = page;
        this.usernameInput = page.locator("#username");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("#login");
    }
    
    // Actions
    public LoginPage enterUsername(String username) {
        usernameInput.fill(username);
        return this;
    }
    
    public LoginPage enterPassword(String password) {
        passwordInput.fill(password);
        return this;
    }
    
    public DashboardPage clickLogin() {
        loginButton.click();
        return new DashboardPage(page);
    }
    
    public LoginPage login(String username, String password) {
        return enterUsername(username)
               .enterPassword(password)
               .clickLogin();
    }
}
```

### 15.2 Using POM in Tests

```java
@Test
public void testLogin() {
    LoginPage loginPage = new LoginPage(page);
    DashboardPage dashboard = loginPage
        .enterUsername("testuser")
        .enterPassword("password")
        .clickLogin();
    
    Assert.assertTrue(dashboard.isLoaded());
}
```

### 15.3 Base Page Class

```java
public class BasePage {
    protected Page page;
    protected Faker faker = new Faker();
    
    public BasePage(Page page) {
        this.page = page;
    }
    
    public String getTitle() {
        return page.title();
    }
    
    public String getUrl() {
        return page.url();
    }
    
    public void navigate(String url) {
        page.navigate(url);
    }
    
    public void waitForLoadState() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
```

### 15.4 Page Factory Pattern

```java
public class PageFactory {
    private Page page;
    
    public PageFactory(Page page) {
        this.page = page;
    }
    
    public LoginPage getLoginPage() {
        return new LoginPage(page);
    }
    
    public DashboardPage getDashboardPage() {
        return new DashboardPage(page);
    }
}
```

### Interview Questions

**Q1: What is Page Object Model?**
- Design pattern for test automation
- Each page represented by a class
- Separates test logic from page logic
- Improves maintainability and reusability

**Q2: What are the benefits of POM?**
- Reusability: Locators defined once
- Maintainability: Changes in one place
- Readability: Cleaner test code
- Separation of concerns

**Q3: How do you implement method chaining in POM?**
- Return `this` for same page
- Return new page object for navigation
- Enables fluent API style

---

## 16. TESTNG FRAMEWORK INTEGRATION

### 16.1 TestNG Setup

```java
@Listeners(org.selectorHub.Playwrite_listeners.TestListener.class)
public class BaseTest {
    public Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;
    
    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }
    
    @AfterMethod
    public void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
```

### 16.2 TestNG Annotations

```java
@BeforeSuite
public void beforeSuite() {
    // Runs before all tests
}

@BeforeTest
public void beforeTest() {
    // Runs before test tag
}

@BeforeClass
public void beforeClass() {
    // Runs before first test in class
}

@BeforeMethod
public void beforeMethod() {
    // Runs before each test method
}

@Test
public void testMethod() {
    // Test method
}

@AfterMethod
public void afterMethod() {
    // Runs after each test method
}

@AfterClass
public void afterClass() {
    // Runs after all tests in class
}

@AfterTest
public void afterTest() {
    // Runs after test tag
}

@AfterSuite
public void afterSuite() {
    // Runs after all tests
}
```

### 16.3 Test Parameters

```java
@Parameters({"browser", "url"})
@Test
public void test(String browser, String url) {
    System.out.println("Browser: " + browser);
    System.out.println("URL: " + url);
}
```

### 16.4 Data Provider

```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][]{
        {"user1", "pass1"},
        {"user2", "pass2"},
        {"user3", "pass3"}
    };
}

@Test(dataProvider = "loginData")
public void testLogin(String username, String password) {
    // Test with different data
}
```

### 16.5 Test Groups

```java
@Test(groups = {"smoke"})
public void smokeTest() {
    // Smoke test
}

@Test(groups = {"regression"})
public void regressionTest() {
    // Regression test
}

@Test(groups = {"smoke", "regression"})
public void bothTest() {
    // Both smoke and regression
}
```

### 16.6 Test Dependencies

```java
@Test
public void login() {
    // Login test
}

@Test(dependsOnMethods = "login")
public void dashboard() {
    // Runs only after login
}

@Test(dependsOnGroups = "smoke")
public void regressionTest() {
    // Runs after smoke group
}
```

### 16.7 Parallel Execution

```java
@Test(threadPoolSize = 3, invocationCount = 5)
public void parallelTest() {
    // Runs 5 times in 3 threads
}
```

### Interview Questions

**Q1: How do you integrate Playwright with TestNG?**
- Use @BeforeMethod for setup
- Use @AfterMethod for teardown
- Use @Test for test methods
- Use @Listeners for custom listeners

**Q2: How do you run tests in parallel with Playwright and TestNG?**
- Use ThreadLocal for driver management
- Configure threadPoolSize in @Test
- Each thread gets its own browser instance
- Use testng.xml for parallel configuration

**Q3: How do you use DataProvider with Playwright?**
- Create DataProvider method
- Pass data to test method
- Test runs multiple times with different data
- Can use complex objects (HashMap, etc.)

---

## 17. PLAYWRIGHT LISTENERS

### 17.1 Dialog Listener

```java
page.onDialog(dialog -> {
    System.out.println("Dialog: " + dialog.message());
    dialog.accept();
});
```

### 17.2 Console Listener

```java
page.onConsoleMessage(msg -> {
    System.out.println("Console: " + msg.text());
    System.out.println("Type: " + msg.type());
});
```

### 17.3 Request Listener

```java
page.onRequest(request -> {
    System.out.println("Request: " + request.url());
    System.out.println("Method: " + request.method());
});
```

### 17.4 Response Listener

```java
page.onResponse(response -> {
    System.out.println("Response: " + response.url());
    System.out.println("Status: " + response.status());
});
```

### 17.5 Popup Listener

```java
page.onPopup(popup -> {
    System.out.println("Popup opened: " + popup.url());
});
```

### 17.6 Download Listener

```java
page.onDownload(download -> {
    System.out.println("Download started: " + download.url());
});
```

### 17.7 Page Error Listener

```java
page.on("pageerror", error -> {
    System.out.println("Page error: " + error);
});
```

### 17.8 Frame Navigation Listener

```java
page.onFrameNavigated(frame -> {
    System.out.println("Frame navigated: " + frame.url());
});
```

### 17.9 TestNG Listener

```java
public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        // Take screenshot on failure
        Page page = (Page) result.getAttribute("page");
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get("failure.png"))
        );
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }
}
```

### Interview Questions

**Q1: What are Playwright listeners used for?**
- Monitor browser events
- Debugging and logging
- Handle dialogs, popups, downloads
- Capture network traffic

**Q2: What is the difference between listeners and waits?**
- Listeners: Observe events continuously
- Waits: Block until specific event occurs
- Listeners for logging/debugging
- Waits for test synchronization

**Q3: How do you handle network requests with listeners?**
- Use `onRequest` for outgoing requests
- Use `onResponse` for incoming responses
- Can modify or block requests
- Useful for API testing

---

## 18. REPORTING WITH EXTENTREPORTS

### 18.1 ExtentReports Setup

```java
public class ExtentReportManager {
    private static ExtentReports extent;
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("reports/ExtentReport.html");
            reporter.config().setReportName("Playwright Automation Report");
            reporter.config().setDocumentTitle("Execution Results");
            
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("Playwright", "1.57.0");
        }
        return extent;
    }
}
```

### 18.2 ExtentTest in TestNG

```java
public class ExtentReport_BaseTest extends BaseTest {
    public ExtentTest extentTest;
    
    @BeforeMethod
    public void setup(Method method) {
        super.setup();
        extentTest = ExtentReportManager.getInstance().createTest(method.getName());
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
            extentTest.addScreenCaptureFromPath("screenshot.png");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test passed");
        }
        super.tearDown();
    }
    
    @AfterSuite
    public void flushReport() {
        ExtentReportManager.getInstance().flush();
    }
}
```

### 18.3 Logging in Tests

```java
@Test
public void testLogin() {
    extentTest.info("Navigating to login page");
    page.navigate("https://example.com/login");
    
    extentTest.info("Entering username");
    page.locator("#username").fill("testuser");
    
    extentTest.info("Entering password");
    page.locator("#password").fill("password");
    
    extentTest.info("Clicking login button");
    page.locator("#login").click();
    
    extentTest.pass("Login successful");
}
```

### 18.4 Adding Screenshots

```java
@Test
public void testWithScreenshot() {
    page.navigate("https://example.com");
    
    // Take screenshot
    page.screenshot(new Page.ScreenshotOptions()
        .setPath(Paths.get("screenshot.png"))
    );
    
    // Add to report
    extentTest.addScreenCaptureFromPath("screenshot.png");
}
```

### Interview Questions

**Q1: How do you integrate ExtentReports with Playwright?**
- Create ExtentReports instance
- Attach ExtentSparkReporter
- Create ExtentTest for each test
- Log test steps and results
- Flush report at the end

**Q2: How do you add screenshots to ExtentReports?**
- Take screenshot using Playwright
- Save to file
- Add to report using `addScreenCaptureFromPath()`
- Can add base64 images directly

**Q3: What information should be included in reports?**
- Test name and description
- Test status (pass/fail/skip)
- Error messages and stack traces
- Screenshots on failure
- Execution time
- System information

---

## 19. AUTO LOGIN AND STATE MANAGEMENT

### 19.1 Save Storage State

```java
// Login and save state
page.navigate("https://example.com/login");
page.locator("#username").fill("testuser");
page.locator("#password").fill("password");
page.locator("#login").click();
page.waitForLoadState();

// Save storage state (cookies, localStorage)
browserContext.storageState(new BrowserContext.StorageStateOptions()
    .setPath(Paths.get("state.json"))
);
```

### 19.2 Load Storage State

```java
// Load saved state
browserContext = browser.newContext(
    new Browser.NewContextOptions()
        .setStorageStatePath(Paths.get("state.json"))
);

// Navigate directly to authenticated page
page = browserContext.newPage();
page.navigate("https://example.com/dashboard");
```

### 19.3 Reuse Authentication

```java
// Login once and reuse session
@BeforeSuite
public void setupAuth() {
    Playwright playwright = Playwright.create();
    Browser browser = playwright.chromium().launch();
    BrowserContext context = browser.newContext();
    Page page = context.newPage();
    
    // Perform login
    page.navigate("https://example.com/login");
    page.locator("#username").fill("testuser");
    page.locator("#password").fill("password");
    page.locator("#login").click();
    page.waitForLoadState();
    
    // Save state
    context.storageState(new BrowserContext.StorageStateOptions()
        .setPath(Paths.get("auth-state.json"))
    );
    
    context.close();
    browser.close();
    playwright.close();
}

@BeforeMethod
public void setup() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
    browserContext = browser.newContext(
        new Browser.NewContextOptions()
            .setStorageStatePath(Paths.get("auth-state.json"))
    );
    page = browserContext.newPage();
}
```

### 19.4 Cookie Management

```java
// Add cookie
browserContext.addCookies(Arrays.asList(
    new Cookie("name", "value")
        .setDomain("example.com")
        .setPath("/")
));

// Get cookies
List<Cookie> cookies = browserContext.cookies();

// Get specific cookie
List<Cookie> specificCookies = browserContext.cookies("https://example.com");

// Clear cookies
browserContext.clearCookies();

// Clear specific cookies
browserContext.clearCookies(new BrowserContext.ClearCookiesOptions()
    .setDomain("example.com")
);
```

### Interview Questions

**Q1: How do you handle authentication in Playwright?**
- Use storage state to save/load session
- Save cookies and localStorage
- Load state in new context
- Skip login for each test

**Q2: What is storage state in Playwright?**
- Contains cookies, localStorage, sessionStorage
- Saved as JSON file
- Can be loaded in new context
- Maintains authentication state

**Q3: How do you manage cookies in Playwright?**
- Use `addCookies()` to add cookies
- Use `cookies()` to get all cookies
- Use `clearCookies()` to clear cookies
- Filter by domain or name

---

## 20. NETWORK INTERCEPTION

### 20.1 Mock API Response

```java
// Mock API response
page.route("**/api/users", route -> {
    route.fulfill(new Route.FulfillOptions()
        .setStatus(200)
        .setContentType("application/json")
        .setBody("[{\"id\":1,\"name\":\"Test User\"}]")
    );
});

page.navigate("https://example.com");
```

### 20.2 Intercept Request

```java
// Intercept and modify request
page.route("**/api/data", route -> {
    // Get original request
    APIRequest request = route.request();
    
    // Modify request
    route.continue(new Route.ContinueOptions()
        .setPostData("{\"modified\":true}")
    );
});
```

### 20.3 Block Request

```java
// Block specific requests
page.route("**/*.png", route -> route.abort());
page.route("**/analytics", route -> route.abort());
```

### 20.4 Monitor Network Traffic

```java
// Monitor all requests
List<Request> requests = new ArrayList<>();
page.onRequest(request -> {
    requests.add(request);
    System.out.println("Request: " + request.url());
});

// Monitor all responses
page.onResponse(response -> {
    System.out.println("Response: " + response.url() + " - " + response.status());
});
```

### 20.5 Wait for Network Idle

```java
// Wait for network to be idle
page.waitForLoadState(LoadState.NETWORKIDLE);
```

### Interview Questions

**Q1: How do you mock API responses in Playwright?**
- Use `page.route()` to intercept requests
- Use `route.fulfill()` to return mock response
- Set status, content type, and body
- Useful for testing without backend

**Q2: How do you block requests in Playwright?**
- Use `page.route()` with `route.abort()`
- Block images, analytics, etc.
- Improves test performance
- Reduces external dependencies

**Q3: How do you monitor network traffic?**
- Use `onRequest` listener
- Use `onResponse` listener
- Store requests/responses in list
- Useful for debugging and API testing

---

## 21. API TESTING WITH PLAYWRIGHT

### 21.1 APIRequestContext

```java
// Create API request context
APIRequestContext apiRequestContext = playwright.request().newContext();
```

### 21.2 GET Request

```java
APIResponse response = apiRequestContext.get("https://api.example.com/users");
System.out.println("Status: " + response.status());
System.out.println("Body: " + response.text());
```

### 21.3 POST Request

```java
APIResponse response = apiRequestContext.post("https://api.example.com/users",
    APIRequestContext.create().setData("{\"name\":\"Test\"}")
);
```

### 21.4 PUT Request

```java
APIResponse response = apiRequestContext.put("https://api.example.com/users/1",
    APIRequestContext.create().setData("{\"name\":\"Updated\"}")
);
```

### 21.5 DELETE Request

```java
APIResponse response = apiRequestContext.delete("https://api.example.com/users/1");
```

### 21.6 With Headers

```java
APIResponse response = apiRequestContext.get("https://api.example.com/users",
    APIRequestContext.create().setHeaders(Map.of(
        "Authorization", "Bearer token",
        "Content-Type", "application/json"
    ))
);
```

### 21.7 API Assertions

```java
APIResponse response = apiRequestContext.get("https://api.example.com/users");

// Assert status
Assert.assertEquals(response.status(), 200);

// Assert body
JsonObject body = new Gson().fromJson(response.text(), JsonObject.class);
Assert.assertEquals(body.get("name").getAsString(), "Test");

// Assert header
Assert.assertEquals(response.headers().get("Content-Type"), "application/json");
```

### Interview Questions

**Q1: How do you perform API testing with Playwright?**
- Use APIRequestContext
- Supports GET, POST, PUT, DELETE
- Can set headers and body
- Assert status, body, headers

**Q2: What are the advantages of API testing with Playwright?**
- Same framework for UI and API
- No need for separate API tool
- Can mock API responses
- Can test UI + API integration

**Q3: How do you handle authentication in API requests?**
- Set Authorization header
- Use Bearer token
- Can use storage state from UI
- Can set cookies in context

---

## 22. PARALLEL EXECUTION

### 22.1 ThreadLocal Driver

```java
public class DriverFactory {
    private static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    
    public static void initBrowser() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        
        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(browser);
        contextThreadLocal.set(context);
        pageThreadLocal.set(page);
    }
    
    public static Page getPage() {
        return pageThreadLocal.get();
    }
    
    public static void tearDown() {
        pageThreadLocal.get().close();
        contextThreadLocal.get().close();
        browserThreadLocal.get().close();
        playwrightThreadLocal.get().close();
    }
}
```

### 22.2 TestNG XML Configuration

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Parallel Suite" parallel="tests" thread-count="3">
    <test name="Test 1">
        <classes>
            <class name="org.selectorHub.tests.TestClass1"/>
        </classes>
    </test>
    <test name="Test 2">
        <classes>
            <class name="org.selectorHub.tests.TestClass2"/>
        </classes>
    </test>
    <test name="Test 3">
        <classes>
            <class name="org.selectorHub.tests.TestClass3"/>
        </classes>
    </test>
</suite>
```

### 22.3 Parallel Methods

```java
@Test(threadPoolSize = 3, invocationCount = 5)
public void parallelTest() {
    // Runs 5 times in 3 threads
}
```

### 22.4 Parallel DataProvider

```java
@DataProvider(name = "data", parallel = true)
public Object[][] getData() {
    return new Object[][]{
        {"data1"},
        {"data2"},
        {"data3"}
    };
}

@Test(dataProvider = "data")
public void test(String data) {
    // Each data set runs in parallel
}
```

### Interview Questions

**Q1: How do you handle parallel execution in Playwright?**
- Use ThreadLocal for driver management
- Each thread gets its own browser instance
- Configure parallel in testng.xml
- Use threadPoolSize in @Test

**Q2: Why use ThreadLocal for parallel execution?**
- Each thread gets its own driver instance
- Prevents interference between tests
- Ensures thread safety
- Essential for parallel testing

**Q3: What are the challenges of parallel execution?**
- Resource contention
- Test data conflicts
- Browser resource limits
- Need proper isolation

---

## 23. JAVA PROGRAMMING FOR INTERVIEWS

### 23.1 Collections

```java
// ArrayList
List<String> list = new ArrayList<>();
list.add("item1");
list.get(0);

// HashMap
Map<String, String> map = new HashMap<>();
map.put("key", "value");
map.get("key");

// LinkedHashMap (maintains insertion order)
Map<String, String> linkedMap = new LinkedHashMap<>();

// HashSet
Set<String> set = new HashSet<>();
set.add("item1");
```

### 23.2 Streams

```java
// Filter
list.stream()
    .filter(item -> item.startsWith("A"))
    .collect(Collectors.toList());

// Map
list.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// ForEach
list.stream()
    .forEach(System.out::println);

// Reduce
int sum = numbers.stream()
    .reduce(0, Integer::sum);
```

### 23.3 Exception Handling

```java
try {
    // Code that may throw exception
} catch (IOException e) {
    // Handle specific exception
} catch (Exception e) {
    // Handle general exception
} finally {
    // Always executed
}
```

### 23.4 Interfaces and Abstract Classes

```java
// Interface
interface WebDriver {
    void get(String url);
    void quit();
}

// Abstract class
abstract class BasePage {
    protected Page page;
    
    public BasePage(Page page) {
        this.page = page;
    }
    
    public abstract void load();
}
```

### 23.5 Generics

```java
public class PageFactory<T> {
    public T getPage(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor(Page.class).newInstance(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

### 23.6 Lambda Expressions

```java
// Lambda
list.forEach(item -> System.out.println(item));

// Method reference
list.forEach(System.out::println);

// Predicate
Predicate<String> startsWithA = item -> item.startsWith("A");
list.stream().filter(startsWithA).collect(Collectors.toList());
```

### Interview Questions

**Q1: What is the difference between ArrayList and LinkedList?**
- ArrayList: Dynamic array, fast random access
- LinkedList: Doubly linked list, fast insertion/deletion
- ArrayList uses more memory
- LinkedList uses more memory for pointers

**Q2: What is the difference between HashMap and LinkedHashMap?**
- HashMap: No order guarantee
- LinkedHashMap: Maintains insertion order
- LinkedHashMap slightly slower
- Use LinkedHashMap when order matters

**Q3: What are Java Streams used for?**
- Functional operations on collections
- Filter, map, reduce operations
- Declarative programming
- Parallel processing support

---

## 24. BEST PRACTICES & DESIGN PATTERNS

### 24.1 Best Practices

**Locator Strategy:**
- Prefer getByRole for accessibility
- Use test IDs for stable selectors
- Avoid brittle XPath expressions
- Use user-visible attributes

**Wait Strategy:**
- Rely on auto-waiting
- Use explicit waits for specific conditions
- Avoid hard-coded sleeps
- Set appropriate timeouts

**Test Structure:**
- Use Page Object Model
- Separate test logic from page logic
- Use descriptive test names
- Keep tests independent

**Code Quality:**
- Use meaningful variable names
- Add comments for complex logic
- Follow coding standards
- Use helper methods for common actions

### 24.2 Design Patterns

**Page Object Model:**
- Each page represented by class
- Locators defined as private fields
- Actions as public methods
- Method chaining for fluent API

**Factory Pattern:**
- Create browser instances
- Create page objects
- Centralize object creation
- Easy to extend

**Singleton Pattern:**
- Single instance of ExtentReports
- Single instance of configuration
- Thread-safe implementation
- Lazy initialization

**Builder Pattern:**
- Complex object creation
- Fluent API
- Optional parameters
- Immutable objects

### 24.3 Test Design

**AAA Pattern:**
- Arrange: Setup test data
- Act: Perform action
- Assert: Verify result

```java
@Test
public void testLogin() {
    // Arrange
    String username = "testuser";
    String password = "password";
    
    // Act
    loginPage.login(username, password);
    
    // Assert
    Assert.assertTrue(dashboardPage.isLoaded());
}
```

**Data-Driven Testing:**
- Use DataProvider
- External data sources (Excel, JSON)
- Parameterized tests
- Test data management

### Interview Questions

**Q1: What are the best practices for Playwright testing?**
- Use Page Object Model
- Prefer getByRole locators
- Rely on auto-waiting
- Keep tests independent
- Use descriptive names

**Q2: What design patterns are used in test automation?**
- Page Object Model
- Factory Pattern
- Singleton Pattern
- Builder Pattern
- Strategy Pattern

**Q3: What is the AAA pattern in testing?**
- Arrange: Setup test conditions
- Act: Execute the test
- Assert: Verify the result
- Improves test readability

---

## 25. COMMON INTERVIEW QUESTIONS

### Playwright Specific

**Q1: What is Playwright and how does it differ from Selenium?**
- Playwright is modern E2E testing framework by Microsoft
- Auto-waiting vs explicit waits
- Native Shadow DOM support
- Built-in network interception
- Cross-browser with single API

**Q2: How does Playwright handle dynamic elements?**
- Auto-waiting for element readiness
- Smart retry mechanism
- No need for explicit waits
- Stable locators (getByRole, test IDs)

**Q3: What is BrowserContext in Playwright?**
- Isolated browser session
- Independent cookies and storage
- Multiple contexts per browser
- Useful for multi-user testing

**Q4: How do you handle iframes in Playwright?**
- Use frameLocator() method
- Chain for nested frames
- No need to switch back
- Works with Shadow DOM

**Q5: What is the difference between page and context?**
- Page: Single tab/window
- Context: Isolated session
- Multiple pages per context
- Contexts share browser

**Q6: How do you take screenshots in Playwright?**
- page.screenshot() for full page
- locator.screenshot() for element
- Options: fullPage, type, quality
- Can save to file or return bytes

**Q7: How do you handle file uploads?**
- setInputFiles() method
- Single or multiple files
- Dynamic file creation with FilePayload
- Works with file chooser

**Q8: What is the use of getByRole?**
- Accessibility-first locator
- Resilient to styling changes
- Works with Shadow DOM
- Recommended by Playwright

**Q9: How do you mock API responses in Playwright?**
- Use page.route() to intercept
- Use route.fulfill() to mock
- Set status, headers, body
- Useful for testing without backend

**Q10: How do you handle authentication in Playwright?**
- Save storage state (cookies, localStorage)
- Load state in new context
- Skip login for each test
- Reuse session across tests

### Java & TestNG

**Q11: What is the difference between @BeforeMethod and @BeforeClass?**
- @BeforeMethod: Runs before each test method
- @BeforeClass: Runs once before all tests in class
- Use @BeforeMethod for test-specific setup
- Use @BeforeClass for class-level setup

**Q12: How do you run tests in parallel with TestNG?**
- Configure parallel in testng.xml
- Use ThreadLocal for driver
- Set threadPoolSize in @Test
- Each thread gets own browser

**Q13: What is DataProvider in TestNG?**
- Provides data to test methods
- Test runs multiple times with different data
- Can be in same or different class
- Supports parallel execution

**Q14: How do you handle test dependencies in TestNG?**
- dependsOnMethods for method dependency
- dependsOnGroups for group dependency
- Skipped if dependency fails
- Use for ordered test execution

**Q15: What are TestNG listeners?**
- Interface for test lifecycle events
- onTestStart, onTestSuccess, onTestFailure
- Used for reporting, logging
- Can take screenshots on failure

### General Testing

**Q16: What is Page Object Model?**
- Design pattern for test automation
- Each page represented by class
- Separates test logic from page logic
- Improves maintainability

**Q17: What are the advantages of POM?**
- Reusability: Locators defined once
- Maintainability: Changes in one place
- Readability: Cleaner test code
- Separation of concerns

**Q18: What is the difference between functional and regression testing?**
- Functional: Tests specific features
- Regression: Tests after changes
- Functional: New features
- Regression: Existing features

**Q19: What is smoke testing?**
- Quick test of main features
- Checks if build is stable
- Subset of regression tests
- Performed on new builds

**Q20: What is the difference between verification and validation?**
- Verification: Are we building it right?
- Validation: Are we building the right thing?
- Verification: Code reviews, static testing
- Validation: Dynamic testing, user testing

### Scenario-Based Questions

**Q21: How do you handle a flaky test that fails intermittently?**
- Increase timeout
- Use more stable locators
- Add explicit waits
- Check for race conditions
- Use retry mechanism

**Q22: How do you test a file upload functionality?**
- Use setInputFiles() method
- Test with different file types
- Test with large files
- Test with multiple files
- Verify upload success

**Q23: How do you handle CAPTCHA in automation?**
- Cannot automate CAPTCHA
- Use test environment without CAPTCHA
- Use CAPTCHA bypass service (not recommended)
- Ask developers to disable for testing

**Q24: How do you handle dynamic IDs in automation?**
- Use stable attributes (data-testid)
- Use relative locators
- Use text-based locators
- Use getByRole
- Ask developers to add test IDs

**Q25: How do you handle slow-loading elements?**
- Rely on Playwright's auto-waiting
- Use waitForSelector if needed
- Increase timeout
- Use waitForLoadState
- Check for network idle

---

## CONCLUSION

This comprehensive guide covers all essential Playwright with Java topics for interview preparation. Key takeaways:

1. **Playwright Fundamentals**: Modern E2E framework with auto-waiting, Shadow DOM support
2. **Locators**: Prefer getByRole, use test IDs, avoid brittle selectors
3. **Waits**: Rely on auto-waiting, use explicit waits for specific conditions
4. **Interactions**: Use locator methods for element-specific actions
5. **Advanced**: Frames, windows, Shadow DOM, file operations
6. **POM**: Design pattern for maintainable tests
7. **TestNG**: Framework for test management
8. **Reporting**: ExtentReports for detailed reports
9. **Best Practices**: Clean code, independent tests, proper structure

Practice these concepts with real-world examples and be prepared to explain the "why" behind each approach. Good luck with your interview!
