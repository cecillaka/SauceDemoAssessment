package test.pages.web;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.utils.WebElementInteractions;

public class WebCheckOutPage {
	WebDriver browserDriver;
	private WebElementInteractions webElementInteractions = new WebElementInteractions();

	public WebCheckOutPage(WebDriver driver){
		browserDriver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//button[@id='checkout']")
	WebElement checkOutButton;

	@FindBy(xpath="//input[@id='first-name']")
	WebElement firstName;
	
	@FindBy(xpath = "//input[@id='last-name']")
	WebElement lastName; 
	
	@FindBy(xpath = "//input[@id='postal-code']")
	WebElement zipCodes; 
	
	@FindBy(xpath = "//input[@id='continue']")
	WebElement continueButton; 
	
	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	WebElement totalPrice; 
	
	@FindBy(xpath = "//button[@id='finish']")
	WebElement finishCheckOutButton; 
	
	@FindBy(xpath = "//h2[@class='complete-header']")
	WebElement checkOutSuccessMessage; 
	

	public void cliCkcheckOutButton() {
		webElementInteractions.clickWebElement(checkOutButton, "checkOutButton");	
	}
	
	public void inputFirstName(String firstNames) {
		webElementInteractions.sendKeysWebElement(firstName, firstNames);
		
	}
	
	public void inputLastName(String lastNames) {
		webElementInteractions.sendKeysWebElement(lastName, lastNames);
		
	}
	
	public void inputZipCode(String zipCode) {
		webElementInteractions.sendKeysWebElement(zipCodes, zipCode);
		
	}
	
	
	public void fillCheckOutForm(String firstName, String lastName ,String zipCode) {
		inputFirstName(firstName);
		inputLastName(lastName);
		inputZipCode(zipCode);
		cliContinueButton();
	}
	
	public void cliContinueButton() {
		webElementInteractions.clickWebElement(continueButton, "continueButton");	
	}
	
	public void getTotal(String total) {
		
		webElementInteractions.compareText(totalPrice, total);
	}
	
public void compareSuccessMessage(String text) {
		
		webElementInteractions.compareText(checkOutSuccessMessage, text);
	}
	
	public void clickFinishButton() {
		webElementInteractions.clickElementWithJS(finishCheckOutButton);
			
	}
	

}