package appiumTest;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import appiumEnv.Appium_Env;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class BrightnessControl extends Appium_Env
{
/*	private AppiumDriver<MobileElement> driver;
	 
	@BeforeClass
	public void setup() throws MalformedURLException
	{
		System.out.println("*****************************************************");
		System.out.println("Setting BrightnessControl capabilities");		
		
		DesiredCapabilities cap = DesiredCapabilities.android();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.curvefish.widgets.brightnesslevel");
    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.curvefish.widgets.brightnesslevel.BrightnessSelect");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
		cap.setCapability(MobileCapabilityType.VERSION,"6.0.1MMB29M");

		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
*/	
	@SuppressWarnings("unchecked")
	@Test
	public void brightnessControl() throws InterruptedException, NullPointerException
	{	
		try
		{
			MobileElement lvl = ((FindsByAndroidUIAutomator<MobileElement>) driver).findElementByAndroidUIAutomator(String.format("new UiSelector().text(\"%s\")","Select brightness level"));
			MobileElement currValue = driver.findElementById("com.curvefish.widgets.brightnesslevel:id/tvCurrentValue");
			MobileElement close = driver.findElementById("com.curvefish.widgets.brightnesslevel:id/cbCloseOnSelect");
			MobileElement seekbar = driver.findElementByClassName("android.widget.SeekBar");

						  int startX = seekbar.getLocation().getX();
						  int startY = seekbar.getLocation().getY();
						  int endX = (startX+seekbar.getSize().getWidth());
//						  int end50X = (int) (startX+((seekbar.getSize().getWidth())*0.51));
						  
						  System.out.println("Heading :"+lvl.getText()+"\nCurent percentage :"+currValue.getText()+"\nFooter :"+close.getText());
						  TouchAction action = new TouchAction(driver);						  			 
						  			  action.longPress(startX, startY).moveTo(endX, startY).release().perform();						  				  			  		 
			

			System.out.println("Asserting contents");
			Assert.assertEquals(currValue.getText(), "100%");
			System.out.println("Heading :"+lvl.getText()+"\nCurent percentage :"+currValue.getText()+"\nFooter :"+close.getText());
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
	}
*/
}

