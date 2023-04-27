package org.selenium.pom.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class BaseTest {
	protected WebDriver driver;
    public static ExtentSparkReporter htmlReporter ;
    public static ExtentReports extent;
    public static ExtentTest test;
	
    @BeforeAll
	public static void setUp() {
    	htmlReporter  = new ExtentSparkReporter("extent_report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
	}
    
	@BeforeEach		
    public void startDriver(TestInfo testInfo) {
		driver = new DriverManager().initializeDriver();
        test = extent.createTest(getClass().getSimpleName(), testInfo.getDisplayName());
    }
	
	@AfterEach		
    public void quitDriver() {					
		extent.flush();
		driver.quit();
    }
	
	@AfterAll		
    public static void tearDown() {					
		extent.flush();
    }
}
