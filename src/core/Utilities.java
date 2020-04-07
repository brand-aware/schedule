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
import java.util.Calendar;

public class Utilities {
	
	private Properties properties;
	
	public Utilities(Properties p){
		properties = p;
	}

	public final boolean isStarted() throws IOException{
		boolean started = false;
		String path = properties.getProgressDir();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if(files != null){
			for(int x = 0; x < files.length; x++){
				BufferedReader reader = new BufferedReader(new FileReader(files[x]));
				if(reader.readLine() != null){
					//System.out.println(path + "\n" + files[x].getAbsolutePath());
					started = true;
				}
				reader.close();
			}
		}
		
		return started;
	}
	
	public final void recordPrevious() throws IOException{
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day =  calendar.get(Calendar.DATE);
		int dow = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		
		String path = properties.getPrevious();
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(year +
				"," +
				month +
				"," +
				day +
				"," +
				dow +
				"," +
				hour +
				"," +
				minute);
		writer.close();
	}
	
	public final String getMonth(int month){
		String name = "";
		
		if(month == 0){
			name = "January";
		}else if(month == 1){
			name = "February";
		}else if(month == 2){
			name = "March";
		}else if(month == 3){
			name = "April";
		}else if(month == 4){
			name = "May";
		}else if(month == 5){
			name = "June";
		}else if(month == 6){
			name = "July";
		}else if(month == 7){
			name = "August";
		}else if(month == 8){
			name = "September";
		}else if(month == 9){
			name = "October";
		}else if(month == 10){
			name = "November";
		}else if(month == 11){
			name = "December";
		}
		
		return name;
	}
	
	public final Properties getProperties(){
		return properties;
	}
	
	public final void setProperties(Properties p){
		properties = p;
	}
	
	public final boolean isWhitespace(String line){
		if(line.compareTo("") == 0 ||
				line.compareTo(" ") == 0 ||
				line.compareTo("\n") == 0){
			return true;
		}
		return false;
	}
}
