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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class IOBasicForm extends CommonBasicForm{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 2653812021622075127L;

	public IOBasicForm(String header) {
		super(header);
	}

	protected final boolean saveData() throws IOException{
		String buffer = "";
		String activityName = activity.getText();
		boolean added = false;
		added = addActivity(activityName);
		if(added || edit){
			buffer += activityName + "#";
			for(int x = 0; x < DAYS; x++){
				if(week[x]){
					String minutes = time[x].getText();
					JComboBox<String> freqDay = freqList.get(x);
					int frequency = freqDay.getSelectedIndex();
					buffer += minutes + ",";
					buffer += x + ",";
					buffer += frequency + "#";
				}
			}
			int end = buffer.lastIndexOf("#");
			buffer += buffer.substring(0, end);
			String path = properties.getScheduleDir();
			path += activityName + ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(buffer);
			writer.close();
			
			if(!utilities.isStarted()){
				utilities.recordPrevious();
			}
			
			return true;
		}else{
			JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
			return false;
		}
	}
	
	protected final boolean addActivity(String text) throws IOException{
		boolean added = false;
		String path = properties.getSchedules();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String buffer = "";
		while(reader.ready()){
			String line = reader.readLine();
			if(line.compareTo(text) == 0){
				reader.close();
				return false;
			}
			buffer += line + "\n";
		}
		buffer += text;
		added = true;
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		writer.write(buffer);
		writer.close();
		
		return added;
	}
	
	public final void load(String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while(reader.ready()){
			String data = reader.readLine();
			String[] schedule = data.split("#");
			String[] section;
			String selectedActivity = schedule[0];
			activity.setText(selectedActivity);
			for(int x = 1; x < schedule.length; x++){
				selectedActivity = schedule[x];
				section = selectedActivity.split(",");
				String day = section[1];
				int index = Integer.parseInt(day);
				week[index] = true;
				String duration = section[0];
				time[index].setText(duration);
				String frequency = section[2];
				int selection = Integer.parseInt(frequency);
				JComboBox<String> freqSelector = freqList.get(index);
				freqSelector.setSelectedIndex(selection);
			}
		}
		reader.close();
		
		if(week[0]){
			sun.setSelected(true);
		}
		if(week[1]){
			mon.setSelected(true);
		}
		if(week[2]){
			tue.setSelected(true);
		}
		if(week[3]){
			wed.setSelected(true);
		}
		if(week[4]){
			thur.setSelected(true);
		}
		if(week[5]){
			fri.setSelected(true);
		}
		if(week[6]){
			sat.setSelected(true);
		}
	}

}
