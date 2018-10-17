package CommonFunctionLibrary;

 

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

 

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.http.conn.params.ConnManagerParams;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import freemarker.*;


public class TestReports 
{
	public static ExtentReports extent = null;
	public static ExtentTest extentTest = null;
	public static String extentReportFile= "";
	public static String extentReportImage= "";
	
	public void setreportfilepath(String date) 
	{
		extentReportFile = ".\\Reports\\"+date+"\\extentReportFile.html";
		extentReportImage =".\\extentReportImage.jpg";
		//System.out.println(extentReportFile);
		
		//System.out.println(extentReportImage);
		
	}
	
	public void captureScreenshot(String date) 
	{
		try{
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
			Date now = new Date();
			String date1 = sdfDate.format(now);
			
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "jpg", new File(".\\Reports\\"+date+"\\screenshot"+date1+".jpg"));
			extentTest.log(LogStatus.INFO, "Image", "Image example: " + extentTest.addScreenCapture(".\\screenshot"+date1+".jpg"));
			
		}catch(Exception e)
		{
			System.out.println("Error while taking the screenshot");
		}
		
	}
	
}