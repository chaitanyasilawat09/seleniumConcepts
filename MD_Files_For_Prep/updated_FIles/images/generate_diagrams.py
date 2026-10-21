#!/usr/bin/env python3
"""Generate PNG diagrams for Selenium & RestAssured interview guides."""
import os
from PIL import Image, ImageDraw, ImageFont

OUT = os.path.dirname(os.path.abspath(__file__))

REG = "/System/Library/Fonts/Helvetica.ttc"
BOLD = "/System/Library/Fonts/Supplemental/Arial Bold.ttf"
MONO = "/System/Library/Fonts/Menlo.ttc"

def font(size, style="reg"):
    p = {"reg": REG, "bold": BOLD, "mono": MONO}[style]
    return ImageFont.truetype(p, size)

# palette
BLUE, LBLUE = "#2563EB", "#DBEAFE"
GREEN, LGREEN = "#16A34A", "#DCFCE7"
ORANGE, LORANGE = "#EA580C", "#FFEDD5"
PURPLE, LPURPLE = "#7C3AED", "#EDE9FE"
RED, LRED = "#DC2626", "#FEE2E2"
TEAL, LTEAL = "#0D9488", "#CCFBF1"
GRAY, LGRAY = "#475569", "#F1F5F9"
PINK, LPINK = "#DB2777", "#FCE7F3"
DARK = "#1E293B"


def canvas(w, h, title=None):
    img = Image.new("RGB", (w, h), "#FFFFFF")
    d = ImageDraw.Draw(img)
    if title:
        tw = d.textlength(title, font=font(34, "bold"))
        d.text(((w - tw) / 2, 22), title, font=font(34, "bold"), fill=DARK)
        d.line([(60, 70), (w - 60, 70)], fill=DARK, width=3)
    return img, d


def text_size(d, s, f):
    bb = d.textbbox((0, 0), s, font=f)
    return bb[2] - bb[0], bb[3] - bb[1]


def box(d, x, y, w, h, lines, fill, outline, tcolor=None, fs=20, style="reg", radius=14):
    d.rounded_rectangle([x, y, x + w, y + h], radius=radius, fill=fill,
                        outline=outline, width=3)
    if isinstance(lines, str):
        lines = [lines]
    f = font(fs, style)
    tc = tcolor or outline
    lh = fs + 8
    total = lh * len(lines)
    cy = y + (h - total) / 2 + 4
    for ln in lines:
        tw = d.textlength(ln, font=f)
        d.text((x + (w - tw) / 2, cy), ln, font=f, fill=tc)
        cy += lh


def arrow(d, x1, y1, x2, y2, color=DARK, label=None, fs=16, lx=0, ly=-22):
    d.line([x1, y1, x2, y2], fill=color, width=3)
    import math
    ang = math.atan2(y2 - y1, x2 - x1)
    L = 14
    for a in (ang + 2.6, ang - 2.6):
        d.line([x2, y2, x2 + L * math.cos(a), y2 + L * math.sin(a)],
               fill=color, width=3)
    if label:
        f = font(fs, "bold")
        d.text(((x1 + x2) / 2 + lx, (y1 + y2) / 2 + ly), label, font=f,
               fill=color)


def note(d, x, y, s, fs=16, color=GRAY, style="reg"):
    d.text((x, y), s, font=font(fs, style), fill=color)


def title_in(d, w, s, y):
    f = font(26, "bold")
    tw = d.textlength(s, font=f)
    d.text(((w - tw) / 2, y), s, font=f, fill=DARK)


def save(img, name):
    img.save(os.path.join(OUT, name), "PNG")
    print("saved", name)


# ============================ SELENIUM ============================

def selenium_architecture():
    img, d = canvas(1200, 780, "Selenium WebDriver Architecture (Selenium 4 - W3C)")
    cx = 600
    bw, bh = 560, 86
    y = 105
    steps = [
        ("Java Test Script  (TestNG / JUnit)", LBLUE, BLUE),
        ("Selenium Client Library  (Java bindings)", LPURPLE, PURPLE),
        ("W3C WebDriver Protocol  (JSON over HTTP)", LORANGE, ORANGE),
        ("Browser Drivers  (chromedriver | geckodriver | edgedriver)", LTEAL, TEAL),
        ("Real Browsers  (Chrome | Firefox | Edge | Safari)", LGREEN, GREEN),
    ]
    labels = [
        "API calls: driver.get(), findElement()",
        "commands -> HTTP REST endpoints",
        "HTTP request / response (no conversion - W3C standard)",
        "driver translates & controls the browser",
    ]
    for i, (t, fill, out) in enumerate(steps):
        box(d, cx - bw // 2, y, bw, bh, t, fill, out, fs=22, style="bold")
        if i < len(steps) - 1:
            arrow(d, cx, y + bh, cx, y + bh + 34)
            note(d, cx + 20, y + bh + 6, labels[i], 16, GRAY)
        y += bh + 34
    # side notes
    box(d, 30, 200, 250, 150, ["Selenium Manager", "(4.6+)", "", "auto-downloads", "correct driver"], LGRAY, GRAY, fs=18)
    box(d, 920, 200, 250, 150, ["Each command =", "one HTTP call", "", "POST /session", "POST /element/click"], LGRAY, GRAY, fs=18)
    box(d, 920, 560, 250, 120, ["No middle server", "Client -> Driver", "-> Browser directly"], LGRAY, GRAY, fs=18)
    save(img, "selenium-architecture.png")


def selenium_vs_playwright():
    img, d = canvas(1260, 860, "Selenium vs Playwright - Quick Comparison")
    rows = [
        ("Feature", "Selenium 4", "Playwright"),
        ("Architecture", "W3C WebDriver (HTTP)", "WebSocket / CDP"),
        ("Auto-waiting", "Manual (WebDriverWait)", "Built-in auto-wait"),
        ("Element lookup", "findElement(By...)", "locator() / getBy*"),
        ("Shadow DOM", "getShadowRoot()", "Native piercing"),
        ("Network mock", "CDP only / external", "page.route() built-in"),
        ("Multi-tab", "window handles", "BrowserContext (isolated)"),
        ("Speed", "Slower (HTTP calls)", "Faster (persistent conn.)"),
        ("Browsers", "Chrome/FF/Edge/Safari/IE", "Chromium/FF/WebKit"),
        ("Ecosystem", "Grid, huge community", "Modern, growing"),
        ("Auth state reuse", "Manual cookies", "storageState built-in"),
    ]
    x0, y0, rh = 40, 100, 58
    cw = [300, 450, 450]
    for r, row in enumerate(rows):
        x = x0
        for c, cell in enumerate(row):
            if r == 0:
                fill, out, tc, st = BLUE if c else GRAY, DARK, "#FFFFFF", "bold"
                if c == 2:
                    fill = GREEN
                d.rounded_rectangle([x, y0, x + cw[c], y0 + rh], 8, fill=fill, outline=out, width=2)
            else:
                fill = LGRAY if r % 2 else "#FFFFFF"
                out = "#CBD5E1"
                d.rectangle([x, y0, x + cw[c], y0 + rh], fill=fill, outline=out, width=2)
                tc, st = DARK if c else "#0F172A", "reg" if c else "bold"
            f = font(19, st)
            tw = d.textlength(cell, font=f)
            d.text((x + (cw[c] - tw) / 2, y0 + rh / 2 - 12), cell, font=f, fill=tc)
            x += cw[c]
        y0 += rh
    save(img, "selenium-vs-playwright.png")


def selenium_waits():
    img, d = canvas(1240, 720, "Selenium Wait Strategies")
    cols = [
        ("Implicit Wait", BLUE, LBLUE,
         ["driver.manage().timeouts()", ".implicitlyWait(10, SECONDS)", "",
          "Global - set once", "Applies to ALL findElement", "Waits before throwing", "NoSuchElementException"]),
        ("Explicit Wait", GREEN, LGREEN,
         ["new WebDriverWait(driver,", "  Duration.ofSeconds(10))", "  .until(ExpectedConditions", "   .visibilityOf(el))", "",
          "Per-condition, per-element", "Many ExpectedConditions", "RECOMMENDED"]),
        ("Fluent Wait", PURPLE, LPURPLE,
         ["new FluentWait<>(driver)", " .withTimeout(30, SECONDS)", " .pollingEvery(5, SECONDS)", " .ignoring(NoSuchElement", "   Exception.class)", "",
          "Explicit + custom polling", "Best for dynamic apps"]),
    ]
    x = 40
    for name, out, fill, lines in cols:
        box(d, x, 100, 380, 470, [name], fill, out, fs=24, style="bold")
        d.rounded_rectangle([x + 12, 150, x + 368, 560], 8, fill="#FFFFFF", outline=out, width=2)
        yy = 162
        for ln in lines:
            d.text((x + 26, yy), ln, font=font(16, "mono"), fill=DARK)
            yy += 27
        x += 400
    box(d, 40, 600, 1180, 90,
        ["Thread.sleep(5000)  ->  AVOID! Hard-coded wait, slows suite, still flaky",
         "Interview tip: Explicit wait is the answer for 'how do you handle dynamic elements?'"],
        LRED, RED, fs=20)
    save(img, "selenium-wait-strategies.png")


def selenium_locator_pyramid():
    img, d = canvas(1000, 720, "Locator Priority Pyramid (Best -> Last Resort)")
    levels = [
        ("ID  (By.id)", GREEN, "Fastest & most stable"),
        ("name / cssSelector", TEAL, "Reliable, readable"),
        ("XPath (relative)", BLUE, "Powerful, flexible"),
        ("linkText / partialLinkText", ORANGE, "Links only"),
        ("className / tagName", RED, "Fragile - many matches"),
    ]
    cx = 430
    y = 110
    w = 220
    for i, (t, c, side) in enumerate(levels):
        box(d, cx - w // 2, y, w, 70, t, "#FFFFFF", c, fs=20, style="bold")
        note(d, cx + w // 2 + 30, y + 24, "<- " + side, 17, c)
        y += 84
        w += 130
    box(d, 60, 600, 880, 90,
        ["Priority:  id > name > css > xpath(relative) > linkText > className/tagName",
         "Rule: Prefer unique & stable attributes. Absolute XPath = never!"],
        LGRAY, GRAY, fs=19)
    save(img, "selenium-locator-priority.png")


def selenium_grid():
    img, d = canvas(1300, 820, "Selenium Grid 4 Architecture (Distributed Execution)")
    box(d, 40, 110, 240, 80, ["Test Scripts", "TestNG / Maven"], LBLUE, BLUE, fs=20, style="bold")
    arrow(d, 280, 150, 360, 150, label="HTTP")
    box(d, 360, 110, 220, 80, ["Router", "entry point"], LORANGE, ORANGE, fs=20, style="bold")
    arrow(d, 580, 150, 660, 150)
    box(d, 660, 80, 260, 70, ["Distributor", "assigns sessions"], LPURPLE, PURPLE, fs=20, style="bold")
    box(d, 660, 170, 260, 70, ["New Session Queue"], LPURPLE, PURPLE, fs=20, style="bold")
    box(d, 960, 115, 280, 70, ["Session Map", "sessionId -> node"], LTEAL, TEAL, fs=20, style="bold")
    arrow(d, 920, 110, 960, 135)
    arrow(d, 920, 205, 960, 165)
    # nodes
    arrow(d, 790, 240, 790, 320)
    for i, b in enumerate(["Chrome", "Firefox", "Edge"]):
        ny = 320 + i * 140
        box(d, 660, ny, 260, 80, [f"Node {i+1}", "slots = max sessions"], LGREEN, GREEN, fs=20, style="bold")
        box(d, 990, ny, 250, 80, [f"Browser: {b}", "chromedriver"], LGRAY, GRAY, fs=20)
        arrow(d, 920, ny + 40, 990, ny + 40)
        if i < 2:
            arrow(d, 790, ny + 80, 790, ny + 140)
    # event bus
    box(d, 60, 320, 420, 70, ["Event Bus (messages between components)"], LRED, RED, fs=19, style="bold")
    arrow(d, 300, 390, 300, 640, label="events")
    box(d, 60, 640, 520, 110,
        ["Commands:", "java -jar selenium-server.jar standalone", "java -jar selenium-server.jar hub / node",
         "docker run selenium/standalone-chrome"], LGRAY, GRAY, fs=17, style="mono")
    save(img, "selenium-grid.png")


def selenium_pom():
    img, d = canvas(1100, 720, "Page Object Model (POM) Structure")
    box(d, 60, 100, 280, 90, ["Test Layer", "LoginTest.java", "@Test methods"], LBLUE, BLUE, fs=19, style="bold")
    arrow(d, 340, 145, 420, 145)
    box(d, 420, 60, 330, 170, ["Page Objects", "", "LoginPage.java", "HomePage.java", "locators + actions"], LGREEN, GREEN, fs=19, style="bold")
    arrow(d, 750, 145, 830, 145)
    box(d, 830, 100, 230, 90, ["WebDriver", "browser"], LORANGE, ORANGE, fs=19, style="bold")
    box(d, 420, 300, 330, 70, ["PageFactory.initElements(driver,this)"], LPURPLE, PURPLE, fs=17, style="mono")
    box(d, 60, 420, 1000, 240,
        ["Why POM?", "",
         "1. Reusability  - write locator once, use in many tests",
         "2. Maintainability - UI change = fix ONE page class",
         "3. Readability - tests read like English: loginPage.login(user,pass)",
         "4. Separation - test logic vs page logic",
         "",
         "Two styles:  By locators (plain POM)  vs  @FindBy (PageFactory)"],
        LGRAY, GRAY, fs=19)
    save(img, "selenium-pom-structure.png")


def selenium_framework_er():
    img, d = canvas(1600, 1020, "Selenium Framework - Entity Relationship Diagram")
    E = [
        ("ConfigReader", 30, 90, TEAL, LTEAL, ["config.properties", "getUrl(), getBrowser()"]),
        ("DriverFactory", 300, 90, PURPLE, LPURPLE, ["ThreadLocal<WebDriver>", "getDriver() / setDriver()", "browser options"]),
        ("BaseTest", 680, 90, BLUE, LBLUE, ["driver: WebDriver", "@BeforeMethod setup()", "@AfterMethod teardown()"]),
        ("testng.xml", 1200, 90, GRAY, LGRAY, ["suite, parallel", "listeners, params"]),
        ("ExcelUtils", 30, 400, ORANGE, LORANGE, ["Apache POI", "getData(sheet)", "@DataProvider"]),
        ("LoginTest", 680, 400, GREEN, LGREEN, ["extends BaseTest", "@Test login()"]),
        ("LoginPage", 980, 400, GREEN, LGREEN, ["@FindBy locators", "login(user,pass)"]),
        ("BasePage", 1280, 400, GREEN, LGREEN, ["common actions", "wait utils"]),
        ("ITestListener", 30, 700, RED, LRED, ["onTestFailure()", "screenshot attach"]),
        ("ExtentReport", 300, 700, RED, LRED, ["ExtentSparkReporter", "log, screenshot"]),
        ("RetryAnalyzer", 570, 700, RED, LRED, ["IRetryAnalyzer", "maxRetry=2"]),
        ("WebDriver", 1280, 700, GRAY, LGRAY, ["RemoteWebDriver", "session"]),
    ]
    W, H = 240, 115
    pos = {}
    for name, x, y, c, f, attrs in E:
        pos[name] = (x, y)
        d.rounded_rectangle([x, y, x + W, y + 38], 8, fill=c, outline=c)
        tw = d.textlength(name, font=font(20, "bold"))
        d.text((x + (W - tw) / 2, y + 8), name, font=font(20, "bold"), fill="#FFFFFF")
        d.rectangle([x, y + 38, x + W, y + H], fill=f, outline=c, width=2)
        yy = y + 44
        for a in attrs:
            d.text((x + 8, yy), a, font=font(14, "mono"), fill=DARK)
            yy += 20

    def rel(a, b, lbl):
        ax, ay = pos[a]; bx, by = pos[b]
        x1, y1 = ax + W // 2, ay + H
        x2, y2 = bx + W // 2, by
        if ay > by:
            y1, y2 = ay, by + H
        arrow(d, x1, y1, x2, y2, color="#94A3B8")
        note(d, (x1 + x2) / 2 + 8, (y1 + y2) / 2 - 10, lbl, 15, GRAY, "bold")

    rel("LoginTest", "BaseTest", "extends")
    rel("BaseTest", "DriverFactory", "uses")
    rel("DriverFactory", "ConfigReader", "reads")
    rel("LoginTest", "LoginPage", "uses")
    rel("LoginPage", "BasePage", "extends")
    rel("LoginTest", "ExcelUtils", "DataProvider")
    rel("BaseTest", "ITestListener", "@Listeners")
    rel("ITestListener", "ExtentReport", "writes")
    rel("BaseTest", "RetryAnalyzer", "retryAnalyzer")
    rel("BasePage", "WebDriver", "drives")
    rel("testng.xml", "BaseTest", "configures")
    save(img, "selenium-framework-er.png")


def selenium_cheatsheet():
    img, d = canvas(1500, 1050, "Selenium with Java - Interview Cheatsheet")
    cards = [
        ("SETUP", BLUE, LBLUE,
         ["WebDriver d = new ChromeDriver();", "d.manage().window().maximize();",
          "d.manage().timeouts().implicitlyWait(", "    Duration.ofSeconds(10));",
          "d.get(\"https://app.com\");"]),
        ("LOCATORS", GREEN, LGREEN,
         ["d.findElement(By.id(\"x\"));", "By.name, By.className, By.tagName",
          "By.linkText, By.partialLinkText", "By.cssSelector(\"input#id\")",
          "By.xpath(\"//input[@id='x']\")", "List<WebElement> = findElements()"]),
        ("ACTIONS", ORANGE, LORANGE,
         ["el.click(); el.sendKeys(\"txt\");", "el.clear(); el.getText();",
          "el.getAttribute(\"value\");", "el.isDisplayed/isEnabled/isSelected",
          "new Actions(d).doubleClick(el)", "  .dragAndDrop(a,b).perform();"]),
        ("WAITS", PURPLE, LPURPLE,
         ["WebDriverWait w =", "  new WebDriverWait(d, ofSeconds(10));",
          "w.until(visibilityOfElementLocated(", "  By.id(\"x\"))).click();",
          "FluentWait: +pollingEvery()", "  +ignoring(Exception.class)"]),
        ("SWITCH", TEAL, LTEAL,
         ["d.switchTo().alert().accept();", "d.switchTo().frame(0/name/el);",
          "d.switchTo().defaultContent();", "d.switchTo().window(handle);",
          "d.getWindowHandles();", "d.switchTo().newWindow(TAB); // S4"]),
        ("SELECT / JS / COOKIES", RED, LRED,
         ["new Select(el).selectByVisibleText", "  (\"India\"); // byValue/byIndex",
          "((JavascriptExecutor)d).executeScript", "  (\"arguments[0].click()\",el);",
          "d.manage().addCookie(c);", "d.manage().getCookies();"]),
    ]
    cw, ch = 470, 400
    for i, (t, c, f, lines) in enumerate(cards):
        x = 40 + (i % 3) * (cw + 20)
        y = 100 + (i // 3) * (ch + 20)
        d.rounded_rectangle([x, y, x + cw, y + ch], 12, fill=f, outline=c, width=3)
        tw = d.textlength(t, font=font(22, "bold"))
        d.text((x + (cw - tw) / 2, y + 14), t, font=font(22, "bold"), fill=c)
        yy = y + 55
        for ln in lines:
            d.text((x + 20, yy), ln, font=font(17, "mono"), fill=DARK)
            yy += 40
    save(img, "selenium-cheatsheet.png")


# ============================ REST ASSURED ============================

def ra_architecture():
    img, d = canvas(1250, 760, "RestAssured Architecture - How a Test Flows")
    cx, bw, bh = 520, 620, 84
    y = 105
    steps = [
        ("TestNG / JUnit Test Method", LBLUE, BLUE),
        ("RestAssured Fluent DSL  (given / when / then)", LPURPLE, PURPLE),
        ("RequestSpecification + Filters + Logging", LORANGE, ORANGE),
        ("Apache HttpClient  (real HTTP call over TCP)", LTEAL, TEAL),
        ("REST API Server  ->  HTTP Response", LGREEN, GREEN),
        ("ValidatableResponse  (Hamcrest matchers)", LRED, RED),
    ]
    labels = ["build request", "serialize body, attach headers/auth",
              "opens connection, sends request", "returns status/headers/body",
              "deserialise + assert"]
    for i, (t, fill, out) in enumerate(steps):
        box(d, cx - bw // 2, y, bw, bh, t, fill, out, fs=21, style="bold")
        if i < len(steps) - 1:
            arrow(d, cx, y + bh, cx, y + bh + 30)
            note(d, cx + 20, y + bh + 4, labels[i], 15, GRAY)
        y += bh + 30
    box(d, 20, 240, 190, 160, ["given()", "", "spec, headers,", "params, auth,", "body, cookies"], LGRAY, GRAY, fs=17)
    box(d, 1040, 240, 190, 160, ["when()", "", "get/post/", "put/patch/", "delete(url)"], LGRAY, GRAY, fs=17)
    box(d, 1040, 500, 190, 160, ["then()", "", "statusCode,", "body(jsonpath,", "matcher), extract"], LGRAY, GRAY, fs=17)
    save(img, "restassured-architecture.png")


def ra_gwt():
    img, d = canvas(1400, 560, "given() / when() / then() - BDD Style Flow")
    data = [
        ("GIVEN  (arrange request)", BLUE, LBLUE,
         ["baseURI / basePath / port", "header(), contentType()", "queryParam(), pathParam()",
          "auth(), oauth2(), cookies", "body(pojo / map / string)", "multiPart(file)"]),
        ("WHEN  (fire HTTP call)", ORANGE, LORANGE,
         [".get(\"/users\")", ".post(\"/users\")", ".put(\"/users/{id}\")",
          ".patch(\"/users/{id}\")", ".delete(\"/users/{id}\")", "", "-> returns Response"]),
        ("THEN  (validate)", GREEN, LGREEN,
         [".statusCode(200)", ".statusLine(\"HTTP/1.1 200\")", ".contentType(JSON)",
          ".body(\"name\", equalTo(\"x\"))", ".time(lessThan(2000L))", ".extract().response()"]),
    ]
    x = 40
    for t, c, f, lines in data:
        d.rounded_rectangle([x, 100, x + 430, 500], 14, fill=f, outline=c, width=3)
        tw = d.textlength(t, font=font(24, "bold"))
        d.text((x + (430 - tw) / 2, 120), t, font=font(24, "bold"), fill=c)
        yy = 170
        for ln in lines:
            d.text((x + 30, yy), ln, font=font(19, "mono"), fill=DARK)
            yy += 46
        if x < 900:
            arrow(d, x + 430, 300, x + 470, 300)
        x += 470
    save(img, "restassured-given-when-then.png")


def http_methods():
    img, d = canvas(1250, 640, "HTTP Methods - CRUD, Safe & Idempotent (Interview Favorite)")
    rows = [
        ("Method", "CRUD", "Safe?", "Idempotent?", "Body?", "Typical Use"),
        ("GET", "Read", "YES", "YES", "No", "Fetch resource(s)"),
        ("POST", "Create", "No", "No", "Yes", "Create, non-idempotent ops"),
        ("PUT", "Update", "No", "YES", "Yes", "Full replace (upsert)"),
        ("PATCH", "Update", "No", "No*", "Yes", "Partial update"),
        ("DELETE", "Delete", "No", "YES", "Rare", "Remove resource"),
        ("HEAD", "-", "YES", "YES", "No", "Headers only (no body)"),
        ("OPTIONS", "-", "YES", "YES", "No", "Allowed methods / CORS"),
    ]
    x0, y0, rh = 40, 105, 52
    cw = [180, 170, 130, 180, 120, 420]
    for r, row in enumerate(rows):
        x = x0
        for c, cell in enumerate(row):
            if r == 0:
                d.rounded_rectangle([x, y0, x + cw[c], y0 + rh], 6, fill=DARK, outline=DARK)
                tc, st = "#FFFFFF", "bold"
            else:
                fill = LGRAY if r % 2 else "#FFFFFF"
                if cell == "YES":
                    fill = LGREEN
                elif cell == "No":
                    fill = LRED
                d.rectangle([x, y0, x + cw[c], y0 + rh], fill=fill, outline="#CBD5E1", width=2)
                tc, st = DARK, "reg" if c else "bold"
            f = font(18, st)
            tw = d.textlength(cell, font=f)
            d.text((x + (cw[c] - tw) / 2, y0 + rh / 2 - 11), cell, font=f, fill=tc)
            x += cw[c]
        y0 += rh
    note(d, 40, y0 + 20, "Safe = no side-effects on server.  Idempotent = same result if repeated.  PATCH *usually not idempotent.", 17, GRAY)
    save(img, "http-methods-crud.png")


def ra_auth():
    img, d = canvas(1350, 760, "Authentication Types in RestAssured")
    cards = [
        ("Basic Auth", BLUE, LBLUE, [".auth().basic(user, pass)", "Base64 user:pass header", "sent when challenged"]),
        ("Preemptive Basic", TEAL, LTEAL, [".auth().preemptive()", ".basic(user, pass)", "sends creds immediately"]),
        ("Digest Auth", GRAY, LGRAY, [".auth().digest(u,p)", "server nonce + hash", "safer than basic"]),
        ("Bearer / JWT", PURPLE, LPURPLE, [".auth().oauth2(token)", "header: Bearer <jwt>", "most common in real apps"]),
        ("OAuth 1.0a", ORANGE, LORANGE, [".auth().oauth(ck, cs,", "  tk, ts)", "signed request (old Twitter)"]),
        ("OAuth 2.0 Flow", GREEN, LGREEN, ["1. POST /token (creds)", "2. get access_token", "3. reuse in oauth2(token)"]),
        ("API Key", RED, LRED, [".header(\"X-API-Key\", key)", "or .queryParam(\"key\",k)", "simple, per-client key"]),
        ("Form / Session", PINK, LPINK, ["POST login -> sessionId", "cookie/JSESSIONID reuse", ".sessionId(id)"]),
    ]
    cw, ch = 310, 290
    for i, (t, c, f, lines) in enumerate(cards):
        x = 40 + (i % 4) * (cw + 18)
        y = 105 + (i // 4) * (ch + 20)
        d.rounded_rectangle([x, y, x + cw, y + ch], 12, fill=f, outline=c, width=3)
        tw = d.textlength(t, font=font(20, "bold"))
        d.text((x + (cw - tw) / 2, y + 12), t, font=font(20, "bold"), fill=c)
        yy = y + 55
        for ln in lines:
            d.text((x + 16, yy), ln, font=font(15, "mono"), fill=DARK)
            yy += 40
    save(img, "restassured-auth-types.png")


def ser_deser():
    img, d = canvas(1250, 620, "Serialization & Deserialization (POJO <-> JSON)")
    box(d, 60, 130, 300, 120, ["Java POJO", "User{ name, age,", "  Address addr }"], LBLUE, BLUE, fs=20, style="bold")
    arrow(d, 360, 190, 500, 190, label="SERIALIZE", lx=-40, ly=-32)
    box(d, 500, 130, 300, 120, ["Jackson / Gson", "(ObjectMapper)", "on classpath = auto"], LPURPLE, PURPLE, fs=20, style="bold")
    arrow(d, 800, 190, 940, 190, label="JSON body", lx=-35, ly=-32)
    box(d, 940, 130, 270, 120, ["HTTP Request", "{ \"name\":..,", "  \"addr\":{..} }"], LORANGE, ORANGE, fs=20, style="bold")
    box(d, 940, 380, 270, 120, ["HTTP Response", "JSON body"], LORANGE, ORANGE, fs=20, style="bold")
    arrow(d, 940, 440, 800, 440, label="raw JSON", lx=-30, ly=-32)
    box(d, 500, 380, 300, 120, ["DESERIALIZE", "response.as(User.class)", "or new TypeRef<>(){}"], LPURPLE, PURPLE, fs=20, style="bold")
    arrow(d, 500, 440, 360, 440, label="mapped fields", lx=-45, ly=-32)
    box(d, 60, 380, 300, 120, ["Java Object", "user.getName()", "assertEquals..."], LBLUE, BLUE, fs=20, style="bold")
    note(d, 60, 560, "Jackson2/Gson/Johnzon must be on classpath -> RestAssured picks automatically. @JsonProperty/@JsonIgnoreProperties for mapping.", 17, GRAY)
    save(img, "restassured-serialization.png")


def status_codes():
    img, d = canvas(1300, 700, "HTTP Status Codes - Must Remember")
    groups = [
        ("1xx  Informational", GRAY, LGRAY, ["100 Continue", "101 Switching Protocols"]),
        ("2xx  Success", GREEN, LGREEN, ["200 OK", "201 Created", "202 Accepted", "204 No Content"]),
        ("3xx  Redirection", BLUE, LBLUE, ["301 Moved Permanently", "302 Found", "304 Not Modified"]),
        ("4xx  Client Error", ORANGE, LORANGE, ["400 Bad Request", "401 Unauthorized", "403 Forbidden",
                                              "404 Not Found", "405 Method Not Allowed", "409 Conflict",
                                              "415 Unsupported Media", "422 Unprocessable", "429 Too Many Req"]),
        ("5xx  Server Error", RED, LRED, ["500 Internal Server Err", "502 Bad Gateway",
                                         "503 Service Unavailable", "504 Gateway Timeout"]),
    ]
    cw, ch = 240, 470
    for i, (t, c, f, lines) in enumerate(groups):
        x = 30 + i * (cw + 14)
        d.rounded_rectangle([x, 110, x + cw, 110 + ch], 12, fill=f, outline=c, width=3)
        tw = d.textlength(t, font=font(18, "bold"))
        d.text((x + (cw - tw) / 2, 126), t, font=font(18, "bold"), fill=c)
        yy = 165
        for ln in lines:
            d.text((x + 14, yy), ln, font=font(15, "mono"), fill=DARK)
            yy += 42
    note(d, 30, 610, "Hot Qs:  401 vs 403 (authN vs authZ)  |  400 vs 422 (syntax vs semantics)  |  201 vs 200 (created)  |  204 = success no body", 17, GRAY)
    save(img, "http-status-codes.png")


def ra_framework_er():
    img, d = canvas(1600, 1020, "RestAssured API Framework - Entity Relationship Diagram")
    E = [
        ("ConfigReader", 30, 90, TEAL, LTEAL, ["env.properties", "getBaseUrl()", "getToken()"]),
        ("SpecFactory", 300, 90, PURPLE, LPURPLE, ["RequestSpecBuilder", "ResponseSpecBuilder", "baseURI, log"]),
        ("Endpoints", 680, 90, BLUE, LBLUE, ["Routes.java", "GET /users", "POST /users/{id}"]),
        ("testng.xml", 1200, 90, GRAY, LGRAY, ["suite, groups", "listeners"]),
        ("UserPayload (POJO)", 30, 400, GREEN, LGREEN, ["name, email, addr", "@JsonProperty", "builder()"]),
        ("UserApi", 420, 400, ORANGE, LORANGE, ["createUser(p)", "getUser(id)", "returns Response"]),
        ("UserTest", 820, 400, GREEN, LGREEN, ["@Test CRUD flow", "assert on Response"]),
        ("JsonUtils", 30, 700, PURPLE, LPURPLE, ["jsonPath extraction", "toJson/fromJson"]),
        ("ExtentReport", 300, 700, RED, LRED, ["request/resp logs", "attach payload"]),
        ("Retry/Filter", 570, 700, RED, LRED, ["OrderedFilter", "log().ifError()"]),
        ("AuthManager", 840, 700, ORANGE, LORANGE, ["token fetch+cache", "oauth2(token)"]),
        ("RestAssured", 1200, 700, GRAY, LGRAY, ["given/when/then", "ValidatableResponse"]),
    ]
    W, H = 240, 110
    pos = {}
    for name, x, y, c, f, attrs in E:
        pos[name] = (x, y)
        d.rounded_rectangle([x, y, x + W, y + 38], 8, fill=c, outline=c)
        tw = d.textlength(name, font=font(19, "bold"))
        d.text((x + (W - tw) / 2, y + 8), name, font=font(19, "bold"), fill="#FFFFFF")
        d.rectangle([x, y + 38, x + W, y + H], fill=f, outline=c, width=2)
        yy = y + 44
        for a in attrs:
            d.text((x + 8, yy), a, font=font(14, "mono"), fill=DARK)
            yy += 19

    def rel(a, b, lbl):
        ax, ay = pos[a]; bx, by = pos[b]
        x1, y1 = ax + W // 2, ay + H
        x2, y2 = bx + W // 2, by
        if ay > by:
            y1, y2 = ay, by + H
        arrow(d, x1, y1, x2, y2, color=GRAY)
        note(d, (x1 + x2) / 2 + 8, (y1 + y2) / 2 - 10, lbl, 14, GRAY, "bold")

    rel("UserApi", "Endpoints", "uses")
    rel("UserApi", "SpecFactory", "uses")
    rel("SpecFactory", "ConfigReader", "reads")
    rel("UserApi", "UserPayload (POJO)", "serializes")
    rel("UserTest", "UserApi", "calls")
    rel("UserTest", "JsonUtils", "extracts")
    rel("SpecFactory", "Retry/Filter", "adds")
    rel("SpecFactory", "AuthManager", "auth")
    rel("UserApi", "RestAssured", "given()")
    rel("UserTest", "ExtentReport", "logs")
    rel("testng.xml", "UserTest", "configures")
    save(img, "restassured-framework-er.png")


def ra_cheatsheet():
    img, d = canvas(1500, 1050, "RestAssured with Java - Interview Cheatsheet")
    cards = [
        ("REQUEST", BLUE, LBLUE,
         ["given().baseUri(url)", " .header(\"K\",\"V\").body(pojo)", " .queryParam(\"page\",1)",
          " .pathParam(\"id\",5)", " .auth().oauth2(token)", ".when().get(\"/users/{id}\")"]),
        ("VALIDATE", GREEN, LGREEN,
         [".then().statusCode(200)", " .contentType(\"application/json\")",
          " .body(\"name\",equalTo(\"x\"))", " .body(\"items.size()\",is(3))",
          " .body(\"id\",notNullValue())", " .time(lessThan(2000L))"]),
        ("EXTRACT", ORANGE, LORANGE,
         ["Response r = given()...get(u);", "int id = r.path(\"data.id\");",
          "String j = r.asString();", "JsonPath jp = r.jsonPath();",
          "List<String> n = jp.getList(", "  \"data.name\");",
          "User u = r.as(User.class);"]),
        ("SPECS", PURPLE, LPURPLE,
         ["RequestSpecification req = new", " RequestSpecBuilder()", " .setBaseUri(u).setContentType",
          " (JSON).log(ALL).build();", "ResponseSpecification res = new", " ResponseSpecBuilder()",
          " .expectStatusCode(200).build();"]),
        ("AUTH", TEAL, LTEAL,
         [".auth().basic(u,p)", ".auth().preemptive().basic(u,p)", ".auth().digest(u,p)",
          ".auth().oauth2(token)", ".header(\"X-API-Key\",key)", ".sessionId(id)"]),
        ("FILES / SCHEMA", RED, LRED,
         [".multiPart(new File(\"a.png\"))", "byte[] f = get(u).asByteArray();",
          "matchesJsonSchemaInClasspath", "  (\"user-schema.json\")",
          ".filter(new RequestLogging", "  Filter()) | .log().all()"]),
    ]
    cw, ch = 470, 400
    for i, (t, c, f, lines) in enumerate(cards):
        x = 40 + (i % 3) * (cw + 20)
        y = 100 + (i // 3) * (ch + 20)
        d.rounded_rectangle([x, y, x + cw, y + ch], 12, fill=f, outline=c, width=3)
        tw = d.textlength(t, font=font(22, "bold"))
        d.text((x + (cw - tw) / 2, y + 14), t, font=font(22, "bold"), fill=c)
        yy = y + 55
        for ln in lines:
            d.text((x + 20, yy), ln, font=font(17, "mono"), fill=DARK)
            yy += 40
    save(img, "restassured-cheatsheet.png")


if __name__ == "__main__":
    selenium_architecture()
    selenium_vs_playwright()
    selenium_waits()
    selenium_locator_pyramid()
    selenium_grid()
    selenium_pom()
    selenium_framework_er()
    selenium_cheatsheet()
    ra_architecture()
    ra_gwt()
    http_methods()
    ra_auth()
    ser_deser()
    status_codes()
    ra_framework_er()
    ra_cheatsheet()
    print("ALL DONE")
