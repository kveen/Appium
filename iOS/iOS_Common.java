import io.appium.java_client.AppiumDriver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


public class iOS_Common 
{
	public static Properties prop=new Properties();
	public static ArrayList<String> strState=new ArrayList<String>();
	public static int i=0,j=0;
	public static void wait(int n)
	{
		long t0,t1;
		t0=System.currentTimeMillis();
		do{
			t1=System.currentTimeMillis();
		}
		while(t1-t0<n);
	}
	public static String executeCommand(String command) {
		 
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
 
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
 
	}
	public static String getDeviceUDID()
	{
		try{
			prop.load(new FileInputStream("KS_iOS.properties"));
			}
		catch(IOException ex){ex.printStackTrace();
			}
		String nodecommand=prop.getProperty("NODE_PATH");
		String scriptpath=prop.getProperty("SCRIPT_PATH");
		String command = executeCommand(nodecommand+" "+ scriptpath +"GetDeviceUDID.js");
		return command.trim();
	}
	public static String getDeviceName()
	{
		try{
			prop.load(new FileInputStream("KS_iOS.properties"));
			}
		catch(IOException ex){ex.printStackTrace();
			}
		String nodecommand=prop.getProperty("NODE_PATH");
		String scriptpath=prop.getProperty("SCRIPT_PATH");
		String command = executeCommand(nodecommand+" "+ scriptpath +"GetDeviceName.js");
		return command.trim();
	}
	public static String getDeviceVersion()
	{
		try{
			prop.load(new FileInputStream("KS_iOS.properties"));
			}
		catch(IOException ex){ex.printStackTrace();
			}
		String nodecommand=prop.getProperty("NODE_PATH");
		String scriptpath=prop.getProperty("SCRIPT_PATH");
		String command = executeCommand(nodecommand+" "+ scriptpath +"GetDeviceVersion.js");
		return command.trim();
	}
	public static void main(String[] args) 
	{
		String udid=getDeviceVersion();
		System.out.println(udid);
	}
	/**public static void scroll(AppiumDriver wd,WebElement element){
        JavascriptExecutor js= (JavascriptExecutor) wd;
        js.executeScript("mobile: scrollTo", element);
    }**/
	public static void scroll(AppiumDriver wd,String direction){       
		JavascriptExecutor js= (JavascriptExecutor) wd;
		Map<String, String>scrollMap =new HashMap<String, String>();
		scrollMap.put("direction", direction);  
		js.executeScript("mobile: scroll", scrollMap);  
	}
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
}
