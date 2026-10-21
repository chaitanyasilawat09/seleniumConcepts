# Selenium with Java - Complete Interview Preparation Guide

> **Last Updated:** October 2026 | **Selenium Version:** 4.x (4.20+) | **Java:** 8/11/17/21 | **Build:** Maven/TestNG
>
> **Purpose:** One document for everything — basics to advanced to deep dive. Selenium WebDriver + Java + TestNG + Framework + BDD + Grid + CI/CD, with cheatsheets, diagrams, ER diagrams and interview questions in every section.

---

## 📊 VISUAL QUICK REFERENCE

### 🖼️ Selenium Cheatsheet (One-Page Revision)
![Selenium Cheatsheet](./images/selenium-cheatsheet.png)

### ⚔️ Selenium vs Playwright
![Selenium vs Playwright](./images/selenium-vs-playwright.png)

### 🏗️ Selenium WebDriver Architecture (W3C)
![Selenium Architecture](./images/selenium-architecture.png)

### 🏗️ Page Object Model (POM)
![POM Structure](./images/selenium-pom-structure.png)

### 🏆 Locator Priority Pyramid
![Locator Priority](./images/selenium-locator-priority.png)

### ⏳ Wait Strategies
![Wait Strategies](./images/selenium-wait-strategies.png)

### 🌐 Selenium Grid 4 Architecture
![Selenium Grid](./images/selenium-grid.png)

### 🧬 Framework ER Diagram
![Framework ER Diagram](./images/selenium-framework-er.png)

---

## 🧠 QUICK RECALL - MEMORY TRICKS

### 🎯 "SELENIUM" Mnemonic (Remember All Features)
```
S - Switch (alerts, frames, windows) - switchTo()
E - Explicit Waits (WebDriverWait + ExpectedConditions)
L - Locators (8 types: id, name, class, tag, link, partial, css, xpath)
E - Elements (WebElement: click, sendKeys, getText)
N - Navigation (get, navigate, back, forward, refresh)
I - Implicit Wait (global timeout)
U - Utilities (Actions, Select, JavaScriptExecutor)
M - Manager (Selenium Manager auto-driver) & Multi-browser (Grid)
```

### 🔑 One-Liner Golden Rules
| Rule | Remember As |
|------|-------------|
| **findElement vs findElements** | "Singular throws, Plural returns empty list" |
| **Implicit wait** | "Set once, applies everywhere" |
| **Explicit wait** | "Wait karo, jab tak condition true" |
| **switchTo()** | "Alerts, Frames, Windows — teeno switchTo se" |
| **Select class** | "Only for `<select>` tag dropdowns" |
| **getText() vs getAttribute("value")** | "Visible text vs input field value" |
| **driver.close() vs quit()** | "Tab band vs Browser band" |
| **Page refresh = Stale Element** | "Element ka reference expire ho gaya" |
| **Absolute XPath** | "Kabhi mat use karo — brittle" |
| **W3C Protocol** | "Selenium 4 = W3C standard, no JSON Wire" |

### ⚡ Quick Patterns (Copy-Paste Ready)

```java
// ============ DRIVER SETUP ============
WebDriver driver = new ChromeDriver();   // Selenium Manager handles driver
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.get("https://example.com");

// ============ EXPLICIT WAIT ============
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn")));
el.click();

// ============ DROPDOWN ============
Select select = new Select(driver.findElement(By.id("country")));
select.selectByVisibleText("India");

// ============ ALERT ============
driver.switchTo().alert().accept();

// ============ FRAME ============
driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
driver.switchTo().defaultContent();

// ============ NEW WINDOW ============
Set<String> handles = driver.getWindowHandles();
for (String h : handles) { driver.switchTo().window(h); }

// ============ ACTIONS ============
new Actions(driver).moveToElement(menu).click(submenu).perform();
new Actions(driver).dragAndDrop(src, target).perform();

// ============ JS EXECUTOR ============
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView(true);", element);
js.executeScript("arguments[0].click();", element);

// ============ SCREENSHOT ============
File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(src, new File("screenshot.png"));

// ============ NEW TAB (Selenium 4) ============
driver.switchTo().newWindow(WindowType.TAB);

// ============ RELATIVE LOCATOR (Selenium 4) ============
driver.findElement(with(By.tagName("input")).below(By.cssSelector("label")));

// ============ PARALLEL-SAFE DRIVER ============
static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
tlDriver.set(new ChromeDriver());
tlDriver.get().get("url");
tlDriver.get().quit();
tlDriver.remove();
```

### 🎪 Selenium vs Playwright - Quick Recall Table
| Aspect | Selenium 4 | Playwright |
|--------|-----------|------------|
| Protocol | W3C WebDriver (HTTP) | WebSocket/CDP |
| Wait | Manual (WebDriverWait) | Auto-wait built-in |
| Locators | `findElement(By.*)` | `locator()` / `getBy*` |
| Tabs | Window handles | BrowserContext |
| Driver setup | Selenium Manager | Bundled browsers |
| Shadow DOM | `getShadowRoot()` | Native piercing |
| Network mock | CDP only | `page.route()` |
| Grid | Selenium Grid built-in | Third-party / containers |
| Best for | Legacy, cross-browser + IE/Safari, huge ecosystem | Speed, modern apps, mocking |

### 🔢 Numbers to Remember
| Number | What |
|--------|------|
| **8** | Locator types in Selenium |
| **3** | Wait types (Implicit, Explicit, Fluent) |
| **3** | Frame switch ways (index, name/id, WebElement) |
| **3** | Select methods (byVisibleText, byValue, byIndex) |
| **4** | Selenium components (IDE, WebDriver, Grid, RC-retired) |
| **5** | Grid 4 components (Router, Distributor, SessionMap, SessionQueue, Node + EventBus) |
| **4.6** | Version when Selenium Manager launched (auto drivers) |
| **10 sec** | Common default explicit wait |
| **500 ms** | Default FluentWait polling interval |
| **2000** | Selenium project started (Jason Huggins, ThoughtWorks) |

### 🏷️ Method Name Tricks
```
findElement*   -> singular = throws NoSuchElementException
                  plural   = returns List (empty, no exception)

getWindow*     -> getWindowHandle()  = current tab (String)
                  getWindowHandles() = all tabs (Set<String>)

selectBy*      -> selectByVisibleText / selectByValue / selectByIndex
                  deselectBy* = same three (multi-select only)

is*            -> isDisplayed() = visible on page?
                  isEnabled()   = clickable/editable?
                  isSelected()  = checkbox/radio checked?

get*           -> getText()           = visible text
                  getAttribute()      = HTML attribute (or property)
                  getDomAttribute()   = pure attribute (S4)
                  getDomProperty()    = JS property (S4)
                  getCssValue()       = computed CSS (color, font)

switchTo().*   -> alert() / frame() / window() / defaultContent()
                  / parentFrame() / activeElement() / newWindow()
```

### 🎭 Interview Quick Answers

**"How does Selenium work internally?"**
> Java bindings convert each command (click, findElement) into an HTTP request using the W3C WebDriver protocol → browser driver (chromedriver) receives it → executes in the real browser → returns JSON response back.

**"How do you handle dynamic elements?"**
> Explicit wait (WebDriverWait) with ExpectedConditions + stable locators (data attributes, relative XPath). Never Thread.sleep.

**"StaleElementReferenceException?"**
> DOM was refreshed after element was located — reference died. Fix: re-locate element inside a retry/wait, don't store elements long-term.

**"How do you run tests in parallel?"**
> ThreadLocal\<WebDriver\> + TestNG parallel="methods" in testng.xml; or Selenium Grid for distributed machines.

**"Difference between close() and quit()?"**
> close() = current window/tab only; quit() = entire browser session + driver process.

**"Why Page Object Model?"**
> Separation of page locators/actions from test logic — one UI change = one file fix; reusable, readable.

**"Selenium 4 new features?"**
> W3C protocol only, Relative locators, newWindow(TAB/WINDOW), element screenshots, Chrome DevTools API (CDP), Selenium Manager, improved Grid (distributed components), WebDriver BiDi (partial), Print to PDF.

**"get() vs navigate().to()?"**
> Both load a page. navigate() adds back/forward/refresh and doesn't necessarily wait for full load; get() waits for page load. Browser history only via navigate().

### 📝 Checklist Before Interview
```
□ Explain WebDriver architecture (W3C) without notes
□ Write all 8 locators from memory
□ Write explicit wait code in 30 seconds
□ Handle alert/frame/window code blindfolded
□ XPath axes: parent, following-sibling, ancestor, descendant
□ Select class 3 methods + multi-select deselect
□ Actions class: drag-drop, hover, double click, context click
□ JavaScriptExecutor: scroll, click, get title
□ ThreadLocal driver for parallel tests
□ POM vs PageFactory (@FindBy, @CacheLookup)
□ TestNG annotations order + DataProvider + retryAnalyzer
□ Screenshot on failure via ITestListener
□ Grid 4 components + docker standalone command
□ Selenium 4 features (relative locators, CDP, newWindow)
□ All common exceptions + their fixes
□ getText() vs getAttribute("value") difference
□ implicit + explicit together = BAD (unpredictable total wait)
```

### 🎨 Visual Memory Aid

```
┌─────────────────────────────────────────────────────────┐
│                  SELENIUM MIND MAP                       │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   WebDriver (Interface)                                 │
│        │                                                │
│        ├── ChromeDriver ──> chromedriver ──> Chrome     │
│        ├── FirefoxDriver ──> geckodriver ──> Firefox    │
│        ├── EdgeDriver ──> msedgedriver ──> Edge         │
│        └── RemoteWebDriver ──> Selenium Grid            │
│                                                         │
│   driver.findElement(By.id/css/xpath) ──> WebElement    │
│        ├── .click()  .sendKeys()  .getText()            │
│        ├── .isDisplayed() .isEnabled() .isSelected()    │
│        └── .getAttribute() .getCssValue()               │
│                                                         │
│   driver.switchTo()                                     │
│        ├── .alert()   -> accept/dismiss/getText         │
│        ├── .frame()   -> index / name / element         │
│        ├── .window()  -> window handle                  │
│        └── .newWindow(TAB/WINDOW)  [Selenium 4]         │
│                                                         │
│   Waits: implicit (global) | explicit (condition)       │
│          fluent (poll + ignore)                         │
│                                                         │
│   Helper classes: Select | Actions | JavascriptExecutor │
│                   TakesScreenshot | WebDriverWait       │
│                                                         │
│   Selenium 4: relative locators | CDP | Manager | Grid  │
└─────────────────────────────────────────────────────────┘
```

### 🚀 Speed Tricks
| Trick | Saves |
|-------|-------|
| `driver.findElements().size()` for existence check | No exception handling |
| Explicit wait ONLY where needed | Faster suite |
| Headless mode in CI | ~30-40% faster |
| `reuseWindow`/session reuse | Skip login each test |
| Disable images via ChromeOptions | Faster page load |
| Grid + parallel | N tests in parallel |
| CSS over XPath where possible | Slightly faster, cleaner |
| `driver.manage().deleteAllCookies()` | Fresh state, fast |

---

## TABLE OF CONTENTS

### Core Concepts
1. [Selenium Fundamentals](#1-selenium-fundamentals)
2. [Architecture & How WebDriver Works](#2-selenium-architecture--how-webdriver-works)
3. [Browser Setup & WebDriver](#3-browser-setup--webdriver)
4. [Locators in Selenium](#4-locators-in-selenium)
5. [XPath Deep Dive](#5-xpath-deep-dive)
6. [CSS Selectors Deep Dive](#6-css-selectors-deep-dive)
7. [Wait Strategies](#7-wait-strategies)

### Web Element Handling
8. [WebElement Interactions](#8-webelement-interactions)
9. [Dropdown Handling](#9-dropdown-handling)
10. [Actions Class (Mouse & Keyboard)](#10-actions-class-mouse--keyboard)
11. [JavaScriptExecutor](#11-javascriptexecutor)
12. [Alerts & Popups](#12-alerts--popups)
13. [Frames & iFrames](#13-frames--iframes)
14. [Window & Tab Handling](#14-window--tab-handling)
15. [File Upload & Download](#15-file-upload--download)
16. [Cookies Management](#16-cookies-management)
17. [Screenshots](#17-screenshots)
18. [Navigation Commands](#18-navigation-commands)
19. [Web Tables & Dynamic Elements](#19-web-tables--dynamic-elements)
20. [Shadow DOM](#20-shadow-dom)

### Framework Integration
21. [Selenium 4 New Features](#21-selenium-4-new-features)
22. [Page Object Model (POM)](#22-page-object-model-pom)
23. [PageFactory (@FindBy)](#23-pagefactory-findby)
24. [TestNG Framework Integration](#24-testng-framework-integration)
25. [TestNG Listeners & Retry](#25-testng-listeners--retry)
26. [Data-Driven Testing](#26-data-driven-testing)
27. [Reporting with ExtentReports](#27-reporting-with-extentreports)
28. [Real Project Framework Structure](#28-real-project-framework-structure)
29. [BDD/Cucumber with Selenium](#29-bddcucumber-with-selenium)

### Advanced Topics
30. [Selenium Grid & Parallel Execution](#30-selenium-grid--parallel-execution)
31. [CI/CD Integration](#31-cicd-integration)
32. [Common Exceptions & Flaky Test Fixes](#32-common-exceptions--flaky-test-fixes)
33. [Java Programming for Interviews](#33-java-programming-for-interviews)
34. [Best Practices & Design Patterns](#34-best-practices--design-patterns)
35. [Common Interview Questions](#35-common-interview-questions)

---

## 1. SELENIUM FUNDAMENTALS

### What is Selenium?
Selenium is an **open-source suite of tools** for automating web browsers. It supports multiple browsers (Chrome, Firefox, Edge, Safari), multiple languages (Java, Python, C#, JS, Ruby), and multiple platforms.

### History (Good for interview context)
| Year | Milestone |
|------|-----------|
| 2004 | Selenium Core / RC — Jason Huggins (ThoughtWorks) |
| 2006 | WebDriver created by Simon Stewart (Google) |
| 2011 | Selenium 2 = RC + WebDriver merged |
| 2016 | Selenium 3 — RC removed, WebDriver core |
| 2021 | **Selenium 4 — W3C WebDriver standard, new features** |

### Key Components of Selenium
1. **Selenium IDE** — Browser plugin for record & playback (quick prototypes, not for real frameworks)
2. **Selenium WebDriver** — Core API to control browsers programmatically (what interviews are about)
3. **Selenium Grid** — Run tests in parallel across machines/browsers
4. **Selenium RC** — Remote Control — **RETIRED** (needed a proxy server; replaced by WebDriver)

### Advantages & Limitations
**Advantages:**
- Open source, massive community, huge ecosystem
- True cross-browser (including Safari via safaridriver, legacy IE)
- Works with any language/framework (TestNG, JUnit, Cucumber, Maven, Jenkins)
- Grid for distributed execution
- W3C standard protocol — vendor-neutral

**Limitations:**
- No built-in auto-waiting (manual wait management)
- No built-in reporting (need ExtentReports/Allure/TestNG)
- No native network interception/mocking (need CDP or proxies)
- Desktop web only (mobile = Appium)
- Can't handle OS-level dialogs (file upload dialogs — workarounds needed)
- Captcha/reCAPTCHA can't be automated (by design)

### Interview Questions

**Q1: What are the components of Selenium suite?**
- IDE (record/playback), WebDriver (API), Grid (parallel/distributed). RC is retired.

**Q2: Selenium 3 vs Selenium 4?**
- S3 used JSON Wire Protocol (custom); S4 is pure W3C standard
- S4 adds: relative locators, `newWindow()`, element screenshots, CDP access, Selenium Manager, re-architected Grid, improved docs

**Q3: Is WebDriver a class or interface?**
- `WebDriver` is an **interface**. `RemoteWebDriver` is the main implementing class; `ChromeDriver`, `FirefoxDriver` etc. extend it.

**Q4: Can Selenium automate desktop/mobile apps?**
- No — web only. Mobile = Appium (which uses WebDriver protocol). Desktop = WinAppDriver/AutoIt.

**Q5: Why can't Selenium handle Captcha?**
- Captcha exists specifically to block automation. Bypass = use test keys from the Captcha provider, or disable on test env, or manual OTP step.

---

## 2. SELENIUM ARCHITECTURE & HOW WEBDRIVER WORKS

### How WebDriver Works (Internal Flow)
```
Java Test Code
      │  driver.findElement(By.id("x")).click()
      ▼
Selenium Client Library (Java bindings)
      │  converts command → HTTP POST /session/{id}/element
      ▼      (W3C WebDriver Protocol = JSON over HTTP)
Browser Driver (chromedriver.exe)  ← starts a local HTTP server
      │  translates → browser-internal protocol (CDP for Chrome)
      ▼
Real Browser executes → response travels back the same chain
```

### JSON Wire Protocol vs W3C Protocol
| | JSON Wire (Selenium 3) | W3C (Selenium 4) |
|---|---|---|
| Standard | Selenium-specific | Official W3C standard |
| Encoding | Needed encode/decode layer | Direct communication |
| Actions API | Legacy | New richer Actions spec |
| Status | Deprecated | Current |

**Interview line:** "Selenium 4 communicates directly with the browser driver via the W3C standard — no encoding/decoding layer like JSON Wire, making it faster and more reliable."

### Selenium Manager (4.6+)
- Automatically detects browser version → downloads correct driver → caches it
- Replaces manual `System.setProperty()` and WebDriverManager dependency
- `WebDriver driver = new ChromeDriver();` just works

### Selenium IDE vs WebDriver vs Grid
| Tool | Purpose |
|------|---------|
| IDE | Record & playback — learning/prototyping |
| WebDriver | Programmatic browser automation — the real deal |
| Grid | Parallel execution on multiple machines/browsers |

### Interview Questions

**Q1: Explain the WebDriver architecture.**
- See diagram above: Client → W3C HTTP → Driver → Browser. Emphasize each command = one HTTP request/response.

**Q2: What is chromedriver?**
- A standalone executable (HTTP server) that implements the W3C WebDriver protocol and translates commands into Chrome DevTools Protocol calls.

**Q3: Why was JSON Wire Protocol removed?**
- Non-standard, extra encoding layer, inconsistent implementations. W3C makes every browser vendor follow one spec.

**Q4: What is Selenium Manager?**
- Built-in tool (Selenium 4.6+) that auto-resolves, downloads and caches the correct browser driver. CLI: `selenium-manager --browser chrome`.

**Q5: What happens when you write `new ChromeDriver()`?**
- Selenium Manager resolves driver → driver executable starts on a port → creates a session (POST /session) → returns session id → subsequent commands carry that session id.

---

## 3. BROWSER SETUP & WEBDRIVER

### What is WebDriver?
`WebDriver` is the root interface. Hierarchy:
```
SearchContext ← WebDriver ← RemoteWebDriver ← ChromeDriver / FirefoxDriver / EdgeDriver
(WebElement also implements SearchContext)
```
`JavascriptExecutor` and `TakesScreenshot` are separate interfaces implemented by RemoteWebDriver.

### 3.1 Basic Setup

```java
// Modern way — Selenium Manager auto-resolves driver (4.6+)
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.get("https://www.google.com");
driver.quit();
```

```java
// Legacy way (pre-4.6 / behind corporate proxy)
System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
WebDriver driver = new ChromeDriver();
```

```java
// WebDriverManager library (external, still used in older projects)
WebDriverManager.chromedriver().setup();
WebDriver driver = new ChromeDriver();
```

### 3.2 Browser Drivers
| Browser | Driver class | Executable |
|---------|-------------|------------|
| Chrome | `ChromeDriver` | chromedriver |
| Firefox | `FirefoxDriver` | geckodriver |
| Edge | `EdgeDriver` | msedgedriver |
| Safari | `SafariDriver` | safaridriver (built-in, enable via `safaridriver --enable`) |
| Remote | `RemoteWebDriver` | Grid URL |

### 3.3 Browser Options (Capabilities)

```java
// ChromeOptions
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless=new");          // headless (new headless in Chrome 112+)
options.addArguments("--incognito");
options.addArguments("--start-maximized");
options.addArguments("--disable-notifications");
options.addArguments("--window-size=1920,1080");
options.addArguments("--disable-extensions");
options.addArguments("user-agent=CustomAgent");
options.setAcceptInsecureCerts(true);            // accept self-signed SSL
options.setPageLoadStrategy(PageLoadStrategy.EAGER); // NORMAL|EAGER|NONE

// disable password manager popup / infobars
options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
Map<String, Object> prefs = new HashMap<>();
prefs.put("credentials_enable_service", false);
prefs.put("profile.password_manager_enabled", false);
prefs.put("download.default_directory", "/tmp/downloads");   // download dir
options.setExperimentalOption("prefs", prefs);

WebDriver driver = new ChromeDriver(options);
```

```java
// FirefoxOptions
FirefoxOptions fo = new FirefoxOptions();
fo.addArguments("-headless");
fo.setBinary("/custom/firefox/path");
fo.addPreference("browser.download.dir", "/tmp");
WebDriver fdriver = new FirefoxDriver(fo);
```

```java
// EdgeOptions — same pattern as Chrome
EdgeOptions eo = new EdgeOptions();
eo.addArguments("--headless=new");
```

### 3.4 Driver Commands (get/manage)
```java
driver.get("https://app.com");                    // open URL (waits for load)
driver.getTitle();                                // page title
driver.getCurrentUrl();                           // current URL
driver.getPageSource();                           // full HTML
driver.manage().window().maximize();
driver.manage().window().setSize(new Dimension(1280, 800));
driver.manage().window().setPosition(new Point(0, 0));
driver.manage().window().fullscreen();
driver.manage().deleteAllCookies();
driver.close();                                   // current window
driver.quit();                                    // all windows + kill driver
```

### 3.5 Thread-Safe Driver (ThreadLocal) — for Parallel Execution
```java
public class DriverFactory {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static void setDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox": tlDriver.set(new FirefoxDriver()); break;
            default:        tlDriver.set(new ChromeDriver());
        }
    }
    public static WebDriver getDriver() { return tlDriver.get(); }
    public static void quitDriver() {
        getDriver().quit();
        tlDriver.remove();           // prevent memory leak!
    }
}
```

### Interview Questions

**Q1: Is `WebDriver driver = new WebDriver()` valid?**
- No — WebDriver is an interface. You instantiate concrete classes (`new ChromeDriver()`). `WebDriver driver = new ChromeDriver()` = upcasting, lets you swap browsers.

**Q2: `close()` vs `quit()`?**
- `close()` closes the current focused window/tab; session continues if other windows open. `quit()` destroys the whole session.

**Q3: How to run headless?**
- `options.addArguments("--headless=new")` (Chrome 112+). Old `--headless` deprecated.

**Q4: What is `setAcceptInsecureCerts`?**
- Skips "Your connection is not private" SSL warning pages on test environments.

**Q5: Why ThreadLocal for driver?**
- In parallel TestNG runs, each thread gets its own WebDriver instance — prevents one test closing another's browser. Always `remove()` after `quit()`.

**Q6: PageLoadStrategy values?**
- NORMAL (wait full load, default), EAGER (DOM ready, resources may load), NONE (return immediately — handle waits yourself).

---

## 4. LOCATORS IN SELENIUM

### What is a Locator?
A strategy to find a `WebElement` in the DOM. Selenium has **8 locators** via the `By` class.

### The 8 Locators

```java
driver.findElement(By.id("username"));                    // 1. id        ★ best
driver.findElement(By.name("email"));                     // 2. name
driver.findElement(By.className("btn-primary"));          // 3. className (single class only!)
driver.findElement(By.tagName("input"));                  // 4. tagName
driver.findElement(By.linkText("Click Here"));            // 5. linkText (exact, <a> only)
driver.findElement(By.partialLinkText("Click"));          // 6. partialLinkText
driver.findElement(By.cssSelector("input#email"));        // 7. cssSelector ★ recommended
driver.findElement(By.xpath("//input[@id='email']"));     // 8. xpath
```

### findElement vs findElements — INTERVIEW FAVORITE
```java
driver.findElement(By.id("x"));      // returns WebElement
                                     // NoSuchElementException if not found!

List<WebElement> els = driver.findElements(By.className("item"));
els.size();                          // 0 if none — NO exception!
els.isEmpty();                       // safe existence check
```

```java
// Safe element-exists check (no try/catch needed)
boolean exists = !driver.findElements(By.id("popup")).isEmpty();
```

### Locator Priority (Interview favorite!)
```
id  >  name  >  cssSelector  >  xpath (relative)  >  linkText  >  className/tagName
```
- **id** — unique, fastest
- **cssSelector** — fast, readable, no text search
- **xpath** — most powerful (traverse up/down DOM, text matching) but slower & fragile
- **linkText** — exact text of `<a>`
- **className** — fails on compound classes (`class="a b"`) — use css `.a.b`

### Relative Locators (Selenium 4 NEW!)
```java
import static org.openqa.selenium.support.locators.RelativeLocator.with;

// above / below / toLeftOf / toRightOf / near
WebElement label = driver.findElement(By.cssSelector("label[for='pwd']"));
driver.findElement(with(By.tagName("input")).below(label)).sendKeys("secret");

driver.findElement(with(By.tagName("button")).toRightOf(By.id("cancel"))).click();
driver.findElement(with(By.tagName("input")).near(By.id("email"))).click();

// chaining
driver.findElement(with(By.tagName("input"))
        .below(By.cssSelector("h3")).above(By.id("footer")));
```

### Interview Questions

**Q1: How many locators does Selenium have?**
- 8: id, name, className, tagName, linkText, partialLinkText, cssSelector, xpath.

**Q2: findElement vs findElements?**
- Singular: returns first match, throws `NoSuchElementException`. Plural: returns `List<WebElement>`, empty list if none.

**Q3: Why is className fragile?**
- Only matches ONE class. `By.className("btn btn-primary")` → `Compound class names not permitted`. Use `By.cssSelector(".btn.btn-primary")`.

**Q4: CSS vs XPath — which is faster?**
- CSS is natively faster in browsers and can't traverse upward. XPath can traverse any direction and match text. Modern difference is small; choose CSS for readability, XPath for complex traversal/text.

**Q5: How to locate a button right of a textbox without unique attributes?**
- Selenium 4 relative locator: `with(By.tagName("button")).toRightOf(By.id("txt"))`.

**Q6: How to check element exists without exception?**
- `driver.findElements(locator).size() > 0` or `.isEmpty()` check.

---

## 5. XPATH DEEP DIVE

### What is XPath?
XML Path Language — navigates DOM nodes. Two types:
- **Absolute:** `/html/body/div[1]/form/input` — brittle, breaks on any UI change. NEVER use.
- **Relative:** `//input[@id='email']` — starts anywhere, stable. ALWAYS use.

### 5.1 XPath Syntax
```xpath
//tag[@attribute='value']              → basic
//input[@id='username']                → by attribute
//button[text()='Login']               → by exact text
//button[.='Login']                    → same, includes nested text
//*[@id='x']                           → any tag
//input[@type='text' and @name='q']    → AND
//input[@id='a' or @id='b']            → OR
//a[1] / (//a)[last()]                 → index / last
```

### 5.2 XPath Functions
```xpath
//button[contains(text(),'Log')]           → partial text
//input[contains(@id,'user')]              → partial attribute  ★ dynamic ids
//a[starts-with(@href,'https')]            → prefix match
//span[ends-with(text(),'Inc.')]           → (XPath 2.0, limited support)
//input[not(@disabled)]                    → negation
//div[normalize-space(text())='Hello']     → trims whitespace  ★
//option[position()=2]                     → position
//td[text()='John']/following-sibling::td  → sibling cell
```

### 5.3 XPath Axes (Traversing DOM) — INTERVIEW FAVORITE
```xpath
child::            //div[@id='form']/child::input          direct children
parent::           //input[@id='email']/parent::div         go UP one level
ancestor::         //input[@id='email']/ancestor::form      any ancestor
descendant::       //form/descendant::input                 all nested inputs
following::        //h2[text()='Login']/following::button   anywhere after
preceding::        //button[@id='submit']/preceding::input  anywhere before
following-sibling:://label[text()='Name']/following-sibling::input   ★ classic
preceding-sibling:://input[@id='email']/preceding-sibling::label
self::             //div/self::div
```

**Classic interview XPath:**
```xpath
//label[text()='Password']/following-sibling::input
//td[text()='John']/following-sibling::td[2]         → sibling cell in row
//a[text()='Edit']/ancestor::tr                      → whole table row
//div[@class='card'][.//h3='Product']                → parent by child text
```

### 5.4 Common XPath Patterns
```java
// Dynamic id (id keeps changing): match stable part
By.xpath("//input[contains(@id,'user')]")           // id="user_82347"

// Text with whitespace
By.xpath("//button[normalize-space()='Submit']")

// Nested element
By.xpath("//div[@class='menu']//a[text()='Logout']")

// Index
By.xpath("(//input[@type='text'])[2]")              // brackets for full-doc index!

// Last element
By.xpath("(//div[@class='item'])[last()]")
```

### Interview Questions

**Q1: Difference between `/` and `//`?**
- `/` = direct child only. `//` = anywhere below (descendant search).

**Q2: Absolute vs relative XPath?**
- Absolute starts `/html/...`, breaks on any DOM change. Relative starts `//` and uses attributes — stable.

**Q3: How to select a parent from a child in XPath?**
- `/parent::div` or `/ancestor::form` — CSS CANNOT do this (favorite trick question).

**Q4: `contains()` vs `text()`?**
- `text()='x'` needs exact match; `contains(text(),'x')` partial; `contains(@id,'x')` partial attribute for dynamic ids.

**Q5: Can XPath find element by visible text?**
- Yes: `//button[text()='Save']` or `contains(text(),...)`. CSS selectors cannot match by text (except `:has-text` pseudo in some engines).

---

## 6. CSS SELECTORS DEEP DIVE

### What is CSS Selector?
Pattern language to match elements — faster & cleaner than XPath, but can't traverse upward or match text.

### 6.1 CSS Syntax Cheat Sheet
```css
#id                      → by id
.className               → by class
tag.class                → tag with class
#id.class                → combined
[attr]                   → has attribute
[attr='val']             → exact attr
[attr^='pre']            → starts-with
[attr$='post']           → ends-with
[attr*='mid']            → contains
parent > child           → DIRECT child only
ancestor descendant      → any nested descendant
sib1 + sib2              → IMMEDIATE next sibling
sib1 ~ sib2              → ANY following sibling
:nth-child(n)            → li:nth-child(2)
:first-child / :last-child
:nth-of-type(n)          → p:nth-of-type(3)
:not(.x)                 → negation
input[type='checkbox']:checked
```

```java
// Examples
By.cssSelector("input#email");
By.cssSelector("button.btn.btn-primary");            // compound class
By.cssSelector("input[name='username']");
By.cssSelector("input[id^='user_']");                // dynamic id start
By.cssSelector("a[href$='.pdf']");
By.cssSelector("div.login-form input[type='password']");
By.cssSelector("ul#menu > li:nth-child(3)");
By.cssSelector("label[for='agree'] + input");        // immediate sibling
```

### CSS vs XPath Comparison
| Feature | CSS | XPath |
|---------|-----|-------|
| Speed | Slightly faster | Slightly slower |
| Traverse to parent | ❌ | ✅ `parent::`, `ancestor::` |
| Text match | ❌ | ✅ `text()`, `contains()` |
| Readability | Cleaner | Verbose |
| Direction | Down/forward only | Any direction |
| Sibling | `+` `~` forward only | both directions via axes |

### Interview Questions

**Q1: Write CSS for "input whose id starts with txt".**
- `input[id^='txt']` — `^=` start, `$=` end, `*=` contains.

**Q2: Difference between `>` and space in CSS?**
- `div > p` = direct child; `div p` = any descendant.

**Q3: Can CSS locate by text?**
- Standard CSS — no. (Playwright's `:has-text` is a Playwright extension, not valid CSS.)

**Q4: `+` vs `~` siblings?**
- `+` = the very next sibling only; `~` = any following sibling.

## 7. WAIT STRATEGIES

### Why Waits?
Web pages load asynchronously. Without waits → `NoSuchElementException`, flaky tests. Selenium does NOT auto-wait (unlike Playwright), so you manage waits.

### 7.1 Implicit Wait
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```
- Set **once** per session, applies to **every** `findElement`
- Polls DOM until timeout, throws `NoSuchElementException` after
- ⚠️ Don't mix large implicit + explicit waits — waits can stack unpredictably

### 7.2 Explicit Wait (WebDriverWait)
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

WebElement el = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
el.click();
```

### 7.3 ExpectedConditions — Full List
```java
// VISIBILITY
visibilityOfElementLocated(By.id("x"))        // in DOM + visible
invisibilityOfElementLocated(By.id("loader"))
presenceOfElementLocated(By.id("x"))          // in DOM (may be hidden)

// CLICK / INTERACTION
elementToBeClickable(By.id("btn"))            // visible + enabled  ★ use for clicks
elementToBeSelected(By.cssSelector("input[type='checkbox']"))
elementSelectionStateToBe(By.id("cb"), false)

// TEXT / ATTRIBUTE
textToBePresentInElementLocated(By.id("msg"), "Success")
textToBe(By.id("status"), "Done")
attributeToBe(By.id("btn"), "class", "active")
attributeContains(By.id("btn"), "class", "dis")

// FRAME / ALERT / WINDOW
frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe"))
alertIsPresent()
numberOfWindowsToBe(2)
titleIs("Dashboard")
titleContains("Dash")
urlToBe("https://app.com/home")
urlContains("/home")

// STALENESS / STATE
stalenessOf(oldElement)                       // wait until element detached
refreshed(ExpectedConditions.elementToBeClickable(by))  // survives refresh ★

// COMBINE
ExpectedConditions.and(cond1, cond2)
ExpectedConditions.or(cond1, cond2)
ExpectedConditions.not(cond1)
```

### 7.4 Fluent Wait
```java
FluentWait<WebDriver> fWait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(30))          // total
        .pollingEvery(Duration.ofSeconds(2))          // poll interval
        .ignoring(NoSuchElementException.class)        // ignore during polling
        .ignoring(StaleElementReferenceException.class);

WebElement el = fWait.until(d -> d.findElement(By.id("dynamic")));
```

- Use for elements appearing after unpredictable delays (websocket updates, long polling).

### 7.5 Thread.sleep (Avoid!)
```java
Thread.sleep(5000);   // ❌ hard-coded; slow & still flaky
```
Only acceptable for quick debugging — never in frameworks.

### Wait Comparison Table
| Wait | Scope | Poll | Condition | When |
|------|-------|------|-----------|------|
| Implicit | Global (all finds) | internal | presence only | baseline safety net |
| Explicit | One element | internal | any ExpectedCondition | **default choice** |
| Fluent | One element | configurable + ignore exceptions | custom lambda | irregular/dynamic timing |
| Thread.sleep | — | — | none | never in prod code |

### Interview Questions

**Q1: Implicit vs Explicit wait?**
- Implicit = global blanket timeout for findElement presence. Explicit = per-element, condition-based (clickable, visible, text). Explicit is preferred; implicit alone can't wait for "clickable".

**Q2: What happens if you set both implicit 30s and explicit 30s?**
- They can compound unpredictably — Selenium docs say don't mix. Total wait may reach 60s. Use small implicit (or zero) + explicit waits.

**Q3: Which ExpectedCondition for clicking?**
- `elementToBeClickable` — waits for visible AND enabled.

**Q4: How to wait for a page title?**
- `wait.until(ExpectedConditions.titleContains("Dashboard"))`.

**Q5: FluentWait vs WebDriverWait?**
- WebDriverWait IS a FluentWait subclass with fixed defaults. Fluent adds custom `pollingEvery` and `ignoring` lists.

**Q6: How do you wait for a loader/spinner to disappear?**
- `wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner")))`.

---

## 8. WEBELEMENT INTERACTIONS

### What is WebElement?
Interface representing a DOM element — returned by `findElement`. Also implements `SearchContext` (so you can search inside an element).

### 8.1 Basic Actions
```java
WebElement el = driver.findElement(By.id("btn"));

el.click();                          // click
el.sendKeys("hello");                // type text
el.clear();                          // clear input
el.submit();                         // submit enclosing form (rarely used now)
el.getText();                        // visible inner text
el.getTagName();                     // "input", "div"...
el.getAttribute("value");            // attribute OR property value
el.getDomAttribute("readonly");      // raw HTML attribute (Selenium 4)
el.getDomProperty("value");          // live JS property (Selenium 4)
el.getCssValue("color");             // "rgba(255,0,0,1)"
el.getCssValue("font-size");
el.isDisplayed();                    // visible?
el.isEnabled();                      // enabled?
el.isSelected();                     // checkbox/radio/option selected?
el.getSize();                        // Dimension (width, height)
el.getLocation();                    // Point (x, y)
el.getRect();                        // x,y + width,height (Selenium 4)
el.getAriaRole();                    // ARIA role (Selenium 4)
el.getAccessibleName();              // accessible name (Selenium 4)

// search within element (scope to container)
WebElement table = driver.findElement(By.id("tbl"));
WebElement cell = table.findElement(By.xpath(".//td[2]"));   // note the "."
```

### 8.2 Keyboard Keys with sendKeys
```java
import org.openqa.selenium.Keys;

el.sendKeys(Keys.ENTER);
el.sendKeys(Keys.TAB);
el.sendKeys(Keys.BACK_SPACE);
el.sendKeys(Keys.ARROW_DOWN);
el.sendKeys(Keys.CONTROL + "a");            // select all
el.sendKeys(Keys.CONTROL, "c");             // copy
el.sendKeys(Keys.chord(Keys.CONTROL, "a")); // chord = held modifiers
el.sendKeys("text" + Keys.ENTER);
```

### 8.3 getText() vs getAttribute("value") — INTERVIEW FAVORITE
```java
// <div>Hello World</div>
div.getText();                    // "Hello World" (visible text)

// <input value="John">
input.getText();                  // "" — inputs have no inner text!
input.getAttribute("value");      // "John" ★ typed text lives here
```

| Method | Returns |
|--------|---------|
| `getText()` | Visible inner text (what user sees between tags) |
| `getAttribute("value")` | value attribute/property — for input fields |
| `getDomProperty("value")` | live JS property (current typed value) |

### 8.4 isDisplayed vs isEnabled vs isSelected
```java
el.isDisplayed();   // rendered & occupies space (hidden/display:none → false)
el.isEnabled();     // not disabled attribute
el.isSelected();    // checked state — checkbox, radio, <option>
```

### Interview Questions

**Q1: getText() vs getAttribute("value")?**
- getText = visible text between tags; getAttribute("value") = the `value` property — use for input fields.

**Q2: How to check a checkbox is checked?**
- `isSelected()`.

**Q3: Element not clickable even though visible?**
- Check `isEnabled()`, overlays may intercept → use `elementToBeClickable` wait or JS click.

**Q4: getDomAttribute vs getDomProperty (Selenium 4)?**
- `getDomAttribute` = literal HTML attribute from markup; `getDomProperty` = current JS property (e.g., `checked`, live `value`). Old `getAttribute` tries property first, then attribute.

**Q5: How to find an element inside another element?**
- `parent.findElement(By.xpath(".//span"))` — note the leading `.` to scope within parent.

---

## 9. DROPDOWN HANDLING

### What is Select Class?
`org.openqa.selenium.support.ui.Select` — **only works on `<select>` tags**.

### 9.1 Select Class Methods
```java
Select select = new Select(driver.findElement(By.id("country")));

select.selectByVisibleText("India");       // by shown text ★
select.selectByValue("IN");                // by value attribute
select.selectByIndex(2);                   // 0-based index

select.deselectAll();                      // multi-select only
select.deselectByVisibleText("India");
select.deselectByValue("IN");
select.deselectByIndex(2);

select.isMultiple();                       // <select multiple>?
select.getOptions();                       // List<WebElement> all options
select.getAllSelectedOptions();
select.getFirstSelectedOption();
select.getWrappedElement();                // the <select> element itself
```

```java
// Print all options
Select s = new Select(driver.findElement(By.id("country")));
for (WebElement opt : s.getOptions()) {
    System.out.println(opt.getText());
}
```

### 9.2 Custom/Bootstrap Dropdowns (No `<select>`)
```java
// Bootstrap/React dropdowns are div/ul/li — Select class FAILS!
driver.findElement(By.cssSelector(".dropdown-toggle")).click();  // open

List<WebElement> options = driver.findElements(
        By.cssSelector(".dropdown-menu li a"));
for (WebElement opt : options) {
    if (opt.getText().equals("Settings")) {
        opt.click();
        break;
    }
}
```

### 9.3 Auto-suggest Dropdown (Google style)
```java
WebElement box = driver.findElement(By.id("search"));
box.sendKeys("seleni");
Thread.sleep(1000); // demo only — use explicit wait in real code
List<WebElement> suggestions = driver.findElements(
        By.cssSelector("ul[role='listbox'] li"));
for (WebElement s : suggestions) {
    if (s.getText().contains("selenium webdriver")) {
        s.click();
        break;
    }
}
// or keyboard: box.sendKeys(Keys.ARROW_DOWN); box.sendKeys(Keys.ENTER);
```

### 9.4 jQuery/Multi-select Dropdown
```java
// select-all checkbox pattern common in apps
driver.findElement(By.cssSelector(".msdd")).click();
driver.findElements(By.cssSelector("ul li input[type='checkbox']"))
      .forEach(WebElement::click);           // select all
driver.findElement(By.cssSelector(".msdd")).click();  // close
```

### Interview Questions

**Q1: Which class handles dropdowns?**
- `Select` class — but only for `<select>` tags. Bootstrap/React dropdowns need findElements + click loop.

**Q2: Three ways to select an option?**
- `selectByVisibleText`, `selectByValue`, `selectByIndex`.

**Q3: What exception if Select is used on a non-select element?**
- `UnexpectedTagNameException` ("Element should have been select but was div").

**Q4: How to verify dropdown has expected options?**
- `getOptions()` → collect `getText()` into `List<String>` → compare with expected list.

**Q5: Handle autosuggestion (Google search)?**
- Type partial text → wait → `findElements` on suggestion list → match & click; or keyboard arrow + ENTER.

---

## 10. ACTIONS CLASS (MOUSE & KEYBOARD)

### What is Actions Class?
`org.openqa.selenium.interactions.Actions` — chains complex user gestures (mouse moves, drag-drop, key combos). In Selenium 4 it follows the **W3C Actions spec** (input sources: pointer/key/wheel).

### 10.1 Mouse Actions
```java
Actions actions = new Actions(driver);

actions.moveToElement(menu).perform();                        // hover
actions.click(element).perform();
actions.doubleClick(element).perform();                       // double click
actions.contextClick(element).perform();                      // RIGHT click
actions.clickAndHold(slider).moveByOffset(100, 0).release().perform();  // slider
actions.dragAndDrop(source, target).perform();                // drag & drop
actions.dragAndDropBy(source, 150, 0).perform();              // drag by offset
actions.moveToElement(el).click().perform();                  // hover+click
actions.moveByOffset(50, 50).click().perform();               // click at offset
actions.pause(Duration.ofSeconds(1)).perform();               // pause in chain
```

### 10.2 Keyboard Actions
```java
actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform(); // Ctrl+A
actions.keyDown(Keys.SHIFT).sendKeys("hello").keyUp(Keys.SHIFT).perform(); // HELLO
actions.sendKeys(Keys.TAB).perform();
actions.sendKeys(element, "text").perform();                   // type into element
```

### 10.3 Chaining Multiple Actions
```java
// build() compiles, perform() executes — often combined: .build().perform()
actions.moveToElement(menu)
       .pause(Duration.ofMillis(500))
       .moveToElement(subMenu)
       .pause(Duration.ofMillis(300))
       .click(item)
       .build()
       .perform();
```

### 10.4 Scroll Actions (Selenium 4 — W3C wheel input)
```java
actions.scrollToElement(element).perform();            // scroll until visible
actions.scrollByAmount(0, 500).perform();              // scroll down 500px
actions.scrollFromOrigin(WheelInput.ScrollOrigin
        .fromElement(element), 0, 200).perform();      // scroll inside element
```

### Interview Questions

**Q1: What is the Actions class?**
- API for complex gestures — mouse hover, drag-drop, keyboard combos. Every action chain must end with `perform()`.

**Q2: build() vs perform()?**
- `build()` compiles the sequence into an action object; `perform()` executes (and implicitly builds). `perform()` alone is usually enough.

**Q3: How to drag and drop?**
- `actions.dragAndDrop(src, tgt).perform()` — if it fails (HTML5 DnD), use JS executor or clickAndHold+moveToElement+release.

**Q4: How to right click?**
- `actions.contextClick(element).perform()`.

**Q5: How to hover over a menu then click submenu?**
- `moveToElement(menu).pause(...).moveToElement(sub).click().perform()`.

**Q6: Drag-and-drop not working — alternative?**
- HTML5 drag-drop sometimes ignores synthetic events → use JavascriptExecutor to dispatch `dragstart/dragover/drop` events, or clickAndHold + moveByOffset + release.

---

## 11. JAVASCRIPTEXECUTOR

### What is JavaScriptExecutor?
Interface to run raw JS inside the browser. `RemoteWebDriver` implements it. Use when normal WebDriver actions fail (hidden elements, disabled fields, overlays, scrolling).

### 11.1 Execute JavaScript
```java
JavascriptExecutor js = (JavascriptExecutor) driver;

js.executeScript("alert('hi')");                          // sync script
Object result = js.executeScript("return document.title"); // returns value!
js.executeAsyncScript("setTimeout(arguments[arguments.length-1], 2000)"); // async
```

### 11.2 Common Use Cases — ALL must know
```java
// 1. Click (when ElementClickIntercepted / element covered by overlay)
js.executeScript("arguments[0].click();", element);

// 2. Scroll
js.executeScript("window.scrollBy(0, 500)");                    // down 500px
js.executeScript("window.scrollBy(0, -500)");                   // up
js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // bottom
js.executeScript("arguments[0].scrollIntoView(true);", element);    // to element

// 3. Type into field (bypasses sendKeys issues)
js.executeScript("arguments[0].value='hello@x.com';", input);

// 4. Read input value
String val = (String) js.executeScript("return arguments[0].value;", input);

// 5. Get page title / URL / domain / innerText
String title = (String) js.executeScript("return document.title;");
String url   = (String) js.executeScript("return window.location.href;");
String text  = (String) js.executeScript("return document.documentElement.innerText;");

// 6. Refresh page
js.executeScript("history.go(0)");        // or location.reload()

// 7. Highlight element (for debugging/screenshots)
js.executeScript("arguments[0].style.border='3px solid red'", element);
js.executeScript("arguments[0].style.backgroundColor='yellow'", element);

// 8. Handle disabled element
js.executeScript("arguments[0].removeAttribute('disabled');", el);

// 9. Zoom page
js.executeScript("document.body.style.zoom='80%'");

// 10. Scroll inside specific div
js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", div);
```

### Interview Questions

**Q1: Why use JavaScriptExecutor?**
- Bypasses WebDriver limitations: click hidden/covered elements, set values on read-only fields, precise scrolling, zoom, flash elements.

**Q2: How to click with JS?**
- `js.executeScript("arguments[0].click()", element)` — bypasses click interception; but note it skips real user-event checks (use as fallback, not default).

**Q3: executeScript vs executeAsyncScript?**
- Sync returns after script finishes. Async waits until script calls the injected callback (last argument) — used for AJAX/timeouts.

**Q4: Risk of JS click?**
- Doesn't simulate real user (no hover/focus chain), can mask UI bugs — interviewers like "why prefer real click": because it verifies element is actually clickable.

---

## 12. ALERTS & POPUPS

### What is Alert?
JavaScript dialogs that block the page — handled via `driver.switchTo().alert()` (returns `Alert` object).

### 12.1 Alert Methods
```java
Alert alert = driver.switchTo().alert();
alert.accept();                 // OK
alert.dismiss();                // Cancel
alert.getText();                // read message
alert.sendKeys("text");         // type into prompt alert
```

### 12.2 Handle All Alert Types
```java
// 1. Simple alert — only OK
driver.switchTo().alert().accept();

// 2. Confirmation — OK / Cancel
Alert confirm = driver.switchTo().alert();
System.out.println(confirm.getText());
confirm.dismiss();                                   // cancel

// 3. Prompt — input field
Alert prompt = driver.switchTo().alert();
prompt.sendKeys("John");
prompt.accept();
```

### 12.3 Wait for Alert (Explicit)
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
alert.accept();
```

### 12.4 Authentication Popup (HTTP Basic Auth)
```java
// Option 1: embed credentials in URL
driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

// Option 2 (Selenium 4, Chromium): HasAuthentication + Predicate
((HasAuthentication) driver).register(
        UsernameAndPassword.of("admin", "admin"));
driver.get("https://the-internet.herokuapp.com/basic_auth");
```

### 12.5 Browser Notifications / Permission Popups
These are browser-level (not JS alerts — `switchTo().alert()` fails).
```java
// Disable via ChromeOptions
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notifications");
// or prefs: prefs.put("profile.default_content_setting_values.notifications", 2);
```

### Interview Questions

**Q1: How to handle alerts in Selenium?**
- `driver.switchTo().alert()` → accept/dismiss/getText/sendKeys.

**Q2: What exception if alert doesn't exist?**
- `NoAlertPresentException`.

**Q3: Difference between JS alert and browser notification popup?**
- JS alerts block page and are handled via Alert API. Browser permission popups (location/notifications) are NOT DOM elements — disable via browser options, or handle via CDP.

**Q4: How to handle authentication popup?**
- Embed credentials in URL, or Selenium 4 `HasAuthentication`/`register(UsernameAndPassword)` for Chromium.

**Q5: How to wait for alert?**
- `wait.until(ExpectedConditions.alertIsPresent())`.

---

## 13. FRAMES & IFRAMES

### What is a Frame?
`<iframe>`/`<frame>` embeds a separate document. Elements inside are NOT reachable until you switch into the frame. Trying directly → `NoSuchElementException`.

### 13.1 Switch to Frame — 3 Ways
```java
driver.switchTo().frame(0);                                      // by index
driver.switchTo().frame("frameName");                            // by name/id
driver.switchTo().frame(driver.findElement(By.cssSelector("iframe"))); // by WebElement ★

// or with wait
wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
        By.cssSelector("iframe")));
```

### 13.2 Switch Back
```java
driver.switchTo().defaultContent();    // back to main page (top document)
driver.switchTo().parentFrame();       // up ONE level only (nested frames)
```

### 13.3 Nested Frames
```java
driver.switchTo().frame("outer");
driver.switchTo().frame("inner");       // nested inside outer
driver.findElement(By.id("btn")).click();

driver.switchTo().parentFrame();        // back to outer
driver.switchTo().defaultContent();     // back to main page
```

### Interview Questions

**Q1: How many ways to switch to a frame?**
- Three: index (0-based), name/id (String), WebElement.

**Q2: parentFrame() vs defaultContent()?**
- `parentFrame()` goes up one frame level; `defaultContent()` goes straight to the top document.

**Q3: What exception inside frame without switching?**
- `NoSuchElementException` — driver only sees the current document.

**Q4: How to find total iframes on a page?**
- `driver.findElements(By.tagName("iframe")).size()`.

**Q5: Element inside frame + shadow DOM?**
- Switch to frame first, then handle shadow DOM via `getShadowRoot()`.

---

## 14. WINDOW & TAB HANDLING

### What are Window Handles?
Each browser window/tab gets a unique string ID (handle). `getWindowHandle()` = current; `getWindowHandles()` = `Set<String>` of all.

### 14.1 Get Window Handles
```java
String parent = driver.getWindowHandle();
Set<String> all = driver.getWindowHandles();
```

### 14.2 Handle New Window/Tab — THE classic pattern
```java
String parent = driver.getWindowHandle();

driver.findElement(By.linkText("Open Window")).click();   // opens child

Set<String> handles = driver.getWindowHandles();
for (String h : handles) {
    if (!h.equals(parent)) {
        driver.switchTo().window(h);
        break;
    }
}
// work in child
driver.findElement(By.id("childBtn")).click();
driver.close();                         // close child
driver.switchTo().window(parent);       // back to parent ★ never forget
```

```java
// wait for new window
wait.until(ExpectedConditions.numberOfWindowsToBe(2));
```

### 14.3 Open New Tab/Window (Selenium 4)
```java
driver.switchTo().newWindow(WindowType.TAB);      // new tab + auto-switch
driver.switchTo().newWindow(WindowType.WINDOW);   // new window + auto-switch
driver.get("https://newurl.com");
```

### Interview Questions

**Q1: getWindowHandle vs getWindowHandles?**
- Singular returns current window handle (String); plural returns Set of all handles.

**Q2: How to switch to a child window and back?**
- Save parent handle → getWindowHandles → loop/skip parent → switchTo child → work → close → switchTo(parent).

**Q3: How to open a new tab in Selenium 4?**
- `driver.switchTo().newWindow(WindowType.TAB)`.

**Q4: How to wait for a second window?**
- `ExpectedConditions.numberOfWindowsToBe(2)`.

---

## 15. FILE UPLOAD & DOWNLOAD

### 15.1 File Upload
```java
// Standard <input type="file"> — just sendKeys the PATH (don't click!)
WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
upload.sendKeys("/Users/me/file.pdf");         // absolute path

// Multiple files (if input has 'multiple')
upload.sendKeys("/path/a.png\n/path/b.png");
```

```java
// If input is hidden — make visible via JS first
js.executeScript("arguments[0].style.display='block';", upload);
upload.sendKeys(path);
```

### 15.2 OS-level Dialog (fallbacks)
```java
// Robot class (legacy workaround — types into OS dialog)
element.click();
Robot robot = new Robot();
StringSelection path = new StringSelection("/Users/me/file.pdf");
Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
robot.keyPress(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_V);
robot.keyRelease(KeyEvent.VK_CONTROL);
robot.keyPress(KeyEvent.VK_ENTER);
// Or use AutoIt script for Windows dialogs
```

### 15.3 File Download
```java
// Set download directory via Chrome prefs
ChromeOptions options = new ChromeOptions();
Map<String, Object> prefs = new HashMap<>();
prefs.put("download.default_directory", "/tmp/downloads");
prefs.put("download.prompt_for_download", false);
options.setExperimentalOption("prefs", prefs);
WebDriver driver = new ChromeDriver(options);

driver.findElement(By.linkText("Download")).click();
// verify file exists
File f = new File("/tmp/downloads/report.pdf");
assert f.exists();
```

```java
// Selenium 4 CDP way (Chromium)
DevTools devTools = ((ChromeDriver) driver).getDevTools();
devTools.createSession();
devTools.send(Browser.setDownloadBehavior(
        new SetDownloadBehaviorBehavior...)); // or Browser.setDownloadBehavior with allow
```

```java
// Grid download (Selenium 4.13+): driver.downloadFile() / grid manages
// RemoteWebDriver supports downloadable files via /session/{id}/file
```

### Interview Questions

**Q1: How to upload a file in Selenium?**
- If `<input type="file">` exists: `sendKeys(absolutePath)` — never click it (OS dialog). Hidden input → JS `display` it first. OS dialog fallback: Robot/AutoIt.

**Q2: How to set download location in Chrome?**
- `prefs.put("download.default_directory", path)` via `setExperimentalOption`. Firefox: `addPreference("browser.download.dir", ...)` + `browser.helperApps.neverAsk.saveToDisk` MIME types.

**Q3: How to verify file downloaded?**
- Check file exists in dir (poll for `.crdownload` to disappear in Chrome) or download via URL directly with Java HttpClient using session cookies.

---

## 16. COOKIES MANAGEMENT

### What are Cookies?
Key-value data stored by the browser; Selenium can read/add/delete them — useful for skipping logins.

```java
// Add cookie
Cookie cookie = new Cookie("sessionId", "abc123", ".example.com", "/", null);
driver.manage().addCookie(cookie);

// Build with options
Cookie c = new Cookie.Builder("token", "xyz")
        .domain(".example.com")
        .path("/")
        .isSecure(true)
        .isHttpOnly(true)
        .sameSite("Lax")                    // Selenium 4
        .expiresOn(new Date())
        .build();
driver.manage().addCookie(c);

// Read
Cookie found = driver.manage().getCookieNamed("sessionId");
Set<Cookie> all = driver.manage().getCookies();

// Delete
driver.manage().deleteCookieNamed("sessionId");
driver.manage().deleteCookie(found);
driver.manage().deleteAllCookies();          // fresh session
```

```java
// Classic pattern: login once, reuse cookies in later tests
Set<Cookie> cookies = driver.manage().getCookies();     // after login
// ... new session:
driver.get("https://app.com");                          // must visit domain first!
cookies.forEach(c -> driver.manage().addCookie(c));
driver.navigate().refresh();                            // now logged in
```

### Interview Questions

**Q1: How do you skip login in tests?**
- Save cookies after first login → add them in subsequent sessions (must `get()` the domain first, then `addCookie`, then refresh). Selenium 4: `sameSite` supported. (Playwright equivalent: `storageState`.)

**Q2: Cookie methods available?**
- `addCookie`, `getCookieNamed`, `getCookies`, `deleteCookieNamed`, `deleteCookie`, `deleteAllCookies`.

**Q3: Why must you visit the domain before addCookie?**
- Browsers attach cookies per-domain — you can't set a cookie for a domain you haven't loaded.

---

## 17. SCREENSHOTS

### What is TakesScreenshot?
Interface implemented by RemoteWebDriver — captures screenshots as FILE/BYTES/BASE64.

### 17.1 Full Page Screenshot
```java
File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(src, new File("screenshots/page.png"));   // commons-io

// as Base64 (for reports)
String base64 = ((TakesScreenshot) driver)
        .getScreenshotAs(OutputType.BASE64);

// Bytes (attach to Allure)
byte[] bytes = ((TakesScreenshot) driver)
        .getScreenshotAs(OutputType.BYTES);
```

### 17.2 Element Screenshot (Selenium 4)
```java
WebElement logo = driver.findElement(By.id("logo"));
File f = logo.getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(f, new File("header.png"));
```

### 17.3 Screenshot on Test Failure (TestNG Listener)
```java
public class ScreenshotListener extends BaseTest implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        File src = ((TakesScreenshot) getDriver())
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(
                "screenshots/" + result.getName() + "_failed.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }
}
```

### 17.4 Full page including scrollable area
```java
// Firefox: full-page screenshot natively
File full = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
// Chrome: use AShot library or CDP Page.captureScreenshot(captureBeyondViewport)
```

### Interview Questions

**Q1: How to take a screenshot?**
- Cast driver to `TakesScreenshot` → `getScreenshotAs(OutputType.FILE)` → copy with FileUtils/Files.copy.

**Q2: OutputType options?**
- FILE, BYTES, BASE64.

**Q3: Screenshot on failure only?**
- Implement `ITestListener.onTestFailure` and capture there — auto-runs on every failure.

**Q4: Element-only screenshot?**
- Selenium 4: `element.getScreenshotAs(...)`. Older: crop full screenshot using element's getRect() with ImageIO/AShot.

---

## 18. NAVIGATION COMMANDS

```java
driver.get("https://app.com");              // open + wait for load

driver.navigate().to("https://app.com");    // similar to get()
driver.navigate().back();                   // browser back
driver.navigate().forward();                // forward
driver.navigate().refresh();                // reload
```

### get() vs navigate().to() — Interview favorite
| `get()` | `navigate().to()` |
|---------|-------------------|
| Loads page, waits for load | Same load, PLUS history tracking |
| No history ops | back()/forward()/refresh() available |
| Accepts String URL | Accepts String or `URL` object |

### Interview Questions

**Q1: get() vs navigate()?**
- Functionally similar for loading; navigate() unlocks browser history operations and can accept a `URL` object.

---

## 19. WEB TABLES & DYNAMIC ELEMENTS

### 19.1 Read Table Data
```java
// rows & cols
List<WebElement> rows = driver.findElements(By.cssSelector("#tbl tbody tr"));
List<WebElement> cols = driver.findElements(
        By.cssSelector("#tbl tbody tr:first-child td"));

for (int r = 1; r <= rows.size(); r++) {
    for (int c = 1; c <= cols.size(); c++) {
        String cell = driver.findElement(By.xpath(
            "//table[@id='tbl']/tbody/tr[" + r + "]/td[" + c + "]")).getText();
        System.out.print(cell + " | ");
    }
    System.out.println();
}
```

### 19.2 Find Cell by Value / Click row action
```java
// Find row containing 'John', click its Edit button
driver.findElement(By.xpath(
    "//td[text()='John']/following-sibling::td//a[text()='Edit']")).click();

// Get the cell next to 'Status' column for a user
String status = driver.findElement(By.xpath(
    "//tr[td[text()='John']]/td[4]")).getText();
```

### 19.3 Dynamic Elements (IDs change every load)
```java
// id="user_12345" -> changes each load → match stable part
By.cssSelector("input[id^='user_']");          // starts-with
By.xpath("//input[contains(@id,'user')]");      // contains
By.xpath("//label[text()='Email']/following-sibling::input"); // anchor by label
```

### 19.4 Handle StaleElementReferenceException
```java
// PROBLEM: element found, then DOM refreshed → reference dead
WebElement el = driver.findElement(By.id("dyn"));
driver.navigate().refresh();
el.click();        // 💥 StaleElementReferenceException

// FIX 1: re-locate after refresh
el = driver.findElement(By.id("dyn"));
el.click();

// FIX 2: wait + refreshed
wait.until(ExpectedConditions.refreshed(
        ExpectedConditions.elementToBeClickable(By.id("dyn")))).click();

// FIX 3: retry pattern
public void clickWithRetry(By locator, int attempts) {
    for (int i = 0; i < attempts; i++) {
        try {
            driver.findElement(locator).click();
            return;
        } catch (StaleElementReferenceException e) { /* retry */ }
    }
}
```

### Interview Questions

**Q1: How do you handle dynamic web tables?**
- Build XPath by row/column index, or anchor by row text (`//tr[td='John']/td[3]`).

**Q2: What causes StaleElementReferenceException and fix?**
- DOM updated after element was located (refresh, AJAX re-render). Re-locate element, use `refreshed()` condition, or retry loop.

**Q3: How to click a link in a specific table row?**
- `//td[text()='Name']/../td[last()]/a` or following-sibling axis.

---

## 20. SHADOW DOM

### What is Shadow DOM?
Encapsulated DOM trees inside a host element (`#shadow-root`). Normal locators can't pierce it.

### 20.1 Single Shadow DOM (Selenium 4 native)
```java
WebElement host = driver.findElement(By.cssSelector("my-component"));
SearchContext shadow = host.getShadowRoot();          // Selenium 4 ★
WebElement btn = shadow.findElement(By.cssSelector("button"));
btn.click();
```

### 20.2 Nested Shadow DOM
```java
SearchContext shadow1 = driver.findElement(By.cssSelector("outer-el"))
        .getShadowRoot();
SearchContext shadow2 = shadow1.findElement(By.cssSelector("inner-el"))
        .getShadowRoot();
shadow2.findElement(By.cssSelector("input")).sendKeys("deep");
```

### 20.3 Via JavaScriptExecutor (works everywhere)
```java
WebElement el = (WebElement) js.executeScript(
    "return document.querySelector('my-comp')"
        + ".shadowRoot.querySelector('button')");
el.click();
```

### Interview Questions

**Q1: How does Selenium 4 handle shadow DOM?**
- `element.getShadowRoot()` returns a `SearchContext` you can `findElement` inside. Chained for nested roots.

**Q2: Can XPath reach inside shadow DOM?**
- No — XPath can't cross shadow boundaries. Use CSS selector inside `getShadowRoot()` context, or JS.

**Q3: Closed shadow root?**
- `mode=closed` → `getShadowRoot()` throws; only option is JS workarounds/asking devs for open mode.

## 21. SELENIUM 4 NEW FEATURES

### 21.1 Relative Locators
```java
import static org.openqa.selenium.support.locators.RelativeLocator.with;

with(By.tagName("input")).below(By.cssSelector("label"));
with(By.tagName("button")).toRightOf(By.id("cancel"));
with(By.tagName("button")).toLeftOf(By.id("submit"));
with(By.tagName("input")).above(By.id("footer"));
with(By.tagName("div")).near(By.id("anchor"));            // ~50px default
with(By.tagName("div")).near(By.id("anchor"), 100);       // custom px
```

### 21.2 New Window/Tab
```java
driver.switchTo().newWindow(WindowType.TAB);
driver.switchTo().newWindow(WindowType.WINDOW);
```

### 21.3 Element Screenshot
```java
element.getScreenshotAs(OutputType.FILE);
```

### 21.4 Chrome DevTools Protocol (CDP)
```java
DevTools devTools = ((ChromeDriver) driver).getDevTools();
devTools.createSession();

// Enable network domain & capture requests
devTools.send(Network.enable(
        Optional.empty(), Optional.empty(), Optional.empty()));
devTools.addListener(Network.requestWillBeSent(), req ->
        System.out.println(req.getRequest().getUrl()));

// Emulate geolocation
devTools.send(Emulation.setGeolocationOverride(
        Optional.of(28.6), Optional.of(77.2), Optional.of(1)));

// Emulate device / network conditions
devTools.send(Emulation.setDeviceMetricsOverride(
        375, 812, 50, true,
        Optional.empty(), Optional.empty(), Optional.empty(),
        Optional.empty(), Optional.empty(), Optional.empty(),
        Optional.empty(), Optional.empty(), Optional.empty()));
devTools.send(Network.emulateNetworkConditions(
        false, 100, 100000, 50000, Optional.of(ConnectionType.CELLULAR4G)));

// Basic auth via CDP
((HasAuthentication) driver).register(
        UsernameAndPassword.of("user", "pass"));
```

### 21.5 Selenium Manager (4.6+)
- Auto driver resolution — no WebDriverManager dependency needed
- CLI: `selenium-manager --browser chrome --debug`

### 21.6 WebDriver BiDi (Bidirectional — the future)
- Two-way protocol (browser can push events to client) — successor to CDP direction
- Enables console logs, JS errors, network events without polling
```java
// BrowsingContext / BiDi APIs — evolving; mention in interview as "future direction"
```

### 21.7 Print to PDF (Chromium)
```java
PrintsPage printer = (PrintsPage) driver;
PrintOptions po = new PrintOptions();
po.setOrientation(PrintOptions.Orientation.PORTRAIT);
Pdf pdf = printer.print(po);
Files.write(Paths.get("page.pdf"),
        Base64.getDecoder().decode(pdf.getContent()));
```

### 21.8 WebDriverListener (intercept driver calls)
```java
WebDriverListener listener = new WebDriverListener() {
    @Override
    public void beforeClick(WebElement element) {
        System.out.println("Clicking: " + element);
    }
};
WebDriver decorated = new EventFiringDecorator<>(listener)
        .decorate(new ChromeDriver());
```

### Interview Questions

**Q1: Top Selenium 4 features?**
- W3C protocol only, relative locators, newWindow, element screenshot, CDP access, Selenium Manager, redesigned Grid, BiDi (partial), print to PDF, WebDriverListener, `getDomAttribute`/`getDomProperty`, scroll wheel actions, `minimize()` window support (`driver.manage().window().minimize()`).

**Q2: What is CDP and why use it?**
- Chrome DevTools Protocol — direct browser internals access: network capture/throttling, geolocation/device emulation, console logs, performance metrics, basic auth.

**Q3: How to capture network traffic?**
- `DevTools` session + `Network.enable` + `Network.requestWillBeSent`/`responseReceived` listeners.

---

## 22. PAGE OBJECT MODEL (POM)

### 22.1 Basic POM Structure
```java
public class LoginPage {
    private WebDriver driver;

    // locators — By objects, no @FindBy
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton   = By.cssSelector("button[type='submit']");
    private By errorMsg      = By.cssSelector(".error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // actions — methods = user actions
    public void enterUsername(String user) {
        driver.findElement(usernameField).sendKeys(user);
    }
    public void enterPassword(String pass) {
        driver.findElement(passwordField).sendKeys(pass);
    }
    public HomePage clickLogin() {                  // return next page (fluent)
        driver.findElement(loginButton).click();
        return new HomePage(driver);
    }
    public String getError() {
        return driver.findElement(errorMsg).getText();
    }
    public HomePage login(String u, String p) {     // composed action
        enterUsername(u);
        enterPassword(p);
        return clickLogin();
    }
}
```

### 22.2 Using POM in Tests
```java
public class LoginTest extends BaseTest {
    @Test
    public void validLogin() {
        LoginPage lp = new LoginPage(driver);
        HomePage hp = lp.login("admin", "admin123");
        Assert.assertTrue(hp.isDashboardVisible());
    }

    @Test
    public void invalidLogin() {
        LoginPage lp = new LoginPage(driver);
        lp.login("bad", "bad");
        Assert.assertEquals(lp.getError(), "Invalid credentials");
    }
}
```

### 22.3 Base Page Class
```java
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);   // if using @FindBy
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    protected void type(By locator, String text) {
        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }
    protected String getText(By locator) {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator)).getText();
    }
    protected boolean isVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) { return false; }
    }
}
```

### Interview Questions

**Q1: What is POM and why use it?**
- Each page = a class containing its locators + action methods. Benefits: maintainability (UI change → one file), reusability, readability, separation of test logic from page logic.

**Q2: POM without PageFactory?**
- Yes — plain `By` fields + `driver.findElement` is valid POM; elements resolved lazily at call time (naturally avoids stale elements vs cached @FindBy without AjaxElementLocatorFactory).

**Q3: Should page methods return page objects?**
- Best practice: action methods return the next page (`clickLogin()` → `new HomePage(driver)`) — enables fluent chaining.

---

## 23. PAGEFACTORY (@FindBy)

### 23.1 @FindBy Annotations
```java
public class LoginPagePF {
    WebDriver driver;

    @FindBy(id = "username")
    WebElement username;

    @FindBy(css = "button[type='submit']")
    WebElement loginBtn;

    @FindBy(xpath = "//a[text()='Logout']")
    WebElement logoutLink;

    // multiple locators — first that finds element wins (OR)
    @FindAll({
        @FindBy(id = "user"),
        @FindBy(name = "username"),
        @FindBy(css = ".user-field")
    })
    WebElement userField;

    // ALL locators must match (AND)
    @FindBys({
        @FindBy(css = "div.form"),
        @FindBy(tagName = "input")
    })
    WebElement nestedInput;

    // cache element after first lookup — risky with dynamic DOM
    @CacheLookup
    @FindBy(id = "logo")
    WebElement logo;

    // list of elements
    @FindBy(css = "ul.menu li")
    List<WebElement> menuItems;

    public LoginPagePF(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);    // binds @FindBy fields
    }

    public void login(String u, String p) {
        username.sendKeys(u);
        driver.findElement(By.id("password")).sendKeys(p);
        loginBtn.click();
    }
}
```

### 23.2 POM vs PageFactory
| Plain POM (`By` fields) | PageFactory (`@FindBy`) |
|------------------------|------------------------|
| Element located at call time | Located via proxy at first use |
| Never stale by design | Can go stale (@CacheLookup worsens) |
| More code | Less boilerplate |
| Works with any wait wrapper | `AjaxElementLocatorFactory` for timeouts |

```java
// AjaxElementLocatorFactory — @FindBy with implicit timeout
PageFactory.initElements(
        new AjaxElementLocatorFactory(driver, 10), this);
```

### Interview Questions

**Q1: What is PageFactory?**
- `PageFactory.initElements(driver, this)` wires `@FindBy` fields to lazy proxies. Deprecated-ish discussion: still widely used in interviews; modern teams often prefer plain By fields.

**Q2: @FindBys vs @FindAll?**
- `@FindBys` = AND (chained, all must match in order). `@FindAll` = OR (first matching locator wins).

**Q3: What is @CacheLookup?**
- Caches the element after first find — faster but dangerous on dynamic pages → stale elements.

**Q4: How does PageFactory handle lazy init?**
- Fields are JDK proxies; `findElement` happens on each method call (unless @CacheLookup) — still can hit stale refs mid-action.

---

## 24. TESTNG FRAMEWORK INTEGRATION

### 24.1 TestNG Annotations Order — MEMORIZE
```
@BeforeSuite        → once before suite
  @BeforeTest       → before each <test> tag in xml
    @BeforeClass    → once per class
      @BeforeMethod → before EVERY @Test
        @Test       → the test
      @AfterMethod  → after EVERY @Test
    @AfterClass     → once per class
  @AfterTest        → after each <test>
@AfterSuite         → once after suite

Plus: @BeforeGroups / @AfterGroups
@BeforeMethod/@AfterMethod run per test — put driver setup/teardown HERE
```

### 24.2 BaseTest with TestNG
```java
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setup(@Optional("chrome") String browser,
                      @Optional("https://app.com") String url) {
        driver = switch (browser) {
            case "firefox" -> new FirefoxDriver();
            case "edge"    -> new EdgeDriver();
            default        -> new ChromeDriver();
        };
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) driver.quit();
    }
}
```

### 24.3 TestNG Assertions
```java
// Hard assert — stops test immediately
Assert.assertEquals(actual, expected);
Assert.assertTrue(condition);
Assert.assertFalse(condition);
Assert.assertNull(obj);
Assert.assertNotNull(obj);
Assert.assertEquals(actual, expected, "Login failed message");

// Soft assert — collects all failures, reports at assertAll()
SoftAssert soft = new SoftAssert();
soft.assertEquals(title, "Home");
soft.assertTrue(logo.isDisplayed());
soft.assertAll();              // ← required, else failures swallowed!
```

### 24.4 TestNG Features
```java
// Parameters from testng.xml
@Test
@Parameters({"username", "password"})
public void login(String u, String p) { ... }

// DataProvider — run test N times with data
@DataProvider(name = "logins")
public Object[][] loginData() {
    return new Object[][] {
        {"admin", "admin123", true},
        {"user",  "wrong",    false},
    };
}
@Test(dataProvider = "logins")
public void loginTest(String u, String p, boolean ok) { ... }

// DataProvider from another class
@Test(dataProvider = "excelData", dataProviderClass = ExcelData.class)

// Groups — smoke/regression
@Test(groups = {"smoke", "login"})
public void quickCheck() { }

// Dependencies
@Test(dependsOnMethods = {"login"})                  // run after
public void logout() { }
@Test(dependsOnGroups = {"smoke"})
public void fullFlow() { }

// Priority (avoid in real frameworks — use dependsOn)
@Test(priority = 1)

// Disable / skip
@Test(enabled = false)
throw new SkipException("skip me");

// Timeout per test
@Test(timeOut = 5000)

// Retry failed test
@Test(retryAnalyzer = RetryAnalyzer.class)

// Expected exception
@Test(expectedExceptions = NoSuchElementException.class)

// Invocation count
@Test(invocationCount = 5, threadPoolSize = 3)
```

### 24.5 testng.xml
```xml
<suite name="RegressionSuite" parallel="methods" thread-count="4">
    <listeners>
        <listener class-name="com.qa.listeners.ScreenshotListener"/>
        <listener class-name="com.qa.listeners.RetryTransformer"/>
    </listeners>
    <parameter name="browser" value="chrome"/>
    <test name="LoginTests">
        <groups>
            <run><include name="smoke"/></run>
        </groups>
        <classes>
            <class name="com.qa.tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

### Interview Questions

**Q1: TestNG annotation execution order?**
- Suite → Test → Groups → Class → Method → @Test (before/after reversed after).

**Q2: Assert vs SoftAssert?**
- Assert stops test on first failure; SoftAssert accumulates and reports on `assertAll()` — forgetting assertAll = false pass!

**Q3: How to run a test with multiple data sets?**
- `@DataProvider` returning `Object[][]` / `Iterator<Object[]>`; `parallel=true` on DataProvider for parallel rows.

**Q4: How to retry a failed test?**
- `IRetryAnalyzer` in `@Test(retryAnalyzer=...)` or apply globally via `IAnnotationTransformer` listener.

**Q5: How to pass values from testng.xml?**
- `<parameter name="x" value="y"/>` + `@Parameters({"x"})` + `@Optional` for defaults.

---

## 25. TESTNG LISTENERS & RETRY

### 25.1 ITestListener Implementation
```java
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("STARTED: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("FAILED: " + result.getName());
        // screenshot on failure
        WebDriver driver = DriverFactory.getDriver();
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(
                "screenshots/" + result.getName() + ".png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void onTestSkipped(ITestResult result) { }
}
```

### 25.2 Retry Analyzer (Retry Failed Tests)
```java
public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX) {
            count++;
            return true;                 // rerun
        }
        return false;
    }
}
// use: @Test(retryAnalyzer = RetryAnalyzer.class)
```

```java
// Apply retry to ALL tests automatically — IAnnotationTransformer
public class RetryTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor ctor, Method method) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
```

### 25.3 Register Listener — 3 Ways
```java
// 1. Annotation
@Listeners(TestListener.class)
public class LoginTest { ... }
```
```xml
<!-- 2. testng.xml -->
<listeners><listener class-name="com.qa.TestListener"/></listeners>
```
```xml
<!-- 3. META-INF/services (ServiceLoader auto-discovery) -->
```

### 25.4 Other Listeners
```java
ISuiteListener      → onStart/onFinish of suite
IInvokedMethodListener → before/after EVERY method (incl. configs)
IReporter           → custom HTML report generation
IAnnotationTransformer → modify annotations at runtime
```

### 25.5 Selenium-side Listener (Selenium 4)
```java
WebDriverListener listener = new WebDriverListener() {
    public void afterClick(WebElement el) { log("clicked " + el); }
    public void afterGet(WebDriver d, String url) { log("nav " + url); }
};
WebDriver driver = new EventFiringDecorator<>(listener)
        .decorate(new ChromeDriver());
```

### Interview Questions

**Q1: How do you capture screenshot on failure automatically?**
- `ITestListener.onTestFailure` — no test code changes needed.

**Q2: How to retry ALL failed tests without annotating each?**
- `IAnnotationTransformer` that sets `setRetryAnalyzer` at runtime, registered in testng.xml.

**Q3: ITestListener vs IInvokedMethodListener?**
- ITestListener → test lifecycle events (start/pass/fail/skip). IInvokedMethodListener → before/after every invoked method including @BeforeMethod configs.

---

## 26. DATA-DRIVEN TESTING

### 26.1 Apache POI — Read Excel
```java
public class ExcelUtils {
    public static Object[][] getData(String path, String sheet) {
        try (FileInputStream fis = new FileInputStream(path);
             Workbook wb = new XSSFWorkbook(fis)) {           // .xlsx
            Sheet s = wb.getSheet(sheet);
            int rows = s.getLastRowNum();
            int cols = s.getRow(0).getLastCellNum();
            Object[][] data = new Object[rows][cols];
            for (int r = 1; r <= rows; r++) {
                for (int c = 0; c < cols; c++) {
                    data[r - 1][c] = s.getRow(r).getCell(c)
                            .toString();
                }
            }
            return data;
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}

// DataProvider feeding from Excel
@DataProvider(name = "excelData")
public Object[][] excelData() {
    return ExcelUtils.getData("testdata/users.xlsx", "logins");
}
```
- `HSSFWorkbook` = .xls (old), `XSSFWorkbook` = .xlsx
- Maven: `org.apache.poi:poi-ooxml`

### 26.2 Properties File (Config)
```java
public class ConfigReader {
    private static Properties prop = new Properties();
    static {
        try (FileInputStream fis =
                new FileInputStream("src/test/resources/config.properties")) {
            prop.load(fis);
        } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static String get(String key) { return prop.getProperty(key); }
}
// config.properties:  browser=chrome   url=https://app.com   timeout=10
```

### 26.3 CSV / JSON
```java
// CSV via OpenCSV or plain BufferedReader split(",")
// JSON via Jackson ObjectMapper or Gson → JsonNode/Map
```

### Interview Questions

**Q1: How to do data-driven testing in Selenium?**
- @DataProvider + Excel (Apache POI) / CSV / JSON / DB. DataProvider feeds each row to @Test.

**Q2: POI classes for xls vs xlsx?**
- HSSFWorkbook (.xls), XSSFWorkbook (.xlsx), SXSSFWorkbook (streaming huge files).

---

## 27. REPORTING WITH EXTENTREPORTS

### 27.1 ExtentReports Setup
```java
// pom.xml — com.aventstack:extentreports
public class ExtentManager {
    private static ExtentReports extent;
    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(
                    "reports/extent-report.html");
            spark.config().setReportName("Automation Results");
            spark.config().setDocumentTitle("Test Report");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Env", "QA");
        }
        return extent;
    }
}
```

### 27.2 ExtentTest in Tests/Listener
```java
public class ExtentTestListener implements ITestListener {
    static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onTestStart(ITestResult r) {
        test.set(ExtentManager.getInstance()
                .createTest(r.getMethod().getMethodName()));
    }
    public void onTestSuccess(ITestResult r) {
        test.get().pass("Test passed");
    }
    public void onTestFailure(ITestResult r) {
        test.get().fail(r.getThrowable());
        String path = takeScreenshot(r.getName());   // helper
        test.get().addScreenCaptureFromPath(path);
        // or base64: .addScreenCaptureFromBase64String(b64)
    }
    public void onFinish(ITestContext ctx) {
        ExtentManager.getInstance().flush();         // ★ write file
    }
}
```

```java
// logging inside test
ExtentTest t = ExtentTestListener.test.get();
t.info("Navigating to login");
t.pass("Login successful");
t.fail("Element not found");
t.warning("Slow response");
```

### 27.3 Other Report Options
- **TestNG default** — test-output/index.html (basic)
- **Allure** — rich, needs allure-testng adapter + `allure serve`
- **ReportPortal / Klov** — enterprise dashboards
- **ExtentReports** — most common in interviews

### Interview Questions

**Q1: How do you generate reports?**
- ExtentReports via ExtentSparkReporter; create test per method in ITestListener, attach screenshots via `addScreenCaptureFromPath`, `flush()` at suite end. Alternative: Allure.

**Q2: Why ThreadLocal for ExtentTest?**
- Parallel runs — each thread writes to its own test node.

---

## 28. REAL PROJECT FRAMEWORK STRUCTURE

### 28.1 Folder Structure
```
src/
├── main/java/
│   └── com/qa/
│       ├── base/           DriverFactory, BaseTest
│       ├── pages/          LoginPage, HomePage (POM)
│       ├── utils/          ConfigReader, ExcelUtils, WaitUtils, JSUtils
│       ├── listeners/      TestListener, RetryAnalyzer
│       └── constants/      AppConstants, Endpoints
├── test/java/
│   └── com/qa/tests/       LoginTest, CartTest
├── test/resources/
│   ├── config.properties
│   ├── testdata/           users.xlsx
│   └── testng.xml
├── reports/                extent-report.html, screenshots/
├── pom.xml
└── Jenkinsfile / .github/workflows/
```

### 28.2 pom.xml Key Dependencies
```xml
<dependencies>
    <!-- Selenium -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.27.0</version>
    </dependency>
    <!-- TestNG -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.2</version>
        <scope>test</scope>
    </dependency>
    <!-- WebDriverManager (optional — Selenium Manager built-in now) -->
    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>5.9.2</version>
    </dependency>
    <!-- Apache POI for Excel -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.3.0</version>
    </dependency>
    <!-- ExtentReports -->
    <dependency>
        <groupId>com.aventstack</groupId>
        <artifactId>extentreports</artifactId>
        <version>5.1.2</version>
    </dependency>
    <!-- commons-io for FileUtils -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.16.1</version>
    </dependency>
    <!-- Cucumber (if BDD) -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.20.1</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-testng</artifactId>
        <version>7.20.1</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- Surefire to run testng.xml -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.5.0</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
                </suiteXmlFiles>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <source>17</source><target>17</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 28.3 config.properties
```properties
browser=chrome
url=https://staging.myapp.com
implicitWait=10
explicitWait=15
headless=false
env=staging
```

### Interview Questions

**Q1: Explain your framework architecture.**
- Layered: DriverFactory (ThreadLocal) → BaseTest (setup/teardown) → Pages (POM) → Tests (TestNG) → Utils (Excel/config) → Listeners (screenshot, retry, ExtentReports) → testng.xml orchestrates → Maven/CI runs.

**Q2: How do you manage multiple environments?**
- `config-qa.properties`, `config-staging.properties` — pick via `System.getProperty("env", "qa")` or Maven profile.

## 29. BDD/CUCUMBER WITH SELENIUM

### 🎯 Quick Revision Cheatsheet

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    BDD/CUCUMBER QUICK REFERENCE                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  📁 FILE TYPES:                                                             │
│     .feature     →  Test scenarios in Gherkin (English)                     │
│     Steps.java   →  Step definitions (actual code)                          │
│     Hooks.java   →  @Before/@After methods                                  │
│     Runner.java  →  Test executor with @CucumberOptions                     │
│                                                                             │
│  🔑 GHERKIN KEYWORDS:                                                       │
│     Feature      →  Functionality name                                      │
│     Scenario     →  Single test case                                        │
│     Given        →  Precondition (setup)                                    │
│     When         →  Action (user does something)                            │
│     Then         →  Expected result (verification)                          │
│     And/But      →  Additional steps                                        │
│     Background   →  Common steps for all scenarios in feature               │
│     Scenario Outline + Examples  →  Data-driven testing                     │
│                                                                             │
│  🏷️ TAGS:                                                                   │
│     @smoke       →  Quick sanity tests                                      │
│     @regression  →  Full test suite                                         │
│     @wip         →  Work in progress (skip)                                 │
│     @ignore      →  Temporarily disabled                                    │
│                                                                             │
│  ⚡ CUCUMBER ANNOTATIONS:                                                   │
│     @Given       →  Setup step                                              │
│     @When        →  Action step                                             │
│     @Then        →  Verification step                                       │
│     @And/@But    →  Additional steps                                        │
│     @Before      →  Run before each scenario                                │
│     @After       →  Run after each scenario                                 │
│     @BeforeAll/@AfterAll  →  Run once per suite                             │
│                                                                             │
│  🔧 CUCUMBEROPTIONS:                                                        │
│     features     →  Path to .feature files                                  │
│     glue         →  Package with steps + hooks                              │
│     plugin       →  Reports (html, json, extent, rerun)                     │
│     tags         →  Filter scenarios                                        │
│     dryRun       →  Validate without execution                              │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### What is BDD (Behavior Driven Development)?
> **Definition:** BDD is a software development approach where tests are written in plain English (Gherkin). This helps both business and technical teams understand what the tests do. Uses "Given-When-Then" format to describe behavior.

### What is Cucumber?
> **Definition:** Cucumber is a BDD framework that reads Gherkin files and executes the corresponding Java code (Step Definitions). It acts as a bridge between business requirements and automated tests.

### What is Gherkin?
> **Definition:** Gherkin is a simple, human-readable language used to write test scenarios. It uses keywords like: `Feature`, `Scenario`, `Given`, `When`, `Then`, `And`, `But`. Even non-programmers can easily understand it.

### Why Cucumber with Selenium?
Cucumber provides a BDD approach where tests are written in plain English (Gherkin syntax), making them readable by non-technical stakeholders. Combined with Selenium's mature browser automation, it creates a framework the whole team can collaborate on.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     BDD CUCUMBER FLOW                                        │
│                                                                             │
│   📝 Feature File          →    🔗 Step Definition    →    🚗 Selenium      │
│   (Gherkin - English)           (Java Methods)              (WebDriver)     │
│                                                                             │
│   Given I am on login      →    @Given("I am on...")  →    driver.get()    │
│   When I enter username    →    @When("I enter...")   →    el.sendKeys()   │
│   Then I see dashboard     →    @Then("I see...")     →    Assert.assert() │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 29.1 Real Project Folder Structure

```
selenium-cucumber-framework/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/qa/
│   │           ├── pages/              # Page Object Classes
│   │           │   ├── BasePage.java
│   │           │   ├── LoginPage.java
│   │           │   ├── DashboardPage.java
│   │           │   └── ProductPage.java
│   │           ├── factory/            # Driver Factory
│   │           │   └── DriverFactory.java
│   │           ├── utils/              # Utility Classes
│   │           │   ├── ConfigReader.java
│   │           │   ├── ExcelUtils.java
│   │           │   └── ScreenshotUtils.java
│   │           └── constants/          # Constants
│   │               └── AppConstants.java
│   └── test/
│       ├── java/
│       │   └── com/qa/
│       │       ├── stepdefinitions/    # Step Definitions
│       │       │   ├── LoginSteps.java
│       │       │   ├── DashboardSteps.java
│       │       │   └── CommonSteps.java
│       │       ├── hooks/              # Cucumber Hooks
│       │       │   └── Hooks.java
│       │       └── runners/            # Test Runners
│       │           ├── TestRunner.java
│       │           └── SmokeTestRunner.java
│       └── resources/
│           ├── features/               # Feature Files
│           │   ├── login.feature
│           │   ├── dashboard.feature
│           │   └── product.feature
│           ├── config/
│           │   ├── config.properties
│           │   └── config-qa.properties
│           ├── testdata/
│           │   └── testdata.xlsx
│           ├── schemas/
│           └── extent.properties
├── reports/                            # Extent + screenshots output
├── pom.xml
├── testng.xml
└── README.md
```

### 29.2 Maven Dependencies (pom.xml)

> **Definition:** Maven is a build automation tool that manages dependencies (libraries). In `pom.xml` file, we declare which libraries we need (Selenium, Cucumber, TestNG), and Maven automatically downloads them.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.qa</groupId>
    <artifactId>selenium-cucumber-framework</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <selenium.version>4.27.0</selenium.version>
        <cucumber.version>7.20.1</cucumber.version>
        <testng.version>7.10.2</testng.version>
    </properties>

    <dependencies>
        <!-- Selenium -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>

        <!-- Cucumber -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-testng</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-picocontainer</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <!-- TestNG -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Extent Reports for Cucumber -->
        <dependency>
            <groupId>tech.grasshopper</groupId>
            <artifactId>extentreports-cucumber7-adapter</artifactId>
            <version>1.14.0</version>
        </dependency>

        <!-- Data Faker -->
        <dependency>
            <groupId>net.datafaker</groupId>
            <artifactId>datafaker</artifactId>
            <version>2.4.0</version>
        </dependency>

        <!-- Apache POI for Excel -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.3.0</version>
        </dependency>

        <!-- commons-io for screenshots -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.16.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.0</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 29.3 Feature Files (Gherkin Syntax)

> **Definition:** Feature file is a `.feature` extension file where test scenarios are written in Gherkin language. Each feature file represents one functionality (e.g., Login, Registration). Business analysts can also read these files.

**Key Gherkin Keywords:**

| Keyword | Purpose | Example |
|---------|---------|---------|
| `Feature` | Functionality name | `Feature: Login Functionality` |
| `Scenario` | Single test case | `Scenario: Valid login` |
| `Given` | Precondition/Setup | `Given I am on login page` |
| `When` | Perform action | `When I click login button` |
| `Then` | Verify expected result | `Then I see dashboard` |
| `And/But` | Additional steps | `And I enter password` |
| `Background` | Common steps for all scenarios | Runs before every scenario |
| `Scenario Outline` | Data-driven testing | With `Examples` table |
| `Examples` | Data table for outline | `| username | password |` |
| `DocString` | Multi-line text | `"""` block inside step |
| `Data Table` | Tabular step data | Rows under a step |
| `#` | Comment | `# this is ignored` |
```gherkin
@login @regression
Feature: User Login
  As a user I want to login so that I can access dashboard

  Background:
    Given user is on login page

  @smoke
  Scenario: Successful login with valid credentials
    When user enters username "admin" and password "admin123"
    And user clicks login button
    Then user should see dashboard
    And page title should be "Dashboard"

  @negative
  Scenario: Failed login
    When user enters username "bad" and password "bad123"
    And user clicks login button
    Then error message "Invalid credentials" should display

  @datadriven
  Scenario Outline: Login with multiple users
    When user enters username "<username>" and password "<password>"
    And user clicks login button
    Then login result should be "<result>"

    Examples:
      | username | password | result  |
      | admin    | admin123 | success |
      | user1    | wrong    | fail    |
      | guest    | guest    | success |

  @datatable
  Scenario: Register user with details
    When user registers with following details
      | field    | value          |
      | name     | John Doe       |
      | email    | john@test.com  |
      | phone    | 9998887777     |
    Then registration should succeed
```

**product.feature:**
```gherkin
@regression @product
Feature: Product Management
  As a logged in user
  I want to manage products
  So that I can maintain inventory

  Background:
    Given I am logged in as "admin@example.com" with password "Admin123"
    And I navigate to products page

  @add
  Scenario: Add new product
    When I click on "Add Product" button
    And I fill product details:
      | name        | iPhone 15 Pro       |
      | price       | 999.99              |
      | category    | Electronics         |
      | description | Latest Apple iPhone |
    And I click on save button
    Then I should see success message "Product added successfully"
    And product "iPhone 15 Pro" should be visible in the list

  @search
  Scenario: Search for product
    When I search for "iPhone"
    Then I should see products containing "iPhone"
    And product count should be greater than 0

  @delete
  Scenario: Delete product
    When I select product "Test Product"
    And I click on delete button
    And I confirm deletion
    Then I should see success message "Product deleted"
```

### 29.4 DriverFactory (Driver Management)

> **Definition:** DriverFactory is a class that creates and manages WebDriver instances. Using `ThreadLocal`, each test thread gets its own driver — essential for parallel execution. Follows Factory Pattern.

**What is ThreadLocal?**
> ThreadLocal is a Java class that maintains a separate copy of a variable for each thread. In parallel testing, if 3 tests run simultaneously, each test gets its own separate browser — no interference.

```java
package com.qa.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    // ThreadLocal for parallel execution
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver initDriver(Properties prop) {

        String browserName = prop.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

        WebDriver driver;
        switch (browserName.toLowerCase()) {
            case "firefox":
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) fo.addArguments("-headless");
                driver = new FirefoxDriver(fo);
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:   // chrome
                ChromeOptions co = new ChromeOptions();
                if (headless) co.addArguments("--headless=new");
                co.addArguments("--disable-notifications");
                co.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(co);
        }
        tlDriver.set(driver);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(
                        prop.getProperty("implicitWait", "10"))));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(
                        prop.getProperty("pageLoadTimeout", "30"))));

        return driver;
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();          // prevent memory leak!
        }
    }
}
```

### 29.5 Config Reader

> **Definition:** ConfigReader is a utility class that reads configuration values from `.properties` files. This avoids hard-coded values — browser type, URL, timeout etc. are stored in config files, allowing environment switching without code changes.

**Benefits:**
- Environment-specific configs (QA, Staging, Prod)
- No code changes for different environments
- Single source of truth for all settings

```java
package com.qa.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    public static Properties initProperties() {
        if (prop == null) {
            prop = new Properties();

            // Get environment from system property or default to "qa"
            String env = System.getProperty("env", "qa");
            String configPath = "src/test/resources/config/config-" + env + ".properties";

            try {
                // First load base config
                prop.load(new FileInputStream(
                        "src/test/resources/config/config.properties"));

                // Then override with environment-specific config
                Properties envProp = new Properties();
                envProp.load(new FileInputStream(configPath));
                prop.putAll(envProp);

            } catch (IOException e) {
                // Fallback to base config only
                try {
                    prop.load(new FileInputStream(
                            "src/test/resources/config/config.properties"));
                } catch (IOException ex) {
                    throw new RuntimeException("Failed to load config: " + ex.getMessage());
                }
            }
        }
        return prop;
    }

    public static String get(String key) {
        return initProperties().getProperty(key);
    }
}
```

**config.properties:**
```properties
# Browser Configuration
browser=chrome
headless=false
implicitWait=10
pageLoadTimeout=30
explicitWait=15

# URLs
baseUrl=https://example.com

# Test Data
testDataPath=src/test/resources/testdata/testdata.xlsx

# Screenshots & Reports
screenshotOnFailure=true
reportPath=reports/
```

### 29.6 Page Objects

> **Definition:** Page Object Model (POM) is a design pattern where each web page is represented by a Java class. Page elements (locators) and actions (methods) are defined in one place. If UI changes, you only update in one place.

**POM Benefits:**

| Benefit | Description |
|---------|-------------|
| **Reusability** | Same page object used in multiple tests |
| **Maintainability** | UI change = only page class update |
| **Readability** | Test code is clean and readable |
| **Separation** | Page logic separate, Test logic separate |

**BasePage.java:**
```java
package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.factory.DriverFactory;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Common methods for all pages
    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    // Reusable action methods (with explicit waits baked in)
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
```

**LoginPage.java:**
```java
package com.qa.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // Locators — By objects
    private By usernameInput  = By.id("email");
    private By passwordInput  = By.id("password");
    private By loginButton    = By.cssSelector("button[type='submit']");
    private By errorMessage   = By.cssSelector(".error-message");
    private By forgotLink     = By.linkText("Forgot Password");

    // Page Actions (fluent — return this or next page)
    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage();
    }

    public DashboardPage login(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isVisible(errorMessage);
    }

    // Assertions within page
    public LoginPage verifyLoginPageLoaded() {
        org.testng.Assert.assertTrue(isVisible(usernameInput));
        org.testng.Assert.assertTrue(isVisible(passwordInput));
        return this;
    }
}
```

**DashboardPage.java:**
```java
package com.qa.pages;

import org.openqa.selenium.By;

public class DashboardPage extends BasePage {

    private By welcomeMessage = By.cssSelector(".welcome-msg");
    private By userNameLabel  = By.cssSelector(".user-name");
    private By logoutButton   = By.cssSelector("button.logout");
    private By productsLink   = By.linkText("Products");

    public boolean isDashboardLoaded() {
        return isVisible(welcomeMessage) && getCurrentUrl().contains("dashboard");
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public String getUserName() {
        return getText(userNameLabel);
    }

    public void navigateToProducts() {
        click(productsLink);
    }

    public LoginPage logout() {
        click(logoutButton);
        return new LoginPage();
    }
}
```

### 29.7 Cucumber Hooks

> **Definition:** Hooks are Java methods that run before/after scenarios — used for technical setup (browser launch, screenshots, cleanup). `@Before`/`@After` run per scenario; `order=` controls sequence; `@BeforeAll`/`@AfterAll` run once per suite.

```java
package com.qa.hooks;

import com.qa.factory.DriverFactory;
import com.qa.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

public class Hooks {

    private static Properties prop;
    private DriverFactory factory = new DriverFactory();

    @BeforeAll
    public static void loadConfig() {          // runs ONCE per suite
        prop = ConfigReader.initProperties();
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("Starting: " + scenario.getName());
        WebDriver driver = factory.initDriver(prop);
        driver.get(prop.getProperty("baseUrl"));
    }

    // Tagged hook — runs only for specific scenarios
    @Before("@login")
    public void loginSetup() {
        System.out.println("Login tag hook — pre-login steps if needed");
    }

    @After(order = 0)                          // lower order runs FIRST in @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName()); // attach!
        }
        DriverFactory.quitDriver();
    }
}
```

### 29.8 Step Definitions
> **Definition:** Step definitions are Java methods that execute the actual Selenium code for each Gherkin step. `@Given`, `@When`, `@Then` annotations map methods to steps. Cucumber Expressions (`{string}`, `{int}`) capture values from steps.

**Step parameter types:** `{string}` `"quoted"`, `{int}`, `{double}`, `{word}` (single word), `{float}`, `{bigdecimal}`, plus custom types via `@ParameterType`. Regex-style also works: `@Then("^result is (.*)$")`.

**LoginSteps.java:**
```java
package com.qa.stepdefinitions;

import com.qa.factory.DriverFactory;
import com.qa.pages.DashboardPage;
import com.qa.pages.LoginPage;
import com.qa.utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import java.util.Map;

public class LoginSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        DriverFactory.getDriver().get(ConfigReader.get("baseUrl"));
        loginPage = new LoginPage();
        loginPage.verifyLoginPageLoaded();
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click on login button")
    public void iClickOnLoginButton() {
        dashboardPage = loginPage.clickLogin();
    }

    @When("I login with credentials:")
    public void iLoginWithCredentials(DataTable dataTable) {
        Map<String, String> credentials =
                dataTable.asMap(String.class, String.class);
        dashboardPage = loginPage.login(
            credentials.get("username"),
            credentials.get("password"));
    }

    @Then("I should be redirected to dashboard")
    public void iShouldBeRedirectedToDashboard() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(),
            "Dashboard did not load");
    }

    @Then("I should see welcome message {string}")
    public void iShouldSeeWelcomeMessage(String expectedMessage) {
        Assert.assertTrue(
            dashboardPage.getWelcomeMessage().contains(expectedMessage));
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String expectedError) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error not displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }

    @Then("I should see {string}")
    public void iShouldSee(String result) {
        if (result.contains("Welcome")) {
            Assert.assertTrue(dashboardPage.isDashboardLoaded());
            Assert.assertTrue(
                dashboardPage.getWelcomeMessage().contains(result));
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed());
            Assert.assertEquals(loginPage.getErrorMessage(), result);
        }
    }
}
```

**CommonSteps.java (shared across features):**
```java
package com.qa.stepdefinitions;

import com.qa.pages.DashboardPage;
import com.qa.pages.LoginPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class CommonSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        loginPage = new LoginPage();
        dashboardPage = loginPage.login(username, password);
    }

    @Given("I navigate to products page")
    public void iNavigateToProductsPage() {
        dashboardPage.navigateToProducts();
    }

    @Then("I should see success message {string}")
    public void iShouldSeeSuccessMessage(String message) {
        // generic toast/success message check — page-agnostic
        org.openqa.selenium.WebElement toast =
            com.qa.factory.DriverFactory.getDriver().findElement(
                org.openqa.selenium.By.cssSelector(".success-message"));
        Assert.assertTrue(toast.getText().contains(message));
    }
}
```

**Sharing state between step classes:** use `cucumber-picocontainer` dependency — constructor injection shares a `TestContext`/`World` object across step classes automatically (PicoContainer creates one instance per scenario).

### 29.9 TestNG Runner

> **Definition:** TestNG Runner is a Java class that executes Cucumber tests. In `@CucumberOptions` annotation, we specify where feature files are located, where step definitions are, which tags to run, and where to generate reports.

**CucumberOptions Explained:**
```
@CucumberOptions(
    features = "..."     →  Path to feature files
    glue = "..."         →  Package with steps + hooks
    plugin = "..."       →  Report formats (html, json, extent, rerun)
    tags = "..."         →  Filter scenarios (@smoke, @regression)
    dryRun = true/false  →  Validate steps without running
    monochrome = true    →  Clean console output
)
```

**TestRunner.java:**
```java
package com.qa.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.qa.stepdefinitions", "com.qa.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "rerun:target/failed_scenarios.txt"
    },
    tags = "@smoke or @regression",
    monochrome = true,
    dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)       // scenario-level parallelism
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

**SmokeTestRunner.java:**
```java
package com.qa.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.qa.stepdefinitions", "com.qa.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/smoke-report.html"
    },
    tags = "@smoke",
    monochrome = true
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
}
```

### 29.10 testng.xml Configuration

> **Definition:** `testng.xml` is a configuration file that controls test execution. It defines which test classes/runners to run, whether to enable parallel execution, and how many threads to use. This file is used when running `mvn test`.

**Parallel Modes:**

| Mode | Description |
|------|-------------|
| `parallel="tests"` | Different `<test>` tags in parallel |
| `parallel="classes"` | Different classes in parallel |
| `parallel="methods"` | Different methods in parallel |
| `thread-count="3"` | Maximum 3 threads simultaneously |

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Selenium Cucumber Suite" parallel="tests" thread-count="3">

    <test name="Smoke Tests">
        <classes>
            <class name="com.qa.runners.SmokeTestRunner"/>
        </classes>
    </test>

    <test name="Regression Tests">
        <classes>
            <class name="com.qa.runners.TestRunner"/>
        </classes>
    </test>

</suite>
```

### 29.11 Extent Reports Configuration

> **Definition:** Extent Reports is a powerful HTML reporting library that generates beautiful, interactive test reports. To integrate with Cucumber, we use `extentreports-cucumber7-adapter`. Reports show screenshots, steps, pass/fail status, everything.

**Report Features:**
- 📊 Dashboard with pass/fail statistics
- 📸 Screenshots on failure (attached via `scenario.attach()` in Hooks)
- 📝 Step-by-step execution logs
- 🏷️ Tag/Category wise filtering
- 📱 Responsive HTML design

**extent.properties** (`src/test/resources/extent.properties`):
```properties
# Report Path
extent.reporter.spark.start=true
extent.reporter.spark.out=reports/SparkReport.html

# Screenshot settings
screenshot.dir=screenshots/
screenshot.rel.path=../screenshots/

# Report configuration
extent.reporter.spark.config=src/test/resources/extent-config.xml

# System Info
systeminfo.OS=Windows
systeminfo.Author=QA Team
systeminfo.Browser=Chrome
systeminfo.Framework=Selenium + Cucumber
```

### 29.12 Selenium vs Playwright Cucumber - Quick Comparison

> **Definition:** If you know Playwright Cucumber, Selenium Cucumber is the same! Feature files remain exactly the same (Gherkin doesn't change). Only Step Definitions and driver/page handling code changes.

```
┌──────────────────────────────────────────────────────────────────────────┐
│           SELENIUM CUCUMBER vs PLAYWRIGHT CUCUMBER                        │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  FEATURE FILES       → Same Gherkin syntax (No change!)                  │
│  STEP DEFINITIONS    → Different page interaction methods                 │
│  HOOKS               → Different driver management                        │
│  RUNNER              → Same TestNG runner configuration                   │
│                                                                          │
├──────────────────────────────────────────────────────────────────────────┤
│  KEY DIFFERENCES:                                                        │
│                                                                          │
│  SELENIUM                          │  PLAYWRIGHT                         │
│  ─────────────────────────────────────────────────────────────────────── │
│  WebDriver driver = new Chrome()   │  Page page = browser.newPage()      │
│  driver.findElement(By.id())       │  page.locator("#id")                │
│  WebDriverWait wait = new...       │  Auto-waiting (no explicit wait)    │
│  driver.switchTo().frame()         │  page.frameLocator()                │
│  Actions actions = new Actions()   │  page.mouse(), page.keyboard()      │
│  Cookies manual                    │  context.storageState()             │
│                                                                          │
├──────────────────────────────────────────────────────────────────────────┤
│  MIGRATION CHECKLIST (Playwright → Selenium):                            │
│  □ Replace Page/locator with WebDriver + By.findElement                  │
│  □ Add explicit waits (WebDriverWait) — no auto-wait here                │
│  □ Update frame handling to switchTo().frame()                           │
│  □ Replace page.mouse/keyboard with Actions class                        │
│  □ Update PlaywrightFactory → DriverFactory (ThreadLocal)                │
│  □ Replace tracing/screenshot opts with TakesScreenshot                  │
│                                                                          │
└──────────────────────────────────────────────────────────────────────────┘
```

### 29.13 Running Tests

> **Definition:** Maven commands are used to run tests. From command line, you can control tags, environment, parallel execution. Same commands are used in CI/CD pipelines.

**Common Commands Quick Reference:**
```
mvn test                        →  Run all tests
mvn test -Dcucumber.filter.tags="@smoke"   →  Run only @smoke tagged
mvn test -Denv=qa               →  Use QA environment config
mvn test -Dcucumber.execution.parallel.enabled=true  →  Parallel run
```

```bash
# Run all tests
mvn test

# Run with specific tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@smoke and @login"
mvn test -Dcucumber.filter.tags="not @wip"

# Run with environment
mvn test -Denv=qa

# Run in parallel
mvn test -Dcucumber.execution.parallel.enabled=true

# Run specific feature
mvn test -Dcucumber.features="src/test/resources/features/login.feature"

# Run failed scenarios
mvn test -Dcucumber.features="@target/failed_scenarios.txt"

# Generate reports
mvn test -Dcucumber.plugin="html:target/reports.html"
```

### Interview Questions - BDD/Cucumber

> **Tip:** These questions are asked in almost every interview. Remember short, crisp answers!

**Q1: What is BDD and how does Cucumber implement it?**
- BDD = Behavior Driven Development
- Tests written in plain English (Gherkin)
- Bridge between business and technical teams
- Cucumber parses Gherkin and runs step definitions

**Q2: What is the difference between Scenario and Scenario Outline?**
- Scenario: Single test case with fixed data
- Scenario Outline: Data-driven test with Examples table
- Scenario Outline runs multiple times with different data

**Q3: What are Cucumber Hooks?**
- @Before: Runs before each scenario
- @After: Runs after each scenario
- @BeforeStep/@AfterStep: Runs before/after each step
- Can be tagged: @Before("@smoke")
- Order parameter controls execution order

**Q4: How do you share data between steps?**
- Instance variables in step definition class
- PicoContainer for dependency injection (shared TestContext)
- Scenario context/World object
- ThreadLocal for parallel execution

**Q5: How do you handle parallel execution with Cucumber and Selenium?**
- Use ThreadLocal for WebDriver instances
- Configure DataProvider with parallel=true
- Use thread-count in testng.xml
- Each thread gets isolated browser session

**Q6: What is the difference between Background and Hooks?**
- Background: Gherkin keyword, runs before each scenario IN FEATURE
- Hooks: Java code, runs before each scenario GLOBALLY
- Background is visible in feature file
- Hooks handle technical setup (driver, DB)

**Q7: How do you handle test data in Cucumber?**
- Inline: `When I enter "username"`
- DataTable: Tabular data in steps
- Scenario Outline + Examples: Multiple data sets
- External: Excel, JSON, Database
- Faker library for dynamic data

**Q8: Explain the Cucumber Test Runner configuration**
- features: Path to .feature files
- glue: Package containing step definitions & hooks
- plugin: Reporting (html, json, extent)
- tags: Filter scenarios (@smoke, @regression)
- dryRun: Validate steps without execution

**Q9: What is glue?**
- The package path telling Cucumber where step definitions + hooks live. Wrong glue = "undefined steps" errors.

**Q10: What is dryRun?**
- `dryRun=true` compiles feature→steps mapping without launching browsers — used to find missing step definitions quickly.

**Q11: How to rerun only failed scenarios?**
- `rerun:target/failed_scenarios.txt` plugin writes failed scenario lines → second runner/command with `features = "@target/failed_scenarios.txt"`.

**Q12: How to pass tabular data in a step?**
- Data Table under the step → `DataTable` argument → `asMap()` / `asLists()` / `asMaps()`.

**Q13: How do you take a screenshot on scenario failure?**
- In `@After` hook: `if (scenario.isFailed())` → `((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)` → `scenario.attach(bytes, "image/png", name)`.

**Q14: How to run Cucumber tests on CI?**
- `mvn test` with headless ChromeOptions (`--headless=new`), `-Denv=staging` for config, `-Dcucumber.filter.tags` for suite slicing; reports archived as artifacts.

---

## 30. SELENIUM GRID & PARALLEL EXECUTION

### 30.1 Parallel Execution with TestNG
```xml
<suite name="ParallelSuite" parallel="methods" thread-count="4">
    <!-- parallel options: methods | classes | tests | instances -->
</suite>
```
```xml
<!-- parallel browsers via <test> tags -->
<suite name="CrossBrowser" parallel="tests" thread-count="3">
    <test name="ChromeTest">
        <parameter name="browser" value="chrome"/>
        <classes><class name="com.qa.LoginTest"/></classes>
    </test>
    <test name="FirefoxTest">
        <parameter name="browser" value="firefox"/>
        <classes><class name="com.qa.LoginTest"/></classes>
    </test>
</suite>
```

```java
// ★ MUST use ThreadLocal driver — else tests stomp each other
private static ThreadLocal<WebDriver> tl = new ThreadLocal<>();
```

### 30.2 Selenium Grid 4 Setup
```bash
# Download selenium-server jar (4.x)

# Standalone (all-in-one)
java -jar selenium-server-4.x.jar standalone

# Hub + Node (distributed)
java -jar selenium-server.jar hub                          # port 4444
java -jar selenium-server.jar node --hub http://hub:4444   # register

# Individual roles
java -jar selenium-server.jar router
java -jar selenium-server.jar distributor
java -jar selenium-server.jar session-map
java -jar selenium-server.jar session-queue
java -jar selenium-server.jar node
java -jar selenium-server.jar event-bus

# Docker — easiest
docker run -d -p 4444:4444 --shm-size="2g" \
    selenium/standalone-chrome:latest
docker run -d -p 4444:4444 selenium/hub:latest
docker run -d -p 5555:5555 --link hub:hub selenium/node-chrome
```

```yaml
# docker-compose.yml
version: "3"
services:
  hub:
    image: selenium/hub:latest
    ports: ["4442:4442", "4443:4443", "4444:4444"]
  chrome:
    image: selenium/node-chrome:latest
    depends_on: [hub]
    environment:
      SE_EVENT_BUS_HOST: hub
      SE_EVENT_BUS_PUBLISH_PORT: 4442
      SE_EVENT_BUS_SUBSCRIBE_PORT: 4443
      SE_NODE_MAX_SESSIONS: 5
    shm_size: 2gb                      # ★ prevents Chrome crashes
```

### 30.3 RemoteWebDriver — connect to Grid
```java
ChromeOptions options = new ChromeOptions();
WebDriver driver = new RemoteWebDriver(
        new URL("http://localhost:4444/wd/hub"), options);
driver.get("https://app.com");
```

### Grid 4 Components (interview!)
| Component | Role |
|-----------|------|
| **Router** | Entry point — receives all requests |
| **Distributor** | Matches new session requests to capable nodes |
| **Session Queue** | Queues new session requests |
| **Session Map** | Maps sessionId → node |
| **Node** | Machine hosting browser slots |
| **Event Bus** | Internal pub/sub between components |

### Interview Questions

**Q1: How to run tests in parallel?**
- TestNG `parallel="methods"` + ThreadLocal driver; or Selenium Grid + RemoteWebDriver for distributed machines.

**Q2: Hub vs Node (classic Grid terminology)?**
- Grid 4 has no "hub" monolith — Router+Distributor+SessionMap+Queue together do hub work; nodes run browsers. `standalone` = all-in-one.

**Q3: Why ThreadLocal with parallel tests?**
- Shared static driver → tests overwrite each other's browser. ThreadLocal = one driver per thread. Always `remove()` after quit.

**Q4: What is RemoteWebDriver?**
- WebDriver that talks to a remote Grid URL instead of a local driver — enables cross-machine/browser testing.

**Q5: Why `shm_size` in docker grid?**
- Chrome uses /dev/shm; default Docker shm is tiny (64MB) → browser crashes. 2gb fixes it.

---

## 31. CI/CD INTEGRATION

### 31.1 GitHub Actions
```yaml
# .github/workflows/selenium.yml
name: Selenium Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
      - name: Cache maven
        uses: actions/cache@v4
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
      - name: Run tests
        run: mvn test -Dbrowser=chrome -Dheadless=true
      - name: Upload report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: extent-report
          path: reports/
```

### 31.2 Jenkins Pipeline
```groovy
pipeline {
    agent any
    tools { maven 'M3'; jdk 'JDK17' }
    stages {
        stage('Checkout') {
            steps { git 'https://github.com/org/repo.git' }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test -Dbrowser=chrome -DsuiteXml=testng.xml'
            }
        }
    }
    post {
        always {
            publishHTML(target: [
                reportDir: 'reports',
                reportFiles: 'extent-report.html',
                reportName: 'Test Report'
            ])
            junit 'target/surefire-reports/*.xml'
        }
    }
}
```

### 31.3 Docker
```dockerfile
# Dockerfile
FROM maven:3.9-eclipse-temurin-17
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
CMD ["mvn", "test", "-Dbrowser=chrome", "-Dheadless=true"]
```

```yaml
# docker-compose.yml — app tests against grid
services:
  selenium-hub:
    image: selenium/hub:latest
    ports: ["4444:4444"]
  chrome:
    image: selenium/node-chrome:latest
    shm_size: 2gb
    depends_on: [selenium-hub]
```

### 31.4 CI/CD Best Practices
- Always run **headless** in CI (`-Dheadless=true` → code reads property → adds ChromeOptions)
- Run smoke suite on PR, regression on schedule (`cron`)
- Upload reports + screenshots as artifacts on failure
- Use Grid containers or cloud (BrowserStack/SauceLabs) — no browser on CI agent otherwise
- Parameterize env/URL via `-D` system properties or env vars — never hardcode

### Interview Questions

**Q1: How do you run Selenium tests in CI?**
- Headless Chrome + Maven + GitHub Actions/Jenkins; artifacts uploaded on failure; Grid or cloud grid for browser matrix.

**Q2: How to handle different environments in CI?**
- `-Denv=staging` → ConfigReader picks `config-staging.properties`; secrets via CI env vars, never committed.

---

## 32. COMMON EXCEPTIONS & FLAKY TEST FIXES

### All Exceptions — One Table
| Exception | Cause | Fix |
|-----------|-------|-----|
| `NoSuchElementException` | Element absent/wrong locator | Verify locator, wait for presence |
| `StaleElementReferenceException` | DOM refreshed, old ref dead | Re-locate, `refreshed()` wait, retry loop |
| `ElementNotInteractableException` | Hidden/disabled element | Wait for visibility, scroll into view |
| `ElementClickInterceptedException` | Overlay covers element | Wait for overlay gone / JS click / scroll |
| `TimeoutException` | Wait condition never met | Check locator/state, increase timeout |
| `NoSuchFrameException` | Wrong frame index/name | Verify iframe exists, wait frameToBeAvailable |
| `NoSuchWindowException` | Window closed/handle wrong | Check handles, timing |
| `NoAlertPresentException` | Alert not up | `alertIsPresent()` wait |
| `InvalidSelectorException` | Bad CSS/XPath syntax | Fix selector (e.g. `#123` invalid css → use `[id='123']`) |
| `InvalidArgumentException` | Bad argument to driver | Check nulls, URL format |
| `WebDriverException` | Driver crash/version mismatch | Update driver/browser, Selenium Manager |
| `SessionNotCreatedException` | Browser/driver mismatch | Selenium Manager re-resolves |
| `ElementNotVisibleException` (old) | Hidden element | visibilityOfElementLocated wait |
| `NoSuchSessionException` | quit() then used driver | Check teardown logic |
| `InvalidElementStateException` | clear() on disabled field | Enable via JS first |
| `MoveTargetOutOfBoundsException` | Actions move off-viewport | scrollIntoView first |
| `ScriptTimeoutException` | executeAsyncScript callback never called | Call callback in script |
| `UnexpectedTagNameException` | Select on non-select tag | Use Bootstrap dropdown pattern |
| `UnhandledAlertException` | Unexpected alert blocking | Handle/dismiss alert first |
| `ConnectionClosedException` | Driver died mid-command | Check driver process/browser version |

### Flaky Test Fixes — Top Causes
1. **Timing** → explicit waits, never sleep
2. **Stale refs** → re-locate elements
3. **Shared state** → independent test data, ThreadLocal driver
4. **Animations** → wait for overlay/spinner invisibility
5. **Dynamic IDs** → stable attribute locators
6. **Order dependency** → tests must be standalone
7. **Resolution/viewport** → fixed window size
8. **Lazy load** → wait for AJAX/loader completion

### Interview Questions

**Q1: What exceptions have you faced and how did you fix them?**
- Walk through StaleElement (re-locate), ClickIntercepted (wait for overlay/JS click), NoSuchElement (explicit wait), Timeout (correct condition).

**Q2: How do you reduce flakiness?**
- Explicit waits, stable locators (data-testid), independent tests, retry analyzer (max 2 — don't mask real bugs), fixed viewport, quarantine genuinely flaky tests.

---

## 33. JAVA PROGRAMMING FOR INTERVIEWS

### 33.1 Collections
```java
// List — ordered, duplicates OK
List<String> list = new ArrayList<>();
list.add("a"); 
list.get(0); 
list.size(); 
list.contains("a");

// Set — unique
Set<String> set = new HashSet<>(list);          // dedupe
Set<String> ordered = new LinkedHashSet<>();    // keeps order

// Map — key-value
Map<String, String> map = new HashMap<>();
map.put("k", "v"); 
map.get("k"); 
map.containsKey("k");
map.forEach((k, v) -> System.out.println(k + v));

// Iterate
for (Map.Entry<String, String> e : map.entrySet()) { }

// Frequency count (classic interview)
Map<String, Integer> freq = new HashMap<>();
for (String w : words)
    freq.merge(w, 1, Integer::sum);              // or getOrDefault
```

### 33.2 Streams
```java
List<String> names = List.of("a", "bb", "ccc");

names.stream()
     .filter(s -> s.length() > 1)
     .map(String::toUpperCase)
     .sorted()
     .forEach(System.out::println);

names.stream().collect(Collectors.toList());
names.stream().anyMatch(s -> s.startsWith("a"));
names.stream().count();
names.stream().findFirst().orElse("none");

// WebElement list → text list (used in dropdown code!)
List<String> texts = options.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
```

### 33.3 Exception Handling
```java
try {
    element.click();
} catch (NoSuchElementException e) {
    // handle
} finally {
    driver.quit();                     // always runs
}

// try-with-resources
try (FileInputStream fis = new FileInputStream("f")) { }
```

### 33.4 Interfaces and Abstract Classes
```java
// Interface — contract only (Selenium: WebDriver, TakesScreenshot)
interface Driver { void get(String url); }

// Abstract — partial implementation (framework: BasePage)
abstract class BasePage {
    abstract void load();
    void wait(int s) { /* shared impl */ }
}
```

### 33.5 Generics
```java
public <T> List<T> getElements(By by) { ... }
FluentWait<WebDriver> wait = new FluentWait<>(driver);
```

### 33.6 Lambda Expressions
```java
options.forEach(o -> System.out.println(o.getText()));
options.stream().filter(o -> o.isDisplayed());
Runnable r = () -> driver.findElement(By.id("x")).click();
```

### Common Interview Coding Tasks
- Reverse a string / check palindrome
- Count char/word frequency (HashMap)
- Find duplicates in array (HashSet)
- FizzBuzz
- Second largest element
- Swap two strings without third variable
- `System.out.println(texts)` patterns — star/pyramid
- StringBuffer vs StringBuilder vs String (immutability, sync)

---

## 34. BEST PRACTICES & DESIGN PATTERNS

### 34.1 Best Practices
```
□ One test = one assertion intent (soft asserts for multi-field check)
□ Independent tests — never depend on order
□ Explicit waits only — no Thread.sleep
□ ThreadLocal driver for parallel
□ Data-driven via DataProvider — no hardcoded data
□ Page objects — no locators inside test classes
□ Meaningful test names/messages in assertions
□ Cleanup in @AfterMethod (finally-safe)
□ Screenshots on failure via listener
□ Small implicit (or zero) + targeted explicit waits
□ Never commit credentials — env vars/secrets
□ Keep waits, URLs, timeouts in config
```

### 34.2 Design Patterns in Selenium Frameworks
| Pattern | Where |
|---------|-------|
| **Page Object** | Page classes encapsulate locators + actions |
| **Factory** | DriverFactory creates browser-specific driver |
| **Singleton** | ConfigReader, ExtentManager (single instance) |
| **Builder** | ChromeOptions, RequestSpecBuilder-style config |
| **Fluent Interface** | page.login().verifyDashboard() chaining |
| **Strategy** | Browser selection via enum/switch |
| **Listener/Observer** | ITestListener, WebDriverListener |
| **Decorator** | EventFiringDecorator wraps driver |
| **Facade** | BasePage hides wait logic |

### 34.3 Test Design
- **AAA pattern:** Arrange (setup data) → Act (action) → Assert (verify)
- **Page objects** hide DOM; tests express business flow
- **Tags/groups** → smoke vs regression vs sanity

### Interview Questions

**Q1: Which design patterns have you used in your framework?**
- POM + Factory (driver creation) + Singleton (config/report) + Fluent (page chaining) + Listener (screenshot/retry).

**Q2: Singleton example in framework?**
- `private static ExtentReports instance; getInstance()` — one report object across suite.

---

## 35. COMMON INTERVIEW QUESTIONS

### Core Selenium

**Q1: What is Selenium? Components?**
- Open-source browser automation suite: IDE, WebDriver, Grid.

**Q2: Difference between findElement and findElements?**
- Singular throws NoSuchElementException; plural returns empty List.

**Q3: What is WebDriver — class or interface?**
- Interface; RemoteWebDriver implements it; ChromeDriver extends RemoteWebDriver.

**Q4: How many locators?**
- 8: id, name, className, tagName, linkText, partialLinkText, cssSelector, xpath.

**Q5: get() vs navigate()?**
- navigate adds back/forward/refresh + accepts URL object; get waits for page load.

**Q6: close() vs quit()?**
- close = current window; quit = whole session.

**Q7: driver.getWindowHandle vs getWindowHandles?**
- current handle (String) vs all handles (Set).

**Q8: getText() vs getAttribute("value")?**
- Visible inner text vs input field's value property.

**Q9: getDomAttribute vs getDomProperty?**
- Raw HTML attribute vs live JS property (Selenium 4).

**Q10: How to check element exists without exception?**
- `findElements(...).size() > 0`.

**Q11: implicit vs explicit vs fluent wait?**
- Global presence timeout vs per-element condition vs custom poll+ignore.

**Q12: Can we use both implicit and explicit?**
- Technically yes, docs say don't — unpredictable compounding.

**Q13: What is Thread.sleep and why avoid?**
- Blind pause — slows suite, doesn't guarantee readiness.

**Q14: How to handle dropdown?**
- `Select` class for `<select>`; findElements+click for custom dropdowns.

**Q15: How to handle alerts?**
- `switchTo().alert()` → accept/dismiss/getText/sendKeys.

**Q16: Three ways to switch to frame?**
- index, name/id, WebElement.

**Q17: How to handle multiple windows?**
- getWindowHandles → loop → switchTo; switch back via saved parent handle.

**Q18: How to take screenshot on failure?**
- ITestListener.onTestFailure + TakesScreenshot.

**Q19: How to upload file?**
- sendKeys absolute path to `<input type='file'>`.

**Q20: How to do drag-drop?**
- Actions.dragAndDrop; fallback clickAndHold+moveTo+release or JS.

### Advanced

**Q21: StaleElementReferenceException — cause & fix?**
- DOM refresh invalidated ref → re-locate / refreshed() wait / retry.

**Q22: How to handle shadow DOM?**
- `getShadowRoot()` (S4) then CSS selector; XPath can't pierce.

**Q23: How to handle authentication popup?**
- creds in URL or S4 `HasAuthentication.register(UsernameAndPassword)`.

**Q24: How to disable browser notifications?**
- ChromeOptions `--disable-notifications` or prefs.

**Q25: Selenium 4 new features?**
- Relative locators, newWindow, element screenshot, CDP, Selenium Manager, Grid 4, BiDi, print to PDF, WebDriverListener.

**Q26: What is CDP usage in Selenium?**
- DevTools session → network capture/throttle, geolocation, device emulation, auth.

**Q27: How to run parallel tests?**
- TestNG parallel + ThreadLocal driver; Grid/RemoteWebDriver for distribution.

**Q28: Grid 4 components?**
- Router, Distributor, Session Queue, Session Map, Node, Event Bus.

**Q29: Why shm_size in docker grid?**
- Chrome needs shared memory; default 64MB crashes it → set 2gb.

**Q30: How to handle captcha/OTP?**
- Not automatable by design → test keys, env flag, or manual step; never bypass prod captcha.

### Framework

**Q31: Explain your framework.**
- Layered: DriverFactory(ThreadLocal) → BaseTest → Pages(POM) → Tests(TestNG) → utils/listeners → testng.xml → Maven → Jenkins/GitHub Actions → ExtentReports.

**Q32: POM vs PageFactory?**
- By fields + lazy findElement vs @FindBy proxies + initElements; PageFactory adds @FindAll/@FindBys/@CacheLookup.

**Q33: How do you retry failed tests?**
- IRetryAnalyzer + IAnnotationTransformer for global apply.

**Q34: How to do data-driven?**
- @DataProvider + Excel (POI)/CSV/JSON; Cucumber Scenario Outline for BDD.

**Q35: How do you manage secrets?**
- Env vars / CI secrets; never in code — `System.getenv("API_KEY")`.

**Q36: How to generate reports?**
- ExtentReports + ITestListener + screenshots; or Allure.

### Scenario-Based

**Q37: Element works manually but automation fails?**
- Check iframe → shadow DOM → dynamic id → timing (explicit wait) → overlay interception.

**Q38: Test passes locally, fails in CI?**
- Headless rendering diffs → set window-size; timing → slower CI needs longer waits; timezone/locale; missing fonts; resource limits.

**Q39: Click works, then fails randomly?**
- ElementClickIntercepted from loading overlay → wait for invisibilityOfElementLocated(spinner) before click.

**Q40: How would you automate a file download verification?**
- Set download dir via prefs → click → poll dir for file (watch `.crdownload`/`part` temp extensions) → assert size/name.

**Q41: Login once, reuse across tests?**
- Store cookies/session → re-add in new sessions (`addCookie` after visiting domain) — similar to Playwright storageState.

**Q42: How to test in multiple browsers/locales?**
- testng.xml `<test>` params + parallel="tests"; or Grid node configs; i18n via `-Dlang` → user-agent/accept-language prefs.

---

## CONCLUSION

### Selenium Mastery Roadmap
```
┌──────────────────────────────────────────────────────────────────┐
│                    SELENIUM MASTERY ROADMAP                      │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  🎯 FUNDAMENTALS (Sections 1-8)                                  │
│     • Architecture: client → W3C HTTP → driver → browser         │
│     • 8 locators, findElement vs findElements                    │
│     • XPath axes + CSS selectors                                 │
│     • Implicit / Explicit / Fluent waits                         │
│                                                                  │
│  🔧 INTERACTIONS (Sections 9-20)                                 │
│     • Select, Actions, JavaScriptExecutor                        │
│     • switchTo: alert / frame / window                           │
│     • upload via sendKeys, download via prefs                    │
│     • cookies, screenshots, tables, shadow DOM                   │
│                                                                  │
│  🏗️ FRAMEWORK (Sections 21-29)                                   │
│     • Selenium 4: relative locators, newWindow, CDP, Manager     │
│     • POM vs PageFactory, TestNG, listeners, retry               │
│     • Excel/POI, ExtentReports, Cucumber BDD                     │
│                                                                  │
│  🚀 ADVANCED (Sections 30-35)                                    │
│     • Grid 4 components, Docker, RemoteWebDriver                 │
│     • ThreadLocal parallelism, CI/CD pipelines                   │
│     • Exceptions + flaky fixes, design patterns                  │
│     • Java refresh + scenario Qs                                 │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

### Quick Interview Tips
1. **Always mention W3C protocol** — shows Selenium 4 knowledge
2. **Compare with Playwright honestly** — auto-wait & network mocking gaps, but Grid/IE/Safari/ecosystem strengths
3. **Know Selenium 4 features cold** — relative locators, Selenium Manager, CDP, newWindow
4. **Explain ThreadLocal** — always asked for parallel execution
5. **Real framework examples** — describe YOUR project structure confidently
6. **Never say Thread.sleep** without "I avoid it"
7. **Stale element + waits + dropdown = top 3 practical questions**

### Resources
- **Official Docs:** [selenium.dev/documentation](https://www.selenium.dev/documentation/)
- **GitHub:** [github.com/SeleniumHQ/selenium](https://github.com/SeleniumHQ/selenium)
- **W3C Spec:** [w3.org/TR/webdriver](https://w3c.github.io/webdriver/)
- **Practice Sites:** the-internet.herokuapp.com, demoqa.com, automationpractice.com, selectorshub practice pages
- **Grid images:** hub.docker.com/u/selenium

---

**Good luck with your interview! 🎉**



