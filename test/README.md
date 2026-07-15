# Inspired Testing Automation Framework

This project contains Selenium web automation tests using Java, Maven, Selenium Grid, and TestNG.

## Prerequisites

Install the following before running the tests:

- Java JDK 17 or newer
- Apache Maven
- Google Chrome
- Eclipse IDE
- TestNG plugin for Eclipse

## Verify Required Installations

Open PowerShell or Command Prompt and run:

```powershell
java -version
```

```powershell
javac -version
```

```powershell
mvn -version
```

If any command is not recognized, install the missing tool and make sure it is added to the system `PATH`.

## Project Location

After cloning the repository, open the project root folder. In this README, the project root means the folder that contains:

```text
pom.xml
launchSeleniumGrid.bat
LaunchTestNg.xml
```

## Run the Test Step by Step

1. Open the project root folder in File Explorer.

2. Start Selenium Grid by double-clicking:

   ```text
   launchSeleniumGrid.bat
   ```

3. Leave the Selenium Grid window open.

   The batch file runs:

   ```powershell
   java -jar selenium-server-4.27.0.jar standalone
   ```

4. Confirm Selenium Grid is running by opening this URL in Chrome:

   ```text
   http://127.0.0.1:4444
   ```

5. Open Eclipse.

6. Import or open the project root folder as a Maven project.

7. In Eclipse, locate:

   ```text
   LaunchTestNg.xml
   ```

8. Right-click `LaunchTestNg.xml`.

9. Select:

   ```text
   Run As > TestNG Suite
   ```

10. The suite will launch Chrome through Selenium Grid and run the configured scenarios against:

    ```text
    https://www.saucedemo.com/
    ```

## Test Scenarios

`LaunchTestNg.xml` currently runs the following scenarios in order:

1. `web.test.NegativeLoginScenario`
   - Enters invalid SauceDemo credentials.
   - Verifies the login error message is displayed.

2. `web.test.WebPlaceOrder`
   - Logs in with valid credentials from the test data sheet.
   - Adds products to the cart.
   - Verifies cart items.
   - Completes checkout.
   - Logs out.

## Useful Maven Commands

Run these commands from the project root folder.

Compile the project:

```powershell
mvn compile
```

Package/install the project while skipping test compilation:

```powershell
mvn install '-Dmaven.test.skip=true'
```

## Test Configuration

The TestNG suite is configured in:

```text
LaunchTestNg.xml
```

Current web test settings:

```text
browserName = chrome
browserURL = https://www.saucedemo.com/
severURL = http://127.0.0.1:4444
```

Configured test classes:

```text
web.test.NegativeLoginScenario
web.test.WebPlaceOrder
```

## Access the Extent Report

After the test run finishes, open the `extent-reports` folder:

```text
extent-reports
```

Open the generated HTML report in Chrome. Common report files are:

```text
Negative Login Scenario-report.html
Chrome Browser Testing-report.html
```

Relative paths:

```text
extent-reports/Negative Login Scenario-report.html
extent-reports/Chrome Browser Testing-report.html
```

## Access the Logs

Execution logs are saved in:

```text
logs
```

The latest log file is usually:

```text
app.log
```

## Notes

- Keep Selenium Grid running while the tests execute.
- Chrome password manager popups are disabled in `BaseTestWeb.java`.
- If the browser does not launch, confirm Chrome is installed and Selenium Grid is running at `http://127.0.0.1:4444`.
