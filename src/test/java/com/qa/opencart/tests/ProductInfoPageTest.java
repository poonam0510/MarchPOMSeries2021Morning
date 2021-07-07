package com.qa.opencart.tests;

import java.util.Map;

//import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;

//import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 100:design a product info page for demo open cart application..")
public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	
	  @Description("Product page title test.....")
	  
	  @Severity(SeverityLevel.MINOR)
	  
	  @Test
	  
	  public void productInfoHeaderTest() {
		  searchRsltPage =  accPage.doSearch("MacBook");
		  productInfoPage =  searchRsltPage.selectProduct("MacBook Pro");
	  Assert.assertEquals(productInfoPage.getProductHeaderText(), "MacBook Pro");
	  
	  }
	  
	  @Description("Product image count test.....")
	  @Severity(SeverityLevel.MINOR)
	  @Test
	  public void productImagesTest()
	  { searchRsltPage =   accPage.doSearch("iMac"); productInfoPage = 	  searchRsltPage.selectProduct("iMac");
	  Assert.assertEquals(productInfoPage.getProductImageCount(),Constants.IMAC_IMAGE_COUNT); 
	  }
	  
	  
	  @Description("Product meta data test.....")
	  
	  @Severity(SeverityLevel.MINOR)
	  
	  @Test 
	  public void productInfoTest() 
	  { 
	  searchRsltPage =   accPage.doSearch("MacBook"); 
	  productInfoPage =  searchRsltPage.selectProduct("MacBook Pro");
	  Map<String, String>  productInfoMap = productInfoPage.getProductInfo();
	  productInfoMap.forEach((k,v)->System.out.println(k+" : "+v));
	  System.out.println("-------------------------------------------------------");
	  System.out.println(productInfoMap.size());
	  
	  sftAssert.assertEquals(productInfoMap.get("Name"),"MacBook Pro");
	  sftAssert.assertEquals(productInfoMap.get("Brand"),"Apple");
	  sftAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
	  
	  sftAssert.assertAll();
	  
	  }
	  
	  @Description("Check success message after adding to the cart test.....")
	  
	  @Severity(SeverityLevel.MINOR)
	  
	  @Test public void checkSuccessMessageTest()
	  { searchRsltPage =  accPage.doSearch("iMac"); 
	  productInfoPage =  searchRsltPage.selectProduct("iMac");
	  Assert.assertTrue(productInfoPage.checkSuccessMessage("1"));
	  
	  }
	 

	@Description("Check empty cart message after adding 0 quantity in the cart")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void checkEmptyCartMessageTest() {
		searchRsltPage = accPage.doSearch("iMac");
		productInfoPage = searchRsltPage.selectProduct("iMac");
		Assert.assertTrue(productInfoPage.emptyCartCheck());

	}

}
