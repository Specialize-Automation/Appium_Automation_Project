package appiumTest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.android.uiautomator.core.UiObjectNotFoundException;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import utility.ScrollElement;

public class MusicApplication 
{
	//private AppiumDriver<MobileElement> driver;
	  private AndroidDriver<MobileElement> driver;
	
	@BeforeClass
	public void musicappSetUp() throws InterruptedException, MalformedURLException, InterruptedException
		{
			
			System.out.println("*****************************************************");
			System.out.println("Setting up Music App capabilities");		
			
			DesiredCapabilities cap = DesiredCapabilities.android();
			cap.setCapability(MobileCapabilityType.DEVICE_NAME,"539e3ee5");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
	    	cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,"com.miui.player");
	    	cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"com.miui.player.ui.MusicBrowserActivity");
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

	@Test(priority=1,description="verifying if library properly opened")
	public void musicLibrary() throws InterruptedException
	{	
		try
		{
			MobileElement apptitle = driver.findElement(By.xpath("//*[@text='My music']"));
			if(apptitle.isDisplayed())
			{
				System.out.println("Music app opened successfully");
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Music app failed to open");
		}

		MobileElement localsongs = driver.findElement(By.xpath("//*[@text='Local songs']"));
		MobileElement totalsongs = driver.findElement(By.xpath("//*[contains(@text,'66 songs')]"));
		
		System.out.println("Moving to :"+localsongs.getText());
		System.out.println("Total songs available :"+totalsongs.getText().toString().substring(0, 2));	
		System.out.println("Navigating to music library");
		if(localsongs.isEnabled()){localsongs.click();}else{System.err.println("Not Enabled");}
		
		MobileElement song1 = driver.findElement(MobileBy.AndroidUIAutomator(String.format("new UiSelector().textContains(\"%s\")", "Aashayein")));
		MobileElement song2 = driver.findElementByAndroidUIAutomator(String.format("new UiSelector().text(\"%s\")","all day"));
		System.out.println("song1 :"+song1.getText()+"\n"+"song2 :"+song2.getText());
		song1.click();
		Thread.sleep(10000);
		
	}
	
	@Test(dependsOnMethods={"musicLibrary"},description="Scroll to particular music and play it")
	public void scrollToMusic() throws InterruptedException, UiObjectNotFoundException
	{
//loading the frame where all musics are displaying as vertical list
		MobileElement songslist = driver.findElement(MobileBy.AndroidUIAutomator(
                String.format("new UiSelector().resourceId(\"%s\")", "com.miui.player:id/load_container")));

				Scanner sc = new Scanner(System.in);
				System.out.print("Enter the song name you wanna scroll :");
				String str = sc.nextLine();

		ScrollElement se = new ScrollElement(driver);
		MobileElement item = se.ScrollToElement(songslist, str);       
		System.out.println("Now playing :"+item.getText());
							  
		item.click();
		sc.close();
		Thread.sleep(10000);
		
		((AndroidDriver<MobileElement>) driver).pressKeyCode(AndroidKeyCode.BACK);	
	}
		
	@Test(dependsOnMethods={"scrollToMusic"},description="Listing all music while swiping")
	public void scrolltoFetchAll() throws InterruptedException, UiObjectNotFoundException
	{	
		System.out.println();
		Thread.sleep(8000);
		MobileElement localsongs = driver.findElement(By.xpath("//*[@text='Local songs']"));
		localsongs.click();
	
/** This will scroll down and print all music tracks, duplicates may be there due to swipe action **/		
		boolean found = false;
		Boolean found_result = false;
		ArrayList<String> tracks = new ArrayList<String>(); //array list to hold all tracks
		while (!found_result)
		{
		    List<MobileElement> ele = driver.findElements(By.id("com.miui.player:id/title"));
		    int size=0;
		     size = size+ele.size();
		    for (int i = 0; i < size; i++) 
		    {
		        String s = ele.get(i).getText();
		        tracks.add(s);  //arrray list to hold store all track founds during swipe
		        if (s.contains("05 Naina")) 
		        {
		            found = true;
		            break;
		        }
		    }
			if(!found)
			{
				//find startx,starty, and Endy
		    	driver.swipe(550, 1685, 550, 325, 2500);
		    }
			else
			break;
		}
		
		System.out.println("Total tracks found during swipe :"+tracks.size());
		
		Set<String> hs = new LinkedHashSet<String>();  //LinkHashSet to maintain order and remove the duplicates
		hs.addAll(tracks);
		tracks.clear();
		tracks.addAll(hs);
		tracks.remove(0); //first index[0] is "Offline" only 
		System.out.println("Total Songs available :"+tracks.size());
		for(int i=0 ;i<tracks.size();i++)
		{
			System.out.println("Track "+(i+1)+"name :"+tracks.get(i));
		}
		
		
		((AndroidDriver<MobileElement>) driver).pressKeyCode(AndroidKeyCode.HOME);
	}
}
