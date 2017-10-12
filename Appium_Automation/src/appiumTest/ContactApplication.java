package appiumTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import utility.ScreenShots;
import utility.ScrollElement;

public class ContactApplication 
{
	  private AppiumDriver<MobileElement> driver;
		
		@BeforeClass
		public void contactSetUp() throws InterruptedException, MalformedURLException, InterruptedException
			{
				
				System.out.println("*****************************************************");
				System.out.println("Setting up Contact App capabilities");		
				
				DesiredCapabilities cap = DesiredCapabilities.android();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.android.contacts");
		    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.android.contacts.activities.PeopleActivity");
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
				cap.setCapability(MobileCapabilityType.VERSION,"6.0.1MMB29M");

				URL url= new URL("http://127.0.0.1:4723/wd/hub");
				driver = new AndroidDriver<MobileElement>(url,cap);	
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
		
		@BeforeMethod
		public void beforeMethod(Method method)
		    {
		        System.out.println("Executing Method :"+method.getName()); 
		        System.out.println("---------------------------------------");
		    }
		 
		@AfterMethod
		public void afterMethod(Method method)
		    {
		        System.out.println("Closing Method :"+method.getName()); 
			 	System.out.println("---------------------------------------");
		    }
	
		@Test(description="Navigating to contact and call")
		public void navigateContact() throws InterruptedException, IOException
		{
			ScreenShots ss = new ScreenShots((AndroidDriver<MobileElement>) driver);
			ScrollElement se = new ScrollElement((AndroidDriver<MobileElement>) driver);
			try
			{
				MobileElement title = driver.findElement(By.xpath("//android.widget.TextView[@text='Contacts']"));
				System.out.println(title.getText()+" page opened");
				ss.takeScreenShots("Contact List");
			}
			catch(NoSuchElementException|IOException e)
			{
				System.out.println("Contact page fail to open");
				e.printStackTrace();
			}

			MobileElement contactList = driver.findElement(By.id("com.android.contacts:id/contacts_list_view"));			
			String searchContact = JOptionPane.showInputDialog ("Please provide the name you want to call"); 
			
			MobileElement trgtcontact = se.ScrollToElement(contactList, searchContact);
/**			MobileElement trgtcontact = ((AndroidElement) contactList)
					.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
							+ "new UiSelector().text(\""+searchContact+"\"));");
			
**/					
						  System.out.println("Navigating to Taget Contact");
						  ss.takeScreenShots("Target Contact");
						  trgtcontact.click();
						  Thread.sleep(2000);
			MobileElement actionCall = driver.findElementById("com.android.contacts:id/primary_action_view");
						  System.out.println("Call initiated to target contact");	
						  ss.takeScreenShots("Contact details");
						  actionCall.click();
						  Thread.sleep(2000);
			MobileElement actionEnd = driver.findElementById("com.android.incallui:id/endButton");
						  System.out.println("Disconnecting call");
						  ss.takeScreenShots("Calling page");
						  actionEnd.click();
						  Thread.sleep(2000);					  
		}

}
