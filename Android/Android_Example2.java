//Click on a list items under tab
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;


public class Android_Example2 
{
	public AppiumDriver driver = null;
	public Properties prop=new Properties();
	public static ArrayList<String> strState=new ArrayList<String>();
	public int i=0,j=0;
	public static List<WebElement> tab2;
	public static String tabName="Base UI";
	public static int apilevel=Integer.parseInt(Android_Common.getDeviceAPILevel().trim());
	public static String deviceversion=Android_Common.getDeviceVersion();
	public static String devicemodel=Android_Common.getDeviceModel();
	public static String deviceserial=Android_Common.getDeviceSerial();
	public static String devicetype=Android_Common.getDeviceType();
	
	@BeforeClass
	public void setUp() throws Exception 
	{
		try{
			prop.load(new FileInputStream("KS_Android.properties"));
		}catch(IOException ex){ex.printStackTrace();}
		File appDir = new File("/Users/dhirendra.jha/Downloads/");
        File app = new File(appDir, "KitchenSink.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if(apilevel<17)
        {
        	capabilities.setCapability("automationName", "Selendroid");
        	System.out.println("In Selendroid Mode....");
        }
        else
        {
        	capabilities.setCapability("automationName", "Appium");
        	System.out.println("In default Appium Mode....");
        }
        capabilities.setCapability("platformVersion", deviceversion);
        capabilities.setCapability("deviceName", devicemodel);
        capabilities.setCapability("udid", deviceserial);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.appcelerator.kitchensink");
        capabilities.setCapability("appWaitActivity", "org.appcelerator.titanium.TiActivity");
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		
	}
	@AfterClass
	public void tearDown() throws Exception 
	{
		driver.quit();
	}
	@Test
	public void test1() throws InterruptedException 
	{
		Android_Common.ClickKSTab(driver, tabName);
		Android_Common.NavigateListOptions(driver);
	}
}
