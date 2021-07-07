package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegistrationPageTest extends BaseTest{

	@BeforeClass
	public void registrationPageSetUp()
	{
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	@Test
	public void userRegistrationPage()
	{
		Assert.assertTrue(registerPage.accountRegistration("poonam","agrawal", "punam12356@gmail.com","0987654321","1234@gmail.com","yes"));
	}
	
	
	
	
	
}
