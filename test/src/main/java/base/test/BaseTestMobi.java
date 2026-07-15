package base.test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import test.utils.AppiumServerManager;
import test.utils.Log4jLogger;
import test.utils.ADBCommandExecutor;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTestMobi {	
	private static AndroidDriver androiddriver;
	public static WebDriver browserDriver;
	public static String stringDeviceName;
	public DesiredCapabilities desiredCapabilities;
	private static String appPackage="za.co.trenstar.android.scanner.agri_dt50";

	public void initializeAndroidDriver(String deviceName, String appiumPort) throws MalformedURLException  {

		stringDeviceName=deviceName;
		
		String appiumServerUrl = "http://127.0.0.1:4723";
		UiAutomator2Options options = getDeviceOptions().get(stringDeviceName);

		if (androiddriver != null) {
		    try {
		    	ADBCommandExecutor.forceStopApp(appPackage);
				androiddriver.quit();						
		        Log4jLogger.loggerInfo("Previous Appium driver session terminated.");
		    } catch (Exception e) {
		        Log4jLogger.loggerWarn("Error while quitting previous driver: " + e.getMessage());
		    }
		}

		try {
		    Log4jLogger.loggerInfo("Appium Server URL: " + appiumServerUrl);
		    Log4jLogger.loggerInfo("Desired Capabilities: " + options.toString());
		    androiddriver = new AndroidDriver(new URL(appiumServerUrl), options);
		    ADBCommandExecutor.launchApp(appPackage,"za.co.trenstar.android.activity.LoginActivity");//test launching the app
		    Log4jLogger.loggerInfo("Appium driver initialized successfully for device: " + deviceName);
		} catch (Exception e) {
		    Log4jLogger.loggerError("Failed to initialize Appium driver for device: " + deviceName);
		    Log4jLogger.loggerError(e.getMessage());
		    throw e; // Rethrow the exception for better visibility during debugging
		}
		}

	public static Map<String, UiAutomator2Options> getDeviceOptions() {
		Map<String, UiAutomator2Options> deviceOptions = new HashMap<>();

		// Device 1 Configuration
		UiAutomator2Options device1Options = new UiAutomator2Options()
				.setPlatformName("Android")
				.setDeviceName("DT50")
				.setUdid("01992421004962")			
				.setAppPackage(appPackage)
				.setAppActivity("za.co.trenstar.android.activity.LoginActivity")
				.setAutomationName("UiAutomator2")				
				.setNoReset(true)
				.setFullReset(false)
				.setSkipDeviceInitialization(false)
				.setAppWaitDuration(Duration.ofSeconds(30))
				.setNewCommandTimeout(Duration.ofMinutes(2));
		

		deviceOptions.put("DT50", device1Options);

		// Device 2 Configuration
		UiAutomator2Options device2Options = new UiAutomator2Options()
				.setPlatformName("Android")
				.setDeviceName("vivo Y02")
				.setUdid("10HD390B6L0008L")
				//.setSystemPort(Integer.parseInt("8201"))
				.setAppPackage("za.co.trenstar.android.scanner.agri_dt50_android_11")
				.setAppActivity("za.co.trenstar.android.activity.LoginActivity")
				.setAutomationName("UiAutomator2")
				.setNoReset(true);        
		deviceOptions.put("vivo Y02", device2Options);

		// Device 3 Configuration
		UiAutomator2Options device3Options = new UiAutomator2Options()
				.setPlatformName("Android")				
				.setDeviceName("DT50U")
				.setUdid("01782328012811")
				
				.setAppPackage("za.co.trenstar.android.scanner.dt50_android_11")
				.setAppActivity("za.co.trenstar.android.activity.LoginActivity")
				.setAutomationName("UiAutomator2")
				.setNoReset(true);
		
		deviceOptions.put("DT50U", device3Options);

		return deviceOptions;
	}

	//AppiumDriver
	public AppiumDriver returnAndroidDriver() {
		return androiddriver;
	}

	//get device name
	public String  getDeviceName() {
		return stringDeviceName;
	}

	public void androidtearDown() {
		try {
			Thread.sleep(5000);
			if (androiddriver != null) {
				ADBCommandExecutor.forceStopApp(appPackage);
				androiddriver.quit();		
				Log4jLogger.loggerWarn("Closing Android Driver...");
			}
		} catch (InterruptedException e) {
			Log4jLogger.loggerError(e.getMessage());
		}
	
	}
}
