package Scripts;

 

//import io.appium.java_client.AppiumDriver;

//import io.appium.java_client.android.AndroidDriver;

 

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Driver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

 
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.relevantcodes.extentreports.LogStatus;
import CommonFunctionLibrary.*;

//import ReusableFunctionality.babyfunction;
//import SupportLibraries.Excel;
//import BusinessComponents.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FunctionLibrary
{
	
	//public static WebDriver driver;
	public static String baseUrl;
	public static BufferedWriter bw;
	public static File f;
	public static String abc;
	public static String Browser1;
	public static DateFormat dateFormat1 = new SimpleDateFormat("HHmmss");
	public static Date date1 = new Date();
	public static String date23=dateFormat1.format(date1);
	// public static WebDriver driver;
	public static TestReports TR = new TestReports();

	///////////////////////////////////////Launch Browser////////////////////////////////////////////////////
	public static String launchBrowser(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try
			{
				String URL = "";
				//Killing IEDriverServer and iexplore process
				Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
				Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
				
				//Getting URl from Config Sheet
				if(Param1.equalsIgnoreCase("Boomi")){
					URL = GlobalData.ConfigData.get("BOOMI_URL");
				}else if(Param1.equalsIgnoreCase("Successfactor")){
					URL = GlobalData.ConfigData.get("SUCCESSFACTOR_URL");
				}
				
				//Defining Chrome driver
				Thread.sleep(2000);
				System.setProperty("webdriver.chrome.driver", GlobalData.ConfigData.get("ChromeDriverPath"));
				GlobalData.driver = new ChromeDriver();
				GlobalData.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				GlobalData.driver.manage().window().maximize();
				GlobalData.driver.get(URL);
				Thread.sleep(2000);
				boolean expc = false;
				
				//ReusableFunctions.waitUntilItemClickable("BoomiUsername");
				
				if ((Param1.equalsIgnoreCase("Boomi")&&(ReusableFunctions.waitUntilItemClickable("BoomiUsername"))) 
						|| (Param1.equalsIgnoreCase("Successfactor") && ReusableFunctions.waitUntilItemClickable("NextButton")))
				{
					TR.extentTest.log(LogStatus.PASS, "Browser Launched Successfully and the URL is "+URL);
					TR.captureScreenshot(date);
					System.out.println("Browser launched Successfully.");
					return "pass";
				}
				else
				{
					TR.extentTest.log(LogStatus.FAIL, "Problem in launching Browser, URL: "+URL );
					TR.captureScreenshot(date);
					System.out.println("Something went wrong while launching the browser.");
					return "fail";
				}
			}
		catch(Exception e)
			{
				System.out.println("Problem in launching browser and the error is "+e);
				return "fail";
				
			}
		
	}
	///////////////////////////////////////Login To Application////////////////////////////////////////////////////
	public static String loginToApp(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try
		{
			String userName="", password="";
			if (Param1.equalsIgnoreCase("Boomi"))
			{
				userName = GlobalData.ConfigData.get("BoomiUserName");
				password = GlobalData.ConfigData.get("BoomiPassword");
				
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BoomiUsername"))).sendKeys(userName);
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BoomiPassword"))).sendKeys(password);
				ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("BoomiUsername"), "Click on UserName field");
				
				ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("BoomiSubmit"), "Click on Submit");
				
			}
			else if (Param1.equalsIgnoreCase("Successfactor"))
			{
				if(ReusableFunctions.waitbyXpath(GlobalData.ObjectRepo.get("NextButton"),10).equalsIgnoreCase("true")){
					GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("CompanyId"))).sendKeys(GlobalData.DataElements.get("CompanyName"));
					ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("NextButton"), "Click on the Next Button");
				}
				Thread.sleep(3000);
				userName = GlobalData.ConfigData.get("SuccessFactorsUserName");
				password = GlobalData.ConfigData.get("SuccessFactorsPassword");
				
				ReusableFunctions.waitTillElementLoad("SuccessfactorUserName",5000);
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("SuccessfactorUserName"))).clear();
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("SuccessfactorUserName"))).sendKeys(userName);
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("SuccessfactorPassword"))).clear();
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("SuccessfactorPassword"))).sendKeys(password);
				
				ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("SuccessfactorPassword"), "Click on password field");
				Thread.sleep(2000);
				ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("LogIn"), "Click on Submit");
				System.out.println("Logged in to Success Factor.");
				
			}
			
			Thread.sleep(3000);
			ReusableFunctions.hardWait(1000);
			ReusableFunctions.handleAlertPopUp();

			TR.extentTest.log(LogStatus.PASS, "Login is successful");
			TR.captureScreenshot(date);
			return "Pass";
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error while logging: "+e);
			return "Fail";

		}

	}
	
	///////////////////////////////////////Manage Menu Navigation////////////////////////////////////////////////////
	public static String BoomiAtomManagmentNavi(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)

	{
		try{
			TestReports TR = new TestReports();
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("MenuNavigation"), "Manage");
			List<WebElement> childs = GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ManageMenu"))).findElements(By.xpath(".//*"));
			MLoop: for(WebElement we:childs)
			{
				if(we.getText().contains("Atom Management")){
					List<WebElement> childEle = we.findElements(By.xpath(".//*"));
					for(WebElement ele:childEle){
						ele.click();
						ReusableFunctions.hardWait(5000);
						TR.extentTest.log(LogStatus.PASS, "Menu Invocation is successful");
						TR.captureScreenshot(date);
						break MLoop;
					}
				}
			}
		}catch(Exception e){
			TR.extentTest.log(LogStatus.FAIL, "Not able to Start the process: "+ Param1 );
			TR.captureScreenshot(date);
			return "Fail";
		}
		return "pass";
	}
	
	public static String DeployedProcess(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try{
			TestReports TR = new TestReports();
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("AtomEnvLink"), "Atom Env");
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("DeployedProcess"), "Process");
			Thread.sleep(5000);
			ReusableFunctions.inputbyxpath(GlobalData.ObjectRepo.get("FilterProcess"), "Enter the Process", Param2);
			
			Thread.sleep(5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("ProcessItemArrow"), "Selecting the required process");
			ReusableFunctions.hardWait(10);
			
			Thread.sleep(5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("ExecuteProcess"), "Clicking on Execute Process");
			TR.extentTest.log(LogStatus.PASS, Param1 + " Deployed Process started successfully");
			TR.captureScreenshot(date);
		}catch(Exception e){
			TR.extentTest.log(LogStatus.FAIL, "Not able to Start the process: "+ Param1 );
			TR.captureScreenshot(date);
			return "fail";
		}
		return "pass";
	}

	public static String StopBrowser(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try{
			GlobalData.driver.quit();
		}catch(Exception e)
		{
			System.out.println("Error: "+e);
		}
		return "pass";
	}
	
	public static String FileValidator(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try{
			String FileName="";
			if(Param1.equalsIgnoreCase("INT-046")){
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
    		 	Date now = new Date();
    		 	String date1 = sdfDate.format(now);
    		 	FileName=Param3+"_"+date1+".txt";
			}
			String sftpusername = GlobalData.ConfigData.get("sftpusername");
			String sftppassword = GlobalData.ConfigData.get("sftppassword");
			String sftphostname = GlobalData.ConfigData.get("sftphostname");
			String sftpport = GlobalData.ConfigData.get("sftpport");
			String channel = GlobalData.ConfigData.get("channel");
			String location = GlobalData.ConfigData.get("location");
			String destinationLocation = GlobalData.ConfigData.get("destinationLocation");
			FileDownloadFromSFTP fileDownloadFromSFTP = new FileDownloadFromSFTP();
			fileDownloadFromSFTP.connectSFTPLocation(sftpusername,sftppassword,sftphostname,sftpport);

			fileDownloadFromSFTP.createSftpChannel(channel);
			fileDownloadFromSFTP.fetchFileToLocal(location,Param2,FileName,destinationLocation);
			fileDownloadFromSFTP.Validate_EMPID(destinationLocation,FileName,date);
			fileDownloadFromSFTP.Validate_HireDate(destinationLocation, FileName, date);
			fileDownloadFromSFTP.Validate_LastName(destinationLocation, FileName, date);
			fileDownloadFromSFTP.Validate_FirstName(destinationLocation, FileName, date);
			fileDownloadFromSFTP.Validate_MiddleInitial(destinationLocation, FileName, date);
		}catch(Exception e){
			
		}
		return "pass";
	}
	
	public static String NavigateToReqPage(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,9000);
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			ReusableFunctions.waitbyXpath(GlobalData.ObjectRepo.get(ObjectProp), 5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Clicking on Search Text Box");	
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Param1);
			
			ReusableFunctions.waitUntilItemClickable(Param2);
			List<WebElement> childs = GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param2))).findElements(By.xpath(".//*"));
			NLoop: for(WebElement we: childs)
			{
				if(we.getText().equalsIgnoreCase(Param1)){
					List<WebElement> nextChild = we.findElements(By.xpath(".//*"));
					for(WebElement ele: nextChild){
						if(ele.getText().equalsIgnoreCase(Param1)){
							ele.click();
							TR.extentTest.log(LogStatus.PASS, Param1 + " Successfully navigated to "+ Param1 + " Page.");
							flag="Pass";
							break NLoop;
						}
					}
				}
			}
		}catch(Exception e){
			TR.extentTest.log(LogStatus.FAIL, "Not able to Start the process: "+ Param1 );
			TR.captureScreenshot(date);
			return "fail";
		}
		return flag;
	}
	public static String EnterHireDate(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		try{
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			String reqDate="";
			if(GlobalData.DataElements.get("HireDate").equalsIgnoreCase("Current")){
				reqDate = dateFormat.format(cal.getTime());
			}
			else if(GlobalData.DataElements.get("HireDate").equalsIgnoreCase("Past")){
				cal.add(Calendar.DATE, -2);
				reqDate = dateFormat.format(cal.getTime());
			}
			else if(GlobalData.DataElements.get("HireDate").equalsIgnoreCase("Future")){
				cal.add(Calendar.DATE, +2);
				reqDate = dateFormat.format(cal.getTime());
			}
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).clear();
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(reqDate);
			TR.extentTest.log(LogStatus.PASS, "Hire Date entered successfully.");
			Thread.sleep(5000);
			return "Pass";
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter the date successfully");
			TR.captureScreenshot(date);
			return "fail";
		}
	}
	
	public static String EnterLegalEntity(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).clear();
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("LegalEntity"));
			Thread.sleep(5000);
			ReusableFunctions.waitUntilItemClickable(Param1);
			List<WebElement> entityList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param1)));
			Thread.sleep(4000);
			int i=0;
			for(WebElement ele: entityList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("LegalEntity"))){
					ReusableFunctions.waitUntilItemClickable("LegalEntityDropDown");
					ele.click();
					Thread.sleep(4000);
					TR.extentTest.log(LogStatus.PASS, "Legal Entity selected successfully.");
					flag= "Pass";
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to Start the process: "+ GlobalData.DataElements.get("LegalEntity") );
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EventSelection(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).clear();
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("EventReason"));
			
			ReusableFunctions.hardWait(5000);
			List<WebElement> entityList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param1)));
			Thread.sleep(4000);
			for(WebElement ele: entityList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("EventReason"))){
					ReusableFunctions.waitUntilItemClickable("EventReasonDropDown");
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Event Reason selected successfully.");
					flag= "Pass";
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Event: "+ GlobalData.DataElements.get("EventReason") );
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterNameInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddhhmm");
		 	Date now = new Date();
		 	String randomDate = sdfDate.format(now);
		 	String randomNum = ReusableFunctions.GenerateRandomNumber(3);
		 	 String LastName = "Auto"+randomNum;
		 	 String FirstName = "EQA_"+randomDate;
		 	GlobalData.DataElements.put("FirstName", FirstName);
		 	GlobalData.DataElements.put("EmpLastName", LastName);
		 	
		 	
		 	ReusableFunctions.waitTillElementLoad("FirstName",5000);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("FirstName"))).sendKeys(FirstName);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("FirstName"))).sendKeys(Keys.TAB);
		 	
		 	//ReusableFunctions.waitbyXpath("MiddleName",3000);
		 	
		 	ReusableFunctions.waitTillElementLoad("MiddleName",5000);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("MiddleName"))).sendKeys("Test_"+randomDate);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("MiddleName"))).sendKeys(Keys.TAB);
		 	
		 	ReusableFunctions.waitTillElementLoad("LastName",5000);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("LastName"))).sendKeys(LastName);
		 	GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("LastName"))).sendKeys(Keys.TAB);
		 	flag = "Pass";
		 	TR.extentTest.log(LogStatus.PASS, "Employee Name entered Successfully.");
		 	
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter the Nmae info correctly ");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String SelectingSalution(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("Salutation"));
			TR.extentTest.log(LogStatus.PASS, "Required Salution is selected Successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Salution correctly ");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterDateOfBirth(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			int age = Integer.parseInt(GlobalData.DataElements.get("Age"));
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			cal.add(Calendar.YEAR, -age);
			String DOB= dateFormat.format(cal.getTime());
			
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(DOB);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			flag= "Pass";
			Thread.sleep(4000);
			TR.extentTest.log(LogStatus.PASS, "Date Of Birth Entered successfully.");
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Dob Correctly ");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterCountryInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Add Nationa Id Info.");
			
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param1))).sendKeys(GlobalData.DataElements.get("Country"));
			
			Thread.sleep(5000);
			//ReusableFunctions.waitTillElementLoad("CountryDropdown",5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param2)));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("Country"))){
					Thread.sleep(4000);
					ele.click();
					Thread.sleep(4000);
					TR.extentTest.log(LogStatus.PASS, "Required Country selected successfully.");
					flag= "Pass";
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the required country. ");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterNationalCardID(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Add Nationa Id Info.");
			
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			List<WebElement> cardList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param1)));
			for(WebElement ele: cardList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("NationalCardID"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required National ID Card selected successfully.");
					flag= "Pass";
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the required Nation Card ID. ");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterNationalID(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("NationalIDNum"));
			TR.extentTest.log(LogStatus.PASS, "Required National ID details selected successfully.");
			
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(Param1), "Add Nationa Id Info.");
			
			ReusableFunctions.waitTillElementLoad(Param2,5000);
			List<WebElement> PrimaryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param2)));
			for(WebElement ele: PrimaryList){
				if(ele.getAttribute("title").contains("Yes")){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Is Primary details entered successfully.");
					flag= "Pass";
					break;
				}
			}
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Tabbing out from the field.");
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter National ID Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterGenderInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("Gender"));
			TR.extentTest.log(LogStatus.PASS, "Required Gender is selected Successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Gender correctly ");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterMartialStatusInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("MartialStatus"));
			TR.extentTest.log(LogStatus.PASS, "Required Martial Status selected successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Martial Status Info.");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterEthinicGroup(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("EthnicGroup"));
			TR.extentTest.log(LogStatus.PASS, "Required Ethinic Group selected successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Ethinic Group Info.");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterEmailTypeInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Add Email id Info.");
			
			flag = ReusableFunctions.SelectingMenuByClick(Param1, Param2, Param3);
			TR.extentTest.log(LogStatus.PASS, "Required Email Type selected successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select Email type");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		
		return flag;
	}
	
	/*public static String EnterBusinessEmailTypeInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad("EmailInfoAddBtn",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("EmailInfoAddBtn"), "Add Email id Info.");
			
			ReusableFunctions.waitTillElementLoad("EmailTypeArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("EmailTypeArrow"), "Selecting the Email Type");
			
			ReusableFunctions.waitTillElementLoad("EmailTypeDropdown",5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("EmailTypeDropdown")));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(Param1)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Email Type selected successfully.");
					flag= "Pass";
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select email type");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}*/
	
	public static String EnterEmailId(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			String emaillId = GlobalData.DataElements.get("EmpLastName")+Param1;
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(emaillId);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			
			TR.extentTest.log(LogStatus.PASS, "Required Email Id entered successfully.");
			flag= "Pass";
			
			/*ReusableFunctions.waitTillElementLoad(Param1,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(Param1), "Selecting the Email Type");
			
			ReusableFunctions.waitTillElementLoad(Param2,5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param2)));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("BusinessMailType"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Email Id entered successfully.");
					flag= "Pass";
					break;
				}
			}*/
			/*ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Selecting the Email Type");*/
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Email ID Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String SelectMailPrimaryInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, Param2);
			TR.extentTest.log(LogStatus.PASS, "Required Email Type selected successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required email type.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPersonalEmailTypeInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad("EmailInfoAddBtn",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("EmailInfoAddBtn"), "Add Email id Info.");
			
			ReusableFunctions.waitTillElementLoad("2ndEmailTypeArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("2ndEmailTypeArrow"), "Selecting the Email Type");
			
			ReusableFunctions.waitTillElementLoad("2ndEmailTypeDropdown",5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("2ndEmailTypeDropdown")));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(Param1)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Personal email type selected successfully.");
					flag= "Pass";
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select 2nd email type info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPersonalEmailId(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			String emaillId = GlobalData.DataElements.get("EmpLastName")+GlobalData.DataElements.get("PersonalMailId");
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(emaillId);
			
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(Param1), "Selecting the Email Type");
			
			ReusableFunctions.waitTillElementLoad(Param2,5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param2)));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("PersonalMailType"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Email Id entered successfully.");
					flag= "Pass";
					break;
				}
			}
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Selecting the Email Type");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Email ID Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterBusinessPhoneNo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad("PhoneInfoAddBtn",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PhoneInfoAddBtn"), "Add Phone Info.");
			
			ReusableFunctions.waitTillElementLoad("BusinessPhoneTypeArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("BusinessPhoneTypeArrow"), "Add Phone Info.");
			
			ReusableFunctions.waitTillElementLoad("BusinessPhoneTypeDrop",5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("BusinessPhoneTypeDrop")));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(Param1)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Phone Type selected successfully.");
					flag= "Pass";
					break;
				}
			}
			String AreaCode = ReusableFunctions.GenerateRandomNumber(3);
			String Exten = ReusableFunctions.GenerateRandomNumber(4);
			
			ReusableFunctions.waitTillElementLoad("BusinessCountryCode",5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BusinessCountryCode"))).sendKeys("+1");
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BusinessAreaCode"))).sendKeys(AreaCode);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BusinessPhoneNum"))).sendKeys(AreaCode+"-"+Exten);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("BusinessExtension"))).sendKeys(Exten);
			
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("BusinessIsPrimaryArrow"), "Add Primary Info.");
			
			ReusableFunctions.waitTillElementLoad("BusinessIsPrimaryDropdown",5000);
			List<WebElement> PrimList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("BusinessIsPrimaryDropdown")));
			for(WebElement ele: PrimList){
				if(ele.getAttribute("title").contains("Yes")){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Primary Deails selected successfully.");
					flag= "Pass";
					break;
				}
			}
			ReusableFunctions.waitTillElementLoad("BusinessExtension",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("BusinessExtension"), "Add Primary Info.");
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to Business Phone Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPersonalPhoneNo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad("PhoneInfoAddBtn",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PhoneInfoAddBtn"), "Add Phone Info.");
			
			ReusableFunctions.waitTillElementLoad("PersonalPhoneTypeArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PersonalPhoneTypeArrow"), "Add Phone Info.");
			
			ReusableFunctions.waitTillElementLoad("PersonalPhoneTypeDrop",5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("PersonalPhoneTypeDrop")));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(Param1)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Phone Type selected successfully.");
					flag= "Pass";
					break;
				}
			}
			String AreaCode = ReusableFunctions.GenerateRandomNumber(3);
			String Exten = ReusableFunctions.GenerateRandomNumber(4);
			
			ReusableFunctions.waitTillElementLoad("PersonalCountryCode",5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("PersonalCountryCode"))).sendKeys("+1");
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("PersonalAreaCode"))).sendKeys(AreaCode);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("PersonalPhoneNum"))).sendKeys(AreaCode+"-"+Exten);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("PersonalExtension"))).sendKeys(Exten);
			
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PersonalIsPrimaryArrow"), "Add Primary Info.");
			
			ReusableFunctions.waitTillElementLoad("PersonalIsPrimaryDropdown",5000);
			List<WebElement> PrimList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("PersonalIsPrimaryDropdown")));
			for(WebElement ele: PrimList){
				if(ele.getAttribute("title").contains("No")){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Primary Deails selected successfully.");
					flag= "Pass";
					break;
				}
			}
			Thread.sleep(3000);
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to personal phone Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterHomeAddress(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			String AddressCode = ReusableFunctions.GenerateRandomNumber(4);
			ReusableFunctions.waitTillElementLoad("HomeAddress1",5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("HomeAddress1"))).sendKeys(AddressCode+" XPO-EQA ST");
			
			String AptNum = ReusableFunctions.GenerateRandomNumber(4);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("HomeAddress2"))).sendKeys("Apt. Number’s: "+AptNum);
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("HomeCity"))).sendKeys(GlobalData.DataElements.get("HomeCity"));
			
			ReusableFunctions.waitTillElementLoad("HomeState",5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("HomeState"))).sendKeys(GlobalData.DataElements.get("HomeState"));
			
			ReusableFunctions.waitTillElementLoad("HomeStateDrop",5000);
			List<WebElement> PrimList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("HomeStateDrop")));
			for(WebElement ele: PrimList){
				if(ele.getAttribute("title").equalsIgnoreCase(GlobalData.DataElements.get("HomeState"))){
					ele.click();
					break;
				}
			}
			ReusableFunctions.waitTillElementLoad("HomeZipCode",5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("HomeZipCode"))).sendKeys(GlobalData.DataElements.get("HomeZip"));
			flag= "Pass";
			TR.extentTest.log(LogStatus.PASS, "Required Home Address entered successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Home Address Info.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		
		return flag;
	}
	
	public static String ClickonContinueBtn(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Clicking on Continue Button");
			flag = "Pass";
			TR.extentTest.log(LogStatus.PASS, "Clicked on Continue Button successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to click on Continue button.");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		return flag;
	}
	
	public static String SelectingBusinessUnit(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			Thread.sleep(8000);
			//flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, Param2);
			flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("BusinessUnit"));
		}catch(Exception e){
			flag = "Fail";
		}
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Business Unit selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Business Unit.");
		}
		return flag;
	}
	
	public static String SelectingDivision(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("Division"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Division selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Division.");
		}
		return flag;
	}
	
	public static String SelectingLocation(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("Location"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Location selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Location.");
		}
		return flag;
	}
	
	public static String SelectingProfitCostCenter(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("ProfitCostCenter"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Profit Cost Center selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Profit Cost Center.");
		}
		return flag;
	}
	
	public static String SelectingDepartment(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("Department"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Profit Cost Center selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Profit Cost Center.");
		}
		return flag;
	}
	
	public static String SelectingManegerSupervisor(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("Maneger"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Maneger/Supervisor selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Maneger/Supervisor.");
		}
		return flag;
	}
	
	public static String SelectingJobCode(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("JobCode"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required JobCode selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required JobCode.");
		}
		return flag;
	}
	
	public static String SelectingClass(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("ClassDetails"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required ClassDetails selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required ClassDetails.");
		}
		return flag;
	}
	
	public static String EnterReqPosition(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		
		try{
			Thread.sleep(5000);
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("Position"));
			Thread.sleep(5000);
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			List<WebElement> PrimList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param1)));
			for(WebElement ele: PrimList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("Position"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Position selected successfully.");
					flag= "Pass";
					Thread.sleep(5000);
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Position");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		return flag;
	}
	
	public static String EnterWeeklyHours(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("WeeklyHours"));
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			flag = "Pass";
			TR.extentTest.log(LogStatus.PASS, "Required Weekly Hrs entered successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Weekly info correctly.");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		return flag;
	}
	
	public static String EnterClassDetails(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Show More Job Info Details");
			
			flag = ReusableFunctions.SelectingMenuByClick(Param1, Param2, GlobalData.DataElements.get("ClassDetails"));
			TR.extentTest.log(LogStatus.PASS, "Required Class selected successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Class details info correctly.");
			TR.captureScreenshot(date);
			flag= "fail";
		}
		return flag;
	}
	
	public static String EnterShiftCodeAndPercent(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Open Shift Code Drop down");
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			List<WebElement> countryList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param1)));
			for(WebElement ele: countryList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("ShiftCode"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Shift Code selected successfully.");
					flag= "Pass";
					break;
				}
			}
			ReusableFunctions.waitTillElementLoad(Param2,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param2))).clear();
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param2))).sendKeys(GlobalData.DataElements.get("ShiftPercentage"));
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param2))).sendKeys(Keys.TAB);
			TR.extentTest.log(LogStatus.PASS, "Required Shift Percentage entered successfully.");
			flag = "Pass";
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Shift code and percentage correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String ClickOnLink(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Click on the link.");
			TR.extentTest.log(LogStatus.PASS, "Required link clicked successfully.");
			flag = "Pass";
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to click link correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterEmployeeType(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("EmployeeType"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Employee Type selected successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Employee type");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String SelectingEmploymentType(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("EmploymentType"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Employment Type selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Employment Type.");
		}
		return flag;
	}
	
	public static String EnterEmploymentType(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("EmploymentType"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Employment Type selected successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Employment type");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterReqBillRateText(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("CWRBillRate"));
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			flag="Pass";
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select the Employment type");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String SelectingBillRateCurrency(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("CWRBillRateCurrency"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Currency Type selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Currency Type.");
		}
		return flag;
	}
	
	public static String SelectingWorkCenter(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("WorkCenter"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Currency Type selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Currency Type.");
		}
		return flag;
	}
	
	public static String EnterEndDate(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
			cal.add(Calendar.YEAR, 1);
			String DOB= dateFormat.format(cal.getTime());
			
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(DOB);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			flag= "Pass";
			Thread.sleep(4000);
			TR.extentTest.log(LogStatus.PASS, "End Date Entered successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter End Date correctly.");
			TR.captureScreenshot(date);
			flag="false";
		}
		return flag;
	}
	
	public static String SelectingVendor(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("Vendor"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Vendor selected successfully.");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Vendor details correctly.");
			TR.captureScreenshot(date);
			flag="false";
		}
		return flag;
	}
	
	public static String SelectingWorkOrderOwner(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		flag = ReusableFunctions.SelectingMenuByEnteringText(ObjectProp, Param1, GlobalData.DataElements.get("Maneger"));
		if(flag.equalsIgnoreCase("Pass")){
			TR.extentTest.log(LogStatus.PASS, "Required Maneger/Supervisor selected successfully.");
		}
		else{
			TR.extentTest.log(LogStatus.FAIL, "Not able to select required Maneger/Supervisor.");
		}
		return flag;
	}
	
	public static String EnterTempID(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			String AgencyTempID = ReusableFunctions.GenerateRandomNumber(4);
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(AgencyTempID);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			TR.extentTest.log(LogStatus.PASS, "Required Agency Temp ID entered successfully.");
			flag="Pass";
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Agency Temp ID currectly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterUnion(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("Union"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Union selected successfully.");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Union details correctly.");
			TR.captureScreenshot(date);
			flag="false";
		}
		return flag;
	}
	
	public static String ContinueToCompensationInfo(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			ReusableFunctions.waitTillElementLoad("JobInfoContinueBtn",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("JobInfoContinueBtn"), "Continuing after entering Job Info.");
			ReusableFunctions.waitTillElementLoad("CompensationAddBtn",5000);
			TR.extentTest.log(LogStatus.PASS, "Navigated to Compensation Page successfully.");
			flag = "Pass";
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to navigated to Compensation Page.");
			TR.captureScreenshot(date);
			flag="false";
		}
		return flag;
	}
	
	public static String EnterPayType(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("PayType"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Pay Type selected successfully.");
			}
			/*
			ReusableFunctions.waitUntilItemClickable("PayTypeArrow");
			ReusableFunctions.waitTillElementLoad("PayTypeArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PayTypeArrow"), "Open Pay Type Drop down");
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad("PayTypeDrop",5000);
			List<WebElement> employList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("PayTypeDrop")));
			for(WebElement ele: employList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("PayType"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Pay Type selected successfully.");
					flag= "Pass";
					break;
				}
			}*/
			/*Thread.sleep(5000);
			ReusableFunctions.waitTillElementLoad("PayGroupArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PayGroupArrow"), "Open Pay Type Drop down");
			Thread.sleep(5000);
			ReusableFunctions.waitTillElementLoad("PayGroupDrop",5000);
			List<WebElement> PayList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("PayGroupDrop")));
			for(WebElement ele: PayList){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Pay Type selected successfully.");
					flag= "Pass";
					break;
			}*/
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Pay type correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPayGroup(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date){
		String flag="";
		try{
			flag = ReusableFunctions.SelectingMenuByClick(ObjectProp, Param1, GlobalData.DataElements.get("PayGroup"));
			if(flag.equalsIgnoreCase("Pass")){
				TR.extentTest.log(LogStatus.PASS, "Required Pay Group selected successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Pay Group correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPayComponent(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Entering Pay Component");
			
			ReusableFunctions.waitTillElementLoad(Param1,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(Param1))).sendKeys(GlobalData.DataElements.get("PayComponent"));
			
			ReusableFunctions.waitTillElementLoad(Param2,5000);
			List<WebElement> employList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get(Param2)));
			for(WebElement ele: employList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("PayComponent"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Pay Component selected successfully.");
					flag= "Pass";
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to enter Pay Component correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterCompensationAmt(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date){
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Entering Pay Component");
			
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(GlobalData.DataElements.get("ComposationAmt"));
			
			GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get(ObjectProp))).sendKeys(Keys.TAB);
			flag = "Pass";
			TR.extentTest.log(LogStatus.PASS, "Required Componsation Amt entered successfully.");
		}catch(Exception e){
			
		}
		return flag;
	}
	
	public static String EnterPayFrequency(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			ReusableFunctions.waitTillElementLoad("FrequencyArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("FrequencyArrow"), "Open Union Drop down");
			
			ReusableFunctions.waitTillElementLoad("FrequencyDrop",5000);
			List<WebElement> employList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("FrequencyDrop")));
			for(WebElement ele: employList){
				if(ele.getAttribute("title").contains(GlobalData.DataElements.get("Frequency"))){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Payment Frequency selected successfully.");
					flag= "Pass";
					break;
				}
			}
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("CompensationAmt"), "Entering Pay Component");
			Thread.sleep(3000);
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select Pay Frequency correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String EnterPaymentMethod(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			Thread.sleep(4000);
			ReusableFunctions.waitTillElementLoad("PaymentMethodArrow",5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("PaymentMethodArrow"), "Open Payment Method Drop down");
			
			ReusableFunctions.waitTillElementLoad("PaymentMethodDrop",5000);
			List<WebElement> employList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("PaymentMethodDrop")));
			for(WebElement ele: employList){
				if(ele.getAttribute("title").contains(Param1)){
					ele.click();
					TR.extentTest.log(LogStatus.PASS, "Required Payment Method selected successfully.");
					flag= "Pass";
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to select Payment Method correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String DeletePaymentMethod(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag="";
		try{
			Thread.sleep(6000);
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Deleting Payment method.");
			flag = "Pass";
			TR.extentTest.log(LogStatus.PASS, "Payment Info deleted successfully.");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to delete Payment info correctly.");
			TR.captureScreenshot(date);
			flag="fail";
		}
		return flag;
	}
	
	public static String SubmittingApplication(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			ReusableFunctions.waitUntilItemClickable(ObjectProp);
			ReusableFunctions.waitTillElementLoad(ObjectProp,5000);
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get(ObjectProp), "Submitting the Employee details");
			
			boolean expc = false;
			int counter = 0;
			while(!expc || counter <=10){
				if(ReusableFunctions.waitTillElementLoad(GlobalData.ObjectRepo.get("ConfirmationMsg"),5000).equalsIgnoreCase("true") ||
						ReusableFunctions.waitTillElementLoad(GlobalData.ObjectRepo.get("ViewProfileLink"),5000).equalsIgnoreCase("true")){
					
					expc = true;
					break;
				}
				else{
					ReusableFunctions.hardWait(1000);
				}
				counter++;
			}
			if(ReusableFunctions.waitTillElementLoad(GlobalData.ObjectRepo.get("ConfirmationMsg"),5000).equalsIgnoreCase("true")){
				ReusableFunctions.waitTillElementLoad("ConfirmationMsg",5000);
				GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ConfirmationMsg"))).sendKeys(GlobalData.DataElements.get("ConfirmationMsg"));
				
				ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("ConfirmationBtn"), "Clicking on the Confirmation Button");
			}
			TR.extentTest.log(LogStatus.PASS, "Employee Details submitted successfully.");
			flag = "Pass";
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to submit employee details correctly.");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		
		
		return flag;
	}
	
	public static String ValidatingUserProfile(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			System.out.println(GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ViewProfileLink"))).getText());
			if(GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ViewProfileLink"))).getText().contains("View profile of"+GlobalData.DataElements.get("FirstName"))){
				flag = "Pass";
				TR.extentTest.log(LogStatus.PASS, "Verified Employee profile successfully.");
			}
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Employee profile not submitted correctly.");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		return flag;
	}
	
	public static String GetEmployeeIDSaveInDataSheet(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		try{
			ReusableFunctions.waitUntilItemClickable("ViewProfileLink");
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("ViewProfileLink"), "Navigating to Employee Details Page");
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Employee profile not submitted correctly.");
			TR.captureScreenshot(date);
			flag = "fail";
		}
		return flag;
	}
	public static String NavigateToProxyUserScreen(String TestCaseName,String Steps,String ExecKey,String ObjectProp,String keyword,String Param1,String Param2,String Param3,String ObjectRepoPath,String date)
	{
		String flag = "";
		boolean expc = false;
		int counter=0;
		try{
			ReusableFunctions.waitUntilItemClickable("UserMenuArrow");
			ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("UserMenuArrow"), "Open Union Drop down");
			 
			ReusableFunctions.waitTillElementLoad("UserMenuList",5000);
			List<WebElement> menuList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("UserMenuList")));
			for(WebElement ele: menuList){
				if(ele.getText().equalsIgnoreCase("Proxy Now")){
					ele.click();
					ReusableFunctions.waitUntilItemClickable("ProxyTargetNameField");
					GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ProxyTargetNameField"))).sendKeys(GlobalData.DataElements.get("ProxyUser"));
					Thread.sleep(4000);
					ReusableFunctions.waitTillElementLoad("ProxyUserList",8000);
					List<WebElement> userList = GlobalData.driver.findElements(By.xpath(GlobalData.ObjectRepo.get("ProxyUserList")));
					int i=1;
					for(WebElement user: userList){
						WebElement list = GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("ProxyUserList")+"["+i+"]/td[2]/div/div[2]"));
						if(list.getText().contains(GlobalData.DataElements.get("ProxyUser"))){
							list.click();
							flag="Pass";
							break;
						}
						i++;
					}
					ReusableFunctions.waitUntilItemClickable("ProxyTargetOKBtn");
					ReusableFunctions.clickbyxpath(GlobalData.ObjectRepo.get("ProxyTargetOKBtn"), "Open Union Drop down");
					Thread.sleep(5000);
					while(!expc && counter <= 15){
						if(GlobalData.driver.findElement(By.xpath(GlobalData.ObjectRepo.get("UserMenuArrow"))).getText().contains("on behalf of")){
							expc = true;
						}else{
							Thread.sleep(4000);
							counter = counter+1; 
						}
					}
					flag = "Pass";
					TR.extentTest.log(LogStatus.PASS, "Successfully navigated to Proxy User Screen.");
					break;
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			TR.extentTest.log(LogStatus.FAIL, "Not able to navigated to Proxy User screen.");
			TR.captureScreenshot(date);
			flag = "False";
		}
		return flag;
	}
}