package com.qa.opencart.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic - 100:design a login page for demo open cart application..")
@Story("US - 101: login page features with title, forgot Password, login and footerlinks tests")
public class LoginPageTest extends BaseTest{

	@Description("Login page title test.....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println(title);
		Assert.assertEquals(title,Constants.LOGIN_PAGE_TITLE );
	}
	
	@Description("Forgot password Test.....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPassLinkTest()
	{
		Assert.assertTrue(loginPage.forgotPasswordLinkPresent());
	}
	
	@Description("Login test.....")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest()
	{
		loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim() );
	}
	
	@Description("Footer Links test....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void footerLinksTest()
	{
		List<String> linkTexts = loginPage.getFooterLinks();
		System.out.println(linkTexts.size());
		Assert.assertEquals(linkTexts.size(),Constants.COUNT_FOOTER_LINKS);
		Assert.assertTrue(linkTexts.contains("About Us"));
		
	}
}
