package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.searchResultPage;

public class BaseTest {

	  public DriverFactory df;
	    public Properties prop;
	    public WebDriver driver;
	    public LoginPage loginPage;
	    public AccountPage accPage;
	    public SoftAssert sftAssert ;
	    public searchResultPage searchRsltPage;
	    public ProductInfoPage productInfoPage;
	    public RegistrationPage registerPage;
	   

	    @Parameters("browser")
	    @BeforeTest
	    public void setUp(String browserName) {
	        sftAssert = new SoftAssert();
	        df = new DriverFactory();
	        prop = this.df.init_prop();
	        if(browserName != null)
	        {
	        	prop.setProperty("browser", browserName);
	        }
	        driver = this.df.init_driver(this.prop);
	        loginPage = new LoginPage(this.driver);

	    }

	    @AfterTest
	    public void tearDown() {
	        this.driver.quit();
	    }
}
