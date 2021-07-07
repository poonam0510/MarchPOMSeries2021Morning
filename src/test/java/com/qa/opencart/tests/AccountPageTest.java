package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.utils.Constants;

public class AccountPageTest extends BaseTest {

	
	
	@BeforeClass
	public void accPageSetUp()
	{
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test
	public void accPageTitleTest()
	{
		
		Assert.assertEquals(accPage.getAccountPageTitle(),Constants.ACCOUNT_PAGE_TITLE);
	}
	
	@Test
	public void accPageLogoHearTest()
	{
		String logoHeader = accPage.getAccountPageLogo();
		Assert.assertEquals(logoHeader, Constants.ACCOUNT_PAGE_LOGOHEADER);
	}
	
	@Test
	public void accPageSecListsTest()
	{
		
		
		List<String> secList = accPage.getAccSecList();
		sftAssert.assertEquals(secList.size(), Constants.ACCOUNT_PAGE_SECTION_LIST_COUNT);
		Assert.assertEquals(secList, Constants.ACCOUNT_PAGE_SECTION_LIST);
		sftAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] productdata()
	{
		return new Object[][] {{"iMac"},
			                   {"MacBook Pro"},
			                   {"MacBook Air"}};
	}
	
	@Test(dataProvider="productdata")
	public void accSearchTest(String productName)
	{
		searchRsltPage = accPage.doSearch(productName);
		Assert.assertTrue(searchRsltPage.getSearchProductListCount()>0 );
		
	}
	
	@DataProvider
	public Object[][] productSelectData() {
		return new Object[][] { 
			{ "MacBook" , "MacBook Pro"},
			{"Apple", "Apple Cinema 30\" "}};
	}
	
	@Test(dataProvider="productSelectData")
	public void selectProductTest(String productName, String selectProductName)
	{
		searchRsltPage=accPage.doSearch(productName);
		productInfoPage = searchRsltPage.selectProduct(selectProductName);
		
		
		
	}
	
	
}
