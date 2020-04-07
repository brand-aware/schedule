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
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

import config.ConfigProgressChecker;
import ui.ProgressScreen;
import ui.Schedule;

public class ProgressChecker extends ConfigProgressChecker{
	
	private Properties properties;
	private Utilities utilities;
	private ProgressScreen progressScreen;
	
	private int[] lastDate;
	private ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	private ArrayList<Activity> todo = new ArrayList<Activity>();
	private ConcurrentHashMap<Integer, Integer> monthLookup;
	
	public ProgressChecker(Utilities utils) throws IOException{
		utilities = utils;
		properties = utilities.getProperties();
		utilities.setProperties(properties);
		
		monthLookup = new ConcurrentHashMap<Integer, Integer>();
		monthLookup.put(JANUARY, JANUARY_DAYS);
		monthLookup.put(FEBRUARY, FEBRUARY_DAYS);
		monthLookup.put(MARCH, MARCH_DAYS);
		monthLookup.put(APRIL, APRIL_DAYS);
		monthLookup.put(MAY, MAY_DAYS);
		monthLookup.put(JUNE, JUNE_DAYS);
		monthLookup.put(JULY, JULY_DAYS);
		monthLookup.put(AUGUST, AUGUST_DAYS);
		monthLookup.put(SEPTEMBER, SEPTEMBER_DAYS);
		monthLookup.put(OCTOBER, OCTOBER_DAYS);
		monthLookup.put(NOVEMBER, NOVEMBER_DAYS);
		monthLookup.put(DECEMBER, DECEMBER_DAYS);
		
		progressScreen = properties.getProgressScreen();
	}
	
	public final void run() throws IOException, InterruptedException{
		utilities.setProperties(properties);
		findSchedules();
		findScheduledItems();
		showGeneratedActivity();
	}
	
	private final void findSchedules() throws IOException{
		String path = properties.getPrevious();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		if(reader.ready()){
			String line = reader.readLine();
			System.out.println(line);
			String[] date = line.split(",");
			lastDate = new int[date.length];
			for(int x = 0; x < date.length; x++){
				lastDate[x] = Integer.parseInt(date[x]);
			}
		}
		reader.close();
		loadSchedules();
	}
	
	private final void loadSchedules() throws IOException{
		String path = properties.getScheduleDir();
		File dir = new File(path);
		File[] paths = dir.listFiles();
		BufferedReader reader;
		for(int x = 0; x < paths.length; x++){
			File file = paths[x];
			path = file.getAbsolutePath();
			reader = new BufferedReader(new FileReader(path));
			Schedule newSchedule = null;
			String data;
			String[] week = null;
			String[] day = null;
			if(reader.ready()){
				data = reader.readLine();
				week = data.split("#");
				newSchedule = new Schedule(week[0]);
			}
			reader.close();
			
			for(int y = 1; y < week.length; y++){
				String line = week[y];
				day = line.split(",");
				data = day[0];
				int min = Integer.parseInt(data);
				data = day[1];
				int dow = Integer.parseInt(data);
				data = day[2];
				int times = Integer.parseInt(data);
				newSchedule.addDay(min, dow, times);
			}
			schedules.add(newSchedule);
		}
	}
	
	private final void findScheduledItems(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		//int dow = calendar.get(Calendar.DAY_OF_WEEK);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		
		int lastYear = lastDate[0];
		int lastMonth = lastDate[1];
		int lastDay = lastDate[2];
		int lastDow = lastDate[3];
		lastDow--;
		int lastHour = lastDate[4];
		int lastMinute = lastDate[5];
		
		int yearDiff = year - lastYear;
		int monthDiff = month - lastMonth;
		int dayDiff = day - lastDay;
		int hourDiff = hour - lastHour;
		int minDiff = min - lastMinute;
		
		System.out.println("time diff1: " + yearDiff + ", " + monthDiff + ", " + dayDiff);
		
		if(minDiff < 0){
			minDiff = MINUTES_IN_HOUR + minDiff + min;
			hourDiff--;
		}
		if(hourDiff < 0){
			hourDiff = HOURS_IN_DAY + hourDiff + hour;
			dayDiff--;
		}
		if(dayDiff < 0){
			int monthDays = getMonthDays(lastYear, lastMonth);
			System.out.println("monthDays: " + monthDays);
			dayDiff = (monthDays - lastDay) + day;
			monthDiff--;
		}
		if(monthDiff < 0){
			monthDiff = (MONTHS_IN_YEAR - lastMonth) + month;
			yearDiff--;
		}
		
		System.out.println("time diff2: " + yearDiff + ", " + monthDiff + ", " + dayDiff);

		generateActivities(
				yearDiff, 
				monthDiff, 
				dayDiff, 
				lastDow, 
				lastYear, 
				lastMonth, 
				lastDay);
	}
	
	private final void generateActivities(
			int years, 
			int months, 
			int days, 
			int lastDow, 
			int year,
			int month,
			int date){
		
		int number = days;
		int leap = 0;
		if(year % 4 == 0){
			leap++;
		}
		number += years * DAYS_IN_YEAR;
		if(number >= DAYS_IN_YEAR){
			number += leap;
		}
		number += monthsToDays(year, months, month);
		
		System.out.println("number: " + number);
		
		for(int x = 0; x < schedules.size(); x++){
			Schedule schedule = schedules.get(x);
			System.out.println("schedule: " + schedule.getActivity());
			ArrayList<Integer> activityDays = schedule.getDays();
			int size = activityDays.size();
			int currentDay = lastDow;
			int place = 0;
			
			int holder = schedule.getDays().get(place);
			while(holder < currentDay){
				place++;
				holder = activityDays.get(place);
			}
			
			for(int y = 0; y < number ; y++){
				if(currentDay == DAYS_OF_THE_WEEK){
					currentDay = 0;
				}
				if(place == size){
					place = 0;
				}
				
				System.out.println("scheduledActivity(day): " + activityDays.get(place) + ", currentDay: " + currentDay + ", place: " + place + ", number: " + number + ", y:" + y);
				if(activityDays.get(place) == currentDay){
					System.out.println("generated activity: " + schedule.getActivity());
					int currentYear = getYear(year, month, date, currentDay);
					int currentMonth = getMonth(year, month, date, currentDay);
					int currentDate = getDay(year, month, date, currentDay);
					Activity activity = new Activity(
							schedule.getActivity(),
							((currentMonth + 1) + " - " + currentDate + " - " + currentYear),
							schedule.getTime(place),
							schedule.getFrequency(place),
							properties);
					activity.setDate(currentYear, currentMonth, currentDate);
					todo.add(activity);
					place++;
				}
				currentDay++;
			}
		}
	}
	private final void showGeneratedActivity() throws InterruptedException{
		if(todo.size() > 0){
			showGeneratedActivity(0);
		}else{
			try {
				utilities.recordPrevious();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final void showGeneratedActivity(int index) throws InterruptedException{
		if(index == todo.size()){
			try {
				utilities.recordPrevious();
				progressScreen.init();
				progressScreen.reloadChart(null, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("showing activity: " + index + ", " + todo.get(index).getName() + ", " + todo.get(index).getDate());
			progressScreen.generateActivity(index, todo.get(index));
			//activityInput.init(index);
			//activityInput.setActivity(todo.get(index));
		}
	}
	
	public final void endInput() throws IOException{
		progressScreen.reloadChart(null, null);
	}
	
	public final int getMonthDays(int year, int month){
		int days = -1;
		if(month == 1 || 
				month == 3 || 
				month == 5 || 
				month == 7 || 
				month == 8 || 
				month == 10 || 
				month == 12){
			days = 31;
		}else if(month == 4 ||
				month == 6 ||
				month == 9 ||
				month == 11){
			days = 30;
		}else if(month == 2){
			if(year % 4 == 0){
				days = 29;
			}else{
				days = 28;
			}
		}
		return days;
	}
	
	private final int monthsToDays(int year, int months, int lastMonth){
		int leap = 0;
		if(year % 4 == 0){
			leap++;
		}
		int days = 0;
		
		for(int x = 0; x < months; x++){
			days += monthLookup.get(lastMonth);
			if(lastMonth == FEBRUARY){
				days += leap;
			}
			if(lastMonth == DECEMBER){
				lastMonth = JANUARY;
				year++;
				if(year % 4 == 0){
					leap++;
				}else{
					leap = 0;
				}
			}else{
				lastMonth++;
			}
		}
		
		return days;
	}
	
	private final int getDay(int lastYear, int lastMonth, int lastDay, int days){
		int currentDay = lastDay + days;
		int month = lastMonth;
		
		int max = monthLookup.get(lastMonth);
		while(currentDay > max){
			currentDay -= max;
			if(month == MONTHS_IN_YEAR){
				month = 0;
				lastYear++;
			}else{
				month++;
			}
			max = monthLookup.get(month);
			if(month == FEBRUARY){
				if(lastYear % 4 == 0){
					max++;
				}
			}
		}
		
		return currentDay;
	}
	
	private final int getMonth(int lastYear, int lastMonth, int lastDay, int days){
		int currentDay = lastDay + days;
		int month = lastMonth;
		
		int max = monthLookup.get(lastMonth);
		while(currentDay > max){
			currentDay -= max;
			if(month == MONTHS_IN_YEAR){
				month = 0;
				lastYear++;
			}else{
				month++;
			}
			max = monthLookup.get(month);
			if(month == FEBRUARY){
				if(lastYear % 4 == 0){
					max++;
				}
			}
		}
		
		return month;
	}
	
	private final int getYear(int lastYear, int lastMonth, int lastDay, int days){
		int currentDay = lastDay + days;
		int month = lastMonth;
		
		int max = monthLookup.get(lastMonth);
		while(currentDay > max){
			currentDay -= max;
			if(month == MONTHS_IN_YEAR){
				month = 0;
				lastYear++;
			}else{
				month++;
			}
			max = monthLookup.get(month);
			if(month == FEBRUARY){
				if(lastYear % 4 == 0){
					max++;
				}
			}
		}
		
		return lastYear;
	}
	
	public final void init() throws IOException, InterruptedException{
		run();
	}
}
