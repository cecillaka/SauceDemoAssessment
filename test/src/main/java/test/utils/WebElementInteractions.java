package test.utils;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import base.test.BaseTestWeb;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.functions.ExpectedCondition;
import test.listeners.*;

public class WebElementInteractions extends TestListener {

	private WebDriverWait wait;
	private static BaseTestWeb baseTest;
	private static WebDriver browserDriver;

	public WebElementInteractions() {

		baseTest=new BaseTestWeb();
		browserDriver=baseTest.getDriver();
		this.wait = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(30));

	}

	public void clickWebElement(WebElement element, String elementName) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());
			test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to click a [button] " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);

		}
	}

	//----------------needs to be updated and checked------------------
	public void clickWebElementRetry(WebElement element, String elementName, int retry) {
		int attempt = 0;
		boolean isClicked = false;

		while (attempt <= retry) {
			try {
				// Wait for the element to be clickable
				wait.until(ExpectedConditions.elementToBeClickable(element));

				// Attempt to click
				element.click();
				Log4jLogger.loggerInfo(elementName + " [button] clicked successfully on " + baseTest.getBrowserName());
				test.pass(elementName + " [button] clicked successfully on " + baseTest.getBrowserName());
				isClicked = true;
				break; // Exit the loop if successful
			} catch (Exception e) {
				attempt++;
				Log4jLogger.loggerError("Attempt " + attempt + " failed to click " + elementName + " [button]: " + e.getMessage());

				// Retry if not the last attempt
				if (attempt > retry) {
					Log4jLogger.loggerError("Max retries reached for " + elementName + " [button]");
					ITestResult result = Reporter.getCurrentTestResult();
					result.setStatus(ITestResult.FAILURE);
					result.setThrowable(e);

					try {
						// Capture a screenshot
						String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
						test.fail("Failed to click [button]: " + e.getMessage(),
								MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
					} catch (Exception screenshotException) {
						Log4jLogger.loggerError("Failed to capture screenshot: " + screenshotException.getMessage());
					}

					onTestFailure(result);	                
					Log4jLogger.loggerError("Failed to click " + elementName+ " after "+retry+" retries :"+e.getMessage());
				}
			}
		}

		if (!isClicked) {
			Log4jLogger.loggerError("Failed to click " + elementName+ " after "+retry+" retries :");
		}
	}


	public void clickWebElementWithReTry(WebElement element, String elementName,int timeInMiliSec) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());
			test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Retry logic 
			try {
				Log4jLogger.loggerError("retry to click button in :"+timeInMiliSec);
				Thread.sleep(timeInMiliSec); // Retry after a brief pause
				element.click();
				Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());
				test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getBrowserName());

			} catch (Exception retry) {
				Log4jLogger.loggerError("Retry also failed: " + retry.getMessage());

				// Manually invoke the failure handling
				ITestResult result = Reporter.getCurrentTestResult();
				result.setStatus(ITestResult.FAILURE);
				result.setThrowable(retry);
				String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
				test.fail("Failed to click a [button] " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				onTestFailure(result);
			}

		}
	}

	public void sendKeysWebElement(WebElement element ,String msg) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(msg);
			Log4jLogger.loggerInfo(msg +" Sent Successfully! on "+baseTest.getBrowserName());
			test.pass(msg +" Sent Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}
	}

	public void sendKeysWithClearWebElement(WebElement element ,String msg) throws Exception {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			Thread.sleep(1000);
			element.sendKeys(msg);
			Log4jLogger.loggerInfo(msg +" Sent Successfully! on "+baseTest.getBrowserName());
			test.pass(msg +" Sent Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}
	public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
		try {
			Select dropdown = new Select(dropdownElement);
			dropdown.selectByVisibleText(visibleText);
			Log4jLogger.loggerInfo(""+visibleText +" Sent Successfully! on "+baseTest.getBrowserName());
			test.pass(""+visibleText +" Sent Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to select from dropdown: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}

	}

	public void accessSideMenu(String menuUrl) {
		try {
			baseTest.getDriver().get("https://eamstaging.trenstar.co.za/Agri/"+menuUrl);
			Log4jLogger.loggerInfo("Navigated to "+menuUrl +" Successfully! on "+baseTest.getBrowserName());
			test.pass("Navigated to "+menuUrl +" Successfully! on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to navigate: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}

	}

	public static void waitForPageToLoad( int timeoutInSeconds) {

		try {
			WebDriverWait wait = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeoutInSeconds));
			wait.until((ExpectedCondition<Boolean>) wd ->
			((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
		}
		Log4jLogger.loggerInfo("Page has fully loaded on "+baseTest.getBrowserName());

	}

	public static void clickElementWithJS( WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) baseTest.getDriver();
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public static WebDriver returnBrowserDriver() {
		return browserDriver;
	}

	public void failOnElementDipslay(WebElement element, String elementName,int timeInSec) throws IOException {

		try {

			WebDriverWait waitForElementToDisappear = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeInSec));

			// check element displayed
			waitForElementToDisappear.until(ExpectedConditions.invisibilityOf(element));

			Log4jLogger.loggerInfo(elementName + " Not Displayed on "+baseTest.getBrowserName());
			test.pass(elementName +" Not Displayed on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed :"+elementName+" Displayed  on: "+baseTest.getBrowserName());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed :"+elementName+" Displayed  on: " + baseTest.getBrowserName(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
			throw new IOException("Manually triggered IOException");
		}
	}

	public void waitForElementToDisplay (WebElement element, String elementName,int timeInSec) throws IOException {

		try {
			WebDriverWait waitForElementToAppear = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeInSec));
			waitForElementToAppear.until(ExpectedConditions.visibilityOf(element));
			Log4jLogger.loggerInfo(elementName + " Displayed on :"+baseTest.getBrowserName());	            	           
			test.pass(elementName +" Displayed on :"+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed :"+e+" "+baseTest.getBrowserName());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed :"+elementName+" : " + baseTest.getBrowserName(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}


	public void compareText(WebElement element, String text) {

		try {
			wait.until(ExpectedConditions.visibilityOf(element));			
			if(element.getText().contains(text)){
				Log4jLogger.loggerInfo(element.getText()+ ": Contains :"+text+" on "+baseTest.getBrowserName());
				test.pass(element.getText()+ ": Contains:"+text+" on "+baseTest.getBrowserName());
			}

			else {
				Log4jLogger.loggerError(element.getText()+ ": Does not  Contains :"+text+" on "+baseTest.getBrowserName());
				// Manually invoke the failure handling
				ITestResult result = Reporter.getCurrentTestResult();
				result.setStatus(ITestResult.FAILURE);				
				String base64Screenshot = ((TakesScreenshot)baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
				test.fail(element.getText()+ ": Does not  Contains :"+text, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				onTestFailure(result);
			}

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Could'nt compare text :" + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}
	
	
	public boolean getTextFromElement(WebElement element) {
		boolean ElementText=false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));			
			if(element.getText()!=null){
				Log4jLogger.loggerInfo(element.getText()+ ": Extracted succesfully :"+" on "+baseTest.getBrowserName());
				test.pass(element.getText()+ ":  Extracted succesfully : on " +baseTest.getBrowserName());
				ElementText=true;
			}

			else {
				Log4jLogger.loggerError("Element contains Null on :"+baseTest.getBrowserName());
			}

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}
		return ElementText;
	}
	

	  public void scrollDown() {
	        try {
	        	Thread.sleep(1000);
	        	Log4jLogger.loggerInfo("scrolling   on "+baseTest.getBrowserName());
				Thread.sleep(1000);
	            JavascriptExecutor js =(JavascriptExecutor)baseTest.getDriver();
	            js.executeScript("windows.scrollBy(0,500)");
	            Log4jLogger.loggerInfo("Scrolling on "+baseTest.getBrowserName());
	           
	        } catch (Exception e) {
	        	Log4jLogger.loggerError(e.getMessage());

				// Manually invoke the failure handling
//				ITestResult result = Reporter.getCurrentTestResult();
//				result.setStatus(ITestResult.FAILURE);
//				result.setThrowable(e);
//				String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
//				test.fail("Failed : " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
//				onTestFailure(result);
	        }
	        
	       
	    }
	  
	  public List<String> getTextFromTable(WebElement tableElement) {
		    List<String> tableData = new ArrayList<>();
		    try {
		        wait.until(ExpectedConditions.visibilityOf(tableElement));
		        
		        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		        for (WebElement row : rows) {
		            List<WebElement> cells = row.findElements(By.tagName("td"));
		            for (WebElement cell : cells) {
		                String cellText = cell.getText();
		                if (cellText != null && !cellText.isEmpty()) {
		                    Log4jLogger.loggerInfo("Extracted: " + cellText + " on " + baseTest.getBrowserName());
		                    test.pass("Extracted: " + cellText + " on " + baseTest.getBrowserName());
		                    tableData.add(cellText);
		                } else {
		                    Log4jLogger.loggerError("Empty or null cell value detected on: " + baseTest.getBrowserName());
		                }
		            }
		        }
		    } catch (Exception e) {
		        Log4jLogger.loggerError(e.getMessage());
		        ITestResult result = Reporter.getCurrentTestResult();
		        result.setStatus(ITestResult.FAILURE);
		        result.setThrowable(e);
		        String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
		        test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
		        onTestFailure(result);
		    }
		    return tableData;
		}

	  

}
