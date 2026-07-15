package test.listeners;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.test.BaseTestMobi;


public class TestListener implements ITestListener {
	public static ExtentReports extent;
	public static ExtentTest test;


	@Override
	public void onStart(ITestContext context) {

		  // Get the test class name
	    String testClassName = context.getCurrentXmlTest().getName();
	    
	    // Generate a dynamic file name using the test class name
	    String reportFileName = "extent-reports/" + testClassName + "-report.html";
	    
	    // Set up the ExtentSparkReporter with the dynamic file name
	    ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportFileName);
	    
	    try {
	        htmlReporter.loadJSONConfig(new File("spark-config.json"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Attach the reporter to the ExtentReports instance
	    extent = new ExtentReports();
	    extent.attachReporter(htmlReporter);
		

	}

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test passed");
							
		}

	}

	@Override
	public void onTestFailure(ITestResult result) {
	
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
		 if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test skipped: " + result.getThrowable().getMessage());
		}
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
