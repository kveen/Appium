//Get all the tabs name
import io.appium.java_client.AppiumDriver;
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
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Android_Example1 
{
	public AppiumDriver driver = null;
	public Properties prop=new Properties();
	public static ArrayList<String> strState=new ArrayList<String>();
	public int i=0,j=0;
	public static List<WebElement> tab2;
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
	public void getTabsName() throws InterruptedException 
	{
		if(apilevel==10 && !devicemodel.equalsIgnoreCase("google_sdk"))
		{
			List<WebElement> view1=driver.findElements(By.className("android.widget.HorizontalScrollView"));
			WebElement gelem=view1.get(0);
			gelem.click();
			Android_Common.wait(200);
			List<WebElement> TextView1 = driver.findElements(By.className("android.widget.TextView"));
			Android_Common.wait(300);
			for(int i=0;i<TextView1.size();i++)
			{
				if(!driver.findElements( By.className("android.widget.TextView") ).isEmpty())
				{
					Android_Common.wait(300);
					String temp=driver.findElements(By.className("android.widget.TextView")).get(i).getText();
					System.out.println("TabName: "+temp);
				}
				if(i==4){break;}
			}
		}
		if (devicemodel.equalsIgnoreCase("google_sdk") || devicetype.equalsIgnoreCase("emulator"))
		{
			Android_Common.wait(500);
			WebDriverWait wait = new WebDriverWait(driver, 50);
            WebElement element =  wait.until(ExpectedConditions.elementToBeClickable(By.className("android.widget.HorizontalScrollView")));
			List<WebElement> view1=driver.findElements(By.className("android.widget.HorizontalScrollView"));
			WebElement view2=view1.get(0);
			Android_Common.wait(200);
			String widID = ((RemoteWebElement)(view2)).getId();
			List<WebElement> TextView = view2.findElements(By.className("android.widget.TextView"));
			Android_Common.wait(200);
			for(WebElement elem : TextView)
			{
				strState.add(i, elem.getText());
				i++;
			}
			if(i<5)
			{
				Android_Common.scrollNavigation(driver, widID,"Mashups","right");;
				List<WebElement> hview=driver.findElements(By.className("android.widget.HorizontalScrollView"));
				WebElement hview2=hview.get(0);
				Android_Common.wait(200);
				List<WebElement> hTextView = hview2.findElements(By.className("android.widget.TextView"));
				Android_Common.wait(200);
				for(WebElement elem : hTextView)
				{
					if (!strState.contains(elem.getText()))
					{
						strState.add(j, elem.getText());
						j++;
					}
				}
			}
			for (String s : strState) {
			    System.out.println("Tab Name: "+ s);
			}
		}
		if(devicemodel.equalsIgnoreCase("Nexus 7"))
		{
			driver.findElement(By.className("android.widget.Spinner")).click();
			Android_Common.wait(50);
			List<WebElement>tab2=driver.findElements(By.className("android.app.ActionBar$Tab"));
			for (WebElement elem : tab2)
			{
				WebElement elem1 = elem.findElement(By.className("android.widget.TextView"));
				System.out.println("TabName: "+elem1.getText());
			}
			
		}
	}
}
