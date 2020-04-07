/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;
import java.io.File;

import ui.ProgressScreen;

public class Properties {
	
	private ProgressScreen progressScreen;
	private ProgressChecker progressChecker;
	private ProgressCalculator progressCalculator;
	
	private String logo;
	private String backupTag;
	private String background;
	private String backgroundPopup;
	private String company;
	private String company_iframe;
	private String users;
	private String username;
	private String rootDir;
	private String schedules;
	private String progress;
	private String previous;
	private String activities;
	private String sampleChart;
	private String importExtension;
	
	private String[] measurement;
	private String selectedActivity;
	private String selectedMeasurement;
	
	public Properties(String root){
		rootDir = root + File.separator;
		logo = rootDir + "img" + File.separator + "logo.png";
		sampleChart = rootDir + "img" + File.separator + "sample_img.png";
		users = rootDir + "settings" + File.separator + "user_list.txt";
		background = rootDir + "img" + File.separator + "background.png";
		backgroundPopup = rootDir + "img" + File.separator + "background_popup.png";
		company = rootDir + "img" + File.separator + "company.png";
		company_iframe = rootDir + "img" + File.separator + "company_iframe.png";
		
		schedules = "schedules" + File.separator;
		activities = "activities.txt";
		progress = "progress";
		previous = "previous.txt";
		
		measurement = new String[]{"number completed", "time spent"};
		importExtension = ".scd";
	}
	
	public void setBackupTag(String tag){
		backupTag = tag;
	}
	public void setSelectedActivity(String activity){
		selectedActivity = activity;
	}
	public void setSelectedMeasurement(String progress){
		selectedMeasurement = progress;
	}
	public void setProgressScreen(ProgressScreen ps){
		progressScreen = ps;
	}
	public void setProgressChecker(ProgressChecker pc){
		progressChecker = pc;
	}
	public void setProgressCalculator(ProgressCalculator pc){
		progressCalculator = pc;
	}
	
	public String getLogo(){
		return logo;
	}
	public String getBackground(){
		return background;
	}
	public String getBackgroundPopup(){
		return backgroundPopup;
	}
	public String getSampleChart(){
		return sampleChart;
	}
	public String getUsers(){
		return users;
	}
	public String getRootDir(){
		return rootDir;
	}
	public String getCompany(){
		return company;
	}
	public String getCompanySmall(){
		return company_iframe;
	}
	public String getBackupPath(){
		return rootDir + username + "_" + backupTag + importExtension;
	}
	public String getSchedules(){
		return rootDir + username + File.separator + activities;
	}
	public String getScheduleDir(){
		return rootDir + username + File.separator + schedules;
	}
	public String getProgressDir(){
		return rootDir + username + File.separator + progress;
	}
	public String getPrevious(){
		return rootDir + username + File.separator + previous;
	}
	public String getImportExtention(){
		return importExtension;
	}
	
	public String[] getMeasurement(){
		return measurement;
	}
	public String getSelectedActivity(){
		return selectedActivity;
	}
	public String getSelectedMeasurement(){
		return selectedMeasurement;
	}
	public ProgressScreen getProgressScreen(){
		return progressScreen;
	}
	public ProgressChecker getProgressChecker(){
		return progressChecker;
	}
	public ProgressCalculator getProgressCalculator(){
		return progressCalculator;
	}
	
	public void setUsername(String name){
		username = name;
	}
	public String getUsername(){
		return username;
	}
}
