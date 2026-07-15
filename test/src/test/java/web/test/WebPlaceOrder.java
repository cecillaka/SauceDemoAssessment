package web.test;
import java.io.IOException;
import java.net.MalformedURLException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.test.BaseTestWeb;
import test.pages.web.WebCartPage;
import test.pages.web.WebCheckOutPage;
import test.pages.web.WebLandingPage;
import test.pages.web.WebLoginPage;
import test.pages.web.WebSideMenu;
import test.utils.ExcelHandler;
import test.utils.Log4jLogger;



public class WebPlaceOrder extends BaseTestWeb {
	private WebLoginPage webLoginPage;
	private WebLandingPage webLandingPage;
	private WebCartPage webCartPage;
	private WebCheckOutPage webCheckOutPage;
	private WebSideMenu webSideMenu;
	private ExcelHandler excelHandler;
	private static final String FILE_PATH = "../test/datasheet/dataSheet.xlsx";
	private static final String SHEET_NAME_LogIn = "login";
	private static final String SHEET_NAME_Items = "items";
	private static final String SHEET_NAME_CheckOut = "checkOutInfo";

	@BeforeClass
	@org.testng.annotations.Parameters({"browserName", "browserURL","severURL"})
	public void startDriver(String browserName,String browserURL ,String severURL ) throws MalformedURLException {	

		try {
			Log4jLogger.loggerInfo("Initializing Objects...");
			setBrowseAndServerName(browserName,severURL);
			initializeBrowserDriver();
			getDriver().get(browserURL); 
			getDriver().manage().window().maximize();
			webLoginPage = new WebLoginPage(getDriver());
			webLandingPage =new WebLandingPage(getDriver());
			webCartPage = new WebCartPage(getDriver());
			webCheckOutPage =new WebCheckOutPage(getDriver());
			webSideMenu =new WebSideMenu(getDriver());
			excelHandler = new ExcelHandler(FILE_PATH);
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
		}

	}

	@Test (priority=1, enabled=true)
	public void login() throws IOException {
		String email=excelHandler.readExcel(SHEET_NAME_LogIn, "username", 1).toString();
		String password=excelHandler.readExcel(SHEET_NAME_LogIn, "password", 1).toString();
		webLoginPage.login(email, password);
		webLoginPage.confirmLoggeinSuccessfully(10);

	}

	@Test (priority=2, enabled=true)
	public void addItemsToCart() throws IOException {
		int itemsCount=0;
		for(int x=1;x<excelHandler.getNumberOfRows(SHEET_NAME_Items);x++) {
			String items=excelHandler.readExcel(SHEET_NAME_Items, "items", x).toString();

			webLandingPage.addItemsToCart(items);
			itemsCount=itemsCount+1;
		}
		webLandingPage.checkItemsCount(Integer.toString(itemsCount));
		
		
	}


	@Test (priority=3, enabled=true)
	public void viewAndVeryfiyCartItems() throws IOException {

		webCartPage.clickCartButton();
		VeryfyCartItems();

	}

	@Test (priority=4, enabled=true)
	public void checkOutProcess() throws IOException {
		String firstname=excelHandler.readExcel(SHEET_NAME_CheckOut, "firstName", 1).toString();
		String lastname=excelHandler.readExcel(SHEET_NAME_CheckOut, "last name", 1).toString();
		String zipCode=excelHandler.readExcel(SHEET_NAME_CheckOut, "postal code", 1).toString();
		webCheckOutPage.cliCkcheckOutButton();
		webCheckOutPage.fillCheckOutForm(firstname, lastname, zipCode);
		VeryfyCartItems();		
		String total=excelHandler.readExcel(SHEET_NAME_Items, "totalPrice", 1).toString();
		webCheckOutPage.getTotal("$"+total);


	}
	
	@Test (priority=5, enabled=true)
	public void finishCheckOut() throws IOException {
		
		webCheckOutPage.clickFinishButton();
		webCheckOutPage.compareSuccessMessage("Thank you for your order!");

	}
	
	@Test (priority=6, enabled=true)
	public void logOut() throws IOException {
		String logOuturl=getDriver().getCurrentUrl();
		webSideMenu.clickSideMenu();
		webSideMenu.clickLogOutButton();
		webSideMenu.checkLogInFormIsDisplayed(0);


	}




	@AfterClass
	public void closeSession() throws InterruptedException {
		Thread.sleep(3000);
		tearDown();

	}


	//helper nmethod

	public void VeryfyCartItems() throws IOException
	{
		for(int x=1;x<excelHandler.getNumberOfRows(SHEET_NAME_Items);x++) {
			String itemsName=excelHandler.readExcel(SHEET_NAME_Items, "items", x).toString();
			String itemsPrice=excelHandler.readExcel(SHEET_NAME_Items, "price", x).toString();

			if(x==1) {
				webCartPage.confirmCorrectPriceAndNameCart1(itemsPrice,itemsName);
			}
			else if(x==2)
				webCartPage.confirmCorrectPriceAndNameCart2(itemsPrice,itemsName);
		}
	}


}
