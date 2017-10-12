package appiumTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Calculator 
{
	
	 private AppiumDriver<MobileElement> driver;
	
		@BeforeClass
		public void calculatorSetUp() throws InterruptedException, MalformedURLException, InterruptedException
			{
				
				System.out.println("*****************************************************");
				System.out.println("Setting up Calculator capabilities");		
				
				DesiredCapabilities cap = DesiredCapabilities.android();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
//				cap.setCapability(MobileCapabilityType.UDID,"A7019FA63B2D7A43");
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		    	cap.setCapability("appPackage","com.miui.calculator");
		    	cap.setCapability("appActivity","com.miui.calculator.cal.NormalCalculatorActivity");
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME,Platform.ANDROID);
				cap.setCapability(MobileCapabilityType.VERSION,"6.0.1MMB29M");

				URL url= new URL("http://127.0.0.1:4723/wd/hub");
				driver = new AndroidDriver<MobileElement>(url,cap);	
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
		
		@Test(description="Addition of multiple numbers")
		public void additionActivity() throws InterruptedException
		{	
			String Firstnum, Secondnum;
			Firstnum = JOptionPane.showInputDialog ( "Enter first integer" ); 			 
			Secondnum = JOptionPane.showInputDialog ( "Enter second interger" );
			
			int a = Integer.parseInt(Firstnum);
			int b = Integer.parseInt(Secondnum);                 // resource-id contains button value from 0-9
			
			MobileElement firstnumber = driver.findElement(By.xpath("//*[contains(@resource-id,'btn_"+a+"')]"));
			MobileElement secondnumber = driver.findElement(By.xpath("//*[contains(@resource-id,'btn_"+b+"')]"));
			MobileElement plus = driver.findElement(By.id("com.miui.calculator:id/btn_plus"));
			MobileElement equal = driver.findElementByClassName("android.view.View");
					
					firstnumber.click();
					plus.click();
					secondnumber.click();
					equal.click();
					Thread.sleep(2000);
					
			List<MobileElement> results = driver.findElementsByClassName("android.widget.TextView");			
			//5th index will show us the result
			JOptionPane.showMessageDialog (null, "The calculated sum is " + results.get(4).getText(),"Results", JOptionPane.PLAIN_MESSAGE);		
		}	

		@AfterTest
		public void TearDown()
		{
			driver.quit();	
			
		}
}
