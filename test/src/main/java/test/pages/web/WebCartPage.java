package test.pages.web;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.utils.WebElementInteractions;

public class WebCartPage {
	WebDriver browserDriver;
	private WebElementInteractions webElementInteractions = new WebElementInteractions();

	public WebCartPage(WebDriver driver){
		browserDriver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//span[@class='shopping_cart_badge']")
	WebElement cartButton;

	@FindBy(xpath="(//div[@class='cart_item'])[1]")
	WebElement cart1;
	
	@FindBy(xpath = "(//div[@class='cart_item'])[2]")
	WebElement cart2; 
	
 
	
	public void clickCartButton() {
		webElementInteractions.clickWebElement(cartButton, "cartButton");	
	}
	
	public void confirmCorrectPriceAndNameCart1(String price ,String Name) throws IOException {
		webElementInteractions.compareText(cart1, price);
		webElementInteractions.compareText(cart1, Name);
	}
	
	public void confirmCorrectPriceAndNameCart2(String price ,String Name) throws IOException {
		webElementInteractions.compareText(cart2, price);
		webElementInteractions.compareText(cart2, Name);
	}
	
	
	

}