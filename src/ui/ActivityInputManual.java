package ui;
/**
 * @author mike802
 * 
 * ??? - 2017
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.ProgressChecker;
import core.Properties;

public class ActivityInputManual extends JInternalFrame {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 727419756631545957L;
	
	private ButtonHandler buttonHandler;
	private Properties properties;
	
	private JComboBox<String> activities, months, year;
	private String[] activityList, monthList, yearList;
	//private JComboBox<String> days, years;
	private JCheckBox completed;
	private JTextField date, duration;
	private JButton submit, exit;
	
	private boolean initialized = false;
	
	public ActivityInputManual(Properties p){
		properties = p;
		buttonHandler = new ButtonHandler();
		
		monthList = new String[]{"January", "February", "March", "April", 
				"May", "June", "July", "August", "September", "October", 
				"November", "December"};
		yearList = new String[]{"2015", "2014", "2013", 
				"2012", "2011", "2010", "2009", "2008", "2007", "2006", 
				"2005"};
		yearList = verifyYear();
	}
	
	private void createPage() throws IOException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Calendar calendar = Calendar.getInstance();
		activityList = loadActivities();
		activities = new JComboBox<String>(activityList);
		activities.setPreferredSize(new Dimension(100, 30));
		JLabel activitySpace = new JLabel();
		activitySpace.setPreferredSize(new Dimension(310, 30));
		
		months = new JComboBox<String>(monthList);
		months.setPreferredSize(new Dimension(100, 30));
		months.setSelectedIndex(calendar.get(Calendar.MONTH));
		JLabel monthsLabel = new JLabel("month");
		monthsLabel.setPreferredSize(new Dimension(100, 30));
		
		date = new JTextField();
		date.setPreferredSize(new Dimension(100, 30));
		date.setText("" + calendar.get(Calendar.DATE));
		JLabel dateLabel = new JLabel("date");
		dateLabel.setPreferredSize(new Dimension(100, 30));
		
		year = new JComboBox<String>(yearList);
		year.setPreferredSize(new Dimension(100, 30));
		JLabel yearLabel = new JLabel("year");
		yearLabel.setPreferredSize(new Dimension(100, 30));
		
		duration = new JTextField();
		duration.setPreferredSize(new Dimension(100, 30));
		duration.setText("30");
		JLabel durationLabel = new JLabel("duration (mins)");
		durationLabel.setPreferredSize(new Dimension(100, 30));
		
		completed = new JCheckBox();
		JLabel checkBoxLabel = new JLabel("check for negative (missed) entry");
		
		submit = new JButton("submit");
		submit.setPreferredSize(new Dimension(100, 30));
		submit.addActionListener(buttonHandler);
		
		exit = new JButton("exit");
		exit.setPreferredSize(new Dimension(100, 30));
		exit.addActionListener(buttonHandler);
		
		JPanel panel1, panel2, panel3, panel4, panel5;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		
		panel1.add(Box.createHorizontalGlue());
		panel1.add(activities);
		panel1.add(activitySpace);
		
		panel2.add(Box.createHorizontalGlue());
		panel2.add(monthsLabel);
		panel2.add(dateLabel);
		panel2.add(yearLabel);
		panel2.add(durationLabel);
		
		panel3.add(Box.createHorizontalGlue());
		panel3.add(months);
		panel3.add(date);
		panel3.add(year);
		panel3.add(duration);
		
		panel4.add(Box.createHorizontalGlue());
		panel4.add(completed);
		panel4.add(checkBoxLabel);
		
		panel5.add(Box.createHorizontalGlue());
		panel5.add(submit);
		panel5.add(exit);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		box.add(panel4);
		box.add(panel5);
		
		add(box);
		pack();
		setVisible(true);
	}
	
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == submit){
				try {
					String entry = createEntry();
					boolean invalid = false;
					if(entry == null){
						JOptionPane.showMessageDialog(null, 
								"Please enter a valid day/month/year for this activity",
								"non-valid date",
								JOptionPane.WARNING_MESSAGE, 
								new ImageIcon(properties.getCompany()));
						invalid = true;
					}
					if(!invalid && isDuplicate(entry)){
						JOptionPane.showMessageDialog(null, 
								"Please enter original data",
								"Duplicate data found", 
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(properties.getCompany()));
						invalid = true;
					}
					if(!invalid){
						String path = getPath();
						saveEntry(path, entry);
						int selected = activities.getSelectedIndex();
						properties.getProgressScreen().reloadChart(
								activities.getItemAt(selected), 
								"number completed");
						properties.getProgressScreen().refreshInput();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == exit){
				dispose();
			}
		}
	}
	
	private String[] loadActivities() throws IOException{
		String path = properties.getSchedules();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> scheduleList = new ArrayList<String>();
		while(reader.ready()){
			String line = reader.readLine();
			scheduleList.add(line);
		}
		reader.close();
		
		String[] list = new String[scheduleList.size()];
		for(int x = 0; x < scheduleList.size(); x++){
			list[x] = scheduleList.get(x);
		}
		return list;
	}
	
	private String createEntry(){
		int selectedMonth = months.getSelectedIndex();
		//String selectedMonth = monthList[selection];
		String dateEntered = date.getText();
		String durationEntered = duration.getText();
		int selection = year.getSelectedIndex();
		String yearSelected = yearList[selection];
		
		if(isFutureDate(
				Integer.parseInt(yearSelected), 
				selectedMonth, 
				Integer.parseInt(dateEntered))){
			return null;
		}
		
		if(!isValidDay(Integer.parseInt(yearSelected), 
				selectedMonth, 
				Integer.parseInt(dateEntered))){
			return null;
		}
		
		String newEntry = yearSelected + "," + formatMonth(selectedMonth) + "," + 
				formatDate(Integer.parseInt(dateEntered)) + "," + durationEntered + 
				"," + "0" + ",";
		
		boolean completedStatus = !completed.isSelected();
		
		if(completedStatus){
			newEntry += "true";
		}else{
			newEntry += "false";
		}
		
		return newEntry;
	}
	
	private String getPath() throws IOException{
		String username = properties.getUsername();
		String root = properties.getRootDir();
		String path = root + File.separator + username + 
				File.separator + "progress";
		int selection = activities.getSelectedIndex();
		String activity = activityList[selection];
		path = path + File.separator + activity + "_progress.txt";
		return path;
	}
	
	private ArrayList<String> readPreviousRecords(String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> record = new ArrayList<String>();
		while(reader.ready()){
			String line = reader.readLine();
			record.add(line);
		}
		reader.close();
		
		return record;
	}
	
	private void saveEntry(String path, String newEntry) throws IOException{
		ArrayList<String> records = readPreviousRecords(path);
		records.add(newEntry);
		records = chronSort(records);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		for(int x = 0; x < (records.size() - 1); x++){
			writer.write(records.get(x) + "\n");
		}
		writer.write(records.get(records.size() - 1));
		writer.close();
	}
	
	private ArrayList<String> chronSort(ArrayList<String> list){
		
		for(int x = 0; x < list.size(); x++){
			for(int y = 0; y < list.size(); y++){
				String item1 = list.get(x);
				String item2 = list.get(y);
				if(item2.compareTo(item1) > 0){
					list.set(x, item2);
					list.set(y, item1);
				}
			}
		}
		
		return list;
	}
	
	private String[] verifyYear(){
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int previous = Integer.parseInt(yearList[0]);
		int difference = currentYear - previous;
		String[] newYearList = new String[yearList.length + difference];
		String[] diffList = new String[difference];
		if(diffList.length > 0){
			diffList[0] = "" + currentYear;
			System.out.println("SYSTEM OUT OF DATE");
		}
		for(int x = 1; x < difference; x++){
			currentYear -= 1;
			diffList[x] = "" + currentYear;
			System.out.println("Update found: " + currentYear);
		}
		int counter = 0;
		boolean change = false;
		if(diffList.length > 0){
			for(int y = 0; y < difference + yearList.length; y++){
				if(!change){
					newYearList[y] = diffList[counter];
					if(counter + 1 >= diffList.length){
						change = true;
						counter = 0;
					}else{
						counter++;
					}
				}else{
					newYearList[y] = yearList[counter];
					counter++;
				}
			}
		}else{
			return yearList;
		}
		return newYearList;
	}
	
	private boolean isDuplicate(String entry) throws IOException{
		entry = entry.substring(0, entry.lastIndexOf(","));
		int selected = activities.getSelectedIndex();
		String activity = activityList[selected];
		String username = properties.getUsername();
		String path = properties.getRootDir() + File.separator +
				username + File.separator + "progress" +
				File.separator + activity + "_progress.txt";
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()){
			String line = reader.readLine();
			line = line.substring(0, line.lastIndexOf(","));
			if(line.compareTo(entry) == 0){
				reader.close();
				System.out.println("line: " + line + ", " + "entry: " + entry);
				return true;
			}
		}
		reader.close();
		
		return false;
	}
	
	private boolean isFutureDate(int year, int month, int date){
		System.out.println("FUTURE CHECK");
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(currentYear < year){
			System.out.println("Fail: " + currentYear + "<" + year);
			return true;
		}
		int currentMonth = calendar.get(Calendar.MONTH);
		if(currentYear == year && currentMonth < month){
			System.out.println("Fail: " + currentMonth + "<" + month);
			return true;
		}
		int currentDate = calendar.get(Calendar.DATE);
		if(currentYear == year &&
				currentMonth == month &&
				currentDate < date){
			System.out.println("Fail: " + currentDate + "<" + date);
			return true;
		}
		
		return false;
	}
	
	/*private boolean isInt(String input){
		
	}*/
	private boolean isValidDay(int year, int month, int day){
		ProgressChecker progressChecker = properties.getProgressChecker();
		int maxDay = progressChecker.getMonthDays(year, month + 1);
		System.out.println("maxDay: " + maxDay);
		if(day >= 1 && day <= maxDay){
			return true;
		}
		return false;
	}
	
	private String formatMonth(int month){
		if(month < 10){
			return "0" + month;
		}else{
			return "" + month;
		}
	}
	private String formatDate(int date){
		if(date < 10){
			return "0" + date;
		}else{
			return "" + date;
		}
	}
	
	public void init() throws IOException{
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
