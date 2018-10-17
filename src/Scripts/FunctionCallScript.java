package Scripts;

 

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.relevantcodes.extentreports.LogStatus;
import CommonFunctionLibrary.Configuration;
import CommonFunctionLibrary.Excel;
import CommonFunctionLibrary.GetProperties;
import CommonFunctionLibrary.GlobalData;
import CommonFunctionLibrary.TestReports;


public class FunctionCallScript {

   /**

     * This script is for calling the required .
     * This script will access the module, testcases sheets to check for .
     * Script Name   : <b>FunctionCallScript</b>
     * Generated     : <b>Mar 08, 2018 6:43:36 PM</b>
     * Description   : Functional Test Script
     * @since  2016/12/14
     * @author Bishnu

     */
	
	public String FunctionCall(String date,String TCName,String TSFilePath,String DataFilePath,String ObjectRepoPath) throws Exception
	{
		
		TestReports TR = new TestReports();
		String DataSheetName = "";
		String TestCaseName="",Steps="",ExecKey="",keyword="",BODDate="",Param1="",Param2="",Param3="",ObjectProp="";
		
		boolean flag = false;
		String retval = "";
		int datacounter = 0,callcount =0,firststep=0;
		
		//CommonFunctionLibrary.GlobalData GD = new CommonFunctionLibrary.GlobalData();
		//GD.GlobalData();
		ArrayList Objects = new ArrayList();
		FileInputStream TestStepfile = new FileInputStream(new File(".\\TestSteps\\"+TSFilePath));
		FileInputStream Datafile = new FileInputStream(new File(".\\TestData\\"+DataFilePath));
		XSSFWorkbook TSworkbook = new XSSFWorkbook(TestStepfile);
		XSSFWorkbook Dataworkbook = new XSSFWorkbook(Datafile);
		
		GetProperties obj = new GetProperties(".\\src\\ObjectRepository\\"+ObjectRepoPath);
		obj.ReadProperty();
		Configuration configobj = new Configuration(".\\Configuration\\Config.Properties");
		configobj.ReadProperty();
		
		//Excel.Exceldata( DataFilePath, "", DataSheetName, RowNum);
		
		//Get first/desired sheet from the workbook
		XSSFSheet TestStepSheet = TSworkbook.getSheetAt(0);
		int TSrowcount = TestStepSheet.getLastRowNum();
		
		MLoop:  for(int j=0;j<TSrowcount;j++)
		{
			TestCaseName = TestStepSheet.getRow(j).getCell(0).getStringCellValue();
			//BODDate = TestStepSheet.getRow(j).getCell(4).getStringCellValue();
			//String Date = GlobalData.ConfigData.get("BODDate");
			//if(TestCaseName.equalsIgnoreCase(TCName)&&BODDate.equalsIgnoreCase(Date))
			if(TestCaseName.equalsIgnoreCase(TCName))
			{
				flag=true;
				firststep = j;
			}
			while(flag)
			{
				Objects.clear();
				TestCaseName = TestStepSheet.getRow(j).getCell(0).getStringCellValue();
				Objects.add(TestCaseName);
				/*if (callcount!=datacounter)
					j=j-1;
				else if(callcount==datacounter)
					callcount=0;*/
				
				if(TestCaseName.equalsIgnoreCase("ENDD")){break MLoop;}
				
				//else if(TestCaseName.equalsIgnoreCase("ENDD")){j=firststep;continue;}
				
				Steps = TestStepSheet.getRow(j).getCell(1).getStringCellValue();
				Objects.add(Steps);
				
				ExecKey = TestStepSheet.getRow(j).getCell(2).getStringCellValue();
				Objects.add(Steps);
				
				if(ExecKey.equalsIgnoreCase("N")){j++;continue;}
				
				try{
					ObjectProp = TestStepSheet.getRow(j).getCell(3).getStringCellValue();
					Objects.add(ObjectProp);
				}catch(Exception e){
					Objects.add(ObjectProp);
				}
				
				keyword = TestStepSheet.getRow(j).getCell(4).getStringCellValue();
				Objects.add(keyword);
				
				//           Objects.add(BODDate);
				
				Param1 = TestStepSheet.getRow(j).getCell(5).getStringCellValue();
				Objects.add(Param1);
				Param2 = TestStepSheet.getRow(j).getCell(6).getStringCellValue();
				Objects.add(Param2);
				Param3 = TestStepSheet.getRow(j).getCell(7).getStringCellValue();
				Objects.add(Param3);
				Objects.add(ObjectRepoPath);
				Objects.add(date);
				
				try
				{
					DataSheetName = TestStepSheet.getRow(j).getCell(8).getStringCellValue();
				}
				catch(Exception e)
				{
					DataSheetName="";
				}
				if(!DataSheetName.equals(""))
				{
					datacounter = Excel.Exceldata( DataFilePath, "", DataSheetName, callcount, TestCaseName,datacounter);
					callcount++;
				}
				j++;
				
				//GetProperties obj = new GetProperties(ObjectRepoPath);
				
				//Make a call to the required function
				try
				{
					Class params[] = new Class[Objects.size()];
					Object[] objvalue = new Object[Objects.size()];
					//int i = Objects.size();
					for(int k=0;k<Objects.size();k++)
					{
						objvalue[k] = Objects.get(k);
						if (Objects.get(k) instanceof Integer) {
							params[k] = Integer.class;
						} else if (Objects.get(k) instanceof String) {
							params[k] = String.class;
						}
					}
					
					FunctionLibrary theobj = new FunctionLibrary();
					Class cl = theobj.getClass();
					Method method = cl.getMethod(keyword, params);
					
					System.out.println("Executing: "+Steps+": "+keyword);
					Object returnvalue = method.invoke(theobj, objvalue);
					retval = returnvalue.toString();
					if(retval.equalsIgnoreCase("PASS"))
					{
						TR.extentTest.log(LogStatus.PASS, keyword+ "Passsed Successfully");
						//flag=false;
					}
					else
					{
						TR.extentTest.log(LogStatus.FAIL, keyword+ "Passsed Successfully");
						break MLoop;
					}
				}
				catch(Exception E)
				{
					E.printStackTrace();
				}
			}
		}
		return retval;
	}
	
}