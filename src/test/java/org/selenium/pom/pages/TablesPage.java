package org.selenium.pom.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class TablesPage extends BasePage {
	
	private final String tablesPageUrl = "http://www.w3schools.com/html/html_tables.asp";
	
	public TablesPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	Navigates to the tables page and waits until loading is done
	*/
	public void load() {
		try {
			//navigate to tables page
			driver.get(tablesPageUrl);
			
			//wait for table loading
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='customers']")));
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/**
	Returns the relevant cell value according to a specific corresponding value (from anther cell)
	@param WebElement table - the relevant table
	@param int searchColumn - the column index of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@return String of the value
	*/
	public String getTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText) {
		
		String result = null;
		
		//create a list of rows
		List<WebElement> rows = table.findElements(By.tagName("tr"));
	
		//get rows count
		int rowsCount = rows.size();
		
		//iterate over rows
		for (int i=0; i<rowsCount; i++) {
			
			//get cells in the current row
			List<WebElement> rowCells = rows.get(i).findElements(By.xpath("./*"));
			//get the the relevant cell in the current row
		    WebElement searchColumnCell = rowCells.get(searchColumn);
		    
		    //check if the given value is in this cell (and row) 
		    if(searchColumnCell.getText().equals(searchText)) {
		    	//get the WebElement of the result cell
		    	WebElement cellElement = rowCells.get(returnColumnText);
		    	//get the result value
		    	result = cellElement.getText();
		    	break;
		    }
		}
	    
	    return result;
	}
	
	/**
	Call getTableCellText method and verify the result value
	@param WebElement table - the relevant table
	@param int searchColumn - the column index of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@param expectedText
	@return String of the value
	*/
	public boolean verifyTableCellText(WebElement table, int searchColumn, String searchText, int returnColumnText, String expectedText) {
		String cellValue = getTableCellText(table, searchColumn, searchText, returnColumnText);
		return cellValue.equals(expectedText);
	}
	
	/**
	Returns the relevant cell value according to a specific corresponding value (from anther cell) using xpath
	@param WebElement table - the relevant table
	@param int searchColumn - the column index of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@return String of the value
	*/
	public String getTableCellTextByXpath(WebElement table, int searchColumn, String searchText, int returnColumnText) throws Exception {
		//convert column indexes into column number
		int givenColumn = searchColumn + 1;
		int resultColumn = returnColumnText + 1;
			    
		WebElement cell = null;
		
		try {
			//get the result cell
			cell = table.findElement(By.xpath(".//tr/td["+ givenColumn +"][text()='"+ searchText +"']/parent::tr/td["+ resultColumn +"]"));

		} catch (NoSuchElementException e) {
			throw new Exception("Element not found");
		}
		//return the result value
		return cell.getText();
	}
	
	/**
	Returns the column index of a given column name
	@param WebElement table - the relevant table
	@param String columnName - the header of the column
	@return int - column index
	*/
	private int convertColumnNameToColumnIndex(WebElement table, String columnName) {
		
		List<WebElement> columns = table.findElements(By.tagName("th"));
		int columnsCount = columns.size();
		
		int columnIndex = -1;
		
		for(int i=0; i<columnsCount; i++) {
			if(columns.get(i).getText().equals(columnName)) {
				columnIndex = i;
				break;
			}
		}
		return columnIndex;
	}
	
	/**
	Returns the relevant cell value according to a specific corresponding value (from anther cell)
	@param WebElement table - the relevant table
	@param String searchColumnName - the column name of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@return String of the value
	*/
	public String getTableCellText(WebElement table, String searchColumnName, String searchText, int returnColumnText) {
		//get the column index of the given value
		int columnIndex = convertColumnNameToColumnIndex(table, searchColumnName);
		//get the result value
		String result = getTableCellText(table, columnIndex, searchText, returnColumnText);
	    return result;
	}
	
	/**
	Call getTableCellText method and verify the result value
	@param WebElement table - the relevant table
	@param String searchColumnName - the column name of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@param expectedText
	@return String of the value
	*/
	public boolean verifyTableCellText(WebElement table, String searchColumnName, String searchText, int returnColumnText, String expectedText) {
		//get the column index of the given value
		int columnIndex = convertColumnNameToColumnIndex(table, searchColumnName);
		//verify result value
		boolean result = verifyTableCellText(table, columnIndex, searchText, returnColumnText, expectedText);
		return result;
	}
	
	/**
	Returns the relevant cell value according to a specific corresponding value (from anther cell) using xpath
	@param WebElement table - the relevant table
	@param String searchColumnName - the column name of the given value
	@param int returnColumnText - the column index of the requested value
	@param String searchText - the given cell value
	@return String of the value
	*/
	public String getTableCellTextByXpath(WebElement table, String searchColumnName, String searchText, int returnColumnText) throws Exception {
		//get the column index of the given value
		int columnIndex = convertColumnNameToColumnIndex(table, searchColumnName);
		//get the result value
		String result = getTableCellTextByXpath(table, columnIndex, searchText, returnColumnText);
		return result;
	}
}
