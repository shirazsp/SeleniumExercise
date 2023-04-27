package org.selenium.pom.pages;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class HomePage extends BasePage {

	private final String homePageUrl = "http://www.888.com";
	private final By languageButton = By.xpath("//nav//span[@class='current-language']");
	private final By languageDropdown = By.xpath("//nav//div[contains(@class,'lang-switch-dropdown')]");
	private final By availableLanguages = By.xpath("//div[@class='lang-switch-dropdown header-only-mobile show']//li[@class='languageLink']/a");
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	/**
	Navigates to the home page and waits until loading is done
	*/
	public void load() {
		try {
			//navigate to home page
			driver.get(homePageUrl);
			
			//wait for 'switch language' Button loading
			wait.until(ExpectedConditions.visibilityOfElementLocated(languageButton));
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	Clicks on the 'switch language' button
	*/
	private void clickLanguageButton() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(languageButton)).click();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/**
	Returns the WebElements of the available languages
	@return List of WebElements (for each language 'a' element)
	*/
	private List<WebElement> getLanguagesElementsList() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(languageDropdown));
		return driver.findElements(availableLanguages);
	}
	
	/**
	Returns the available languages
	@return List of languages (strings)
	*/
	public List<String> getAvailableLanguages() {
		
		List<String> languages = new ArrayList<String>();
		
		//click on the language button (to open the dropdown)
		clickLanguageButton();
		
		//add languages to list
		List<WebElement> languagesElements = getLanguagesElementsList();

		for (WebElement languageElement : languagesElements) {
		    languages.add(languageElement.getText());
		}
		
		//add default language to the list
		languages.add("English");
		
		//click on the language button again (to close the dropdown)
		clickLanguageButton();
		
		return languages;
	}
	
	/**
	Prints the available languages
	*/
	public void printAvailableLanguages() {
		//get available languages
		List<String> languages = getAvailableLanguages();
		//print available languages
		System.out.println("Available languages:");
		System.out.println(languages);
	}
	
	/**
	 * Clicks on the 'switch language' button, clicks on each language and validates
	 * the button changed accordingly
	 */
	public Boolean validateLanguagesSwitch(String language) {
		// click on the language button to open dropdown
		clickLanguageButton();

		// click on each language and validate the switch
		String languageLink, languageCode1, languageCode2, languageCode3="", buttonText;
		WebElement languageLinkElement;

		// get the link element of the language
		languageLinkElement = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(language)));

		// get the link of the language
		languageLink = languageLinkElement.getAttribute("href"); // for example: "https://de.888.com/" or "https://www.888.com/?lang=en"

		// check the link structure
		if(languageLink.contains("lang=")) {
			// get language code (using method 1 - substring)
			languageCode1 = languageLink.substring(26);
			
			// get language code (using method 2 - split)
			languageCode2 = languageLink.split("lang=")[1];
			
			// get language code (using method 3 - regex)
			Pattern pattern = Pattern.compile("\\?lang=(\\w+)");
			Matcher matcher = pattern.matcher(languageLink);
			if (matcher.find()) {
				languageCode3 = matcher.group(1);
	        }
		}else { // link does not contains 'lang'
			// get language code (using method 1 - substring)
			languageCode1 = languageLink.substring(8, 10);

			// get language code (using method 2 - split)
			languageCode2 = languageLink.split(".888")[0].split("//")[1];

			// get language code (using method 3 - regex)
			Pattern pattern = Pattern.compile("https://(\\w+).888.com/");
			Matcher matcher = pattern.matcher(languageLink);
			if (matcher.find()) {
				languageCode3 = matcher.group(1);
	        }
		}

		// click on the language link
		languageLinkElement.click();

		// VALIDATION
		
		// get text of 'switch language' button
		buttonText = wait.until(ExpectedConditions.visibilityOfElementLocated(languageButton)).getText();
		
		// validate using method 1
		if (!languageCode1.toUpperCase().equals(buttonText)) {
			return false;
		}

		// validate using method 2
		if (!languageCode2.toUpperCase().equals(buttonText)) {
			return false;
		}

		// validate using method 3
		if (!languageCode3.toUpperCase().equals(buttonText)) {
			return false;
		}
	return true;
	}
}	
