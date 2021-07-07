package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtility {
	
	private WebDriver driver;
	private Select sel;
	private JavaScriptUtil jsUtil;
	
	/**
	 * @param driver
	 */
	public ElementUtility(WebDriver driver)
	{
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	/**
	 * @param locator
	 * @return
	 */
	public WebElement getElement(By locator)
	{
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
		
	}
	
	/**
	 * @param locator
	 * @return
	 */
	public List<WebElement> getElements(By locator)
	{
		List<WebElement> elementList = driver.findElements(locator);
		//System.out.println(elementList.size());
		
		return elementList;
	}
	
	/**
	 * @param locator
	 * @param value
	 */
	public void doSendKeys(By locator, String value)
	{
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	/**
	 * @param locator
	 */
	public void doClick(By locator)
	{
		getElement(locator).click();
	}
	
	
	/**
	 * @param locator
	 * @return
	 */
	public String getTextMethod(By locator)
	{
		return getElement(locator).getText().trim();
	}
	
	
	public boolean doIsDisplayed(By locator)
	{
		return getElement(locator).isDisplayed();
	}

	

	
	/**
	 * @param locator
	 */
	public List<String> getLinksTexts(By locator)
	{
		List<WebElement> elementList = getElements(locator);
		List<String> linksTexts = new ArrayList<String>();
		for(WebElement e : elementList)
		{
			String text = e.getText();
			if(!text.isEmpty())
			{
			linksTexts.add(text);	
			}
		}
		return linksTexts;
		
	}
	
	
	/** This method returns the list of value 
	 * of the given attribute of the elements.
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public List<String> getListByAttributes(By locator,String attribute)
	{
		List<WebElement> elements = getElements(locator); 
		List<String> attributeList = new ArrayList<String>();
		for(WebElement e : elements)
		{
			
			
			String attributeText = e.getAttribute(attribute);
			if(attributeText != null)
			{
				if(!attributeText.isEmpty())
				{
			
					attributeList.add(attributeText);
				}
			}
				
			
		}
		
		return attributeList;
	}
	
	/***********  Select based drop down list***************************************************.
	 * 
	 * 
	 * 
	 */
	
	public void doSelectDropDownByIndex(By locator,int index) {
		sel = new Select(getElement(locator));
		sel.selectByIndex(index);
	}

	public void doSelectDropDownByValue(By locator,String value) {
		sel = new Select(getElement(locator));
		sel.selectByValue(value);
		
	}
	
	public void doSelectDropDownByVisibleText(By locator,String text) {
		sel = new Select(getElement(locator));
		sel.selectByVisibleText(text);
		
	}
	
	
	public  void printDropDownOptions(By locator)
	{
		getDropDownListTexts(locator).stream().forEach(e -> System.out.println(e));
		
	}
	
	public  List<String> getDropDownListTexts(By locator)
	{
		sel = new Select(getElement(locator));
		List<WebElement> listOptionsList = sel.getOptions();
		List<String> listOptionsText = new ArrayList<String>();
		for(WebElement e : listOptionsList)
		{
			listOptionsText.add(e.getText());
		}
		return listOptionsText;
	}
	
	public void doSelectValueFromDropDown(By locator,String value)
	{
		sel = new Select(getElement(locator));
		List<WebElement> optionsList = sel.getOptions();
		for(WebElement e : optionsList )
		{
			if(e.getText().equals(value))
			{
				e.click();
				break;
			}
		}
	}
	
	public void doSelectValuesFromDropDown(By locator,String value)
	{
		List<WebElement> optionsList = getElements(locator);
		for(WebElement e : optionsList)
		{
			if(e.getText().equals(value)) {
				e.click();
				break;
			}
		}
	}
	
	
	/*********************************************************
	 * Actions Utils
	 *********************************/
	public void handleTwoLevelMenu(By parentLocator, By childLocator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		doClick(childLocator);
	}
	
	public void handleThreeLevelMenu(By parentLocator1, By parentLocator2, By childLocator)
			throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator1)).perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(parentLocator2)).perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();
	}
	
	
	/*********************************** Wait Utils ********************************************************/
	/**An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	
	public  WebElement getPresenceOfelementLocated(By locator,int timeout)
	{
          WebDriverWait wait = new WebDriverWait(driver,timeout);
      	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}
	
	/**
	 * 
	 * @param locator
	 * @param timeOut(secs)
	 * @param intervalTime  (milli secs)
	 * @return
	 */
	public WebElement doPresenceOfElementLocated(By locator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(intervalTime))
				.ignoring(StaleElementReferenceException.class,NoSuchElementException.class);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	public void waitForFrameWithFluentWait(By locator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(intervalTime))
				.ignoring(NoSuchFrameException.class);

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	
	/**An expectation for checking that an element, known to be present on the DOM of a page, isvisible. 
	 * Visibility means that the element is not only displayed but also has a height andwidth that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	public  WebElement doVisibilityOfelementLocated(By locator,int timeout)
	{
          WebDriverWait wait = new WebDriverWait(driver,timeout);
		
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
		
	}
	
	/**An expectation for checking that an element, known to be present on the DOM of a page, isvisible. 
	 * Visibility means that the element is not only displayed but also has a height andwidth that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return WebElement
	 */
	public List<WebElement> waitForVisibilityOfElements(By locator, int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	
	
	public Alert waitForAlert(int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver,10);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	public void acceptAlert(int timeout)
	{
		waitForAlert(timeout).accept();
	}
	
	public void dismissAlert(int timeout)
	{
		waitForAlert(timeout).dismiss();
	}
	
	public String getAlertText(int timeout)
	{
		return waitForAlert(timeout).getText();
	}
	
	public boolean waitForURL(String urlFraction,int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
		return wait.until(ExpectedConditions.urlContains(urlFraction));
	}
	
	public WebElement waitForElementToBeClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public void clickWhenReady(By locator, int timeOut) {
		waitForElementToBeClickable(locator, timeOut).click();
	}
	
	public String getPageTitle(String titleFraction, int timeOut) {
		waitForTitle(titleFraction, timeOut);
		return driver.getTitle();
	}
	
	public boolean waitForTitle(String title,int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeOut);
	    return wait.until(ExpectedConditions.titleContains(title));
		
	}
	
	
	public boolean waitForTitleIs(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.titleIs(titleVal));
	}
	
	public void waitForFrameElement(String IDORNAME, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDORNAME));
	}
	
	public Alert waitForAlertWithFluentWait(By locator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofMillis(intervalTime))
				.ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	
}
