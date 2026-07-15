package test.pages.web;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.utils.WebElementInteractions;

public class WebSideMenu {
	WebDriver browserDriver;
	private WebElementInteractions webElementInteractions = new WebElementInteractions();

	public WebSideMenu(WebDriver driver){
		browserDriver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//button[@id='react-burger-menu-btn']")
	WebElement menu;

	@FindBy(xpath="//a[@id='logout_sidebar_link']")
	WebElement logOutButton;
	
	@FindBy(xpath = "//div[@class='login_wrapper-inner']")
	WebElement loginForm; 
	
 
	
	public void clickSideMenu() {
		webElementInteractions.clickWebElement(menu, "menu");	
	}
	
	public void clickLogOutButton() {
		webElementInteractions.clickWebElement(logOutButton, "logOutButton");	
	}
	
	public void checkLoggedOutUrls(String CapturedUrl, String ActualURL) {
		assertEquals(CapturedUrl, ActualURL);
		
	}
	
	public void checkLogInFormIsDisplayed(int timeInsec) throws IOException {
		webElementInteractions.waitForElementToDisplay(loginForm, "loginForm", timeInsec);
		
	}
	
	
	
	

}