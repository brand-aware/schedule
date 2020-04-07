/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package ui;
import java.util.ArrayList;


public class Schedule {
	
	private String title;
	private ArrayList<Integer> minutes = new ArrayList<Integer>();
	private ArrayList<Integer> days = new ArrayList<Integer>();
	private ArrayList<Integer> frequency = new ArrayList<Integer>();
	
	public Schedule(String name){
		title = name;
	}

	public void addDay(int min, int day, int numTimes){
		minutes.add(min);
		days.add(day);
		frequency.add(numTimes);
	}
	
	public ArrayList<Integer> getDays(){
		return days;
	}
	
	public Integer getTime(int day){
		return minutes.get(day);
	}
	public Integer getFrequency(int day){
		return frequency.get(day);
	}
	public String getActivity(){
		return title;
	}
}
