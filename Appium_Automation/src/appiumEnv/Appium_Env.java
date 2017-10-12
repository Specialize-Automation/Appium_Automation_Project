/**if you wanna install app from a location then 
  String path = "D://appium//demoapp.apk"
  File file = new File(path);
  cap.setCapability(MobileCapabilityType.APP, file.getAbsolutePath());
 
 or*
 
   File appDir = new File("D:\\aditya\\appfiles"); 
   File app = new File(appDir, "Facebook.apk");
   cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
   cap.setCapability(MobileCapabilityType.NO_RESET,"true");   //app wont get install everytime
   cap.setCapability(MobileCapabilityType.FULL_RESET,"false");  //app get installed and uninstalled everytime
 **/


package appiumEnv;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Appium_Env 
{
	protected AppiumDriver<MobileElement> driver;
	
	@Parameters({"device","apppackage","activity","version","appiumServer"})
	@BeforeClass
	public void deviceSetUp(String device, String apppackage, String activity, String version, String appiumServer) throws InterruptedException, MalformedURLException, InterruptedException
		{
			
			System.out.println("*****************************************************");
			System.out.println("Setting up device and desired capabilities");		
			
			DesiredCapabilities cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME,device);	
			cap.setCapability(MobileCapabilityType.UDID,device);
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
	    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,apppackage);
	    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,activity);
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
			cap.setCapability(MobileCapabilityType.BROWSER_NAME,BrowserType.ANDROID);
			cap.setCapability(MobileCapabilityType.VERSION,version);
/*			cap.setCapability(MobileCapabilityType.NO_RESET,"true");
			cap.setCapability(MobileCapabilityType.FULL_RESET,"false");*/

			URL url= new URL(appiumServer);
			driver = new AndroidDriver<MobileElement>(url,cap);	
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
/*			driver.closeApp();
			driver.installApp(appPath);
			driver.launchApp();
			driver.resetApp();*/
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





