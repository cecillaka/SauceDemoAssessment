package test.pages.web;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.utils.WebElementInteractions;

public class WebLandingPage {
	WebDriver browserDriver;
	private WebElementInteractions webElementInteractions = new WebElementInteractions();

	public WebLandingPage(WebDriver driver){
		browserDriver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//div[.='Sauce Labs Backpack']")
	WebElement selectSouceLabBackpack;

	@FindBy(xpath="//div[.='Sauce Labs Bike Light']")
	WebElement selectSouceLabBikeLight;
	
	@FindBy(xpath = "//button[@id='add-to-cart']")
	WebElement addToCart; 
	
	@FindBy(xpath = "//button[@id='back-to-products']")
	WebElement backToProducts; 
	
	@FindBy(css = ".shopping_cart_link")
	WebElement itemsCount; 
	
	

	public void clickSelectSouceLabBackpack() {
		webElementInteractions.clickWebElement(selectSouceLabBackpack, "selectSouceLabBackpack");	
	}
	
	public void clickSelectSouceLabBikeLight() {
		webElementInteractions.clickWebElement(selectSouceLabBikeLight, "selectSouceLabBikeLight");	
	}
	
	public void clickAddToCartButton() {
		webElementInteractions.clickWebElement(addToCart, "addToCart");	
	}
	
	public void clickBackToProductsButton() {
		webElementInteractions.clickWebElement(backToProducts, "backToProducts");	
	}
	
	public void addItemsSouceLabBackpackToCart() {
		clickSelectSouceLabBackpack();
		clickAddToCartButton();
		clickBackToProductsButton();
	}
	
	public void addItemsSouceLabBikeLightToCart() {
		clickSelectSouceLabBikeLight();
		clickAddToCartButton();
		clickBackToProductsButton();
	}
	
	public void addItemsToCart(String itemName) {
		if (itemName.contains("Bike Light"))
		{
			addItemsSouceLabBikeLightToCart();
		}
		
		else if(itemName.contains("Labs Backpack")) {
			addItemsSouceLabBackpackToCart();
		}
		
	}
	
	public void checkItemsCount(String TotalItemscount) {
		webElementInteractions.compareText(itemsCount, TotalItemscount);
	}

}