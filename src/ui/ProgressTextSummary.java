package ui;
/**
 *@author mike802
 *
 * ??? - 2017
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import config.ConfigProgressTextSummary;
import core.Activity;
import core.ProgressCalculator;
import core.Properties;
import core.SummaryData;

public class ProgressTextSummary extends ConfigProgressTextSummary{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 7798743647638083833L;

	private Properties properties;
	//private ProgressScreen progressScreen;
	private ProgressCalculator progressCalculator;
	private ListSelectionHandler listHandler;
	private ButtonHandler buttonHandler;
	
	private JList<String> selectableList;
	private JScrollPane summaryScroll;
	private ListSelectionModel lsm;
	
	private JButton changeView;
	
	private boolean summary = false, all = false, category = false;
	
	private boolean initialized = false;
	
	public ProgressTextSummary(Properties p){
		properties = p;
		progressCalculator = properties.getProgressCalculator();
		//progressScreen = properties.getProgressScreen();
		listHandler = new ListSelectionHandler();
		buttonHandler = new ButtonHandler();
	}
			
	public void createPage() throws IOException{
		//setPreferredSize(new Dimension(
		//		DISPLAY_WIDTH, 
		//		DISPLAY_HEIGHT + 5 + 30 + 30));
		String[] summaryList = loadSummary();
		loadDisplay(summaryList);
		summary = true;
		
		changeView = new JButton("view");
		//int buttonX = (DISPLAY_WIDTH / 2) - (100 / 2);
		//changeView.setBounds(buttonX, DISPLAY_HEIGHT + 5, 100, 30);
		changeView.setPreferredSize(new Dimension(100, 30));
		changeView.addActionListener(buttonHandler);
		add(changeView);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == changeView){
				if(summary){
					summary = false;
					all = true;
					category = false;
					String[] summaryList = loadAllData();
					loadDisplay(summaryList);
				}else if(all){
					summary = false;
					all = false;
					category = true;
					//String[] summaryList = loadCategoryData("");
					String[] summaryList = loadSummary();
					loadDisplay(summaryList);					
				}else if(category){
					summary = true;
					all = false;
					category = false;
					String[] summaryList = loadSummary();
					loadDisplay(summaryList);
				}
			}
		}
	}
	
	private class ListSelectionHandler implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent event) {
			
			//String element = selectableList.getSelectedValue();
			//progressScreen = properties.getProgressScreen();
			//progressScreen.setSelectedElement(element);
			
		}
	}
	
	public String[] loadSummary(){
		ArrayList<SummaryData> summary = progressCalculator.getSummary();
		
		ArrayList<String> summaryList = new ArrayList<String>();
		for(int x = 0; x < summary.size(); x++){
			SummaryData data = summary.get(x);
			String line = data.printSummarizedData();
			summaryList.add(line);
		}
		String[] list = new String[summaryList.size()];
		for(int x = 0; x < list.length; x++){
			list[x] = summaryList.get(x);
		}
		
		return list;
	}
	
	public String[] loadAllData(){
		ArrayList<SummaryData> summary = progressCalculator.getSummary();
		
		ArrayList<String> summaryList = new ArrayList<String>();
		for(int x = 0; x < summary.size(); x++){
			SummaryData data = summary.get(x);
			ArrayList<Activity> activityList = data.getActivityList();
			String name = data.getName();
			for(int y = 0; y < activityList.size(); y++){
				String line = activityList.get(y).printActivity();
				line = name + " - " + line;
				summaryList.add(line);
			}
		}
		String[] list = new String[summaryList.size()];
		for(int x = 0; x < list.length; x++){
			list[x] = summaryList.get(x);
		}
		
		return list;
	}
	
	public String[] loadCategoryData(String category){
		ArrayList<SummaryData> summary = progressCalculator.getSummary();
		
		ArrayList<String> summaryList = new ArrayList<String>();
		for(int x = 0; x < summary.size(); x++){
			SummaryData data = summary.get(x);
			ArrayList<Activity> activityList = data.getActivityList();
			for(int y = 0; y < activityList.size(); y++){
				String line = activityList.get(y).printActivity();
				if(line.contains(category)){
					summaryList.add(line);
				}
			}
		}
		String[] list = new String[summaryList.size()];
		for(int x = 0; x < list.length; x++){
			list[x] = summaryList.get(x);
		}
		
		return list;
	}
	
	public void loadDisplay(String[] summaryList){
		if(selectableList == null){
			selectableList = new JList<>(summaryList);
			selectableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lsm = selectableList.getSelectionModel();
			lsm.addListSelectionListener(listHandler);
			summaryScroll = new JScrollPane(selectableList);
			summaryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			summaryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			summaryScroll.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT- 5 - 5 - 30));
			//summaryScroll.setBounds(0,0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		}else{
			JList<String> newList = new JList<>(summaryList);
			selectableList.setModel(newList.getModel());			
		}
		
		add(summaryScroll);
	}
	
	public final void init() throws IOException{
		if(!initialized){
			progressCalculator.init();
			createPage();
			initialized = true;
		}
	}
}
