package CommonFunctionLibrary;

import java.io.*;
import java.util.*;

public class GetProperties 
{
	String str;
	private String filepath;

	String Key = "";
	String Value= "";

	public GetProperties(String filepath) 
	{
		this.filepath = filepath;
	}
	
	public void ReadProperty() 
	{
		String propval = "";
		try {
			int check = 0;
			while (check == 0) {
				check = 1;
				File cfgfile = new File(filepath);
				if (cfgfile.exists()) {
					FileInputStream fis = new FileInputStream(cfgfile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					String line = null;
					while ((line = br.readLine())!=null)
					{
						if (line.contains(":"))
						{
							String key_value[] = line.split(":");
							Key = key_value[0];
							Value = key_value[1];
							GlobalData.ObjectRepo.put(Key, Value);
						}
					}
				} else {
					check = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return propval;
	}
}