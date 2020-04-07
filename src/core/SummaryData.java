/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;

import java.util.ArrayList;

public class SummaryData {

	private Utilities utilities;
	private String name = "";
	private int year = -1;
	private int month = -1;
	private int completed = 0;
	private int missed = 0;
	private int total = 0;
	
	ArrayList<Activity> activityList;
	
	
	public SummaryData(Utilities utils, String activity){
		utilities = utils;
		name = activity;
		activityList = new ArrayList<Activity>();
	}
	
	public void addActivity(Activity activity){
		activityList.add(activity);
	}
	
	public void setGroup(int yr, int mth){
		year = yr;
		month = mth;
	}
	public void setCompleted(int comp){
		completed = comp;
	}
	public void setMissed(int miss){
		missed = miss;
	}
	public void setTotal(int time){
		total = time;
	}
	
	public void addMissed(){
		missed++;
	}
	public void addCompleted(){
		completed++;
	}
	
	public void addTime(int amount){
		total += amount;
	}
	
	public String getName(){
		return name;
	}
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	public int getCompleted(){
		return completed;
	}
	public int getMissed(){
		return missed;
	}
	public int getTotalTime(){
		return total;
	}
	
	public ArrayList<Activity> getActivityList(){
		return activityList;
	}
	
	public String printSummarizedData(){
		String buffer = "";
		buffer += name + ": ";
		buffer += utilities.getMonth(month) + " - " + year + "   ";
		buffer += "completed: " + completed;
		buffer += ", missed: " + missed;
		return buffer;
	}
}
