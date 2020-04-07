/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Activity {

	private String name = "";
	private String date = "";
	private int duration;
	private int frequency;
	private Properties properties;
	private int year;
	private int month;
	private int day;
	
	private boolean completed = false;
	
	public Activity(String title, String time, Integer length, Integer order, Properties p){
		name = title;
		date = time;
		duration = length;
		frequency = order;
		properties = p;
	}
	
	public String getName(){
		return name;
	}
	public String getDate(){
		return date;
	}
	public int getDuration(){
		return duration;
	}
	public int getFrequency(){
		return frequency;
	}
	
	public void setDate(int yr, int mth, int dy){
		year = yr;
		month = mth;
		day = dy;
	}
	public void setSuccess(boolean success){
		completed = success;
	}
	public boolean getCompleted(){
		return completed;
	}
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	
	public void recordProgress(boolean completed) throws IOException{
		String result = "";
		if(completed){
			result = "true";
		}else{
			result = "false";
		}
		String dirPath = properties.getProgressDir();
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		String path = dirPath + File.separator + name + "_progress.txt";
		File file = new File(path);
		boolean exists = file.exists();
		String buffer = "";
		if(exists){
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while(reader.ready()){
				String line = reader.readLine();
				buffer += line + "\n";
			}
			reader.close();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		if(exists){
			writer.write(buffer);
		}
		String sMonth;
		if(month > 10){
			sMonth = "0" + month;
		}else{
			sMonth = month + "";
		}
		writer.write(
				year + 
				"," + 
				sMonth + 
				"," + 
				day + 
				"," + 
				duration + 
				"," + 
				frequency +
				"," +
				result);
		writer.close();
	}
	
	public String printActivity(){
		String result = "";
		if(completed){
			result = "true";
		}else{
			result = "false";
		}
		
		return year + 
				"," + 
				month + 
				"," + 
				day + 
				"," + 
				duration + 
				"," + 
				frequency +
				"," +
				result;
	}
}