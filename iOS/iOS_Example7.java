//Options Dialog
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;

public class iOS_Example7
{
	public AppiumDriver driver = null;
	public Properties prop=new Properties();
	public static String deviceversion=iOS_Common.getDeviceVersion();
	public static String deviceudid=iOS_Common.getDeviceUDID();
	public static String devicename=iOS_Common.getDeviceName();
	
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
		//Navigating items under BaseUI tab"
		driver.findElement(By.xpath(prop.getProperty("BaseUI"))).click();
		iOS_Common.wait(200);
		if(driver.findElement(By.xpath(prop.getProperty("Tab_Header"))).getText().equals("Base UI"))
		{
			System.out.println("Found Base UI Tab");
			WebElement item=iOS_Common.getItem(driver,"Views");
			if(item.getAttribute("name").equalsIgnoreCase("Views"))
			{
				item.click();
				iOS_Common.wait(1000);
			}
			item=iOS_Common.getItem(driver,"Options dialog");
			if(item.getAttribute("name").equalsIgnoreCase("Options dialog"))
			{
				item.click();
				iOS_Common.wait(2000);
			}
		}
	}

}
