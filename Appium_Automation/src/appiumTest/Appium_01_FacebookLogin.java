package appiumTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Appium_01_FacebookLogin 
{
// instances of appiumTest driver hierarchy	wise 1. RemoteWebDriver > 2. AppiumDriver > 3.AndroidDriver or IOSDriver
	private AppiumDriver<MobileElement> driver;

	@BeforeClass
	public void deviceSetUp() throws InterruptedException, MalformedURLException, InterruptedException
		{
			
			System.out.println("*****************************************************");
			System.out.println("Setting up device and desired capabilities for Chrome");
//   Creating object of URL class and specify the appiumTest server address			
			URL url= new URL("http://127.0.0.1:4723/wd/hub");
			
//   Setting up all desired capabilities
			DesiredCapabilities cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
//			cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,"E:\\chromedriver.exe");
	    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.android.chrome");
	    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.chromium.chrome.browser.ChromeTabbedActivity");
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
			cap.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
			cap.setCapability(MobileCapabilityType.VERSION,"6.0.1MMB29M");

//   Anotherway to set capabilities
//			cap.setCapability("appPackage","com.android.chrome");
//			cap.setCapability("appActivity","com.google.android.apps.chrome.Main");
			
			driver = new AndroidDriver<MobileElement>(url,cap);	
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	
	@Test
	public void facebookLoginPageCheck()
	{
		driver.navigate().to("http://www.facebook.com");
		System.out.println("Title "+driver.getTitle());
		if(driver.getTitle().contains("Facebook"))
		{
			System.out.println(driver.getTitle()+" opened successfully");
		}
		else
		{
			System.out.println("Facebook page failed to load");
		}
		try
		{
			if(driver.findElement(By.name("email")).isDisplayed())
			{
				System.out.println(driver.findElement(By.name("email")).getAttribute("placeholder")+": Displayed");
				driver.findElement(By.name("email")).sendKeys("abc@gmail.com");
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		try
		{
			if(driver.findElementById("u_0_2").isDisplayed())
			{
				System.out.println(driver.findElementById("u_0_2").getAttribute("placeholder")+": Displayed");
				driver.findElementById("u_0_2").sendKeys("abc@gmail.com");
			}
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		System.out.println("Clicking on :"+driver.findElementById("u_0_6").getText());
		driver.findElementById("u_0_6").click();
		
		
// if you wanna know the context name use below this
		System.out.println("Current context in use :"+driver.getContext());
// to know about context handles ( Native or Webview )		
		System.out.println("Current context handles :"+driver.getContextHandles());
// to list all driver capabilities for the device under test
		System.out.println("Current capabilities :"+driver.getCapabilities().toString());
	}
		
	@AfterClass
	public void tearDown() throws Exception
		{
			if(driver!=null)
			{
				System.out.println("******************************************************");
				System.out.println("Destroying Test Environment");
				driver.quit();
			}
		}
}

/** Use this below part for native app only works, not for webview
driver.findElementByAccessibilityId("id goes here");
**/

