package com.qa.opencart.pages;



import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtility;

public class AccountPage {
	
	private WebDriver driver;
	private ElementUtility elementUtil;
	
	private By yourStoreLogo = By.cssSelector("div#logo a");
	private By acctSectionsHeaders = By.cssSelector("div#content h2");
	private By searchText = By.name("search");
	private By searchButton = By.cssSelector("div#search button");
	private By logoutLink = By.linkText("Logout");
	
		
	public AccountPage(WebDriver driver)
	{
		this.driver = driver;
		elementUtil = new ElementUtility(this.driver);		
	}
	
	public String getAccountPageTitle()
	{
		String title = elementUtil.getPageTitle(Constants.ACCOUNT_PAGE_TITLE,Constants.DEFAULT_TIME_OUT);
		System.out.println(title);
		return title;
	}
	
   public boolean getAccountPageURL()
   {
	   return elementUtil.waitForURL("route=account",Constants.DEFAULT_TIME_OUT );
   }
   
   public String getAccountPageLogo()
   {
	   return elementUtil.getTextMethod(yourStoreLogo);
   }
	
   public List<String> getAccSecList()
   {
	   List<String> accSecListTexts = new ArrayList<String>();
	   List<WebElement> sectionElements = elementUtil.waitForVisibilityOfElements(acctSectionsHeaders, Constants.DEFAULT_TIME_OUT);
	   for(WebElement e : sectionElements)
	   {
		   accSecListTexts.add(e.getText());
		  
	   }
	   return accSecListTexts;
	   
   }
   
   public boolean isLogoutLinkExists()
   {
	 return elementUtil.doIsDisplayed(logoutLink);
   }
   
   public void logoutAccPage()
   {
	   if(isLogoutLinkExists())
	   {
		   elementUtil.doClick(logoutLink);
	   }
   }
   
	public searchResultPage doSearch(String productName)
	{
		System.out.println("Searching the product: "+productName);
		elementUtil.doSendKeys(searchText, productName);
		elementUtil.doClick(searchButton);
		return new searchResultPage(driver);
		
		
	}
}
