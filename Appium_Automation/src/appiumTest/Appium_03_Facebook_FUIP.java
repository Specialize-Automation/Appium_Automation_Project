package appiumTest;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import appiumEnv.Appium_Env;
import io.appium.java_client.MobileElement;

public class Appium_03_Facebook_FUIP extends Appium_Env
{	
/*	@BeforeClass
	public void deviceSetUp() throws InterruptedException, MalformedURLException, InterruptedException
		{
			
			System.out.println("*****************************************************");
			System.out.println("Setting up device and desired capabilities");		
			
			DesiredCapabilities cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");			
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
	    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.android.browser");
	    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.android.browser.BrowserActivity");
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
			cap.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.ANDROID);
			cap.setCapability(MobileCapabilityType.VERSION,"6.0");

			URL url= new URL("http://127.0.0.1:4723/wd/hub");
			driver = new AndroidDriver<MobileElement>(url,cap);	
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
*/	
	@Test(description="forgot password notification check")
	public void fuipCheck() throws InterruptedException
	{
		driver.navigate().to("http://www.facebook.com");
		Thread.sleep(20000);
		try
		{
				MobileElement fuip = driver.findElement(By.xpath("//*[@content-desc='Forgotten password?']"));
					  System.out.println("Clicking on:"+fuip.getAttribute("name"));
					  fuip.click();
				MobileElement fuiptxt = driver.findElement(By.xpath("//*[contains(@content-desc,'Please enter your email')]"));
					  System.out.println("Displaying :"+fuiptxt.getAttribute("name"));
				MobileElement input = driver.findElement(By.xpath("//*[@content-desc='Email address or phone number']"));
					  System.out.println("Displaying :"+input.getAttribute("name"));
					  System.out.println("Passing blank value");
				MobileElement search = driver.findElement(By.xpath("//*[contains(@content-desc,'Search')]"));
					  System.out.println("Clicking on :"+search.getAttribute("name"));
					  search.click();
					  Thread.sleep(3000);
				MobileElement notifn = driver.findElement(By.xpath("//*[contains(@content-desc,'Fill in at least one')]"));			  
					  System.err.println("1st Notification :"+notifn.getAttribute("name"));
					  System.out.println("Passing invalid phone");
					  Thread.sleep(3000);
					  input.sendKeys("23");
					  search.click();
				MobileElement notifn2 = driver.findElement(By.xpath("//*[contains(@content-desc,'Your search did not')]"));
					  System.err.println("2nd Notification :"+notifn2.getAttribute("name"));
	}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
	}
/*	
	@AfterClass
	public void tearDown() throws Exception
	{
		if(driver!=null)
		{
			System.out.println("******************************************************");
			System.out.println("Destroying Test Environment");
			driver.quit();
		}
	}*/
}


