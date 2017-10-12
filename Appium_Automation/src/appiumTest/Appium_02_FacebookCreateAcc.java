package appiumTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import utility.ScreenShots;

public class Appium_02_FacebookCreateAcc 
{
// instances of appiumTest driver hierarchy	wise 1. RemoteWebDriver > 2. AppiumDriver > 3.AndroidDriver or IOSDriver
	private AppiumDriver<MobileElement> driver;

	@BeforeClass
	public void deviceSetUp() throws InterruptedException, MalformedURLException, InterruptedException
		{
			
			System.out.println("*****************************************************");
			System.out.println("Setting up device and desired capabilities for Chrome");		
			
			DesiredCapabilities cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);			
			cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.android.chrome");
	    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.chromium.chrome.browser.ChromeTabbedActivity");
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
			cap.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.CHROME);
			cap.setCapability(MobileCapabilityType.VERSION,"6.0.1MMB29M");

			URL url= new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver<MobileElement>(url,cap);	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	
	@Test
	public void facebookSignup() throws IOException, InterruptedException
	{
		ScreenShots ss = new ScreenShots((AndroidDriver<MobileElement>) driver);
		
		driver.navigate().to("http://www.facebook.com");
		System.out.println("Title "+driver.getTitle());	
		ss.takeScreenShots("HomePage");
		
		System.out.println("Clicking on signup");
		driver.findElementById("signup-button").click();
		System.out.println("Nagigated to :"+driver.getTitle());
		ss.takeScreenShots("Create Account");
		if(driver.findElementByXPath("//*[@id='mobile-reg-form']/div[3]/div[1]/span").isDisplayed())
		{System.out.println(driver.findElementByXPath("//*[@id='mobile-reg-form']/div[3]/div[1]/span").getText()+" :Displayed");}
		if(driver.findElementByXPath("//*[@id='mobile-reg-form']/div[3]/div[2]/span").isDisplayed())
		{System.out.println(driver.findElementByXPath("//*[@id='mobile-reg-form']/div[3]/div[2]/span").getText()+" :Displayed");}		
		if(driver.findElementByXPath("//*[@id='login_link']").isDisplayed())
		{System.out.println(driver.findElementByXPath("//*[@id='login_link']").getText()+" :Displayed");}

	}
	
	@Test(dependsOnMethods ={"facebookSignup"})
	public void enterUserDetails() throws InterruptedException, IOException
	{
		ScreenShots ss = new ScreenShots((AndroidDriver<MobileElement>) driver);
		
		System.out.println("Entering 1st Name");
		driver.findElementByXPath("//*[@name='firstname']").sendKeys("Aditya");
		System.out.println("Entering 2nd Name");
		driver.findElementByXPath("//*[@name='lastname']").sendKeys("Roy");
		Thread.sleep(2000);
		
		ss.takeScreenShots("Details Page");
		System.out.println("Clicking on next");
		driver.findElementByXPath("//*[@type='submit']").click();
	
		WebElement ele = driver.findElementByXPath("//*[@id='mobile-reg-form']/div[4]/div[1]/span");
		System.out.println("Asserting :"+ele.getText());
		Assert.assertEquals(ele.getText(), "Enter your phone number or email address", "Phone text not displayed");
		ss.takeScreenShots("Next Screen");
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

/** for device back button or home button use this
  		((AndroidDriver<MobileElement>) driver).pressKeyCode(AndroidKeyCode.BACK);
		((AndroidDriver<MobileElement>) driver).pressKeyCode(AndroidKeyCode.HOME);
**/

