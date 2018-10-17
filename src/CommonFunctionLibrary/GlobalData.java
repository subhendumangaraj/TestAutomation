package CommonFunctionLibrary;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;

public class GlobalData 
{
	public static HashMap<String, String> DataElements = new HashMap<String,String>();
	public static HashMap<String, String> ObjectRepo = new HashMap<String,String>();
	public static HashMap<String, String> ConfigData = new HashMap<String,String>();
	public static WebDriver driver;
	/*public void GlobalData(){
		ConfigData.put("BODDate", "Date-1");
		
	}*/
	
}