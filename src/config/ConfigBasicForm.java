/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package config;

import javax.swing.JInternalFrame;

public class ConfigBasicForm extends JInternalFrame{

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -4100514822663581341L;
	
	protected final String HEADER = "Create a new schedule";
	protected final String TITLE = "New schedule form";
	protected final String ACTIVITY_LABEL = "Activity:";
	protected final String ENTER_LABEL = "enter";
	protected final String CANCEL_LABEL = "cancel";
	
	protected final String SUNDAY = "Sunday";
	protected final String MONDAY = "Monday";
	protected final String TUESDAY = "Tuesday";
	protected final String WEDNESDAY = "Wednesday";
	protected final String THURSDAY = "Thursday";
	protected final String FRIDAY = "Friday";
	protected final String SATURDAY = "Saturday";
	
	protected static final String TIME_UNITS = "(mins)";
	protected final String[] FREQUENCY_LABELS = {"once", "twice", "three",
			"four", "five", "six", "seven", "eight", "nine", "ten" };
	
	protected static final int DAYS = 7;

	protected final int ACTIVITY_LABEL_WIDTH = 200;
	protected final int ACTIVITY_LABEL_HEIGHT = 30;
	
	protected final int ACTIVITY_FIELD_WIDTH = 200;
	protected final int ACTIVITY_FIELD_HEIGHT = 30;
	
	protected final int TIME_FIELD_WIDTH = 100;
	protected final int TIME_FIELD_HEIGHT = 30;
	
	protected final int WEEK_LABEL_WIDTH = 70;
	protected final int WEEK_LABEL_HEIGHT = 30;
	
	protected final int WEEK_CHECK_BOX_WIDTH = 70;
	protected final int WEEK_CHECK_BOX_HEIGHT = 30;
	
	protected final int ENTER_BUTTON_WIDTH = 100;
	protected final int ENTER_BUTTON_HEIGHT = 30;
	
	protected final int CANCEL_BUTTON_WIDTH = 100;
	protected final int CANCEL_BUTTON_HEIGHT = 30;
	
	
	protected final String SUCCESS_CREATE_MESSAGE = "You have successfully created a schedule for: ";
	protected final String SUCCESS_EDIT_MESSAGE = "You have successfully edited your schedule for: ";
	protected final String ERROR_MESSAGE = "Please enter a unique name";
	
	public ConfigBasicForm(String header){
		super(header);
	}
	
}
