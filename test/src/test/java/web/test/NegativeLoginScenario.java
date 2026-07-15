package web.test;

import java.net.MalformedURLException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.test.BaseTestWeb;
import test.pages.web.WebLoginPage;
import test.utils.Log4jLogger;

public class NegativeLoginScenario extends BaseTestWeb {
	private WebLoginPage webLoginPage;
	private static final String INVALID_USERNAME = "invalid_user";
	private static final String INVALID_PASSWORD = "invalid_password";
	private static final String INVALID_LOGIN_ERROR = "Epic sadface: Username and password do not match any user in this service";

	@BeforeClass
	@org.testng.annotations.Parameters({"browserName", "browserURL", "severURL"})
	public void startDriver(String browserName, String browserURL, String severURL) throws MalformedURLException {
		try {
			Log4jLogger.loggerInfo("Initializing Negative Login Scenario...");
			setBrowseAndServerName(browserName, severURL);
			initializeBrowserDriver();
			getDriver().get(browserURL);
			getDriver().manage().window().maximize();
			webLoginPage = new WebLoginPage(getDriver());
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
		}
	}

	@Test(priority = 1, enabled = true)
	public void invalidLogin() {
		webLoginPage.login(INVALID_USERNAME, INVALID_PASSWORD);
		webLoginPage.confirmInvalidLoginError(INVALID_LOGIN_ERROR);
	}

	@AfterClass
	public void closeSession() throws InterruptedException {
		Thread.sleep(3000);
		tearDown();
	}
}
