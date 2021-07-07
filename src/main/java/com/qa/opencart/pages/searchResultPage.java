package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtility;

public class searchResultPage {
	
	private WebDriver driver;
	private ElementUtility element;
	
	
	
	By searchHeaderName = By.cssSelector("div#content h1");
	By productResults = By.cssSelector("div.caption a");
	
	
	public searchResultPage(WebDriver driver)
	{
		this.driver = driver;
		element = new ElementUtility(this.driver);
		
		
	}
	
	public String getSearchHeader()
	{
		return element.getTextMethod(searchHeaderName);
	}
	
	public int getSearchProductListCount()
	{
		return element
				.waitForVisibilityOfElements(productResults, Constants.DEFAULT_TIME_OUT).size();
	}
	
	public ProductInfoPage selectProduct(String productName)
	{
		List<WebElement> productList = element.waitForVisibilityOfElements(productResults, Constants.DEFAULT_TIME_OUT);
		for(WebElement e : productList)
		{
			String text = e.getText();
			if(text.equals(productName))
			{
				e.click();
				break;
			}
		}
		
		return new ProductInfoPage(driver);
	}
	
	

}
