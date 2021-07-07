package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtility;

import io.qameta.allure.Step;

public class LoginPage {

	WebDriver driver;
	ElementUtility elementUtil;
	
	//1. By locators
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By yourStoreLogoLink = By.linkText("Your Store");
	private By footerLinks = By.xpath("//footer//div[@class='row']//a");
	private By registerLink = By.linkText("Register");
	//private By loginError = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	//2. Constructor
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		elementUtil = new ElementUtility(driver);
	}
	
	
	//3. Page Actions
	@Step("getting login page title..")
	public String getLoginPageTitle()
	{
		return elementUtil.getPageTitle();
	}
	
	@Step("chechking forgot pwd link present..")
	public boolean forgotPasswordLinkPresent()
	{
		return elementUtil.getElement(forgotPwdLink).isDisplayed();
		
	}
	
	@Step("login to app with username:{0} and password:{1}")
	public AccountPage doLogin(String un, String pwd)
	{
		elementUtil.getPresenceOfelementLocated(username,
				Constants.DEFAULT_TIME_OUT).sendKeys(un);
		elementUtil.doSendKeys(password,pwd);
		elementUtil.getElement(loginButton).click();
		return new AccountPage(driver);
		
		
	}
	@Step("getting footer linkd for the test")
	public List<String> getFooterLinks() {
		return elementUtil.getLinksTexts(footerLinks);
	}
	
	public boolean yourStoreLogoDisplayed()
	{
		return elementUtil.doVisibilityOfelementLocated(yourStoreLogoLink, 5).isDisplayed();
	}
	
	
	/**
	 * Navigate to Register page
	 * @return 
	 * 
	 */
	@Step("navigating to the Registration page")
	public RegistrationPage navigateToRegisterPage()
	{
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}
	
}
