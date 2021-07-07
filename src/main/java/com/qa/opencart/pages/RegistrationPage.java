package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtility;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtility elementUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");
	
	private By agreeCheckBox = By.name("agree");
	
	private By continueButton = By.cssSelector("input.btn.btn-primary");
	private By successMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegistrationPage(WebDriver driver)
	{
		this.driver = driver;
		elementUtil = new ElementUtility(this.driver);
		
	}
	
	
	public boolean accountRegistration(String fName, String lName, String emailID,String phone,String pass, String subscribe)
	{
		elementUtil.doSendKeys(firstName, fName);
		elementUtil.doSendKeys(lastName, lName);
		elementUtil.doSendKeys(email, emailID);
		elementUtil.doSendKeys(telephone, phone);
		elementUtil.doSendKeys(password, pass);
		elementUtil.doSendKeys(confirmPassword, pass);
		if(subscribe.equalsIgnoreCase("yes"))
		{
			elementUtil.doClick(subscribeYes);
		}
		else
		{
			elementUtil.doClick(subscribeNo);
		}
		
		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(continueButton);
		
		String msg = elementUtil
				.doVisibilityOfelementLocated(successMsg, 
						Constants.DEFAULT_TIME_OUT).getText();
		
		if(msg.contains(Constants.REGISTER_SUCCESS_MSG))
		{
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
	
	
	
	



  
}
