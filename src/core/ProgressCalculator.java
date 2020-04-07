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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProgressCalculator {
	
	private Utilities utilities;
	private Properties properties;
	private ArrayList<Activity> activities;
	private ArrayList<SummaryData> summary;
	private boolean initialized = false;
	
	public ProgressCalculator(Utilities utils) throws IOException{
		utilities = utils;
		properties = utilities.getProperties();
		activities = new ArrayList<Activity>();
		summary = new ArrayList<SummaryData>();
	}
	
	private final void loadProgress() throws IOException{
		String dirPath = properties.getProgressDir();
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		for(int x = 0; x < files.length; x++){
			File file = files[x];
			String name = file.getName();
			name = name.substring(0, name.lastIndexOf("_"));
			String path = file.getAbsolutePath();
			//System.out.println(path);
			FileReader fr = new FileReader(path);
			BufferedReader reader = new BufferedReader(fr);
			while(reader.ready()){
				String line = reader.readLine();
				if(!utilities.isWhitespace(line)){
					String[] data = line.split(",");
					String section = data[0];
					int year = Integer.parseInt(section);
					section = data[1];
					int month = Integer.parseInt(section);
					section = data[2];
					int day = Integer.parseInt(section);
					section = data[3];
					int duration = Integer.parseInt(section);
					section = data[4];
					int frequency = Integer.parseInt(section);
					String result = data[5];
					
					boolean success = false;
					if(result.compareTo("true") == 0){
						success = true;
					}
					String time = month + " - " + day + " - " + year;
					Activity activity = new Activity(
							name,
							time,
							duration,
							frequency,
							properties);
					activity.setDate(year, month, day);
					activity.setSuccess(success);
					activities.add(activity);
				}
			}
			reader.close();
		}
	}
	
	private final void calculateSummary(){
		summary = new ArrayList<SummaryData>();
		SummaryData data = null;
		int year = -1;
		int month = -1;
		String name = "";
		for(int x = 0; x < activities.size(); x++){
			Activity activity = activities.get(x);
			String activityName = activity.getName();
			if(year != activity.getYear() || 
					month != activity.getMonth() ||
					name.compareTo(activityName) != 0){
				if(data != null){
					summary.add(data);
				}
				year = activity.getYear();
				month = activity.getMonth();
				name = activity.getName();
				data = new SummaryData(utilities, name);
				data.setGroup(year, month);
			}
			int duration = activity.getDuration();
			data.addActivity(activity);
			data.addTime(duration);
			if(activity.getCompleted()){
				data.addCompleted();
			}else{
				data.addMissed();
			}
		}
		summary.add(data);
	}
	
	public final void init() throws IOException{
		if(!initialized){
			loadProgress();
			calculateSummary();
			initialized = true;
		}else{
			activities = new ArrayList<Activity>();
			summary = new ArrayList<SummaryData>();
			loadProgress();
			calculateSummary();
		}
	}
	
	public final ArrayList<SummaryData> getSummary(){
		return summary;
	}
}
