package test.utils;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import base.test.BaseTestMobi;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import test.listeners.*;

public class MobiElementInteractions extends TestListener {

	private WebDriverWait wait;
	private BaseTestMobi baseTest;

	public MobiElementInteractions() {

		baseTest=new BaseTestMobi();
		this.wait = new WebDriverWait(baseTest.returnAndroidDriver(), Duration.ofSeconds(45));

	}

	public void clickMobiElement(WebElement element, String elementName) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());
			test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to click a [button] " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);

		}
	}
	
	public boolean verifyMobiElementDisplayed(WebElement element, String elementName) {
		 boolean isDisplayed =false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			Log4jLogger.loggerInfo(elementName +" Displayed Successfully on "+baseTest.getDeviceName());
			test.pass(elementName +" Displayed Successfully! on "+baseTest.getDeviceName());
			isDisplayed= true;

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to Display :" + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
			isDisplayed=false;

		}
		
		return isDisplayed;
	}
	
	public void compareText(WebElement element, String text) {
		
		try {
			wait.until(ExpectedConditions.visibilityOf(element));			
			if(element.getText().contains(text)){
				Log4jLogger.loggerInfo(text+ ": Match with :"+element.getText()+" on "+baseTest.getDeviceName());
				test.pass(text+ ": Match with :"+element.getText()+" on "+baseTest.getDeviceName());
			}
			
			else {
				Log4jLogger.loggerError(text+ ": Does not Match with :"+element.getText()+" on "+baseTest.getDeviceName());
				// Manually invoke the failure handling
				ITestResult result = Reporter.getCurrentTestResult();
				result.setStatus(ITestResult.FAILURE);				
				String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
				test.fail(text+ ": Does not Match with :"+element.getText(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				onTestFailure(result);
			}
			
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Could'nt compare text :" + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
			

		}
		
		
	}
	
	public void retryClickMobiElement(WebElement element, String elementName) {
		try {
			  WebDriverWait waitForrRetry = new WebDriverWait(baseTest.returnAndroidDriver(), Duration.ofSeconds(10));
			  waitForrRetry.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());
			

		} catch (Exception e) {
			Log4jLogger.loggerWarn(e.getMessage());


		}
	}



	public void clickMobiElementWithReTry(WebElement element, String elementName,int timeInSec) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());
			test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Retry logic 
			try {
				Log4jLogger.loggerError("retry to click button in :"+timeInSec);
				Thread.sleep(timeInSec); // Retry after a brief pause
				element.click();
				Log4jLogger.loggerInfo(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());
				test.pass(elementName +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());

			} catch (Exception retry) {
				Log4jLogger.loggerError("Retry also failed: " + retry.getMessage());

				// Manually invoke the failure handling
				ITestResult result = Reporter.getCurrentTestResult();
				result.setStatus(ITestResult.FAILURE);
				result.setThrowable(retry);
				String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
				test.fail("Failed to click a [button] " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				onTestFailure(result);
			}


		}
	}

	public void sendKeysMobiElement(WebElement element ,String msg) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(msg);
			Log4jLogger.loggerInfo(msg +" Sent Successfully! on "+baseTest.getDeviceName());
			test.pass(msg +" Sent Successfully! on "+baseTest.getDeviceName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}
	}

	public void sendKeysWithClearMobiElement(WebElement element ,String msg) throws Exception {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			Thread.sleep(1000);
			element.sendKeys(msg);
			Log4jLogger.loggerInfo("input box cleared "+msg +" Sent Successfully! on "+baseTest.getDeviceName());
			test.pass("input box cleared "+msg +" Sent Successfully! on "+baseTest.getDeviceName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}


	  public void scrollAndClickElementByText(String textToScrollTo) {
	        try {
	        	Thread.sleep(1000);
	        	Log4jLogger.loggerInfo("scrolling to element containing text : "+textToScrollTo +"  on "+baseTest.getDeviceName());
				test.pass("scrolling to element containing text :"+textToScrollTo +" on "+baseTest.getDeviceName());
	           
				WebElement element = baseTest.returnAndroidDriver().findElement(AppiumBy.androidUIAutomator(
	                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
	                ".scrollIntoView(new UiSelector().textContains(\"" + textToScrollTo + "\").instance(0))"
	            ));
	            element.click();
	            Log4jLogger.loggerInfo(element +" [button] "+ " clicked Successfully! on "+baseTest.getDeviceName());
	           
	        } catch (Exception e) {
	        	Log4jLogger.loggerError(e.getMessage());

				// Manually invoke the failure handling
				ITestResult result = Reporter.getCurrentTestResult();
				result.setStatus(ITestResult.FAILURE);
				result.setThrowable(e);
				String base64Screenshot = ((TakesScreenshot) baseTest.returnAndroidDriver()).getScreenshotAs(OutputType.BASE64);
				test.fail("Failed : " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
				onTestFailure(result);
	        }
	        
	       
	    }
	  
	  public boolean waitForElementToDisappear(WebElement element, String elementName) {
		    boolean hasDisappeared = true;
		    try {
		        // Initialize WebDriverWait with a 10-second timeout
		        WebDriverWait waitForElementToDisappear = new WebDriverWait(baseTest.returnAndroidDriver(), Duration.ofSeconds(10));
		        
		        // Wait for the element to disappear
		        //waitForElementToDisappear.until(ExpectedConditions.invisibilityOf(element));
		        waitForElementToDisappear.until(ExpectedConditions.visibilityOf(element));
		        
		        // Log success and set return value to true
		        Log4jLogger.loggerInfo(elementName + " has displayed successfully on " + baseTest.getDeviceName());
		        Thread.sleep(1000);
		        hasDisappeared = false;
		    } catch (Exception e) {
		        // Log warning if element does not disappear
		       // Log4jLogger.loggerWarn("Failed to confirm disappearance of " + elementName + " on " + baseTest.getDeviceName());
		    }
		    return hasDisappeared;
		}

	  
	  public void tapByCoordinates(int x ,int y) {
		  PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		  Sequence tap = new Sequence(finger, 0);
		  tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		  tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		  tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		  baseTest.returnAndroidDriver().perform(Arrays.asList(tap));
	  }
	  
	  public void pressArrowDown() {
		  ((PressesKey) baseTest.returnAndroidDriver()).pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
      }
	
}

