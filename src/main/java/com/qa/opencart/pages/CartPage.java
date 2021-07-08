package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class CartPage {
	
	private By cartButton = By.id("cart");
	public CartPage()
	{
		System.out.println("Cart page constructor");
	}

	public void addToCart()
	{
		System.out.println("add to cart ....."+cartButton);
	}
}
