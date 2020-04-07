package ui;
/**
 * @author mike802
 * 
 * ??? - 2017
 * 
 */

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import core.Activity;
import core.ChartFilters;
import core.IOMain;
import core.ProgressCalculator;
import core.ProgressChecker;
import core.SummaryData;
import core.Utilities;

public class ProgressScreen extends IOMain{
	
	private MenuListener menuListener = new MenuListener();
	private ActivityInputManual manualActivityInput;
	
	//private String element = null;
	private boolean chartView = false;
	
	public ProgressScreen(Utilities utils) throws IOException{
		utilities = utils;
		properties = utilities.getProperties();
		utilities.setProperties(properties);	
		progressCalculator = new ProgressCalculator(utilities);
		chartBar = new ChartBar(utilities);
		fileChooser = new JFileChooser();
	}
	
	public void createPage() throws IOException{
		progressPage = new JFrame(HEADER);
		progressPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image company = Toolkit.getDefaultToolkit().getImage(properties.getCompany());
		progressPage.setIconImage(company);
		desktopPane = new JDesktopPane();
		desktopPane.setPreferredSize(new Dimension(801, 544));
		
		menuBar = new JMenuBar();
		file = new JMenu("File");
		tools = new JMenu("Tools");
		options = new JMenu("Options");
		view = new JMenu("View");
		help = new JMenu("Help");
		
		login = new JMenuItem("login");
		login.addActionListener(menuListener);
		register = new JMenuItem("register");
		register.addActionListener(menuListener);
		exit = new JMenuItem("exit");
		exit.addActionListener(menuListener);
		
		inputData = new JMenuItem("input data");
		inputData.addActionListener(menuListener);
		quickStart = new JCheckBoxMenuItem("start from today");
		quickStart.addActionListener(menuListener);
		manualInput = new JMenuItem("manual input");
		manualInput.addActionListener(menuListener);
		editEntry = new JMenuItem("edit entry");
		editEntry.addActionListener(menuListener);
		deleteSelected = new JMenuItem("delete selected entry");
		deleteSelected.addActionListener(menuListener);
		editSchedules = new JMenuItem("edit schedules");
		editSchedules.addActionListener(menuListener);
		createNewSchedule = new JMenuItem("new schedule");
		createNewSchedule.addActionListener(menuListener);
		
		deleteUser = new JMenuItem("delete");
		deleteUser.addActionListener(menuListener);
		importData = new JMenuItem("import");
		importData.addActionListener(menuListener);
		reset = new JMenuItem("reset user data");
		reset.addActionListener(menuListener);
		backup = new JMenuItem("backup user data");
		backup.addActionListener(menuListener);
		
		textOnly = new JMenuItem("text only");
		textOnly.addActionListener(menuListener);
		filters = new JMenuItem("edit filters");
		filters.addActionListener(menuListener);
		
		faq = new JMenuItem("faq");
		faq.addActionListener(menuListener);
		about = new JMenuItem("about...");
		about.addActionListener(menuListener);
		
		file.add(login);
		file.add(register);
		file.add(exit);
		
		tools.add(inputData);
		tools.add(quickStart);
		tools.add(manualInput);
		tools.add(editEntry);
		tools.add(deleteSelected);
		tools.add(createNewSchedule);
		tools.add(editSchedules);
		
		options.add(deleteUser);
		options.add(importData);
		options.add(reset);
		options.add(backup);
		
		view.add(textOnly);
		view.add(filters);
		
		help.add(faq);
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(tools);
		menuBar.add(options);
		menuBar.add(view);
		menuBar.add(help);
		progressPage.setJMenuBar(menuBar);
		
		tools.setEnabled(false);
		options.setEnabled(false);
		view.setEnabled(false);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(new ImageIcon(properties.getBackground()));
		backgroundLabel.setBounds(0, 0, 900, 700);
		desktopPane.add(backgroundLabel);
				
		boolean started = utilities.isStarted();
		if(started){
			reloadChart();
		}else{
			panel1 = new JPanel();
			JLabel defaultChart = new JLabel();
			ImageIcon chartPic = new ImageIcon(properties.getSampleChart());
			defaultChart.setIcon(chartPic);
			panel1.add(defaultChart);
		}
		panel1.setBounds(50, 50, 701, 447);
		desktopPane.add(panel1);
		desktopPane.moveToFront(panel1);
		
		progressPage.add(desktopPane);
		progressPage.pack();
		progressPage.setVisible(true);
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == register){
				System.out.println("register");
				NewUser newUser = new NewUser(utilities);
				newUser.init();
				desktopPane.add(newUser);
				desktopPane.moveToFront(newUser);
			}else if(event.getSource() == login){
				try {
					String name = loadUserList("login");
					if(name != null ){
						properties.setUsername(name);
						String[] scheduleList = loadScheduleList();
						if(scheduleList.length > 0){
							reloadChart(scheduleList[0], properties.getMeasurement()[0]);
						}else{
							reloadChart();
						}
						tools.setEnabled(true);
						options.setEnabled(true);
						view.setEnabled(true);
					}					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == importData){
				System.out.println("importData");
				try {
					importAllUserData();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == deleteUser) {
				System.out.println("deleteUser");
				try {
					int choice = JOptionPane.showOptionDialog(null, 
							"Click \"ok\" to delete all user data", 
							"delete all user data", 
							JOptionPane.OK_CANCEL_OPTION, 
							JOptionPane.PLAIN_MESSAGE, 
							new ImageIcon(properties.getCompany()), 
							null, null);
					if(choice == 0){
						deleteAllUserData();
						String name = loadUserList("delete user");
						removeUserFromUserList(name);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == exit) {
				System.exit(0);
			}else if(event.getSource() == inputData){
				try {
					doInputData();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == editEntry){
				
			}else if(event.getSource() == deleteSelected){
				
			}else if(event.getSource() == manualInput){
				doManualInput();				
			}else if(event.getSource() == reset) {
				System.out.println("reset");
			}else if(event.getSource() == backup) {
				System.out.println("backup");
				try {
					int choice = JOptionPane.showOptionDialog(null, 
							"Click \"ok\" to backup all user data", 
							"backup all user data", 
							JOptionPane.OK_CANCEL_OPTION, 
							JOptionPane.PLAIN_MESSAGE, 
							new ImageIcon(properties.getCompany()), 
							null, null);
					if(choice == 0){
						backupAllData();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == quickStart){
				if(quickStart.isSelected()){
					JOptionPane.showMessageDialog(null, "Warning: Selecting this "
							+ "option will skip data entry since last use of application.  "
							+ "Ok to continue.", "Quick Start", 0,
							new ImageIcon(properties.getCompany()));
				}
			}else if(event.getSource() == textOnly){
				try {
					doViewSwitch();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == filters){
				doEditFilters();
			}else if(event.getSource() == editSchedules){
				try {
					String[] scheduleList = loadScheduleList();
					Object choice = JOptionPane.showInputDialog(null, "schedules", 
							"please slected schedule to edit", 
							JOptionPane.QUESTION_MESSAGE, 
							new ImageIcon(properties.getCompany()), 
							scheduleList, 0);
					if(choice != null){
						BasicForm basicForm = new BasicForm(utilities);
						basicForm.init();
						String selection = (String)choice;
						basicForm.edit(selection);
						desktopPane.add(basicForm);
						desktopPane.moveToFront(basicForm);
					}
				} catch (HeadlessException | IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == createNewSchedule){
				BasicForm basicForm = new BasicForm(utilities);
				basicForm.init();
				desktopPane.add(basicForm);
				desktopPane.moveToFront(basicForm);
			}else if(event.getSource() == faq){
				JOptionPane.showMessageDialog(null, "Frequently Asked Questions - \n\n" +
						"all usernames must be unique", 
						"faq", 
						JOptionPane.INFORMATION_MESSAGE, 
						new ImageIcon(properties.getCompany()));
			}else if(event.getSource() == about) {				
				JOptionPane.showMessageDialog(null, "schedule(tm)\n\n"
						+ "Product of:  ???\n\n"
						+ "Contact product vender", "About", 0,
						new ImageIcon(properties.getCompany()));
			}
		}
	}
	
	private final void doManualInput(){
		try {
			properties.setProgressScreen(this);
			if(progressChecker == null){
				properties.setProgressChecker(new ProgressChecker(utilities));
			}
			manualActivityInput = new ActivityInputManual(properties);
			manualActivityInput.init();
			desktopPane.add(manualActivityInput);
			desktopPane.moveToFront(manualActivityInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private final void doEditFilters(){
		ChartFilters chartFilters;
		try {
			properties.setProgressScreen(this);
			chartFilters = new ChartFilters(properties);
			chartFilters.init();
			desktopPane.add(chartFilters);
			desktopPane.moveToFront(chartFilters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final void doViewSwitch() throws IOException{
		if(chartView){
			reloadChart(
					properties.getSelectedActivity(), 
					properties.getSelectedMeasurement());
			chartView = false;
		}else{
			desktopPane.remove(panel1);
			desktopPane.repaint();
			properties.setProgressCalculator(progressCalculator);
			properties.setProgressScreen(this);
			ProgressTextSummary textSummary = new ProgressTextSummary(properties);
			textSummary.init();
			panel1 = textSummary;
			panel1.setBounds(60, 60, 681, 424);
			panel1.setVisible(true);
			desktopPane.add(panel1);
			desktopPane.moveToFront(panel1);
			chartView = true;
		}
	}
	
	private final void doInputData() throws IOException, InterruptedException{
		if(utilities.isStarted()){
			properties.setProgressScreen(this);
			utilities.setProperties(properties);
			progressChecker = new ProgressChecker(utilities);
			progressChecker.init();
			//desktopPane.add(progressChecker);
			//desktopPane.moveToFront(progressChecker);
		}
	}
	
	private final void removeUserFromUserList(String name){
		String[] newUserList = new String[userList.length - 1];
		int counter = 0;
		for(int x = 0; x < userList.length; x++){
			if(userList[x].compareTo(name) != 0){
				newUserList[counter] = userList[x];
				counter ++;
			}
		}
		userList = newUserList;
	}
	
	private final ChartPanel getChartPanel(String activityName, String progressName) throws IOException{
		progressCalculator.init();
		ArrayList<SummaryData> summary = progressCalculator.getSummary();
		ChartPanel panel = chartBar.getChartPanel(summary, activityName, progressName);
		return panel;
	}
	
	private void reloadChart() throws IOException{
		desktopPane.remove(panel1);
		desktopPane.repaint();
		panel1 = getChartPanel(null, null);
		panel1.setBounds(60, 60, 681, 424);
		panel1.setVisible(true);
		desktopPane.add(panel1);
		desktopPane.moveToFront(panel1);
	}
	public final void reloadChart(String activityName, String progressName) throws IOException{
		desktopPane.remove(panel1);
		desktopPane.repaint();
		panel1 = getChartPanel(activityName, progressName);
		panel1.setBounds(60, 60, 681, 424);
		panel1.setVisible(true);
		desktopPane.add(panel1);
		desktopPane.moveToFront(panel1);
	}
	
	public final void refreshInput(){
		desktopPane.moveToFront(manualActivityInput);
	}
	
	public final void generateActivity(int index, Activity activity){
		properties.setProgressChecker(progressChecker);
		utilities.setProperties(properties);
		ActivityInput activityInput = new ActivityInput(utilities);
		activityInput.init(index);
		activityInput.setActivity(activity);
		desktopPane.add(activityInput);
		desktopPane.moveToFront(activityInput);
	}
	
	/*public final void setSelectedElement(String selected){
		element = selected;
	}*/
	
	public final void init() throws IOException{
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
	
	public final void addActivityWindow(ActivityInput activityInput){
		desktopPane.add(activityInput);
	}
}