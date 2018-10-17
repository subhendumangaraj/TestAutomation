package CommonFunctionLibrary;

 

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

 
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.rowset.internal.Row;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import CommonFunctionLibrary.GlobalData;


public class Excel
{
	static int reqrow = 0;
	static int DataColCount = 0;
	public static int Exceldata(String DataFilePath,String ColumnName,String DataSheetName,int callcount, String TestCaseName,int datacounter) throws BiffException, IOException
	{
		String Key = "";
		String Value= "";
		String tcid = "";
		reqrow = 0; int matchcount=0;
		File inputworkbook=new File(".\\TestData\\"+DataFilePath);
		XSSFWorkbook Dataworkbook;
		
		try {
			Dataworkbook = new XSSFWorkbook(inputworkbook);
			
			//Get first/desired sheet from the workbook
			XSSFSheet DataSheet = Dataworkbook.getSheet(DataSheetName);
			int Datarowcount = DataSheet.getLastRowNum();
			XSSFRow r = DataSheet.getRow(1);
			DataColCount =  r.getLastCellNum();
			
			if(datacounter==0)
			{
				for(int h=0;h<=Datarowcount;h++)
				{
					tcid = DataSheet.getRow(h).getCell(2).getStringCellValue();
					if(tcid.equalsIgnoreCase(TestCaseName))
					{
						datacounter++;
					}
				}
			}
			
			for(int y=0;y<=Datarowcount;y++)
			{
				tcid = DataSheet.getRow(y).getCell(2).getStringCellValue();
				
				if(tcid.equalsIgnoreCase(TestCaseName)&&matchcount==callcount)
				{
					reqrow = y;
				}
				
				if(tcid.equalsIgnoreCase(TestCaseName))
					matchcount++;
			}
			
			for(int u=0;u<DataColCount;u++)
			{
				try{
					Key = DataSheet.getRow(0).getCell(u).getStringCellValue();
					Value = DataSheet.getRow(reqrow).getCell(u).getStringCellValue();
					
				}catch(Exception e){
					Value="";
				}
				GlobalData.DataElements.put(Key, Value);
				
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return datacounter;
		
	}
	
	public static String WriteIntoExcel()
	{
		
		return null;
	}
	
}