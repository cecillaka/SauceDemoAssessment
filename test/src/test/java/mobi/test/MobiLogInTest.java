package mobi.test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.test.BaseTestMobi;
import test.pages.mobi.*;
import test.utils.ExcelHandler;
import test.utils.Log4jLogger;

public class MobiLogInTest extends BaseTestMobi {

	private MobiLoginPage loginPage;
	private ExcelHandler excelHandler;
	private static final String FILE_PATH = "../test/datasheet/dataSheet.xlsx";
	private static final String SHEET_NAME = "Sheet1";


	@BeforeClass
	@org.testng.annotations.Parameters({ "deviceName", "appiumPort" })
	public void startDriver(String deviceName, String appiumPort) throws MalformedURLException {
		
		try {
			Log4jLogger.loggerInfo("Initializing Objects...");
			initializeAndroidDriver(deviceName, appiumPort);
			loginPage = new MobiLoginPage(returnAndroidDriver());
			excelHandler = new ExcelHandler(FILE_PATH);
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			
		}
		
		
	}

	@Test(priority = 0, enabled = true)
	public void Login() throws Exception {

		String username = excelHandler.readExcel(SHEET_NAME, "username", 1).toString();
		String password = excelHandler.readExcel(SHEET_NAME, "password", 1).toString();
		loginPage.login(password, username);


	}

	@Test(priority = 1, dependsOnMethods = { "Login" }, enabled = true)
	public void Login2() throws InterruptedException, Exception, IOException {

	}

	@AfterClass
	public void closeSession() throws InterruptedException {

		androidtearDown();

	}


	public void ImplicitWait(int timeDurationInSec) {
		returnAndroidDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeDurationInSec));
	}

}
