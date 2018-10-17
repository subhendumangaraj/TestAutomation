package Scripts;

 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctionLibrary.TestReports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
//import org.apache.http.conn.params.ConnManagerParams;
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
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
 

public class DriverScript {

    /**

     * This script is for Driving the complete project.
     * This script will access the module, testcases sheets to check for .
     * Script Name   : <b>DriverScript</b>
     * Generated     : <b>Mar 01, 2018 6:43:36 PM</b>
     * Description   : Functional Test Script
     * @since  2016/12/14
     * @author Bishnu

     */

 

     public static void main(String args[]) throws Exception {
    	 try {
    		 	//BasicConfigurator.configure();
    		 	String log4jConfPath = "./Configuration/log4j.properties";
    		 	PropertyConfigurator.configure(log4jConfPath);
    		 	FunctionCallScript FC = new FunctionCallScript();
    		 	TestReports TR = new TestReports();
    		 	FileInputStream Modulefile = new FileInputStream(new File(".\\Module\\XPOModules.xlsx"));
    		 	XSSFWorkbook workbook = new XSSFWorkbook(Modulefile);
    		 	
    		 	
    		 	//Get first/desired sheet from the workbook
    		 	
    		 	XSSFSheet sheet = workbook.getSheetAt(0);
    		 	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd_HH-mm-ss");
    		 	Date now = new Date();
    		 	String date = sdfDate.format(now);
    		 	int rowcount = sheet.getLastRowNum();
    		 	for(int i=0;i<=rowcount;i++)
    		 		{
    		 			int passcounter = 0, failcounter = 0;
    		 			String msgeuuid="";
    		 			//workbook = new XSSFWorkbook(file);
    		 			//sheet = workbook.getSheetAt(0);
    		 			String ind = "",ModuleName="",FileName="",chanName="",TCind="",TCName="",TSFilePath="",DataFilePath="",ObjectRepoPath="",DataSheetName="",ColNum="";
    		 			int NoOfCalls= 0;
    		 			try
    		 				{
    		 					ind = sheet.getRow(i).getCell(0).getStringCellValue();
    		 					ModuleName = sheet.getRow(i).getCell(1).getStringCellValue();
    		 					FileName = sheet.getRow(i).getCell(2).getStringCellValue();
    		 					ObjectRepoPath = sheet.getRow(i).getCell(3).getStringCellValue();
    		 					if(ind.equalsIgnoreCase("YES"))
    		 						{
    		 							FileInputStream TestCasefile = new FileInputStream(new File(".\\TestCases\\"+FileName+".xlsx"));
    		 							XSSFWorkbook TCworkbook = new XSSFWorkbook(TestCasefile);
    		 							//Get first/desired sheet from the workbook
    		 							XSSFSheet TCsheet = TCworkbook.getSheetAt(0);
    		 							int TCrowcount = TCsheet.getLastRowNum();
                                        for(int j=0;j<=TCrowcount;j++)
                                        	{
                                        		TCind = TCsheet.getRow(j).getCell(0).getStringCellValue();
                                        		TCName = TCsheet.getRow(j).getCell(1).getStringCellValue();
                                        		TSFilePath = TCsheet.getRow(j).getCell(2).getStringCellValue();
                                        		DataFilePath = TCsheet.getRow(j).getCell(3).getStringCellValue();
                                        		//DataSheetName = TCsheet.getRow(j).getCell(4).getStringCellValue();
                                        		//ColNum = TCsheet.getRow(j).getCell(5).getStringCellValue();
                                        		if(TCind.equalsIgnoreCase("YES"))
                                        			{
                                        				TR.setreportfilepath(date);
                                        				TR.extent = new ExtentReports(TR.extentReportFile, false);
                                        				TR.extentTest = TR.extent.startTest("Test Result for Test case: "+TCName);
                                        				String Status = FC.FunctionCall(date,TCName,TSFilePath,DataFilePath,ObjectRepoPath);
                                        				if(Status.equalsIgnoreCase("PASS"))
                                        					{
                                        						TR.extentTest.log(LogStatus.PASS, TCName+" passed");
                                        					}
                                        				else
                                        					{
                                        						TR.extentTest.log(LogStatus.FAIL, TCName+" failed");
                                        					}
                                        						//TR.captureScreenshot(TCName);
                                        						//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                                        						//ImageIO.write(image, "jpg", new File(".\\Reports\\SG-WMS-TC1\\image.jpg"));
                                        						//TR.extentTest.log(LogStatus.INFO, "Image", "Image example: " + TR.extentTest.addScreenCapture(".\\image.jpg"));
                                        						// close report.
                                        						TR.extent.endTest(TR.extentTest);
                                        						// writing everything to document.
                                        						TR.extent.flush();
                                        						Thread.sleep(3000);
                                        			}
                                        	}
    		 						}
    		 				}
    		 				catch(Exception e)
    		 					{
    		 						e.printStackTrace();
    		 						TR.extentTest.log(LogStatus.INFO,"Error Snapshot : " + TR.extentTest.addScreenCapture(TR.extentReportImage));
    		 						// close report.
    		 						TR.extent.endTest(TR.extentTest);
    		 						// writing everything to document.
    		 						TR.extent.flush();
                               }
                       }
    		 	
    	 		}
    	 		catch (Exception e) {
    	 			
                        e.printStackTrace();
                    }
    	 			//System.exit(0);
                }

}