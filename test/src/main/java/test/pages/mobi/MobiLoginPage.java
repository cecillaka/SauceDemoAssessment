package test.pages.mobi;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import test.utils.MobiElementInteractions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MobiLoginPage {

	private MobiElementInteractions mobiElementInteractions = new MobiElementInteractions();
	public MobiLoginPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id, 'login_username')]")
	private WebElement userNameInputBox;
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id, 'login_password')]")
	private WebElement passwordInputBox;
	
	@AndroidFindBy(xpath = "//*[contains(@resource-id, 'login_sign_in_button')]")
	private WebElement signInButton;
	
	public void login(String password ,String username) {
		mobiElementInteractions.sendKeysMobiElement(userNameInputBox,username);
		mobiElementInteractions.sendKeysMobiElement(passwordInputBox,password);
		mobiElementInteractions.clickMobiElement(signInButton, "signInButton");
		
		
	}

}
