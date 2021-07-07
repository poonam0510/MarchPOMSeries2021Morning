package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtility;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtility elementUtil;
	
	
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By addToCartButton = By.id("button-cart");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");	
	private By quantity = By.id("input-quantity");
	private By successMesg = By.cssSelector("div.alert.alert-success.alert-dismissible");
	private Map<String, String> productInfoDataMap;
	private By cartTotal = By.cssSelector("span#cart-total");
	private By cartButton = By.cssSelector("button.btn.btn-inverse.btn-block.btn-lg.dropdown-toggle");
	private By emptyShoppingCartMessage = By.cssSelector("p.text-center");
	
	public ProductInfoPage(WebDriver driver)
	{
		this.driver = driver;
		elementUtil = new ElementUtility(this.driver);
		
	}
	
	public String getProductHeaderText()
	{
		return elementUtil.getTextMethod(productHeader);
	}
	
	public int getProductImageCount()
	{
		return elementUtil
				.waitForVisibilityOfElements(productImages, 
						Constants.DEFAULT_TIME_OUT).size();
	}

	/**
	 * 
	 * @return
	 */
	public boolean checkSuccessMessage(String qty)
	{
		
		elementUtil.doSendKeys(quantity, qty);
		
		elementUtil.doClick(addToCartButton);
		
		TimeUtil.midTime();
		
		String cartText= elementUtil.doVisibilityOfelementLocated(cartTotal, Constants.DEFAULT_TIME_OUT).getText().trim();
		String[] cartTextMsg = cartText.split("-");
		System.out.println(cartTextMsg[0] +" "+cartTextMsg[1]);
		String[] qt = cartTextMsg[0].split(" ");
		System.out.println(qt[0]);
		WebElement msgElement = elementUtil.doVisibilityOfelementLocated(successMesg, Constants.DEFAULT_TIME_OUT);
		if(msgElement.getText().contains("Success"))
		{
			return true;
		}
		return false;
	}
	
	public boolean emptyCartCheck()
	{
		elementUtil.doSendKeys(quantity, "0");
		elementUtil.doClick(addToCartButton);
		TimeUtil.midTime();
		
		 String cartText= elementUtil.doVisibilityOfelementLocated(cartTotal,
		 Constants.DEFAULT_TIME_OUT).getText().trim(); String[] cartTextMsg =
		 cartText.split("-"); System.out.println(cartTextMsg[0] +" "+cartTextMsg[1]);
		 String[] qt = cartTextMsg[0].split(" ");
		 
		//System.out.println(qt[0]);
		if(qt[0].equals("0"))
		{
			elementUtil.doClick(cartButton);
			String emptyCartText = elementUtil.doVisibilityOfelementLocated(emptyShoppingCartMessage, Constants.DEFAULT_TIME_OUT).getText().trim();
			if(emptyCartText.contains("Your shopping cart is empty!"))
			{
				System.out.println("0 quantity empty cart success message test");
				return true;
			}
		}
		
		return false;
		
	}
	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getProductInfo()
	{
		productInfoDataMap = new LinkedHashMap<String, String>();
		productInfoDataMap.put("Name", getProductHeaderText());
		getProductMetaData();
		getProductPriceData();
		return productInfoDataMap;
	}
	
	
	/**
	 * 
	 */
	private void getProductMetaData()
	{
		
		List<WebElement> listProductMetaDataElements = elementUtil.getElements(productMetaData);
		System.out.println("total product meta data: " + listProductMetaDataElements.size());
		for(WebElement e :listProductMetaDataElements ) {
			String metaData[] = e.getText().split(":");
			String metaKey = metaData[0].trim();
			String metaValue = metaData[1].trim();
			productInfoDataMap.put(metaKey, metaValue);
			
		}
	}
	
	/**
	 * 
	 */
	private void getProductPriceData()
	{
		List<WebElement> priceListElements = elementUtil.getElements(productPriceData);
		System.out.println("total price meta data: "+priceListElements.size());
		String price = priceListElements.get(0).getText().trim();
		String exPrice = priceListElements.get(1).getText().trim();
		productInfoDataMap.put("Price",price );
		productInfoDataMap.put("Ex Tax", exPrice);
	}
}
