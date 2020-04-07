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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import core.IOBasicForm;
import core.Utilities;

public class BasicForm extends IOBasicForm{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 3449364964948584916L;
	
	public BasicForm(Utilities utils){
		super("basic form");
		utilities = utils;
		properties = utilities.getProperties();
		layeredPane = new JLayeredPane();
	}
	
	public void createPage(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		CheckListener listener = new CheckListener();
		
		basicY = ACTIVITY_LABEL_HEIGHT + ACTIVITY_FIELD_HEIGHT + 
				WEEK_LABEL_HEIGHT + WEEK_CHECK_BOX_HEIGHT +
				ENTER_BUTTON_HEIGHT + 25 + 25;
		//currentX = WEEK_LABEL_WIDTH * week.length;
		currentX += WEEK_CHECK_BOX_WIDTH * week.length;
		currentX += 50 + (week.length * 5);
		layeredPane.setPreferredSize(new Dimension(currentX, basicY));
		finalX = currentX;
		currentX = 0;
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(new ImageIcon(properties.getBackgroundPopup()));
		backgroundLabel.setBounds(0, 0, 700, 900);
		
		JLabel activityLabel = new JLabel(ACTIVITY_LABEL);
		currentY = 25;
		currentX = 25;
		activityLabel.setBounds(currentX, currentY, ACTIVITY_LABEL_WIDTH, ACTIVITY_LABEL_HEIGHT);
		activity = new JTextField();
		currentY += ACTIVITY_LABEL_HEIGHT + 5;
		activity.setBounds(currentX, currentY, ACTIVITY_FIELD_WIDTH, ACTIVITY_FIELD_HEIGHT);
		
		for(int x = 0; x < DAYS; x++){
			week[x] = false;
			time[x] = new JTextField();
			
			frequencies = new JComboBox<String>(frequencyList);
			freqList.add(frequencies);
			timeUnits[x] = new JLabel(TIME_UNITS);
		}
		
		currentX = 25;
		currentY += ACTIVITY_FIELD_HEIGHT + 5;
		weekLabels1[0] = new JLabel(SUNDAY);
		weekLabels1[0].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[1] = new JLabel(MONDAY);
		weekLabels1[1].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[2] = new JLabel(TUESDAY);
		weekLabels1[2].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[3] = new JLabel(WEDNESDAY);
		weekLabels1[3].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[4] = new JLabel(THURSDAY);
		weekLabels1[4].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[5] = new JLabel(FRIDAY);
		weekLabels1[5].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		currentX += WEEK_LABEL_WIDTH + 5;
		weekLabels1[6] = new JLabel(SATURDAY);
		weekLabels1[6].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
		
		currentX = 25;
		currentY += WEEK_LABEL_HEIGHT + 5;
		sun = new JCheckBox();
		sun.addItemListener(listener);
		//currentY += ACTIVITY_FIELD_HEIGHT + 5;
		sun.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		mon = new JCheckBox();
		mon.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		mon.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		tue = new JCheckBox();
		tue.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		tue.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		wed = new JCheckBox();
		wed.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		wed.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		thur = new JCheckBox();
		thur.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		thur.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		fri = new JCheckBox();
		fri.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		fri.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		sat = new JCheckBox();
		sat.addItemListener(listener);
		currentX += WEEK_CHECK_BOX_WIDTH + 5;
		sat.setBounds(currentX, currentY, WEEK_CHECK_BOX_WIDTH, WEEK_CHECK_BOX_HEIGHT);
		
		currentX = (finalX / 2) - ((ENTER_BUTTON_WIDTH * 2 + 5) / 2);
		currentY += WEEK_CHECK_BOX_HEIGHT + 5;
		enter = new JButton(ENTER_LABEL);
		enter.setBounds(currentX, currentY, ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT);
		enter.addActionListener(handler);
		
		currentX += ENTER_BUTTON_WIDTH + 5;
		cancel = new JButton(CANCEL_LABEL);
		cancel.setBounds(currentX, currentY, CANCEL_BUTTON_WIDTH, CANCEL_BUTTON_HEIGHT);
		cancel.addActionListener(handler);
		
		weekLabels2[0] = new JLabel(SUNDAY);
		weekLabels2[1] = new JLabel(MONDAY);
		weekLabels2[2] = new JLabel(TUESDAY);
		weekLabels2[3] = new JLabel(WEDNESDAY);
		weekLabels2[4] = new JLabel(THURSDAY);
		weekLabels2[5] = new JLabel(FRIDAY);
		weekLabels2[6] = new JLabel(SATURDAY);
		
		layeredPane.add(backgroundLabel);
		layeredPane.add(activityLabel);
		layeredPane.add(activity);
		
		for(int y = 0; y < DAYS; y++){
			layeredPane.add(weekLabels1[y]);
			layeredPane.add(weekLabels2[y]);
			layeredPane.add(time[y]);
			layeredPane.add(timeUnits[y]);
			layeredPane.add(freqList.get(y));
		}
		
		layeredPane.add(sun);
		layeredPane.add(mon);
		layeredPane.add(tue);
		layeredPane.add(wed);
		layeredPane.add(thur);
		layeredPane.add(fri);
		layeredPane.add(sat);
		
		layeredPane.add(enter);
		layeredPane.add(cancel);
		
		layeredPane.moveToFront(activityLabel);
		layeredPane.moveToFront(activity);
		
		for(int y = 0; y < DAYS; y++){
			layeredPane.moveToFront(weekLabels1[y]);
			layeredPane.moveToBack(weekLabels2[y]);
			layeredPane.moveToBack(time[y]);
			layeredPane.moveToBack(timeUnits[y]);
			layeredPane.moveToBack(freqList.get(y));
		}
		
		layeredPane.moveToFront(sun);
		layeredPane.moveToFront(mon);
		layeredPane.moveToFront(tue);
		layeredPane.moveToFront(wed);
		layeredPane.moveToFront(thur);
		layeredPane.moveToFront(fri);
		layeredPane.moveToFront(sat);
		
		layeredPane.moveToFront(enter);
		layeredPane.moveToFront(cancel);
		
		add(layeredPane);
		pack();
		setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == enter){
				try {
					saveData();
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(event.getSource() == cancel){
				dispose();
			}
		}
	}
	
	private class CheckListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent event) {
			if(event.getSource() == sun){
				if(sun.isSelected()){
					week[0] = true;
				}else{
					week[0] = false;
					time[0].setText("");
				}
				resetForm();
			}else if(event.getSource() == mon){
				if(mon.isSelected()){
					week[1] = true;
				}else{
					week[1] = false;
					time[1].setText("");
				}
				resetForm();
			}else if(event.getSource() == tue){
				if(tue.isSelected()){
					week[2] = true;
				}else{
					week[2] = false;
					time[2].setText("");
				}
				resetForm();
			}else if(event.getSource() == wed){
				if(wed.isSelected()){
					week[3] = true;
				}else{
					week[3] = false;
					time[3].setText("");
				}
				resetForm();
			}else if(event.getSource() == thur){
				if(thur.isSelected()){
					week[4] = true;
				}else{
					week[4] = false;
					time[4].setText("");
				}
				resetForm();
			}else if(event.getSource() == fri){
				if(fri.isSelected()){
					week[5] = true;
				}else{
					week[5] = false;
					time[5].setText("");
				}
				resetForm();
			}else if(event.getSource() == sat){
				if(sat.isSelected()){
					week[6] = true;
				}else{
					week[6] = false;
					time[6].setText("");
				}
				resetForm();
			}
		}
	}
	
	private void resetForm(){
		for(int x = 0; x < week.length; x++){
			layeredPane.moveToBack(weekLabels2[x]);
			layeredPane.moveToBack(time[x]);
			layeredPane.moveToBack(timeUnits[x]);
			layeredPane.remove(freqList.get(x));
		}
		currentX = (finalX / 2) - ((TIME_FIELD_WIDTH + WEEK_LABEL_WIDTH + 45 + 45 + 15) / 2);
		currentY = basicY;
		
		for(int y = 0; y < week.length; y++){
			if(week[y]){
				currentY += TIME_FIELD_HEIGHT + 5;
			}
		}
		currentY -= 5;
		layeredPane.setPreferredSize(new Dimension(finalX, currentY + ENTER_BUTTON_HEIGHT + 25));
		pack();
		
		currentY = basicY - 25;
		for(int z = 0; z < week.length; z++){
			currentX = (finalX / 2) - ((TIME_FIELD_WIDTH + WEEK_LABEL_WIDTH + 45 + 45 + 15) / 2);
			if(week[z]){
				weekLabels2[z].setBounds(currentX, currentY, WEEK_LABEL_WIDTH, WEEK_LABEL_HEIGHT);
				currentX += WEEK_LABEL_WIDTH + 5;
				time[z].setBounds(currentX, currentY, TIME_FIELD_WIDTH, TIME_FIELD_HEIGHT);
				currentX += TIME_FIELD_WIDTH + 5;
				timeUnits[z].setBounds(currentX, currentY, 45, 30);
				currentX += 45 + 5;
				freqList.get(z).setBounds(currentX, currentY, 75, 30);
				
				layeredPane.moveToFront(weekLabels2[z]);
				layeredPane.moveToFront(time[z]);
				layeredPane.moveToFront(timeUnits[z]);
				layeredPane.add(freqList.get(z));
				layeredPane.moveToFront(freqList.get(z));
				currentY += TIME_FIELD_HEIGHT + 5;
			}
		}
		layeredPane.moveToBack(enter);
		layeredPane.moveToBack(cancel);
		currentY += TIME_FIELD_HEIGHT + 5;
		currentX = (finalX / 2) - ((ENTER_BUTTON_WIDTH * 2 + 5) / 2);
		enter.setBounds(currentX, currentY, ENTER_BUTTON_WIDTH, ENTER_BUTTON_HEIGHT);
		currentX += ENTER_BUTTON_WIDTH + 5;
		cancel.setBounds(currentX, currentY, CANCEL_BUTTON_WIDTH, CANCEL_BUTTON_HEIGHT);
		layeredPane.moveToFront(enter);
		layeredPane.moveToFront(cancel);
	}
	
	/*private final void selectDay(int index){
		if(index == 0){
			sun.setSelected(true);
		}else if(index == 1){
			mon.setSelected(true);
		}else if(index == 2){
			tue.setSelected(true);
		}else if(index == 3){
			wed.setSelected(true);
		}else if(index == 4){
			thur.setSelected(true);
		}else if(index == 5){
			fri.setSelected(true);
		}else if(index == 6){
			sat.setSelected(true);
		}
	}*/
	
	public final void edit(String scheduleName) throws IOException{
		load(properties.getScheduleDir() + scheduleName + ".txt");
		resetForm();
	}
	
	public final void init(){
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
