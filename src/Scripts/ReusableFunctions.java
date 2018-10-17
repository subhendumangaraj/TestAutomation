package Scripts;

//import io.appium.java_client.AppiumDriver;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.LogStatus;
import CommonFunctionLibrary.GlobalData;


public class ReusableFunctions extends FunctionLibrary
{
	/////////////////////////////////////////Click by using ID///////////////////////
	public static String clickbyid(String objects, String description, String testData)  
	{
		try 
		{
			int count = GlobalData.driver.findElements(By.id(objects)).size();
			if (count == 1) 
			{
				GlobalData.driver.findElement(By.id(objects)).click();
				TR.extentTest.log(LogStatus.PASS, description+" is successful");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, description+" is not successful and the count of object is "+count);
				return "false";
			}
			
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Click by using name///////////////////////
	public static String clickbyname(String objects, String description, String testData) 
	{
		try 
		{
			int count = GlobalData.driver.findElements(By.name(objects)).size();
			if (count == 1) 
			{
				GlobalData.driver.findElement(By.name(objects)).click();
				TR.extentTest.log(LogStatus.PASS, description+" is successful");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, description+" is not successful and the count of object is "+count);
				return "false";
			}
			
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Click by using xpath///////////////////////
	public static String clickbyxpath(String objects,String description)
	{
		try
		{
			int count=GlobalData.driver.findElements(By.xpath(objects)).size();
			//System.out.println("count"+count);
			if(count==1)
			{
				GlobalData.driver.findElement(By.xpath(objects)).click();
				TR.extentTest.log(LogStatus.PASS, description+" is successful");
				return "true";
			}else
			{
				TR.extentTest.log(LogStatus.FAIL, description+" is not successful and the count of object is "+count);
				return "false";
			}
		}
		catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Click by using LinkText///////////////////////
	public static String clickbylinktext(String objects, String description, String testData)
	{
		try 
		{
			int count = GlobalData.driver.findElements(By.linkText(objects)).size();
			if (count == 1) {
				GlobalData.driver.findElement(By.linkText(objects)).click();
				TR.extentTest.log(LogStatus.PASS, description+" is successful");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, description+" is not successful and the count of object is "+count);
				return "false";
			}
		}catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Input by using ID///////////////////////
	public static String inputbyid(String objects, String description, String testData)
	{
		try{
			int count = GlobalData.driver.findElements(By.id(objects)).size();
			if (count == 1) {
				GlobalData.driver.findElement(By.id(objects)).sendKeys(testData);
				TR.extentTest.log(LogStatus.PASS, testData+" is entered successfully in "+description+" field");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, testData+" is not entered successfully and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Input by using name///////////////////////
	public static String inputbyname(String objects, String description, String testData){
		try {
			int count = GlobalData.driver.findElements(By.name(objects)).size();
			if (count == 1) {
				GlobalData.driver.findElement(By.name(objects)).sendKeys(testData);
				TR.extentTest.log(LogStatus.PASS, testData+" is entered successfully in "+description+" field");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, testData+" is not entered successfully and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Drop Down Selection Through Xpath(No test data)///////////////////////
	public static String DropboxSelectXpath(String objects, String description, String testData) {
		try {
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 1) {
				String dropText =  GlobalData.driver.findElement(By.xpath(objects)).getText();
				String[] drop=objects.split("/option");
				new Select(GlobalData.driver.findElement(By.xpath(drop[0]))).selectByVisibleText(dropText);
				TR.extentTest.log(LogStatus.PASS, testData+" is selected successfully in "+description+" dropbox");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, testData+" is not selected successfully and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Input by using xpath///////////////////////
	public static String inputbyxpath(String objects, String description, String testData){
		try{
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 1) {
				WebElement ele = GlobalData.driver.findElement(By.xpath(objects));
				System.out.println(testData);
				ele.clear();
				ele.sendKeys(testData);
				TR.extentTest.log(LogStatus.PASS, testData+" is entered successfully in "+description+" field");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, testData+" is not entered successfully and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Save Text by using xpath///////////////////////
	public static String Savebyxpath(String objects, String description, String testData)  {
		try {
			String runtimeText="";
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 1) {
				runtimeText=GlobalData.driver.findElement(By.xpath(objects)).getText();
				if(runtimeText.trim().equals(testData))
				{
					TR.extentTest.log(LogStatus.PASS, "Expected text is available: "+testData);
					return "true";
				}else {
					TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
					return "false";
				}
			} else {
				TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Save Text by using id///////////////////////    
	public static String Savebyid(String objects, String description, String testData)  
	{
		try {
			String runtimeText = "";
			int count = GlobalData.driver.findElements(By.id(objects)).size();
			if (count == 1) {
				runtimeText = GlobalData.driver.findElement(By.id(objects)).getText();
				if (runtimeText.trim().equals(testData)) {
					TR.extentTest.log(LogStatus.PASS, "Expected text is available: "+testData);
					return "true";
				} else {
					TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
					return "false";
				}
				// Report.pass(description, objects, testData);
			} else {
				TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////////////////////////////////Save Text by using name///////////////////////            
	public static String Savebyname(String objects, String description, String testData) 
	{
		try{
			String runtimeText = "";
			int count = GlobalData.driver.findElements(By.name(objects)).size();
			if (count == 1) {
				runtimeText = GlobalData.driver.findElement(By.name(objects)).getText();
				if (runtimeText.trim().equals(testData)) {
					TR.extentTest.log(LogStatus.PASS, "Expected text is available: "+testData);
					return "true";
				} else {
					TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
					return "false";
					}
			} else {
				TR.extentTest.log(LogStatus.PASS, "Expected text is not available: "+testData+" and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	////////Drop Down Select/////////////////////  
	public static String DropSelectbyID(String objects, String description, String testData) {
		try{
			String runtimeText = "";
			int count = GlobalData.driver.findElements(By.id(objects)).size();
			if (count == 1) {
				new Select(GlobalData.driver.findElement(By.id(objects))).selectByVisibleText(testData);
				TR.extentTest.log(LogStatus.PASS, testData+" is selected successfully in "+description+" dropbox");
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL, testData+" is not selected successfully and the count of object is "+count);
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	////////Mouse Moving verification/////////////////////
	public static String MouseMovebyXpath(String objects, String description, String testData)  {
		try{
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 1) {
				WebElement MainMenu;
				//WebElement SubMenu;
				MainMenu = GlobalData.driver.findElement(By.xpath(objects));
				//SubMenu = driver.findElement(By.xpath("//span/span[contains(.,'PAGs, PAs & Accounts')]"));
				Actions builder = new Actions(GlobalData.driver);
				// Move cursor to the Main Menu Element
				builder.moveToElement(MainMenu).perform();
				// Waiting For the Submenu to Display
				TR.extentTest.log(LogStatus.PASS,description+ " Mouse moving success");
				return "true";
				//     Report.pass("Mouse moving success ", objects, testData);
			}else{
				TR.extentTest.log(LogStatus.FAIL, description+ " Mouse moving success and the count of object is "+count);
				return "false";
			}
		}
		catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	////////Child Window navigation/////////////////////
	public static String NavigateChildWIndow(String objects, String description, String testData) {
		try{
			int noOfWindows = GlobalData.driver.getWindowHandles().size();
			if (noOfWindows>1)
			{
				for (String handle : GlobalData.driver.getWindowHandles()) {
					GlobalData.driver.switchTo().window(handle);
					String strCurrentUrl = GlobalData.driver.getCurrentUrl();
					//Decoding the URL
					String strDecodedUrl=URLDecoder.decode(strCurrentUrl, "US-ASCII");
					if ((strDecodedUrl.toUpperCase()).indexOf(testData.toUpperCase())!=-1){
						try {
							((JavascriptExecutor) GlobalData.driver).executeScript("window.focus();");
							TR.extentTest.log(LogStatus.PASS,"Navigation to "+testData+" child window is success" );
							return "true";
						} catch (Exception ex){
							TR.extentTest.log(LogStatus.FAIL,"Child window not available: "+testData);
							return "false";
							//     Report.fail("Child window not available: "+testData, objects, testData);
						}
					}
				}
			}else {
				TR.extentTest.log(LogStatus.FAIL,"Only one window is available" );
				return "false";
			}
			Thread.sleep(3000);}catch (Exception e) {
				TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
				return "false";
			}
		return "false";
	}
	
	////////Object available validation/////////////////////
	public static String ObjectXpathYes(String objects, String description, String testData) {
		try {
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 1) {
				TR.extentTest.log(LogStatus.PASS,description+ " object exist" );
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL,description+ " object does not exist" );
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}

	////////Object not available validation/////////////////////     
	public static String ObjectXpathNO(String objects, String description, String testData)  
	{
		try {
			int count = GlobalData.driver.findElements(By.xpath(objects)).size();
			if (count == 0) {
				TR.extentTest.log(LogStatus.PASS,description+ " object does not exist" );
				return "true";
			} else {
				TR.extentTest.log(LogStatus.FAIL,description+ " object exist" );
				return "false";
			}
		} catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	///Page Scroll Down///////////////////
	public static String ScrollDownEnd() 
	{
		try{
			Robot robot=new Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_END);
			robot.keyRelease(java.awt.event.KeyEvent.VK_END);
			Thread.sleep(3000);
			return "true";
		}
		catch (Exception e) {
			TR.extentTest.log(LogStatus.FAIL, "Error: "+e);
			return "false";
		}
	}
	
	/////////////Wait by ID/////////////////////////
	public static String waitbyID(String obj,int waittime) 
	{
		try{
			(new WebDriverWait(GlobalData.driver, waittime)).until(ExpectedConditions.elementToBeClickable(By.id(obj)));
			return "true";
		}catch (Exception e) {
			return "false";
			//  Report.failm("Error: "+e, "Nil", "Nil");
		}
		
	}
	
	/////////////Wait by Xpath/////////////////////////
	public static String waitbyXpath(String obj,int waittime) 
	{
		try{
			(new WebDriverWait(GlobalData.driver, waittime)).until(ExpectedConditions.elementToBeClickable(By.xpath(obj)));
			return "true";
		}catch (Exception e) {
			return "false";
			//   Report.failm("Error: "+e, "Nil", "Nil");
			
		}
		
	}
	
	/////////////Wait by Name/////////////////////////
	public static String waitbyName(String obj,int waittime) 
	{
		try{
			(new WebDriverWait(GlobalData.driver, waittime)).until(ExpectedConditions.elementToBeClickable(By.name(obj)));
			return "true";
		}catch (Exception e) {
			return "false";
		}
	}
	
	////////////////////////////Handling Alert Pop Up////////////////////////////////////////
	public static boolean isAlertPresent()
	{
		try
		{
			GlobalData.driver.switchTo().alert();
			return true;
		}
		catch (NoAlertPresentException Ex)
		{
			return false;
		}
	}
	
	public static void handleAlertPopUp()
	{
		if (isAlertPresent())
		{
			Alert simpleAlert = GlobalData.driver.switchTo().alert();
			simpleAlert.accept();
			//Report.pass("Alert pop up handled successfully", "Nil",                    "Nil");
		}
	}
	
	///////////////////////////////////waiting for Frame to Load/////////////////////////////////////////
	public static String wait_frameId(String frameName)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(GlobalData.driver,60);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
			return "true";
		}
		catch(Exception e) {
			return "false";
		}
	}
	
	public static void wait_frameId1(String frameName)
	{
		WebDriverWait wait = new WebDriverWait(GlobalData.driver,60);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
		
	}
	
	public static void hardWait(long time_sec)
	{
		try {
			Thread.sleep(time_sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//            Report.fail("Error: "+e, "Nil", "Nil");
		}
	}
	
	public static void domRefresh()
	{
		Actions actions = new Actions(GlobalData.driver);
		//actions.keyDown("Keys.FN").keyDown("Keys.F12")
		actions.sendKeys(Keys.F12).build().perform();
		hardWait(2000);
		actions.sendKeys(Keys.TAB).build().perform();
		hardWait(2000);
		actions.sendKeys(Keys.F5).build().perform();
		hardWait(2000);
		actions.sendKeys(Keys.F12).build().perform();
		hardWait(3000);
	}
	
	public static String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }
	
	public static String SelectingMenuByClick(String obj1, String obj2, String item)
	{
		String flag="";
		try{
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad(obj1,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(obj1), "Open Drop down");
			Thread.sleep(4000);
			
			ReusableFunctions.waitTillElementLoad(obj2,5000);
		 	List<WebElement> entityList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(obj2)));
			for(WebElement ele: entityList){
				if(!item.equalsIgnoreCase("")){
					if(ele.getAttribute("title").contains(item)){
						ele.click();
						flag= "Pass";
						break;
					}
				}else{
					ele.click();
					flag= "Pass";
					TR.extentTest.log(LogStatus.PASS, "Salutation info entered successfully.");
					break;
				}
			}
			
		}catch(Exception e){
			flag="fail";
		}
		return flag;
	}
	
	public static String SelectingMenuByEnteringText(String obj1, String obj2, String item)
	{
		String flag="";
		
		try{
			Thread.sleep(5000);
			//ReusableFunctions.waitUntilItemClickable(obj1);
			ReusableFunctions.waitTillElementLoad(obj1,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(obj1))).sendKeys(item);
			Thread.sleep(5000);
			ReusableFunctions.waitTillElementLoad(obj2,5000);
			List<WebElement> PrimList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(obj2)));
			for(WebElement ele: PrimList){
				if(ele.getAttribute("title").contains(item)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Position selected successfully.");
					flag= "Pass";
					Thread.sleep(5000);
					break;
				}
			}
		}catch(Exception e){
			flag = "fail";
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean waitUntilItemClickable(String obj){
		boolean expc = false;
		while(!expc){
			if(ReusableFunctions.waitbyXpath(GlobalData.ObjectRepo.get(obj),5000).equalsIgnoreCase("true")){
				expc = true;
				break;
			}
			else{
				ReusableFunctions.hardWait(1000);
			}
		}
		return expc;
	}
	
	public static String waitTillElementLoad(String obj,int waittime) 
	{
		try{
			(new WebDriverWait(GlobalData.driver, waittime)).until(ExpectedConditions.visibilityOf(GlobalData.driver.findElement(By.xpath(obj))));
			return "true";
		}catch (Exception e) {
			return "false";
			//   Report.failm("Error: "+e, "Nil", "Nil");
			
		}
		
	}
}