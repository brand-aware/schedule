/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package config;
import java.awt.Font;

import javax.swing.JInternalFrame;

public class ConfigActivityInput extends JInternalFrame{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -7074668150824706118L;
	
	protected final String HEADER = "Time to record your progress!";
	protected final String DIRECTIONS = "Did you do the activity:";
	protected final String YES_LABEL = "yes";
	protected final String NO_LABEL = "no";
	protected final String END_LABEL = "end";
	
	protected static final String DISPLAY_PT1 = "For ";
	protected static final String DISPLAY_PT2 = "mins. on ";
	
	
	protected final String DIRECTIONS_FONT_NAME = Font.SANS_SERIF;
	protected final int DIRECTIONS_FONT_STYLE = Font.PLAIN;
	protected final int DIRECTIONS_FONT_SIZE = 24;
	
	protected final String ACTIVITY_FONT_NAME = Font.SANS_SERIF;
	protected final int ACTIVITY_FONT_STYLE = Font.PLAIN;
	protected final int ACTIVITY_FONT_SIZE = 20;

	protected final String INFO_FONT_NAME = Font.SANS_SERIF;
	protected final int INFO_FONT_STYLE = Font.PLAIN;
	protected final int INFO_FONT_SIZE = 20;
	
	
	protected final int YES_BUTTON_WIDTH = 100;
	protected final int YES_BUTTON_HEIGHT = 30;
	
	protected final int NO_BUTTON_WIDTH = 100;
	protected final int NO_BUTTON_HEIGHT = 30;
	
	protected final int END_BUTTON_WIDTH = 100;
	protected final int END_BUTTON_HEIGHT = 30;
	
	
}
