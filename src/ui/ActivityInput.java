/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package ui;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.ConfigActivityInput;
import core.Activity;
import core.ProgressChecker;
import core.Properties;
import core.Utilities;


public class ActivityInput extends ConfigActivityInput{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -4358821336089010346L;
	
	private Utilities utilities;
	private ProgressChecker progressChecker;
	private Properties properties;
	
	private JButton yes, no, end;
	private JLabel part1, part2, part3;
	private Activity selectedActivity;
	
	private int id;
	private boolean initialized = false;
	
	public ActivityInput(Utilities utils){
		utilities = utils;
		properties = utilities.getProperties();
		utilities.setProperties(properties);
		progressChecker = properties.getProgressChecker();
	}
	
	public void createPage(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		
		int totalX = 410;
		int totalY = 235;
		setPreferredSize(new Dimension(totalX, totalY));
		
		part1 = new JLabel(DIRECTIONS);
		part1.setFont(new Font(DIRECTIONS_FONT_NAME, DIRECTIONS_FONT_STYLE, DIRECTIONS_FONT_SIZE));
		part1.setPreferredSize(new Dimension(310, 30));
		part2 = new JLabel();
		part2.setFont(new Font(ACTIVITY_FONT_NAME, ACTIVITY_FONT_STYLE, ACTIVITY_FONT_SIZE));
		part2.setPreferredSize(new Dimension(310, 30));
		part3 = new JLabel();
		part3.setFont(new Font(INFO_FONT_NAME, INFO_FONT_STYLE, INFO_FONT_SIZE));
		part3.setPreferredSize(new Dimension(310, 30));
		
		yes = new JButton(YES_LABEL);
		yes.setPreferredSize(new Dimension(YES_BUTTON_WIDTH, YES_BUTTON_HEIGHT));
		yes.addActionListener(handler);
		
		no = new JButton(NO_LABEL);
		no.setPreferredSize(new Dimension(NO_BUTTON_WIDTH, NO_BUTTON_HEIGHT));
		no.addActionListener(handler);
		
		end = new JButton(END_LABEL);
		end.setPreferredSize(new Dimension(END_BUTTON_WIDTH, END_BUTTON_HEIGHT));
		end.addActionListener(handler);
		
		JPanel panel1, panel2, panel3, panel4;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		
		panel1.add(Box.createGlue());
		panel1.add(part1);
		
		panel2.add(Box.createGlue());
		panel2.add(part2);
		
		panel3.add(Box.createGlue());
		panel3.add(part3);
		
		panel4.add(Box.createGlue());
		panel4.add(yes);
		panel4.add(no);
		panel4.add(end);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		box.add(panel4);
		
		add(box);
		pack();
		setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == yes){
				try {
					selectedActivity.recordProgress(true);
					progressChecker.showGeneratedActivity(id + 1);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == no){
				try {
					selectedActivity.recordProgress(false);
					progressChecker.showGeneratedActivity(id + 1);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == end){
				try {
					utilities.recordPrevious();
					progressChecker.endInput();
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public final void setActivity(Activity activity){
		selectedActivity = activity;
		part2.setText(activity.getName());
		part3.setText(
				DISPLAY_PT1 + 
				activity.getDuration() + 
				DISPLAY_PT2 + 
				activity.getDate());
		pack();
	}
	
	public final void init(int index){
		id = index;
		if(!initialized){
			createPage();
			initialized = true;
		}else{
			show();
		}
	}
	public final void callDispose(){
		dispose();
	}
}
