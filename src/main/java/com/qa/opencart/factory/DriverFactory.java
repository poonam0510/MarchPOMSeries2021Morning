package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;



import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
		
	/**
	 * This method returns driver
	 * @param browserName
	 * @return
	 */
	public WebDriver init_driver(Properties prop)
	{
		String browser = prop.getProperty("browser").trim();
		System.out.println("browser name is: "+browser);
		
		highlight = prop.getProperty("highlight");
		optionManager = new OptionsManager(prop);
		
		switch (browser) {
		case "chrome": WebDriverManager.chromedriver().setup();
		              if(Boolean.parseBoolean(prop.getProperty("remote")))
		              {
		            	  init_remoteDriver("chrome");
		              }
		              else {
		              tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
		              }
					break;
		case "firefox": WebDriverManager.firefoxdriver().setup();
		                if(Boolean.parseBoolean(prop.getProperty("remote")))
                       {
                     	  init_remoteDriver("firefox");
                       }
                      else {
                        tlDriver.set(new FirefoxDriver(optionManager.getFirefoxOptions()));
                      }
		
		               break;
		case "safari": 
			          //driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
				       break;

		default:  System.out.println("please pass the right browser name..."+browser);
			break;
		}
		
		getWebDriver().manage().deleteAllCookies();
		getWebDriver().manage().window().maximize();
		getWebDriver().get(prop.getProperty("url").trim());

		return getWebDriver();
	}
	
	private void init_remoteDriver(String browserName) {
		System.out.println("Running test on remote with browser: "+browserName);
		if(browserName.equals("chrome"))
		{
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, optionManager.getChromeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap ));
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			}
		}
		else if(browserName.equals("firefox"))
		{
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),cap ));
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			}
		}
		
	}

	public static synchronized WebDriver getWebDriver()
	{
		return tlDriver.get();
	}
	
	
	/**
	 * This method initialize the properties from config.properties file.
	 * @return
	 */
	//mvn clean install -Denv
	public Properties init_prop()
	{
		FileInputStream inputStr=null;
		
		prop = new Properties();
		
		String env = System.getProperty("env");
		if(env==null)
		{
			System.out.println("Running on production environment: ");
			try {
				 inputStr = new FileInputStream("./src/test/resources/config/config.properties");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Running on Environment: "+env);
			try {
			switch (env) {
			case "qa": 	inputStr = new FileInputStream("./src/test/resources/config/qa.config.properties");
						break;
				
			case "dev": inputStr = new FileInputStream("./src/test/resources/config/dev.config.properties");
						break;

			case "stage": inputStr = new FileInputStream("./src/test/resources/config/stage.config.properties");
				          break;
			  default:
				         break;
			          }
			}catch(FileNotFoundException e) {
				
			}
		}
			try {
				prop.load(inputStr);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		
		
		return prop;
	}
	
	
	/**
	 * Take Screenshot method
	 * 
	 */
	public String getScreenshot() {
		File srcFile = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
}
