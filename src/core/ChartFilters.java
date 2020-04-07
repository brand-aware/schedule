/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package core;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChartFilters extends JInternalFrame{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -8122895508569411047L;
	
	private Properties properties;
	
	private JComboBox<String> activities;
	private String[] activityList;
	private JComboBox<String> progress;
	private String[] progressList;
	//private static JComboBox<String> chartType;
	//private static String[] chartTypeList;
	private JButton apply, close;
	
	private final String DEFAULT_SCHEDULE = "<creat a schedule>";
	private boolean initialized = false;
	
	public ChartFilters(Properties p) throws IOException{
		super("Edit chart filters");
		properties = p;
	}
	
	public void createPage() throws IOException{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		
		JLabel activityLabel = new JLabel("select a dataset");
		loadActivityList();
		
		JLabel progressLabel = new JLabel("how is progress measured?");
		progressList = properties.getMeasurement();
		progress = new JComboBox<String>(progressList);
		
		apply = new JButton("apply");
		apply.setPreferredSize(new Dimension(100, 30));
		apply.addActionListener(handler);
		
		close = new JButton("close");
		close.setPreferredSize(new Dimension(100, 30));
		close.addActionListener(handler);
		
		JPanel panel1, panel2, panel3, panel4, panel5;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		
		panel1.add(Box.createGlue());
		panel1.add(activityLabel);
		
		panel2.add(Box.createGlue());
		panel2.add(activities);
		
		panel3.add(Box.createGlue());
		panel3.add(progressLabel);
		
		panel4.add(Box.createGlue());
		panel4.add(progress);
		
		panel5.add(Box.createGlue());
		panel5.add(apply);
		panel5.add(close);
		
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
			if(event.getSource() == apply){
				int index = activities.getSelectedIndex();
				String activityName = activityList[index];
				index = progress.getSelectedIndex();
				String progressName = progressList[index];
				try {
					//System.out.println(activityName + ", " + progressName);
					properties.getProgressScreen().
						reloadChart(activityName, progressName);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == close){
				dispose();
			}
		}
	}
	
	public final void loadActivityList() throws IOException{
		String path = properties.getSchedules();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		ArrayList<String> list = new ArrayList<String>();
		boolean started = false;
		while(reader.ready()){
			started = true;
			String line = reader.readLine();
			while(line.startsWith(" ")){
				line = line.replaceFirst(" ", "");
			}
			if(line.compareTo("") == 0){
				line = DEFAULT_SCHEDULE;
			}
			list.add(line);
		}
		if(!started){
			list.add(DEFAULT_SCHEDULE);
		}
		reader.close();
		
		activityList = new String[list.size()];
		for(int x = 0; x < list.size(); x++){
			activityList[x] = list.get(x);
		}
		activities = new JComboBox<String>(activityList);
	}
	
	public final void init() throws IOException{
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
