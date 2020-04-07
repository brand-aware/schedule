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

public class ConfigNewUser extends JInternalFrame{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -4497208743474139111L;
	
	protected final String HEADER = "Create a new profile";
	protected final String DIRECTIONS = "Create username for profile schedules and progress summaries";
	protected final String USERNAME_LABEL = "username";
	protected final String CREATE_LABEL = "create";
	protected final String CLOSE_LABEL = "close";
	
		
	protected final int USERNAME_FIELD_WIDTH = 200;
	protected final int USERNAME_FIELD_HEIGHT = 30;
	
	protected final int CREATE_BUTTON_WIDTH = 100;
	protected final int CREATE_BUTTON_HEIGHT = 30;
	
	protected final int CLOSE_BUTTON_WIDTH = 100;
	protected final int CLOSE_BUTTON_HEIGHT = 30;
	
	
	protected final String SUCCESS_MESSAGE = " has been successfully created!";
	protected final String TRY_AGAIN_MESSAGE = "Please enter a different username";
	
	public ConfigNewUser(String header){
		super(header);
	}
	
}
