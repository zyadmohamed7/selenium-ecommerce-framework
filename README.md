# Selenium Automation Framework

A modern, robust, and highly parallelized test automation framework built with **Java 25**, **Selenium WebDriver**, **TestNG**, and **Allure Reports**.

---

## 🚀 Key Features

* **Page Object Model (POM)**: Implemented with fluent interface design (chainable page transitions) to keep tests readable, maintainable, and type-safe.
* **Thread-Safe Parallel Execution**: Supports method-level parallel execution (`thread-count="3"`) utilizing Java `ThreadLocal` variables for `WebDriver` and action helpers.
* **Smart Soft Assertions**: Automatically executes and asserts all soft assertions (`assertAll()`) after each test method completes via TestNG listeners, removing boilerplate code.
* **Automatic Failure Screenshots**: Automatically captures a screenshot on any test failure and attaches it directly to the Allure report and local storage.
* **Automatic Flaky Test Retry**: Automatically retries any failed test method up to 2 times (`RetryAnalyzer` + `RetryTransformer`) without needing manual annotation.
* **Rich Allure Reports**: Fully integrated with Allure annotations (`@Epic`, `@Feature`, `@Story`, `@Severity`, `@Description`), assertion logging, and environment dashboards.
* **Data-Driven Testing**: Integrates JSON-based data readers to feed TestNG DataProviders.

---

## 📁 Project Structure

```
├── src
│   ├── main/java/com/seleniumframework
│   │   ├── drivers         # WebDriver setup, factory, and thread safety configurations
│   │   ├── listeners       # TestNG Listeners for soft assertions, retries, and screenshot logging
│   │   ├── media           # Media handling (local & Allure screenshot attachments)
│   │   ├── pages           # Fluent Page Objects representing UI screens
│   │   ├── utils           # Actions helpers, configuration reader, wait managers, and logs
│   │   └── validations     # Soft assertions (Validations) and Hard assertions (Verifications)
│   │
│   └── test
│       ├── java/com/seleniumframework
│       │   ├── BaseTest.java     # Setup/Teardown with ThreadLocal initializations
│       │   └── tests             # Test suites (E2ETests, VoidComparisonTest)
│       └── resources
│           └── test-data         # JSON test data inputs
│
├── testng.xml       # Main TestNG XML suite file configuring parallel run & listeners
└── pom.xml          # Maven dependencies, compiler configs, and plugins (Allure & Surefire)
```

---

## 🛠️ Prerequisites

* **Java JDK 21+** (Java 25 is recommended)
* **Apache Maven 3.9+**
* Chrome or your preferred browser installed (configured via [config.properties](src/main/resources/config.properties) or config reader)

---

## 💻 How to Run the Tests

### Run all tests in parallel
To clean the target directory, compile, and run the test suite defined in `testng.xml` in parallel:
```bash
mvn clean test
```

### Run specific tests
To run a specific test method or class:
```bash
mvn test -Dtest=E2ETests#testUserRegistrationAndDeletion
```

---

## 📊 Allure Reporting

Allure generates interactive, clean visual dashboards containing execution histories, step timelines, categories, and browser screenshots.

### Step 1: Execute Tests
Run the tests using the command above. Allure results will populate inside `target/allure-results/`.

### Step 2: Serve the Report
To compile the raw results and launch a local web server displaying the report:
```bash
mvn allure:serve
```
*This command compiles the results into a temporary directory and opens the report automatically in your default browser.*

### Step 3: Generate a Static HTML Report (Optional)
If you wish to build static HTML files that can be stored or shared:
```bash
mvn allure:report
```
The report is created in `target/site/allure-maven-plugin/`.

---

## ⚙️ Advanced Configurations

### Adjusting Thread Count
Open [testng.xml](testng.xml) and update the `thread-count` attribute to speed up or slow down execution:
```xml
<suite name="Automation Exercise Test Suite" verbose="1" parallel="methods" thread-count="3">
```

### Changing Retry Count
Open [RetryAnalyzer.java](src/main/java/com/seleniumframework/listeners/RetryAnalyzer.java) and change the maximum retry limit:
```java
private static final int MAX_RETRY_COUNT = 2; // Change this to alter retries
```
