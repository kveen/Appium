//No 3 - KitchenSink: Textfield-Events
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class iOS_Example3
{
	public AppiumDriver driver = null;
	public Properties prop=new Properties();
	public static String deviceversion=iOS_Common.getDeviceVersion();
	public static String deviceudid=iOS_Common.getDeviceUDID();
	public static String devicename=iOS_Common.getDeviceName();
	public static WebElement getItem(AppiumDriver wd, String itemname)
	{
		WebElement elemname=null;
		WebElement table=wd.findElement(By.className("UIATableView"));
		List<WebElement> rows=table.findElements(By.className("UIATableCell"));
		int rowsize=rows.size();
		System.out.println(rowsize);
		for (int i=0;i< rowsize;i++)
		{
			String item=rows.get(i).getAttribute("name");
			if(!rows.get(i).isDisplayed())
			{
				iOS_Common.scroll(wd, "up");
				iOS_Common.wait(300);
			}
			if(item.equalsIgnoreCase(itemname))
			{
				System.out.println("Found an item: "+item);
				elemname=rows.get(i);
				iOS_Common.wait(100);
			}
		}
		return elemname;
	}
	
	@BeforeMethod
	public void setUp() throws Exception 
	{
		File appDir = new File("/Users/dhirendra.jha/Projects/TitaniumApp/KitchenSink/build/iphone/build/Debug-iphonesimulator");
        File app = new File(appDir, "KitchenSink.app");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("platformName", "iOS");
        if(deviceversion.isEmpty())
        {
        	capabilities.setCapability("platformVersion", "7.1");
        }
        else
        {
        	capabilities.setCapability("platformVersion", deviceversion);
        	System.out.println(deviceversion);
        }
        if(devicename.isEmpty())
        {
        	capabilities.setCapability("deviceName", "iPhone Simulator");
        }
        else
        {
        	capabilities.setCapability("deviceName", devicename);
        	System.out.println(devicename);
        }
        if(deviceudid.isEmpty())
        {
        }
        else
        {
        	capabilities.setCapability("udid", deviceudid);
        	System.out.println(deviceudid);
        }
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("bundleId", "com.appcelerator.kitchensink");
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        try{
    		prop.load(new FileInputStream("KS_iOS.properties"));
    	}catch(IOException ex)
    	{ex.printStackTrace();}
		
	}
	@AfterMethod
	public void tearDown() throws Exception 
	{
		driver.quit();
	}
	@Test
	public void test2() throws InterruptedException 
	{
		driver.findElement(By.xpath(prop.getProperty("Controls"))).click();
		iOS_Common.wait(200);
		if(driver.findElement(By.xpath(prop.getProperty("Tab_Header"))).getText().equals("Controls"))
		{
			System.out.println("Found Controls Tab");
			WebElement item=getItem(driver,"Text Field");
			if(item.getAttribute("name").equalsIgnoreCase("Text Field"))
			{
				item.click();
				iOS_Common.wait(3000);
			}
			item=getItem(driver,"Events");
			if(item.getAttribute("name").equalsIgnoreCase("Events"))
			{
				item.click();
				iOS_Common.wait(3000);
			}
			iOS_Common.scroll(driver, "up");
			iOS_Common.wait(1000);
			//=========
			WebElement textfield=driver.findElement(By.xpath("//UIATextField"));
			if(textfield.isDisplayed())
			{
				System.out.println("Found text field");
				textfield.click();
				iOS_Common.wait(200);
				WebElement keyboard = driver.findElement(By.xpath("//UIAKeyboard"));
				if(keyboard.isDisplayed())
				{
					System.out.println("Keyboard found");
				}
				WebElement textmessage=driver.findElement(By.xpath("//UIAStaticText[1]"));
				System.out.println(textmessage);
				iOS_Common.wait(200);
				
				WebElement button=driver.findElement(By.xpath("//UIAButton[2]"));
				button.click();
				iOS_Common.wait(200);
				System.out.println(textmessage);
				
				textmessage=driver.findElement(By.xpath("//UIAStaticText[1]"));
				System.out.println(textmessage);
				
			}
			for(int i=0;i<5;i++)
			{
				WebElement button=driver.findElement(By.xpath("//UIAButton[3]"));
				button.click();
				System.out.println("Click on Hide/Show button");
				iOS_Common.wait(500);
				textfield=driver.findElement(By.xpath("//UIATextField"));
				if(textfield.isDisplayed())
				{
					System.out.println("Text Field is showing");
				}
				if(!textfield.isDisplayed())
				{
					System.out.println("Text Field is not showing");
				}
			}
			iOS_Common.wait(1000);
		}		
	}

}
