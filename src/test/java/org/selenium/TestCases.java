package org.selenium;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.TablesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class TestCases extends BaseTest {
		
	@Test
	@DisplayName("Languages Test")
	//@Disabled
	public void firstTest() {
		Boolean testResult = false;
		HomePage homePage = new HomePage(driver);
		
		//navigate to 888 home page and wait until page done loading
		homePage.load();
		
		//print all the languages in the 'switch language' dropdown
		homePage.printAvailableLanguages();
		
		// get languages list
		List<String> languages = homePage.getAvailableLanguages();
		
		for (String language : languages) {
			//switch to each language and verify that the button has changed accordingly
			testResult = homePage.validateLanguagesSwitch(language);
			if(testResult) {
				test.pass(language);
			} else {
				test.fail(language);
			}
			assertTrue(testResult);
		}		
	}
	
	//@Disabled
    @ParameterizedTest
	@DisplayName("Second Test")
    @CsvFileSource(resources = "/testdata_secondTest.csv", numLinesToSkip = 1)	
	public void secondTest(String sourceColumn, String sourceColumnName, String searchText, String targetColumn, String verifyText) {
		Boolean testResult = false;
    	TablesPage tablesPage = new TablesPage(driver);
		int sourceColumnInt = Integer.parseInt(sourceColumn);
		int targetColumnInt = Integer.parseInt(targetColumn);
		
		//navigate to tables page and wait until page done loading
		tablesPage.load();
				
		//find element of customers table
		WebElement customerTable = driver.findElement(By.xpath("//table[@id='customers']"));
				
		//getTableCellText
		testResult = verifyText.equals(tablesPage.getTableCellText(customerTable, sourceColumnInt, searchText, targetColumnInt));
		assertTrue(testResult);
		if(testResult) {
			test.pass("getTableCellText");
		} else {
			test.fail("getTableCellText");
		}
		
		//verifyTableCellText
		testResult = tablesPage.verifyTableCellText(customerTable, sourceColumnInt, searchText, targetColumnInt, verifyText);
		assertTrue(testResult);
		if(testResult) {
			test.pass("verifyTableCellText");
		} else {
			test.fail("verifyTableCellText");
		}

		//getTableCellTextByXpath
		try {
			assertEquals(tablesPage.getTableCellTextByXpath(customerTable, sourceColumnInt, searchText, targetColumnInt ), verifyText); 
			test.pass("getTableCellTextByXpath");
		} catch (Exception e) {
			test.fail("getTableCellTextByXpath. " + e.getMessage());
		}

		//getTableCellText
		testResult = verifyText.equals(tablesPage.getTableCellText(customerTable, sourceColumnName, searchText, targetColumnInt));
		assertTrue(testResult);
		if(testResult) {
			test.pass("getTableCellText");
		} else {
			test.fail("getTableCellText");
		}		
		
		//verifyTableCellText
		testResult = tablesPage.verifyTableCellText(customerTable, sourceColumnName, searchText, targetColumnInt, verifyText);
		assertTrue(testResult);
		if(testResult) {
			test.pass("getTableCellText");
		} else {
			test.fail("getTableCellText");
		}
		
		//getTableCellTextByXpath
		try {
			assertEquals(tablesPage.getTableCellTextByXpath(customerTable, sourceColumnName, searchText, targetColumnInt), verifyText);
			test.pass("getTableCellTextByXpath");
		} catch (Exception e) {
			test.fail(e.getMessage());
			test.fail("getTableCellTextByXpath. " + e.getMessage());
		}
	}
}
