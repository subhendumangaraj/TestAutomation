package CommonFunctionLibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.relevantcodes.extentreports.LogStatus;

public class FileDownloadFromSFTP {

	Session session = null;
	Channel channel = null;
	ChannelSftp downloadChannelSftp = null;

	public void connectSFTPLocation(String userName, String password, String hostName, String port) throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(userName, hostName, Integer.valueOf(port));
		session.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		System.out.println("Host connected");
	}

	public void createSftpChannel(String channelType) throws JSchException {
		channel = session.openChannel(channelType);
		channel.connect();
		System.out.println(channelType + "channel opened and connected");
		downloadChannelSftp = (ChannelSftp) channel;
	}

	public void fetchFileToLocal(String location, String interfaceName, String fileName,String destination) {
		try {
			int readCount;
			String interfacePath = downloadChannelSftp.getHome() + location + interfaceName;
			downloadChannelSftp.cd(interfacePath);
			System.out.println("Path:"+downloadChannelSftp.pwd());
			System.out.println("test:"+interfacePath+fileName);
			File downloadFile = new File(interfacePath+fileName);
			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(downloadChannelSftp.get(downloadFile.getName()));
			File newFile = new File(destination+fileName);
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			System.out.println("Writing into file "+fileName+" in D drive...");
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			System.out.println("File Downloaded successfully...");
			bis.close();
			bos.close();
		} catch (IOException | SftpException exception) {
			exception.printStackTrace();
		}

	}
	
	public void Validate_EMPID(String destinationLocation,String fileName,String date){
		LineIterator it = null;
		boolean flag=true;
		TestReports TR = new TestReports();
		try{
			
			File reqFile = new File(destinationLocation+fileName);
			it = FileUtils.lineIterator(reqFile, "UTF-8");
			int i=0;
			while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] temp = line.split(",");
		        String EmpId = temp[0];
		        i++;
		        if(EmpId.equalsIgnoreCase("Emp ID") || EmpId.equalsIgnoreCase("\"F\"")){continue;}
		        if(!(EmpId.length()==8) && (EmpId.matches("[A-Za-z0-9]+")))
		        {
		        	flag=false;
		        	System.out.println("Emp ID Error Record: "+line);
		        	TR.extentTest.log(LogStatus.FAIL, "Emp ID validation failed at Row# "+ i );
					//TR.captureScreenshot(date);
		        	break;
		        }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		 finally {
			 if(flag){
				TR.extentTest.log(LogStatus.PASS, " Emp ID validation completed successfully");
				//TR.captureScreenshot(date);
			 }
		    LineIterator.closeQuietly(it);
		}
	}
	
	public static void Validate_HireDate(String destinationLocation,String fileName,String date){
		LineIterator it = null;
		boolean flag=true;
		TestReports TR = new TestReports();
		final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		try{
			File reqFile = new File(destinationLocation+fileName);
			it = FileUtils.lineIterator(reqFile, "UTF-8");
			int i=0;
			while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] temp = line.split(",");
		        String HireDate = temp[1];
		        String firstChar = temp[0];
		        i++;
		        if(HireDate.equalsIgnoreCase("Hire Dt") || firstChar.equalsIgnoreCase("\"F\"")){continue;}
		        
		        if(HireDate != null){
		        	try{
		        		java.util.Date ret = sdf.parse(HireDate.trim());
		        		if (!sdf.format(ret).equals(HireDate.trim())) 
		        		{
		        			flag=false;
		        			System.out.println("HireDate Error Record: "+line);
		        			TR.extentTest.log(LogStatus.FAIL, "Hire Date validation failed at Row# "+ i + " due to Date Format.");
							TR.captureScreenshot(date);
							break;
		        		}
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }else{
		        	flag = false;
		        	System.out.println("Error Record: "+line);
		        	TR.extentTest.log(LogStatus.FAIL, "Hire Date validation failed at Row# "+ i +" because of Null entry." );
					//TR.captureScreenshot(date);
		        }
			}
		}catch(Exception e){e.printStackTrace();}
		 finally {
			 if(flag){
				 TR.extentTest.log(LogStatus.PASS, " Hire Date validation completed successfully");
				 //TR.captureScreenshot(date);
			 }
		    LineIterator.closeQuietly(it);
		}
	}
	
	public static void Validate_LastName(String destinationLocation,String fileName,String date){
		LineIterator it = null;
		boolean flag=true;
		TestReports TR = new TestReports();
		final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		try{

			File reqFile = new File(destinationLocation+fileName);
			it = FileUtils.lineIterator(reqFile, "UTF-8");
			int i=0;
			while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] temp = line.split(",");
		        String LastName = temp[2];
		        String firstChar = temp[0];
		        i++;
		        if(LastName.equalsIgnoreCase("Last Name") || firstChar.equalsIgnoreCase("\"F\"")){continue;}
		        
		        if(LastName != null){
		        	try{
		        		if (!((LastName.length()<=30) && (LastName.matches("[A-Za-z0-9\\s]+")))) 
		        		{
		        			flag=false;
		        			System.out.println("LastName Error Record: "+line);
		        			TR.extentTest.log(LogStatus.FAIL, "Last Name validation failed at Row# "+ i + " due to Length or Format.");
							//TR.captureScreenshot(date);
							break;
		        		}
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }else{
		        	flag = false;
		        	System.out.println("Error Record: "+line);
		        	TR.extentTest.log(LogStatus.FAIL, "Last Name validation failed at Row# "+ i +" because of Null entry." );
					//TR.captureScreenshot(date);
		        }
			}
		
		}catch(Exception e){e.printStackTrace();}
		 finally {
			 if(flag){
				 TR.extentTest.log(LogStatus.PASS, " Last Name validation completed successfully");
				 //TR.captureScreenshot(date);
			 }
		    LineIterator.closeQuietly(it);
		}
	}
	
	public static void Validate_FirstName(String destinationLocation,String fileName,String date)
	{

		LineIterator it = null;
		boolean flag=true;
		TestReports TR = new TestReports();
		final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		try{

			File reqFile = new File(destinationLocation+fileName);
			it = FileUtils.lineIterator(reqFile, "UTF-8");
			int i=0;
			while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] temp = line.split(",");
		        String FirstName = temp[3];
		        String firstChar = temp[0];
		        i++;
		        if(FirstName.equalsIgnoreCase("First Name") || firstChar.equalsIgnoreCase("\"F\"")){continue;}
		        
		        if(FirstName != null){
		        	try{
		        		if (!((FirstName.length()<=30) && (FirstName.matches("[A-Za-z0-9\\s]+")))) 
		        		{
		        			flag=false;
		        			System.out.println("First Name Error Record: "+line);
		        			TR.extentTest.log(LogStatus.FAIL, "First Name validation failed at Row# "+ i + " due to Length or Format.");
							//TR.captureScreenshot(date);
							break;
		        		}
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }else{
		        	flag = false;
		        	System.out.println("Error Record: "+line);
		        	TR.extentTest.log(LogStatus.FAIL, "First Name validation failed at Row# "+ i +" because of Null entry." );
					//TR.captureScreenshot(date);
		        }
			}
		
		}catch(Exception e){e.printStackTrace();}
		 finally {
			 if(flag){
				 TR.extentTest.log(LogStatus.PASS, " First Name validation completed successfully");
				 //TR.captureScreenshot(date);
			 }
		    LineIterator.closeQuietly(it);
		}
	
	}
	
	public static void Validate_MiddleInitial(String destinationLocation,String fileName,String date)
	{


		LineIterator it = null;
		boolean flag=true;
		TestReports TR = new TestReports();
		final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		try{

			File reqFile = new File(destinationLocation+fileName);
			it = FileUtils.lineIterator(reqFile, "UTF-8");
			int i=0;
			while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] temp = line.split(",");
		        String MiddleInitial = temp[4];
		        String firstChar = temp[0];
		        i++;
		        if(MiddleInitial.equalsIgnoreCase("MI") || firstChar.equalsIgnoreCase("\"F\"")){continue;}
		        
		        if(MiddleInitial.equals(null) || MiddleInitial.equals("")){continue;}
		        
		        else if(MiddleInitial != null){
		        	try{
		        		if (!((MiddleInitial.length()<=1) && (MiddleInitial.matches("[A-Za-z0-9]+")))) 
		        		{
		        			flag=false;
		        			System.out.println("Middle Initial Error Record: "+line);
		        			TR.extentTest.log(LogStatus.FAIL, "Middle Initial validation failed at Row# "+ i + " due to Length or Format.");
							//TR.captureScreenshot(date);
							break;
		        		}
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }else{
		        	flag = false;
		        	System.out.println("Error Record: "+line);
		        	TR.extentTest.log(LogStatus.FAIL, "Middle Initial validation failed at Row# "+ i +" because of Null entry." );
					//TR.captureScreenshot(date);
		        }
			}
		
		}catch(Exception e){e.printStackTrace();}
		 finally {
			 if(flag){
				 TR.extentTest.log(LogStatus.PASS, " Middle Initial validation completed successfully");
				 //TR.captureScreenshot(date);
			 }
		    LineIterator.closeQuietly(it);
		}
	
	
	}

}
